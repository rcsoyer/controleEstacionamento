import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PatioComponent } from './patio.component';
import { PatioDetailComponent } from './patio-detail.component';
import { PatioPopupComponent } from './patio-dialog.component';
import { PatioDeletePopupComponent } from './patio-delete-dialog.component';

@Injectable()
export class PatioResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const patioRoute: Routes = [
    {
        path: 'patio',
        component: PatioComponent,
        resolve: {
            'pagingParams': PatioResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'controleEstacionamentoApp.patio.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'patio/:id',
        component: PatioDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'controleEstacionamentoApp.patio.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const patioPopupRoute: Routes = [
    {
        path: 'patio-new',
        component: PatioPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'controleEstacionamentoApp.patio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'patio/:id/edit',
        component: PatioPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'controleEstacionamentoApp.patio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'patio/:id/delete',
        component: PatioDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'controleEstacionamentoApp.patio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
