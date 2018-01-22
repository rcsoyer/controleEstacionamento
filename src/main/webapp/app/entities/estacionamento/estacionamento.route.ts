import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { EstacionamentoComponent } from './estacionamento.component';
import { EstacionamentoDetailComponent } from './estacionamento-detail.component';
import { EstacionamentoPopupComponent } from './estacionamento-dialog.component';
import { EstacionamentoDeletePopupComponent } from './estacionamento-delete-dialog.component';

@Injectable()
export class EstacionamentoResolvePagingParams implements Resolve<any> {

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

export const estacionamentoRoute: Routes = [
    {
        path: 'estacionamento',
        component: EstacionamentoComponent,
        resolve: {
            'pagingParams': EstacionamentoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'controleEstacionamentoApp.estacionamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'estacionamento/:id',
        component: EstacionamentoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'controleEstacionamentoApp.estacionamento.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const estacionamentoPopupRoute: Routes = [
    {
        path: 'estacionamento-new',
        component: EstacionamentoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'controleEstacionamentoApp.estacionamento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'estacionamento/:id/edit',
        component: EstacionamentoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'controleEstacionamentoApp.estacionamento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'estacionamento/:id/delete',
        component: EstacionamentoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'controleEstacionamentoApp.estacionamento.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
