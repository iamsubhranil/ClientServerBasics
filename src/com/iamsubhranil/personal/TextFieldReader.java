package com.iamsubhranil.personal;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.Reader;

/**
 * Author : Nil
 * Date : 11/7/2016 at 8:59 PM.
 * Project : ClientServerBasics
 */
public class TextFieldReader extends Reader {

    private TextField inputField;

    public TextFieldReader(TextField textField) {
        super();
        inputField = textField;

        inputField.setOnAction(k -> {
            synchronized (lock) {
                try {
                    lock.notify();
                } catch (Exception e) {
                }
            }
        });
    }


    /**
     * Reads characters into a portion of an array.  This method will block
     * until some input is available, an I/O error occurs, or the end of the
     * stream is reached.
     *
     * @param cbuf Destination buffer
     * @param off  Offset at which to start storing characters
     * @param len  Maximum number of characters to read
     * @return The number of characters read, or -1 if the end of the
     * stream has been reached
     * @throws IOException If an I/O error occurs
     */
    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        synchronized (lock) {
            if (inputField == null) {
                throw new IOException("Reader is already closed!");
            }
            if (inputField.getText().equals("")) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String text = inputField.getText() + "\n";
            char[] chars = text.toCharArray();
            System.arraycopy(chars, 0, cbuf, off, chars.length < len ? chars.length : len);
            inputField.clear();
            return len;
        }
    }

    /**
     * Closes the stream and releases any system resources associated with
     * it.  Once the stream has been closed, further read(), ready(),
     * mark(), reset(), or skip() invocations will throw an IOException.
     * Closing a previously closed stream has no effect.
     *
     * @throws IOException If an I/O error occurs
     */
    @Override
    public void close() throws IOException {
        inputField.setDisable(true);
        inputField = null;
    }
}
