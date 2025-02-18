package ar.edu.utn.frba.dds.Models.Domain.ImportarColaboraciones;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaHumana.PersonaHumana;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class VerificadorDeUsuarios {
    public boolean esUsuarioNuevo(PersonaHumana persona){
        return true; 
    }
    public void enviarMail(PersonaHumana persona){
       
    }
    public void agregarUsuarioNuevo(PersonaHumana persona){
         
    }
}
