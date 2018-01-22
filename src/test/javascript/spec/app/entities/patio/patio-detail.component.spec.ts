/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { ControleEstacionamentoTestModule } from '../../../test.module';
import { PatioDetailComponent } from '../../../../../../main/webapp/app/entities/patio/patio-detail.component';
import { PatioService } from '../../../../../../main/webapp/app/entities/patio/patio.service';
import { Patio } from '../../../../../../main/webapp/app/entities/patio/patio.model';

describe('Component Tests', () => {

    describe('Patio Management Detail Component', () => {
        let comp: PatioDetailComponent;
        let fixture: ComponentFixture<PatioDetailComponent>;
        let service: PatioService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ControleEstacionamentoTestModule],
                declarations: [PatioDetailComponent],
                providers: [
                    PatioService
                ]
            })
            .overrideTemplate(PatioDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PatioDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PatioService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Patio(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.patio).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
