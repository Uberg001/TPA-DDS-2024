package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Models.DTOs.HeladeraDTOSalida;
import ar.edu.utn.frba.dds.Models.DTOs.SuscripcionDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.SuscripcionDTOSalida;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaJuridica;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Suscripcion.Suscripcion;
import ar.edu.utn.frba.dds.Models.Domain.Suscripcion.SuscripcionDesperfecto;
import ar.edu.utn.frba.dds.Models.Domain.Suscripcion.SuscripcionDistribucion;
import ar.edu.utn.frba.dds.Models.Domain.Suscripcion.SuscripcionReposicion;
import ar.edu.utn.frba.dds.Models.Repositories.HeladeraRepository;
import ar.edu.utn.frba.dds.Models.Repositories.PersonaHumanaRepository;
import ar.edu.utn.frba.dds.Models.Repositories.PersonaJuridicaRepository;
import ar.edu.utn.frba.dds.Models.Repositories.SuscripcionRepository;
import org.modelmapper.ModelMapper;

import java.util.List;

public class SuscripcionServiceImpl implements SuscripcionService {

    private SuscripcionRepository suscripcionRepository;
    private HeladeraRepository heladeraRepository;
    private PersonaHumanaRepository personaHumanaRepository;
    private PersonaJuridicaRepository personaJuridicaRepository;

    private ModelMapper modelMapper;

    public SuscripcionServiceImpl(SuscripcionRepository suscripcionRepository, ModelMapper modelMapper, HeladeraRepository heladeraRepository, PersonaHumanaRepository personaHumanaRepository, PersonaJuridicaRepository personaJuridicaRepository) {
        this.suscripcionRepository = suscripcionRepository;
        this.modelMapper = modelMapper;
        this.heladeraRepository = heladeraRepository;
        this.personaHumanaRepository = personaHumanaRepository;
        this.personaJuridicaRepository = personaJuridicaRepository;
    }


    @Override
    public List<SuscripcionDTOSalida> getAllSuscripciones() {
        List<Suscripcion> suscripciones = suscripcionRepository.buscarTodos();
        return suscripciones.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<SuscripcionDTOSalida> getSuscripcionByPersona(Long idPersona) {
        return List.of();
    }

    @Override
    public void save(SuscripcionDTOEntrada suscripcionDTOEntrada) {
        Suscripcion suscripcion = DtoToEntity(suscripcionDTOEntrada);
        suscripcionRepository.guardar(suscripcion);
    }

    private SuscripcionDTOSalida convertToDto(Suscripcion suscripcion) {

        SuscripcionDTOSalida suscripcionDTOSalida = modelMapper.map(suscripcion, SuscripcionDTOSalida.class);
        suscripcion.getHeladeras().forEach(heladera -> suscripcionDTOSalida.getHeladeras().add(
                modelMapper.map(heladera, HeladeraDTOSalida.class)));
        return suscripcionDTOSalida;
    }

    private Suscripcion DtoToEntity(SuscripcionDTOEntrada suscripcionDTOEntrada) {
        Suscripcion suscripcion = createSuscripcionByType(suscripcionDTOEntrada.getTipo());
        suscripcion.setHeladera((Heladera) heladeraRepository.buscar(suscripcionDTOEntrada.getIdHeladera()));
        addPersonaToSuscripcion(suscripcion, suscripcionDTOEntrada);
        return suscripcion;
    }

    private Suscripcion createSuscripcionByType(String tipo) {
        return switch (tipo) {
            case "desperfecto" -> new SuscripcionDesperfecto();
            case "reposicion" -> new SuscripcionReposicion();
            case "distribucion" -> new SuscripcionDistribucion();
            default -> null;
        };
    }

    private void addPersonaToSuscripcion(Suscripcion suscripcion, SuscripcionDTOEntrada suscripcionDTOEntrada) {
        if (suscripcionDTOEntrada.getTipoPersona().equals("humana")) {
            suscripcion.addPersona((PersonaHumana) personaHumanaRepository.buscar(suscripcionDTOEntrada.getIdPersona()));
        } else if (suscripcionDTOEntrada.getTipoPersona().equals("juridica")) {
            suscripcion.addPersonaJuridica((PersonaJuridica) personaJuridicaRepository.buscar(suscripcionDTOEntrada.getIdPersona()));
        }
    }
}
