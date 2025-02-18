package ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaColaboradora;
import ar.edu.utn.frba.dds.Models.Domain.Actores.Usuario;
import ar.edu.utn.frba.dds.Models.Domain.Colaboraciones.Colaboracion;
import ar.edu.utn.frba.dds.Models.Domain.Colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.Models.Domain.Contacto.Contacto;
import ar.edu.utn.frba.dds.Models.Domain.Direccion.Direccion;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.AccesosHeladera.Tarjeta;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import ar.edu.utn.frba.dds.Models.Domain.Suscripcion.Suscripcion;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Puntaje;
import javax.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class PersonaHumana extends Persistente implements PersonaColaboradora {

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_direccion")
    private Direccion direccion;

    @Embedded
    private Puntaje puntaje = new Puntaje((float) 0, new ArrayList<>());

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tarjeta")
    private Tarjeta tarjeta;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Contacto> contacto = new ArrayList<>();

    @Transient
    private List<ColaboracionPuntuable> colaboraciones = new ArrayList<>();

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
    private List<Accion> acciones = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "persona_suscripcion",
            joinColumns = @JoinColumn(name = "id_persona"),
            inverseJoinColumns = @JoinColumn(name = "id_suscripcion"))
    private List<Suscripcion> suscripciones = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Usuario usuario;

    public PersonaHumana(String nombre, Contacto contacto) {
        this.nombre = nombre;
        this.contacto.add(contacto);
        this.puntaje = new Puntaje();
    }

    public PersonaHumana() {

    }

    public void calcularPuntaje() {
        this.colaboraciones.forEach(colaboracion -> this.puntaje.sumarPuntaje(colaboracion.calcularPuntaje()));
    }

    public ArrayList<Contacto> obtenerContactos() {
        return (ArrayList<Contacto>) this.contacto;
    }

    public void addContacto(Contacto nuevoContacto) {
        this.contacto.add(nuevoContacto);
    }

    public boolean esLaMisma(PersonaHumana otraPersona) {
        return this.nombre.equals(otraPersona.nombre) &&
                this.apellido.equals(otraPersona.apellido) &&
                this.contacto.equals(otraPersona.getContacto());
    }

    public void addColaboracion(ColaboracionPuntuable nuevoColaboracionPuntuable) {
        this.colaboraciones.add(nuevoColaboracionPuntuable);
    }

    public void suscribirse(Suscripcion suscripcion) {
        this.suscripciones.add(suscripcion);
    }

    public void registrarAccion(TipoAccion tipoAccion, LocalDateTime inicio,
                                LocalDateTime fin, Boolean validez, Heladera heladera) {
        Accion accion = new Accion(tipoAccion, inicio, fin, validez, heladera);
        this.acciones.add(accion);
    }
    @Override
    public void setActivo(boolean activo) {
        super.setActivo(activo);
        this.setFechaBaja(LocalDateTime.now());
    }
}