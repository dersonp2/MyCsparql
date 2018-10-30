package Csparql;

import Model.Query;
import Model.ResponseQuery;
import Start.StartBroker;
import Start.StartCsparql;
import ConfigLog.ConfigLog;
import WriteTxt.WriteTxtAll;
import WriteTxt.WriteTxtCS;
import WriteTxt.WriteTxtRQ;
import WriteTxt.WriteTxtSSQ;
import com.google.gson.Gson;
import eu.larkc.csparql.common.RDFTable;
import eu.larkc.csparql.common.RDFTuple;
import eu.larkc.csparql.core.engine.CsparqlEngine;
import eu.larkc.csparql.core.engine.CsparqlQueryResultProxy;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
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
    private WriteTxtAll writeTxtAll = new WriteTxtAll();

    public QueryCsparql(Query query) {
        topic = TOPIC_RESPONSE + query.getReturnCode();
        connect();
        configLog();
        startCsparql();
        try {
            new WriteTxtSSQ().setTimestamp(System.currentTimeMillis());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("WRITETXT SSQ");
        }
        query(query.getQuery(), query.getVar1(), query.getVar2());
    }

    public void startCsparql() {
        engine = StartCsparql.getInstance().getEngine();
    }

    public void configLog() {
        logger = new ConfigLog().log(QueryCsparql.class);
    }

    public void query(String query, String var1, String var2) {
        if (var1 != null && var2 != null) {
            engine.putStaticNamedModel(var1, var2);
        }
        try {
            try {
                new WriteTxtCS().setTimestamp(System.currentTimeMillis());
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("WRITETXT CS");
            }
            csparqlQueryResult = engine.registerQuery(query, false);
            csparqlQueryResult.addObserver(new Observer() {
                @Override
                public void update(java.util.Observable o, Object arg) {

                    ArrayList<RDFTuple> rdfTuples = new ArrayList();
                    RDFTable q = (RDFTable) arg;
                    Iterator i$ = q.iterator();

                    while (i$.hasNext()) {
                        RDFTuple t = (RDFTuple) i$.next();
                        rdfTuples.add(t);
                    }
                    ResponseQuery rq = new ResponseQuery();
                    rq.setObservable(o);
                    rq.setRdfTuples(rdfTuples);
                    try {
                        new WriteTxtCS().setTimestamp(System.currentTimeMillis());
                    } catch (IOException e) {
                        e.printStackTrace();
                        logger.error("WRITETXT CS");
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
        try {
            new WriteTxtRQ().setTimestamp(System.currentTimeMillis());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("WRITETXT RQ");
        }
        Gson gson = new Gson();
        String m = gson.toJson(rq);

        msg = new MqttMessage();
        msg.setQos(2);
        msg.setRetained(false);
        msg.setPayload(m.getBytes());

        try {
            client.publish(topic, msg);
            logger.info("\n" + "Publish Response: " + topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        try {
            new WriteTxtRQ().setTimestamp(System.currentTimeMillis());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("WRITETXT RQ");
        }
    }
}
