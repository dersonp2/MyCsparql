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

public class ParkingStream2 extends RdfStream implements Runnable {
    private RdfQuadruple v1, v2, v3, v4;
    String pk = new OntologyPrefix().getPk();
    private static MqttClient client;
    private boolean keepRunning;
    private long tempTS;

    public ParkingStream2(String iri) {
        super(iri);
    }

    @Override
    public void run() {
        keepRunning = true;
        while (keepRunning == true) {

            //Timestamp
            tempTS = System.currentTimeMillis();
            RdfQuadruple v1 = new RdfQuadruple(pk + "Space01", pk + "hasState", pk + "Free", tempTS);
            this.put(v1);
            RdfQuadruple v2 = new RdfQuadruple(pk + "Space02", pk + "hasState", pk + "Busy", tempTS);
            this.put(v2);
            RdfQuadruple v3 = new RdfQuadruple(pk + "Space03", pk + "hasState", pk + "Busy", tempTS);
            this.put(v3);
            RdfQuadruple v4 = new RdfQuadruple(pk + "Space04", pk + "hasState", pk + "Free", tempTS);
            this.put(v4);
            RdfQuadruple v5 = new RdfQuadruple(pk + "Space05", pk + "hasState", pk + "Busy", tempTS);
            this.put(v5);
            RdfQuadruple v6 = new RdfQuadruple(pk + "Space06", pk + "hasState", pk + "Free", tempTS);
            this.put(v6);
            RdfQuadruple v7 = new RdfQuadruple(pk + "Space07", pk + "hasState", pk + "Busy", tempTS);
            this.put(v7);
            RdfQuadruple v8 = new RdfQuadruple(pk + "Space08", pk + "hasState", pk + "Free", tempTS);
            this.put(v8);
            RdfQuadruple v9 = new RdfQuadruple(pk + "Space09", pk + "hasState", pk + "Busy", tempTS);
            this.put(v9);
            RdfQuadruple v10 = new RdfQuadruple(pk + "Space10", pk + "hasState", pk + "Free", tempTS);
            this.put(v10);

            RdfQuadruple v11 = new RdfQuadruple(pk + "Space11", pk + "hasState", pk + "Free", tempTS);
            this.put(v11);
            RdfQuadruple v12 = new RdfQuadruple(pk + "Space12", pk + "hasState", pk + "Busy", tempTS);
            this.put(v12);
            RdfQuadruple v13 = new RdfQuadruple(pk + "Space13", pk + "hasState", pk + "Busy", tempTS);
            this.put(v13);
            RdfQuadruple v14 = new RdfQuadruple(pk + "Space14", pk + "hasState", pk + "Free", tempTS);
            this.put(v14);
            RdfQuadruple v15 = new RdfQuadruple(pk + "Space15", pk + "hasState", pk + "Free", tempTS);
            this.put(v15);
            RdfQuadruple v16 = new RdfQuadruple(pk + "Space16", pk + "hasState", pk + "Busy", tempTS);
            this.put(v16);
            RdfQuadruple v17 = new RdfQuadruple(pk + "Space17", pk + "hasState", pk + "Busy", tempTS);
            this.put(v17);
            RdfQuadruple v18 = new RdfQuadruple(pk + "Space18", pk + "hasState", pk + "Free", tempTS);
            this.put(v18);
            RdfQuadruple v19 = new RdfQuadruple(pk + "Space19", pk + "hasState", pk + "Busy", tempTS);
            this.put(v19);
            RdfQuadruple v20 = new RdfQuadruple(pk + "Space20", pk + "hasState", pk + "Free", tempTS);
            this.put(v20);

            RdfQuadruple v21 = new RdfQuadruple(pk + "Space21", pk + "hasState", pk + "Free", tempTS);
            this.put(v21);
            RdfQuadruple v22 = new RdfQuadruple(pk + "Space22", pk + "hasState", pk + "Busy", tempTS);
            this.put(v22);
            RdfQuadruple v23 = new RdfQuadruple(pk + "Space23", pk + "hasState", pk + "Busy", tempTS);
            this.put(v23);
            RdfQuadruple v24 = new RdfQuadruple(pk + "Space24", pk + "hasState", pk + "Free", tempTS);
            this.put(v24);
            RdfQuadruple v25 = new RdfQuadruple(pk + "Space25", pk + "hasState", pk + "Free", tempTS);
            this.put(v25);
            RdfQuadruple v26 = new RdfQuadruple(pk + "Space26", pk + "hasState", pk + "Busy", tempTS);
            this.put(v26);
            RdfQuadruple v27 = new RdfQuadruple(pk + "Space27", pk + "hasState", pk + "Busy", tempTS);
            this.put(v27);
            RdfQuadruple v28 = new RdfQuadruple(pk + "Space28", pk + "hasState", pk + "Free", tempTS);
            this.put(v28);
            RdfQuadruple v29 = new RdfQuadruple(pk + "Space29", pk + "hasState", pk + "Busy", tempTS);
            this.put(v29);
            RdfQuadruple v30 = new RdfQuadruple(pk + "Space30", pk + "hasState", pk + "Free", tempTS);
            this.put(v30);

            RdfQuadruple v31 = new RdfQuadruple(pk + "Space31", pk + "hasState", pk + "Free", tempTS);
            this.put(v31);
            RdfQuadruple v32 = new RdfQuadruple(pk + "Space32", pk + "hasState", pk + "Busy", tempTS);
            this.put(v32);
            RdfQuadruple v33 = new RdfQuadruple(pk + "Space33", pk + "hasState", pk + "Busy", tempTS);
            this.put(v33);
            RdfQuadruple v34 = new RdfQuadruple(pk + "Space34", pk + "hasState", pk + "Free", tempTS);
            this.put(v34);
            RdfQuadruple v35 = new RdfQuadruple(pk + "Space35", pk + "hasState", pk + "Free", tempTS);
            this.put(v35);
            RdfQuadruple v36 = new RdfQuadruple(pk + "Space36", pk + "hasState", pk + "Busy", tempTS);
            this.put(v36);
            RdfQuadruple v37 = new RdfQuadruple(pk + "Space37", pk + "hasState", pk + "Busy", tempTS);
            this.put(v37);
            RdfQuadruple v38 = new RdfQuadruple(pk + "Space38", pk + "hasState", pk + "Free", tempTS);
            this.put(v38);
            RdfQuadruple v39 = new RdfQuadruple(pk + "Space39", pk + "hasState", pk + "Busy", tempTS);
            this.put(v39);
            RdfQuadruple v40 = new RdfQuadruple(pk + "Space40", pk + "hasState", pk + "Free", tempTS);
            this.put(v40);

            RdfQuadruple v41 = new RdfQuadruple(pk + "Space41", pk + "hasState", pk + "Free", tempTS);
            this.put(v41);
            RdfQuadruple v42 = new RdfQuadruple(pk + "Space42", pk + "hasState", pk + "Busy", tempTS);
            this.put(v42);
            RdfQuadruple v43 = new RdfQuadruple(pk + "Space43", pk + "hasState", pk + "Busy", tempTS);
            this.put(v43);
            RdfQuadruple v44 = new RdfQuadruple(pk + "Space44", pk + "hasState", pk + "Free", tempTS);
            this.put(v44);
            RdfQuadruple v45 = new RdfQuadruple(pk + "Space45", pk + "hasState", pk + "Busy", tempTS);
            this.put(v45);
            RdfQuadruple v46 = new RdfQuadruple(pk + "Space46", pk + "hasState", pk + "Free", tempTS);
            this.put(v46);
            RdfQuadruple v47 = new RdfQuadruple(pk + "Space47", pk + "hasState", pk + "Busy", tempTS);
            this.put(v47);
            RdfQuadruple v48 = new RdfQuadruple(pk + "Space48", pk + "hasState", pk + "Free", tempTS);
            this.put(v48);
            RdfQuadruple v49 = new RdfQuadruple(pk + "Space49", pk + "hasState", pk + "Busy", tempTS);
            this.put(v49);
            RdfQuadruple v50 = new RdfQuadruple(pk + "Space50", pk + "hasState", pk + "Free", tempTS);
            this.put(v50);

            RdfQuadruple v51 = new RdfQuadruple(pk + "Space51", pk + "hasState", pk + "Free", tempTS);
            this.put(v51);
            RdfQuadruple v52 = new RdfQuadruple(pk + "Space52", pk + "hasState", pk + "Busy", tempTS);
            this.put(v52);
            RdfQuadruple v53 = new RdfQuadruple(pk + "Space53", pk + "hasState", pk + "Busy", tempTS);
            this.put(v53);
            RdfQuadruple v54 = new RdfQuadruple(pk + "Space54", pk + "hasState", pk + "Free", tempTS);
            this.put(v54);
            RdfQuadruple v55 = new RdfQuadruple(pk + "Space55", pk + "hasState", pk + "Free", tempTS);
            this.put(v55);
            RdfQuadruple v56 = new RdfQuadruple(pk + "Space56", pk + "hasState", pk + "Busy", tempTS);
            this.put(v56);
            RdfQuadruple v57 = new RdfQuadruple(pk + "Space57", pk + "hasState", pk + "Busy", tempTS);
            this.put(v57);
            RdfQuadruple v58 = new RdfQuadruple(pk + "Space58", pk + "hasState", pk + "Free", tempTS);
            this.put(v58);
            RdfQuadruple v59 = new RdfQuadruple(pk + "Space59", pk + "hasState", pk + "Busy", tempTS);
            this.put(v59);
            RdfQuadruple v60 = new RdfQuadruple(pk + "Space60", pk + "hasState", pk + "Free", tempTS);
            this.put(v60);

            RdfQuadruple v61 = new RdfQuadruple(pk + "Space61", pk + "hasState", pk + "Free", tempTS);
            this.put(v61);
            RdfQuadruple v62 = new RdfQuadruple(pk + "Space62", pk + "hasState", pk + "Busy", tempTS);
            this.put(v62);
            RdfQuadruple v63 = new RdfQuadruple(pk + "Space63", pk + "hasState", pk + "Busy", tempTS);
            this.put(v63);
            RdfQuadruple v64 = new RdfQuadruple(pk + "Space64", pk + "hasState", pk + "Free", tempTS);
            this.put(v64);
            RdfQuadruple v65 = new RdfQuadruple(pk + "Space65", pk + "hasState", pk + "Free", tempTS);
            this.put(v65);
            RdfQuadruple v66 = new RdfQuadruple(pk + "Space66", pk + "hasState", pk + "Busy", tempTS);
            this.put(v66);
            RdfQuadruple v67 = new RdfQuadruple(pk + "Space67", pk + "hasState", pk + "Busy", tempTS);
            this.put(v67);
            RdfQuadruple v68 = new RdfQuadruple(pk + "Space68", pk + "hasState", pk + "Free", tempTS);
            this.put(v68);
            RdfQuadruple v69 = new RdfQuadruple(pk + "Space69", pk + "hasState", pk + "Busy", tempTS);
            this.put(v69);
            RdfQuadruple v70 = new RdfQuadruple(pk + "Space70", pk + "hasState", pk + "Free", tempTS);
            this.put(v70);

            RdfQuadruple v71 = new RdfQuadruple(pk + "Space71", pk + "hasState", pk + "Free", tempTS);
            this.put(v71);
            RdfQuadruple v72 = new RdfQuadruple(pk + "Space72", pk + "hasState", pk + "Busy", tempTS);
            this.put(v72);
            RdfQuadruple v73 = new RdfQuadruple(pk + "Space73", pk + "hasState", pk + "Busy", tempTS);
            this.put(v73);
            RdfQuadruple v74 = new RdfQuadruple(pk + "Space74", pk + "hasState", pk + "Free", tempTS);
            this.put(v74);
            RdfQuadruple v75 = new RdfQuadruple(pk + "Space75", pk + "hasState", pk + "Free", tempTS);
            this.put(v75);
            RdfQuadruple v76 = new RdfQuadruple(pk + "Space76", pk + "hasState", pk + "Busy", tempTS);
            this.put(v76);
            RdfQuadruple v77 = new RdfQuadruple(pk + "Space77", pk + "hasState", pk + "Busy", tempTS);
            this.put(v77);
            RdfQuadruple v78 = new RdfQuadruple(pk + "Space78", pk + "hasState", pk + "Free", tempTS);
            this.put(v78);
            RdfQuadruple v79 = new RdfQuadruple(pk + "Space79", pk + "hasState", pk + "Busy", tempTS);
            this.put(v79);
            RdfQuadruple v80 = new RdfQuadruple(pk + "Space80", pk + "hasState", pk + "Free", tempTS);
            this.put(v80);

            RdfQuadruple[] rdfQuadruple;
            for (int i = 1; i<81;i++){
                rdfQuadruple = new RdfQuadruple[]{v1,v2,v3,};
            }



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
