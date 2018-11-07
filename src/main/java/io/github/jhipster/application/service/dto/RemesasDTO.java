package io.github.jhipster.application.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Remesas entity.
 */
public class RemesasDTO implements Serializable {

    private Long id;

    @NotNull
    private String empresa;

    @NotNull
    private String tasa;

    private LocalDate fechaAct;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getTasa() {
        return tasa;
    }

    public void setTasa(String tasa) {
        this.tasa = tasa;
    }

    public LocalDate getFechaAct() {
        return fechaAct;
    }

    public void setFechaAct(LocalDate fechaAct) {
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

        RemesasDTO remesasDTO = (RemesasDTO) o;
        if (remesasDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), remesasDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RemesasDTO{" +
            "id=" + getId() +
            ", empresa='" + getEmpresa() + "'" +
            ", tasa='" + getTasa() + "'" +
            ", fechaAct='" + getFechaAct() + "'" +
            "}";
    }
}
