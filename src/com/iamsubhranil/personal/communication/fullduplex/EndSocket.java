package com.iamsubhranil.personal.communication.fullduplex;

import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Author : Nil
 * Date : 11/12/2016 at 6:54 PM.
 * Project : ClientServerBasics
 */
public class EndSocket extends CustomIO {

    private final Socket endSocket;
    private final ExecutorService readExecutorService;
    private final ExecutorService writeExecutorService;
    private final InputStream inputStream;
    private final OutputStream outputStream;
    private final Object locker;
    private String sign = "Server";
    private boolean isServer = true;
    private boolean prevWasRep = false;
    private boolean hasLocker = false;

    public EndSocket(Socket s, boolean isServe) throws IOException {
        this(s, isServe, null);
    }

    public EndSocket(Socket s, boolean isServr, Object lock) throws IOException {
        endSocket = s;
        inputStream = endSocket.getInputStream();
        outputStream = endSocket.getOutputStream();
        sign = isServr ? "Client" : "Server";
        isServer = isServr;
        locker = lock == null ? new Object() : lock;
        hasLocker = lock != null;
        ThreadFactory daemonThreadFactory = r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        };
        readExecutorService = Executors.newSingleThreadExecutor(daemonThreadFactory);
        writeExecutorService = Executors.newSingleThreadExecutor(daemonThreadFactory);
    }

    public void setStage(Stage s) {
        s.setOnCloseRequest(e -> {
            try {
                close();
            } catch (IOException ignored) {
            }
        });
    }

    public void close() throws IOException {
        synchronized (locker) {
            inputStream.close();
            readExecutorService.shutdownNow();
            writeExecutorService.shutdownNow();
            outputStream.close();
            endSocket.close();
            if (hasLocker) {
                try {
                    locker.notify();
                } catch (Exception ignored) {
                }
            }
        }
    }

    public void startReadExecutor() {
        checkStreams();
        if (!isServer) {
            writeAll("Thank you!");
        }

        readExecutorService.execute(() -> {
            while (!endSocket.isClosed()) {
                try {
                    if (inputStream.available() > 0) {
                        int ch = 0;
                        if (!prevWasRep) {
                            outputViewer.print("\n");
                        }
                        outputViewer.print("\n" + sign + " : ");
                        ch = inputStream.read();
                        while (ch != -1 && ch != '\n') {
                            try {
                                outputViewer.print(String.format("%c", ch));
                            } catch (Exception r) {
                                System.out.println("Error in format : " + ch);
                            }
                            prevWasRep = true;
                            ch = inputStream.read();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error in EndSocket:68");
                    e.printStackTrace();
                }
            }
        });
    }

    public Socket getSocket() {
        return endSocket;
    }

    public void writeAll(String response) {
        checkStreams();
        writeExecutorService.execute(() -> {
            try {
                outputStream.write((response + "\r\n").getBytes());
                outputStream.flush();
                if (prevWasRep) {
                    outputViewer.print("\n");
                    prevWasRep = false;
                }
                outputViewer.print("\n~# : " + response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
