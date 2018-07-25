package Resultados;

import eu.larkc.csparql.common.RDFTable;
import eu.larkc.csparql.common.RDFTuple;
import eu.larkc.csparql.core.ResultFormatter;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import javax.swing.*;
import java.util.Iterator;
import java.util.Observable;

public class Formatter extends ResultFormatter{
    String brokerUrl = "tcp://lsdi.ufma.br:1883";
    MqttClient cliente = null;
    String topic="";
    String topicResponse = "ResponseQuery";

    public Formatter() {
        try {
            cliente = new MqttClient(brokerUrl,"pubAnderson");
            cliente.connect();
            System.out.println("Conectou ao broker: "+brokerUrl);
        } catch (MqttException e) {
            e.printStackTrace();
            System.out.println("erro: "+e.getCause());
            System.out.println("erro: "+e.toString());
        }
    }

    public void update(Observable o, Object arg) {
        System.out.println("--------* ARG To String: "+arg.toString());
        RDFTable q = (RDFTable)arg;
        System.out.println("--------* Q To String: "+q.toString());
        System.out.println();
        System.out.println("-------" + q.size() + " results at SystemTime=[" + System.currentTimeMillis() + "]--------");
        Iterator i$ = q.iterator();
        System.out.println("--------* i$ To String: "+i$.toString());

        while(i$.hasNext()) {
            RDFTuple t = (RDFTuple)i$.next();
            //System.out.println(t.toString());
            System.out.println("--------* T To String: "+t.toString());
            pubResult(t);
        }

        System.out.println();
    }
    public void pubResult(RDFTuple t) {
        String result = t.toString();
        MqttMessage msg = new MqttMessage((result).getBytes());
        msg.setQos(1);
        msg.setRetained(false);
        try {
            cliente.publish(topicResponse, msg);
            System.out.println("\n"+"Publicou no topico: "+topicResponse+" a resposta: "+t);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
