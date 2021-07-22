package com.kafka.stomp.domain.chatting.dto;

import com.kafka.stomp.global.enums.MessageType;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

  private MessageType messageType;
  private String roomId;
  private String sender;
  private String message;


}
