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
public class ClientThread extends CustomIOThread {

    private final Socket socket;
    private final String signature;

    public ClientThread(Socket s) {
        socket = s;
        //    signature = "#socket@" + socket.getLocalSocketAddress().toString();
        signature = "Me";
        setDaemon(true);
    }

    public void run() {
        try {
            BufferedReader clientInputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter clientOutputStream = new PrintWriter(socket.getOutputStream(), true);
            checkStreams();
            String serverResponse;
            outputViewer.println(signature + ": Socket initialized..\n" + signature + ": Waiting for server response..");
            while (!((serverResponse = clientInputStream.readLine()).equals("stop"))) {
                outputViewer.println("Server: " + serverResponse);
                String instruction = instructor.readLine();
                outputViewer.println(signature + ": " + instruction);
                clientOutputStream.println(instruction);
            }
            outputViewer.println(signature + ": Connection terminated by server!");
            clientInputStream.close();
            clientOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
