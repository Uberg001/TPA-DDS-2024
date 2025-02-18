package ar.edu.utn.frba.dds.Models.DTOs;

import ar.edu.utn.frba.dds.Models.Domain.Actores.Usuario;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Puntaje;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PersonaJuridicaDTOEntrada {
    private String nombre;
    private UsuarioDTOEntrada usuario;
    private String tipoDocumento;
    private String numeroDocumento;
    private List<ContactoDTOEntrada> contacto = new ArrayList<>();
    private String PJtipo;
    private String rubro;
    private Puntaje puntaje;
    public void addContacto(ContactoDTOEntrada contactoDTOEntrada){
        contacto.add(contactoDTOEntrada);
    }
}
