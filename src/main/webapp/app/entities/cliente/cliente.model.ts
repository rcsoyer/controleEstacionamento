import { BaseEntity } from './../../shared';

export class Cliente implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public cpf?: string,
        public telefone?: string,
        public veiculos?: BaseEntity[],
    ) {
    }
}
