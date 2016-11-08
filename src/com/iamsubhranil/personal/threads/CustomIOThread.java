package com.iamsubhranil.personal.threads;

import java.io.*;

/**
 * Author : Nil
 * Date : 11/7/2016 at 10:24 PM.
 * Project : ClientServerBasics
 */
public class CustomIOThread extends Thread {

    protected BufferedReader instructor;
    protected PrintWriter outputViewer;

    public void setReader(Reader r) {
        instructor = new BufferedReader(r);
    }

    public void setWriter(Writer w) {
        outputViewer = new PrintWriter(w, true);
    }

    protected void checkStreams() {
        if (instructor == null) {
            instructor = new BufferedReader(new InputStreamReader(System.in));
        }
        if (outputViewer == null) {
            outputViewer = new PrintWriter(System.out, true);
        }
    }

}
