package util.contentreader.dataclasses;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Choice extends ContentBase{
    HashMap<Integer, String> choices = new HashMap<>();
    HashMap<Integer, List<ContentBase>> answers = new HashMap();
    Long points;
}
