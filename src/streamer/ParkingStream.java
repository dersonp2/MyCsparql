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

import java.util.Random;

public class ParkingStream extends RdfStream implements Runnable {
    private RdfQuadruple v1, v2, v3, v4;
    String pk = new OntologyPrefix().getPk();
    private static MqttClient client;
    private boolean keepRunning;
    private long tempTS;

    public ParkingStream(String iri) {
        super(iri);
    }

    @Override
    public void run() {
        keepRunning = true;
        while (keepRunning == true) {

            tempTS = System.currentTimeMillis();

            String[] states = {"Busy", "Free"};
            Random random = new Random();
            int cont=0;
            RdfQuadruple[] rdfQuadruple = new RdfQuadruple[80];

            for (int i = 0; i < rdfQuadruple.length; i++) {
                int index = random.nextInt(101) <= 30 ? 1 : 0;
                rdfQuadruple[i] = new RdfQuadruple(pk + "Space" + i, pk + "hasState", pk + states[index], tempTS);
                //this.put(rdfQuadruple[i]);
                if (index == 1) {
                    cont++;
                }
            }
            System.out.println("Vagas Livres: "+cont);
            System.out.println("Vagas ocupadas: "+(80-cont)+"\n");

            pubResult(rdfQuadruple);
            try {
                Thread.sleep(5000);
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
