package com.example.callable.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class MessagePopper implements Runnable {

    @Autowired
    private JmsTemplate template;

    @Value("${activemq-queue}")
    private String queueName;

    private ExecutorService threadPool;

    @Override
    public void run() {
        System.out.println("application running");
        threadPool = Executors.newCachedThreadPool();
        try {
            this.runProcesses();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * For each vehicle, a thread is started which simulates a journey for that vehicle.
     * When all vehicles have completed, we start all over again.
     *
     * @throws InterruptedException
     */
    private void runProcesses() throws InterruptedException {
        boolean stillRunning = true;
        while (stillRunning) {
            List<Callable<Object>> calls = new ArrayList<>();

            for (int i= 1; i < 1000; i++) {
                calls.add(new Message("simple title " + i,null,template,queueName));
                System.out.println("new message created");
            }
            System.out.println("passing to invoke");
            threadPool.invokeAll(calls);

            if (threadPool.isShutdown()) {
                stillRunning = false;
            }
        }

    }

    public void finish() {threadPool.shutdownNow();}
}
