package bot.temp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import objects.chatfuel.ChatfuelResponse;
import objects.shared.ContentByPlatform;
import objects.shared.ContentRequestResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ChatfuelFileContentReader {

    public String getContentAsJson(String filename) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(getContentByPlatform(filename));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ContentByPlatform getContentByPlatform(String filename) {
        ContentByPlatform content = new ContentByPlatform();
        try {
            content.setChatfuelResponse(getResponse(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public ChatfuelResponse getResponse(String fileName) throws IOException {
        ChatfuelFileContentReader main = new ChatfuelFileContentReader();

        //String fileName = "/json/contentForTesting/chatfuelJsonExample.json";

        Class clazz = ChatfuelFileContentReader.class;
        InputStream inputStream = clazz.getResourceAsStream(fileName);
        String data = main.readFromInputStream(inputStream);


        return castToObj(data);
    }

    private ChatfuelResponse castToObj(String json) throws IOException {
        return new ObjectMapper().readValue(json, ChatfuelResponse.class);
    }

    private String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

}
