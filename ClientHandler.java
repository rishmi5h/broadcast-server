import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final BroadcastServer server;
    private PrintWriter out;
    private BufferedReader in;
    private final String clientId;

    public ClientHandler(Socket socket, BroadcastServer server) {
        this.socket = socket;
        this.server = server;
        this.clientId = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String message;
            while ((message = in.readLine()) != null) {
                if ("exit".equalsIgnoreCase(message)) {
                    System.out.println("Client " + clientId + " requested to exit.");
                    break;
                }
                System.out.println("Received from " + clientId + ": " + message);
                server.broadcast(clientId + ": " + message, this);
            }
        } catch (IOException e) {
            System.out.println("Error handling client " + clientId + ": " + e.getMessage());
        } finally {
            close();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void close() {
        try {
            server.removeClient(this);
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
            System.out.println("Client disconnected: " + clientId);
        } catch (IOException e) {
            System.out.println("Error closing client connection " + clientId + ": " + e.getMessage());
        }
    }

    public String getClientId() {
        return clientId;
    }
}
