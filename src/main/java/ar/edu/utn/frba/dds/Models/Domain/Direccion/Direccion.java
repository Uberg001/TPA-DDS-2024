package ar.edu.utn.frba.dds.Models.Domain.Direccion;

import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import ar.edu.utn.frba.dds.Models.Domain.RecomendarCoordenadas.Coordenada;
import javax.persistence.*;

import lombok.*;

@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
public class Direccion extends Persistente {

    @Column(name = "nombre_calle")
    private String calle;

    @Column(name = "numero_calle")
    private int altura;

    @Embedded
    private Localidad localidad;

    @Embedded
    //@Convert(converter = CoordenadaAttributeConverter.class)
    private Coordenada coordenada;
}