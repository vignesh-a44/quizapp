package com.vignesh.a44.quizapp.Schema;

public class MailQueueMessage {
    private String toAddress;
    private String topic;
    private String message;

    public MailQueueMessage(String toAddress, String topic, String message) {
        this.toAddress = toAddress;
        this.topic = topic;
        this.message = message;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "content: {" +
                "toAddress: '" + toAddress + '\'' +
                ", topic: '" + topic + '\'' +
                ", message: '" + message + '\'' +
                '}';
    }
}
