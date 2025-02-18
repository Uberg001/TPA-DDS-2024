package ar.edu.utn.frba.dds.Models.Domain.Actores;

import ar.edu.utn.frba.dds.Models.Domain.Documento.Documento;
import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import ar.edu.utn.frba.dds.Models.Domain.RecomendarCoordenadas.Area;
import ar.edu.utn.frba.dds.Models.Domain.Contacto.Contacto;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
public class Tecnico extends Persistente {

    @OneToOne(cascade = CascadeType.ALL)
    private Usuario usuario;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Embedded
    private Documento documento;

    @Column
    private String cuil;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_contacto")
    private Contacto contacto;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_area")
    private Area areaCobertura;
}