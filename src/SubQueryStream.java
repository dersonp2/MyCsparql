import Communication.Communication;
import Csparql.StaticModel;
import Model.OntologyPrefix;
import streamer.ParkingStream;
import streamer.SensorStream;

public class SubQueryStream {

    public static void main(String[] args) {
        new Communication().start();
        //SensorStream rdfStream = new SensorStream("http://mycsparql.lsdi/stream");
        //rdfStream.run();
        OntologyPrefix p = new OntologyPrefix();
        //ParkingStream parkingStream = new ParkingStream(p.getPk());
        //parkingStream.run();
    }
}
