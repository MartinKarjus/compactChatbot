package util.contentreader;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class FolderReader {

    public List<String> getAllContentFileNames() {
        List<String> contentFiles = new ArrayList<>();

        File[] file;


        try {
            file = (new File(new FolderReader().getClass().getResource("/botcontent").toURI())).listFiles();
            for (File file1 : file) {
                //System.out.println(file1.getName());
                contentFiles.add(file1.getName());
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        return contentFiles;
    }
}
