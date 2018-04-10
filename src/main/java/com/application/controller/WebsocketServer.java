package com.application.controller;


import javax.ejb.Singleton;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Singleton
@ServerEndpoint(value = "/websocketServer")
public class WebsocketServer {

    private final List<Session> sessionList = Collections.synchronizedList(new ArrayList<>());

    @OnOpen
    public void onOpen(Session session) {
        sessionList.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        sessionList.remove(session);
    }

    @OnError
    public void onError(Throwable error) {
        System.err.println(error.getMessage());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        sessionList.stream().filter( p -> p.isOpen())
                .forEachOrdered( p ->
                        p.getAsyncRemote().sendText(message + " - Total sessions: " + sessionList.size()));
    }
}
