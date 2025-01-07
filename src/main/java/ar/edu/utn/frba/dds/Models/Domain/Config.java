package ar.edu.utn.frba.dds.Models.Domain;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

@Getter
public class Config {

    private static Properties properties = new Properties();

    public static final int TIEMPO_MAXIMO_ACCESO = 3;

    public static final int ESTADO_BASE_DISTRIBUIR = 0;

    public static final int MULTIPLICADOR_ACCESO_MENORES = 2;

    public static final int MINIMO_ACCESOS_DIARIOS = 4;

    public static final String CODIGO_VALIDO_TARJETA = "[a-zA-Z0-9]{11}";

    public static final String SENOR_TOPIC_MOVIMIENTO = "sensor.topic.movimiento";

    public static final String SOLICITUD_TOPIC = "sensor.topic.movimiento";

    public static final String SENSOR_TEMP_TOPIC = "sensor.topic.movimiento";

    public static final BigDecimal RADIO_TECNICO = new BigDecimal(2000);

}