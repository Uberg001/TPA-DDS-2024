package ar.edu.utn.frba.dds.Models.Domain.Heladera.Sensor;

import ar.edu.utn.frba.dds.Models.Domain.Heladera.Heladera;
import ar.edu.utn.frba.dds.Models.Domain.Heladera.Sensor.Temperatura.MyCustomMessageReceptorTemperatura;
import lombok.Data;

@Data
public abstract class Sensor {
    Heladera heladera;

    public abstract void medicion(String medicion);
    public abstract void connect(MyCustomMessageReceptorTemperatura receptor);
}
