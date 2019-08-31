package objects.chatfuel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import objects.chatfuel.response.ChatfuelMessage;
import objects.shared.ContentRequestResponse;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatfuelResponse extends ContentRequestResponse {
    @JsonProperty("set_attributes")
    Map<String, String> setUserAttributes;
    private List<ChatfuelMessage> messages;
}
