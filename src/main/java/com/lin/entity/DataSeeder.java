package com.lin.entity;

import com.lin.entity.Instructor.Announcement;
import com.lin.entity.Instructor.Classroom;
import com.lin.entity.Instructor.ClassroomRelationship;
import com.lin.entity.Instructor.Meeting;
import com.lin.entity.Pomodoro.Task;
import com.lin.entity.Pomodoro.TaskPriority;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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
                10,
                null,null,null
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
                5,
                null,null,null
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
                12,
                null,null,null
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
                6,
                null,null,null
        );


        User instructor1 = new User();
        instructor1.setUserId("123");
        instructor1.setEmail("instructor1@example.com");
        instructor1.setPassword(hashedPassword1);
        instructor1.setAbout("Experienced teacher with a passion for technology.");
        instructor1.setImage("https://picsum.photos/204");
        instructor1.setLocation("New York");
        instructor1.setCreated_at("2023-01-01");
        instructor1.setLast_active("2023-12-01");
        instructor1.setOnline(true);
        instructor1.setTelephone("1234567890");
        instructor1.setFavouriteCategories("Technology, Science");
        instructor1.setAge(35);
        instructor1.setNumOfCreatedFlashcards(100);
        instructor1.setNumOfCompletedFlashcards(80);
        instructor1.setStudyStreak(30);
        instructor1.setUsername("instructor1");
        instructor1.setEnabled(true);
        instructor1.setRole(UserRole.INSTRUCTOR);
        instructor1.setFlashcardsCount(200);
        instructor1.setClassroomsCreated(Arrays.asList("classroom_1", "classroom_2","classroom_3"));
        instructor1.setClassroomsJoined(Arrays.asList("studentClassroom1", "studentClassroom2"));
        instructor1.setReminders(Arrays.asList("Reminder for class 1", "Reminder for class 2"));

        // Instructor 2
        User instructor2 = new User();
        instructor2.setUserId(UUID.randomUUID().toString());
        instructor2.setEmail("instructor2@example.com");
        instructor2.setPassword(hashedPassword1);
        instructor2.setAbout("Dedicated educator with experience in web development.");
        instructor2.setImage("https://picsum.photos/204");
        instructor2.setLocation("San Francisco");
        instructor2.setCreated_at("2023-02-01");
        instructor2.setLast_active("2023-12-10");
        instructor2.setOnline(false);
        instructor2.setTelephone("0987654321");
        instructor2.setFavouriteCategories("Programming, Web Development");
        instructor2.setAge(40);
        instructor2.setNumOfCreatedFlashcards(120);
        instructor2.setNumOfCompletedFlashcards(100);
        instructor2.setStudyStreak(15);
        instructor2.setUsername("instructor2");
        instructor2.setEnabled(true);
        instructor2.setRole(UserRole.INSTRUCTOR);
        instructor2.setFlashcardsCount(240);
        instructor2.setClassroomsCreated(Arrays.asList("classroom3"));
        instructor2.setClassroomsJoined(Arrays.asList("studentClassroom3"));
        instructor2.setReminders(Arrays.asList("Reminder for class 3"));

        Classroom classroom1 = new Classroom(
                UUID.randomUUID().toString(),  // Unique ID for the classroom
                "Math 101",  // Classroom name
                "Introduction to basic mathematics",  // Description
                "instructor1",  // Instructor ID
                "INVITE123",  // Invitation code
                "2025-01-05T10:00:00",  // Created at (timestamp)
                20,  // Max number of students
                3,  // Current number of students enrolled
                true  // Ongoing status (class is ongoing)
        );

        // Classroom 2 - Physics 101
        Classroom classroom2 = new Classroom(
                UUID.randomUUID().toString(),  // Unique ID for the classroom
                "Physics 101",  // Classroom name
                "Introduction to basic physics concepts",  // Description
                "instructor1",  // Instructor ID
                "INVITE456",  // Invitation code
                "2025-01-06T12:00:00",  // Created at (timestamp)
                20,  // Max number of students
                2,  // Current number of students enrolled
                true  // Ongoing status (class is ongoing)
        );

        // Classroom 3 - Chemistry 101
        Classroom classroom3 = new Classroom(
                UUID.randomUUID().toString(),  // Unique ID for the classroom
                "Chemistry 101",  // Classroom name
                "Basic concepts of chemistry",  // Description
                "instructor1",  // Instructor ID
                "INVITE789",  // Invitation code
                "2025-01-07T14:00:00",  // Created at (timestamp)
                20,  // Max number of students
                3,  // Current number of students enrolled
                false  // Ongoing status (class has ended)
        );

        Announcement announcement1 = new Announcement(
                "announcement1", // _id
                "classroom1",    // classroomId
                "Weekly Review", // title
                "2025-01-10",    // date
                "15:00",         // time
                "Prepare Chapters 1–3", // details
                "2025-01-08T09:00:00Z" // timestamp
        );

        Announcement announcement2 = new Announcement(
                "announcement2", // _id
                "classroom2",    // classroomId
                "Project Discussion", // title
                "2025-01-12",    // date
                "10:00",         // time
                "Review project drafts.", // details
                "2025-01-08T11:00:00Z" // timestamp
        );

        // Example for Meeting 1
        Meeting meeting1 = new Meeting(
                "meeting1",           // meetingId
                "classroom1",         // classroomId
                "http://yourapp.com/meet/abc123", // link
                "Scheduled",          // status
                Arrays.asList("student1", "student2"), // waitingRoom
                List.of(),      // attendees (empty initially)
                "2025-01-08T09:00:00Z", // createdAt
                "2025-01-15T10:00:00", // startTime
                null                   // endTime (null initially, as the meeting has not ended)
        );

        // Example for Meeting 2
        Meeting meeting2 = new Meeting(
                "meeting2",           // meetingId
                "classroom2",         // classroomId
                null,                 // link (null initially)
                "Scheduled",          // status
                List.of(),      // waitingRoom
                List.of(),      // attendees
                "2025-01-08T11:00:00Z", // createdAt
                "2025-01-20T14:00:00", // startTime
                null                   // endTime
        );


        Task task1 = new Task(
                "Complete Pomodoro App" // name
        );
        task1.setId("task1"); // _id
        task1.setUserId("user123"); // User ID
        task1.setProject("Work"); // project
        task1.setDueDate(LocalDate.of(2025, 1, 10)); // due date
        task1.setNotes("Focus on core functionality."); // notes
        task1.setPriority(TaskPriority.HIGH); // priority
        task1.setClocksRequired(3); // clocks required
        task1.setSubtasks(Arrays.asList("Implement Timer", "Connect to Backend")); // subtasks
        task1.setFlagColor("#FF5733"); // flag color

        Task task2 = new Task(
                "Write Unit Tests" // name
        );
        task2.setId("task2"); // _id
        task2.setUserId("user124"); // User ID
        task2.setProject("Development"); // project
        task2.setDueDate(LocalDate.of(2025, 1, 12)); // due date
        task2.setNotes("Cover all edge cases."); // notes
        task2.setPriority(TaskPriority.MEDIUM); // priority
        task2.setClocksRequired(2); // clocks required
        task2.setTags(Arrays.asList("testing", "backend")); // tags

        Task task3 = new Task(
                "Finalize UI/UX Design" // name
        );
        task3.setId("task3"); // _id
        task3.setUserId("user123"); // User ID
        task3.setProject("Design"); // project
        task3.setDueDate(LocalDate.of(2025, 1, 15)); // due date
        task3.setNotes("Ensure responsive layout."); // notes
        task3.setPriority(TaskPriority.LOW); // priority
        task3.setClocksRequired(1); // clocks required
        task3.setSubtasks(Arrays.asList("Update color scheme", "Optimize icons")); // subtasks

        Task task4 = new Task(
                "Deploy Application to Server" // name
        );
        task4.setId("task4"); // _id
        task4.setUserId("user125"); // User ID
        task4.setProject("Deployment"); // project
        task4.setDueDate(LocalDate.of(2025, 1, 20)); // due date
        task4.setNotes("Test before deployment."); // notes
        task4.setPriority(TaskPriority.HIGH); // priority
        task4.setCompleted(true); // completed status
        task4.setClocksRequired(3); // clocks required

        Task task5 = new Task(
                "Research Task Management Trends" // name
        );
        task5.setId("task5"); // _id
        task5.setUserId("user126"); // User ID
        task5.setProject("Research"); // project
        task5.setDueDate(LocalDate.of(2025, 1, 30)); // due date
        task5.setNotes("Read recent articles on productivity."); // notes
        task5.setPriority(TaskPriority.LOW); // priority
        task5.setClocksRequired(2); // clocks required
        task5.setTags(Arrays.asList("research", "productivity")); // tags


        mongoTemplate.save(task1);
        mongoTemplate.save(task2);
        mongoTemplate.save(task3);
        mongoTemplate.save(task4);
        mongoTemplate.save(task5);


        ClassroomRelationship classroomRelationship1 = new ClassroomRelationship(
                UUID.randomUUID().toString(),
                classroom1.getClassroomId(),
                instructor1.getUserId(),
                Arrays.asList(user2.getUserId(), user3.getUserId()
                ));

        ClassroomRelationship classroomRelationship2 = new ClassroomRelationship(
                UUID.randomUUID().toString(),
                classroom2.getClassroomId(),
                instructor1.getUserId(),
                Arrays.asList(user1.getUserId(), user3.getUserId()
                ));

        ClassroomRelationship classroomRelationship3 = new ClassroomRelationship(
                UUID.randomUUID().toString(),
                classroom3.getClassroomId(),
                instructor1.getUserId(),
                Arrays.asList(user1.getUserId(),user2.getUserId(), user3.getUserId()
                ));


        ParentChildRelationship relationship1 = new ParentChildRelationship(
                UUID.randomUUID().toString(),
                user1.getUserId(),
                Arrays.asList(user2.getUserId(), user3.getUserId())
        );


        /*
        mongoTemplate.save(user1);
        mongoTemplate.save(user2);
        mongoTemplate.save(user3);
        mongoTemplate.save(user4);


        mongoTemplate.save(instructor1);
        mongoTemplate.save(instructor2);



        mongoTemplate.save(classroom1);
        mongoTemplate.save(classroom2);
        mongoTemplate.save(classroom3);

        mongoTemplate.save(relationship1);
        mongoTemplate.save(classroomRelationship1);
        mongoTemplate.save(classroomRelationship2);
        mongoTemplate.save(classroomRelationship3);


        mongoTemplate.save(meeting2);
        mongoTemplate.save(meeting1);


        mongoTemplate.save(announcement2);
        mongoTemplate.save(announcement1);
*/
        System.out.println("Users collection seeded.");

        Message message1 = new Message("1", "user1", "user2", "Hello, how are you?", "2024-12-29T10:00:00", "false", MessageType.TEXT);
        Message message2 = new Message("2", "user2", "user1", "I'm good, thanks! How about you?", "2024-12-29T10:05:00", "true", MessageType.TEXT);
        Message message3 = new Message("3", "user1", "user3", "Happy holidays!", "2024-12-29T11:00:00", "false", MessageType.TEXT);
        Message message4 = new Message("4", "user3", "user1", "Thank you! Same to you!", "2024-12-29T11:10:00", "true", MessageType.TEXT);
/*
        mongoTemplate.save(message1);
        mongoTemplate.save(message2);
        mongoTemplate.save(message3);
        mongoTemplate.save(message4);*/
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
        flashcard1.setVisibility(FlashcardVisibility.PUBLIC);
        flashcard1.setImageUrl("https://picsum.photos/201");
        flashcardRepository.save(flashcard1);

        Flashcard flashcard2 = new Flashcard();
        flashcard2.setQuestion("[{\"insert\":\"What is 7 * 6?\\n\"}]"); // Delta format JSON string for the question
        flashcard2.setAnswer("[{\"insert\":\"42\\n\"}]"); // Delta format JSON string for the answer
        flashcard2.setDifficulty(2);
        flashcard2.setLastResponse(true);
        flashcard2.setSubject(subject1);
        flashcard2.setTopic(topic1);
        flashcard2.setVisibility(FlashcardVisibility.PUBLIC);
        flashcard2.setImageUrl("https://picsum.photos/201");

        flashcardRepository.save(flashcard2);

        Flashcard flashcard3 = new Flashcard();
        flashcard3.setQuestion("[{\"insert\":\"Solve for x: 2x = 10\\n\"}]"); // Delta format JSON string for the question
        flashcard3.setAnswer("[{\"insert\":\"5\\n\"}]"); // Delta format JSON string for the answer
        flashcard3.setDifficulty(2);
        flashcard3.setLastResponse(false);
        flashcard3.setSubject(subject1);
        flashcard3.setTopic(topic1);
        flashcard3.setVisibility(FlashcardVisibility.PRIVATE);
        flashcard3.setImageUrl("https://picsum.photos/201");
        flashcardRepository.save(flashcard3);

        Flashcard flashcard4 = new Flashcard();
        flashcard4.setQuestion("[{\"insert\":\"What is the square root of 16?\\n\"}]"); // Delta format JSON string for the question
        flashcard4.setAnswer("[{\"insert\":\"4\\n\"}]"); // Delta format JSON string for the answer
        flashcard4.setDifficulty(1);
        flashcard4.setLastResponse(true);
        flashcard4.setSubject(subject1);
        flashcard4.setTopic(topic1);
        flashcard4.setVisibility(FlashcardVisibility.PRIVATE);
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
