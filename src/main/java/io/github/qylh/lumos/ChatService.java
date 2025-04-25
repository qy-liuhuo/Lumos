package io.github.qylh.lumos;


import io.github.qylh.lumos.model.SQL;
import io.github.qylh.lumos.prompts.SystemPrompts;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service
public class ChatService {

    @Autowired
    private ChatClient chatClient;

    public String getResponse(String msg){
        PromptChatMemoryAdvisor promptChatMemoryAdvisor = new PromptChatMemoryAdvisor(new InMemoryChatMemory());
        SQL sql = chatClient.prompt()
                .system(SystemPrompts.ROLE_PROMPT)
                .advisors(new SimpleLoggerAdvisor(), promptChatMemoryAdvisor)
                .user(SystemPrompts.PROMPT_FOR_SQL.create(Map.of("user_input", msg)).getContents())
                .call()
                .entity(SQL.class);
        if(sql == null){
            return "No SQL generated";
        }else{
            //TODO checkSQL

            //TODO executeSQL
            return sql.getSql();
        }

    }
    public Flux<String> getStreamResponse(String msg){
        return chatClient.prompt().user(msg).stream().content();
    }



}
