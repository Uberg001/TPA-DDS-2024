package ar.edu.utn.frba.dds.Models.Domain.Colaboraciones;

import java.time.LocalDate;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaJuridica;
import ar.edu.utn.frba.dds.Models.Domain.Utils.CalculadorDeHeladerasActivas;
import javax.persistence.*;
import lombok.*;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;

@Data
@Entity
@DiscriminatorValue("HacerseCargoDeHeladeras")
@AllArgsConstructor
@NoArgsConstructor
public class HacerseCargoDeHeladeras extends ColaboracionPuntuable {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_heladera")
    private Heladera heladera;

    @Column(name = "cantidad_heladeras")
    private Integer cantidadHeladeras;

    @Column(name = "fecha_realizacion")
    private LocalDate fechaRealizacion;
    

    @Column(name = "id_Usuario_HCH")
    private Long idUsuario;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_persona_juridica")
    //@Nullable
    private PersonaJuridica personaJuridica;

    @Transient
    private CalculadorDeHeladerasActivas heladerasActivas;
    public int sumatoriaTiempoActividad(Heladera heladera) {
        return heladera.tiempoActividad();
    }


    //Refactor: Inyecto cantidad de heladeras y la sumatoria del tiempo de actividad de cada una
    public Float calcularPuntajeBeta(Integer cantHeladeras, Integer sumaMesesActividad) {
        Float coeficiente = getCoeficiente(this.getClass().getSimpleName());
        return coeficiente * sumaMesesActividad * sumaMesesActividad;
    }


    @Override
    public Float calcularPuntaje() {
        Float coeficiente = getCoeficiente(this.getClass().getSimpleName());
        return coeficiente * sumatoriaTiempoActividad(heladera) * cantidadHeladeras;
    }
}