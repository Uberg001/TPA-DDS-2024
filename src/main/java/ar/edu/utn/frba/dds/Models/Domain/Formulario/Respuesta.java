package ar.edu.utn.frba.dds.Models.Domain.Formulario;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaJuridica;
import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import javax.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "respuesta_persona")
@NoArgsConstructor
@AllArgsConstructor
public class Respuesta extends Persistente {

    @Column
    private String respuesta;

    @Embedded
    private CampoDinamico campo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_persona_humana")
    //@Nullable TODO: VER QUE HACER CON ESTO
    private PersonaHumana personaHumana;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_persona_juridica")
    //@Nullable
    private PersonaJuridica personaJuridica;
}