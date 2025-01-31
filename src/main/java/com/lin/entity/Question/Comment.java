package com.lin.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    private String commentId;
    private String content; // The comment text
    private String userId; // The user (instructor) who commented
    private String createdAt;
    private List<String> likes; // List of user IDs who liked this comment
    private List<Reply> replies; // Replies to this comment
    private boolean isPinned;
}
