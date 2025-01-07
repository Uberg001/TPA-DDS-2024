package ar.edu.utn.frba.dds.Models.Domain.Direccion;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Embeddable
public class Partido {

    private String nombre;

    private Provincia provincia;
}