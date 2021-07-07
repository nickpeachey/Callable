package com.example.callable;

import com.example.callable.application.MessagePopper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class CallableApplication {

    public static void main(String[] args) throws IOException, InterruptedException {

        try (ConfigurableApplicationContext ctx = SpringApplication.run(CallableApplication.class)) {
            final MessagePopper application = ctx.getBean(MessagePopper.class);

            Thread mainThread = new Thread(application);
            mainThread.start();
        }
    }

}
