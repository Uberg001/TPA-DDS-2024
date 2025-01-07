package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Models.DTOs.ResolucionDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.ResolucionDTOSalida;
import ar.edu.utn.frba.dds.Models.DTOs.TecnicoDTOSalida;
import ar.edu.utn.frba.dds.Models.Domain.Actores.Tecnico;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.EstadoHeladera;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.TipoEstadoHeladera;
import ar.edu.utn.frba.dds.Models.Domain.Incidentes.EstadoIncidente;
import ar.edu.utn.frba.dds.Models.Domain.Incidentes.Incidente;
import ar.edu.utn.frba.dds.Models.Domain.Incidentes.Resolucion;
import ar.edu.utn.frba.dds.Models.Repositories.IncidenteRepository;
import ar.edu.utn.frba.dds.Models.Repositories.ResolucionRepository;
import ar.edu.utn.frba.dds.Models.Repositories.TecnicoRepository;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class ResolucionServiceImpl implements ResolucionService {

    private IncidenteRepository incidenteRepository;

    private TecnicoRepository tecnicoRepository;

    private ResolucionRepository resolucionRepository;

    private ModelMapper modelMapper;

    public ResolucionServiceImpl(IncidenteRepository incidenteRepository, TecnicoRepository tecnicoRepository, ResolucionRepository resolucionRepository, ModelMapper modelMapper) {
        this.incidenteRepository = incidenteRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.resolucionRepository = resolucionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createResolucion(ResolucionDTOEntrada resolucionDTOEntrada) {
        Resolucion resolucion = convertToEntity(resolucionDTOEntrada);
        verificarEstadoHeladera(resolucion);
        resolucionRepository.guardar(resolucion);
    }

    @Override
    public List<ResolucionDTOSalida> getResolucionesIncidente(Long id) {
        List<Resolucion> resoluciones = resolucionRepository.getResolucionesIncidente(id);
        return resoluciones.stream()
                .map(this::convertToDto)
                .toList();
    }

    private void verificarEstadoHeladera(Resolucion resolucion) {
        Heladera heladera = resolucion.getHeladera();
        EstadoHeladera estadoHeladeraNuevo = new EstadoHeladera();

        estadoHeladeraNuevo.setFechaInicio(LocalDateTime.now());
        estadoHeladeraNuevo.setTipoEstadoHeladera(TipoEstadoHeladera.valueOf(resolucion.getEstadoHeladera().toString()));

        heladera.setEstadoActual(estadoHeladeraNuevo);
        resolucion.setHeladera(heladera);
    }

    private Resolucion convertToEntity(ResolucionDTOEntrada resolucionDTOEntrada) {
        Resolucion resolucion = new Resolucion();
        resolucion.setDescripcion(resolucionDTOEntrada.getDescripcion());
        resolucion.setFoto_path(resolucionDTOEntrada.getFoto_path());
        resolucion.setFecha(LocalDateTime.now());

        Incidente incidente = (Incidente) incidenteRepository.buscar(resolucionDTOEntrada.getIdIncidente());
        Heladera heladera = incidente.getHeladera();
        Tecnico tecnico = (Tecnico) tecnicoRepository.buscar(resolucionDTOEntrada.getIdTecnico());

        if (resolucionDTOEntrada.getEstadoHeladera().equals("FUNCIONANDO")){
            incidente.setEstadoIncidente(EstadoIncidente.SOLUCIONADO);
            heladera.setActiva(true);
        } else if (resolucionDTOEntrada.getEstadoHeladera().equals("EN_REPARACION")) {
            incidente.setEstadoIncidente(EstadoIncidente.NO_SOLUCIONADO);
        } else {
            incidente.setEstadoIncidente(EstadoIncidente.PENDIENTE_REVISION);
        }
        resolucion.setIncidente(incidente);
        resolucion.setHeladera(heladera);
        resolucion.setTecnico(tecnico);


        resolucion.setEstadoHeladera(TipoEstadoHeladera.valueOf(resolucionDTOEntrada.getEstadoHeladera()));
        return resolucion;
    }

    private ResolucionDTOSalida convertToDto(Resolucion resolucion) {
        ResolucionDTOSalida resolucionDTOSalida = modelMapper.map(resolucion, ResolucionDTOSalida.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy, hh:mm a", new Locale("es", "ES"));
        resolucionDTOSalida.setFecha(resolucion.getFecha().format(formatter));
        resolucionDTOSalida.setTecnico(convertToTecnicoDto(resolucion.getTecnico()));
        resolucionDTOSalida.setEstadoHeladera(resolucion.getEstadoHeladera().toString());
        resolucionDTOSalida.setFoto_path(resolucion.getFoto_path());
        return resolucionDTOSalida;
    }

    private TecnicoDTOSalida convertToTecnicoDto(Tecnico tecnico) {
        TecnicoDTOSalida tecnicoDTOSalida = modelMapper.map(tecnico, TecnicoDTOSalida.class);
        tecnicoDTOSalida.setTipoDocumento(tecnico.getDocumento().getTipo().toString());
        tecnicoDTOSalida.setNumeroDocumento(tecnico.getDocumento().getNumero());
        tecnicoDTOSalida.setCuil(tecnico.getDocumento().getNumero());
        tecnicoDTOSalida.setLatitud(tecnico.getAreaCobertura().getPuntoCentral().getLatitud());
        tecnicoDTOSalida.setLongitud(tecnico.getAreaCobertura().getPuntoCentral().getLongitud());
        tecnicoDTOSalida.setRadio(tecnico.getAreaCobertura().getRadioM());
        return tecnicoDTOSalida;
    }
}
