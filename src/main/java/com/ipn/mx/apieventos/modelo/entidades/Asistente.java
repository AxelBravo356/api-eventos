package com.ipn.mx.apieventos.modelo.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Entity
@Table(name = "Asistente")
public class Asistente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAsistente;

    @NotBlank(message = "email: Campo Obligatorio")
    @Size(min = 6, max = 20, message = "El email debe estar entre 4 y 10 caracteres")
    @Column(name = "email", length = 20, nullable = false)
    private String email;

    @NotBlank(message = "time: Campo Obligatorio")
    @Size(min = 5, max = 10, message = "La fecha debe estar entre 5 y 10 caracteres")
    @Column(name = "fechaRegistro", length = 10, nullable = false)
    private String fechaRegistro;

    @NotBlank(message = "El nombre del usuario es obligatorio")
    @Size(min = 4, max = 50, message = "El nombre debe estar entre 4 y 50 caracteres.")
    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @NotBlank(message = "El ap paterno del usuario es obligatorio")
    @Size(min = 4, max = 50, message = "El apellido paterno debe estar entre 4 y 50 caracteres.")
    @Column(name = "paterno", length = 50, nullable = false)
    private String paterno;

    public void setIdAsistente(Long idAsistente) {
        this.idAsistente = idAsistente;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    @NotBlank(message = "El ap materno del usuario es obligatorio")
    @Size(min = 4, max = 50, message = "El apellido materno debe estar entre 4 y 50 caracteres.")
    @Column(name = "materno", length = 50, nullable = false)
    private String materno;

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="idEvento")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Evento idEvento;

    public void setIdEvento(Evento idEvento) {
        this.idEvento = idEvento;
    }
}
