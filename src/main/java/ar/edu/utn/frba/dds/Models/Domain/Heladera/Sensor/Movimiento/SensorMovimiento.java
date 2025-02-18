package ar.edu.utn.frba.dds.Models.Domain.Heladera.Sensor.Movimiento;

import ar.edu.utn.frba.dds.Models.Domain.Config;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Sensor.Entrada.EntradaSensor;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Sensor.Registro;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Sensor.Sensor;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Sensor.Temperatura.MyCustomMessageReceptorTemperatura;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Estado;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class SensorMovimiento extends Sensor {

    private MyCustomMessageReceptorMovimiento receptor;

    @Override
    public void medicion(String medicion) {
        EntradaSensor entrada = new EntradaSensor();
        entrada.setMovimiento(medicion);
        Estado estado = !getHeladera().getAbierta() ? Estado.ACTIVA : Estado.INACTIVA_INCIDENTE_ALERTA_FRAUDE;
        Registro registro = new Registro(entrada, LocalDateTime.now(), estado, getHeladera());
        this.getHeladera().agregarRegistro(registro);
        System.out.println("Registro: " + registro.toString());
    }

    @Override
    public void connect(MyCustomMessageReceptorTemperatura receptor) {
        String topicTemplate = Config.SENOR_TOPIC_MOVIMIENTO;
        String topic = topicTemplate.replace("${id}", String.valueOf(this.getHeladera().getId()));
        String broker = "tcp://localhost:1883";
        String clientId = "Movimiento" + this.getHeladera().getId();
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);

            System.out.println("Connecting to broker: " + broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");

            System.out.println("Build our receptor");

            System.out.println("Now we subscribe to the topic");
            sampleClient.subscribe(topic, receptor);

            System.out.println(receptor);

            System.out.println("Right! We are subscribed");
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }

}
