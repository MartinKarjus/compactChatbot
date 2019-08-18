package bot.chatfuelapi;

import objects.chatfuel.ChatfuelBroadcastResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;

@Component
public class ChatfuelBroadcaster {
    //todo For chatfuel max broadcasts per second is 25, we need to use scheduling(taskexecutor in spring?) to prevent overload
    // (we make a fairly big number of broadcasts per user and they could happen at the same time)

    @Autowired
    public Environment env;

    private StringBuilder stringBuilder = new StringBuilder();


    public void broadcastBlockToUser(String chatfuelUserId, String blockName, Map<String, String> userAttributes) {
        String url = makeUrl(chatfuelUserId, blockName, userAttributes);

        // application/json

        RestTemplate restTemplate = new RestTemplate();
        String payload = "{}";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<>(payload, headers);
        System.out.println("url is:" + url);
        String response = restTemplate.postForObject(url, request, String.class);
        System.out.println("response:\n" + response);
//        System.out.println(response.getResult());
//        System.out.println(response.isSuccess());
//        String response =
//                template.exchange("url", HttpMethod.POST, requestEntity,
//                        String.class);
    }

    private String makeUrl(String userId, String blockName, Map<String, String> userAttributes) {
        stringBuilder.setLength(0); //clear last url
        stringBuilder
                .append("https://api.chatfuel.com/bots/")
                .append(env.getProperty("bot_id"))
                .append("/users/")
                .append(userId)
                .append("/send?chatfuel_token=")
                .append(env.getProperty("chatfuel_token"))
                .append("&chatfuel_message_tag=")
                .append(env.getProperty("chatfuel_message_tag"))
                .append("&chatfuel_block_name=")
                .append(blockName);

        if (userAttributes != null) {
            for (Map.Entry<String, String> entry : userAttributes.entrySet()) {
                if(entry.getKey().startsWith("chatfuel")) {
                    throw new IllegalArgumentException("Chatfuel user attributes can't start with 'chatfuel' (reserved value by chatfuel)");
                }
                stringBuilder
                        .append("&")
                        .append(entry.getKey())
                        .append("=")
                        .append(entry.getValue());
            }
        }

        return stringBuilder.toString();
    }


}
