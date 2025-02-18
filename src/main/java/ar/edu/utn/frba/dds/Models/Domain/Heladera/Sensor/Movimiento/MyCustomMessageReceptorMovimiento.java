package ar.edu.utn.frba.dds.Models.Domain.Heladera.Sensor.Movimiento;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Sensor.MyCustomMessageReceptor;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.concurrent.CountDownLatch;

@Data
@AllArgsConstructor
public class MyCustomMessageReceptorMovimiento implements MyCustomMessageReceptor {

    private SensorMovimiento sensorMovimiento;
    private CountDownLatch latch;

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        System.out.println("Message recived from topic " + topic + ": " + mqttMessage.toString());
        sensorMovimiento.medicion(mqttMessage.toString());
        latch.countDown();
    }
}