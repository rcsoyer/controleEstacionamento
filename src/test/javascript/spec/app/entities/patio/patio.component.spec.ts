/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { ControleEstacionamentoTestModule } from '../../../test.module';
import { PatioComponent } from '../../../../../../main/webapp/app/entities/patio/patio.component';
import { PatioService } from '../../../../../../main/webapp/app/entities/patio/patio.service';
import { Patio } from '../../../../../../main/webapp/app/entities/patio/patio.model';

describe('Component Tests', () => {

    describe('Patio Management Component', () => {
        let comp: PatioComponent;
        let fixture: ComponentFixture<PatioComponent>;
        let service: PatioService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ControleEstacionamentoTestModule],
                declarations: [PatioComponent],
                providers: [
                    PatioService
                ]
            })
            .overrideTemplate(PatioComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PatioComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PatioService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Patio(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.patios[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
