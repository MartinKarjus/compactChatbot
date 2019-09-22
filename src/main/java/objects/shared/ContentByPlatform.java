package objects.shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.util.Pair;
import lombok.Data;
import objects.chatfuel.ChatfuelResponse;
import objects.skype.SkypeResponse;

import java.util.HashMap;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentByPlatform {
    private ChatfuelResponse chatfuelResponse = new ChatfuelResponse();
    private SkypeResponse skypeResponse;
    private Double waitTime;
    private Pair<Double, Double> writeTime;
    private HashMap<Double, Double> answerIdToPoints;
    private Double points;
    private List<Pair<String, String>> attributes;
    private boolean isFork = false;

}
