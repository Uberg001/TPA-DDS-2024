package ar.edu.utn.frba.dds.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaHumanaDTOSalida {

    private Long id;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private List<ContactoDTOSalida> contactos = new ArrayList<>();
    private String domicilioCalle;
    private int domicilioNumero;
    private String domicilioLocalidad;
    private Long idUsuario;
    private float puntaje;

}
