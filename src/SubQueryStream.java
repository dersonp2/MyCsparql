import Communication.Communication;
import Model.ResponseQuery;
import com.google.gson.Gson;

public class SubQueryStream {

    private static String TOPIC_STREAM = "Stream";
    private static String TOPIC_QUERY = "QueryCSparql";
    static java.util.Observable o = null;
    static Object arg = "x";


    public static void main(String[] args) {

        ResponseQuery rq = new ResponseQuery();
        rq.setO(o);
        rq.setArg(arg);

        Gson gson = new Gson();
        String m = gson.toJson(rq);
        System.out.println(m);
        //Communication c = new Communication();
        //c.start();

    }
}
