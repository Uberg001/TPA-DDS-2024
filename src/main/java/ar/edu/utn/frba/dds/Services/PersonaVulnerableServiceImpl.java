package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.Models.DTOs.DireccionDTOSalida;
import ar.edu.utn.frba.dds.Models.DTOs.DocumentoDTOSalida;
import ar.edu.utn.frba.dds.Models.DTOs.PersonaVulnerableDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.PersonaVulnerableDTOSalida;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaVulnerable;
import ar.edu.utn.frba.dds.Models.Domain.Actores.TipoActor;
import ar.edu.utn.frba.dds.Models.Domain.Actores.Usuario;
import ar.edu.utn.frba.dds.Models.Repositories.PersonaVulnerableRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

//@Service
public class PersonaVulnerableServiceImpl implements PersonaVulnerableService {
    private static final Logger logger = LoggerFactory.getLogger(PersonaVulnerableService.class);


    private PersonaVulnerableRepository personaVulnerableRepository;
    private ModelMapper modelMapper;

    public PersonaVulnerableServiceImpl(PersonaVulnerableRepository personaVulnerableRepository,
                                        ModelMapper modelMapper) {
        this.personaVulnerableRepository = personaVulnerableRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PersonaVulnerableDTOSalida> getAllUsers() {
        List<PersonaVulnerable> personasVulnerables = personaVulnerableRepository.buscarTodos();
        return personasVulnerables.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public PersonaVulnerableDTOSalida getUserById(long userId) {
        PersonaVulnerable personaVulnerable = (PersonaVulnerable) personaVulnerableRepository.buscar(userId);
        return convertToDto(personaVulnerable);
    }

    @Override
    public PersonaVulnerableDTOSalida createUser(PersonaVulnerableDTOEntrada personaVulnerableDTOEntrada) {

        PersonaVulnerable personaVulnerable = convertToEntity(personaVulnerableDTOEntrada);
        personaVulnerableRepository.guardar(personaVulnerable);
        return convertToDto(personaVulnerable);
    }

    
    @Override
    public Long updateUser(long userId, PersonaVulnerableDTOEntrada personaVulnerableDTOEntrada) {
        return null;
    }

    @Override
    public void deleteUser(long userId) {
        PersonaVulnerable personaVulnerable = (PersonaVulnerable) personaVulnerableRepository.buscar(userId);
        personaVulnerable.setActivo(false);
        personaVulnerableRepository.guardar(personaVulnerable);
    }

    private PersonaVulnerableDTOSalida convertToDto(PersonaVulnerable personaVulnerable) {

        PersonaVulnerableDTOSalida personaVulnerableDTOSalida = modelMapper.map(personaVulnerable,
                PersonaVulnerableDTOSalida.class);

        if (personaVulnerable.getDomicilio() != null) {
            DireccionDTOSalida direccionDTOSalida = modelMapper.map(personaVulnerable.getDomicilio(), DireccionDTOSalida.class);
            personaVulnerableDTOSalida.setDomicilio(direccionDTOSalida);
        }

        if (personaVulnerable.getDocumento() != null) {
            DocumentoDTOSalida documentoDTOSalida = modelMapper.map(personaVulnerable.getDocumento(), DocumentoDTOSalida.class);
            personaVulnerableDTOSalida.setDocumento(documentoDTOSalida);
        }

        return personaVulnerableDTOSalida;
    }

    private PersonaVulnerable convertToEntity(PersonaVulnerableDTOEntrada personaVulnerableDTOEntrada) {
        PersonaVulnerable personaVulnerable = modelMapper.map(personaVulnerableDTOEntrada, PersonaVulnerable.class);
        personaVulnerable.setUsuario(new Usuario(personaVulnerableDTOEntrada.getUsuario().getNombre(),
                personaVulnerableDTOEntrada.getUsuario().getContrasenia(),false,TipoActor.VULNERABLE));
        return personaVulnerable;

    }
}