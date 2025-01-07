package ar.edu.utn.frba.dds.Config;

import ar.edu.utn.frba.dds.Controllers.*;
import ar.edu.utn.frba.dds.Controllers.Colaboraciones.*;
import ar.edu.utn.frba.dds.Controllers.Suscripciones.SuscripcionController;
import ar.edu.utn.frba.dds.Controllers.Suscripciones.SuscripcionDesperfectoController;
import ar.edu.utn.frba.dds.Controllers.Suscripciones.SuscripcionDistribucionController;
import ar.edu.utn.frba.dds.Controllers.Suscripciones.SuscripcionReposicionController;
import ar.edu.utn.frba.dds.Models.Repositories.*;
import ar.edu.utn.frba.dds.Services.*;
import ar.edu.utn.frba.dds.Models.Repositories.HeladeraRepository;
import ar.edu.utn.frba.dds.Models.Repositories.PersonaHumanaRepository;
import ar.edu.utn.frba.dds.Models.Repositories.PersonaJuridicaRepository;
import ar.edu.utn.frba.dds.Models.Repositories.SuscripcionRepository;
import ar.edu.utn.frba.dds.Models.Repositories.UsuarioRepository;
import ar.edu.utn.frba.dds.Models.Repositories.ViandaRepository;
import ar.edu.utn.frba.dds.Services.PersonaHumanaServiceImpl;
import ar.edu.utn.frba.dds.Services.PersonaJuridicaServiceImpl;

import org.modelmapper.ModelMapper;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
    private static Map<String, Object> instances = new HashMap<>();


    @SuppressWarnings("unchecked")
    public static <T> T instanceOf(Class<T> componentClass) {
        String componentName = componentClass.getName();

        if (!instances.containsKey(componentName)) {
            if (componentName.equals(SuscripcionController.class.getName())) { // SUSCRIPCION CONTROLLER
                SuscripcionController instance = new SuscripcionController(instanceOf(SuscripcionRepository.class), instanceOf(SuscripcionServiceImpl.class), instanceOf(HeladeraServiceImpl.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(SuscripcionRepository.class.getName())) { // SUSCRIPCION REPOSITORY
                SuscripcionRepository instance = new SuscripcionRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(LogInController.class.getName())) { // LOG IN CONTROLLER
                LogInController instance = new LogInController();
            } else if (componentName.equals(UsuarioServiceImpl.class.getName())) { // USUARIO SERVICE IMPL
                UsuarioServiceImpl instance = new UsuarioServiceImpl(instanceOf(UsuarioRepository.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(UsuarioController.class.getName())) { // USUARIO CONTROLLER
                UsuarioController instance = new UsuarioController(instanceOf(UsuarioServiceImpl.class), instanceOf(SessionController.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(UsuarioRepository.class.getName())) { // USUARIO REPOSITORY
                UsuarioRepository instance = new UsuarioRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(PersonaHumanaRepository.class.getName())) { // PERSONA HUMANA REPOSITORY
                PersonaHumanaRepository instance = new PersonaHumanaRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(PersonaHumanaServiceImpl.class.getName())) { // PERSONA HUMANA SERVICE IMPL
                PersonaHumanaServiceImpl instance = new PersonaHumanaServiceImpl(instanceOf(PersonaHumanaRepository.class), instanceOf(ModelMapper.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(PersonaVulnerableRepository.class.getName())) { // PERSONA VULNERABLE REPOSITORY
                PersonaVulnerableRepository instance = new PersonaVulnerableRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(PersonaVulnerableServiceImpl.class.getName())) { // PERSONA VULNERABLE SERVICE IMPL
                PersonaVulnerableServiceImpl instance = new PersonaVulnerableServiceImpl(instanceOf(PersonaVulnerableRepository.class), instanceOf(ModelMapper.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(PersonaVulnerableController.class.getName())) { // PERSONA VULNERABLE CONTROLLER
                PersonaVulnerableController instance = new PersonaVulnerableController(instanceOf(PersonaVulnerableServiceImpl.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(PersonaJuridicaRepository.class.getName())) { // PERSONA JURIDICA REPOSITORY
                PersonaJuridicaRepository instance = new PersonaJuridicaRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(PersonaJuridicaServiceImpl.class.getName())) { // PERSONA JURIDICA SERVICE
                PersonaJuridicaService instance = new PersonaJuridicaServiceImpl(instanceOf(PersonaJuridicaRepository.class), instanceOf(ModelMapper.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(PersonaJuridicaController.class.getName())) { // PERSONA JURIDICA CONTROLLER
                PersonaJuridicaController instance = new PersonaJuridicaController(instanceOf(PersonaJuridicaServiceImpl.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(ModelMapper.class.getName())) { // MODEL MAPPER
                ModelMapper instance = new ModelMapper();
                instances.put(componentName, instance);
            } else if (componentName.equals(ColaboracionController.class.getName())) {
                ColaboracionController instance = new ColaboracionController(instanceOf(ColaboracionRepository.class), instanceOf(ModeloServiceImpl.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(ColaboracionRepository.class.getName())) {
                ColaboracionRepository instance = new ColaboracionRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(RecomendadorColaboradoresController.class.getName())) {
                RecomendadorColaboradoresController instance = new RecomendadorColaboradoresController();
                instances.put(componentName, instance);
            } else if (componentName.equals(ViandaRepository.class.getName())) {
                ViandaRepository instance = new ViandaRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(PersonaHumanaController.class.getName())) { // PERSONA HUMANA CONTROLLER
                PersonaHumanaController instance = new PersonaHumanaController(instanceOf(PersonaHumanaRepository.class), instanceOf(PersonaHumanaServiceImpl.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(HomeController.class.getName())) { // HOME CONTROLLER
                HomeController instance = new HomeController();
                instances.put(componentName, instance);
            } else if (componentName.equals(AlertasController.class.getName())) { // ALERTAS CONTROLLER
                AlertasController instance = new AlertasController(instanceOf(HeladeraController.class), instanceOf(IncidenteServiceImpl.class), instanceOf(UsuarioController.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(IncidenteServiceImpl.class.getName())) { // INCIDENTE SERVICE IMPL
                IncidenteServiceImpl instance = new IncidenteServiceImpl(instanceOf(IncidenteRepository.class),
                        instanceOf(ModelMapper.class), instanceOf(HeladeraRepository.class),instanceOf(ResolucionServiceImpl.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(HeladeraController.class.getName())) { // HELADERA CONTROLLER
                HeladeraController instance = new HeladeraController(instanceOf(HeladeraServiceImpl.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(HeladeraServiceImpl.class.getName())) { // HELADERA SERVICE IMPL
                HeladeraServiceImpl instance = new HeladeraServiceImpl(instanceOf(HeladeraRepository.class),
                        instanceOf(PersonaHumanaServiceImpl.class), instanceOf(IncidenteRepository.class),
                        instanceOf(ModelMapper.class), instanceOf(PersonaJuridicaServiceImpl.class),
                        instanceOf(TecnicoServiceImpl.class), instanceOf(ResolucionRepository.class),
                        instanceOf(ColaboracionRepository.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(HeladeraRepository.class.getName())) { // HELADERA REPOSITORY
                HeladeraRepository instance = new HeladeraRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(IncidenteRepository.class.getName())) { // INCIDENTE REPOSITORY
                IncidenteRepository instance = new IncidenteRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(DonarDineroController.class.getName())) { // DONAR DINERO CONTROLLER
                DonarDineroController instance = new DonarDineroController(instanceOf(DonarDineroService.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(DonarDineroService.class.getName())) { // DONAR DINERO SERVICE
                DonarDineroService instance = new DonarDineroService(instanceOf(DonarDineroRepository.class), instanceOf(PersonaHumanaRepository.class), instanceOf(PersonaJuridicaRepository.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(DonarDineroRepository.class.getName())) { // DONAR DINERO REPOSITORY
                DonarDineroRepository instance = new DonarDineroRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(DonarViandaController.class.getName())) {
                DonarViandaController instance = new DonarViandaController(instanceOf(DonarViandaService.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(DonarViandaService.class.getName())) {
                DonarViandaService instance = new DonarViandaService(instanceOf(DonarViandaRepository.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(DonarViandaRepository.class.getName())) {
                DonarViandaRepository instance = new DonarViandaRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(DistribuirViandasController.class.getName())) {
                DistribuirViandasController instance = new DistribuirViandasController(instanceOf(DistribuirViandasService.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(DistribuirViandasService.class.getName())) {
                DistribuirViandasService instance = new DistribuirViandasService(instanceOf(DistribuirViandasRepository.class), instanceOf(HeladeraRepository.class), instanceOf(PersonaHumanaRepository.class), instanceOf(ModelMapper.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(DistribuirViandasRepository.class.getName())) {
                DistribuirViandasRepository instance = new DistribuirViandasRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(HacerseCargoDeHeladerasController.class.getName())) {
                HacerseCargoDeHeladerasController instance = new HacerseCargoDeHeladerasController(instanceOf(HacerseCargoDeHeladeraService.class), instanceOf(PersonaJuridicaServiceImpl.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(HacerseCargoDeHeladeraService.class.getName())) {
                HacerseCargoDeHeladeraService instance = new HacerseCargoDeHeladeraService(instanceOf(HacerseCargoDeHeladeraRepository.class), instanceOf(ModeloRepository.class), instanceOf(PersonaJuridicaRepository.class), instanceOf(CoordenadaServiceImpl.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(HacerseCargoDeHeladeraRepository.class.getName())) {
                HacerseCargoDeHeladeraRepository instance = new HacerseCargoDeHeladeraRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(RegistrarPersonasVulnerablesController.class.getName())) {
                RegistrarPersonasVulnerablesController instance = new RegistrarPersonasVulnerablesController();
                instances.put(componentName, instance);
            } else if (componentName.equals(RegistrarPersonaVulnerableRepository.class.getName())) {
                RegistrarPersonaVulnerableRepository instance = new RegistrarPersonaVulnerableRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(SuscripcionDesperfectoController.class.getName())) {
                SuscripcionDesperfectoController instance = new SuscripcionDesperfectoController(instanceOf(SuscripcionRepository.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(SuscripcionReposicionController.class.getName())) {
                SuscripcionReposicionController instance = new SuscripcionReposicionController(instanceOf(SuscripcionRepository.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(SuscripcionDistribucionController.class.getName())) {
                SuscripcionDistribucionController instance = new SuscripcionDistribucionController(instanceOf(SuscripcionRepository.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(ImportarCSVController.class.getName())) {
                ImportarCSVController instance = new ImportarCSVController();
                instances.put(componentName, instance);
            } else if (componentName.equals(OfertaController.class.getName())) {
                OfertaController instance = new OfertaController(instanceOf(OfertaServiceImpl.class),
                        instanceOf(PersonaHumanaServiceImpl.class), instanceOf(PersonaJuridicaServiceImpl.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(OfertaServiceImpl.class.getName())) {
                OfertaServiceImpl instance = new OfertaServiceImpl(instanceOf(OfertaRepository.class),
                        instanceOf(ModelMapper.class), instanceOf(ColaboracionRepository.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(OfertaRepository.class.getName())) {
                OfertaRepository instance = new OfertaRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(ReporteController.class.getName())) {
                ReporteController instance = new ReporteController();
                instances.put(componentName, instance);
            } else if (componentName.equals(HeladeraRepository.class.getName())) {
                HeladeraRepository instance = new HeladeraRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(MapaController.class.getName())) {
                MapaController instance = new MapaController(instanceOf(HeladeraController.class), instanceOf(TecnicoController.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(DashboardController.class.getName())) {
                DashboardController instance = new DashboardController();
                instances.put(componentName, instance);
            } else if (componentName.equals(AccesoRepository.class.getName())) {
                AccesoRepository instance = new AccesoRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(AccionRepository.class.getName())) {
                AccionRepository instance = new AccionRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(AperturaHeladeraRepository.class.getName())) {
                AperturaHeladeraRepository instance = new AperturaHeladeraRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(AreaRepository.class.getName())) {
                AreaRepository instance = new AreaRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(CanjeRepository.class.getName())) {
                CanjeRepository instance = new CanjeRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(ContactoRepository.class.getName())) {
                ContactoRepository instance = new ContactoRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(DireccionRepository.class.getName())) {
                DireccionRepository instance = new DireccionRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(EntradaSensorRepository.class.getName())) {
                EntradaSensorRepository instance = new EntradaSensorRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(EstadoHeladeraRepository.class.getName())) {
                EstadoHeladeraRepository instance = new EstadoHeladeraRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(EstadoVisitaRepository.class.getName())) {
                EstadoVisitaRepository instance = new EstadoVisitaRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(FallaTecnicaRepository.class.getName())) {
                FallaTecnicaRepository instance = new FallaTecnicaRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(HeladeraSuscripcionRepository.class.getName())) {
                HeladeraSuscripcionRepository instance = new HeladeraSuscripcionRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(MensajeRepository.class.getName())) {
                MensajeRepository instance = new MensajeRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(ModeloRepository.class.getName())) {
                ModeloRepository instance = new ModeloRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(PersonaSuscripcionRepository.class.getName())) {
                PersonaSuscripcionRepository instance = new PersonaSuscripcionRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(PersonaHumanaContactoRepository.class.getName())) {
                PersonaHumanaContactoRepository instance = new PersonaHumanaContactoRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(OfrecerProductosRepository.class.getName())) {
                OfrecerProductosRepository instance = new OfrecerProductosRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(RegistroRepository.class.getName())) {
                RegistroRepository instance = new RegistroRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(ReporteRepository.class.getName())) {
                ReporteRepository instance = new ReporteRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(RespuestaPersonaRepository.class.getName())) {
                RespuestaPersonaRepository instance = new RespuestaPersonaRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(RubroRepository.class.getName())) {
                RubroRepository instance = new RubroRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(TarjetasRepository.class.getName())) {
                TarjetasRepository instance = new TarjetasRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(TecnicosRepository.class.getName())) {
                TecnicosRepository instance = new TecnicosRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(VisitaRepository.class.getName())) {
                VisitaRepository instance = new VisitaRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(TecnicoRepository.class.getName())) {
                TecnicoRepository instance = new TecnicoRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(TecnicoServiceImpl.class.getName())) {
                TecnicoServiceImpl instance = new TecnicoServiceImpl(instanceOf(TecnicoRepository.class), instanceOf(ModelMapper.class), instanceOf(CoordenadaServiceImpl.class), instanceOf(HeladeraRepository.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(TecnicoController.class.getName())) {
                TecnicoController instance = new TecnicoController(instanceOf(TecnicoServiceImpl.class), instanceOf(UsuarioController.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(ModeloRepository.class.getName())) {
                ModeloRepository instance = new ModeloRepository();
                instances.put(componentName, instance);
            } else if (componentName.equals(ModeloServiceImpl.class.getName())) {
                ModeloServiceImpl instance = new ModeloServiceImpl(instanceOf(ModeloRepository.class), instanceOf(ModelMapper.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(SuscripcionServiceImpl.class.getName())) {
                SuscripcionServiceImpl instance = new SuscripcionServiceImpl(instanceOf(SuscripcionRepository.class), instanceOf(ModelMapper.class), instanceOf(HeladeraRepository.class), instanceOf(PersonaHumanaRepository.class), instanceOf(PersonaJuridicaRepository.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(SessionServiceImpl.class.getName())) {
                SessionServiceImpl instance = new SessionServiceImpl(instanceOf(UsuarioRepository.class), instanceOf(PersonaVulnerableRepository.class), instanceOf(PersonaJuridicaRepository.class), instanceOf(PersonaHumanaRepository.class), instanceOf(TecnicoRepository.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(SessionController.class.getName())) {
                SessionController instance = new SessionController(instanceOf(SessionServiceImpl.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(CoordenadaServiceImpl.class.getName())) {
                CoordenadaServiceImpl instance = new CoordenadaServiceImpl();
                instances.put(componentName, instance);
            } else if (componentName.equals(ResolucionController.class.getName())) {
                ResolucionController instance = new ResolucionController(instanceOf(ResolucionServiceImpl.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(ResolucionServiceImpl.class.getName())) {
                ResolucionServiceImpl instance = new ResolucionServiceImpl(instanceOf(IncidenteRepository.class),
                        instanceOf(TecnicoRepository.class), instanceOf(ResolucionRepository.class), instanceOf(ModelMapper.class));
                instances.put(componentName, instance);
            } else if (componentName.equals(ResolucionRepository.class.getName())) {
                ResolucionRepository instance = new ResolucionRepository();
                instances.put(componentName, instance);
            } else {
                throw new RuntimeException("No se puede crear la instancia de " + componentName);
            }
        }

        /*
        if (!instances.containsKey(componentName)) {
            if(componentName.equals(ProductosController.class.getName())) {
                ProductosController instance = new ProductosController(instanceOf(RepositorioDeProductos.class));
                instances.put(componentName, instance);
            }
            else if (componentName.equals(RepositorioDeProductos.class.getName())) {
                RepositorioDeProductos instance = new RepositorioDeProductos();
                instances.put(componentName, instance);
            }
        }
        */

        return (T) instances.get(componentName);
    }
}