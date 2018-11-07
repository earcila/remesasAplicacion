/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { RemesasTestModule } from '../../../test.module';
import { RemesasUpdateComponent } from 'app/entities/remesas/remesas-update.component';
import { RemesasService } from 'app/entities/remesas/remesas.service';
import { Remesas } from 'app/shared/model/remesas.model';

describe('Component Tests', () => {
    describe('Remesas Management Update Component', () => {
        let comp: RemesasUpdateComponent;
        let fixture: ComponentFixture<RemesasUpdateComponent>;
        let service: RemesasService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RemesasTestModule],
                declarations: [RemesasUpdateComponent]
            })
                .overrideTemplate(RemesasUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RemesasUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RemesasService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Remesas(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.remesas = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Remesas();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.remesas = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
