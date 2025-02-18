package ar.edu.utn.frba.dds.Models.Domain.Suscripcion;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaJuridica;
import ar.edu.utn.frba.dds.Models.Domain.Enviadores.Enviador;
import ar.edu.utn.frba.dds.Models.Domain.Enviadores.Mensaje;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Persistente;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Suscripcion")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Suscripcion extends Persistente {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_heladera", referencedColumnName = "id")
    private Heladera heladera;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "persona_suscripcion",
            joinColumns = @JoinColumn(name = "id_suscripcion"),
            inverseJoinColumns = @JoinColumn(name = "id_persona"))
    private List<PersonaHumana> personas = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "personaJ_suscripcion",
            joinColumns = @JoinColumn(name = "id_suscripcion"),
            inverseJoinColumns = @JoinColumn(name = "id_persona"))
    private List<PersonaJuridica> personasJuridicas = new ArrayList<>();

    @Transient
    private Enviador enviador;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_suscripcion")
    private List<Mensaje> mensajes = new ArrayList<>();

    //
    @Transient
    private String tipo;
    @Transient
    private String viandasMinimas;
    @Transient
    private String viandasMaximas;
    //

    public Suscripcion(Heladera heladera, List<PersonaHumana> personas, Enviador enviador) {
        this.heladera = heladera;
        this.personas = personas;
        this.enviador = enviador;
    }

    public void addHeladera(Heladera heladera) {
//        this.heladeras.add(heladera);
    }

    public void addPersona(PersonaHumana persona) {
        this.personas.add(persona);
    }

    public void addPersonaJuridica(PersonaJuridica personaJuridica) {
        this.personasJuridicas.add(personaJuridica);
    }

    public List<Heladera> getHeladeras() {
        List<Heladera> heladeras = new ArrayList<>();
        heladeras.add(this.heladera);
        return heladeras;
    }

    public List<PersonaHumana> getPersonas() {
        return this.personas;
    }

    public abstract void analizarEstadoHeladera(Heladera heladera);
}
