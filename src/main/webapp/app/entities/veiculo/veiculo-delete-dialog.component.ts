import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Veiculo } from './veiculo.model';
import { VeiculoPopupService } from './veiculo-popup.service';
import { VeiculoService } from './veiculo.service';

@Component({
    selector: 'jhi-veiculo-delete-dialog',
    templateUrl: './veiculo-delete-dialog.component.html'
})
export class VeiculoDeleteDialogComponent {

    veiculo: Veiculo;

    constructor(
        private veiculoService: VeiculoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.veiculoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'veiculoListModification',
                content: 'Deleted an veiculo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-veiculo-delete-popup',
    template: ''
})
export class VeiculoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private veiculoPopupService: VeiculoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.veiculoPopupService
                .open(VeiculoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
