package objects.chatfuel.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatfuelDefaultAction {
    String type; //always web_url ?
    String url;
    @JsonProperty("messenger_extensions")
    boolean messengerExtensions;
}
