package com.rcsoyer.controleestaciomanto.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Estacionamento.
 */
@Entity
@Table(name = "estacionamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Estacionamento implements Serializable {

  private static final long serialVersionUID = -2252329273360682961L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  private Long id;

  @NotNull
  @Column(name = "entrada", nullable = false)
  private ZonedDateTime entrada;

  @Column(name = "saida")
  private ZonedDateTime saida;

  @NotNull
  @Min(value = 1)
  @Max(value = 1000000)
  @Column(name = "vaga", nullable = false)
  private Integer vaga;

  @NotNull
  @Column(name = "em_uso", nullable = false)
  private Boolean emUso;

  @Column(name = "vlr_pagamento")
  private Double vlrPagamento;

  @OneToOne(optional = false)
  @NotNull
  @JoinColumn(unique = true)
  private Veiculo veiculo;

  @ManyToOne(optional = false)
  @NotNull
  private Patio patio;

  // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ZonedDateTime getEntrada() {
    return entrada;
  }

  public Estacionamento entrada(ZonedDateTime entrada) {
    this.entrada = entrada;
    return this;
  }

  public void setEntrada(ZonedDateTime entrada) {
    this.entrada = entrada;
  }

  public ZonedDateTime getSaida() {
    return saida;
  }

  public Estacionamento saida(ZonedDateTime saida) {
    this.saida = saida;
    return this;
  }

  public void setSaida(ZonedDateTime saida) {
    this.saida = saida;
  }

  public Integer getVaga() {
    return vaga;
  }

  public Estacionamento vaga(Integer vaga) {
    this.vaga = vaga;
    return this;
  }

  public void setVaga(Integer vaga) {
    this.vaga = vaga;
  }

  public Boolean isEmUso() {
    return emUso;
  }

  public Estacionamento emUso(Boolean emUso) {
    this.emUso = emUso;
    return this;
  }

  public void setEmUso(Boolean emUso) {
    this.emUso = emUso;
  }

  public Double getVlrPagamento() {
    return vlrPagamento;
  }

  public Estacionamento vlrPagamento(Double vlrPagamento) {
    this.vlrPagamento = vlrPagamento;
    return this;
  }

  public void setVlrPagamento(Double vlrPagamento) {
    this.vlrPagamento = vlrPagamento;
  }

  public Veiculo getVeiculo() {
    return veiculo;
  }

  public Estacionamento veiculo(Veiculo veiculo) {
    this.veiculo = veiculo;
    return this;
  }

  public void setVeiculo(Veiculo veiculo) {
    this.veiculo = veiculo;
  }

  public Patio getPatio() {
    return patio;
  }

  public Estacionamento patio(Patio patio) {
    this.patio = patio;
    return this;
  }

  public void setPatio(Patio patio) {
    this.patio = patio;
  }
  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not
  // remove

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Estacionamento estacionamento = (Estacionamento) o;
    if (estacionamento.getId() == null || getId() == null) {
      return false;
    }
    return Objects.equals(getId(), estacionamento.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getId());
  }

  @Override
  public String toString() {
    return "Estacionamento{" + "id=" + getId() + ", entrada='" + getEntrada() + "'" + ", saida='"
        + getSaida() + "'" + ", vaga=" + getVaga() + ", emUso='" + isEmUso() + "'"
        + ", vlrPagamento=" + getVlrPagamento() + "}";
  }
}
