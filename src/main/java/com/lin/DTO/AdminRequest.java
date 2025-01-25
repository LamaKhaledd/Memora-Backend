package com.lin.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "adminRequests")
public class AdminRequest {
    @Id
    private String id;
    private String email;
    private String username;
    private String requestDate;
    private boolean isReviewed;
    private boolean isApproved;
}
