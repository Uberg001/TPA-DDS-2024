package ar.edu.utn.frba.dds.Models.Domain.Heladera.Sensor.SolicitudAbrir;

import ar.edu.utn.frba.dds.Models.Domain.Config;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import org.eclipse.paho.client.mqttv3.MqttMessage;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Solicitud {

    private Heladera heladera;

    private String topic;

    private MqttClient client;

    public void solicitar(String codigoTarjeta) {
        try {
            this.publish("Heladera abierta! ID: " +
                    this.getHeladera().getId() +
                    " NOMBRE: " + this.getHeladera().getNombre() +
                    "CODIGO DE TARJETA: " + codigoTarjeta);

        } catch (Exception e) {
            System.out.println("Error al abrir la heladera: " + e.getMessage());
        }
    }

    public void connect() {
        String topicTemplate = Config.SOLICITUD_TOPIC;
        this.topic = topicTemplate.replace("${id}", String.valueOf(this.getHeladera().getId()));
        String clientId = "Solicitud" + this.getHeladera().getId();
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            String broker = "tcp://localhost:1883" + topic;
            client = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            System.out.println("Connecting to broker: " + topic);
            client.connect(connOpts);
            System.out.println("Connected");
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        } catch (Exception e){
            System.out.println("Error al conectar: " + e.getMessage());
        }
    }

    public void publish(String content) {
        try {
            System.out.println("Publishing message: " + content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(2);
            client.publish(topic, message);
            System.out.println("Message published");
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        } catch (Exception e){
            System.out.println("Error al publicar: " + e.getMessage());
        }
    }
}
