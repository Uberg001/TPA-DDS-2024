package ar.edu.utn.frba.dds.Models.Domain.Documento;
import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import javax.persistence.*;
import lombok.*;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Documento{

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento")
    private TipoDocumento tipo;

    @Column(name = "numero_documento")
    private String numero;
}