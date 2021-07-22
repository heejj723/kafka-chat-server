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
import org.springframework.web.socket.WebSocketSession;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomReq {
  private String roomId;
  private String roomName;
  private Set<WebSocketSession> sessions;

  public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
    if (chatMessage.getMessageType().equals(MessageType.ENTER)) {
      sessions.add(session);
      chatMessage.setMessage("'" + chatMessage.getSender() + "' 님이 입장하셨습니다.");
    } else {
      chatMessage.setMessage("[" + chatMessage.getSender() + "]: " + chatMessage.getMessage());
    }

    sendMessage(chatMessage, chatService);
  }

  private void sendMessage(ChatMessage chatMessage, ChatService chatService) {
    sessions.parallelStream().forEach(session -> chatService.sendMessage(session, chatMessage));
  }
}
