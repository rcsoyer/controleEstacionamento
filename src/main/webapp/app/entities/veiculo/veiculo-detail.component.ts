import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Veiculo } from './veiculo.model';
import { VeiculoService } from './veiculo.service';

@Component({
    selector: 'jhi-veiculo-detail',
    templateUrl: './veiculo-detail.component.html'
})
export class VeiculoDetailComponent implements OnInit, OnDestroy {

    veiculo: Veiculo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private veiculoService: VeiculoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVeiculos();
    }

    load(id) {
        this.veiculoService.find(id).subscribe((veiculo) => {
            this.veiculo = veiculo;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVeiculos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'veiculoListModification',
            (response) => this.load(this.veiculo.id)
        );
    }
}
