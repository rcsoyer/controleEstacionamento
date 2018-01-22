import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ControleEstacionamentoSharedModule } from '../../shared';
import {
    VeiculoService,
    VeiculoPopupService,
    VeiculoComponent,
    VeiculoDetailComponent,
    VeiculoDialogComponent,
    VeiculoPopupComponent,
    VeiculoDeletePopupComponent,
    VeiculoDeleteDialogComponent,
    veiculoRoute,
    veiculoPopupRoute,
    VeiculoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...veiculoRoute,
    ...veiculoPopupRoute,
];

@NgModule({
    imports: [
        ControleEstacionamentoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        VeiculoComponent,
        VeiculoDetailComponent,
        VeiculoDialogComponent,
        VeiculoDeleteDialogComponent,
        VeiculoPopupComponent,
        VeiculoDeletePopupComponent,
    ],
    entryComponents: [
        VeiculoComponent,
        VeiculoDialogComponent,
        VeiculoPopupComponent,
        VeiculoDeleteDialogComponent,
        VeiculoDeletePopupComponent,
    ],
    providers: [
        VeiculoService,
        VeiculoPopupService,
        VeiculoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ControleEstacionamentoVeiculoModule {}
