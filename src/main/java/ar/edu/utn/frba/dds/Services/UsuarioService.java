package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Models.Domain.Actores.Usuario;

public interface UsuarioService {

    public Usuario buscarPorCredenciales(String nombre, String contrasenia);

    public Usuario buscarPorId(Long id);
}
