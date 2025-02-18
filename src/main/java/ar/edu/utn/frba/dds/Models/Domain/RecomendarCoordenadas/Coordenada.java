package ar.edu.utn.frba.dds.Models.Domain.RecomendarCoordenadas;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.*;
import java.math.*;


@Data
@Embeddable
@AllArgsConstructor @NoArgsConstructor
public class Coordenada {

    @Column
    private BigDecimal latitud;

    @Column
    private BigDecimal longitud;
}