package objects.chatfuel.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatfuelPayload {
    String template_type; //generic for galleries, media for videos/images/text, list for list of choices /
    String text;
    @JsonProperty("top_element_style")
    String topElementStyle; // anything other than large? g2 test and find out
    String url; //videos, images
    @JsonProperty("image_aspect_ratio")
    String imageAspectRatio; //square? cant find other options
    List<ChatfuelAttachmentElement> elements;
    List<ChatfuelButton> buttons;
}
