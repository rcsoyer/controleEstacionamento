import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ControleEstacionamentoSharedModule } from '../../shared';
import {
    ClienteService,
    ClientePopupService,
    ClienteComponent,
    ClienteDetailComponent,
    ClienteDialogComponent,
    ClientePopupComponent,
    ClienteDeletePopupComponent,
    ClienteDeleteDialogComponent,
    clienteRoute,
    clientePopupRoute,
    ClienteResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...clienteRoute,
    ...clientePopupRoute,
];

@NgModule({
    imports: [
        ControleEstacionamentoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ClienteComponent,
        ClienteDetailComponent,
        ClienteDialogComponent,
        ClienteDeleteDialogComponent,
        ClientePopupComponent,
        ClienteDeletePopupComponent,
    ],
    entryComponents: [
        ClienteComponent,
        ClienteDialogComponent,
        ClientePopupComponent,
        ClienteDeleteDialogComponent,
        ClienteDeletePopupComponent,
    ],
    providers: [
        ClienteService,
        ClientePopupService,
        ClienteResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ControleEstacionamentoClienteModule {}
