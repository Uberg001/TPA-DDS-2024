package ar.edu.utn.frba.dds.Models.Domain.Heladera.Sensor.Temperatura;

import ar.edu.utn.frba.dds.Models.Domain.Config;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Sensor.Entrada.EntradaSensor;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Sensor.Registro;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Sensor.Sensor;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Estado;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@NoArgsConstructor
@Data
public class SensorTemperatura extends Sensor {

    private LocalDateTime ultimoTiempoMedicion = LocalDateTime.now();
    private static Properties properties = new Properties();

    @Override
    public void medicion(String medicion) {
        ultimoTiempoMedicion = LocalDateTime.now();
        EntradaSensor entrada = createEntrada(medicion);
        Estado estado = determineEstado(medicion);
        Registro registro = createRegistro(entrada, estado);
        this.getHeladera().agregarRegistro(registro);
        System.out.println("Registro: " + registro);
    }

    private EntradaSensor createEntrada(String medicion) {
        EntradaSensor entrada = new EntradaSensor();
        entrada.setTemperatura(medicion);
        return entrada;
    }

    private Estado determineEstado(String medicionFloat) {
        if (isOutsideTemperatureRange(Float.valueOf(medicionFloat))) {
            return Estado.INACTIVA_INCIDENTE_ALERTA_TEMPERATURA;

        }
        return Estado.ACTIVA;
    }

    private boolean isOutsideTemperatureRange(Float medicionFloat) {
        return medicionFloat > getHeladera().getModelo().getTemperaturaMaxima()
                || medicionFloat < getHeladera().getModelo().getTemperaturaMinima();
    }

    private Registro createRegistro(EntradaSensor entrada, Estado estado) {
        return new Registro(entrada, LocalDateTime.now(), estado, getHeladera()) ;
    }

    @Override
    public void connect(MyCustomMessageReceptorTemperatura receptor) {
        String topicTemplate = Config.SENSOR_TEMP_TOPIC;
        String topic = topicTemplate.replace("${id}", String.valueOf(this.getHeladera().getId()));
        String broker = "tcp://localhost:1883";
        String clientId = "Temperatura" + this.getHeladera().getId();
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


            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            Integer tiempoMaximoSinMedicion = Integer.parseInt(Config.SENSOR_TEMP_TOPIC);
            executorService.scheduleAtFixedRate(this::checkLastMeasurement, tiempoMaximoSinMedicion, tiempoMaximoSinMedicion, TimeUnit.SECONDS);
            //Esta puesto en 1 seg, para no esperar en el test
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }

    private void checkLastMeasurement() {
        if (ultimoTiempoMedicion.plusSeconds(1).isBefore(LocalDateTime.now())) {
            // Generar un registro porque han pasado más de 1 minuto sin una nueva medición
            Registro registro = new Registro(null, LocalDateTime.now(), Estado.INACTIVA_INCIDENTE_ALERTA_FALLA_CONEXION, getHeladera());
            this.getHeladera().agregarRegistro(registro);
            System.out.println("Registro: " + registro);
        }
    }

}
