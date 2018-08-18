package Csparql;

import Communication.Communication;
import ConfigLog.ConfigLog;
import Start.StartCsparql;
import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.cep.api.RdfStream;
import eu.larkc.csparql.core.engine.CsparqlEngineImpl;
import org.slf4j.Logger;
import streamer.SocialRDFStreamTesteGenerator;

public class StreamCsparql {
    public static StreamCsparql instance = null;
    static RdfStream rdfStream = null;
    private static CsparqlEngineImpl engine = null;
    private static String iri = "http://mycsparql.lsdi/stream";
    private static Logger logger;

    public static StreamCsparql getInstance() {
        if (instance == null) {
            instance = new StreamCsparql();
            if (engine == null) {
                engine = StartCsparql.getInstance().getEngine();
                logger = new ConfigLog().log(Communication.class);
                rdfStream = new RdfStream(iri);
                engine.registerStream(rdfStream);
            }
        }
        return instance;
    }

    public void setStream(RdfQuadruple rdfq) {
        this.rdfStream.put(rdfq);
    }
}
