import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Patio } from './patio.model';
import { PatioPopupService } from './patio-popup.service';
import { PatioService } from './patio.service';

@Component({
    selector: 'jhi-patio-dialog',
    templateUrl: './patio-dialog.component.html'
})
export class PatioDialogComponent implements OnInit {

    patio: Patio;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private patioService: PatioService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.patio.id !== undefined) {
            this.subscribeToSaveResponse(
                this.patioService.update(this.patio));
        } else {
            this.subscribeToSaveResponse(
                this.patioService.create(this.patio));
        }
    }

    private subscribeToSaveResponse(result: Observable<Patio>) {
        result.subscribe((res: Patio) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Patio) {
        this.eventManager.broadcast({ name: 'patioListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-patio-popup',
    template: ''
})
export class PatioPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private patioPopupService: PatioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.patioPopupService
                    .open(PatioDialogComponent as Component, params['id']);
            } else {
                this.patioPopupService
                    .open(PatioDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
