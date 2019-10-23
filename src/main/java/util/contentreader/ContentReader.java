package util.contentreader;

import javafx.util.Pair;
import objects.chatfuel.response.ChatfuelMessage;
import util.contentreader.dataclasses.*;
import util.contentreader.exception.InvalidFieldException;


import java.io.IOException;
import java.util.*;

public class ContentReader {
    private List<String> validOptions = new ArrayList<>(Arrays.asList("timetosend", "daytosend", "company", "outsideworkhours", "description", "namedmessage"));
    private List<String> validMessages = new ArrayList<>(Arrays.asList("message", "writemsg", "waitmsg"));
    private List<String> validSubtypes = new ArrayList<>(Arrays.asList("text", "image", "link", "video", "leadto", "query", "queryend", "choices", "answer", "wait", "write", "points", "setattribute", "lead_to"));
    private List<String> ignoredTypes = new ArrayList<>(Arrays.asList("comment", "queryend"));
    private List<String> pointsPresets = new ArrayList<>(Arrays.asList("few", "medium", "many"));


    private String latestMessage = "";
    private Map<String, List<String>> options = new HashMap<>();
    private List<ContentBase> question = new ArrayList<>();
    private List<Choice> choices = new ArrayList<>();

    public static Integer findMax(Set<Integer> set) {

        if (set == null || set.size() == 0) {
            return Integer.MIN_VALUE;
        }

        List<Integer> sortedlist = new ArrayList<>(set);

        Collections.sort(sortedlist);

        return sortedlist.get(sortedlist.size() - 1);
    }


    public void reset() {
        latestMessage = "";
        choices = new ArrayList<>();
        options = new HashMap<>();
        question = new ArrayList<>();
    }

    private boolean validBlock(String previous, String current) {
        return false;
    }

    private String removeAllButNumber(String str) {
        str = str.replaceAll("[^\\d.]", "");
        return str;
    }

    private ChatfuelMessage chatfuelMessageFromCompactContent() {
        return null;
    }

    private Map<String, List<String>> handleOptions(Map<String, List<String>> options, List<String> newOption) {
        // Currently the options are added as a List<String> from the entire line, the first element(option name) should not be added, BUT everything else accounts for this.
        if (options.containsKey(newOption.get(0))) {
            throw new IllegalArgumentException("Option '" + newOption.get(0) + "' already set, fix input file.");
        }

        options.put(newOption.get(0), newOption);
        return options;
    }

    private Integer getNumberFromAnswer(String answer) {
        answer = answer.replaceAll("[^\\d.]", "");
        return Integer.valueOf(answer);
    }


    private void getPointsForKeyword(List<String> line, SubType subType) {
        switch (line.get(1)) {
            case "few":
                subType.setPoints(5.0);
                break;
            case "medium":
                subType.setPoints(10.0);
                break;
            case "many":
                subType.setPoints(15.0);
                break;
        }
    }

    private void fillOutLastAnswer() {
        if (choices.size() > 0) {
            choices.get(choices.size() - 1).getAnswers().get(findMax(choices.get(choices.size() - 1).getAnswers().keySet())).addAll(question);
            question = new ArrayList<>();
            choices.remove(choices.size() - 1);
            if (choices.size() > 0) {
                //todo when theres a choice inside a choice that ends correctly, it would throw an error..
                //throw new IllegalArgumentException("Using a Message type component before finishing answers for all choices")
            }
        }
    }

    private void setQuestionToLastAnswer(Integer nr) {
        if (nr == null) {
            Integer last = findMax(choices.get(choices.size() - 1).getAnswers().keySet());
            choices.get(choices.size() - 1).getAnswers().get(last).addAll(question);
        } else if (nr > 1) {
            choices.get(choices.size() - 1).getAnswers().get(nr - 1).addAll(question);
        }
        choices.remove(choices.size() - 1);

        question = new ArrayList<>();
    }

    public static void main(String[] args) throws IOException {
        new ContentReader().readAndUpdate("/temp/Drinking - Morning (1).csv");
    }

    public List<List<ContentBase>> update(List<List<String>> linesLeft) {
        reset();


        List<List<ContentBase>> content = new ArrayList<>();

        Iterator iterator = linesLeft.listIterator();

        while (iterator.hasNext()) {

            List<String> line = (ArrayList<String>) iterator.next();
            line.set(0, line.get(0).toLowerCase());
            iterator.remove();

//            System.out.println("line: " + line);


            if (validOptions.contains(line.get(0))) {
                if(line.get(0).equals("description") || line.get(0).equals("company")) {
                    options = handleOptions(options, line);
                } else {
                    if(line.size() >= 2) {
                        line.set(1, line.get(1).toLowerCase());
                    }
                    if(line.size() >= 3) {
                        line.set(2, line.get(2).toLowerCase());
                    }
                    options = handleOptions(options, line);
                }
            } else if (ignoredTypes.contains(line.get(0))) {
               continue;
            } else if (validMessages.contains(line.get(0))) {

                fillOutLastAnswer();

                SubType subType = new SubType();

                switch (line.get(0)) {
                    case "message":
                        latestMessage = "message";
                        if (question.size() > 0) {
                            content.add(question);
                            question = new ArrayList<>();
                        }
                        break;
                    case "writemsg":
                        latestMessage = "writemsg";
                        subType.setWriteTime(new Pair<>(Double.valueOf(line.get(1)), Double.valueOf(line.get(2))));
                        question.add(subType);
                        content.add(question);
                        question = new ArrayList<>();
                        break;
                    case "waitmsg":
                        latestMessage = "waitmsg";
                        subType.setWaitTime(Double.valueOf(line.get(1)));
                        question.add(subType);
                        content.add(question);
                        question = new ArrayList<>();
                        break;
                    default:
                        throw new IllegalArgumentException("Unhandled messagetype: " + line.get(0)+ " ,on line: " + line);
                }


            } else if (validSubtypes.contains(line.get(0)) || line.get(0).contains("answer")) {
                if (latestMessage.equals("message")) {
                    String subTypeName = line.get(0);
                    SubType subType = new SubType();

                    if (subTypeName.contains("answer")) {
                        if (choices.size() == 0) {
                            throw new IllegalArgumentException("Answer block without preceeding Choices block. On line: " + line);
                        }

//                        System.out.println("----start answer----");
//                        System.out.println("choices start: " + choices);
                        Integer nr = getNumberFromAnswer(subTypeName);
                        boolean dealtWith = false;
                        while (!dealtWith) {
                            if (choices.get((choices.size() - 1)).getAnswers().keySet().contains(nr) &&
                                    choices.get(choices.size() - 1).getAnswers().get(nr) == null) {
                                choices.get(choices.size() - 1).getAnswers().put(nr, new ArrayList<>());
                                if (line.size() == 2) {
                                    line.set(1, line.get(1).toLowerCase());
                                    if(pointsPresets.contains(line.get(1))) {
                                        getPointsForKeyword(line, subType);
                                    } else {
                                        subType.setPoints(Double.valueOf(line.get(1)));
                                    }
                                    choices.get(choices.size() - 1).getAnswers().get(nr).add(subType);
                                }

                                if (nr > 1) {
                                    choices.get(choices.size() - 1).getAnswers().get(nr - 1).addAll(question);
                                    question = new ArrayList<>();
                                }

                                dealtWith = true;
                            } else if (choices.get(choices.size() - 1).getAnswers().keySet().size() < nr) { //answer nr too big for current choice
                                setQuestionToLastAnswer(nr);
                                //choices.remove(choices.size() - 1);
                            } else if (choices.get(choices.size() - 1).getAnswers().get(nr) != null) { //goes up in questions hierarchy
                                setQuestionToLastAnswer(nr);
                                //choices.remove(choices.size() - 1);
                            } else {
                                throw new RuntimeException("oh crap.. this shouldnt happen");
                            }
                            if (choices.size() == 0) {
                                dealtWith = true;
                            }
                        }
//                        System.out.println("choices end: " + choices);
//                        System.out.println("-----end answer-----");
                        continue;
                    }

                    switch (subTypeName) {
                        case "text":
                        case "query":
                            subType.setText(line.get(1));
                            question.add(subType);
                            break;
                        case "image":
                            subType.setImageUrl(line.get(1));
                            question.add(subType);
                            break;
                        case "link":
                            subType.setWeblinkUrl(new Pair<>(line.get(1), line.get(2)));
                            question.add(subType);
                            break;
                        case "points":
                            line.set(1, line.get(1).toLowerCase());

                            if(pointsPresets.contains(line.get(1))) {
                                getPointsForKeyword(line, subType);
                            } else {
                                subType.setPoints(Double.valueOf(line.get(1)));
                            }

                            question.add(subType);
                            break;
                        case "video":
                            subType.setVideoUrl(line.get(1));
                            question.add(subType);
                            break;
                        case "leadto":
                        case "lead_to":
                            subType.setLeadTo(line.get(1));
                            question.add(subType);
                            //question.add(new ContentOptions(options));
                            //content.add(question);
                            //question = new ArrayList<>();
                            break;
                        case "queryend":
                            break;
                        case "choices":
                            Choice choice = new Choice();
                            for (int i = 0; i < line.size(); i++) {
                                if (i != 0) {
                                    choice.getChoices().put(i, line.get(i));
                                    choice.getAnswers().put(i, null);
                                }
                            }
                            choices.add(choice);
                            question.add(choice);
                            content.add(question);
                            question = new ArrayList<>();
                            break;
                        case "setattribute":
                            subType.setAttribute(new Pair<>(line.get(1), line.get(2)));
                            question.add(subType);
                            break;
                        case "write":
                            subType.setWriteTime(new Pair<>(Double.valueOf(line.get(1)), Double.valueOf(line.get(2))));
                            question.add(subType);
                            break;
                        case "wait":
                            subType.setWaitTime(Double.valueOf(line.get(1)));
                            question.add(subType);
                            break;
                        default:
                            throw new IllegalArgumentException("Unhandled subtype: " + subTypeName + " , on line: " + line);
                    }
                } else {
                    throw new IllegalArgumentException("Invalid use of subtypes, all subtypes must follow a Message, current last:" + latestMessage +"\n Line:" + line);
                }


            } else if (line.get(0).equals("endmessage")) {
//                fillOutLastAnswer();
//                question.add(new ContentOptions(options));
//                content.add(question);
//                return content;
                break;
            } else {
                throw new InvalidFieldException("Line starts with no valid option, message or subtype type: " + line.get(0) + "\n" + "Entire line: " + line);
            }
        }

        fillOutLastAnswer();

        if(question.size() > 0) {
            content.add(question);
            question = new ArrayList<>();
            question.add(new ContentOptions(options));
            content.add(question);
        } else {
            question.add(new ContentOptions(options));
            content.add(question);
        }

        return content;
    }

    public List<List<List<ContentBase>>> readAndUpdate(String fileName) throws IOException {


        List<List<String>> lines = new ContentFileReader().readFile(fileName);
        System.out.println("****************** READING CONTENT FILE: " + fileName + " ******************");

        List<List<List<ContentBase>>> plans = new ArrayList<>();

        while(lines.size() > 0) {
            plans.add(update(lines)); // reads the entire file, EndMessage marks a new block and returns
        }

        //printResult(plans);

        return plans;
    }

    private void printResult(List<List<List<ContentBase>>> plans) {
        StringBuilder s = new StringBuilder("cake");
        s.append("\n\n");
        for (List<List<ContentBase>> plan : plans) {
            s.append("\n\n------------------");
            s.append("\n").append(plan);
            for (List<ContentBase> contentBases : plan) {
                s.append("\n\t/-/-/-/-/-/-/-/-/");
                //s.append(contentBases);
                for (ContentBase contentBase : contentBases) {
                    s.append("\n\t\t").append(contentBase);
                }
            }
        }
        System.out.println(s.toString());
    }

}
