package com.iamsubhranil.personal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Author : Nil
 * Date : 11/7/2016 at 1:19 PM.
 * Project : ClientServerBasics
 */
public class ClientServerThread extends Thread {

    private final Socket client;
    private final BufferedReader clientInputStream;
    private final PrintWriter clientOutputStream;
    private final BufferedReader instructor;
    private final String signature;

    public ClientServerThread(Socket socket, BufferedReader bufferedReader, String sign) throws IOException {
        client = socket;
        clientInputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        clientOutputStream = new PrintWriter(socket.getOutputStream(), true);
        instructor = bufferedReader == null ? new BufferedReader(new InputStreamReader(System.in)) : bufferedReader;
        signature = sign + "->" + socket.getPort();
        if (instructor != bufferedReader) {
            System.out.println(signature + ": Socket at " + client.getPort() + " is receiving from System.in..");
        }
        setDaemon(true);
    }

    public void close() throws IOException {
        clientInputStream.close();
        clientOutputStream.close();
        client.close();
    }

    public void run() {
        clientOutputStream.println("Welcome to the network!");
        try {
            String instruction = "";
            while (!instruction.equals("stop")) {
                String response;
                response = clientInputStream.readLine();
                System.out.println(signature + ": socket responded : " + response);
                System.out.print(signature + "~reply: ");
                instruction = instructor.readLine();
                clientOutputStream.println(instruction);
            }
            close();
            System.out.println(signature + ": Connection terminated..");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
