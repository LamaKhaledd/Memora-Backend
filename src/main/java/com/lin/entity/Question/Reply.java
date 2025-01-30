package com.lin.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
    private String replyId;
    private String content; // The reply text
    private String userId; // The user who replied
    private String createdAt;
}
