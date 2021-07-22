package com.kafka.stomp.domain.chatting.entity;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import sun.util.calendar.BaseCalendar.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table
@Builder
@Getter
@Setter
public class ChatRoom{

  // 방 고유 번호
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;

  @Column
  @DateTimeFormat(iso = ISO.DATE)
  private ZonedDateTime createdTime;

  @Column(length = 100)
  private String roomName;

  @Column(length = 100)
  private String password;

  @Column
  private Integer memberMax;

  @Column
  private Integer memberNum;



}
