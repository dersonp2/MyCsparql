package streamer;

import eu.larkc.csparql.cep.api.RdfQuadruple;
import eu.larkc.csparql.cep.api.RdfStream;

import java.util.Random;

public class FacebookStreamer4RoomConnection extends RdfStream implements Runnable {

    private long sleepTime;
    private String baseUri;
    private boolean keepRunning;


    public FacebookStreamer4RoomConnection(String iri, String baseUri, long sleepTime) {
        super(iri);
        this.sleepTime = sleepTime;
        this.baseUri = baseUri;
    }

    @Override
    public void run() {

        keepRunning = true;
        while (keepRunning == true) {

            try {
                Random random = new Random();
                int senderIndex; //remetente
                int subjectIndex; //topico, sujeito
                int postIndex; //post
                int numberOfPerson; //número de pessoa

                numberOfPerson = random.nextInt(3);
                senderIndex = random.nextInt(5);
                subjectIndex = random.nextInt(5);
                postIndex = random.nextInt(Integer.MAX_VALUE);

                RdfQuadruple q = new RdfQuadruple(baseUri + "person" + senderIndex,
                        baseUri + "posts", baseUri + "post" + postIndex, System.currentTimeMillis());
                this.put(q);

                if (numberOfPerson < 2) {
                    q = new RdfQuadruple(baseUri + "post" + postIndex, baseUri + "who",
                            baseUri + "person" + subjectIndex, System.currentTimeMillis());
                    this.put(q);
                } else {
                    q = new RdfQuadruple(baseUri + "post" + postIndex, baseUri + "who",
                            baseUri + "person" + senderIndex, System.currentTimeMillis());
                    this.put(q);
                    q = new RdfQuadruple(baseUri + "post" + postIndex, baseUri + "who",
                            baseUri + "person" + subjectIndex, System.currentTimeMillis());
                    this.put(q);
                }
                q = new RdfQuadruple(baseUri + "post" + postIndex, baseUri + "where",
                        baseUri + "room", System.currentTimeMillis());
                this.put(q);

                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
