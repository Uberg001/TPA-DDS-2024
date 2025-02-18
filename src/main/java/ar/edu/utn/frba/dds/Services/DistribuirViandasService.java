package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Config.ServiceLocator;
import ar.edu.utn.frba.dds.Models.DTOs.DistribuirViandasDTOEntrada;
import ar.edu.utn.frba.dds.Models.DTOs.DonarViandaDTOEntrada;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaJuridica;
import ar.edu.utn.frba.dds.Models.Domain.Colaboraciones.DistribuirViandas;
import ar.edu.utn.frba.dds.Models.Domain.Colaboraciones.RegistrarPersonasVulnerables;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Utils.MotivoDistribucion;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Puntaje;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Vianda;
import ar.edu.utn.frba.dds.Models.Repositories.DistribuirViandasRepository;
import ar.edu.utn.frba.dds.Models.Repositories.HeladeraRepository;
import ar.edu.utn.frba.dds.Models.Repositories.PersonaHumanaRepository;
import io.javalin.http.Context;

import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DistribuirViandasService {

    private DistribuirViandasRepository distribuirViandasRepository;

    private HeladeraRepository heladeraRepository;

    private PersonaHumanaRepository personaHumanaRepository;
    private ModelMapper modelMapper;

    public DistribuirViandasService(DistribuirViandasRepository distribuirViandasRepository, HeladeraRepository heladeraRepository, PersonaHumanaRepository personaHumanaRepository, ModelMapper modelMapper) {
        this.distribuirViandasRepository = distribuirViandasRepository;
        this.heladeraRepository = heladeraRepository;
        this.personaHumanaRepository = personaHumanaRepository;
        this.modelMapper = modelMapper;
    }

    public void distribuirViandas(DistribuirViandasDTOEntrada dto) {
        DistribuirViandas distribuirViandas = dtoToEntity(dto);
        actualizarPuntaje(distribuirViandas);
        this.distribuirViandasRepository.guardar(distribuirViandas);
    }

    public void actualizarPuntaje(DistribuirViandas colaboracion) {
        Puntaje puntaje = new Puntaje();
        puntaje.setPuntaje(colaboracion.calcularPuntaje() + colaboracion.getPersonaHumana().getPuntaje().getPuntaje());
        colaboracion.getPersonaHumana().setPuntaje(puntaje);
    }

    public void distribuirViandasCSV(DistribuirViandasDTOEntrada dto) {
        DistribuirViandas distribuirViandas = dtoToEntityCSV(dto);
        this.distribuirViandasRepository.guardar(distribuirViandas);
    }

    public DistribuirViandas dtoToEntity(DistribuirViandasDTOEntrada dto) {
//        DistribuirViandas distribuirViandas = new DistribuirViandas();
        PersonaHumana personaHumana = (PersonaHumana) personaHumanaRepository.buscarPorUsuario(dto.getPersonaHumana());
        Heladera heladeraOrigen = (Heladera) heladeraRepository.buscar(dto.getHeladeraOrigen());
        Heladera heladeraDestino = (Heladera) heladeraRepository.buscar(dto.getHeladeraDestino());
//        distribuirViandas.setHeladeraOrigen(heladeraOrigen);
//        distribuirViandas.setHeladeraDestino(heladeraDestino);
//        distribuirViandas.setMotivo(MotivoDistribucion.valueOf(dto.getMotivo()));
//        distribuirViandas.setFechaRealizacion(dto.getFechaRealizacion());
//        distribuirViandas.setCantidad((Integer) dto.getCantViandas());
//        distribuirViandas.setPersonaHumana(personaHumana);

          DistribuirViandas distribuirViandas = new DistribuirViandas(
                heladeraOrigen,
                heladeraDestino,
                MotivoDistribucion.valueOf(dto.getMotivo()),
                dto.getFechaRealizacion(),
                (int) dto.getCantViandas(),
                new ArrayList<>(),
                personaHumana,
                null);
        return distribuirViandas;
    }

    public DistribuirViandas dtoToEntityCSV(DistribuirViandasDTOEntrada dto) {
//        ModelMapper modelMapper = new ModelMapper();
//        //PersonaHumana personaHumana = modelMapper.map(dto.getColaborador(), PersonaHumana.class);
//        List<Vianda> viandas = new ArrayList<>();
//        DistribuirViandas distribuirViandas = new DistribuirViandas(
//                dto.getHeladeraOrigen(),
//                dto.getHeladeraDestino(),
//                MotivoDistribucion.valueOf(dto.getMotivo()),
//                dto.getFechaRealizacion(),
//                (int) dto.getCantViandas(),
//                viandas,
//                null,
//                null);
//        return distribuirViandas;
        return null;
    }
}