package ar.edu.utn.frba.dds.Models.Domain.Colaboraciones;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaJuridica;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Vianda;
//import javax.annotation.Nullable;
import javax.persistence.*;
//import javax.validation.constraints.Null;
import lombok.*;
import ar.edu.utn.frba.dds.Models.Domain.Utils.MotivoDistribucion;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("DistribuirViandas")
public class DistribuirViandas extends ColaboracionPuntuable {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_heladera_origen")
    private Heladera heladeraOrigen;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_heladera_destino")
    private Heladera heladeraDestino;

    @Enumerated(EnumType.STRING)
    private MotivoDistribucion motivo;

    @Column(name = "fecha_realizacion")
    private LocalDate fechaRealizacion;

    @Column(name = "cantidad")
    private Integer cantidad;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "viandas_distribuidas",
        joinColumns = @JoinColumn(name = "id_colaboracion"),
        inverseJoinColumns = @JoinColumn(name = "id_vianda")
    )
    private List<Vianda> viandas = new ArrayList<>();

    //
    @Transient
    private Vianda vianda;
    //
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_persona_humana")
    //@Nullable TODO: VER QUE HACER CON ESTO
    private PersonaHumana personaHumana;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_persona_juridica")
    //@Nullable
    private PersonaJuridica personaJuridica;


    public DistribuirViandas(Heladera heladeraOrigen, Heladera heladeraDestino, MotivoDistribucion motivo, LocalDate fechaRealizacion, Integer cantidad, List<Vianda> viandas, PersonaHumana personaHumana, PersonaJuridica personaJuridica) {
        this.heladeraOrigen = heladeraOrigen;
        this.heladeraDestino = heladeraDestino;
        this.motivo = motivo;
        this.fechaRealizacion = fechaRealizacion;
        this.cantidad = cantidad;
        this.viandas = viandas;
        this.personaHumana = personaHumana;
        this.personaJuridica = personaJuridica;
    }



    public DistribuirViandas(LocalDate fechaRealizacion, Integer cantidad) {
        this.fechaRealizacion   = fechaRealizacion;
        this.cantidad           = cantidad;
    }

    @Override
    public Float calcularPuntaje() {
        Float coeficiente = getCoeficiente(this.getClass().getSimpleName());
        return (float) this.cantidad * coeficiente;
    }

}