package com.rcsoyer.controleestaciomanto.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Estacionamento entity.
 */
public class EstacionamentoDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime entrada;

    @NotNull
    private ZonedDateTime saida;

    @NotNull
    @Min(value = 1)
    @Max(value = 1000000)
    private Integer vaga;

    @NotNull
    private Boolean emUso;

    private Long veiculoId;

    private Long patioId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getEntrada() {
        return entrada;
    }

    public void setEntrada(ZonedDateTime entrada) {
        this.entrada = entrada;
    }

    public ZonedDateTime getSaida() {
        return saida;
    }

    public void setSaida(ZonedDateTime saida) {
        this.saida = saida;
    }

    public Integer getVaga() {
        return vaga;
    }

    public void setVaga(Integer vaga) {
        this.vaga = vaga;
    }

    public Boolean isEmUso() {
        return emUso;
    }

    public void setEmUso(Boolean emUso) {
        this.emUso = emUso;
    }

    public Long getVeiculoId() {
        return veiculoId;
    }

    public void setVeiculoId(Long veiculoId) {
        this.veiculoId = veiculoId;
    }

    public Long getPatioId() {
        return patioId;
    }

    public void setPatioId(Long patioId) {
        this.patioId = patioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EstacionamentoDTO estacionamentoDTO = (EstacionamentoDTO) o;
        if(estacionamentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), estacionamentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EstacionamentoDTO{" +
            "id=" + getId() +
            ", entrada='" + getEntrada() + "'" +
            ", saida='" + getSaida() + "'" +
            ", vaga=" + getVaga() +
            ", emUso='" + isEmUso() + "'" +
            "}";
    }
}
