package ar.edu.utn.frba.dds.Models.Domain.Actores;

import ar.edu.utn.frba.dds.Models.Domain.Direccion.Direccion;
import ar.edu.utn.frba.dds.Models.Domain.Documento.Documento;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.AccesosHeladera.Tarjeta;
import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import javax.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class PersonaVulnerable extends Persistente implements Persona {

    @Column
    private String nombre;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_alta")
    private LocalDate fechaAlta;

    @Column(name = "en_condicion_calle")
    private Boolean enCondicionCalle;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_domicilio")
    private Direccion domicilio;

    @Embedded
    private Documento documento;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tarjeta")
    private Tarjeta tarjeta;

    //@OneToMany(cascade = CascadeType.ALL)
    //@JoinColumn(referencedColumnName = "id", name = "id_tutor")
    @Transient
    private List<PersonaVulnerable> personasACargo = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Usuario usuario;
    
    @Column(name = "id_tutor")
    private Long idTutor;

    public Boolean poseeMenoresACargo() {
        return !this.getMenoresACargo().isEmpty();
    }

    public Integer cantidadMenoresACargo() {
        return this.getMenoresACargo().size();
    }

    //agregar funciones al diagrama
    public List<PersonaVulnerable> getMenoresACargo() {
        return this.personasACargo.stream().filter(PersonaVulnerable::soyMenor).toList();
    }
    public void agregarPersonaACargo(PersonaVulnerable persona) {
        this.personasACargo.add(persona);
    }
    public Boolean soyMenor() {
        return this.edad() < 18;
    }

    public int edad() {
        LocalDate fechaActual = LocalDate.now();
        return Period.between(this.fechaNacimiento, fechaActual).getYears();
    }

    @Override
    public void setActivo(boolean activo) {
        super.setActivo(activo);
        this.setFechaBaja(LocalDateTime.now());
    }

}