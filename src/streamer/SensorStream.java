package streamer;

import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.cep.api.RdfStream;

public class SensorStream extends RdfStream implements Runnable{
    private String streamIri = "www.lsdi.ufma.br/ontolLsdi#";
    static String dc = " http://purl.org/dc/elements/1.1/";
    String dcterms = "http://purl.org/dc/terms/";
    String foaf = "http://xmlns.com/foaf/0.1/";
    String geo = "http://www.w3.org/2003/01/geo/wgs84_pos#";
    static String iotlite ="http://purl.oclc.org/NET/UNIS/fiware/iot-lite#";
    String m3lite ="http://purl.org/iot/vocab/m3-lite#";
    String owl ="http://www.w3.org/2002/07/owl#" ;
    String qu ="http://purl.org/NET/ssnx/qu/qu#" ;
    String qurec20= "http://purl.org/NET/ssnx/qu/qu-rec20#";
    String rdf ="http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    String rdfs = "http://www.w3.org/2000/01/rdf-schema#" ;
    String skos = "http://www.w3.org/2004/02/skos/core#" ;
    String sosa = "http://www.w3.org/ns/sosa/" ;
    static String ssn ="http://purl.oclc.org/NET/ssnx/ssn#";
    String time = "http://www.w3.org/2006/time#" ;
    String vann = "http://purl.org/vocab/vann/" ;
    String voaf = "http://purl.org/vocommons/voaf#";
    String xml ="http://www.w3.org/XML/1998/namespace";
    String xsd ="http://www.w3.org/2001/XMLSchema#" ;

    public SensorStream(String iri) {
        super(iri);
    }

    @Override
    public void run() {
        try {
            RdfQuadruple q;
            long tempTS;
            Thread.sleep(1000);

            //Primeira Observação
            tempTS = System.currentTimeMillis();
            q = new RdfQuadruple("Temperature",iotlite+":hasType",ssn+"SensingDevice", System.currentTimeMillis());
            this.put(q);
            q = new  RdfQuadruple("Temperature",iotlite+":hasUnit",iotlite+"Celsius", System.currentTimeMillis());
            this.put(q);
            q = new RdfQuadruple("Temperature",iotlite+":hasQuatityKind",iotlite+"TemperatureKind", System.currentTimeMillis());
            this.put(q);
            System.out.println("First Observation: " + System.currentTimeMillis());

            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
