package com.ipn.mx.apieventos.modelo.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "evento")
public class Evento implements Serializable {

    private static final long serialVersionUID= 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvento;

    @NotBlank(message = "La descripción del evento es obligatorio")
    @Size(min=5, max=100, message = "La descripción debe estar entre 5 y 100 caracteres" )
    @Column(name = "descripcionEvento", length = 100, nullable = false)
    private String descripcionEvento;

    @NotBlank(message="La fecha del evento es obligatorio")
    @Size(min=5, max=10, message = "La fecha del evento debe estar entre 5 y 10 caracteres")
    @Column(name = "fechaCreacion", length = 10, nullable = false)
    private String fechaCreacion;

    @NotBlank(message="El nombre del evento es obligatorio")
    @Size(min=5, max=15, message ="El nombre del evento debe estar entre 5 y 15 caracteres")
    @Column(name = "nombreEvento", length = 15, nullable = false )
    private String nombreEvento;

    @OneToMany(mappedBy = "idEvento", cascade = CascadeType.ALL)
    private Set<Asistente> asistentes = new HashSet<>();

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public void setAsistentes(Set<Asistente> asistentes) {
        this.asistentes = asistentes;
        for (Asistente asistente: asistentes) {
            asistente.setIdEvento(this);
        }
    }
}
