package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Models.DTOs.*;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.EstadoHeladera;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.TipoEstadoHeladera;
import ar.edu.utn.frba.dds.Models.Domain.Incidentes.EstadoIncidente;
import ar.edu.utn.frba.dds.Models.Domain.Incidentes.Incidente;
import ar.edu.utn.frba.dds.Models.Domain.Incidentes.TipoIncidente;
import ar.edu.utn.frba.dds.Models.Repositories.HeladeraRepository;
import ar.edu.utn.frba.dds.Models.Repositories.IncidenteRepository;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class IncidenteServiceImpl implements IncidenteService {

    private IncidenteRepository incidenteRepository;

    private ModelMapper modelMapper;

    private HeladeraRepository heladeraRepository;

    private ResolucionService resolucionService;

    public IncidenteServiceImpl(IncidenteRepository incidenteRepository, ModelMapper modelMapper, HeladeraRepository heladeraRepository, ResolucionService resolucionService) {
        this.incidenteRepository = incidenteRepository;
        this.modelMapper = modelMapper;
        this.heladeraRepository = heladeraRepository;
        this.resolucionService = resolucionService;
    }

    @Override
    public void reportarIncidente(IncidenteDTOEntrada incidenteDTOEntrada) {

        Incidente incidente = convertToEntity(incidenteDTOEntrada);
        incidente.getHeladera().setActiva(false);
        EstadoHeladera estadoHeladera = new EstadoHeladera();
        estadoHeladera.setTipoEstadoHeladera(TipoEstadoHeladera.DESCOMPUESTA);
        estadoHeladera.setFechaInicio(LocalDateTime.now());
        incidente.getHeladera().setEstadoActual(estadoHeladera);
        incidenteRepository.guardar(incidente);
    }

    @Override
    public IncidenteDTOSalida getIncidente(Long id) {
        Incidente incidente = (Incidente) incidenteRepository.buscar(id);
        return convertToDto(incidente);
    }

    private Incidente convertToEntity(IncidenteDTOEntrada incidenteDTOEntrada) {
        //Incidente incidente = new Incidente();
        Incidente incidente = modelMapper.map(incidenteDTOEntrada, Incidente.class);
        incidente.setTipoIncidente(TipoIncidente.valueOf(incidenteDTOEntrada.getTipoIncidente().toUpperCase()));
        incidente.setHeladera((Heladera) heladeraRepository.buscar(incidenteDTOEntrada.getHeladera()));
        incidente.getHeladera().setActiva(false);
        incidente.setEstadoIncidente(EstadoIncidente.PENDIENTE_REVISION);
        return incidente;
    }

    private IncidenteDTOSalida convertToDto(Incidente incidente) {
        IncidenteDTOSalida incidenteDTOSalida = modelMapper.map(incidente, IncidenteDTOSalida.class);
        HeladeraDTOSalida heladeraDTOSalida = convertToHeladeraDto(incidente.getHeladera());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy, hh:mm a", new Locale("es", "ES"));
        incidenteDTOSalida.setFecha(incidente.getFecha().format(formatter));
        //String formattedDate = dateTime.format(formatter);
        incidenteDTOSalida.setHeladera(heladeraDTOSalida);
        incidenteDTOSalida.setTipoIncidente(incidente.getTipoIncidente().toString());
        incidenteDTOSalida.setResolucion(resolucionService.getResolucionesIncidente(incidente.getId()));
        incidenteDTOSalida.setDescripcion(incidente.getDescripcion());
        incidenteDTOSalida.setEstadoIncidente(incidente.getEstadoIncidente().toString());

        return incidenteDTOSalida;
    }

    private HeladeraDTOSalida convertToHeladeraDto(Heladera heladera) {

        HeladeraDTOSalida heladeraDTOSalida = modelMapper.map(heladera, HeladeraDTOSalida.class);

        if (heladera.getDireccion() != null && heladera.getDireccion().getCoordenada() != null) {
            DireccionDTOSalida direccionDTOSalida = modelMapper.map(heladera.getDireccion(), DireccionDTOSalida.class);
            String localidadProvincia = heladera.getDireccion().getLocalidad().getNombre() + ", " + heladera.getDireccion().getLocalidad().getProvincia().getNombre();
            direccionDTOSalida.setLocalidad(localidadProvincia);
            direccionDTOSalida.setLatitud(heladera.getDireccion().getCoordenada().getLatitud());
            direccionDTOSalida.setLongitud(heladera.getDireccion().getCoordenada().getLongitud());
            direccionDTOSalida.setAltura(heladera.getDireccion().getAltura());
            heladeraDTOSalida.setDireccion(direccionDTOSalida);
        }

        if (heladera.getModelo() != null) {
            ModeloDTOSalida modeloDTOSalida = modelMapper.map(heladera.getModelo(), ModeloDTOSalida.class);
            heladeraDTOSalida.setModelo(modeloDTOSalida);
        }

        if (heladera.getEstado() != null) {
            EstadoHeladeraDTOSalida estadoHeladeraDTOSalida = modelMapper.map(heladera.getEstado(), EstadoHeladeraDTOSalida.class);
            heladeraDTOSalida.setEstado(estadoHeladeraDTOSalida);
        }


        heladera.getViandas().forEach(vianda -> {
            ViandaDTOSalida viandaDTOSalida = modelMapper.map(vianda, ViandaDTOSalida.class);
            heladeraDTOSalida.getViandas().add(viandaDTOSalida);
        });

        return heladeraDTOSalida;
    }
}
