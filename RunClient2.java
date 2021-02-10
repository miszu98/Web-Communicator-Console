

public class RunClient2 {
    public static void main(String[] args) {
        var client = new Client("Roman", "127.0.0.2");
        client.connect();
    }
}

