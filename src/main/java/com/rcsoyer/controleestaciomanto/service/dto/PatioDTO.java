package com.rcsoyer.controleestaciomanto.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Patio entity.
 */
public class PatioDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    private String descricao;

    @NotNull
    @Min(value = 1)
    @Max(value = 1000000)
    private Integer qtdVagas;

    @NotNull
    @DecimalMin(value = "1")
    @DecimalMax(value = "1000000")
    private Double taxaHora;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQtdVagas() {
        return qtdVagas;
    }

    public void setQtdVagas(Integer qtdVagas) {
        this.qtdVagas = qtdVagas;
    }

    public Double getTaxaHora() {
        return taxaHora;
    }

    public void setTaxaHora(Double taxaHora) {
        this.taxaHora = taxaHora;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PatioDTO patioDTO = (PatioDTO) o;
        if(patioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), patioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PatioDTO{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            ", qtdVagas=" + getQtdVagas() +
            ", taxaHora=" + getTaxaHora() +
            "}";
    }
}
