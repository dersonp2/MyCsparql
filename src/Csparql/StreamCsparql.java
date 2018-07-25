package Csparql;

import Start.StartCsparql;
import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.cep.api.RdfStream;
import eu.larkc.csparql.core.engine.CsparqlEngineImpl;

public class StreamCsparql {
    public static StreamCsparql instance = null;
    RdfStream rdfStream = null;
    private static CsparqlEngineImpl engine = null;
    private  String iri = "http://lsdi.ufma.br/stream";
    public static StreamCsparql getInstance() {
        if (instance == null) {
            instance = new StreamCsparql();
            if (engine == null) {
                engine = StartCsparql.getInstance().getEngine();
            }
        }
        return instance;
    }

    public void setStream(RdfQuadruple rdfq) {
        //startCsparql();
        this.rdfStream = new RdfStream(iri);
        this.rdfStream.put(rdfq);
        engine.registerStream(this.rdfStream);

    }
}
