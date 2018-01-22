/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { ControleEstacionamentoTestModule } from '../../../test.module';
import { VeiculoDetailComponent } from '../../../../../../main/webapp/app/entities/veiculo/veiculo-detail.component';
import { VeiculoService } from '../../../../../../main/webapp/app/entities/veiculo/veiculo.service';
import { Veiculo } from '../../../../../../main/webapp/app/entities/veiculo/veiculo.model';

describe('Component Tests', () => {

    describe('Veiculo Management Detail Component', () => {
        let comp: VeiculoDetailComponent;
        let fixture: ComponentFixture<VeiculoDetailComponent>;
        let service: VeiculoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ControleEstacionamentoTestModule],
                declarations: [VeiculoDetailComponent],
                providers: [
                    VeiculoService
                ]
            })
            .overrideTemplate(VeiculoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VeiculoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VeiculoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Veiculo(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.veiculo).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
