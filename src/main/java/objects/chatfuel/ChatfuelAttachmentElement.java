package objects.chatfuel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ChatfuelAttachmentElement {
    @JsonProperty("media_type")
    String mediaType;
    String url;
    @JsonProperty("image_url")
    String imageUrl;
    String subtitle;
    String title;
    @JsonProperty("default_action")
    ChatfuelDefaultAction defaultAction;
    List<ChatfuelButton> buttons;

}
