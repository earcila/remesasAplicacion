/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RemesasTestModule } from '../../../test.module';
import { RemesasDetailComponent } from 'app/entities/remesas/remesas-detail.component';
import { Remesas } from 'app/shared/model/remesas.model';

describe('Component Tests', () => {
    describe('Remesas Management Detail Component', () => {
        let comp: RemesasDetailComponent;
        let fixture: ComponentFixture<RemesasDetailComponent>;
        const route = ({ data: of({ remesas: new Remesas(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RemesasTestModule],
                declarations: [RemesasDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RemesasDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RemesasDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.remesas).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
