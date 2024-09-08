import java.io.*;
import java.net.*;

public class BroadcastClient {
    private final String host;
    private final int port;

    public BroadcastClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        System.out.println("Connecting to Broadcast Server on " + host + ":" + port);
        try (
            Socket socket = new Socket(host, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Connected to server. Type messages to broadcast (or 'exit' to quit):");
            
            // Thread for receiving messages
            Thread receiveThread = new Thread(() -> {
                String serverMessage;
                try {
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println("Received: " + serverMessage);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            });
            receiveThread.start();

            // Main thread for sending messages
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                if ("exit".equalsIgnoreCase(userInput)) {
                    out.println("exit");
                    break;
                }
                out.println(userInput);
            }
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
        System.out.println("Client disconnected.");
    }
}
