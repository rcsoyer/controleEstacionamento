import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Veiculo } from './veiculo.model';
import { VeiculoPopupService } from './veiculo-popup.service';
import { VeiculoService } from './veiculo.service';
import { Cliente, ClienteService } from '../cliente';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-veiculo-dialog',
    templateUrl: './veiculo-dialog.component.html'
})
export class VeiculoDialogComponent implements OnInit {

    veiculo: Veiculo;
    isSaving: boolean;

    clientes: Cliente[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private veiculoService: VeiculoService,
        private clienteService: ClienteService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.clienteService.query()
            .subscribe((res: ResponseWrapper) => { this.clientes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.veiculo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.veiculoService.update(this.veiculo));
        } else {
            this.subscribeToSaveResponse(
                this.veiculoService.create(this.veiculo));
        }
    }

    private subscribeToSaveResponse(result: Observable<Veiculo>) {
        result.subscribe((res: Veiculo) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Veiculo) {
        this.eventManager.broadcast({ name: 'veiculoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackClienteById(index: number, item: Cliente) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-veiculo-popup',
    template: ''
})
export class VeiculoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private veiculoPopupService: VeiculoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.veiculoPopupService
                    .open(VeiculoDialogComponent as Component, params['id']);
            } else {
                this.veiculoPopupService
                    .open(VeiculoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
