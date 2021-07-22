package com.kafka.stomp.domain.chatting.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.stomp.domain.chatting.dto.ChatMessage;
import com.kafka.stomp.domain.chatting.dto.ChatRoomReq;
import com.sun.tools.javac.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

  private final ObjectMapper objectMapper;

  private Set<WebSocketSession> allSessions = new HashSet<>();
  private Map<String, ChatRoomReq> chatRooms;

  @PostConstruct
  private void init() {
    chatRooms = new LinkedHashMap<>();
  }

  public ChatRoomReq findRoomById(String roomId) {
    return chatRooms.get(roomId);
  }

  public ChatRoomReq createRoom(String roomName) {
    String randomId = UUID.randomUUID().toString();
    ChatRoomReq chatRoomReq = ChatRoomReq.builder()
      .roomId(randomId)
      .roomName(roomName)
      .build();
    chatRooms.put(randomId, chatRoomReq);
    return chatRoomReq;
  }

  public <T> void sendMessage(WebSocketSession session, T message) {
    try {
      session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }

  public void addSession(WebSocketSession session) {
    allSessions.add(session);
  }

  public void removeSession(WebSocketSession session) {
    allSessions.remove(session);

    for (String key : chatRooms.keySet()) {
      chatRooms.get(key).handleClosedSession(session);
    }
  }

  public ArrayList<ChatRoomReq> findAllRoom() {
    return new ArrayList<ChatRoomReq>(chatRooms.values());
  }
}
