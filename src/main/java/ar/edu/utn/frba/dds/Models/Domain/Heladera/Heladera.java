package ar.edu.utn.frba.dds.Models.Domain.Heladera;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import ar.edu.utn.frba.dds.Models.Domain.Incidentes.Incidente;
import ar.edu.utn.frba.dds.Models.Domain.Direccion.Direccion;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.AccesosHeladera.Acceso;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Sensor.Registro;
import ar.edu.utn.frba.dds.Models.Domain.Suscripcion.Suscripcion;
import ar.edu.utn.frba.dds.Models.Domain.Suscripcion.SuscripcionDesperfecto;
import ar.edu.utn.frba.dds.Models.Domain.Suscripcion.SuscripcionDistribucion;
import ar.edu.utn.frba.dds.Models.Domain.Suscripcion.SuscripcionReposicion;
import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Vianda;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ManyToAny;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Heladera extends Persistente {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_direccion")
    private Direccion direccion;

    @Column
    private String nombre;

    @Column
    private Integer capacidad;

    @Column
    private LocalDate fechaInstalacion;

    @Column
    private Boolean activa = true;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_modelo")
    private Modelo modelo;

    @Column
    private Float temperaturaAnterior = null;

    @Column
    private Boolean abierta = false;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_heladera")
    private List<EstadoHeladera> estado = new ArrayList<>();

    @OneToMany(mappedBy = "heladera",cascade = CascadeType.ALL)
    private List<Vianda> viandas = new ArrayList<>();

    @OneToMany(mappedBy = "heladera",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Registro> historialRegistros = new ArrayList<>();

    @OneToMany(mappedBy = "heladera",cascade = CascadeType.ALL)
    private List<AperturaHeladera> aperturas = new ArrayList<>();

    @OneToMany(mappedBy = "heladera",cascade = CascadeType.ALL)
    private List<Incidente> incidentes = new ArrayList<>();

    @OneToMany(mappedBy = "heladeraUtilizada",cascade = CascadeType.ALL)
    private List<Acceso> accesosHistoricos = new ArrayList<>();

    @OneToMany(mappedBy = "heladeraUtilizada",cascade = CascadeType.ALL)
    private List<Acceso> accesosActivos = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "heladera_suscripcion",
            joinColumns = @JoinColumn(name = "id_heladera"),
            inverseJoinColumns = @JoinColumn(name = "id_suscripcion"))
    private List<Suscripcion> suscripciones = new ArrayList<>();


    public Heladera(String nombre) {

        this.nombre = nombre;
    }

    public EstadoHeladera getEstadoActual() {
    if (estado == null || estado.isEmpty()) {
        return null;
    }
    return estado.get(estado.size() - 1);
}

    public void setEstadoActual(EstadoHeladera estadoHeladera) {
        EstadoHeladera estadoActual = getEstadoActual();
        if (estadoActual != null) {
            estadoActual.setFechaFin(LocalDateTime.now());
            estadoActual.setActivo(false);
        }

        if (!estadoHeladera.getTipoEstadoHeladera().equals(TipoEstadoHeladera.FUNCIONANDO)){
            this.setActiva(false);
        }
        estado.add(estadoHeladera);
    }

    public void agregarVianda(Vianda vianda) {
        viandas.add(vianda);
        suscripciones.stream()
                .filter(suscripcion -> suscripcion instanceof SuscripcionDistribucion)
                .forEach(suscripcion -> suscripcion.analizarEstadoHeladera(this));
    }

    public void agregarSuscripcion(Suscripcion suscripcion) {
        suscripciones.add(suscripcion);
    }

    public void retirarVianda(Vianda vianda) {
        if (viandas.contains(vianda)) {
            viandas.remove(vianda);
            suscripciones.stream()
                    .filter(suscripcion -> suscripcion instanceof SuscripcionReposicion)
                    .forEach(suscripcion -> suscripcion.analizarEstadoHeladera(this));
            //suscripciones.forEach(suscripcion -> suscripcion.analizarEstadoHeladera());
        } else {
            System.out.println("La vianda no se encuentra en la heladera");
        }
    }

    public int cantidadViandas() {
        return viandas.size();
    }

    public Boolean entraOtra() {
        return viandas.size() < capacidad;
    }

    public Boolean temperaturaNormal() {
        return temperaturaAnterior >= modelo.getTemperaturaMinima()
                && temperaturaAnterior <= modelo.getTemperaturaMaxima();
    }

    public void agregarRegistro(Registro registro) {
        historialRegistros.add(registro);
    }

    public void agregarIncidente(Incidente incidente) {
        this.incidentes.add(incidente);
        suscripciones.stream()
                .filter(suscripcion -> suscripcion instanceof SuscripcionDesperfecto)
                .forEach(suscripcion -> suscripcion.analizarEstadoHeladera(this));
    }

    public Integer tiempoActividad() {
        LocalDate fechaActual = LocalDate.now();
        Period periodo = Period.between(fechaInstalacion, fechaActual);
        return periodo.getMonths();
    }

    public Boolean estaActiva() {
        return activa;
    }

    public void addApertura(AperturaHeladera apertura) {
        aperturas.add(apertura);
    }

    public Boolean abrirHeladera(String codigoTarjeta) throws Exception {
        Acceso acceso = accesosActivos.stream()
                .filter(acc -> acc.getCodigoTarjeta().equals(codigoTarjeta) && acc.puedeUsarse())
                .min(Comparator.comparing(Acceso::getFechaRealizado))//Busca la accion mas antigua
                .orElse(null);
        if (acceso != null) {
            acceso.usarse();
            accesosActivos.remove(acceso);
            accesosHistoricos.add(acceso);
            return true;
        } else {
            throw new Exception("No se encontró un acceso activo con el código de tarjeta proporcionado o el acceso está vencido.");
        }

    }

    public boolean tieneEsteAccesoActivo(Acceso acceso) {
        return accesosActivos.contains(acceso);
    }

    public void efectuarAcceso(Acceso acceso) {
        if (tieneEsteAccesoActivo(acceso)) {
            accesosActivos.remove(acceso);
        }
        accesosHistoricos.add(acceso);
    }

    public void actualizarVencidos() {
        List<Acceso> vencidos = accesosActivos.stream()
                .filter(acceso -> LocalDateTime.now().isAfter(acceso.fechaDeVencimiento()))
                .collect(Collectors.toList());

        vencidos.forEach(acceso -> {
            acceso.setEstado(3);
            accesosActivos.remove(acceso);
            accesosHistoricos.add(acceso);
        });
    }

    @Override
    public void setActivo(boolean activo) {
        super.setActivo(activo);
        this.setFechaBaja(LocalDateTime.now());
    }
}