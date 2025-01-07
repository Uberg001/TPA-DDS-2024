package ar.edu.utn.frba.dds.Models.DTOs;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.dds.Models.Domain.Direccion.Direccion;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.AccesosHeladera.Tarjeta;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Puntaje;

@Data
public class PersonaHumanaDTOEntrada {

    private UsuarioDTOEntrada usuario;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private List<ContactoDTOEntrada> contactos = new ArrayList<>();
    private Puntaje puntaje;
    private Tarjeta tarjeta;
    private Direccion direccion;

    public void addContacto(ContactoDTOEntrada contacto){
        this.contactos.add(contacto);
    }
}
