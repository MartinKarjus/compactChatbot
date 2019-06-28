package objects.chatfuel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ChatfuelMessage {
    private ChatfuelAttachment attachment;
    @JsonProperty("quick_replies")
    private List<ChatFuelQuickReply> quickReplies;
}
