package it.almaviva.searen.planet.sensordata.socket;


import java.util.concurrent.CopyOnWriteArraySet;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.websocket.Session;

@ServerEndpoint("/optronic_status")         
@ApplicationScoped
public class OptronicSocket {

     private static final CopyOnWriteArraySet<Session> sessions = new CopyOnWriteArraySet<>();


    @OnOpen
    public void onOpen(Session session) {
        
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
       
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        sessions.remove(session);
       
    }

    @OnMessage
    public  void onMessage(String message) {
        broadcast(message);
    }

    public static void broadcast(String message) {
        sessions.forEach(s -> {
            try {
                s.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
