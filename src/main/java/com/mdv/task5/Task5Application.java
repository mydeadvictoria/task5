package com.mdv.task5;

import com.mdv.task5.service.ConsumerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class Task5Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Task5Application.class, args);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ConsumerService consumerService = context.getBean(ConsumerService.class);

        executorService.execute(consumerService::poll);
    }

}
