import java.net.ServerSocket;
import java.net.Socket;

 class Connections {

    private static final int PORT = 6500;

     void startConnection() {

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket client = serverSocket.accept();
                Connection conn = new Connection(client);
                conn.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection terminated");
        }
    }
}
