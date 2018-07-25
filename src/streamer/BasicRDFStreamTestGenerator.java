package streamer;

import Csparql.StreamCsparql;
import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.cep.api.RdfStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicRDFStreamTestGenerator extends RdfStream implements Runnable {

    /** The logger. */
    protected final Logger logger = LoggerFactory.getLogger(BasicRDFStreamTestGenerator.class);

    public int c = 1;
    private boolean keepRunning = false;

    public BasicRDFStreamTestGenerator(String iri) {
        super(iri);
    }

    public void stop(){
    keepRunning = false;
    logger.debug("Fluxo Parado");
    }

    @Override
    public void run() {
        keepRunning = true;

        while (keepRunning==true){

            final RdfQuadruple rdfQuadruple =  new RdfQuadruple(getIRI()+"/Temperatura " +this.c+" ",getIRI()+"/P"+this.c+"",getIRI()+"O "+this.c+" ",System.currentTimeMillis());

            if(c%10==0) logger.info(c+ " triplas transmitidas até agora");
            //this.put(rdfQuadruple);
            //StreamCsparql.getInstance().setStream(rdfQuadruple);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info(String.valueOf(rdfQuadruple));
            this.c++;
        }
    }

}
