/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ControleEstacionamentoTestModule } from '../../../test.module';
import { PatioDialogComponent } from '../../../../../../main/webapp/app/entities/patio/patio-dialog.component';
import { PatioService } from '../../../../../../main/webapp/app/entities/patio/patio.service';
import { Patio } from '../../../../../../main/webapp/app/entities/patio/patio.model';

describe('Component Tests', () => {

    describe('Patio Management Dialog Component', () => {
        let comp: PatioDialogComponent;
        let fixture: ComponentFixture<PatioDialogComponent>;
        let service: PatioService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ControleEstacionamentoTestModule],
                declarations: [PatioDialogComponent],
                providers: [
                    PatioService
                ]
            })
            .overrideTemplate(PatioDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PatioDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PatioService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Patio(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.patio = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'patioListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Patio();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.patio = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'patioListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
