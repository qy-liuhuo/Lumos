package io.github.qylh.lumos;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private ChatClient chatClient;

    public String getResponse(String msg){
        return chatClient.prompt().user(msg).call().content();
    }

}
