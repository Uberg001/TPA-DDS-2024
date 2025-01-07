package ar.edu.utn.frba.dds.Services;

import ar.edu.utn.frba.dds.Config.ServiceLocator;
import ar.edu.utn.frba.dds.Models.DTOs.DonarDineroDTOEntrada;
import ar.edu.utn.frba.dds.Models.Domain.Actores.Persona;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaJuridica;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import ar.edu.utn.frba.dds.Models.Domain.Actores.TipoActor;
import ar.edu.utn.frba.dds.Models.Domain.Colaboraciones.DonarDinero;
import ar.edu.utn.frba.dds.Models.Domain.Colaboraciones.RegistrarPersonasVulnerables;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Puntaje;
import ar.edu.utn.frba.dds.Models.Repositories.DonarDineroRepository;
import ar.edu.utn.frba.dds.Models.Repositories.PersonaHumanaRepository;
import ar.edu.utn.frba.dds.Models.Repositories.PersonaJuridicaRepository;
import io.javalin.http.Context;

public class DonarDineroService {

    private DonarDineroRepository donarDineroRepository;

    private PersonaHumanaRepository personaHumanaRepository;

    private PersonaJuridicaRepository personaJuridicaRepository;


    public DonarDineroService(DonarDineroRepository donarDineroRepository, PersonaHumanaRepository personaHumanaRepository, PersonaJuridicaRepository personaJuridicaRepository) {
        this.donarDineroRepository = donarDineroRepository;
        this.personaHumanaRepository = personaHumanaRepository;
        this.personaJuridicaRepository = personaJuridicaRepository;
    }

    public void donarDinero(DonarDineroDTOEntrada dto) {
        //DonarDinero donacion    = new DonarDinero(dto.getFechaDeDonacion(), dto.getMonto(), dto.getFrecuencia());

        DonarDinero donacion = dtoToEntity(dto);

        actualizarPuntaje(donacion);

        donarDineroRepository.guardar(donacion);
    }

    private DonarDinero dtoToEntity(DonarDineroDTOEntrada dto) {

        DonarDinero donacion = new DonarDinero();
        donacion.setFechaConcrecion(dto.getFechaDeDonacion());
        donacion.setMonto(dto.getMonto());
        donacion.setFrecuencia(dto.getFrecuencia());

        if (dto.getTipoActor().equals(TipoActor.HUMANA)) {
            PersonaHumana colaborador = (PersonaHumana) personaHumanaRepository.buscarPorUsuarioId(dto.getIdColaborador());
            donacion.setPersonaHumana(colaborador);
        } else if (dto.getTipoActor().equals(TipoActor.JURIDICA)) {
            PersonaJuridica colaborador = (PersonaJuridica) personaJuridicaRepository.buscarPorUsuario(dto.getIdColaborador());
            donacion.setPersonaJuridica(colaborador);
        }

        return donacion;
    }

    public void actualizarPuntaje(DonarDinero colaboracion) {

        Puntaje puntaje = new Puntaje();

        if (colaboracion.getPersonaHumana() != null) {
            puntaje.setPuntaje(colaboracion.calcularPuntaje() + colaboracion.getPersonaHumana().getPuntaje().getPuntaje());
            colaboracion.getPersonaHumana().setPuntaje(puntaje);
        } else if (colaboracion.getPersonaJuridica() != null) {
            puntaje.setPuntaje(colaboracion.calcularPuntaje() + colaboracion.getPersonaJuridica().getPuntaje().getPuntaje());
            colaboracion.getPersonaJuridica().setPuntaje(puntaje);
        }
    }
}
