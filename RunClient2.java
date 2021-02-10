package com.Zajecia.Lab5.WebCommunicator_5_5_i_5_6;

public class RunClient2 {
    public static void main(String[] args) {
        var client = new Client("Roman", "127.0.0.2");
        client.connect();
    }
}

