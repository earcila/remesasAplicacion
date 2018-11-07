import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IRemesas } from 'app/shared/model/remesas.model';
import { RemesasService } from './remesas.service';

@Component({
    selector: 'jhi-remesas-update',
    templateUrl: './remesas-update.component.html'
})
export class RemesasUpdateComponent implements OnInit {
    remesas: IRemesas;
    isSaving: boolean;
    fechaActDp: any;

    constructor(private remesasService: RemesasService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ remesas }) => {
            this.remesas = remesas;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.remesas.id !== undefined) {
            this.subscribeToSaveResponse(this.remesasService.update(this.remesas));
        } else {
            this.subscribeToSaveResponse(this.remesasService.create(this.remesas));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRemesas>>) {
        result.subscribe((res: HttpResponse<IRemesas>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
