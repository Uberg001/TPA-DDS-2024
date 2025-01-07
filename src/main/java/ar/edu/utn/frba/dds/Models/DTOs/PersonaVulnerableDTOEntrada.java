package ar.edu.utn.frba.dds.Models.DTOs;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PersonaVulnerableDTOEntrada {
    private String nombre;
    private UsuarioDTOEntrada usuario;
    private String email;

}
