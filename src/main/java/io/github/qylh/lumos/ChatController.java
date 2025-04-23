package io.github.qylh.lumos;


import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/chat")
    public String chat(@RequestParam(value = "msg", defaultValue = "Hello") String msg) {
        return chatService.getResponse(msg);
    }

    @GetMapping("/chat/stream")
    public Flux<String> getStreamResponse(@RequestParam(value = "msg", defaultValue = "Hello") String msg) {
        return chatService.getStreamResponse(msg);
    }

}
