package ar.edu.utn.frba.dds.Models.Domain.Colaboraciones;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaVulnerable;
import javax.persistence.*;
import lombok.*;

@Data
@Entity
@DiscriminatorValue("RegistrarPersonasVulnerables")
@AllArgsConstructor
@NoArgsConstructor
public class RegistrarPersonasVulnerables extends ColaboracionPuntuable {
    @Column(name = "id_persona_vulnerable")
    private Long idPersonaVulnerable;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "personas_vulnerables_registradas",
            joinColumns = @JoinColumn(name = "id_colaboracion"),
            inverseJoinColumns = @JoinColumn(name = "id_persona_vulnerable")
    )
    private List<PersonaVulnerable> personasVulnerables = new ArrayList<>();

    @Transient
    private LocalDate fechaAlta;

    @Override
    public Float calcularPuntaje() {
        Float coeficiente = getCoeficiente(this.getClass().getSimpleName());
        return (float) (this.personasVulnerables.size() * coeficiente);
    }

    public RegistrarPersonasVulnerables(LocalDate fechaRealizacion) {
        this.fechaAlta = fechaRealizacion;
    }
}