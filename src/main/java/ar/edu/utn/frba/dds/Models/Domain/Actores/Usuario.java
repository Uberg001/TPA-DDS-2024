package ar.edu.utn.frba.dds.Models.Domain.Actores;

import ar.edu.utn.frba.dds.Models.DTOs.UsuarioDTOEntrada;
import ar.edu.utn.frba.dds.Models.Domain.Persistente;

import javax.persistence.*;
//import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
//vamos a tomar esto para el inicio de sesion de los 3 tipos de personas
public class Usuario extends Persistente {

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private Boolean admin;

    @Enumerated(EnumType.STRING)
    private TipoActor tipoActor;

    @Override
    public void setActivo(boolean activo) {
        super.setActivo(activo);
        this.setFechaBaja(LocalDateTime.now());
    }

    public Usuario(UsuarioDTOEntrada dto) {
        this.username = dto.getNombre();
        this.password = dto.getContrasenia();
    }

}
