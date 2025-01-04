
package com.lin.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "messages") 
public class Message {
    @Id
    private String messageId; 
    private String fromId; 
    private String toId; 
    private String msg;
    private String sent;
    private String read;
    private MessageType type;
}
