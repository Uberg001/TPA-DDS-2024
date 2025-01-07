package ar.edu.utn.frba.dds.Models.Domain.Heladera.AccesosHeladera;

import ar.edu.utn.frba.dds.Models.Domain.Actores.Persona;
import ar.edu.utn.frba.dds.Models.Domain.Config;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Persistente;
import javax.persistence.*;
import lombok.Data;
import lombok.Setter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Tarjeta extends Persistente {

    @Column
    private String codigo;

    @OneToMany(mappedBy = "codigoTarjeta")
    private List<Acceso> accesos = new ArrayList<>();

    @Transient
    private Persona duenio;

    @OneToMany(mappedBy = "codigoTarjeta")
    private List<Acceso> accesosDiarios = new ArrayList<>();

    @Column
    private Integer copiaNumero;

    public Tarjeta() {
        this.codigo = generarCodigo();
    }

    public Boolean CodigoValido() {
        return this.codigo.matches(Config.CODIGO_VALIDO_TARJETA);
    }

    public Boolean puedeUsarseEn(Heladera heladera) {
        return accesos.stream().anyMatch(acceso -> acceso.puedeUsarseEn(heladera));
    }

    public String generarCodigo(){
        int length = 11;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder codigo = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            codigo.append(characters.charAt(index));
        }
        return codigo.toString();
    }
}