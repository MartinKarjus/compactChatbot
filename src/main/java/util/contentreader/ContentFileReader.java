package util.contentreader;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContentFileReader {


    public static void main(String[] args) throws IOException {
        new ContentFileReader().readFile("/botcontent/Chatbot flow new - Sheet1.csv");
    }

    private InputStream getInputStream(String filename) {
        Class clazz = ContentFileReader.class;
        return clazz.getResourceAsStream(filename);
    }

    public List<List<String>> readFile(String fileName) throws IOException {

        List<List<String>> records = new ArrayList<List<String>>();
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(getInputStream(fileName), StandardCharsets.UTF_8));) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                List<String> l = new ArrayList<>(Arrays.asList(values));
                l.removeAll(Arrays.asList("", null));
                if(l.size() > 0) {
                    records.add(l);
                }
            }
        }

        return records;
    }

}
