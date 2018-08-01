package Csparql;

import Communication.Communication;
import ConfigLog.ConfigLog;
import Start.StartCsparql;
import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.cep.api.RdfStream;
import eu.larkc.csparql.core.engine.CsparqlEngineImpl;
import org.slf4j.Logger;

public class StreamCsparql {
    public static StreamCsparql instance = null;
    static RdfStream rdfStream = null;
    private static CsparqlEngineImpl engine = null;
    private  static String iri = "http://lsdi.ufma.br/stream";
    private static Logger logger;

    public static StreamCsparql getInstance() {
        if (instance == null) {
            instance = new StreamCsparql();
            if (engine == null) {
                engine = StartCsparql.getInstance().getEngine();
                logger = new ConfigLog().log(Communication.class);
                logger.info("Iniciou o Stream do Csparql");
                rdfStream = new RdfStream(iri);
            }
        }
        return instance;
    }

    public void setStream(RdfQuadruple rdfq) {
        //startCsparql();
        logger.info("Registrou o fluxo");
        this.rdfStream.put(rdfq);
    }
}
