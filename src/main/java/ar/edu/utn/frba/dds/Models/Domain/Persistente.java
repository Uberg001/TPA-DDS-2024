package ar.edu.utn.frba.dds.Models.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class Persistente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "activo")
    protected boolean activo = true;

    @Setter
    @Column(name = "fechaCreacion")
    protected LocalDateTime fechaCreacion = LocalDateTime.now();

    @Setter
    @Column(name = "fechaModificacion", nullable = true)
    protected LocalDateTime fechaModificacion = null;

    @Setter
    @Column(name = "fechaBaja", nullable = true)
    protected LocalDateTime fechaBaja = null;
}