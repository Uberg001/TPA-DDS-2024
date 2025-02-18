package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Models.DTOs.SessionDTO;
import ar.edu.utn.frba.dds.Models.DTOs.UsuarioDTOSalida;
import ar.edu.utn.frba.dds.Models.Domain.Actores.*;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Repositories.*;

public class SessionServiceImpl implements SessionService {

    private UsuarioRepository usuarioRepository;

    private PersonaVulnerableRepository personaVulnerableRepository;
    private PersonaJuridicaRepository personaJuridicaRepository;
    private PersonaHumanaRepository personaHumanaRepository;
    private TecnicoRepository tecnicoRepository;

    public SessionServiceImpl(UsuarioRepository usuarioRepository, PersonaVulnerableRepository personaVulnerableRepository, PersonaJuridicaRepository personaJuridicaRepository, PersonaHumanaRepository personaHumanaRepository, TecnicoRepository tecnicoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.personaVulnerableRepository = personaVulnerableRepository;
        this.personaJuridicaRepository = personaJuridicaRepository;
        this.personaHumanaRepository = personaHumanaRepository;
        this.tecnicoRepository = tecnicoRepository;
    }

    @Override
    public SessionDTO setSession(Long idUsuario) {

        SessionDTO session = new SessionDTO();

        Usuario usuario = (Usuario) usuarioRepository.buscar(idUsuario);


        if (usuario != null) {
            session.setIdUsuario(usuario.getId());
            session.setTipoPersona(usuario.getTipoActor().toString().toLowerCase());
            session.setUsername(usuario.getUsername());

            if (usuario.getTipoActor().equals(TipoActor.JURIDICA)) {
                PersonaJuridica personaJuridica = (PersonaJuridica) personaJuridicaRepository.buscarPorUsuario(idUsuario);
                session.setIdPersona(personaJuridica.getId());
            } else if (usuario.getTipoActor().equals(TipoActor.HUMANA)) {
                PersonaHumana personaHumana = (PersonaHumana) personaHumanaRepository.buscarPorUsuario(idUsuario);
                session.setIdPersona(personaHumana.getId());
            } else if (usuario.getTipoActor().equals(TipoActor.VULNERABLE)) {
                PersonaVulnerable personaVulnerable = (PersonaVulnerable) personaVulnerableRepository.buscarPorUsuario(idUsuario);
                session.setIdPersona(personaVulnerable.getId());
            } else if (usuario.getTipoActor().equals(TipoActor.TECNICO)) {
                Tecnico tecnico = (Tecnico) tecnicoRepository.buscarPorUsuario(idUsuario);
                session.setIdPersona(tecnico.getId());
            }
        }
        return session;
    }
}
