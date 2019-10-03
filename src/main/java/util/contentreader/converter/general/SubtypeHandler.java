package util.contentreader.converter.general;

import javafx.util.Pair;
import objects.dbentities.Question;
import objects.shared.ContentByPlatform;
import util.contentreader.converter.chatfuel.ChatfuelConverter;
import util.contentreader.dataclasses.SubType;

public class SubtypeHandler {

    private QuestionHandler questionHandler;
    private ChatfuelConverter chatfuelConverter = new ChatfuelConverter();

    public SubtypeHandler(QuestionHandler questionHandler) {
        this.questionHandler = questionHandler;
    }

    public void handleSubType(SubType subType) {

        if (!subType.oneNonNull()) {
            throw new IllegalArgumentException("More than one non null value in subtype: " + subType);
        }

        if (subType.getImageUrl() != null) {
            chatfuelConverter.addImage(questionHandler.getContentForCurrentQuestion().getChatfuelResponse(), subType.getImageUrl());
        } else if (subType.getText() != null) {
            questionHandler.makeNextQuestion();
            chatfuelConverter.addText(questionHandler.getContentForCurrentQuestion().getChatfuelResponse(), subType.getText());
        } else if (subType.getVideoUrl() != null) {
            chatfuelConverter.addVideo(questionHandler.getContentForCurrentQuestion().getChatfuelResponse(), subType.getVideoUrl());
        } else if (subType.getLeadTo() != null) {
            questionHandler.getContentForCurrentQuestion().setFork(true);
            questionHandler.getCurrentQuestion().setLeadsToQuestionName(subType.getLeadTo());
            questionHandler.makeNextQuestion();
        } else if (subType.getWeblinkUrl() != null) {
            chatfuelConverter.addWebLink(questionHandler.getContentForCurrentQuestion().getChatfuelResponse(), subType.getWeblinkUrl());
        } else if (subType.getWaitTime() != null) {
            questionHandler.makeNextQuestion();
            questionHandler.getContentForCurrentQuestion().setWaitTime(subType.getWaitTime());
        } else if (subType.getWriteTime() != null) {
            questionHandler.makeNextQuestion();
            questionHandler.getContentForCurrentQuestion().setWriteTime(subType.getWriteTime());
        } else if (subType.getAttribute() != null) {
            questionHandler.getContentForCurrentQuestion().getAttributes().add(subType.getAttribute());
        } else if (subType.getPoints() != null) {
            questionHandler.getContentForCurrentQuestion().setPoints(subType.getPoints());
        } else {
            throw new IllegalArgumentException("Unhandled subtype: " + subType);
        }

    }
}
