package io.github.qylh.lumos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LumosApplication {

    public static void main(String[] args) {
        SpringApplication.run(LumosApplication.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(ChatService chatService) {
        return args -> {
            System.out.println("Input:");
            String input = System.console().readLine();
            while (!input.equalsIgnoreCase("exit")) {
                String response = chatService.getResponse(input);
                System.out.println("Response: " + response);
                System.out.println("Input:");
                input = System.console().readLine();
            }
        };
    }

}
