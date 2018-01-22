import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Veiculo } from './veiculo.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class VeiculoService {

    private resourceUrl =  SERVER_API_URL + 'api/veiculos';

    constructor(private http: Http) { }

    create(veiculo: Veiculo): Observable<Veiculo> {
        const copy = this.convert(veiculo);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(veiculo: Veiculo): Observable<Veiculo> {
        const copy = this.convert(veiculo);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Veiculo> {
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

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Veiculo.
     */
    private convertItemFromServer(json: any): Veiculo {
        const entity: Veiculo = Object.assign(new Veiculo(), json);
        return entity;
    }

    /**
     * Convert a Veiculo to a JSON which can be sent to the server.
     */
    private convert(veiculo: Veiculo): Veiculo {
        const copy: Veiculo = Object.assign({}, veiculo);
        return copy;
    }
}
