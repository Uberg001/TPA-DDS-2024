package ar.edu.utn.frba.dds.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioDTOSalida {

    private String nombre;
    private String contrasenia;
}
