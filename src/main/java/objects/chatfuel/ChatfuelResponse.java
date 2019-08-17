package objects.chatfuel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ChatfuelResponse {
    @JsonProperty("set_attributes")
    Map<String, String> setUserAttributes;
    private List<ChatfuelMessage> messages;
}
