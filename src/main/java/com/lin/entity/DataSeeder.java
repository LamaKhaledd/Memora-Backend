package com.lin.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lin.repository.FlashcardRepository;
import com.lin.repository.QuizRepository;
import com.lin.repository.SubjectRepository;
import com.lin.repository.TopicRepository;
import com.lin.repository.UserFlashcardRelationshipRepository;
import com.lin.repository.UserRepository;

import java.util.Arrays;
import java.util.UUID;

import javax.annotation.PostConstruct;

@Service
public class DataSeeder {

    private final SubjectRepository subjectRepository;
    private final TopicRepository topicRepository;
    private final FlashcardRepository flashcardRepository;
    private final UserRepository userRepository;
    private final UserFlashcardRelationshipRepository relationshipRepository;
    private final QuizRepository quizRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public DataSeeder(SubjectRepository subjectRepository, TopicRepository topicRepository, FlashcardRepository flashcardRepository,
                       UserRepository userRepository,QuizRepository quizRepository, UserFlashcardRelationshipRepository relationshipRepository) {
        this.subjectRepository = subjectRepository;
        this.topicRepository = topicRepository;
        this.flashcardRepository = flashcardRepository;
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
        this.relationshipRepository = relationshipRepository;
       
    }

    @PostConstruct
    public void seedData() {

        // Add sample users
        

        // Hash passwords before saving users
        String hashedPassword1 = encoder.encode("password123");
        String hashedPassword2 = encoder.encode("password456");
        String hashedPassword3 = encoder.encode("mypassword789");
        String hashedPassword4 = encoder.encode("securepass012");

        User user1 = new User(
            "user1", 
            "user1@example.com", 
            hashedPassword1, 
            "Loves mathematics and science", 
            "https://picsum.photos/201", 
            "Nablus",
            "2024-01-01T08:00:00", 
            "2024-12-29T10:00:00", 
            true, 
            "123-456-7890", 
            "Mathematics, Science", 
            25, 
            20, 
            15, 
            5, 
            "Lama Ibrahim", 
            true, 
            UserRole.PARENT,
            10
    );

    User user2 = new User(
            "user2", 
            "user2@example.com", 
            hashedPassword2, 
            "Enjoys exploring history", 
            "https://picsum.photos/202", 
            "Nablus",
            "2024-01-01T08:00:00", 
            "2024-12-29T10:05:00", 
            false, 
            "321-654-0987", 
            "History, Literature", 
            30, 
            10, 
            5, 
            3, 
            "Razan Dwekat", 
            true, 
            UserRole.USER,
            5
    );

    User user3 = new User(
            UUID.randomUUID().toString(), 
            "user3@example.com", 
            hashedPassword3, 
            "Avid learner of technology", 
            "https://picsum.photos/203", 
            "Nablus",
            "2024-01-01T08:00:00", 
            "2024-12-29T11:00:00", 
            true, 
            "456-789-0123", 
            "Science, Technology", 
            28, 
            25, 
            20, 
            7, 
            "Wafa'a Jawad", 
            true, 
            UserRole.ADMIN,
            12
    );

    User user4 = new User(
            UUID.randomUUID().toString(), 
            "user4@example.com", 
            hashedPassword4, 
            "Passionate about literature and arts", 
            "https://picsum.photos/204", 
            "Nablus",
            "2024-01-01T08:00:00", 
            "2024-12-29T11:10:00", 
            false, 
            "789-012-3456", 
            "Literature, Arts", 
            22, 
            15, 
            10, 
            2, 
            "Tasneem Jawabreh", 
            true, 
            UserRole.USER,
            6
    );

    mongoTemplate.save(user1);
    mongoTemplate.save(user2);
    mongoTemplate.save(user3);
    mongoTemplate.save(user4);


    ParentChildRelationship relationship1 = new ParentChildRelationship(
        UUID.randomUUID().toString(),
        user1.getUserId(), 
        Arrays.asList(user2.getUserId(), user3.getUserId()) 
    );
    
    mongoTemplate.save(relationship1);

    System.out.println("Users collection seeded.");

        Message message1 = new Message("1", "user1", "user2", "Hello, how are you?", "2024-12-29T10:00:00", "false", MessageType.TEXT);
        Message message2 = new Message("2", "user2", "user1", "I'm good, thanks! How about you?", "2024-12-29T10:05:00", "true", MessageType.TEXT);
        Message message3 = new Message("3", "user1", "user3", "Happy holidays!", "2024-12-29T11:00:00", "false", MessageType.TEXT);
        Message message4 = new Message("4", "user3", "user1", "Thank you! Same to you!", "2024-12-29T11:10:00", "true", MessageType.TEXT);

        mongoTemplate.save(message1);
        mongoTemplate.save(message2);
        mongoTemplate.save(message3);
        mongoTemplate.save(message4);
        // Add sample subjects
        Subject subject = new Subject();
        subject.setSubjectName("Arabic");
        subject.setuserId("nmnm");
        subject.setImageUrl("https://picsum.photos/200");
        subjectRepository.save(subject);

        Subject subject1 = new Subject();
        subject1.setSubjectName("Mathematics");
        subject1.setuserId("nmnm");
        subject1.setImageUrl("https://picsum.photos/200");
        subjectRepository.save(subject1);

        Subject subject2 = new Subject();
        subject2.setSubjectName("History");
        subject2.setuserId("wewe");
        subject2.setImageUrl("https://picsum.photos/201");
        subjectRepository.save(subject2);

        Subject subject3 = new Subject();
        subject3.setSubjectName("Science");
        subject3.setuserId("wewe");
        subject3.setImageUrl("https://picsum.photos/202");
        subjectRepository.save(subject3);

        Subject subject4 = new Subject();
        subject4.setSubjectName("Literature");
        subject4.setuserId("wewe");
        subject4.setImageUrl("https://picsum.photos/203");
        subjectRepository.save(subject4);

        // Add sample topics
        Topic topic = new Topic();
        topic.setId("3amti");
        topic.setTopicName("Algebra");
        topic.setImageUrl("https://picsum.photos/201");
        topic.setUserId("nmnm");
        topic.setSubject(subject1);
        topicRepository.save(topic);

        Topic topic2 = new Topic();
        topic2.setTopicName("Geometry");
        topic2.setImageUrl("https://picsum.photos/201");
        topic2.setUserId("nmnm");
        topic2.setSubject(subject1);
        topicRepository.save(topic2);

        Topic topic3 = new Topic();
        topic3.setTopicName("Trigonometry");
        topic3.setImageUrl("https://picsum.photos/201");
        topic3.setUserId("wewe");
        topic3.setSubject(subject1);
        topicRepository.save(topic3);

        Topic topic4 = new Topic();
        topic4.setTopicName("Calculus");
        topic4.setImageUrl("https://picsum.photos/201");
        topic4.setUserId("wewe");
        topic4.setSubject(subject1);
        topicRepository.save(topic4);

        Topic topic1 = new Topic();
        topic1.setTopicName("Statistics");
        topic1.setImageUrl("https://picsum.photos/201");
        topic1.setUserId("wewe");
        topic1.setSubject(subject1);
        topicRepository.save(topic1);

        // Add sample flashcards with questions and answers in Delta format
        Flashcard flashcard1 = new Flashcard();
        flashcard1.setQuestion("[{\"insert\":\"What is 3 + 5?\\n\"}]"); // Delta format JSON string for the question
        flashcard1.setAnswer("[{\"insert\":\"8\\n\"}]"); // Delta format JSON string for the answer
        flashcard1.setDifficulty(1);
        flashcard1.setLastResponse(false);
        flashcard1.setSubject(subject1);
        flashcard1.setTopic(topic1);
        flashcard1.setImageUrl("https://picsum.photos/201");
        flashcardRepository.save(flashcard1);

        Flashcard flashcard2 = new Flashcard();
        flashcard2.setQuestion("[{\"insert\":\"What is 7 * 6?\\n\"}]"); // Delta format JSON string for the question
        flashcard2.setAnswer("[{\"insert\":\"42\\n\"}]"); // Delta format JSON string for the answer
        flashcard2.setDifficulty(2);
        flashcard2.setLastResponse(true);
        flashcard2.setSubject(subject1);
        flashcard2.setTopic(topic1);
        flashcard2.setImageUrl("https://picsum.photos/201");

        flashcardRepository.save(flashcard2);

        Flashcard flashcard3 = new Flashcard();
        flashcard3.setQuestion("[{\"insert\":\"Solve for x: 2x = 10\\n\"}]"); // Delta format JSON string for the question
        flashcard3.setAnswer("[{\"insert\":\"5\\n\"}]"); // Delta format JSON string for the answer
        flashcard3.setDifficulty(2);
        flashcard3.setLastResponse(false);
        flashcard3.setSubject(subject1);
        flashcard3.setTopic(topic1);
        flashcard3.setImageUrl("https://picsum.photos/201");
        flashcardRepository.save(flashcard3);

        Flashcard flashcard4 = new Flashcard();
        flashcard4.setQuestion("[{\"insert\":\"What is the square root of 16?\\n\"}]"); // Delta format JSON string for the question
        flashcard4.setAnswer("[{\"insert\":\"4\\n\"}]"); // Delta format JSON string for the answer
        flashcard4.setDifficulty(1);
        flashcard4.setLastResponse(true);
        flashcard4.setSubject(subject1);
        flashcard4.setTopic(topic1);
        flashcard4.setImageUrl("https://picsum.photos/201");
        flashcardRepository.save(flashcard4);

        
        UserFlashcardRelationship userFlashcardRelationship1 = new UserFlashcardRelationship();
        userFlashcardRelationship1.setUser(user1);
        userFlashcardRelationship1.setFlashcard(flashcard1);
        userFlashcardRelationship1.setRelationshipType(RelationshipType.VIEWER);
        relationshipRepository.save(userFlashcardRelationship1);

        UserFlashcardRelationship userFlashcardRelationship2 = new UserFlashcardRelationship();
        userFlashcardRelationship2.setUser(user1);
        userFlashcardRelationship2.setFlashcard(flashcard2);
        userFlashcardRelationship2.setRelationshipType(RelationshipType.VIEWER);
        relationshipRepository.save(userFlashcardRelationship2);



        // Seeding quizzes
        Topic algebraTopic = topicRepository.findById("3amti").orElseThrow(() -> new RuntimeException("Topic not found"));

        Quiz algebraQuiz = new Quiz();
        algebraQuiz.setQuestions(Arrays.asList(
                "What is the value of x in 2x + 3 = 7?",
                "Simplify: 3x + 4x",
                "Find the slope of a line with equation y = 2x + 1"
        ));
        algebraQuiz.setCorrectAnswers(Arrays.asList("2", "7x", "2"));
        algebraQuiz.setOption1Controller(Arrays.asList("1", "5x", "0"));
        algebraQuiz.setOption2Controller(Arrays.asList("2", "7x", "2"));
        algebraQuiz.setOption3Controller(Arrays.asList("3", "4x", "-1"));
        algebraQuiz.setOption4Controller(Arrays.asList("4", "10x", "1"));
        algebraQuiz.setTopic(algebraTopic);
        quizRepository.save(algebraQuiz);

        System.out.println("Quizzes collection seeded.");
    }

}
