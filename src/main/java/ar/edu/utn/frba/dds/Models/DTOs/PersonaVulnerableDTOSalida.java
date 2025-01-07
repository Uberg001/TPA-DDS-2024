package ar.edu.utn.frba.dds.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaVulnerableDTOSalida {

    private Long id;
    private String nombre;
    private LocalDate fechaNacimiento;
    private LocalDate fechaAlta;
    private Boolean enCondicionCalle;
    private DireccionDTOSalida domicilio;
    private DocumentoDTOSalida documento;
    private String tipo;
    private List<Long> personasACargo;
    private Long idUsuario;
}
