package objects.chatfuel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChatfuelDefaultAction {
    String type; //always web_url ?
    String url;
    @JsonProperty("messenger_extensions")
    boolean messengerExtensions;
}
