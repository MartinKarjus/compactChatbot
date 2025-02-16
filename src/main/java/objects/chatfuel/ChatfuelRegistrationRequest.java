package objects.chatfuel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;



@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatfuelRegistrationRequest {
    @JsonProperty("user_id")
    private String chatfuelUserId;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("token")
    private String token;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("team_id")
    private Long teamId;//todo maybe could change to nr but need to check what kind of data chatfuel keeps its variables in

}
