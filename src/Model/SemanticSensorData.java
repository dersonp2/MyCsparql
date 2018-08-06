package Model;

import eu.larkc.csparql.cep.api.RdfQuadruple;

//RODOLFO
public class SemanticSensorData extends SensorData{
    private RdfQuadruple[] rdfQuadruple;

    public RdfQuadruple[] getRdfQuadruple() {
        return rdfQuadruple;
    }

    public void setRdfQuadruple(RdfQuadruple[] rdfQuadruple) {
        this.rdfQuadruple = rdfQuadruple;
    }
}
