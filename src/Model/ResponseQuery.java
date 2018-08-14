package Model;

import eu.larkc.csparql.common.RDFTable;

import java.util.Observable;

public class ResponseQuery {
    private java.util.Observable observable;
    private RDFTable object;

    public Observable getObservable() {
        return observable;
    }

    public void setObservable(Observable observable) {
        this.observable = observable;
    }

    public RDFTable getObject() {
        return object;
    }

    public void setObject(RDFTable object) {
        this.object = object;
    }
}
