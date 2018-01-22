/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ControleEstacionamentoTestModule } from '../../../test.module';
import { EstacionamentoDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/estacionamento/estacionamento-delete-dialog.component';
import { EstacionamentoService } from '../../../../../../main/webapp/app/entities/estacionamento/estacionamento.service';

describe('Component Tests', () => {

    describe('Estacionamento Management Delete Component', () => {
        let comp: EstacionamentoDeleteDialogComponent;
        let fixture: ComponentFixture<EstacionamentoDeleteDialogComponent>;
        let service: EstacionamentoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ControleEstacionamentoTestModule],
                declarations: [EstacionamentoDeleteDialogComponent],
                providers: [
                    EstacionamentoService
                ]
            })
            .overrideTemplate(EstacionamentoDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EstacionamentoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstacionamentoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
