import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Estacionamento } from './estacionamento.model';
import { EstacionamentoService } from './estacionamento.service';

@Component({
    selector: 'jhi-estacionamento-detail',
    templateUrl: './estacionamento-detail.component.html'
})
export class EstacionamentoDetailComponent implements OnInit, OnDestroy {

    estacionamento: Estacionamento;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private estacionamentoService: EstacionamentoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEstacionamentos();
    }

    load(id) {
        this.estacionamentoService.getPgCalculado(id).subscribe((estacionamento) => {
            this.estacionamento = estacionamento;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEstacionamentos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'estacionamentoListModification',
            (response) => this.load(this.estacionamento.id)
        );
    }

    pagar() {
        this.estacionamentoService.pagar(this.estacionamento);
    }
}
