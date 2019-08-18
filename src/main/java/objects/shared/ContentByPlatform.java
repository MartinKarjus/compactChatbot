package objects.shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import objects.chatfuel.ChatfuelResponse;
import objects.skype.SkypeResponse;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentByPlatform {
    private ChatfuelResponse chatfuelResponse;
    private SkypeResponse skypeResponse;
}
