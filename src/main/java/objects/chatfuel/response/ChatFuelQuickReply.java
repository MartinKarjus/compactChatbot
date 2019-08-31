package objects.chatfuel.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatFuelQuickReply {
    String title;
    String url;
    String type; //can be json_plugin_url
    @JsonProperty("text_attribute_name")
    String textAttributeName; //todo can use for initializing user information through questions
    @JsonProperty("process_text_by_ai")
    boolean processTextByAi;
    @JsonProperty("set_attributes")
    Map<String, String> setUserAttributes;
    @JsonProperty("block_names")
    List<String> blockNames;
}
