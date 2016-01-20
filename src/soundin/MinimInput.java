package soundin;

import java.io.IOException;
import java.io.InputStream;

public class MinimInput {
    String sketchPath(String fileName) {
        return "";
    }
    InputStream createInput(String fileName) {
        return new InputStream() {
            public int read() throws IOException {
                return 0;
            }
        };
    };
}