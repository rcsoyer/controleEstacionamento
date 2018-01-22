import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Estacionamento } from './estacionamento.model';
import { EstacionamentoPopupService } from './estacionamento-popup.service';
import { EstacionamentoService } from './estacionamento.service';
import { Veiculo, VeiculoService } from '../veiculo';
import { Patio, PatioService } from '../patio';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-estacionamento-dialog',
    templateUrl: './estacionamento-dialog.component.html'
})
export class EstacionamentoDialogComponent implements OnInit {

    estacionamento: Estacionamento;
    isSaving: boolean;

    veiculos: Veiculo[];

    patios: Patio[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private estacionamentoService: EstacionamentoService,
        private veiculoService: VeiculoService,
        private patioService: PatioService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.veiculoService
            .query({filter: 'estacionamento-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.estacionamento.veiculoId) {
                    this.veiculos = res.json;
                } else {
                    this.veiculoService
                        .find(this.estacionamento.veiculoId)
                        .subscribe((subRes: Veiculo) => {
                            this.veiculos = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.patioService.query()
            .subscribe((res: ResponseWrapper) => { this.patios = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.estacionamento.id !== undefined) {
            this.subscribeToSaveResponse(
                this.estacionamentoService.update(this.estacionamento));
        } else {
            this.subscribeToSaveResponse(
                this.estacionamentoService.create(this.estacionamento));
        }
    }

    private subscribeToSaveResponse(result: Observable<Estacionamento>) {
        result.subscribe((res: Estacionamento) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Estacionamento) {
        this.eventManager.broadcast({ name: 'estacionamentoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackVeiculoById(index: number, item: Veiculo) {
        return item.id;
    }

    trackPatioById(index: number, item: Patio) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-estacionamento-popup',
    template: ''
})
export class EstacionamentoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private estacionamentoPopupService: EstacionamentoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.estacionamentoPopupService
                    .open(EstacionamentoDialogComponent as Component, params['id']);
            } else {
                this.estacionamentoPopupService
                    .open(EstacionamentoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
