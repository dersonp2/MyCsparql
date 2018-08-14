package Csparql;

import Model.Query;
import Model.ResponseQuery;
import Start.StartBroker;
import Start.StartCsparql;
import ConfigLog.ConfigLog;
import Teste.ReceiverResult;
import com.google.gson.Gson;
import eu.larkc.csparql.cep.api.RdfStream;
import eu.larkc.csparql.common.RDFTable;
import eu.larkc.csparql.common.RDFTuple;
import eu.larkc.csparql.core.engine.CsparqlEngine;
import eu.larkc.csparql.core.engine.CsparqlQueryResultProxy;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;

import java.text.ParseException;
import java.util.Iterator;
import java.util.Observer;

public class QueryCsparql {
    //Csparql
    private static CsparqlEngine engine = null;
    private CsparqlQueryResultProxy csparqlQueryResult;
    //Logger
    private Logger logger;
    //Mqtt
    private MqttClient client = null;
    private MqttMessage msg = null;
    final private String TOPIC_RESPONSE = "responseQuery/";
    private String topic = null;

    public QueryCsparql(Query query) {
        System.out.println("ResponseQuery - Chegou no ResponseQuery");
        topic = TOPIC_RESPONSE + query.getReturnCode();
        System.out.println("Vai publicar em " + topic);
        connect();
        configLog();
        startCsparql();
        query(query.getQuery());
    }

    public void startCsparql() {
        engine = StartCsparql.getInstance().getEngine();
    }

    public void configLog() {
        logger = new ConfigLog().log(QueryCsparql.class);
    }

    public void query(String query) {
        try {
            logger.info("Chegou a consulta");
            csparqlQueryResult = engine.registerQuery(query, false);
            logger.debug("Query: {}", query);
            logger.debug("Query Start Time : {}", System.currentTimeMillis());


            csparqlQueryResult = engine.registerQuery(query, false);
            csparqlQueryResult.addObserver(new Observer() {
                @Override
                public void update(java.util.Observable o, Object arg) {

                    RDFTable q = (RDFTable) arg;
                    RDFTable q1 = new RDFTable() ;
                    q1.add(new RDFTuple());
                    System.out.println();
                    System.out.println("-------" + q.size() + " results at SystemTime=[" + System.currentTimeMillis() + "]--------");
                    Iterator i$ = q.iterator();

                    ResponseQuery rq = new ResponseQuery();
                    rq.setObservable(o);
                    rq.setObject(q);

                    while (i$.hasNext()) {
                        RDFTuple t = (RDFTuple) i$.next();
                        System.out.println(t.toString());
                    }

                    pubResponseQuery(rq);
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        client = StartBroker.getInstance().Connection("SSD");
    }

    public void pubResponseQuery(ResponseQuery rq) {
        Gson gson = new Gson();
        String m = gson.toJson(rq);

        msg = new MqttMessage();
        msg.setQos(2);
        msg.setRetained(false);
        msg.setPayload(m.getBytes());

        new ReceiverResult().reveiver(rq);
        // new ReceiverResult().reveiver(msg);

        try {
            client.publish(topic, msg);
            logger.info("\n" + "Publicou no topico: " + topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
