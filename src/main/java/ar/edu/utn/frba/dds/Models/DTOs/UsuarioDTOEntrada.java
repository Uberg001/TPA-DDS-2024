package ar.edu.utn.frba.dds.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioDTOEntrada {

    private String nombre;
    private String contrasenia;
    private boolean esAdmin;
}
