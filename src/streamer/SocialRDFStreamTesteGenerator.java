package streamer;

import Csparql.StreamCsparql;
import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.cep.api.RdfStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocialRDFStreamTesteGenerator extends RdfStream implements Runnable {

    protected final Logger logger = LoggerFactory.getLogger(SocialRDFStreamTesteGenerator.class);

    private int c = 1;
    private int ct = 1;
    private boolean keepRunnig = false;

    private String streamIri = "http://www.modaclouds.eu/ontologies/2013/2/monitoring#";
    public SocialRDFStreamTesteGenerator(String iri) {
        super(iri);
    }

    public void stop() {
        keepRunnig = false;
    }

    @Override
    public void run() {

        keepRunnig = true;

        while (keepRunnig) {
            final RdfQuadruple rdfQuadruple = new RdfQuadruple(getIRI() + "/user" + this.c,
                    streamIri+"http://mycsparql.lsdi/likes", "http://mycsparql.lsdi/Obj" + this.c, System.currentTimeMillis());

            this.put(rdfQuadruple);
            ct++;
            double n = Math.random()*5;

            for (int i = 0; i<n; i++){

                final RdfQuadruple rdfQuadruple2 = new RdfQuadruple(streamIri+"/user" + this.c+i,
                        "http://mycsparql.lsdi/likes", "http://mycsparql.lsdi/Obj" + this.c, System.currentTimeMillis());
                this.put(rdfQuadruple2);
                StreamCsparql.getInstance().setStream(rdfQuadruple2);
                    logger.info(rdfQuadruple2.toString());

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ct++;

            }//For

            if(c%10==0) logger.info(ct+ " triplas transmitidas até agora");


            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.c++;

        }//while

    }//run
}//main
