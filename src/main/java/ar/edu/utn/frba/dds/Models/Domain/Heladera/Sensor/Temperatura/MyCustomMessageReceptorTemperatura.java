package ar.edu.utn.frba.dds.Models.Domain.Heladera.Sensor.Temperatura;

import ar.edu.utn.frba.dds.Models.Domain.Heladera.Sensor.MyCustomMessageReceptor;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.concurrent.CountDownLatch;

@Data
@AllArgsConstructor
public class MyCustomMessageReceptorTemperatura implements MyCustomMessageReceptor {

    private SensorTemperatura sensorTemperatura;
    private CountDownLatch latch;
    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        try {
            this.sensorTemperatura.medicion(mqttMessage.toString());
        } catch (Exception e) {
            System.out.println("Error during medicion: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Message recived from topic " + topic + ": " + mqttMessage.toString());
        latch.countDown();
    }
}