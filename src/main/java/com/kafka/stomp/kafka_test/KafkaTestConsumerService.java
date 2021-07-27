package com.kafka.stomp.kafka_test;

import java.io.IOException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaTestConsumerService {

  @KafkaListener(topics = "test", groupId = "group-id-heej")
  public void consume(String message) throws IOException {
    System.out.println("Recieve Message: " + message);
  }
}
