package ar.edu.utn.frba.dds.Models.Domain.Colaboraciones;

import java.time.LocalDate;

import ar.edu.utn.frba.dds.Models.Domain.Config;
import javax.persistence.*;
import lombok.*;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Vianda;

@Data
@Entity
@DiscriminatorValue("DonarVianda")
@NoArgsConstructor
@AllArgsConstructor
public class DonarVianda extends ColaboracionPuntuable {

    @Column(name = "fecha_realizacion")
    private LocalDate fechaConcrecion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_vianda")
    @NonNull
    private Vianda vianda;
    public DonarVianda(LocalDate fechaRealizacion) {
        this.fechaConcrecion = fechaRealizacion;
    }
    @Override
    public Float calcularPuntaje() {
        Float coeficiente = getCoeficiente(this.getClass().getSimpleName());
        return coeficiente;
    }


}