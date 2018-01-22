import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { VeiculoComponent } from './veiculo.component';
import { VeiculoDetailComponent } from './veiculo-detail.component';
import { VeiculoPopupComponent } from './veiculo-dialog.component';
import { VeiculoDeletePopupComponent } from './veiculo-delete-dialog.component';

@Injectable()
export class VeiculoResolvePagingParams implements Resolve<any> {

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

export const veiculoRoute: Routes = [
    {
        path: 'veiculo',
        component: VeiculoComponent,
        resolve: {
            'pagingParams': VeiculoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'controleEstacionamentoApp.veiculo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'veiculo/:id',
        component: VeiculoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'controleEstacionamentoApp.veiculo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const veiculoPopupRoute: Routes = [
    {
        path: 'veiculo-new',
        component: VeiculoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'controleEstacionamentoApp.veiculo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'veiculo/:id/edit',
        component: VeiculoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'controleEstacionamentoApp.veiculo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'veiculo/:id/delete',
        component: VeiculoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'controleEstacionamentoApp.veiculo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
