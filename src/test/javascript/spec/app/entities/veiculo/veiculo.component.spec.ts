/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { ControleEstacionamentoTestModule } from '../../../test.module';
import { VeiculoComponent } from '../../../../../../main/webapp/app/entities/veiculo/veiculo.component';
import { VeiculoService } from '../../../../../../main/webapp/app/entities/veiculo/veiculo.service';
import { Veiculo } from '../../../../../../main/webapp/app/entities/veiculo/veiculo.model';

describe('Component Tests', () => {

    describe('Veiculo Management Component', () => {
        let comp: VeiculoComponent;
        let fixture: ComponentFixture<VeiculoComponent>;
        let service: VeiculoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ControleEstacionamentoTestModule],
                declarations: [VeiculoComponent],
                providers: [
                    VeiculoService
                ]
            })
            .overrideTemplate(VeiculoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VeiculoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VeiculoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Veiculo(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.veiculos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
