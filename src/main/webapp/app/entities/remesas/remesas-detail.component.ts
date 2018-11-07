import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRemesas } from 'app/shared/model/remesas.model';

@Component({
    selector: 'jhi-remesas-detail',
    templateUrl: './remesas-detail.component.html'
})
export class RemesasDetailComponent implements OnInit {
    remesas: IRemesas;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ remesas }) => {
            this.remesas = remesas;
        });
    }

    previousState() {
        window.history.back();
    }
}
