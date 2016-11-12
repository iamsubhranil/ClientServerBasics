package com.iamsubhranil.personal.threads.fullduplex;

import java.io.PrintWriter;
import java.io.Writer;

/**
 * Author : Nil
 * Date : 11/12/2016 at 7:05 PM.
 * Project : ClientServerBasics
 */
public class CustomIO {
    // InputStreamReader instructor;
    PrintWriter outputViewer;

  /*  public void setReader(InputStreamReader r) {
        instructor = r;
    }
    */

    public void setWriter(Writer w) {
        outputViewer = new PrintWriter(w, true);
    }

    protected void checkStreams() {
     /*   if (instructor == null) {
            instructor = new InputStreamReader(System.in);
        }
        */
        if (outputViewer == null) {
            outputViewer = new PrintWriter(System.out, true);
        }
    }
}
