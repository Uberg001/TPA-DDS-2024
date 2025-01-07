package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Models.DTOs.*;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Actores.TipoActor;
import ar.edu.utn.frba.dds.Models.Domain.Actores.Usuario;
import ar.edu.utn.frba.dds.Models.Domain.Contacto.Contacto;
import ar.edu.utn.frba.dds.Models.Repositories.PersonaHumanaRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class PersonaHumanaServiceImpl implements PersonaHumanaService {
    private ModelMapper modelMapper;

    private PersonaHumanaRepository personaHumanaRepository;

    public PersonaHumanaServiceImpl(PersonaHumanaRepository personaHumanaRepository, ModelMapper modelMapper) {
        this.personaHumanaRepository = personaHumanaRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public List<PersonaHumanaDTOSalida> obtenerTodos() {

        List<PersonaHumana> personaHumanas = personaHumanaRepository.buscarTodos();

        return personaHumanas.stream()
                .map(this::convertToDto)
                .toList();
        
    }

    @Override
    public PersonaHumanaDTOSalida getById(long userId) {
        PersonaHumana personaHumana = (PersonaHumana) personaHumanaRepository.buscar(userId);
        return convertToDto(personaHumana);
    }

    public PersonaHumanaDTOSalida getByDocumento(long documento) {
        PersonaHumana personaHumana = (PersonaHumana) personaHumanaRepository.buscarPorDocumento(documento);
        return convertToDto(personaHumana);
    }

    @Override
    public PersonaHumanaDTOSalida createPH(PersonaHumanaDTOEntrada personaHumanaDTOEntrada) {
        PersonaHumana personaHumana = convertToEntity(personaHumanaDTOEntrada);
        personaHumanaRepository.guardar(personaHumana);
        return convertToDto(personaHumana);
    }

    public PersonaHumanaDTOSalida getByUserId(long userId) {
        PersonaHumana personaHumana = (PersonaHumana) personaHumanaRepository.buscarPorUsuario(userId);
        return convertToDto(personaHumana);
    }

    @Override
    public Long updatePH(long userId, PersonaHumanaDTOEntrada personaHumanaDTOEntrada) {
        return null;
    }

    @Override
    public void deletePH(long userId) {

    }

    @Override
    public Float getPoints(long userId) {
        PersonaHumana personaHumana = (PersonaHumana) personaHumanaRepository.buscarPorUsuario(userId);
        if (personaHumana.getPuntaje() != null) {
            return personaHumana.getPuntaje().getPuntaje();
        }
        return null;
    }

    private PersonaHumana convertToEntity(PersonaHumanaDTOEntrada personaHumanaDTOEntrada) {
        PersonaHumana personaHumana = modelMapper.map(personaHumanaDTOEntrada, PersonaHumana.class);
        Usuario usuario = new Usuario();
        usuario.setUsername(personaHumanaDTOEntrada.getUsuario().getNombre());
        usuario.setPassword(personaHumanaDTOEntrada.getUsuario().getContrasenia());
        usuario.setAdmin(false);
        usuario.setTipoActor(TipoActor.HUMANA);
        personaHumana.setUsuario(usuario);
        personaHumanaDTOEntrada.getContactos().forEach(contactoDTOEntrada ->
                personaHumana.addContacto(modelMapper.map(contactoDTOEntrada, Contacto.class)));
        return personaHumana;
    }

    private PersonaHumanaDTOSalida convertToDto(PersonaHumana personaHumana) {
        PersonaHumanaDTOSalida personaHumanaDTOSalida = modelMapper.map(personaHumana, PersonaHumanaDTOSalida.class);

        if (personaHumana.getDireccion() != null) {
            personaHumanaDTOSalida.setDomicilioCalle(personaHumana.getDireccion().getCalle());
            personaHumanaDTOSalida.setDomicilioNumero(personaHumana.getDireccion().getAltura());
            personaHumanaDTOSalida.setDomicilioLocalidad(personaHumana.getDireccion().getLocalidad().getNombre());
        }
        if(personaHumana.getPuntaje() != null){
            personaHumanaDTOSalida.setPuntaje(personaHumana.getPuntaje().getPuntaje());
        }
        personaHumanaDTOSalida.setIdUsuario(personaHumana.getUsuario().getId());
        return personaHumanaDTOSalida;
    }
}
