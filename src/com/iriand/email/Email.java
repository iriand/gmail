package com.iriand.email;

/**
 * Created By Andrew Ben
 * 1/6/13 10:56 PM
 */
public interface Email {
    void send(String from, String[] recipientsTo, String[] recipientsCC, String[] recipientsBCC, String subject, String body, String[] files);
}
