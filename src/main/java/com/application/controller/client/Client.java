package com.application.controller.client;

import javax.ejb.Stateless;
import javax.websocket.*;
import javax.ws.rs.container.AsyncResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ClientEndpoint
@Stateless
public class Client {

    private final String serverPath = "ws://localhost:8080/ReactiveWebsocket/websocketServer";

    private Session session;
    private AsyncResponse asyncResponse;

    public Client() {}

    public void connect() {
        WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
        try {
            webSocketContainer.connectToServer(this, new URI(serverPath));
        } catch (URISyntaxException | DeploymentException | IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void setAsyncResponse(AsyncResponse asyncResponse) {
        this.asyncResponse = asyncResponse;
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        asyncResponse.resume(message);
    }

    public void send(String message) {
        session.getAsyncRemote().sendText(message);
    }

    public void close() {
        try {
            session.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
