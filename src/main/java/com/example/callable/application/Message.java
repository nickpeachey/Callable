package com.example.callable.application;

import org.springframework.jms.core.JmsTemplate;

import java.util.List;
import java.util.concurrent.Callable;

public class Message implements Callable<Object> {

    private String title;
    private List<String> subjects;
    private JmsTemplate template;
    private String queueName;

    public Message(String title, List<String> subjects, JmsTemplate template, String queueName) {
        this.title = title;
        this.subjects = subjects;
        this.template = template;
        this.queueName = queueName;
    }
    @Override
    public Object call() throws Exception {
        while (true) {
            System.out.println("call on object made");
            sendToQueue(this.title);
        }
    }

    private void sendToQueue(String value) {
        System.out.println("sending template to queue");
        this.template.convertAndSend(queueName, value);

    }
}
