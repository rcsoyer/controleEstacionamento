/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { ControleEstacionamentoTestModule } from '../../../test.module';
import { EstacionamentoDetailComponent } from '../../../../../../main/webapp/app/entities/estacionamento/estacionamento-detail.component';
import { EstacionamentoService } from '../../../../../../main/webapp/app/entities/estacionamento/estacionamento.service';
import { Estacionamento } from '../../../../../../main/webapp/app/entities/estacionamento/estacionamento.model';

describe('Component Tests', () => {

    describe('Estacionamento Management Detail Component', () => {
        let comp: EstacionamentoDetailComponent;
        let fixture: ComponentFixture<EstacionamentoDetailComponent>;
        let service: EstacionamentoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ControleEstacionamentoTestModule],
                declarations: [EstacionamentoDetailComponent],
                providers: [
                    EstacionamentoService
                ]
            })
            .overrideTemplate(EstacionamentoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EstacionamentoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstacionamentoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Estacionamento(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.estacionamento).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
