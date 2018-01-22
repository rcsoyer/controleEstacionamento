/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ControleEstacionamentoTestModule } from '../../../test.module';
import { EstacionamentoDialogComponent } from '../../../../../../main/webapp/app/entities/estacionamento/estacionamento-dialog.component';
import { EstacionamentoService } from '../../../../../../main/webapp/app/entities/estacionamento/estacionamento.service';
import { Estacionamento } from '../../../../../../main/webapp/app/entities/estacionamento/estacionamento.model';
import { VeiculoService } from '../../../../../../main/webapp/app/entities/veiculo';
import { PatioService } from '../../../../../../main/webapp/app/entities/patio';

describe('Component Tests', () => {

    describe('Estacionamento Management Dialog Component', () => {
        let comp: EstacionamentoDialogComponent;
        let fixture: ComponentFixture<EstacionamentoDialogComponent>;
        let service: EstacionamentoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ControleEstacionamentoTestModule],
                declarations: [EstacionamentoDialogComponent],
                providers: [
                    VeiculoService,
                    PatioService,
                    EstacionamentoService
                ]
            })
            .overrideTemplate(EstacionamentoDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EstacionamentoDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstacionamentoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Estacionamento(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.estacionamento = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'estacionamentoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Estacionamento();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.estacionamento = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'estacionamentoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
