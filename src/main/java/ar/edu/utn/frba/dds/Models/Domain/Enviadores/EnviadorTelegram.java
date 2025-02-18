package ar.edu.utn.frba.dds.Models.Domain.Enviadores;

import ar.edu.utn.frba.dds.Models.Domain.Actores.PersonaColaboradora;
import ar.edu.utn.frba.dds.Models.Domain.Contacto.TipoContacto;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;

public class EnviadorTelegram extends TelegramLongPollingBot implements Enviador{

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return "tpdiseniosistemasbot";
    }
    @Override
    public String getBotToken() {
        return "7252709744:AAG5j_HaxYK-_Dq8w6cIDFaoKPvVxm4ejac";
    }

    @Override
    public void enviar(Mensaje mensaje) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(this.obtenerContacto((PersonaColaboradora) mensaje.getReceptor()));
        sendMessage.setText(mensaje.getSubject() + "\n" + mensaje.getBody());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Error al enviar mensaje de Telegram" + Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public String obtenerContacto(PersonaColaboradora persona) {
        return persona  .obtenerContactos()
                .stream()
                .filter(contacto -> contacto.getTipo().equals(TipoContacto.TELEGRAM))
                .findFirst()
                .get()
                .getValor();
    }
}
