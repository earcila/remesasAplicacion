import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RemesasSharedModule } from 'app/shared';
import {
    RemesasComponent,
    RemesasDetailComponent,
    RemesasUpdateComponent,
    RemesasDeletePopupComponent,
    RemesasDeleteDialogComponent,
    remesasRoute,
    remesasPopupRoute
} from './';

const ENTITY_STATES = [...remesasRoute, ...remesasPopupRoute];

@NgModule({
    imports: [RemesasSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RemesasComponent,
        RemesasDetailComponent,
        RemesasUpdateComponent,
        RemesasDeleteDialogComponent,
        RemesasDeletePopupComponent
    ],
    entryComponents: [RemesasComponent, RemesasUpdateComponent, RemesasDeleteDialogComponent, RemesasDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RemesasRemesasModule {}
