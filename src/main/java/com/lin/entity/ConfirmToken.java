package com.lin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "confirmation_tokens")
public class ConfirmToken {
    @Id
    private String tokenId;

    private String confirmationToken;
    private Date createdDate;
    private String userId; 

    public ConfirmToken(String userId, String token) {
        this.userId = userId;
        this.confirmationToken = token;
        this.createdDate = new Date();
    }
}
