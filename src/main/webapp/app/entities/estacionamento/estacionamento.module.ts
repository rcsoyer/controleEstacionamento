import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ControleEstacionamentoSharedModule } from '../../shared';
import {
    EstacionamentoService,
    EstacionamentoPopupService,
    EstacionamentoComponent,
    EstacionamentoDetailComponent,
    EstacionamentoDialogComponent,
    EstacionamentoPopupComponent,
    EstacionamentoDeletePopupComponent,
    EstacionamentoDeleteDialogComponent,
    estacionamentoRoute,
    estacionamentoPopupRoute,
    EstacionamentoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...estacionamentoRoute,
    ...estacionamentoPopupRoute,
];

@NgModule({
    imports: [
        ControleEstacionamentoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EstacionamentoComponent,
        EstacionamentoDetailComponent,
        EstacionamentoDialogComponent,
        EstacionamentoDeleteDialogComponent,
        EstacionamentoPopupComponent,
        EstacionamentoDeletePopupComponent,
    ],
    entryComponents: [
        EstacionamentoComponent,
        EstacionamentoDialogComponent,
        EstacionamentoPopupComponent,
        EstacionamentoDeleteDialogComponent,
        EstacionamentoDeletePopupComponent,
    ],
    providers: [
        EstacionamentoService,
        EstacionamentoPopupService,
        EstacionamentoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ControleEstacionamentoEstacionamentoModule {}
