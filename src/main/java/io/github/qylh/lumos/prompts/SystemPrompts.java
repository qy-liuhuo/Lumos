package io.github.qylh.lumos.prompts;

import org.springframework.ai.chat.prompt.PromptTemplate;

public class SystemPrompts {

    public static final String ROLE_PROMPT = "You are a data analyst.";

    public static final PromptTemplate PROMPT_FOR_SQL = new PromptTemplate("Firstly, you need to build a query script based on the user's input to get the data. The user's input is: {user_input}");

}
