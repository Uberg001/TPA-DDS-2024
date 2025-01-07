package ar.edu.utn.frba.dds.Models.Domain.Formulario;

import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Embeddable
public class CampoDinamico{

    @Column(name = "nombre_campo")
    private String nombre;

}