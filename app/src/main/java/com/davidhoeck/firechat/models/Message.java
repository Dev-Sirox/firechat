package com.davidhoeck.firechat.models;

import java.security.Timestamp;
import java.text.SimpleDateFormat;

import static java.lang.System.currentTimeMillis;

/**
 * The model for a basic message
 * @author David Höck
 */
public class Message {

    /**
     * The Name of the user who sent the message
     */
    public String parentUserName;

    /**
     * The UserId of the user who sent the message
     */
    public String parentUserId;

    /**
     * The actual message
     */
    public String message;

    /**
     * Datetime of creation
     */
    public String createdAt;

    /**
     * The unique messageId
     */
    public String messageId;

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

    /**
     * Generates a messageId for this message
     * @return String messageId
     */
    private String generateMessageId(){

        String messageId = null;

        return messageId;
    }

    private String generateCreatedAt(){
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        return timestamp;
    }
}
