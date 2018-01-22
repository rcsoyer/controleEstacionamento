import { BaseEntity } from './../../shared';

export class Veiculo implements BaseEntity {
    constructor(
        public id?: number,
        public placa?: string,
        public modelo?: string,
        public cor?: string,
        public clienteId?: number,
    ) {
    }
}
