package Communication;

import ConfigLog.ConfigLog;
import Csparql.QueryCsparql;
import Csparql.StreamCsparql;
import Model.Query;
import Model.SensorDataExtended;
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
    //Logger
    private Logger logger = null;

    public void connect() {
        logger = new ConfigLog().log(Communication.class);
        client = StartBroker.getInstance().Connection("SSD");
    }

    //Subscreve ao topico de consulta e encaminha para ResponseQuery
    public void subQueryTopic(String TOPIC_QUERY) {
        connect();
        try {
            client.subscribe(TOPIC_QUERY, 2);
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    logger.error("connectionLost");
                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    logger.info("Recebeu mensagem no topico: "+TOPIC_QUERY);
                    gson = new Gson();
                    String mgson = new String(mqttMessage.getPayload());
                    Query query = gson.fromJson(mgson, Query.class);
                    new QueryCsparql(query);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    logger.info("deliveryComplete");

                }
            });
            logger.info("Subscreveu no tópico: "+TOPIC_QUERY);
        } catch (MqttException e) {
            e.printStackTrace();
            System.out.println("SubQueryStream - Erro ao subscrever");
        }
    }

    //Subscreve ao topico de consulta e encaminha para StreamCsparql
    public void subStreamTopic(String TOPIC_STREAM) {
        connect();
        try {
            client.subscribe(TOPIC_STREAM, 2);
            logger.info("Subscreveu no tópico: "+TOPIC_STREAM);
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    logger.info("Recebeu mensagem no topico: "+TOPIC_STREAM);
                    String mStream = new String(mqttMessage.getPayload());
                    SensorDataExtended sensorDataExtended = gson.fromJson(mStream, SensorDataExtended.class);
                    RdfQuadruple[] rdfQuadruples = sensorDataExtended.getRdfQuadruple();
                    for (RdfQuadruple rdfQ : rdfQuadruples) {
                        StreamCsparql.getInstance().setStream(rdfQ);
                    }
                }
                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
            System.out.println("SubQueryStream - Erro ao subscrever");
        }
    }
}
