package WriteTxt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteTxtAll {
    public void setTimestamp (long timestamp) throws IOException {
        BufferedWriter bw = null;

        bw = new BufferedWriter(new FileWriter("teste/txtAll.txt", true));
        bw.write( ""+timestamp);
        bw.newLine();
        bw.flush();
    }
}
