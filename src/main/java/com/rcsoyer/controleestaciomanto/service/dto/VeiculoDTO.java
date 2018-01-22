package com.rcsoyer.controleestaciomanto.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Veiculo entity.
 */
public class VeiculoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 7, max = 7)
    private String placa;

    @NotNull
    @Size(min = 1, max = 20)
    private String modelo;

    @NotNull
    @Size(min = 1, max = 20)
    private String cor;

    private Long clienteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VeiculoDTO veiculoDTO = (VeiculoDTO) o;
        if(veiculoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), veiculoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VeiculoDTO{" +
            "id=" + getId() +
            ", placa='" + getPlaca() + "'" +
            ", modelo='" + getModelo() + "'" +
            ", cor='" + getCor() + "'" +
            "}";
    }
}
