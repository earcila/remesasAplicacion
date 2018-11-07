package io.github.jhipster.application.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Remesas.
 */
@Entity
@Table(name = "remesas")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Remesas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "empresa", nullable = false)
    private String empresa;

    @NotNull
    @Column(name = "tasa", nullable = false)
    private String tasa;

    @Column(name = "fecha_act")
    private LocalDate fechaAct;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public Remesas empresa(String empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getTasa() {
        return tasa;
    }

    public Remesas tasa(String tasa) {
        this.tasa = tasa;
        return this;
    }

    public void setTasa(String tasa) {
        this.tasa = tasa;
    }

    public LocalDate getFechaAct() {
        return fechaAct;
    }

    public Remesas fechaAct(LocalDate fechaAct) {
        this.fechaAct = fechaAct;
        return this;
    }

    public void setFechaAct(LocalDate fechaAct) {
        this.fechaAct = fechaAct;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Remesas remesas = (Remesas) o;
        if (remesas.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), remesas.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Remesas{" +
            "id=" + getId() +
            ", empresa='" + getEmpresa() + "'" +
            ", tasa='" + getTasa() + "'" +
            ", fechaAct='" + getFechaAct() + "'" +
            "}";
    }
}
