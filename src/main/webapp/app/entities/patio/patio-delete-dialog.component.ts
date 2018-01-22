import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Patio } from './patio.model';
import { PatioPopupService } from './patio-popup.service';
import { PatioService } from './patio.service';

@Component({
    selector: 'jhi-patio-delete-dialog',
    templateUrl: './patio-delete-dialog.component.html'
})
export class PatioDeleteDialogComponent {

    patio: Patio;

    constructor(
        private patioService: PatioService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.patioService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'patioListModification',
                content: 'Deleted an patio'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-patio-delete-popup',
    template: ''
})
export class PatioDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private patioPopupService: PatioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.patioPopupService
                .open(PatioDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
