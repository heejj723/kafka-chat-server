package com.kafka.stomp.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.stomp.domain.chatting.dto.ChatMessage;
import com.kafka.stomp.domain.chatting.dto.ChatRoomReq;
import com.kafka.stomp.domain.chatting.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSockChatHandler extends TextWebSocketHandler {

  private final ObjectMapper objectMapper;
  private final ChatService chatService;
  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    String payload = message.getPayload();
    log.info("payload: {}", payload);

    ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
    ChatRoomReq chatRoom = chatService.findRoomById(chatMessage.getRoomId());

//    log.info("chatMessage: {}, chatRoom: {}", chatMessage.getMessage(), chatRoom.getRoomName());

    chatRoom.handleActions(session, chatMessage, chatService);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    chatService.removeSession(session);
  }
}
