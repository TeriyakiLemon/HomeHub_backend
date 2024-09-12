package com.tianchen.homehub_backend.Handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new HashMap<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username = (String) session.getAttributes().get("username");
        if (username != null) {
            sessions.put(username, session);
            System.out.println("User " + username + " connected");
        } else {
            System.out.println("No username found in WebSocket session attributes");
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String username = (String) session.getAttributes().get("username");
        if (username != null) {
            String payload = message.getPayload();
            String[] parts = payload.split(":", 2);
            if (parts.length == 2) {
                String receiverUsername = parts[0];
                String MessageContent = parts[1];

                WebSocketSession receiverSession = sessions.get(receiverUsername);
                if (receiverSession != null && receiverSession.isOpen()) {
                    receiverSession.sendMessage(new TextMessage(username + ": " + MessageContent));
                } else {
                    session.sendMessage(new TextMessage("User " + receiverUsername + " is not online"));
                }
            } else {
                session.sendMessage(new TextMessage("Invalid message format"));
            }
        } else {
            System.out.println("No username found in WebSocket session attributes");
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = (String) session.getAttributes().get("username");
        if (username != null) {
            sessions.remove(username);
            System.out.println("User " + username + " disconnected");
        } else {
            System.out.println("No username found in WebSocket session attributes");
        }
    }

}
