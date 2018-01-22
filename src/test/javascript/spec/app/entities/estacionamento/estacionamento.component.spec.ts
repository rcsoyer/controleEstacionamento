/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { ControleEstacionamentoTestModule } from '../../../test.module';
import { EstacionamentoComponent } from '../../../../../../main/webapp/app/entities/estacionamento/estacionamento.component';
import { EstacionamentoService } from '../../../../../../main/webapp/app/entities/estacionamento/estacionamento.service';
import { Estacionamento } from '../../../../../../main/webapp/app/entities/estacionamento/estacionamento.model';

describe('Component Tests', () => {

    describe('Estacionamento Management Component', () => {
        let comp: EstacionamentoComponent;
        let fixture: ComponentFixture<EstacionamentoComponent>;
        let service: EstacionamentoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ControleEstacionamentoTestModule],
                declarations: [EstacionamentoComponent],
                providers: [
                    EstacionamentoService
                ]
            })
            .overrideTemplate(EstacionamentoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EstacionamentoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstacionamentoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Estacionamento(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.estacionamentos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
