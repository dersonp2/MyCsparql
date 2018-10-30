package WriteTxt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteTxtCS {
    public void setTimestamp (long timestamp) throws IOException {
        BufferedWriter bw = null;

        bw = new BufferedWriter(new FileWriter("teste/txtCS.txt", true));
        bw.write( ""+timestamp);
        bw.newLine();
        bw.flush();
    }
}
