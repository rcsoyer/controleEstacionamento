package com.rcsoyer.controleestaciomanto.service.dto;


import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO for the Patio entity.
 */
public class PatioDTO implements Serializable {

  private static final long serialVersionUID = -3659047438589187455L;

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

  private Integer vagasDisponiveis;

  private Integer vagasOcupadas;

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

  public Integer getVagasDisponiveis() {
    return vagasDisponiveis;
  }

  public void setVagasDisponiveis(Integer vagasDisponiveis) {
    this.vagasDisponiveis = vagasDisponiveis;
  }

  public Integer getVagasOcupadas() {
    return vagasOcupadas;
  }

  public void setVagasOcupadas(Integer vagasOcupadas) {
    this.vagasOcupadas = vagasOcupadas;
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
    if (patioDTO.getId() == null || getId() == null) {
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
    return "PatioDTO{" + "id=" + getId() + ", descricao='" + getDescricao() + "'" + ", qtdVagas="
        + getQtdVagas() + ", taxaHora=" + getTaxaHora() + "}";
  }
}
