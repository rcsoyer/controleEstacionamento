import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Estacionamento } from './estacionamento.model';
import { EstacionamentoService } from './estacionamento.service';

@Injectable()
export class EstacionamentoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private estacionamentoService: EstacionamentoService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.estacionamentoService.find(id).subscribe((estacionamento) => {
                    estacionamento.entrada = this.datePipe
                        .transform(estacionamento.entrada, 'yyyy-MM-ddTHH:mm:ss');
                    estacionamento.saida = this.datePipe
                        .transform(estacionamento.saida, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.estacionamentoModalRef(component, estacionamento);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.estacionamentoModalRef(component, new Estacionamento());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    estacionamentoModalRef(component: Component, estacionamento: Estacionamento): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.estacionamento = estacionamento;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
