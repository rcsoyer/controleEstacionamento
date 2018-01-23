import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Estacionamento } from './estacionamento.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class EstacionamentoService {

    private resourceUrl = SERVER_API_URL + 'api/estacionamentos';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(estacionamento: Estacionamento): Observable<Estacionamento> {
        const copy = this.convert(estacionamento);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(estacionamento: Estacionamento): Observable<Estacionamento> {
        const copy = this.convert(estacionamento);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Estacionamento> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    getPgCalculado(id: number): Observable<Estacionamento> {
        return this.http.get(`${this.resourceUrl}/getPgCalculado/${id}`)
            .map((res: Response) => {
                const jsonResponse = res.json();
                return this.convertItemFromServer(jsonResponse);
            });
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Estacionamento.
     */
    private convertItemFromServer(json: any): Estacionamento {
        const entity: Estacionamento = Object.assign(new Estacionamento(), json);
        entity.entrada = this.dateUtils
            .convertDateTimeFromServer(json.entrada);
        entity.saida = this.dateUtils
            .convertDateTimeFromServer(json.saida);
        return entity;
    }

    /**
     * Convert a Estacionamento to a JSON which can be sent to the server.
     */
    private convert(estacionamento: Estacionamento): Estacionamento {
        const copy: Estacionamento = Object.assign({}, estacionamento);

        copy.entrada = this.dateUtils.toDate(estacionamento.entrada);

        copy.saida = this.dateUtils.toDate(estacionamento.saida);
        return copy;
    }
}
