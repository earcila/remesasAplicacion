import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Remesas } from 'app/shared/model/remesas.model';
import { RemesasService } from './remesas.service';
import { RemesasComponent } from './remesas.component';
import { RemesasDetailComponent } from './remesas-detail.component';
import { RemesasUpdateComponent } from './remesas-update.component';
import { RemesasDeletePopupComponent } from './remesas-delete-dialog.component';
import { IRemesas } from 'app/shared/model/remesas.model';

@Injectable({ providedIn: 'root' })
export class RemesasResolve implements Resolve<IRemesas> {
    constructor(private service: RemesasService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Remesas> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Remesas>) => response.ok),
                map((remesas: HttpResponse<Remesas>) => remesas.body)
            );
        }
        return of(new Remesas());
    }
}

export const remesasRoute: Routes = [
    {
        path: 'remesas',
        component: RemesasComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'remesasApp.remesas.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'remesas/:id/view',
        component: RemesasDetailComponent,
        resolve: {
            remesas: RemesasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'remesasApp.remesas.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'remesas/new',
        component: RemesasUpdateComponent,
        resolve: {
            remesas: RemesasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'remesasApp.remesas.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'remesas/:id/edit',
        component: RemesasUpdateComponent,
        resolve: {
            remesas: RemesasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'remesasApp.remesas.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const remesasPopupRoute: Routes = [
    {
        path: 'remesas/:id/delete',
        component: RemesasDeletePopupComponent,
        resolve: {
            remesas: RemesasResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'remesasApp.remesas.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
