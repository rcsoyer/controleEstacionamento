import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Patio } from './patio.model';
import { PatioService } from './patio.service';

@Component({
    selector: 'jhi-patio-detail',
    templateUrl: './patio-detail.component.html'
})
export class PatioDetailComponent implements OnInit, OnDestroy {

    patio: Patio;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private patioService: PatioService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPatios();
    }

    load(id) {
        this.patioService.find(id).subscribe((patio) => {
            this.patio = patio;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPatios() {
        this.eventSubscriber = this.eventManager.subscribe(
            'patioListModification',
            (response) => this.load(this.patio.id)
        );
    }
}
