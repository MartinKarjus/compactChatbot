package util.contentreader.dataclasses;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Message extends ContentBase{
    String name;
    List<SubType> subTypeList = new ArrayList<>();
}
