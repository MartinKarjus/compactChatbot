package util.contentreader.dataclasses;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class ContentOptions extends ContentBase{
    Map<String, List<String>> options;

    public ContentOptions(Map<String, List<String>> options) {
        this.options = options;
    }
}
