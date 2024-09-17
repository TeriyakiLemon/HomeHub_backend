package com.tianchen.homehub_backend.Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new HashMap<>();
    private final ObjectMapper objectMapper;

    public ChatWebSocketHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username = (String) session.getAttributes().get("username");
        if (username != null) {
            // 打印当前会话列表，确保没有覆盖现有的用户会话
            if (sessions.containsKey(username)) {
                System.out.println("User " + username + " already connected. Updating session.");
            }
            sessions.put(username, session);  // 添加或更新用户的 WebSocket session
            System.out.println("User " + username + " connected. Session ID: " + session.getId());
        } else {
            System.out.println("No username found in WebSocket session attributes. Session ID: " + session.getId());
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String username = (String) session.getAttributes().get("username");
        if (username != null) {
            String payload = message.getPayload();

            // 解析 JSON 数据
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> messageData = objectMapper.readValue(payload, Map.class);

            String receiverUsername = (String) messageData.get("receiverUsername");
            String messageContent = (String) messageData.get("content");

            // 记录解析后的数据
            System.out.println("Parsed message data: " + messageData);

            // 发送消息给接收方
            WebSocketSession receiverSession = sessions.get(receiverUsername);
            if (receiverSession != null && receiverSession.isOpen()) {
                receiverSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(messageData)));
                System.out.println("Message sent from " + username + " to " + receiverUsername);
            } else {
                System.out.println("Message could not be sent to " + receiverUsername + " (session not found or closed)");
            }

            // 这里不再发送消息给发送方
        } else {
            System.out.println("No username found in WebSocket session attributes");
        }
    }





    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = (String) session.getAttributes().get("username");
        if (username != null) {
            sessions.remove(username);  // 移除断开连接的用户
            System.out.println("User " + username + " disconnected. Session ID: " + session.getId());
        } else {
            System.out.println("No username found in WebSocket session attributes. Session ID: " + session.getId());
        }
    }


}
