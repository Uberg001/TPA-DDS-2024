package ar.edu.utn.frba.dds.Models.Domain.Validador;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usuario {

    private String nombreUsuario;
    @Setter
    private String password;

    public Usuario(String nombreUsuario, String password) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
    }
}
