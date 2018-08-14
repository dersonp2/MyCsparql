import Model.OntologyPrefix;
import Resultados.Formatter;
import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.cep.api.RdfStream;
import eu.larkc.csparql.common.utils.CsparqlUtils;
import eu.larkc.csparql.core.ResultFormatter;
import eu.larkc.csparql.core.engine.ConsoleFormatter;
import eu.larkc.csparql.core.engine.CsparqlEngine;
import eu.larkc.csparql.core.engine.CsparqlEngineImpl;
import eu.larkc.csparql.core.engine.CsparqlQueryResultProxy;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import streamer.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.Observer;
import java.util.Properties;

public class Main {

    String query = null;
    RdfStream rdfStream = null;
    static CsparqlEngine engine = null;
    CsparqlQueryResultProxy csparqlQueryResult = null;
    Logger logger;
    private static OntologyPrefix prefix = new OntologyPrefix();
    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(Main.class);

        String caminho = "src/ConfigLog/csparql_log4j.properties";
        try {
            PropertyConfigurator.configure(caminho);
            logger.debug("configurou Loog");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        //Iniciar C-sparql
        engine = new CsparqlEngineImpl();

        try {
            engine.initialize(true);
            logger.info("iniciou c-sparql ");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Seleciona o Metodo
        //basicRDF();
        //percntilRDF();
        //socialRDF();
        //cloudMonitoring();
        staticKnowledge();

    }//psvm

    public static void basicRDF() {
        String query, query2 = null;
        RdfStream rdfStream = null;
        CsparqlQueryResultProxy csparqlQueryResult = null;
        Logger logger = LoggerFactory.getLogger(Main.class);

        query = "REGISTER QUERY WhoLikesWhat AS "
                + "PREFIX ex: <"+prefix.getSosa()+"> "
                + "PREFIX iot: <"+prefix.getIotlite()+"> "
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
                + "SELECT ?s ?o "
                + "FROM STREAM <http://myexample.org/stream> [RANGE 5s STEP 1s] "
                + "WHERE { ?s iot:hasQuatityKind ?o }";

        query2 = "REGISTER QUERY WhoLikesWhat AS "
                + "PREFIX ex: <"+prefix.getSosa()+"> "
                + "PREFIX iot: <"+prefix.getIotlite()+"> "
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
                + "SELECT ?result "
                + "FROM STREAM <http://myexample.org/stream> [RANGE 5s STEP 1s] "
                + "WHERE { ?s iot:hasQuatityKind iot:Temperature . "
                +"?s ex:hasResult ?result . " +
                "FILTER (?result > \"26\"^^xsd:integer)}";

        query2 = "REGISTER QUERY WhoLikesWhat AS "
                + "PREFIX ex: <"+prefix.getSosa()+"> "
                + "PREFIX iot: <"+prefix.getIotlite()+"> "
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
                + "SELECT ?result "
                + "FROM STREAM <http://myexample.org/stream> [RANGE 5s STEP 1s] "
                + "WHERE { ?s iot:hasQuatityKind iot:Temperature . "
                +"?s ex:hasResult ?result . " +
                "FILTER (?result > \"26\"^^xsd:integer)}";
        rdfStream = new BasicRDFStreamTestGenerator("http://myexample.org/stream");

        engine.registerStream(rdfStream);
        final Thread thread = new Thread((Runnable) rdfStream);
        thread.start();

        try {
            csparqlQueryResult = engine.registerQuery(query2, false);
            logger.debug("Query: {}", query);
            logger.debug("Query Start Time : {}", System.currentTimeMillis());
            csparqlQueryResult.addObserver(new ConsoleFormatter());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void percntilRDF() {
        String query, query2 = null;
        RdfStream rdfStream = null;
        CsparqlQueryResultProxy csparqlQueryResult = null;
        Logger logger = LoggerFactory.getLogger(Main.class);

        query = "REGISTER QUERY HelloWorld AS "
                + "PREFIX mc: <http://www.modaclouds.eu/ontologies/2013/2/monitoring#> "
                + "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
                + "SELECT ?s (COUNT(?o1) AS ?ok) (COUNT(?o) AS ?tot) ((COUNT(?o1) / COUNT(?o)) AS ?fr) "
                + "FROM STREAM <http://myexample.org/stream> [RANGE 10s STEP 10s] "
                + "WHERE { " +
                "{ " +
                "?s ?p ?o . " +
                "} " +
                "UNION " +
                "{ " +
                "?s ?p ?o1 " +
                "FILTER(?o1 > \"8\"^^xsd:integer) " +
                "} " +
                "} " +
                "GROUP BY ?s " +
                "HAVING (?fr > 0.1)";

        rdfStream = new PercentileStream("http://myexample.org/stream");

        engine.registerStream(rdfStream);
        final Thread thread = new Thread((Runnable) rdfStream);
        thread.start();

        try {
            csparqlQueryResult = engine.registerQuery(query, false);
            logger.debug("Query: {}", query);
            logger.debug("Query Start Time : {}", System.currentTimeMillis());
            csparqlQueryResult.addObserver(new ConsoleFormatter());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void staticKnowledge() {
        String query,queryOri, query2 = null;
        RdfStream rdfStream = null;
        CsparqlQueryResultProxy csparqlQueryResult = null;
        Logger logger = LoggerFactory.getLogger(Main.class);
        OntologyPrefix p = new OntologyPrefix();
        try {
           // engine.putStaticNamedModel("http://streamreasoning.org/roomConnection",
             //       CsparqlUtils.serializeRDFFile("examples_files/roomConnection.rdf"));

            engine.putStaticNamedModel("http://streamreasoning.org/roomConnection",
                    CsparqlUtils.serializeRDFFile("examples_files/OntoRDF.owl"));
            logger.info("SERIALIZOU O RDF");
        } catch (Exception e) {
            e.printStackTrace();
        }

        queryOri = "REGISTER QUERY staticKnowledge AS "
                + "PREFIX :<http://www.streamreasoning.org/ontologies/sr4ld2014-onto#> "
                + "SELECT ?p1 ?r1 "
                + "FROM STREAM <http://streamreasoning.org/streams/fb> [RANGE 1s STEP 1s] "
                + "FROM <http://streamreasoning.org/roomConnection> "
                + "WHERE { "
                + "?p :posts [ :who ?p1 ; :where ?r ] . "
                + "?r :isConnectedTo ?r1 . "
                + "} ";

        query = "REGISTER QUERY staticKnowledge AS "
                + "PREFIX :<http://www.streamreasoning.org/ontologies/sr4ld2014-onto#> "
                + "PREFIX iot: <"+prefix.getIotlite()+"> "
                + "SELECT ?g "
                + "FROM STREAM <http://streamreasoning.org/streams/fb> [RANGE 1s STEP 1s] "
                + "FROM <http://streamreasoning.org/roomConnection> "
                + "WHERE { "
                + "?p :posts ?post . " +
                "?post :who ?p1 ." +
                "?post :where ?r ."
                + "?r :isConnectedTo ?r1 . "
                +"?id iot:hasQuatityKind ?g ."
                + "} ";

        query2 = "REGISTER QUERY staticKnowledge AS "
                + "PREFIX :<http://www.streamreasoning.org/ontologies/sr4ld2014-onto#> "
                + "SELECT ?p1 ?r1 "
                + "FROM STREAM <http://streamreasoning.org/streams/fb> [RANGE 1s STEP 1s] "
                + "FROM <http://streamreasoning.org/roomConnection> "
                + "WHERE { "
                + "?p :posts ?post . " +
                "?post :who ?p1 ." +
                "?post :where ?r ."
                +"?r :isConnectedTo ?r1 . "
                + "} ";

        FacebookStreamer4RoomConnection fb = new FacebookStreamer4RoomConnection("http://streamreasoning.org/streams/fb",
                "http://www.streamreasoning.org/ontologies/sr4ld2014-onto#", 1000L);

        engine.registerStream(fb);
        Thread fbThread = new Thread(fb);
        fbThread.start();

        try {
            //csparqlQueryResult = engine.registerQuery(query, false);
            CsparqlQueryResultProxy c = engine.registerQuery(query, false);
            logger.debug("Query: {}", query);
            logger.debug("Query Start Time : {}", System.currentTimeMillis());
            c.addObserver(new ConsoleFormatter());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public static void socialRDF() {
        String query = null;
        RdfStream rdfStream = null;
        CsparqlQueryResultProxy csparqlQueryResult = null;
        Logger logger = LoggerFactory.getLogger(Main.class);
        String query1, query2, query3;

        logger.debug("QuemGostadoQue");
        query = "REGISTER QUERY QuemGostadoQue AS "
                + "PREFIX ex:<http://mycsparql.lsdi/> "
                + "SELECT ?s ?o "
                + "FROM STREAM <http://mycsparql.lsdi/stream> [RANGE 5s STEP 1s] "
                + "WHERE { ?s ex:likes ?o }";
        logger.debug("Quantos usuários gostam do mesmo obj");
        query1 = "REGISTER QUERY QuantosUsuariosLikeMesmoObj AS " +
                "PREFIX ex: <http://mycsparql.lsdi/> " +
                "SELECT ?o (count (?s) as ?contarUsuarios) " +
                "FROM STREAM <http://mycsparql.lsdi/stream> [RANGE 5s STEP 1s] " +
                "WHERE {?s ex:likes ?o} " +
                "GROUP BY ?o " +
                "ORDER BY DESC(?contarUsuarios)";

        logger.debug("Encontrar Formadores de Opniões");
        query2 = ""
                + "REGISTER QUERY EncontrarFormadoresdeOpn AS "
                + "PREFIX f: <http://larkc.eu/csparql/sparql/jena/ext#> "
                + "PREFIX ex: <http://mycsparql.lsdi/> "
                + "SELECT ?opinionMaker ?o (COUNT(?follower) AS ?n) "
                + "FROM STREAM <http://mycsparql.lsdi/stream> [RANGE 10s STEP 5s] "
                + "WHERE { "
                + "?opinionMaker ex:likes ?o . "
                + "?follower ex:likes ?o . "
                + "FILTER(?opinionMaker!=?follower)"
                + "FILTER (f:timestamp(?follower,ex:likes,?o) > f:timestamp(?opinionMaker,ex:likes,?o)) "
                + "} "
                + "GROUP BY ?opinionMaker ?o "
                + "HAVING (COUNT(?follower)>2)";

       // engine.putStaticNamedModel("http://streamreasoning.org/larkc/csparql/LBSMA-static-k.rdf", "http://streamreasoning.org/larkc/csparql/LBSMA-static-k.rdf");

        query3 = ""
                + "REGISTER QUERY StreamingAndExternalStaticRdfGraph AS "
                + "PREFIX f: <http://larkc.eu/csparql/sparql/jena/ext#> "
                + "PREFIX ex: <http://mycsparql.lsdi/>  "
                + "SELECT ?opinionMaker ?o (COUNT(?follower) AS ?n) "
                + "FROM STREAM <http://mycsparql.lsdi/stream> [RANGE 10s STEP 5s] "
                + "FROM <http://streamreasoning.org/larkc/csparql/LBSMA-static-k.rdf> "
                + "WHERE { "
                + "?opinionMaker ex:likes ?o . "
                + "?follower ex:follows ?opinionMaker . "
                + "?follower ex:likes ?o . "
                + "FILTER(?opinionMaker!=?follower)"
                + "FILTER (f:timestamp(?follower,ex:likes,?o) > f:timestamp(?opinionMaker,ex:likes,?o)) "
                + "} "
                + "GROUP BY ?opinionMaker ?o "
                + "HAVING (COUNT(?follower)>3)";

        rdfStream = new SocialRDFStreamTesteGenerator("http://mycsparql.lsdi/stream");
        engine.registerStream(rdfStream);

        final Thread thread = new Thread((Runnable) rdfStream);
        thread.start();

        try {
            csparqlQueryResult = engine.registerQuery(query, false);
            logger.debug("Query: {}", query);
            logger.debug("Query Start Time : {}", System.currentTimeMillis());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        csparqlQueryResult.addObserver(new Formatter());

        try {
            Thread.sleep(600000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        ((SocialRDFStreamTesteGenerator) rdfStream).stop();
        engine.unregisterStream(rdfStream.getIRI());
    }

    public static void cloudMonitoring() {
        String query = null;
        RdfStream rdfStream = null;
        CsparqlQueryResultProxy csparqlQueryResult = null;
        Logger logger = LoggerFactory.getLogger(Main.class);
        String query1, query2, query3;

        logger.debug("CLOUD_MONITORING_TEST example");
        engine.putStaticNamedModel("http://streamreasoning.org/modaclouds/ModaCloudsSK.rdf", "http://streamreasoning.org/modaclouds/ModaCloudsSK.rdf");
        query = "REGISTER QUERY HelloWorld AS "
                + "PREFIX mc: <http://www.modaclouds.eu/ontologies/2013/2/monitoring#> "
                + "SELECT * "
                + "FROM STREAM <http://myexample.org/stream> [RANGE 10s STEP 5s] "
                + "FROM <http://streamreasoning.org/modaclouds/ModaCloudsSK.rdf> "
                + "WHERE { " + "?m a mc:MySQL ; " + "mc:isIn ?vm . "
                + "?vm mc:exposes ?dc . " + "?dc mc:observes ?o . "
                + "?o mc:hasMonitoredMetric mc:CPUUtilization ; "
                + "mc:isAbout ?m ; " + "mc:hasValue ?v . " + "}";

        rdfStream = new CloudMonitoringRDFStreamTestGenerator(
                "http://myexample.org/stream");
        engine.registerStream(rdfStream);

        final Thread thread = new Thread((Runnable) rdfStream);
        thread.start();

        try {
            csparqlQueryResult = engine.registerQuery(query, false);
            logger.debug("Query: {}", query);
            logger.debug("Query Start Time : {}", System.currentTimeMillis());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        csparqlQueryResult.addObserver(new ConsoleFormatter());
        try {
            Thread.sleep(600000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }

    }
}//m
