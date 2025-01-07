package ar.edu.utn.frba.dds.Models.Domain.Direccion;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.*;

@Data
@Embeddable
@AllArgsConstructor @NoArgsConstructor
public class Provincia {

    @Column(name = "nombre_provincia")
    private String nombre;
}