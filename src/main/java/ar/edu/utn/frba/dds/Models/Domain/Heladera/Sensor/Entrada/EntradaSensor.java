package ar.edu.utn.frba.dds.Models.Domain.Heladera.Sensor.Entrada;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Data;
import lombok.Getter;

@Data
@Entity

public class EntradaSensor extends ar.edu.utn.frba.dds.Models.Domain.Persistente {

    public void setMovimiento(String movimiento) {
        this.movimiento = Boolean.valueOf(movimiento);
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = Float.valueOf(temperatura);
    }

    @Column
    protected Boolean movimiento;

    @Column
    protected Float temperatura;

}