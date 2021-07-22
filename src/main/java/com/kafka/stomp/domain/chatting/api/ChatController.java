package com.kafka.stomp.domain.chatting.api;

import com.kafka.stomp.domain.chatting.dto.ChatRoomReq;
import com.kafka.stomp.domain.chatting.service.ChatService;
import com.sun.tools.javac.util.List;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
  private final ChatService chatService;

  @PostMapping
  public ChatRoomReq createRoom(@RequestParam String name) {
    return chatService.createRoom(name);
  }


}
