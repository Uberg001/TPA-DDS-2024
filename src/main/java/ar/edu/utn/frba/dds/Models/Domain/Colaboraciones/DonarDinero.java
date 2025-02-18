package ar.edu.utn.frba.dds.Models.Domain.Colaboraciones;

import java.time.LocalDate;

import javax.persistence.*;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaJuridica;
import lombok.*;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Frecuencia;
import org.jetbrains.annotations.Nullable;

@Data
@Entity
@DiscriminatorValue("DonarDinero")
@AllArgsConstructor
@NoArgsConstructor
public class DonarDinero extends ColaboracionPuntuable {

    @Column(name = "fecha_realizacion")
    private LocalDate fechaConcrecion;

    @Column(name = "monto")
    private Integer monto;

    @Enumerated(EnumType.STRING)
    private Frecuencia frecuencia;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_persona_humana")
    @Nullable
    private PersonaHumana personaHumana;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_persona_juridica")
    @Nullable
    private PersonaJuridica personaJuridica;

    

    @Override
    public Float calcularPuntaje() {
        Float coeficiente = getCoeficiente(this.getClass().getSimpleName());
        return (float) ((float) monto * coeficiente);
    }

    public DonarDinero(LocalDate fechaRealizacion, Integer monto) {
        this.fechaConcrecion    = fechaRealizacion;
        this.monto              = monto;
    }
}