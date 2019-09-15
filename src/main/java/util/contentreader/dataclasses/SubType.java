package util.contentreader.dataclasses;


import javafx.util.Pair;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.lang.reflect.Field;


@EqualsAndHashCode(callSuper = true)
@Data
public class SubType extends ContentBase {
    String text;
    String imageUrl;
    String videoUrl;
    String leadTo;
    String weblinkUrl;
    Double waitTime;
    Pair<Double, Double> writeTime;
    Pair<String, String> attribute;
    Double points;

    @Override
    public String toString() {

        Field[] fields = this.getClass().getDeclaredFields();

        String s = "";
        s += "SubType:(";
        for (Field field : fields) {
            String name = field.getName();
            field.setAccessible(true);
            try {
                if (field.get(this) != null) {
                    if (s.length() > 9) {
                        s += ", ";
                    }
                    s += name + "=" + field.get(this);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        s += ")";

        return s;
    }
}
