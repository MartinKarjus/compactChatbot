package util;

import bot.temp.ChatfuelFileContentReader;
import db.repository.*;
import objects.dbentities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
public class ContentGenerator {

    @Autowired
    private BotUserRepository userRepository;

    @Autowired
    private AnswersRepository answersRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private PlanAccomplishedRepository planAccomplishedRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private PlatformRepository platformRepository;

    @Autowired
    private PlatformToUserRepository platformToUserRepository;

    @Autowired
    private QuestionGroupRepository questionGroupRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TimeToSendRepository timeToSendRepository;

    @Autowired
    private TransmissionLogRepository transmissionLogRepository;

    private Random random = new Random();
    private ChatfuelFileContentReader chatfuelFileContentReader = new ChatfuelFileContentReader();

    public int getRan(List<?> list)
    {
        return random.nextInt(list.size());
    }

    public void addContent() {


        Question question2 = new Question();
        question2.setName("content2");
        question2.setDescription("for testing");
        question2.setText(chatfuelFileContentReader.getContentAsJson("/json/contentForTesting/content3.json"));
        question2.setMediaId(1L);
        questionRepository.save(question2);

        Question question = new Question();
        question.setName("content1");
        question.setDescription("for testing");
        question.setText(chatfuelFileContentReader.getContentAsJson("/json/contentForTesting/content2.json"));
        question.setLeadsToQuestionId(2L);
        question.setMediaId(1L);
        questionRepository.save(question);

        Question question3 = new Question();
        question3.setName("content3");
        question3.setDescription("for testing");
        question3.setText(chatfuelFileContentReader.getContentAsJson("/json/contentForTesting/content4.json"));
        question3.setMediaId(1L);
        questionRepository.save(question3);


        List<Question> questions = questionRepository.findAll();


        TimeToSend timeToSend = new TimeToSend();
        LocalDateTime time = LocalDateTime.now();
        timeToSend.setTimeToSend(Timestamp.valueOf(time));
        timeToSendRepository.save(timeToSend);

        TimeToSend timeToSend2 = new TimeToSend();
        LocalDateTime time2 = LocalDateTime.now().plusMinutes(1);;
        timeToSend2.setTimeToSend(Timestamp.valueOf(time2));
        timeToSendRepository.save(timeToSend2);

        List<TimeToSend> timeToSends = timeToSendRepository.findAll();


        Plan plan = new Plan();
        plan.setQuestionId(3L);
        plan.setTimeToSendId(1L);
        plan.setDay(0L);
        plan.setCompanyId(1L);
        planRepository.save(plan);

        Plan plan2 = new Plan();
        plan2.setQuestionId(4L);
        plan2.setTimeToSendId(2L);
        plan2.setDay(0L);
        plan2.setCompanyId(1L);
        planRepository.save(plan2);

        List<Plan> plans = planRepository.findAll();
    }

    public void addStaticValues() {
        Company company = new Company();
        company.setName("compactTesters");
        companyRepository.save(company);
        List<Company> companies = companyRepository.findAll();

        QuestionGroup questionGroup = new QuestionGroup();
        questionGroup.setName("testers");
        questionGroup.setCompanyId(companies.get(getRan(companies)).getId());
        questionGroup.setDateCreated(Timestamp.valueOf(LocalDateTime.now()));
        questionGroupRepository.save(questionGroup);
        List<QuestionGroup> questionGroups = questionGroupRepository.findAll();

        Media media = new Media();
        media.setType("noMedia");
        mediaRepository.save(media);
        List<Media> medias = mediaRepository.findAll();

        Team team = new Team();
        team.setName("testers team");
        team.setScore(0L);
        teamRepository.save(team);




        Platform platform = new Platform();
        platform.setName("chatfuel");
        platformRepository.save(platform);




    }
}
