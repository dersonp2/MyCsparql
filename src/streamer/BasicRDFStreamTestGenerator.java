package streamer;

import Csparql.StreamCsparql;
import Model.OntologyPrefix;
import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.cep.api.RdfStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicRDFStreamTestGenerator extends RdfStream implements Runnable {
    RdfQuadruple q1, q2, q3, q4;
    OntologyPrefix onto = new OntologyPrefix();
    /**
     * The logger.
     */
    protected final Logger logger = LoggerFactory.getLogger(BasicRDFStreamTestGenerator.class);

    public int c = 1;
    private boolean keepRunning = false;
    private long tempTS;
    private int result=0;

    public BasicRDFStreamTestGenerator(String iri) {
        super(iri);
    }

    public void stop() {
        keepRunning = false;
        logger.debug("Fluxo Parado");
    }

    @Override
    public void run() {
        keepRunning = true;

        while (keepRunning == true) {
            result+=13;
            //final RdfQuadruple rdfQuadruple =  new RdfQuadruple(getIRI()+"/Temperatura " +this.c+" ",getIRI()+"/P"+this.c+"",getIRI()+"O "+this.c+" ",System.currentTimeMillis());
            tempTS = System.currentTimeMillis();
            q1 = new RdfQuadruple(onto.getIotlite() + "FFE1-0000-1000-8000-00805F9B34FB", onto.getRdf()
                    + "Type", onto.getSsn() + "SensingDevice", tempTS);
            this.put(q1);
            q2 = new RdfQuadruple(onto.getIotlite() + "FFE1-0000-1000-8000-00805F9B34FB", onto.getIotlite()
                    + "hasUnit", onto.getIotlite() + "Celsius", tempTS);
            this.put(q2);
            q3 = new RdfQuadruple(onto.getIotlite() + "FFE1-0000-1000-8000-00805F9B34FB", onto.getIotlite()
                    + "hasQuatityKind", onto.getIotlite() + "Temperature", tempTS);
            this.put(q3);
            q4 = new RdfQuadruple(onto.getIotlite() + "FFE1-0000-1000-8000-00805F9B34FB", onto.getSosa()
                    + "hasResult", String.valueOf(result) + "^^http://www.w3.org/2001/XMLSchema#integer", tempTS);
            this.put(q4);
            if (c % 10 == 0) logger.info(c + " triplas transmitidas até agora");
            //this.put(rdfQuadruple);
            //StreamCsparql.getInstance().setStream(rdfQuadruple);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //logger.info(String.valueOf());
            this.c++;
        }
    }

    public void pleaseStop() {
        keepRunning = false;
    }

}
