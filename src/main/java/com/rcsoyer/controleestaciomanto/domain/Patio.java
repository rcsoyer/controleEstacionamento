package com.rcsoyer.controleestaciomanto.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Patio.
 */
@Entity
@Table(name = "patio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Patio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "descricao", length = 30, nullable = false)
    private String descricao;

    @NotNull
    @Min(value = 1)
    @Max(value = 1000000)
    @Column(name = "qtd_vagas", nullable = false)
    private Integer qtdVagas;

    @NotNull
    @DecimalMin(value = "1")
    @DecimalMax(value = "1000000")
    @Column(name = "taxa_hora", nullable = false)
    private Double taxaHora;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Patio descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQtdVagas() {
        return qtdVagas;
    }

    public Patio qtdVagas(Integer qtdVagas) {
        this.qtdVagas = qtdVagas;
        return this;
    }

    public void setQtdVagas(Integer qtdVagas) {
        this.qtdVagas = qtdVagas;
    }

    public Double getTaxaHora() {
        return taxaHora;
    }

    public Patio taxaHora(Double taxaHora) {
        this.taxaHora = taxaHora;
        return this;
    }

    public void setTaxaHora(Double taxaHora) {
        this.taxaHora = taxaHora;
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
        Patio patio = (Patio) o;
        if (patio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), patio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Patio{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", qtdVagas=" + getQtdVagas() +
            ", taxaHora=" + getTaxaHora() +
            "}";
    }
}
