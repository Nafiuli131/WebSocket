package com.example.demo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import java.util.Random;

@Controller
public class NotificationController {

    private final SimpMessagingTemplate messagingTemplate;
    private final Random rand = new Random();

    //constructor
    public NotificationController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/notifications")
    public String sendMessage(String message) {
        System.out.println("message : " + message);
        return message;
    }

    @Scheduled(fixedRate = 1000)  // Sends a new random value every 1 second
    public void sendRandomValue() {
        int randomValue = rand.nextInt(1000);  // Random value between 0 and 999
        messagingTemplate.convertAndSend("/topic/randomValue", randomValue);  // Send to the WebSocket topic
    }
}
