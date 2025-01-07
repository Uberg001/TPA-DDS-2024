package ar.edu.utn.frba.dds.Models.Domain.Direccion;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import lombok.*;

@Data
@Embeddable
@AllArgsConstructor @NoArgsConstructor
public class Localidad {

    @Column(name = "nombre_localidad")
    private String nombre;

    @Embedded
    private Provincia provincia;
}