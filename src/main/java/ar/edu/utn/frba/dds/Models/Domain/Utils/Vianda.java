package ar.edu.utn.frba.dds.Models.Domain.Utils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Formulario.Respuesta;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.Cascade;

@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
public class Vianda extends Persistente {

    @Column
    private String comida;

    @Column(name = "fecha_caducidad")
    private LocalDate fechaCaducidad;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_colaborador")
    private PersonaHumana colaborador;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "id_heladera")
    private Heladera heladera;

    @Column
    private Boolean entregado;

    @Transient
    private List<Respuesta> respuestas = new ArrayList<>();
}