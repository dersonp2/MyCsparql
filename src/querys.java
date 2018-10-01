import Model.OntologyPrefix;

public class querys {
    OntologyPrefix p = new OntologyPrefix();

    //obter o resultado, tipo e a localização
    String query2 = "REGISTER QUERY MhubSemantic AS "
            + "PREFIX iot-lite:<"+p.getIotlite()+"> "
            + "PREFIX sosa:<" + p.getSosa() + "> "
            + "SELECT ?result ?t ?l "
            + "FROM STREAM <http://mycsparql.lsdi/stream> [RANGE 10s STEP 5s] "
            + "FROM <http://streamreasoning.org/roomConnection> "
            + "WHERE { "
            + "?id sosa:hasResult ?result ."
            + "?id iot-lite:hasLocation ?l . "
            + "?id iot-lite:hasQuantityKind ?t . "
            + "} ";

    String query3 = "REGISTER QUERY MhubSemantic AS "
            + "PREFIX iot-lite:<"+p.getIotlite()+"> "
            + "PREFIX sosa:<" + p.getSosa() + "> "
            + "SELECT ?result  ?l "
            + "FROM STREAM <http://mycsparql.lsdi/stream> [RANGE 10s STEP 5s] "
            + "FROM <http://streamreasoning.org/roomConnection> "
            + "WHERE { "
            + "?id sosa:hasResult ?result ."
            + "?id iot-lite:hasLocation ?l . "
            + "?id iot-lite:hasQuantityKind iot-lite:Temperature . "
            + "} ";

    String query4 = "REGISTER QUERY MhubSemantic AS "
            + "PREFIX iot-lite:<"+p.getIotlite()+"> "
            + "PREFIX sosa:<" + p.getSosa() + "> "
            + "PREFIX geo:<" + p.getGeo() + "> "
            + "SELECT ?result  "
            + "FROM STREAM <http://mycsparql.lsdi/stream> [RANGE 10s STEP 5s] "
            + "FROM <http://streamreasoning.org/roomConnection> "
            + "WHERE { "
            + "?id sosa:hasResult ?result ."
            + "?id iot-lite:hasLocation geo:Lsdi . "
            + "?id iot-lite:hasQuantityKind iot-lite:Temperature . "
            + "} ";

    String query5 = "REGISTER QUERY MhubSemantic AS "
            + "PREFIX iot-lite:<"+p.getIotlite()+"> "
            + "PREFIX sosa:<" + p.getSosa() + "> "
            + "PREFIX geo:<" + p.getGeo() + "> "
            + "SELECT ?result  "
            + "FROM STREAM <http://mycsparql.lsdi/stream> [RANGE 10s STEP 5s] "
            + "FROM <http://streamreasoning.org/roomConnection> "
            + "WHERE { "
            + "?id sosa:hasResult ?result ."
            + "?id iot-lite:hasLocation geo:Lsdi . "
            + "?id iot-lite:hasQuantityKind iot-lite:Temperature . "
            + "FILTER(?result > \"26\"^^xsd:integer) "
            + "} ";

    String querySSD = "REGISTER QUERY MhubSemantic AS "
            + "PREFIX iot-lite:<"+p.getIotlite()+"> "
            + "PREFIX sosa:<" + p.getSosa() + "> "
            + "PREFIX geo:<" + p.getGeo() + "> "
            + "SELECT ?result ?l "
            + "FROM STREAM <http://mycsparql.lsdi/stream> [RANGE 10s STEP 5s] "
            + "FROM <http://mycsparql.lsdi/knowlegdeBase> "
            + "WHERE { "
            + "?id sosa:hasResult ?result ."
            + "?id iot-lite:hasLocation ?l . "
            + "?id iot-lite:hasQuantityKind iot-lite:Temperature . "
            + "} ";


    String query6 = "REGISTER QUERY MhubSemantic AS "
            + "PREFIX iot-lite:<"+p.getIotlite()+"> "
            + "PREFIX sosa:<" + p.getSosa() + "> "
            + "PREFIX geo:<" + p.getGeo() + "> "
            + "SELECT ?t ?result "
            + "FROM STREAM <http://mycsparql.lsdi/stream> [RANGE 10s STEP 5s] "
            + "FROM <http://streamreasoning.org/roomConnection> "
            + "WHERE { "
            + "?id sosa:hasResult ?result ."
            + "?id iot-lite:hasLocation geo:Lsdi . "
            + "?id iot-lite:hasQuantityKind ?t . "
            + "} ";

    String query7 = "REGISTER QUERY MhubSemantic AS "
            + "PREFIX iot-lite:<"+p.getIotlite()+"> "
            + "PREFIX sosa:<" + p.getSosa() + "> "
            + "PREFIX geo:<" + p.getGeo() + "> "
            + "SELECT ?result ?l "
            + "FROM STREAM <http://mycsparql.lsdi/stream> [RANGE 10s STEP 5s] "
            + "FROM <http://mycsparql.lsdi/knowlegdeBase> "
            + "WHERE { "
            + "?id sosa:hasResult ?result ."
            + "?id iot-lite:hasLocation ?l . "
            + "?id iot-lite:hasQuantityKind iot-lite:Humidity . "
            + "} ";

    //Parking Smart
    String queryp1 = "REGISTER QUERY MhubSemantic AS "
            + "PREFIX pk:<" + p.getPk() + "> "
            + "SELECT ?s ?p ?obj "
            + "FROM STREAM <"+ p.getPk() +"> [RANGE 10s STEP 5s] "
            + "WHERE { "
            + "?s pk:hasState pk:Busy "
            + "} ";

    String queryp2 = "REGISTER QUERY MhubSemantic AS "
            + "PREFIX pk:<" + p.getPk() + "> "
            + "SELECT ?s ?vehicle "
            + "FROM STREAM <"+ p.getPk() +"> [RANGE 10s STEP 5s] "
            + "FROM <http://streamreasoning.org/roomConnection> "
            + "WHERE { "
            + "?s pk:hasState pk:Free ."
            + "?s pk:hasVehicleSpace pk:CarSpace "
            + "} ";

    String queryp3 = "REGISTER QUERY MhubSemantic AS "
            + "PREFIX pk:<" + p.getPk() + "> "
            + "SELECT ?s "
            + "FROM STREAM <"+ p.getPk() +"> [RANGE 5s STEP 1s] "
            + "FROM <http://streamreasoning.org/roomConnection> "
            + "WHERE { "
            + "?s pk:hasState pk:Free ."
            + "?s pk:hasVehicleSpace pk:MotorcycleSpace "
            + "} ";
}
