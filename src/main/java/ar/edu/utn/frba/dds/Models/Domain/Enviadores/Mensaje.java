package ar.edu.utn.frba.dds.Models.Domain.Enviadores;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaColaboradora;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Suscripcion.Suscripcion;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaJuridica;
import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import javax.persistence.*;
import lombok.*;


@Data
@Entity
@Table(name = "mensajes")
@AllArgsConstructor
@NoArgsConstructor
public class Mensaje extends Persistente {


    @Column(name = "subject")
    private String subject;

    @Column(name = "body")
    private String body;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_persona_juridica")
    private PersonaJuridica personaJuridica;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_persona_humana")
    private PersonaHumana personaHumana;
    //
    @Transient
    private Long idSuscripcion;
    //
    
    public Object getReceptor() {
        if (personaJuridica != null) {
            return personaJuridica;
        } else if (personaHumana != null) {
            return personaHumana;
        } else {
            return null;
        }
    }

}
