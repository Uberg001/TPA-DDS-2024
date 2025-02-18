package ar.edu.utn.frba.dds.Models.Domain.Actores;

import ar.edu.utn.frba.dds.Models.DTOs.PersonaJuridicaDTOEntrada;
import ar.edu.utn.frba.dds.Models.Domain.Colaboraciones.ColaboracionPuntuable;
import ar.edu.utn.frba.dds.Models.Domain.Colaboraciones.HacerseCargoDeHeladeras;
import ar.edu.utn.frba.dds.Models.Domain.Contacto.Contacto;
import ar.edu.utn.frba.dds.Models.Domain.Direccion.Direccion;
import ar.edu.utn.frba.dds.Models.Domain.Documento.Documento;
import ar.edu.utn.frba.dds.Models.Domain.Documento.TipoDocumento;
import ar.edu.utn.frba.dds.Models.Domain.Formulario.Respuesta;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.AccesosHeladera.Tarjeta;
import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Rubro;
import ar.edu.utn.frba.dds.Models.Domain.Utils.TipoPJ;
import javax.persistence.*;
import lombok.*;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Puntaje;
import org.hibernate.annotations.Cascade;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
public class PersonaJuridica extends Persistente implements PersonaColaboradora {

    @Column
    private String razonSocial;

    @Enumerated(EnumType.STRING)
    private TipoPJ tipo;

    @OneToOne
    @JoinColumn(name = "id_rubro")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Rubro rubro;

    @OneToMany
    @JoinColumn(name = "id_persona_juridica")
    @Nullable
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Contacto> contacto;

    @OneToOne
    @JoinColumn(name = "id_direccion")
    private Direccion direccion;

    @Transient
    private List<ColaboracionPuntuable> colaboraciones;

    @OneToMany
    @JoinColumn(name = "id_persona_juridica")
    private List<Respuesta> opcionales;

    @Embedded
    private Puntaje puntaje;

    @OneToOne
    @JoinColumn(name = "id_tarjeta")
    private Tarjeta tarjeta;

//    @Embedded
//    private Documento documento;

    @OneToOne(cascade = CascadeType.ALL)
    private Usuario usuario;

    @Override
    public ArrayList<Contacto> obtenerContactos() {
        return (ArrayList<Contacto>) this.contacto;
    }

    // Constructor DTO -> Persona Juridica

    public PersonaJuridica(PersonaJuridicaDTOEntrada dto){
        this.usuario    = (new Usuario(dto.getUsuario()));
//        this.documento  = new Documento(TipoDocumento.valueOf(dto.getTipoDocumento()), dto.getNumeroDocumento());
        this.contacto   = dto.getContacto().stream().map(dtoContacto -> new Contacto(dtoContacto)).collect(Collectors.toList());
    }

    public void addContacto(Contacto contacto) {
        this.contacto.add(contacto);
    }

    public void calcularPuntaje() {
        float puntajeTotal = (float) colaboraciones.stream()
                .mapToDouble(ColaboracionPuntuable::calcularPuntaje)
                .sum();
    }

    private List<ColaboracionPuntuable> getColaboracionesSinHacerseCargoDeHeladeras() {
        return colaboraciones.stream()
                .filter(colaboracion -> !(colaboracion instanceof HacerseCargoDeHeladeras))
                .collect(Collectors.toList());
    }

    @Deprecated
    public int calcularHeladerasActivas(){
        return 0;
    }

    @Override
    public void setActivo(boolean activo) {
        super.setActivo(activo);
        this.setFechaBaja(LocalDateTime.now());
    }
}
