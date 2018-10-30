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

        int[] iStates = new int[80];
        String[] states = {"Busy", "Free"};
        RdfQuadruple[] rdfQuadruple = new RdfQuadruple[80];
        Random random = new Random();


        for (int i = 0; i < iStates.length; i++) {
            int index = random.nextInt(101) <= 15 ? 1 : 0;
            iStates[i] = index;
        }

        keepRunning = true;
        while (keepRunning) {

            tempTS = System.currentTimeMillis();

            int cont = 0;

            for (int i = 0; i < rdfQuadruple.length; i++) {
                if (iStates[i] == 0) { /* O -> L */
                    iStates[i] = random.nextInt(101) <= 1 ? 1 : 0;
                } else { /* L -> O */
                    iStates[i] = random.nextInt(101) <= 5 ? 0 : 1;
                    cont++;
                }
                rdfQuadruple[i] = new RdfQuadruple(pk + "Space" + i, pk + "hasState", pk + states[iStates[i]], tempTS);

            }
            System.out.println("Vagas Livres: " + cont);
            System.out.println("Vagas ocupadas: " + (80 - cont) + "\n");

            pubResult(rdfQuadruple);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            pubResult(rdfQuadruple);

            try {
                Thread.sleep(3000);
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
