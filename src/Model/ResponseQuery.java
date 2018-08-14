package Model;

import java.util.Observable;

public class ResponseQuery {
    java.util.Observable o;
    Object arg;

    public Observable getO() {
        return o;
    }

    public void setO(Observable o) {
        this.o = o;
    }

    public Object getArg() {
        return arg;
    }

    public void setArg(Object arg) {
        this.arg = arg;
    }
}
