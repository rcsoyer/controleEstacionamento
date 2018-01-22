import { BaseEntity } from './../../shared';

export class Estacionamento implements BaseEntity {
    constructor(
        public id?: number,
        public entrada?: any,
        public saida?: any,
        public vaga?: number,
        public emUso?: boolean,
        public vlrPagamento?: number,
        public veiculoId?: number,
        public patioId?: number,
    ) {
        this.emUso = true;
    }
}
