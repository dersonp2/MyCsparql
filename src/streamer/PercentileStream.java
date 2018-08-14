package streamer;

import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.cep.api.RdfStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.UUID;

public class PercentileStream extends RdfStream implements Runnable  {

    /** The logger. */
    protected final Logger logger = LoggerFactory.getLogger(PercentileStream.class);

    private int c = 1;
    private boolean keepRunning = false;

    public PercentileStream(final String iri) {
        super(iri);
    }

    public void pleaseStop() {
        keepRunning = false;
    }

    @Override
    public void run() {
        keepRunning = true;
        while (keepRunning) {
            Random rnd = new Random();
            int n = rnd.nextInt(10);
//			final RdfQuadruple q = new RdfQuadruple(getIRI()+"/S",	getIRI()+"/P" + this.c, String.valueOf(n) + "^^http://www.w3.org/2001/XMLSchema#integer",System.currentTimeMillis());
            final RdfQuadruple q = new RdfQuadruple("urn:"+ UUID.randomUUID().toString(),	getIRI()+"/P" + this.c, String.valueOf(n) + "^^http://www.w3.org/2001/XMLSchema#integer",System.currentTimeMillis());

//			System.out.println(q);

            this.put(q);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.c++;
        }
    }
}