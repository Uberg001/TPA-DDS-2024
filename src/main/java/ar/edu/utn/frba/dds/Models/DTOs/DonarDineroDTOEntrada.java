package ar.edu.utn.frba.dds.Models.DTOs;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaJuridica;
import ar.edu.utn.frba.dds.Models.Domain.Actores.TipoActor;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Frecuencia;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor @Data
public class DonarDineroDTOEntrada {
    private LocalDate fechaDeDonacion;
    private Integer monto;
    private Frecuencia frecuencia;
    private Long idColaborador;
    private TipoActor tipoActor;
    //private PersonaHumana ph;
    //private PersonaJuridica pj;
}
