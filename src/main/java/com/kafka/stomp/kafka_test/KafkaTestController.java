package com.kafka.stomp.kafka_test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaTestController {

  @Autowired
  private KafkaTestProducerService kafkaTestProducerService;

  @PostMapping(value = "/sendMessage")
  public void sendMessage(@RequestParam(value = "message") String message) {
    kafkaTestProducerService.sendMessage(message);
  }
}
