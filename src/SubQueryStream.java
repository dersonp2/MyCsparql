import Communication.Communication;
import streamer.SensorStream;

public class SubQueryStream {

    public static void main(String[] args) {
        new Communication().start();
        SensorStream rdfStream = new SensorStream("http://mycsparql.lsdi/stream");
        rdfStream.run();
    }
}
