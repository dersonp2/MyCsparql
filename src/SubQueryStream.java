import Communication.Communication;

public class SubQueryStream {

    private static String TOPIC_STREAM = "Stream";
    private static String TOPIC_QUERY = "QueryCSparql";

    public static void main(String[] args) {
        Communication c = new Communication();
        c.start();
    }
}
