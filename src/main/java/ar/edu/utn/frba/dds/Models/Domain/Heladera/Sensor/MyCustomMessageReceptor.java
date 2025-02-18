package ar.edu.utn.frba.dds.Models.Domain.Heladera.Sensor;


import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface MyCustomMessageReceptor extends IMqttMessageListener {

    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception;
}