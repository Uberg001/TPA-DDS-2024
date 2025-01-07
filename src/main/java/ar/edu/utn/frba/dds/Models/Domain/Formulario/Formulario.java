package ar.edu.utn.frba.dds.Models.Domain.Formulario;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class Formulario {
    private LocalDate fechaFormulario;
    private List<CampoDinamico> elementos=new ArrayList<>();

    public void agregarCampo(CampoDinamico campo) {
        this.elementos.add(campo);
    }
}