package Start;

import ConfigLog.ConfigLog;
import eu.larkc.csparql.core.engine.CsparqlEngineImpl;
import org.slf4j.Logger;

public class StartCsparql {
    public static StartCsparql instance = null;
    private static CsparqlEngineImpl engine = null;
    //Logger
    private Logger logger;

    public static StartCsparql getInstance() {
        if (instance == null) {
            instance = new StartCsparql();
        }
        return instance;
    }

    public CsparqlEngineImpl getEngine() {
        if (engine == null) {
            configLog();
            engine = new CsparqlEngineImpl();
            try {
                engine.initialize(true);
                logger.info("Start C-SPARQL");

            } catch (Exception e) {
                e.printStackTrace();
                logger.info("Error Starting C-SPARQL");
            }
        }
        return engine;
    }

    public void configLog() {
        logger = new ConfigLog().log(StartCsparql.class);
    }
}
