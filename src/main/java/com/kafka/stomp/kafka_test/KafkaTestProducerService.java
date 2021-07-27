package com.kafka.stomp.kafka_test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaTestProducerService {

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  public void sendMessage(String message) {
    System.out.println("Send Message: " + message);
    this.kafkaTemplate.send("heej", message);
  }
}
