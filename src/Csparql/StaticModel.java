package Csparql;

import ConfigLog.ConfigLog;
import Start.StartCsparql;
import com.hp.hpl.jena.rdf.model.Model;
import eu.larkc.csparql.common.utils.CsparqlUtils;
import eu.larkc.csparql.core.engine.CsparqlEngine;
import org.slf4j.Logger;

public class StaticModel {
    private static CsparqlEngine engine = null;
    //Logger
    private Logger logger = null;

    public StaticModel() {
        logger = new ConfigLog().log(StaticModel.class);
    }

    public void putStaticNamedModel(String var1, String var2) {
        engine = StartCsparql.getInstance().getEngine();
        try {
            //engine.putStaticNamedModel(var1, var2); Modificar depois
            engine.putStaticNamedModel(var1, CsparqlUtils.serializeRDFFile(var2));
            engine.putStaticNamedModel("http://mycsparql.lsdi/smartParking", CsparqlUtils.serializeRDFFile(
                    "examples_files/ParkingRDF.owl"));
            //engine.putStaticNamedModel("http://mycsparql.lsdi/smartParking", CsparqlUtils.serializeRDFFile("examples_files/ParkingRDF.owl"));
            logger.info("Serializou RDF");
        }catch (Exception e){
            System.out.println("Error putStaticNamedModel");
            e.printStackTrace();
        }
    }
    public void putStaticJenaModel(String var1,Model model){
        engine = StartCsparql.getInstance().getEngine();
        try {
            engine.putStaticNamedModel(var1, CsparqlUtils.serializeJenaModel(model));
        } catch (Exception e) {
            System.out.println("Error putStaticJenaModel");
            e.printStackTrace();
        }
    }

    private void startCsparql() {
        engine = StartCsparql.getInstance().getEngine();
    }
}
