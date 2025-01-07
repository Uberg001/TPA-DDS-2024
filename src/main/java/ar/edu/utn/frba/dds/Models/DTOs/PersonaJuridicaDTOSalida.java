package ar.edu.utn.frba.dds.Models.DTOs;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaJuridica;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaJuridicaDTOSalida {

    private Long id;
    private String razonSocial;
    private String tipo;
    private String rubroNombre;
    private List<ContactoDTOSalida> contactos;
    private DireccionDTOSalida direccion;
    private Float puntaje;
    private String codigoTarjeta;
    private String tipoDocumento;
    private String valorDocumento;
    private Long idUsuario;
    private String rubro;

    public void addContacto(ContactoDTOSalida contacto){
        this.contactos.add(contacto);
    }



}
