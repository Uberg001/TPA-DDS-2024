package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Config.ServiceLocator;
import ar.edu.utn.frba.dds.Models.DTOs.HacerseCargoDeHeladeraDTOEntrada;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaJuridica;
import ar.edu.utn.frba.dds.Models.Domain.Colaboraciones.HacerseCargoDeHeladeras;
import ar.edu.utn.frba.dds.Models.Domain.Direccion.Direccion;
import ar.edu.utn.frba.dds.Models.Domain.Direccion.Localidad;
import ar.edu.utn.frba.dds.Models.Domain.Direccion.Provincia;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.EstadoHeladera;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Modelo;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.TipoEstadoHeladera;
import ar.edu.utn.frba.dds.Models.Domain.RecomendarCoordenadas.Coordenada;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Puntaje;
import ar.edu.utn.frba.dds.Models.Repositories.HacerseCargoDeHeladeraRepository;
import ar.edu.utn.frba.dds.Models.Repositories.ModeloRepository;
import ar.edu.utn.frba.dds.Models.Repositories.PersonaJuridicaRepository;
import ar.edu.utn.frba.dds.Utils.EntityManagerProvider;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class HacerseCargoDeHeladeraService {

    private HacerseCargoDeHeladeraRepository hacerseCargoDeHeladeraRepository;
    private ModeloRepository modeloRepository;

    private PersonaJuridicaRepository personaJuridicaRepository;

    private CoordenadaServiceImpl coordenadaService;

    public HacerseCargoDeHeladeraService(HacerseCargoDeHeladeraRepository hacerseCargoDeHeladeraRepository,
                                         ModeloRepository modeloRepository,
                                         PersonaJuridicaRepository personaJuridicaRepository,
                                         CoordenadaServiceImpl coordenadaService) {
        this.hacerseCargoDeHeladeraRepository = hacerseCargoDeHeladeraRepository;
        this.modeloRepository = modeloRepository;
        this.personaJuridicaRepository = personaJuridicaRepository;
        this.coordenadaService = coordenadaService;
    }

    public void cargarColaboracion(Context ctx, HacerseCargoDeHeladeraDTOEntrada dto) {
        HacerseCargoDeHeladeras colaboracion = convertToEntity(dto);
        //Cargo la colaboracion al colaborador
        actualizarPuntaje(ctx, colaboracion);

        hacerseCargoDeHeladeraRepository.guardar(colaboracion);
    }

    public HacerseCargoDeHeladeras convertToEntity(HacerseCargoDeHeladeraDTOEntrada dto) {
        Provincia provincia = new Provincia(dto.getProvincia());
        Localidad locaidad = new Localidad(dto.getLocalidad(), provincia);
        Direccion direccion = new Direccion(dto.getCalle(), dto.getAltura(), locaidad, null);
        Modelo modelo = (Modelo) modeloRepository.buscar(dto.getModelo());
        direccion.setCoordenada(coordenadaService.obtenerCoordenada(direccion));
//        Heladera heladera = new Heladera(direccion,
//                dto.getNombre(),
//                modelo.getCapacidad(),
//                dto.getFechaInstalacion(),
//                true,
//                modelo,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null);
        Heladera heladera = new Heladera();
        heladera.setNombre(dto.getNombre());
        heladera.setCapacidad(modelo.getCapacidad());
        heladera.setFechaInstalacion(dto.getFechaInstalacion());
        heladera.setModelo(modelo);
        heladera.setDireccion(direccion);
        EstadoHeladera estadoHeladera = new EstadoHeladera();
        estadoHeladera.setFechaInicio(LocalDateTime.now());
        estadoHeladera.setTipoEstadoHeladera(TipoEstadoHeladera.FUNCIONANDO);
        heladera.setEstadoActual(estadoHeladera);

        PersonaJuridica personaJuridica = (PersonaJuridica) personaJuridicaRepository.buscarPorUsuario(dto.getIdUsuario());

        return new HacerseCargoDeHeladeras(heladera, 1, LocalDate.now(), dto.getIdUsuario(), personaJuridica, null);
    }

    @SuppressWarnings("unchecked")
    public void actualizarPuntaje(Context ctx, HacerseCargoDeHeladeras colaboracion) {
        EntityManager em = EntityManagerProvider.getEntityManager();
        long idUsuario = ctx.sessionAttribute("id");
        //Me traigo al colaborador
        PersonaJuridica colaborador = (PersonaJuridica) ServiceLocator.instanceOf(PersonaJuridicaRepository.class).buscarPorUsuario(idUsuario);
        //Me traigo todas sus heladeras con una query nativa
        List<Object[]> heladeras = em.createNativeQuery("SELECT h.activa, h.fechaInstalacion FROM Colaboracion c LEFT JOIN Heladera h ON h.id = c.id_heladera INNER JOIN Usuario u ON u.id = c.id_Usuario_HCH WHERE u.id = :idColaborador").setParameter("idColaborador", idUsuario).getResultList();
        List<Heladera> heladerasPersona = new ArrayList<>();
        Integer cantidadHeladeras = 0;
        Integer sumaMesesActividad = 0;

        //Mapeo los resultados de la query a objetos Heladera
        for (Object[] fila : heladeras) {
            Heladera heladera = new Heladera();
            if (fila[0] == null && fila[1] == null) {
                break;
            }
            heladera.setActiva((Boolean) fila[0]);
            System.out.println(fila[0]);
            System.out.println(fila[1]);
            heladera.setFechaInstalacion(((java.sql.Date) fila[1]).toLocalDate());
            heladerasPersona.add(heladera);
        }
        //La cantidad de heladeras de la persona es la cantidad de registros que me traigo, siempre que sean activas
        cantidadHeladeras = heladerasPersona.stream().filter(heladera -> heladera.getActiva()).toList().size();
        //Para la suma de los meses de actividad de cada heladera, mapeo cada heladera a su tiempo de actividad y luego sumo
        sumaMesesActividad = heladerasPersona.stream().map(heladera -> heladera.tiempoActividad()).reduce(0, Integer::sum);

        //Inyecto los datos calculados en el metodo de calculo de puntaje de la colaboracion
        Puntaje puntaje = new Puntaje();
        puntaje.setPuntaje(colaboracion.calcularPuntajeBeta(cantidadHeladeras, sumaMesesActividad) + colaborador.getPuntaje().getPuntaje());
        colaborador.setPuntaje(puntaje);

        //System.out.println("El puntaje del colaborador es: "+colaborador.getPuntaje().getPuntaje());
        //System.out.println("La cantidad de heladeras del colaborador es: "+cantidadHeladeras);
        //System.out.println("La suma de los meses de actividad de las heladeras del colaborador es: "+sumaMesesActividad);
        //System.out.println("El incremento seria: "+colaboracion.calcularPuntajeBeta(cantidadHeladeras,sumaMesesActividad));
        ServiceLocator.instanceOf(PersonaJuridicaRepository.class).actualizar(colaborador);
    }

}
