import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final BroadcastServer server;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket socket, BroadcastServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Received from " + socket.getInetAddress() + ": " + message);
                server.broadcast(message, this);
            }
        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
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
            System.out.println("Client disconnected: " + socket.getInetAddress());
        } catch (IOException e) {
            System.out.println("Error closing client connection: " + e.getMessage());
        }
    }
}
