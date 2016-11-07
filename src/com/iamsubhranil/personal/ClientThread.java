package com.iamsubhranil.personal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Author : Nil
 * Date : 11/7/2016 at 2:15 PM.
 * Project : ClientServerBasics
 */
public class ClientThread extends Thread {

    private final Socket socket;
    private final String signature;

    public ClientThread(Socket s) {
        socket = s;
        signature = "#socket@" + socket.getLocalSocketAddress().toString();
        setDaemon(true);
    }

    public void run() {
        try {
            BufferedReader clientInputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter clientOutputStream = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader instructor = new BufferedReader(new InputStreamReader(System.in));
            String serverResponse;
            System.out.println(signature + ": Socket initialized..\n" + signature + ": Waiting for server response..");
            while (!((serverResponse = clientInputStream.readLine()).equals("stop"))) {
                System.out.println(signature + ": Server responded : " + serverResponse);
                System.out.print(signature + "~reply: ");
                String instruction = instructor.readLine();
                clientOutputStream.println(instruction);
            }
            System.out.println(signature + ": Connection terminated by server!");
            clientInputStream.close();
            clientOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
