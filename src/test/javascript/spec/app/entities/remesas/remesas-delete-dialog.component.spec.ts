/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { RemesasTestModule } from '../../../test.module';
import { RemesasDeleteDialogComponent } from 'app/entities/remesas/remesas-delete-dialog.component';
import { RemesasService } from 'app/entities/remesas/remesas.service';

describe('Component Tests', () => {
    describe('Remesas Management Delete Component', () => {
        let comp: RemesasDeleteDialogComponent;
        let fixture: ComponentFixture<RemesasDeleteDialogComponent>;
        let service: RemesasService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RemesasTestModule],
                declarations: [RemesasDeleteDialogComponent]
            })
                .overrideTemplate(RemesasDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RemesasDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RemesasService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
