//
//package ar.edu.utn.frba.dds.Models.Domain.Enviadores;
//import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaColaboradora;
//import ar.edu.utn.frba.dds.Models.Domain.Contacto.TipoContacto;
//
//import java.util.Arrays;
//import java.util.Properties;
//import javax.mail.*;
//import javax.mail.internet.*;
//
//
//public class EnviadorMail implements Enviador {
//    @Override
//    public void enviar(Mensaje mensaje) {
//
//        // Propiedades del servidor de correo
//        Properties properties = new Properties();
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable", "true");
//        properties.put("mail.smtp.host", "smtp.gmail.com");
//        properties.put("mail.smtp.port", "587");
//
//        // Autenticación
//        final String username = "grupofllut@gmail.com";  // Tu correo
//        final String password = "vqadwcxafxlqtnbr";  // Tu contraseña diseniodeziztemaz
//        //myaccount.google.com/apppasswords -> link para generar contraseñas de aplicaciones
//
//        // Crear la sesión
//        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username, password);
//            }
//        });
//
//        try {
//            // Crear el mensaje
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress("grupofllut@gmail.com"));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(this.obtenerContacto((PersonaColaboradora) mensaje.getReceptor())));
//            message.setSubject(mensaje.getSubject());
//            message.setText(mensaje.getBody());
//
//            // Enviar el mensaje
//            Transport.send(message);
//
//            System.out.println("Correo enviado exitosamente");
//
//        } catch (MessagingException e) {
//            throw new RuntimeException("Error al enviar mensaje de Mail" + Arrays.toString(e.getStackTrace()));
//        }
//    }
//
//    @Override
//    public String obtenerContacto(PersonaColaboradora persona) {
//        return persona  .obtenerContactos()
//                        .stream()
//                        .filter(contacto -> contacto.getTipo().equals(TipoContacto.MAIL))
//                        .findFirst()
//                        .get()
//                        .getValor();
//    }
//
//}
