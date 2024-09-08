public class BroadcastApp {
    private static final int PORT = 5000;

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: broadcast-server [start|connect]");
            return;
        }

        String command = args[0];
        switch (command) {
            case "start":
                new BroadcastServer(PORT).start();
                break;
            case "connect":
                new BroadcastClient("localhost", PORT).start();
                break;
            default:
                System.out.println("Invalid command. Use 'start' or 'connect'.");
        }
    }
}
