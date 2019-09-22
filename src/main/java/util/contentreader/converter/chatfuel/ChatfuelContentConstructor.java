package util.contentreader.converter.chatfuel;

import objects.chatfuel.ChatfuelResponse;
import objects.chatfuel.response.*;

import java.util.ArrayList;

public class ChatfuelContentConstructor {
    public static final String BUTTON_TEMPLATE_TYPE = "button";
    private static final String DEFAULT_ATTACHMENT_TYPE = "template";
    private static final String DEFAULT_ATTACHMENT_TEMPLATE_TYPE = "generic";
    private static final String ATTACHMENT_TEMPLATE_TYPE_IMAGE = "image";
    private static final String DEFAULT_IMAGE_ASPECT_RATIO = "square";
    private static final String ATTACHMENT_TEMPLATE_TYPE_VIDEO = "video";

    public ChatfuelMessage getLastMessage(ChatfuelResponse chatfuelResponse) {
        return chatfuelResponse.getMessages().get(chatfuelResponse.getMessages().size() - 1);
    }

//            if(getLastMessage(chatfuelResponse).getAttachment() == null) {
//        getLastMessage(chatfuelResponse).setAttachment(new ChatfuelAttachment());
//    }
//        if(getLastMessage(chatfuelResponse).getAttachment().getType() == null) {
//        getLastMessage(chatfuelResponse).getAttachment().setType(DEFAULT_ATTACHMENT_TYPE);
//    }
//        if(getLastMessage(chatfuelResponse).getAttachment().getType() == null) {
//        getLastMessage(chatfuelResponse).getAttachment().setType(ATTACHMENT_TEMPLATE_TYPE_IMAGE);
//    }
//        if(getLastMessage(chatfuelResponse).getAttachment().getPayload() == null) {
//        getLastMessage(chatfuelResponse).getAttachment().setPayload(new ChatfuelPayload());
//    }
//        if(getLastMessage(chatfuelResponse).getAttachment().getPayload().getImageAspectRatio() == null) {
//        getLastMessage(chatfuelResponse).getAttachment().getPayload().setImageAspectRatio(DEFAULT_IMAGE_ASPECT_RATIO); //this is optional apparently, should try changing it to different ones
//    }
//        if(getLastMessage(chatfuelResponse).getAttachment().getPayload().getElements() == null) {
//        getLastMessage(chatfuelResponse).getAttachment().getPayload().setElements(new ArrayList<>());
//    }

    public void makeNewChatfuelMessage(ChatfuelResponse chatfuelResponse) {
        if (chatfuelResponse.getMessages() == null) {
            chatfuelResponse.setMessages(new ArrayList<>());
        }

        chatfuelResponse.getMessages().add(new ChatfuelMessage());

        getLastMessage(chatfuelResponse).setAttachment(new ChatfuelAttachment());
    }

    public void makeImageElement(ChatfuelResponse chatfuelResponse) {
        makeNewChatfuelMessage(chatfuelResponse);

        getLastMessage(chatfuelResponse).getAttachment().setType(ATTACHMENT_TEMPLATE_TYPE_IMAGE);

        getLastMessage(chatfuelResponse).getAttachment().setPayload(new ChatfuelPayload());

        getLastMessage(chatfuelResponse).getAttachment().getPayload().setImageAspectRatio(DEFAULT_IMAGE_ASPECT_RATIO); //this is optional apparently, should try changing it to different ones
    }

    public void makeVideoElement(ChatfuelResponse chatfuelResponse) {
        makeNewChatfuelMessage(chatfuelResponse);

        getLastMessage(chatfuelResponse).getAttachment().setType(ATTACHMENT_TEMPLATE_TYPE_VIDEO);

        getLastMessage(chatfuelResponse).getAttachment().setPayload(new ChatfuelPayload());

    }

    public void makeTextElement(ChatfuelResponse chatfuelResponse) {
        if (chatfuelResponse.getMessages() == null) {
            chatfuelResponse.setMessages(new ArrayList<>());
        }

        chatfuelResponse.getMessages().add(new ChatfuelMessage());
    }

    public void makeWebLinkElement(ChatfuelResponse chatfuelResponse) {
        if (chatfuelResponse.getMessages() != null) { // checks incase its a series of links
            if (chatfuelResponse.getMessages().size() > 0) {
                if (getLastMessage(chatfuelResponse).getAttachment() != null) {
                    if (getLastMessage(chatfuelResponse).getAttachment().getPayload() != null) {
                        if (getLastMessage(chatfuelResponse).getAttachment().getPayload().getButtons() != null) {
                            getLastMessage(chatfuelResponse).getAttachment().getPayload().getButtons().add(new ChatfuelButton());
                        }
                    }
                }
            }
        } else {
            makeNewChatfuelMessage(chatfuelResponse);
            getLastMessage(chatfuelResponse).getAttachment().setType(DEFAULT_ATTACHMENT_TYPE);
            getLastMessage(chatfuelResponse).getAttachment().setPayload(new ChatfuelPayload());
            getLastMessage(chatfuelResponse).getAttachment().getPayload().setTemplate_type(DEFAULT_ATTACHMENT_TEMPLATE_TYPE);
            getLastMessage(chatfuelResponse).getAttachment().getPayload().setImageAspectRatio(DEFAULT_IMAGE_ASPECT_RATIO);
            getLastMessage(chatfuelResponse).getAttachment().getPayload().setButtons(new ArrayList<>());
            getLastMessage(chatfuelResponse).getAttachment().getPayload().getButtons().add(new ChatfuelButton());
        }
    }

    public void makeChoices(ChatfuelResponse chatfuelResponse) {
        makeNewChatfuelMessage(chatfuelResponse);
        getLastMessage(chatfuelResponse).getAttachment().setType(DEFAULT_ATTACHMENT_TYPE);
        getLastMessage(chatfuelResponse).getAttachment().setPayload(new ChatfuelPayload());
        getLastMessage(chatfuelResponse).getAttachment().getPayload().setTemplate_type(BUTTON_TEMPLATE_TYPE);
        getLastMessage(chatfuelResponse).getAttachment().getPayload().setButtons(new ArrayList<>());
    }
}
