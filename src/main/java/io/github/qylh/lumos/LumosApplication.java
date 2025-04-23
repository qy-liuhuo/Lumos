package io.github.qylh.lumos;

import io.github.qylh.lumos.tools.MySQLProperties;
import io.github.qylh.lumos.tools.MySQLTool;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties({MySQLProperties.class})
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
