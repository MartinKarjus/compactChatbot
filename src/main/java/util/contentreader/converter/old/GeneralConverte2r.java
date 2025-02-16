package util.contentreader.converter.old;

import db.repository.QuestionRepository;
import db.repository.TimeToSendRepository;
import objects.dbentities.Plan;
import objects.dbentities.Question;
import objects.dbentities.TimeToSend;
import objects.shared.ContentByPlatform;
import util.contentreader.converter.ConvertResult;
import util.contentreader.converter.chatfuel.ChatfuelConverter;
import util.contentreader.dataclasses.Choice;
import util.contentreader.dataclasses.ContentBase;
import util.contentreader.dataclasses.ContentOptions;
import util.contentreader.dataclasses.SubType;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
public class GeneralConverter {

    private List<String> timePresets = new ArrayList<>(Arrays.asList("morning", "afternoon", "evening", "never", "always"));


    private ChatfuelConverter chatfuelConverter = new ChatfuelConverter();


    private QuestionRepository questionRepository;

    private TimeToSendRepository timeToSendRepository;
    private HashMap<Question, ContentByPlatform> questionContentByPlatformMap = new LinkedHashMap<>();
    private HashMap<Plan, HashMap<Question, ContentByPlatform>> planToQuestionToContent = new LinkedHashMap<>();
    private HashMap<Plan, Question> planToFirstQuestion = new LinkedHashMap<>();
    private Plan currentPlan = new Plan();

    public GeneralConverter(QuestionRepository questionRepository, TimeToSendRepository timeToSendRepository) {
        this.questionRepository = questionRepository;
        this.timeToSendRepository = timeToSendRepository;
    }

    public static Map.Entry<Question, ContentByPlatform> getLastQuestion(HashMap<Question, ContentByPlatform> questionContentByPlatformMap) {
        Map.Entry<Question, ContentByPlatform> lastQuestion = null;
        Iterator<Map.Entry<Question, ContentByPlatform>> iterator = questionContentByPlatformMap.entrySet().iterator();
        while (iterator.hasNext()) { // its linkedhashmap so no pointer to last element, need to loop through
            lastQuestion = iterator.next();
        }
        return lastQuestion;
    }

    public static Map.Entry<Question, ContentByPlatform> getLastQuestionWithId(HashMap<Question, ContentByPlatform> questionContentByPlatformMap) {
        Map.Entry<Question, ContentByPlatform> lastQuestion = null;
        Map.Entry<Question, ContentByPlatform> q = null;
        Iterator<Map.Entry<Question, ContentByPlatform>> iterator = questionContentByPlatformMap.entrySet().iterator();
        while (iterator.hasNext()) { // its linkedhashmap so no pointer to last element, need to loop through
            lastQuestion = iterator.next();
            if (lastQuestion.getKey().getId() != null) {
                q = lastQuestion;
            }
        }
        return q;
    }

    private void reset() {
        questionContentByPlatformMap = new LinkedHashMap<>(); //todo ContentByPlatform needs to be converted to json and added onto questions before this
    }

    private Question makeNextQuestion(boolean connectWithLastQuestion, Question question) {

        if (connectWithLastQuestion) {
            Map.Entry<Question, ContentByPlatform> lastQuestion = getLastQuestionWithId(questionContentByPlatformMap);
            question = questionRepository.save(question);

            if (lastQuestion != null) {
                if (question.getId() != null) {
                    lastQuestion.getKey().setLeadsToQuestionId(question.getId());
                } else {
                    throw new RuntimeException("Trying to set null ID to leadsToQuestionId(probably failed to save to DB)");
                }
            }
        }

        Question newQuestion = new Question();
        questionContentByPlatformMap.put(newQuestion, new ContentByPlatform());


        return newQuestion;
    }

    private Question makeNextQuestionAfterChoice(Question choiceQuestion, Question questionBeforeChoiceQuestion) {

        choiceQuestion = questionRepository.save(choiceQuestion);

        Question emptyQuestion = questionRepository.save(new Question());
        questionContentByPlatformMap.put(emptyQuestion, new ContentByPlatform());//todo This solution screams: "remove the entire class and start again.!"

        if (choiceQuestion.getId() != null) {
            questionBeforeChoiceQuestion.setLeadsToQuestionId(choiceQuestion.getId());
        } else {
            throw new RuntimeException("Trying to set null ID to leadsToQuestionId(probably failed to save to DB)");
        }

        if (emptyQuestion.getId() != null) {
            choiceQuestion.setLeadsToQuestionId(emptyQuestion.getId());
        } else {
            throw new RuntimeException("Trying to set null ID to leadsToQuestionId(probably failed to save to DB)");
        }


        Question newQuestion = new Question();
        questionContentByPlatformMap.put(newQuestion, new ContentByPlatform());


        return newQuestion;
    }

//    private Question getLastQuestionWithFork() {
//        List<Question> questionMapKeys = new ArrayList<>(questionContentByPlatformMap.keySet());
//        Collections.reverse(questionMapKeys);
//        for (Question questionMapKey : questionMapKeys) {
//            if()
//        }
//
//        return null;
//    }

    private void setLastQuestionAsFinal() {

        Map.Entry<Question, ContentByPlatform> lastQuestion = getLastQuestion(questionContentByPlatformMap);
        Map.Entry<Question, ContentByPlatform> lastQuestionWithId = getLastQuestionWithId(questionContentByPlatformMap);
        Question q = questionRepository.save(lastQuestion.getKey());
        if (lastQuestionWithId != null) {
            lastQuestionWithId.getKey().setLeadsToQuestionId(q.getId());
        }
    }

    private HashMap<Question, ContentByPlatform> convertContentBase(ContentBase contentBase, Question currentQuestion) {
        if(!questionContentByPlatformMap.containsKey(currentQuestion)) {
            questionContentByPlatformMap.put(currentQuestion, new ContentByPlatform()); //todo figure out why this is ever not in the map
        }
        //System.out.println("Converting: " + contentBase);
        if (contentBase instanceof Choice) {
            System.out.println("CURRENTQUESTION AT START: " + currentQuestion.hashCode());
            Choice choice = (Choice) contentBase;
            HashMap<Integer, HashMap<Question, ContentByPlatform>> answerIdToQuestion = new LinkedHashMap<>();

            Map.Entry<Question, ContentByPlatform> previousQuestion = getLastQuestionWithId(questionContentByPlatformMap);
            //currentQuestion.getValue().setFork(true);
            questionContentByPlatformMap.get(currentQuestion).setFork(true);

            System.out.println("current question AT START:" + currentQuestion + "\n\t" + questionContentByPlatformMap.get(currentQuestion));
            for (int i = 1; i <= choice.getAnswers().keySet().size(); i++) {
                HashMap<Question, ContentByPlatform> map = new HashMap<>();

                GeneralConverter converter = new GeneralConverter(questionRepository, timeToSendRepository);

                for (ContentBase base : choice.getAnswers().get(i)) {
                    if (converter.getQuestionContentByPlatformMap().size() > 0) {
                        Map.Entry<Question, ContentByPlatform> lastQuestion = getLastQuestion(converter.getQuestionContentByPlatformMap());
                        map = converter.convertContentBase(base, lastQuestion.getKey());
                    } else {
                        Question newQuestion = new Question();
                        converter.getQuestionContentByPlatformMap().put(newQuestion, new ContentByPlatform());
                        map = converter.convertContentBase(base, newQuestion);
                    }
                }
                answerIdToQuestion.put(i, map);
                converter.setLastQuestionAsFinal();

                for (Map.Entry<Question, ContentByPlatform> entry : converter.getQuestionContentByPlatformMap().entrySet()) {
                    if (questionContentByPlatformMap.containsKey(entry.getKey())) {
                        throw new RuntimeException("Somehow, a question within a choice has ended up " +
                                "inside the questionContentByPlatformMap of the object that called it.\nRecursion is fun");
                    }
                    questionContentByPlatformMap.put(entry.getKey(), entry.getValue());
                }

            }
            //chatfuelConverter.addChoice(questionContentByPlatformMap.get(question).getChatfuelResponse(), choice, answerIdToQuestion, currentQuestion.getKey().getId());
            makeNextQuestionAfterChoice(currentQuestion,previousQuestion.getKey());
            makeNextQuestion(true, currentQuestion);

            System.out.println("*************************************************************");
            questionContentByPlatformMap.entrySet().forEach(e -> System.out.println(e.getKey() + "\n\t" + e.getValue()));
            System.out.println("current question:" + currentQuestion);
            System.out.println("*************************************************************");
            System.out.println("CURRENTQUESTION HERE: " + currentQuestion.hashCode());
            if(!questionContentByPlatformMap.containsKey(currentQuestion)) {
                questionContentByPlatformMap.put(currentQuestion, new ContentByPlatform()); //todo figure out why this is ever not in the map // i think that somewhere, somehow, currentQuestion is replaced..
            }

            chatfuelConverter.addChoice(questionContentByPlatformMap.get(currentQuestion).getChatfuelResponse(), choice, answerIdToQuestion, currentQuestion.getId());



        } else if (contentBase instanceof ContentOptions) {
            ContentOptions options = (ContentOptions) contentBase;
            if (options.getOptions().containsKey("timetosend")) {
                TimeToSend timeToSend = getTimeToSend(options.getOptions().get("timetosend").get(1));
                //todo check for existing values in the DB and use those instead of making a new one each time
                timeToSend = timeToSendRepository.save(timeToSend);
                currentPlan.setTimeToSendId(timeToSend.getId());
            }
            if (options.getOptions().containsKey("daytosend")) {
                currentPlan.setDay(Long.valueOf(options.getOptions().get("daytosend").get(1)));
            }
            if (options.getOptions().containsKey("company")) {
                //todo implement
            }
            if (options.getOptions().containsKey("outsideworkhours")) {
                //todo implement
            }
            if (options.getOptions().containsKey("description")) {
                currentPlan.setDescription(options.getOptions().get("description").get(1));
            }
            if (options.getOptions().containsKey("namedmessage")) {
                currentPlan.setNamedMessage(options.getOptions().get("namedmessage").get(1));
            }
        } else if (contentBase instanceof SubType) {

            handleSubType((SubType) contentBase, currentQuestion, questionContentByPlatformMap.get(currentQuestion));

        }
        return questionContentByPlatformMap;
    }

    private TimeToSend getTimeToSend(String timeAsString) {
        TimeToSend timeToSend = new TimeToSend();

        if (timePresets.contains(timeAsString)) { //todo improve this, confirm times..
            switch (timeAsString) {
                case "morning":
                    timeAsString = "09:00:00";
                    break;
                case "afternoon":
                    timeAsString = "14:30:00";
                    break;
                case "evening":
                    timeAsString = "16:30:00";
                    break;
                case "never":
                    timeToSend.setNeverSend(true);
                    return timeToSend;
                case "always":
                    timeToSend.setAlwaysSend(true);
                    return timeToSend;
                default:
                    throw new IllegalArgumentException("Unhandled time as string:" + timeAsString);
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "HH:mm:ss");
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(timeAsString);
            timeToSend.setTimeToSend(new Timestamp(parsedDate.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeToSend;
    }

    private Question handleSubType(SubType subType, Question question, ContentByPlatform contentByPlatform) {

        if (!subType.oneNonNull()) {
            throw new IllegalArgumentException("More than one non null value in subtype: " + subType);
        }

        if (subType.getImageUrl() != null) {
            chatfuelConverter.addImage(contentByPlatform.getChatfuelResponse(), subType.getImageUrl());
        } else if (subType.getText() != null) {
            question = makeNextQuestion(true, question);
            chatfuelConverter.addText(contentByPlatform.getChatfuelResponse(), subType.getText());
        } else if (subType.getVideoUrl() != null) {
            chatfuelConverter.addVideo(contentByPlatform.getChatfuelResponse(), subType.getVideoUrl());
        } else if (subType.getLeadTo() != null) {
            questionContentByPlatformMap.get(question).setFork(true);
            question.setLeadsToQuestionName(subType.getLeadTo());
        } else if (subType.getWeblinkUrl() != null) {
            chatfuelConverter.addWebLink(contentByPlatform.getChatfuelResponse(), subType.getWeblinkUrl());
        } else if (subType.getWaitTime() != null) {
            question = makeNextQuestion(true, question);
            contentByPlatform.setWaitTime(subType.getWaitTime());
        } else if (subType.getWriteTime() != null) {
            question = makeNextQuestion(true, question);
            contentByPlatform.setWriteTime(subType.getWriteTime());
        } else if (subType.getAttribute() != null) {
            // todo implement
        } else if (subType.getPoints() != null) {
            contentByPlatform.setPoints(subType.getPoints());
        } else {
            throw new IllegalArgumentException("Unhandled subtype: " + subType);
        }

        return question; //todo dont think returning this actually does anything.. test and remove
    }


    public ConvertResult convertPlans(List<List<List<ContentBase>>> plans) {

//        for (List<List<ContentBase>> plan : plans) {
//            System.out.println("------");
//            for (List<ContentBase> contentBases : plan) {
//                System.out.println("/-/-/-/-/-/-/-/");
//                for (ContentBase contentBase : contentBases) {
//                    System.out.println(contentBase);
//                }
//            }
//        }


        for (List<List<ContentBase>> stringPlans : plans) {
            reset();
            currentPlan = new Plan();
            for (List<ContentBase> contentBases : stringPlans) {

                for (ContentBase contentBase : contentBases) {
                    System.out.println(contentBase);
                    if (questionContentByPlatformMap.size() > 0) {
                        Map.Entry<Question, ContentByPlatform> lastQuestion = getLastQuestion(questionContentByPlatformMap);
                        convertContentBase(contentBase, lastQuestion.getKey());
                    } else {
                        Question question = new Question();
                        questionContentByPlatformMap.put(question, new ContentByPlatform());
                        convertContentBase(contentBase, question);
                    }
                    //printCurrentPlan();
                }
            }
            setLastQuestionAsFinal();
            planToFirstQuestion.put(currentPlan, questionContentByPlatformMap.entrySet().iterator().next().getKey());
            //printCurrentPlan();

            planToQuestionToContent.put(currentPlan, new LinkedHashMap<>());
            for (Map.Entry<Question, ContentByPlatform> entry : questionContentByPlatformMap.entrySet()) {
                planToQuestionToContent.get(currentPlan).put(entry.getKey(), entry.getValue());
            }

            reset();
        }

        System.out.println("---------------------------------------------------------");
        for (Map.Entry<Plan, HashMap<Question, ContentByPlatform>> entry : planToQuestionToContent.entrySet()) {
            System.out.println("||||||||||||||||||||");
            System.out.println("Plan: " + entry.getKey());
            for (Map.Entry<Question, ContentByPlatform> entry1 : entry.getValue().entrySet()) {
                System.out.println("\tQuestion: " + entry1.getKey());
                System.out.println("\t\tContent: " + entry1.getValue());
            }
        }

        return new ConvertResult(planToQuestionToContent, planToFirstQuestion);
    }


    private void printCurrentPlan() {
        System.out.println("--------------------------");
        System.out.println("current plan:" + currentPlan);
        for (Map.Entry<Question, ContentByPlatform> entry : questionContentByPlatformMap.entrySet()) {
            System.out.println("\tquestion: " + entry.getKey());
            System.out.println("\t\tcontent: " + entry.getValue());
        }
        System.out.println("---/--/-/-/--/-/-/-/-/-/-/-/-/---------");
    }

    public HashMap<Question, ContentByPlatform> getQuestionContentByPlatformMap() {
        return questionContentByPlatformMap;
    }
}
*/