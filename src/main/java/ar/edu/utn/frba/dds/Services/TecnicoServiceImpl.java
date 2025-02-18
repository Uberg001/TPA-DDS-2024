package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Models.DTOs.*;
import ar.edu.utn.frba.dds.Models.Domain.Actores.Tecnico;
import ar.edu.utn.frba.dds.Models.Domain.Actores.TipoActor;
import ar.edu.utn.frba.dds.Models.Domain.Actores.Usuario;
import ar.edu.utn.frba.dds.Models.Domain.Config;
import ar.edu.utn.frba.dds.Models.Domain.Direccion.Direccion;
import ar.edu.utn.frba.dds.Models.Domain.Direccion.Localidad;
import ar.edu.utn.frba.dds.Models.Domain.Direccion.Provincia;
import ar.edu.utn.frba.dds.Models.Domain.Documento.Documento;
import ar.edu.utn.frba.dds.Models.Domain.Documento.TipoDocumento;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.RecomendarCoordenadas.Area;
import ar.edu.utn.frba.dds.Models.Domain.RecomendarCoordenadas.Coordenada;
import ar.edu.utn.frba.dds.Models.Repositories.HeladeraRepository;
import ar.edu.utn.frba.dds.Models.Repositories.TecnicoRepository;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class TecnicoServiceImpl implements TecnicoService {

    private ModelMapper modelMapper;
    private TecnicoRepository tecnicoRepository;

    private CoordenadaServiceImpl coordenadaService;

    private HeladeraRepository heladeraRepository;

    private IncidenteServiceImpl incidenteService;


    public TecnicoServiceImpl(TecnicoRepository tecnicoRepository, ModelMapper modelMapper,
                              CoordenadaServiceImpl coordenadaService, HeladeraRepository heladeraRepository) {
        this.tecnicoRepository = tecnicoRepository;
        this.modelMapper = modelMapper;
        this.coordenadaService = coordenadaService;
        this.heladeraRepository = heladeraRepository;
    }

    @Override
    public TecnicoDTOSalida createTecnico(TecnicoDTOEntrada tecnicoDTOEntrada) {

        Tecnico tecnico = convertToEntity(tecnicoDTOEntrada);
        tecnicoRepository.guardar(tecnico);
        return convertToDto(tecnico);
    }

    @Override
    public TecnicoDTOSalida getByUserId(Long id) {
        Tecnico tecnico = (Tecnico) tecnicoRepository.buscarPorUsuario(id);
        return convertToDto(tecnico);
    }

    @Override
    public List<TecnicoDTOSalida> getAllTecnicos() {
        List<Tecnico> tecnicos = tecnicoRepository.buscarTodos();
        return tecnicos.stream()
                .map(this::convertToDto)
                .toList();
    }

    private Tecnico convertToEntity(TecnicoDTOEntrada tecnicoDTOEntrada) {

        Tecnico tecnico = modelMapper.map(tecnicoDTOEntrada, Tecnico.class);
        Documento documento = new Documento(TipoDocumento.valueOf(tecnicoDTOEntrada.getTipoDocumento())
                , tecnicoDTOEntrada.getNumeroDocumento());
        Usuario usuario = new Usuario();
        usuario.setUsername(tecnicoDTOEntrada.getUsuario().getNombre());
        usuario.setPassword(tecnicoDTOEntrada.getUsuario().getContrasenia());
        usuario.setAdmin(false);
        usuario.setTipoActor(TipoActor.TECNICO);
        tecnico.setUsuario(usuario);
        tecnico.setDocumento(documento);

        Area area = new Area();
        Direccion direccion = new Direccion();
        direccion.setCalle(tecnicoDTOEntrada.getDireccion().getCalle());
        direccion.setAltura(tecnicoDTOEntrada.getDireccion().getAltura());
        Provincia provincia = new Provincia();
        provincia.setNombre(tecnicoDTOEntrada.getDireccion().getProvincia());
        Localidad localidad = new Localidad();
        localidad.setNombre(tecnicoDTOEntrada.getDireccion().getLocalidad());
        localidad.setProvincia(provincia);
        direccion.setLocalidad(localidad);
        area.setPuntoCentral(coordenadaService.obtenerCoordenada(direccion));
        area.setRadioM(Config.RADIO_TECNICO);
        tecnico.setAreaCobertura(area);
        return tecnico;
    }

    public List<HeladeraDTOSalida> getHeladerasEnZona(Long id) {
        Tecnico tecnico = (Tecnico) tecnicoRepository.buscarPorUsuario(id);
        List<HeladeraDTOSalida> heladeras = heladeraRepository.getHeladerasMapaTecnico().stream()
                .map(this::convertToHeladeraDTO)
                .toList();
        List<HeladeraDTOSalida> heladerasEnZona = heladeras.stream()
                .filter(heladera -> tecnico.getAreaCobertura().puntoEstaDentroDeArea(heladera.getDireccion().getCoordenada()))
                .collect(Collectors.toList());

        return heladerasEnZona;
    }

    private TecnicoDTOSalida convertToDto(Tecnico tecnico) {
        TecnicoDTOSalida tecnicoDTOSalida = modelMapper.map(tecnico, TecnicoDTOSalida.class);
        tecnicoDTOSalida.setTipoDocumento(tecnico.getDocumento().getTipo().toString());
        tecnicoDTOSalida.setNumeroDocumento(tecnico.getDocumento().getNumero());
        tecnicoDTOSalida.setCuil(tecnico.getDocumento().getNumero());
        tecnicoDTOSalida.setLatitud(tecnico.getAreaCobertura().getPuntoCentral().getLatitud());
        tecnicoDTOSalida.setLongitud(tecnico.getAreaCobertura().getPuntoCentral().getLongitud());
        tecnicoDTOSalida.setRadio(tecnico.getAreaCobertura().getRadioM());
        return tecnicoDTOSalida;
    }

    private HeladeraDTOSalida convertToHeladeraDTO(Heladera heladera) {
        HeladeraDTOSalida heladeraDTOSalida = modelMapper.map(heladera, HeladeraDTOSalida.class);

        if (heladera.getDireccion() != null && heladera.getDireccion().getCoordenada() != null) {
            DireccionDTOSalida direccionDTOSalida = modelMapper.map(heladera.getDireccion(), DireccionDTOSalida.class);
            direccionDTOSalida.setLatitud(heladera.getDireccion().getCoordenada().getLatitud());
            direccionDTOSalida.setLongitud(heladera.getDireccion().getCoordenada().getLongitud());
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
