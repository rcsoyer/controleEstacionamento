import { BaseEntity } from './../../shared';

export class Patio implements BaseEntity {
    constructor(
        public id?: number,
        public descricao?: string,
        public qtdVagas?: number,
        public taxaHora?: number,
    ) {
    }
}
