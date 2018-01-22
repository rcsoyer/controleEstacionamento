import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ControleEstacionamentoClienteModule } from './cliente/cliente.module';
import { ControleEstacionamentoVeiculoModule } from './veiculo/veiculo.module';
import { ControleEstacionamentoPatioModule } from './patio/patio.module';
import { ControleEstacionamentoEstacionamentoModule } from './estacionamento/estacionamento.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ControleEstacionamentoClienteModule,
        ControleEstacionamentoVeiculoModule,
        ControleEstacionamentoPatioModule,
        ControleEstacionamentoEstacionamentoModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ControleEstacionamentoEntityModule {}
