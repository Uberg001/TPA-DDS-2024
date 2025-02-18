package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.Models.DTOs.*;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaJuridica;
import ar.edu.utn.frba.dds.Models.Domain.Actores.TipoActor;
import ar.edu.utn.frba.dds.Models.Domain.Direccion.Direccion;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.EstadoHeladera;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Modelo;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.TipoEstadoHeladera;
import ar.edu.utn.frba.dds.Models.Domain.Incidentes.Incidente;
import ar.edu.utn.frba.dds.Models.Domain.Incidentes.Resolucion;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Vianda;
import ar.edu.utn.frba.dds.Models.Repositories.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class HeladeraServiceImpl implements HeladeraService {

    private HeladeraRepository heladeraRepository;
    private PersonaHumanaServiceImpl personaHumanaService;

    private PersonaJuridicaServiceImpl personaJuridicaService;

    private TecnicoServiceImpl tecnicoService;

    private IncidenteRepository incidenteRepository;

    private ResolucionRepository resolucionRepository;



    private ModelMapper modelMapper;

    private ColaboracionRepository colaboracionRepository;

    public HeladeraServiceImpl(HeladeraRepository heladeraRepository, PersonaHumanaServiceImpl personaHumanaService,
                               IncidenteRepository incidenteRepository, ModelMapper modelMapper,
                               PersonaJuridicaServiceImpl personaJuridicaService, TecnicoServiceImpl tecnicoService,
                               ResolucionRepository resolucionRepository, ColaboracionRepository colaboracionRepository) {
        this.heladeraRepository = heladeraRepository;
        this.personaHumanaService = personaHumanaService;
        this.incidenteRepository = incidenteRepository;
        this.modelMapper = modelMapper;
        this.personaJuridicaService = personaJuridicaService;
        this.tecnicoService = tecnicoService;
        this.resolucionRepository = resolucionRepository;
        this.colaboracionRepository = colaboracionRepository;
    }

    @Override
    public Long createHeladera(HeladeraDTOEntrada heladeraDTOEntrada) {
        Heladera heladera = DtoToEntity(heladeraDTOEntrada);
        heladeraRepository.guardar(heladera);
        return heladera.getId();
    }

    public HeladeraDTOSalida getById(Long id) {
        Heladera heladera = (Heladera) heladeraRepository.buscar(id);
        return convertToDto(heladera);
    }

    @Override
    public List<HeladeraDTOSalida> getAllHeladeras() {
        List<Heladera> heladeras = heladeraRepository.buscarTodos();
        return heladeras.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<HeladeraDTOSalida> getHeladerasByLocalidad(String localidad) {
        List<HeladeraDTOSalida> heladeras = heladeraRepository.findByDireccionLocalidad(localidad).stream()
                .map(this::convertToDto)
                .toList();
        return heladeras;
    }

    @Override
    public Response agregarVianda(Long idHeladera, Long idVianda) {
        //TODO: Ver si primero agrego la vianda al sistema y dsp la meto en heladera (como esta ahora) o al revez

        return null;
    }

    @Override
    public HeladeraDTOSalida getHeladera(Long id) {
        Optional<Heladera> heladera = Optional.ofNullable((Heladera) heladeraRepository.buscar(id));
        if (heladera.isPresent()) {
            return convertToDto(heladera.get());
        }
        return null;
    }

    @Override
    public List<HeladeraDTOSalida> getAlertasHeladerasPersona(Long id, TipoActor tipoActor, Boolean solucionadas, SessionDTO sessionDTO) {
        List<HeladeraDTOSalida> heladeras = new ArrayList<>();

        if (tipoActor.equals(TipoActor.HUMANA)) {
            Optional<PersonaHumanaDTOSalida> personaHumana = Optional.ofNullable(personaHumanaService.getByUserId(id));
            personaHumana.ifPresent(ph ->
                    heladeras.addAll(heladeraRepository.getHeladerasPH(ph.getId()).stream()
                            .map(this::convertToDto)
                            .toList())

            );
            getAlertas(heladeras,TipoActor.HUMANA,sessionDTO.getIdPersona());
        } else if (tipoActor.equals(TipoActor.JURIDICA)) {
            Optional<PersonaJuridicaDTOSalida> personaJuridica = Optional.ofNullable(personaJuridicaService.getByUserId(id));
            personaJuridica.ifPresent(pj ->
                    heladeras.addAll(heladeraRepository.getHeladerasPJ(pj.getId()).stream()
                            .map(this::convertToDto)
                            .toList())
            );
            getAlertas(heladeras,TipoActor.JURIDICA,sessionDTO.getIdPersona());
        } else if (tipoActor.equals(TipoActor.TECNICO)) {
            heladeras.addAll(tecnicoService.getHeladerasEnZona(id));
            getAlertas(heladeras,TipoActor.TECNICO,sessionDTO.getIdPersona());
        }
        return heladeras;
    }

    @Override
    public List<HeladeraDTOSalida> getHeladerasSuscPJ(Long id) {
        List<Heladera> heladeras = heladeraRepository.getHeladerasSuscPJ(id);
        return heladeras.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public Object getAllHeladerasMapa() {
        List<Heladera> heladeras = heladeraRepository.getHeladerasMapa();
        return heladeras.stream()
                .map(this::convertToDto)
                .toList();
    }

    public List<HeladeraDTOSalida> getHeladerasSuscPH(Long id) {
        List<Heladera> heladeras = heladeraRepository.getHeladerasSuscPH(id);
        return heladeras.stream()
                .map(this::convertToDto)
                .toList();
    }

    private void getAlertas(List<HeladeraDTOSalida> heladeras, TipoActor tipoActor, Long idPersona) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy, hh:mm a", new Locale("es", "ES"));
        heladeras.forEach(heladera -> {
            List<Incidente> incidentes = new ArrayList<>();
            if (tipoActor.equals(TipoActor.HUMANA)){
                incidentes.addAll(incidenteRepository.buscarPorHeladeraPH(heladera.getId(),idPersona)) ;
            }
            else if (tipoActor.equals(TipoActor.JURIDICA)){
                incidentes.addAll(incidenteRepository.buscarPorHeladeraPJ(heladera.getId(),idPersona)) ;
                if(colaboracionRepository.getSiHeladeraEsDePJ(heladera.getId(),idPersona)){
                    incidentes.addAll(incidenteRepository.buscarPorHeladera(heladera.getId())) ;
                }
            } else if (tipoActor.equals(TipoActor.TECNICO)){
                incidentes.addAll(incidenteRepository.buscarPorHeladera(heladera.getId())) ;
            }
            if (incidentes.isEmpty()) {
                return;
            }
            incidentes.forEach(incidente -> {
                IncidenteDTOSalida incidenteDTOSalida = new IncidenteDTOSalida(incidente.getId(), incidente.getFecha().format(formatter),
                        convertToDto(incidente.getHeladera()), incidente.getTipoIncidente().toString(), null, incidente.getDescripcion(), incidente.getEstadoIncidente().toString());
                heladera.addIncidente(incidenteDTOSalida);
            });
        });
    }

    private Heladera DtoToEntity(HeladeraDTOEntrada heladeraDTOEntrada) {

        Heladera heladera = modelMapper.map(heladeraDTOEntrada, Heladera.class);
//        Direccion direccion = modelMapper.map(heladeraDTOEntrada.getDireccion(), Direccion.class);
//        heladera.setDireccion(direccion);
//
//        heladera.setFechaInstalacion(LocalDate.now());
//
//        //TODO: Ver si cuando se crea la heladera ya esta activa o hay que activarla dsp
//        heladera.setActiva(true);
//
//        Optional<Modelo> modelo = (Optional<Modelo>) modeloRepository.buscar(heladeraDTOEntrada.getIdModelo());
//        modelo.ifPresent(heladera::setModelo);
//
//        EstadoHeladera estadoHeladera = new EstadoHeladera();
//        estadoHeladera.setFechaInicio(LocalDateTime.now());
//        estadoHeladera.setTipoEstadoHeladera(TipoEstadoHeladera.FUNCIONANDO);
//        heladera.setEstado(estadoHeladera);

        return heladera;
    }

    private HeladeraDTOSalida convertToDto(Heladera heladera) {

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
