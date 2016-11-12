package com.iamsubhranil.personal.communication.simplex;

import java.io.*;
import java.net.Socket;

/**
 * Author : Nil
 * Date : 11/7/2016 at 1:19 PM.
 * Project : ClientServerBasics
 */
public class ClientServerThread extends CustomIOThread {

    private final Socket client;
    private final BufferedReader clientInputStream;
    private final PrintWriter clientOutputStream;
    private final String signature;

    public ClientServerThread(Socket socket, String sign) throws IOException {
        client = socket;
        clientInputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        clientOutputStream = new PrintWriter(socket.getOutputStream(), true);
        signature = sign + "->" + socket.getPort();
        setDaemon(true);
    }

    public void close() throws IOException {
        clientInputStream.close();
        clientOutputStream.close();
        client.close();
    }

    public void setReader(Reader r) {
        instructor = new BufferedReader(r);
    }

    public void setWriter(Writer w) {
        outputViewer = new PrintWriter(w, true);
    }

    public void run() {
        checkStreams();
        clientOutputStream.println("Welcome to the network!");
        try {
            String instruction = "";
            while (!instruction.equals("stop")) {
                String response;
                response = clientInputStream.readLine();
                outputViewer.println(signature + ": socket responded : " + response);
                instruction = instructor.readLine();
                outputViewer.println(signature + ": " + instruction);
                clientOutputStream.println(instruction);
            }
            close();
            outputViewer.println(signature + ": Connection terminated..");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
