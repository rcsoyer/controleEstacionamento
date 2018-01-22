import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Estacionamento } from './estacionamento.model';
import { EstacionamentoPopupService } from './estacionamento-popup.service';
import { EstacionamentoService } from './estacionamento.service';

@Component({
    selector: 'jhi-estacionamento-delete-dialog',
    templateUrl: './estacionamento-delete-dialog.component.html'
})
export class EstacionamentoDeleteDialogComponent {

    estacionamento: Estacionamento;

    constructor(
        private estacionamentoService: EstacionamentoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.estacionamentoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'estacionamentoListModification',
                content: 'Deleted an estacionamento'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-estacionamento-delete-popup',
    template: ''
})
export class EstacionamentoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private estacionamentoPopupService: EstacionamentoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.estacionamentoPopupService
                .open(EstacionamentoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
