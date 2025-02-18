package ar.edu.utn.frba.dds.Models.Domain.Contacto;
import ar.edu.utn.frba.dds.Models.DTOs.ContactoDTOEntrada;
import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaJuridica;

import javax.persistence.*;
import lombok.*;

@NoArgsConstructor
@Data
@Entity
public class Contacto extends Persistente {

    @Column
    private String valor;

    @Enumerated(EnumType.STRING)
    private TipoContacto tipo;
    
    public Contacto(String valor, TipoContacto tipo) {
        this.valor = valor;
        this.tipo = tipo;
    }
    
    //
    @Transient
    private PersonaJuridica personaJuridica;
    //

    public Contacto(ContactoDTOEntrada dto){
        this.valor  = dto.getValor();
        this.tipo   = TipoContacto.valueOf(dto.getTipo());
    }
}