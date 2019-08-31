package objects.chatfuel.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatfuelMessage {
    private ChatfuelAttachment attachment;
    @JsonProperty("quick_replies")
    private List<ChatFuelQuickReply> quickReplies;
    String text;
}
