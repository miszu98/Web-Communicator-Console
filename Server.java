
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Network {

    private Socket socket;
    private ServerSocket serverSocket;
    private List<Socket> clients = new ArrayList<>();


    public Server() {
        try {
            serverSocket = new ServerSocket(PORT);
            acceptIncomingConnections();
        } catch (IOException e) {
            System.out.println("Error server: " + e);
        }
    }

    public void acceptIncomingConnections() {
        try {
            while (true) {
                Thread thread = new Thread(() -> {
                    try {
                        socket = serverSocket.accept();
                        System.out.println("Connected: " + socket.getLocalSocketAddress());
                        clients.add(socket);
                        run(socket);

                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                });
                thread.start();
                Thread.sleep(5000); // slow down loop, protection againts out of memory
            }
        } catch (InterruptedException e) {
            System.out.println("acceptIncomingConnections: " + e);
        }
    }


    public void run(Socket client) {
        try {
            DataOutputStream outputStream = new DataOutputStream(client.getOutputStream());
            DataInputStream inputStream = new DataInputStream(client.getInputStream());
            outputStream.writeUTF("Hello, server here!");

            String message = "";
            while (!message.equals("exit")) {
                message = inputStream.readUTF();
                System.out.println(message);

                for (Socket socket : clients) {
                    DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                    output.writeUTF(message);
                    outputStream.flush();
                }
            }
            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            System.out.println("Server error: " + e);
        }
    }
}
