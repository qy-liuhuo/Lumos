package io.github.qylh.lumos.config;

import io.github.qylh.lumos.tools.MySQLTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(OpenAiChatModel chatModel, MySQLTool mySQLTool) {
        ToolCallbackProvider tools = MethodToolCallbackProvider.builder().toolObjects(mySQLTool).build();
        return ChatClient.builder(chatModel).defaultTools(tools).build();
    }
}
