/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ControleEstacionamentoTestModule } from '../../../test.module';
import { VeiculoDialogComponent } from '../../../../../../main/webapp/app/entities/veiculo/veiculo-dialog.component';
import { VeiculoService } from '../../../../../../main/webapp/app/entities/veiculo/veiculo.service';
import { Veiculo } from '../../../../../../main/webapp/app/entities/veiculo/veiculo.model';
import { ClienteService } from '../../../../../../main/webapp/app/entities/cliente';

describe('Component Tests', () => {

    describe('Veiculo Management Dialog Component', () => {
        let comp: VeiculoDialogComponent;
        let fixture: ComponentFixture<VeiculoDialogComponent>;
        let service: VeiculoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ControleEstacionamentoTestModule],
                declarations: [VeiculoDialogComponent],
                providers: [
                    ClienteService,
                    VeiculoService
                ]
            })
            .overrideTemplate(VeiculoDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VeiculoDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VeiculoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Veiculo(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.veiculo = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'veiculoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Veiculo();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.veiculo = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'veiculoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
