import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRemesas } from 'app/shared/model/remesas.model';

type EntityResponseType = HttpResponse<IRemesas>;
type EntityArrayResponseType = HttpResponse<IRemesas[]>;

@Injectable({ providedIn: 'root' })
export class RemesasService {
    public resourceUrl = SERVER_API_URL + 'api/remesas';

    constructor(private http: HttpClient) {}

    create(remesas: IRemesas): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(remesas);
        return this.http
            .post<IRemesas>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(remesas: IRemesas): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(remesas);
        return this.http
            .put<IRemesas>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRemesas>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRemesas[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(remesas: IRemesas): IRemesas {
        const copy: IRemesas = Object.assign({}, remesas, {
            fechaAct: remesas.fechaAct != null && remesas.fechaAct.isValid() ? remesas.fechaAct.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.fechaAct = res.body.fechaAct != null ? moment(res.body.fechaAct) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((remesas: IRemesas) => {
                remesas.fechaAct = remesas.fechaAct != null ? moment(remesas.fechaAct) : null;
            });
        }
        return res;
    }
}
