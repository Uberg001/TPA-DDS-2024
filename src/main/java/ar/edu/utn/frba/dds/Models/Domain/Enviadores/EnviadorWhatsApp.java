package ar.edu.utn.frba.dds.Models.Domain.Enviadores;
import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaColaboradora;
import ar.edu.utn.frba.dds.Models.Domain.Contacto.TipoContacto;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class EnviadorWhatsApp implements Enviador{

    public static final String ACCOUNT_SID = "AC494678d087d2d012704361bf26d94ffa";
    public static final String AUTH_TOKEN = "25a197be668499e63e0d0e635ec6e181";

    @Override
    public void enviar(Mensaje mensaje) {
//        try{
//            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//            Message message = Message.creator(
//                    new com.twilio.type.PhoneNumber("whatsapp:" + this.obtenerContacto(mensaje.getReceptor())),
//                    new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
//                    mensaje.getSubject() + " \n " + mensaje.getBody()
//            ).create();
//        } catch (Exception e) {
//            throw new RuntimeException("Error al enviar el mensaje de whatsapp");
//        }
    }

    @Override
    public String obtenerContacto(PersonaColaboradora persona) {
        return persona  .obtenerContactos()
                .stream()
                .filter(contacto -> contacto.getTipo().equals(TipoContacto.WHATSAPP))
                .findFirst()
                .get()
                .getValor();
    }
}
