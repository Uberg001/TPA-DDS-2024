package ar.edu.utn.frba.dds.Models.Domain.Utils;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaJuridica;
import ar.edu.utn.frba.dds.Models.Domain.Persistente;
//import javax.annotation.Nullable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
public class Canje extends Persistente {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_oferta")
    private Oferta ofertaARealizar;

    @Column
    private LocalDate fecha;

    @Column(name = "puntos_canjeados")
    private BigDecimal puntosCanjeados;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_persona_juridica")
    @Nullable
    private PersonaJuridica personaJuridica;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_persona_humana")
    private PersonaHumana personaHumana;
}
