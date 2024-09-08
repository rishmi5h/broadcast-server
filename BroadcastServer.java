import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class BroadcastServer {
    private final int port;
    private final Set<ClientHandler> clients = ConcurrentHashMap.newKeySet();
    private ServerSocket serverSocket;

    public BroadcastServer(int port) {
        this.port = port;
    }

    public void start() {
        System.out.println("Starting Broadcast Server on port " + port);
        try {
            serverSocket = new ServerSocket(port);
            Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));

            while (!serverSocket.isClosed()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                    clients.add(clientHandler);
                    new Thread(clientHandler).start();
                    System.out.println("New client connected: " + clientSocket.getInetAddress());
                } catch (IOException e) {
                    if (!serverSocket.isClosed()) {
                        System.out.println("Error accepting client connection: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    public void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    private void shutdown() {
        System.out.println("Shutting down server...");
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            for (ClientHandler client : clients) {
                client.close();
            }
            clients.clear();
        } catch (IOException e) {
            System.out.println("Error during server shutdown: " + e.getMessage());
        }
    }
}
