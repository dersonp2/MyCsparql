package Teste;

import Model.ResponseQuery;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import eu.larkc.csparql.common.RDFTable;
import eu.larkc.csparql.common.RDFTuple;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.Iterator;

public class ReceiverResult {
    Object arg;

    public void reveiver(MqttMessage mqttMessage) {
        Gson gson = new Gson();
        String mgson = new String(mqttMessage.getPayload());
        ResponseQuery rq = gson.fromJson(mgson, ResponseQuery.class);
        try {
            RDFTable q = (RDFTable) arg;
            System.out.println();
            System.out.println("-------" + q.size() + " results at SystemTime=[" + System.currentTimeMillis() + "]--------");
            Iterator i$ = q.iterator();

            while (i$.hasNext()) {
                RDFTuple t = (RDFTuple) i$.next();
                System.out.println(t.toString());
            }
        } catch (Exception e) {
            System.out.println("erro " + e);
        }
    }

    public void reveiver(ResponseQuery rq) {
        Gson gson = new Gson();
        String m = gson.toJson(rq);

        Gson gson1 = new Gson();
        ResponseQuery rqN = new ResponseQuery();

        try {
            rqN = gson1.fromJson(m, ResponseQuery.class);
        } catch (Exception e) {
            System.out.println(e);
        }

        ArrayList<RDFTuple> rdfTuples = new ArrayList<>();
        rdfTuples = rqN.getRdfTuples();
        int n = rdfTuples.size();

        int i = 0;

        for (i = 0; i < n; i++) {
            RDFTuple t = new RDFTuple();
            t = rdfTuples.get(i);
            System.out.println(t.toString());
        }


    }
}