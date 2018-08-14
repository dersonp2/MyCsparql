package Teste;

import Model.ResponseQuery;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import eu.larkc.csparql.common.RDFTable;
import eu.larkc.csparql.common.RDFTuple;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Iterator;

public class ReceiverResult {
    Object arg;
    public void reveiver(MqttMessage mqttMessage) {
        Gson gson = new Gson();
        String mgson = new String(mqttMessage.getPayload());
        ResponseQuery rq = gson.fromJson(mgson, ResponseQuery.class);
        arg = rq.getObject();
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
        ResponseQuery t1 = new ResponseQuery();
        t1 = rq;
        /*ObjectMapper objectMapper = new ObjectMapper();
        try {
            String rqString = objectMapper.writeValueAsString(t1);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }*/


        Gson gson = new Gson();
        String m = gson.toJson(rq);

        Gson gson1 = new Gson();
        try{
            ResponseQuery rqN = gson1.fromJson(m, ResponseQuery.class);
            arg = rqN.getObject();
        } catch (Exception e){
            System.out.println(e);
        }


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
}