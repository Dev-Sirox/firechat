package com.davidhoeck.firechat.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.currentTimeMillis;

/**
 * The model for a basic message
 * @author David Höck
 */
@IgnoreExtraProperties
public class Message {


    public String parentUserName;
    public String parentUserId;
    public String message;
    public String createdAt;
    public String messageId;


    public Message(){
        // Default constructor required for calls to DataSnapshot.getValue(Message.class)
    }

    /**
     * The constructor
     * @param message
     * @param parentUserId
     * @param parentUserName
     *
     */
    public Message(String message, String parentUserId, String parentUserName) {
        this.message = message;
        this.parentUserId = parentUserId;
        this.messageId = this.generateMessageId();
        this.createdAt = this.generateCreatedAt();
        this.parentUserName = parentUserName;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("parentUserId", this.getParentUserId());
        result.put("parentUserName", this.getParentUserName());
        result.put("message", this.getMessage());
        result.put("messageId", this.getMessageId());
        result.put("createdAt", this.getCreatedAt());
        return result;
    }


    public String getParentUserName() {
        return parentUserName;
    }

    public String getParentUserId() {
        return parentUserId;
    }

    public String getMessage() {
        return message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getMessageId() {
        return messageId;
    }

    /**
     * Generates a messageId for this message
     * @return String messageId
     */
    private String generateMessageId(){

        String messageId = "msgId";
        return messageId;
    }

    private String generateCreatedAt(){
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        return timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "parentUserName='" + parentUserName + '\'' +
                ", parentUserId='" + parentUserId + '\'' +
                ", message='" + message + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", messageId='" + messageId + '\'' +
                '}';
    }
}
