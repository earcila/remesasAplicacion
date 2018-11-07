package io.github.jhipster.application.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the Remesas entity. This class is used in RemesasResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /remesas?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RemesasCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter empresa;

    private StringFilter tasa;

    private LocalDateFilter fechaAct;

    public RemesasCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEmpresa() {
        return empresa;
    }

    public void setEmpresa(StringFilter empresa) {
        this.empresa = empresa;
    }

    public StringFilter getTasa() {
        return tasa;
    }

    public void setTasa(StringFilter tasa) {
        this.tasa = tasa;
    }

    public LocalDateFilter getFechaAct() {
        return fechaAct;
    }

    public void setFechaAct(LocalDateFilter fechaAct) {
        this.fechaAct = fechaAct;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RemesasCriteria that = (RemesasCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(empresa, that.empresa) &&
            Objects.equals(tasa, that.tasa) &&
            Objects.equals(fechaAct, that.fechaAct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        empresa,
        tasa,
        fechaAct
        );
    }

    @Override
    public String toString() {
        return "RemesasCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (empresa != null ? "empresa=" + empresa + ", " : "") +
                (tasa != null ? "tasa=" + tasa + ", " : "") +
                (fechaAct != null ? "fechaAct=" + fechaAct + ", " : "") +
            "}";
    }

}
