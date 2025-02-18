package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Models.DTOs.*;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaJuridica;
import ar.edu.utn.frba.dds.Models.Domain.Actores.TipoActor;
import ar.edu.utn.frba.dds.Models.Domain.Actores.Usuario;
import ar.edu.utn.frba.dds.Models.Domain.Contacto.Contacto;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Rubro;
import ar.edu.utn.frba.dds.Models.Domain.Utils.TipoPJ;
import ar.edu.utn.frba.dds.Models.Repositories.PersonaJuridicaRepository;
import org.modelmapper.ModelMapper;

import java.util.List;

public class PersonaJuridicaServiceImpl implements PersonaJuridicaService {


    private PersonaJuridicaRepository personaJuridicaRepository;

    private ModelMapper modelMapper;

    public PersonaJuridicaServiceImpl(PersonaJuridicaRepository personaJuridicaRepository, ModelMapper modelMapper) {
        this.personaJuridicaRepository = personaJuridicaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PersonaJuridicaDTOSalida> getAllUsers() {
        List<PersonaJuridica> personaJuridicas = personaJuridicaRepository.buscarTodos();
        return personaJuridicas.stream().map(this::convertToDto).toList();
    }

    @Override
    public PersonaJuridicaDTOSalida getUserById(long userId) {
        PersonaJuridica personaJuridica = (PersonaJuridica) personaJuridicaRepository.buscar(userId);
        return convertToDto(personaJuridica);
    }

    @Override
    public PersonaJuridicaDTOSalida createUser(PersonaJuridicaDTOEntrada personaJuridicaDTOEntrada) {
        PersonaJuridica personaJuridica = convertToEntity(personaJuridicaDTOEntrada);
        personaJuridicaRepository.guardar(personaJuridica);
        return convertToDto(personaJuridica);
    }

    @Override
    public PersonaJuridicaDTOSalida updateUser(long userId, PersonaJuridicaDTOEntrada personaJuridicaDTOEntrada) {
        //TODO: Hacer el DTO, hay que ver primero que viene del front
        return null;
    }

    @Override
    public Float getPoints(long userId) {
        PersonaJuridica personaJuridica = (PersonaJuridica) personaJuridicaRepository.buscarPorUsuario(userId);
        if (personaJuridica.getPuntaje() != null) {
            return personaJuridica.getPuntaje().getPuntaje();
        }
        return null;
    }

    @Override
    public void deleteUser(long userId) {
        PersonaHumana personaHumana = (PersonaHumana) personaJuridicaRepository.buscar(userId);
        personaHumana.setActivo(false);
        personaJuridicaRepository.actualizar(personaHumana);
    }

    @Override
    public PersonaJuridicaDTOSalida getByUserId(long userId) {
        PersonaJuridica personaJuridica = (PersonaJuridica) personaJuridicaRepository.buscarPorUsuario(userId);
        return convertToDto(personaJuridica);
    }

    private PersonaJuridicaDTOSalida convertToDto(PersonaJuridica personaJuridica) {
        PersonaJuridicaDTOSalida personaJuridicaDTOSalida = modelMapper.map(personaJuridica, PersonaJuridicaDTOSalida.class);
        if (personaJuridica.getTipo() != null) {
            personaJuridicaDTOSalida.setTipo(personaJuridica.getTipo().toString());
        }
        personaJuridicaDTOSalida.setRubroNombre(personaJuridica.getRubro().getNombre());
        if (personaJuridica.getRubro() != null) {
            personaJuridicaDTOSalida.setRubro(personaJuridica.getRubro().getNombre());
        }
        if (personaJuridica.getContacto() != null) {
            personaJuridica.getContacto().forEach(
                    contacto ->
                            personaJuridicaDTOSalida.addContacto(modelMapper.map(contacto, ContactoDTOSalida.class)));
        }

        if (personaJuridica.getPuntaje() != null) {
            personaJuridicaDTOSalida.setPuntaje(personaJuridica.getPuntaje().getPuntaje());
        }
        if (personaJuridica.getTarjeta() != null) {
            personaJuridicaDTOSalida.setCodigoTarjeta(personaJuridica.getTarjeta().getCodigo());
        }
        if (personaJuridica.getUsuario() != null) {
            personaJuridicaDTOSalida.setIdUsuario(personaJuridica.getUsuario().getId());
        }

        return personaJuridicaDTOSalida;


    }

    private PersonaJuridica convertToEntity(PersonaJuridicaDTOEntrada personaJuridicaDTOEntrada) {
        PersonaJuridica personaJuridica = modelMapper.map(personaJuridicaDTOEntrada, PersonaJuridica.class);
        Usuario usuario = new Usuario();
        usuario.setUsername(personaJuridicaDTOEntrada.getUsuario().getNombre());
        usuario.setPassword(personaJuridicaDTOEntrada.getUsuario().getContrasenia());
        usuario.setAdmin(false);
        usuario.setTipoActor(TipoActor.JURIDICA);
        personaJuridica.setUsuario(usuario);
        personaJuridicaDTOEntrada.getContacto().forEach(contactoDTOEntrada ->
                personaJuridica.addContacto(modelMapper.map(contactoDTOEntrada, Contacto.class)));
        personaJuridica.setTipo(TipoPJ.valueOf(personaJuridicaDTOEntrada.getPJtipo()));
        Rubro rubro = new Rubro();
        //TODO: Esto no deberia crear un rubro, el front deberia mostrar los rubros que estan en db.
        rubro.setNombre(personaJuridicaDTOEntrada.getRubro());
        personaJuridica.setRubro(rubro);
        return personaJuridica;
    }
}