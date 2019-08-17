package objects.shared;

import lombok.Data;
import objects.chatfuel.ChatfuelResponse;
import objects.skype.SkypeResponse;

@Data
public class ContentByPlatform {
    private ChatfuelResponse chatfuel;
    private SkypeResponse skype;
}
