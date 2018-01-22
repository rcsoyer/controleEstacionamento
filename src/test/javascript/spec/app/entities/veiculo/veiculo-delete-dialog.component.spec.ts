/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ControleEstacionamentoTestModule } from '../../../test.module';
import { VeiculoDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/veiculo/veiculo-delete-dialog.component';
import { VeiculoService } from '../../../../../../main/webapp/app/entities/veiculo/veiculo.service';

describe('Component Tests', () => {

    describe('Veiculo Management Delete Component', () => {
        let comp: VeiculoDeleteDialogComponent;
        let fixture: ComponentFixture<VeiculoDeleteDialogComponent>;
        let service: VeiculoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ControleEstacionamentoTestModule],
                declarations: [VeiculoDeleteDialogComponent],
                providers: [
                    VeiculoService
                ]
            })
            .overrideTemplate(VeiculoDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VeiculoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VeiculoService);
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
