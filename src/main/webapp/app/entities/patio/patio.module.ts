import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ControleEstacionamentoSharedModule } from '../../shared';
import {
    PatioService,
    PatioPopupService,
    PatioComponent,
    PatioDetailComponent,
    PatioDialogComponent,
    PatioPopupComponent,
    PatioDeletePopupComponent,
    PatioDeleteDialogComponent,
    patioRoute,
    patioPopupRoute,
    PatioResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...patioRoute,
    ...patioPopupRoute,
];

@NgModule({
    imports: [
        ControleEstacionamentoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PatioComponent,
        PatioDetailComponent,
        PatioDialogComponent,
        PatioDeleteDialogComponent,
        PatioPopupComponent,
        PatioDeletePopupComponent,
    ],
    entryComponents: [
        PatioComponent,
        PatioDialogComponent,
        PatioPopupComponent,
        PatioDeleteDialogComponent,
        PatioDeletePopupComponent,
    ],
    providers: [
        PatioService,
        PatioPopupService,
        PatioResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ControleEstacionamentoPatioModule {}
