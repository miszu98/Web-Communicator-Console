package com.Zajecia.Lab5.WebCommunicator_5_5_i_5_6;

public class RunClient1 {
    public static void main(String[] args) {
        var client = new Client("Michal", "127.0.0.1");
        client.connect();
    }
}
