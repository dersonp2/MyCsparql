package Communication;

import ConfigLog.ConfigLog;
import Csparql.QueryCsparql;
import Csparql.StaticModel;
import Csparql.StreamCsparql;
import Model.ModelStaticModel;
import Model.Query;
import Model.SemanticSensorData;
import Start.StartBroker;
import com.google.gson.Gson;
import eu.larkc.csparql.cep.api.RdfQuadruple;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;


public class Communication {
    //Json
    private Gson gson = null;
    //Mqtt
    private static MqttClient client;
    private static String TOPIC_STREAM = "Stream";
    private static String TOPIC_QUERY = "QueryCSparql";
    private static String TOPIC_SJM = "SSD/StaticJenaModel";
    private static String TOPIC_SNM = "SSD/StaticNamedModel";
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
            logger.info("Subscriber on Topic: " + TOPIC_QUERY);
            client.subscribe(TOPIC_STREAM, 2);
            logger.info("Subscriber on Topic: " + TOPIC_STREAM);
            client.subscribe(TOPIC_SJM, 2);
            logger.info("Subscriber on Topic: " + TOPIC_SJM);
            client.subscribe(TOPIC_SNM, 2);
            logger.info("Subscriber on Topic: " + TOPIC_SNM);
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    logger.info("connectionLost");
                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    // logger.info("Chegou msg");
                    if (s.equals(TOPIC_QUERY)) {
                        queryTopic(mqttMessage);
                    } else if (s.equals(TOPIC_STREAM)) {
                        streamTopic(mqttMessage);
                    } else if (s.equals(TOPIC_SNM)) {
                        snmTopic(mqttMessage);
                    } else if (s.equals(TOPIC_SJM)) {
                        sjmTopic(mqttMessage);
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    //logger.info("deliveryComplete");
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void sjmTopic(MqttMessage mqttMessage) {
        StreamCsparql.getInstance();
        gson = new Gson();
        String mgson = new String(mqttMessage.getPayload());
        ModelStaticModel staticModel = gson.fromJson(mgson, ModelStaticModel.class);
        new StaticModel().putStaticJenaModel(staticModel.getVar3(), staticModel.getModel());
    }

    private void snmTopic(MqttMessage mqttMessage) {
        StreamCsparql.getInstance();
        gson = new Gson();
        String mgson = new String(mqttMessage.getPayload());
        ModelStaticModel staticModel = gson.fromJson(mgson, ModelStaticModel.class);
        new StaticModel().putStaticNamedModel(staticModel.getVar1(), staticModel.getVar2());
    }

    public void queryTopic(MqttMessage mqttMessage) {
        logger.info("New Query");
        gson = new Gson();
        String mgson = new String(mqttMessage.getPayload());
        Query query = gson.fromJson(mgson, Query.class);
        StreamCsparql.getInstance();
        new QueryCsparql(query);

    }

    public void streamTopic(MqttMessage mqttMessage) {
        logger.info("New Stream");
        gson = new Gson();
        String mStream = new String(mqttMessage.getPayload());
        SemanticSensorData data = gson.fromJson(mStream, SemanticSensorData.class);

        RdfQuadruple[] rdfQuadruples = data.getRdfQuadruple();
        for (RdfQuadruple rdfQ : rdfQuadruples) {
            StreamCsparql.getInstance().setStream(rdfQ);
        }
    }
}
