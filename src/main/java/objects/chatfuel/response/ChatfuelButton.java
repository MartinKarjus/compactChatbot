package objects.chatfuel.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatfuelButton {
    String title;
    @JsonProperty("block_names")
    List<String> blockNames;
    String type; //weburl/json_plugin_url/show_block/element_share
    String url;
    @JsonProperty("set_attributes")
    Map<String, String> setUserAttributes; // (show_block  or json_plugin_url  only)

    //String phoneNumber; dont think we need to make calls

    //type element_share allows users to share bot message with their friends, probably dont need
}
