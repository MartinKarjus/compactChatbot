package util.contentreader.dataclasses;

import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class ContentHolder {
    Iterator iterator;
    private List<List<ContentBase>> currentContent = new ArrayList<>();
    private List<List<String>> linesLeft;


    public ContentHolder(List<List<String>> linesLeft) {
        this.linesLeft = linesLeft;
        iterator = linesLeft.listIterator();
    }
}
