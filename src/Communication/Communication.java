package Communication;

import ConfigLog.ConfigLog;
import Csparql.QueryCsparql;
import Csparql.StreamCsparql;
import Model.ModelRDFs;
import Model.Query;
import Model.SensorDataExtended;
import Start.StartBroker;
import com.google.gson.Gson;
import eu.larkc.csparql.cep.api.RdfQuadruple;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;


public class Communication{
    //Json
    private Gson gson = null;
    //Mqtt
    private static MqttClient client;
    private static String TOPIC_STREAM = "Stream";
    private static String TOPIC_QUERY = "QueryCSparql";
    //Logger
    private Logger logger = null;

    public void connect() {
        logger = new ConfigLog().log(Communication.class);
        client = StartBroker.getInstance().Connection("SSD");
    }

    public void start() {
        connect();
        try {
            client.subscribe(TOPIC_QUERY, 2);
            logger.info("Subscreveu no tópico: " + TOPIC_QUERY);
            client.subscribe(TOPIC_STREAM, 2);
            logger.info("Subscreveu no tópico: " + TOPIC_STREAM);
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    logger.info("connectionLost");
                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    if (s.equals("QueryCSparql")) {
                        queryTopic(mqttMessage);
                    } else {
                        streamTopic(mqttMessage);
                    }
                }
                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    logger.info("deliveryComplete");
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void queryTopic(MqttMessage mqttMessage) {
        //System.out.println(s);
        logger.info("Recebeu mensagem no topico: " + "QueryCsparl");
        gson = new Gson();
        String mgson = new String(mqttMessage.getPayload());
        Query query = gson.fromJson(mgson, Query.class);
        new QueryCsparql(query);
    }

    public void streamTopic(MqttMessage mqttMessage) {
        logger.info("Recebeu mensagem no topico: " + "Stream");
        gson = new Gson();
        String mStream = new String(mqttMessage.getPayload());
        ModelRDFs mRdfs = gson.fromJson(mStream, ModelRDFs.class);
        RdfQuadruple[] rdfQuadruples = mRdfs.getRdfQuadruple();
        for (RdfQuadruple rdfQ : rdfQuadruples) {
            StreamCsparql.getInstance().setStream(rdfQ);
        }
    }
}
