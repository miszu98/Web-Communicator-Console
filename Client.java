
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client implements Network {

    private Socket socket;
    private String IP;
    private DataInputStream inputStream;
    private DataInputStream input;
    private DataOutputStream outputStream;
    private String nickname;


    public Client(String nickname, String IP) {
        this.IP = IP;
        this.nickname = nickname;
    }

    public void connect() {
        try {
            socket = new Socket(IP, PORT);
            input = new DataInputStream(System.in);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());

            Thread response = new Thread(() -> {
               while (true) {
                   if (!socket.isClosed()) {
                       try {
                           System.out.println(inputStream.readUTF());
                       } catch (IOException exception) {
                           System.out.println("Thread error: " + exception);
                       }
                   } else {
                       break;
                   }
               }
            });
            response.start();

            String message = "";
            while (!message.equals("exit")) {
                message = input.readLine();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                Date date = new Date();
                outputStream.writeUTF(simpleDateFormat.format(date) + " " + nickname + ": " + message);
            }

            outputStream.close();
            inputStream.close();
            input.close();


        } catch (IOException e) {
            System.out.println("Client error: " + e);
        }
    }
}
