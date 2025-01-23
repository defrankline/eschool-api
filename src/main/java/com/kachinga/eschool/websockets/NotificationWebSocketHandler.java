package com.kachinga.eschool.websockets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kachinga.eschool.dto.NotificationPayloadDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * NotificationWebSocketHandler handles WebSocket connections for billing
 * updates.
 * It extends TextWebSocketHandler to manage text-based WebSocket messages.
 */
@Slf4j
public class NotificationWebSocketHandler extends TextWebSocketHandler {

    // ObjectMapper instance for converting Java objects to JSON and vice versa.
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Thread-safe list to store active WebSocket sessions.
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    // BlockingQueue to manage messages to be sent to clients.
    private final BlockingQueue<MessageWrapper> messageQueue = new LinkedBlockingQueue<>();

    /**
     * Constructor initializes the NotificationWebSocketHandler and starts the
     * message
     * processing thread.
     */
    public NotificationWebSocketHandler() {
        new Thread(this::processQueue).start();
    }

    /**
     * Invoked after a WebSocket connection is established.
     *
     * @param session the WebSocket session that was established
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.info("Notifications WebSocket connection established: {}", session.getId());
        sessions.add(session); // Add the new session to the list of active sessions.
        log.info("Current active sessions after connection established: {}", sessions.size());
    }

    /**
     * Invoked after a WebSocket connection is closed.
     *
     * @param session the WebSocket session that was closed
     * @param status  the status code and reason for the closure
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("Notifications WebSocket connection closed: {}", session.getId());
        sessions.remove(session); // Remove the session from the list of active sessions.
        log.info("Current active sessions after connection closed: {}", sessions.size());
    }

    public void send(Long id, UUID uuid, String title, String message, LocalDateTime sentAt) throws IOException {
        String time = sentAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        NotificationPayloadDto notificationDto = new NotificationPayloadDto(id, uuid, title, message, time);
        String payload = objectMapper.writeValueAsString(notificationDto);
        if (sessions.isEmpty()) {
            log.info("No active WebSocket connections to send bill update.");
            return;
        }
        for (WebSocketSession session : sessions) {
            boolean sendNotification = messageQueue.offer(new MessageWrapper(session, new TextMessage(payload)));
            if (sendNotification) {
                log.info("Message offered successfully!");
            } else {
                log.info("Message was not offered successfully!");
            }
        }
    }

    /**
     * Processes the message queue and sends messages to the appropriate WebSocket
     * sessions.
     */
    private void processQueue() {
        while (true) {
            try {
                // Retrieve and remove the head of the queue.
                MessageWrapper messageWrapper = messageQueue.take();
                WebSocketSession session = messageWrapper.getSession();
                TextMessage message = messageWrapper.getMessage();

                if (session.isOpen()) {
                    log.info("Sending message to session: {}", session.getId());
                    // Send the message if the session is open.
                    session.sendMessage(message);
                } else {
                    log.warn("Session is closed, cannot send message: {}", session.getId());
                }
            } catch (InterruptedException | IOException e) {
                // Log any exceptions that occur during message processing.
                log.error("Error processing message queue", e);
            }
        }
    }

    /**
     * Inner class to wrap a WebSocket session and its corresponding message.
     */
    private static class MessageWrapper {
        private final WebSocketSession session;
        private final TextMessage message;

        /**
         * Constructor for MessageWrapper.
         *
         * @param session the WebSocket session
         * @param message the text message to be sent
         */
        public MessageWrapper(WebSocketSession session, TextMessage message) {
            this.session = session;
            this.message = message;
        }

        /**
         * @return the WebSocket session
         */
        public WebSocketSession getSession() {
            return session;
        }

        /**
         * @return the text message
         */
        public TextMessage getMessage() {
            return message;
        }
    }
}
