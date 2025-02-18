package ar.edu.utn.frba.dds.Models.Domain.Heladera.Sensor;
import java.time.LocalDateTime;

import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Sensor.Entrada.EntradaSensor;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Estado;
import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Registro extends Persistente {

    @OneToOne
    @JoinColumn(name = "id_entrada_sensor")
    private EntradaSensor entradaSensor;

    @Column
    private LocalDateTime fecha;

    @Enumerated
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "id_heladera")
    private Heladera heladera;
}