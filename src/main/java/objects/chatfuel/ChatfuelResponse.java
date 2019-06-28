package objects.chatfuel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class ChatfuelResponse {
    @JsonProperty("set_attributes")
    Map<String, String> setUserAttributes;
    private List<ChatfuelMessage> messages;
}
