package ar.edu.utn.frba.dds.Models.Domain.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
public class Modelo extends Persistente {

    @Column(name = "temperatura_maxima")
    private Float temperaturaMaxima;

    @Column(name = "temperatura_minima")
    private Float temperaturaMinima;

    @Column
    private String nombre;

    @Column
    private Integer capacidad;
}