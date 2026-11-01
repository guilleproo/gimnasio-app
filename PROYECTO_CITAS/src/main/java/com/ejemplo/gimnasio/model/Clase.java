package com.ejemplo.gimnasio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "clases")
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El tipo de clase es obligatorio")
    private String tipo; // Solo admitira: "Crossfit", "Yoga", "Zumba"

    @ManyToOne
    @JoinColumn(name = "entrenador_id", nullable = false)
    @NotNull(message = "Debe asignar un entrenador")
    private Usuario entrenador;

    @NotNull(message = "La fecha y hora de inicio son obligatorias")
    @Future(message = "La fecha de la clase debe ser en el futuro")
    private LocalDateTime fechaHoraInicio;

    private LocalDateTime fechaHoraFin; // Se calcula automaticamente (+1 hora)

    private int cupoMaximo = 5; // Ccupo maximo 5 personas

    // Constructor vacío
    public Clase() {}

    // Constructor parametros
    public Clase(String tipo, Usuario entrenador, LocalDateTime fechaHoraInicio) {
        this.tipo = tipo;
        this.entrenador = entrenador;
        this.fechaHoraInicio = fechaHoraInicio;
        calcularHoraFin();
    }

    // metodo para calcular la hora de finalizacion
    @PrePersist
    @PreUpdate
    public void calcularHoraFin() {
        if (this.fechaHoraInicio != null) {
            this.fechaHoraFin = this.fechaHoraInicio.plusHours(1);
        }
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Usuario getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Usuario entrenador) {
        this.entrenador = entrenador;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
        calcularHoraFin(); // Asegura el cálculo si se modifica
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    // No hay setter publico para fechaHoraFin, ya que se calcula automaticamente

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }
}