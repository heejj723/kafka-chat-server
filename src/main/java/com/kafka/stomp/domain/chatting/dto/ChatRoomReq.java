package com.kafka.stomp.domain.chatting.dto;

import com.kafka.stomp.domain.chatting.service.ChatService;
import com.kafka.stomp.global.enums.MessageType;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class ChatRoomReq {
  private String roomId;
  private String roomName;

  @Builder.Default
  private Set<WebSocketSession> sessions = new HashSet<>();

  public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService)
    throws IllegalAccessException {
    if (chatMessage.getMessageType().equals(MessageType.ENTER)) {
      sessions.add(session);
      chatMessage.setMessage("'" + chatMessage.getSender() + "' 님이 입장하셨습니다.");
    }

    else{
       if (!sessions.contains(session)) {
         throw new IllegalAccessException("'" + chatMessage.getSender() + "' 는 이 방에 속해 있지 않습니다.");
       }
      chatMessage.setMessage("[" + chatMessage.getSender() + "]: " + chatMessage.getMessage());
    }

    sendMessage(chatMessage, chatService);
  }

  private void sendMessage(ChatMessage chatMessage, ChatService chatService) {
    sessions.parallelStream().forEach(session -> {
        chatService.sendMessage(session, chatMessage);
      }
    );
  }

  public void handleClosedSession(WebSocketSession closedSession) {
    sessions.remove(closedSession);
  }

}
