package loaders;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    protected static List<String> readLinesFromBufferReader(BufferedReader br) {
        String line;
        List<String> lines = new ArrayList<String>();
        try {
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    protected static BufferedReader getBufferedReader(String filepath) {
        File file = new File(filepath);
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bufferedReader;
    }
}
