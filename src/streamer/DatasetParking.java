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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DatasetParking extends RdfStream implements Runnable {
    private RdfQuadruple q1, q2, q3, q4;
    private OntologyPrefix onto = new OntologyPrefix();
    private static MqttClient client;
    private boolean keepRunning;
    private long tempTS;
    private int resultTemp, resultFum = 0;
    private float resultUmi = 0;

    public DatasetParking(String iri) {
        super(iri);
        //connect();
    }

    @Override
    public void run() {
        keepRunning = true;
        int inte =0;
        while (keepRunning == true) {
            try {
                BufferedReader datasetA = new BufferedReader(new FileReader("src/streamer/Dataset/DatasetA.txt"));
                System.out.println("Ready");
                String pk = new OntologyPrefix().getPk();
                String timestamp = null;

                while (datasetA.ready()) {
                    inte++;
                    System.out.println("\n*-*-*-*- Interação"+inte+" -*-*-*-*\n");
                    long tempTS = System.currentTimeMillis();
                    int wait = 0;
                    //RdfQuadruple[] rdfQuadruple = new RdfQuadruple[51];

                    for (int i = 0; i < 51; i++) {
                        String row = datasetA.readLine();
                        String[] separateResultT = new String[3];

                        separateResultT = row.split("_");

                        timestamp = separateResultT[0];
                        String spaceId = separateResultT[1];
                        String state = separateResultT[2];

                        RdfQuadruple quadruple = new RdfQuadruple(pk + "Space" + spaceId, pk + "hasState", pk + state, tempTS);
                        this.put(quadruple);
                        //System.out.println(quadruple);
                    }
                    Thread.sleep(5000);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
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