package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Models.Domain.Actores.Usuario;
import ar.edu.utn.frba.dds.Models.Repositories.UsuarioRepository;

public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario buscarPorCredenciales(String nombre, String contrasenia) {
        return usuarioRepository.buscarPorCredenciales(nombre, contrasenia);
    }

    @Override
    public Usuario buscarPorId(Long id) {
        Usuario usuario = (Usuario) usuarioRepository.buscar(id);
        return usuario;
    }


}
