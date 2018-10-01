package streamer;

import Model.OntologyPrefix;
import Model.SemanticSensorData;
import Start.StartBroker;
import com.google.gson.Gson;
import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.cep.api.RdfStream;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SensorStream extends RdfStream implements Runnable {
    private RdfQuadruple q1, q2, q3, q4;
    private OntologyPrefix onto = new OntologyPrefix();
    private static MqttClient client;
    private boolean keepRunning;
    private long tempTS;
    private int resultTemp, resultFum = 0;
    private float resultUmi = 0;

    public SensorStream(String iri) {
        super(iri);
        //connect();
    }

    @Override
    public void run() {
        keepRunning = true;
        while (keepRunning == true) {
            try {

                Thread.sleep(5000);

                resultTemp += 3;
                resultUmi += 1.7;
                if(resultTemp == 27){
                resultFum =50;}
                //Primeira Observação
                tempTS = System.currentTimeMillis();
                //q1 = new RdfQuadruple(onto.getIotlite() + "FFE1-0000-1000-8000-00805F9B34FB", onto.getRdf()
                //      + "Type", onto.getSsn() + "SensingDevice", tempTS);

                // q2 = new RdfQuadruple(onto.getIotlite() + "FFE1-0000-1000-8000-00805F9B34FB", onto.getIotlite()
                //       + "hasUnit", onto.getIotlite() + "Celsius", tempTS);

                // q3 = new RdfQuadruple(onto.getIotlite() + "FFE1-0000-1000-8000-00805F9B34FB", onto.getIotlite()
                //       + "hasQuantityKind", onto.getIotlite() + "Temperature", tempTS);

                //q4 = new RdfQuadruple(onto.getIotlite() + "FFE1-0000-1000-8000-00805F9B34FB", onto.getSosa()
                //+ "hasResult", String.valueOf(result) + "^^http://www.w3.org/2001/XMLSchema#integer", tempTS);

                q4 = new RdfQuadruple(onto.getIotlite() + "FFE1-0000-1000-8000-00805F9B34FB", onto.getSosa()
                        + "hasResult", resultTemp + "^^http://www.w3.org/2001/XMLSchema#integer", tempTS);
                this.put(q4);
                q4 = new RdfQuadruple(onto.getIotlite() + "UM1-D4D3-0000", onto.getSosa()
                        + "hasResult", resultUmi + "^^http://www.w3.org/2001/XMLSchema#float", tempTS);
                this.put(q4);
                q4 = new RdfQuadruple(onto.getIotlite() + "FU-M4C4-0011", onto.getSosa()
                        + "hasResult", resultFum + "^^http://www.w3.org/2001/XMLSchema#integer", tempTS);
                this.put(q4);
                //this.put(q1);
                //this.put(q2);
                //this.put(q3);
                //this.put(q4);
                //RdfQuadruple[] rdfQuadruple = new RdfQuadruple[]{q1, q2, q3, q4};
                //pubResult(rdfQuadruple);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void pubResult(RdfQuadruple[] q) {
        connect();
        SemanticSensorData data = new SemanticSensorData();
        data.setRdfQuadruple(q);
        Gson gson = new Gson();
        String result = gson.toJson(data);


        MqttMessage msg = new MqttMessage((result).getBytes());
        msg.setQos(1);
        msg.setRetained(false);
        try {
            client.publish("Stream", msg);
            //System.out.println("\n" + "Publicou: " + tempTS);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    public void connect() {
        client = StartBroker.getInstance().Connection("SSD");
    }
}