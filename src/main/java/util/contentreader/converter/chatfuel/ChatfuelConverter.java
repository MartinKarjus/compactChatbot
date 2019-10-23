package util.contentreader.converter.chatfuel;

import javafx.util.Pair;
import objects.chatfuel.ChatfuelResponse;
import objects.chatfuel.response.ChatfuelButton;
import objects.dbentities.Question;
import objects.shared.ContentByPlatform;
import util.contentreader.dataclasses.Choice;

import java.util.HashMap;

public class ChatfuelConverter {

    public static final String JSON_PLUGIN_URL = "json_plugin_url";
    public static final String ADDRESS = "http://89.235.212.13:8080";
    public static final String API_CHATFUEL_ANSWER = "/api/chatfuel/answer";

    private ChatfuelContentConstructor constructor = new ChatfuelContentConstructor();


    public void addImage(ChatfuelResponse chatfuelResponse, String imageUrl) {
        constructor.makeImageElement(chatfuelResponse);

        constructor.getLastMessage(chatfuelResponse).getAttachment().getPayload().setUrl(imageUrl);
    }

    public void addText(ChatfuelResponse chatfuelResponse, String text) {
        constructor.makeTextElement(chatfuelResponse);

        constructor.getLastMessage(chatfuelResponse).setText(text);

    }

    public void addVideo(ChatfuelResponse chatfuelResponse, String videoUrl) {
        constructor.makeVideoElement(chatfuelResponse);

        constructor.getLastMessage(chatfuelResponse).getAttachment().getPayload().setUrl(videoUrl);
    }


    public void addWebLink(ChatfuelResponse chatfuelResponse, Pair<String, String> weblinkUrl) {
        constructor.makeWebLinkElement(chatfuelResponse);

        System.out.println("weblink url pair: " + weblinkUrl);
        System.out.println("last msg: " + constructor.getLastMessage(chatfuelResponse));

        constructor.getLastMessage(chatfuelResponse).getAttachment().getPayload().getButtons()
                .get(constructor.getLastMessage(chatfuelResponse).getAttachment().getPayload().getButtons().size() - 1)
                .setUrl(weblinkUrl.getKey());
        constructor.getLastMessage(chatfuelResponse).getAttachment().getPayload().getButtons()
                .get(constructor.getLastMessage(chatfuelResponse).getAttachment().getPayload().getButtons().size() - 1)
                .setType("web_url");
        constructor.getLastMessage(chatfuelResponse).getAttachment().getPayload().getButtons()
                .get(constructor.getLastMessage(chatfuelResponse).getAttachment().getPayload().getButtons().size() - 1)
                .setTitle(weblinkUrl.getValue());
    }

    public void addChoice(ChatfuelResponse chatfuelResponse, Choice choice, HashMap<Integer, HashMap<Question, ContentByPlatform>> answerIdToQuestion, Long currentQuestionId) {
        constructor.makeChoices(chatfuelResponse);

        for (int i = 1; i <= choice.getAnswers().keySet().size(); i++) {
            if(i % 4 == 0) { // chatfuel supports 3 buttons in one message
                constructor.makeChoices(chatfuelResponse);
            }
            ChatfuelButton button = new ChatfuelButton();

            button.setType(JSON_PLUGIN_URL);
            button.setTitle(choice.getChoices().get(i));

            Question questionToAskForAfterAnswer = answerIdToQuestion.get(i).entrySet().iterator().next().getKey();

            button.setUrl(ADDRESS + API_CHATFUEL_ANSWER +
                    "?userId={{messenger user id}}&choiceQuestionId=" + currentQuestionId + "&requestQuestionId=" + questionToAskForAfterAnswer.getId() );

            constructor.getLastMessage(chatfuelResponse).getAttachment().getPayload().getButtons().add(button);
        }
    }
}
