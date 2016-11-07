package com.iamsubhranil.personal;

import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.Writer;

/**
 * Author : Nil
 * Date : 11/7/2016 at 9:27 PM.
 * Project : ClientServerBasics
 */
public class TextAreaWriter extends Writer {

    private TextArea textArea;

    public TextAreaWriter(TextArea ta) {
        super();
        textArea = ta;
    }

    /**
     * Writes a portion of an array of characters.
     *
     * @param cbuf Array of characters
     * @param off  Offset from which to start writing characters
     * @param len  Number of characters to write
     * @throws IOException If an I/O error occurs
     */
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        if (textArea == null) {
            throw new IOException("Writer has already been closed!");
        }
        String toWrite = String.copyValueOf(cbuf, off, len);
        textArea.appendText(toWrite);

    }

    /**
     * Flushes the stream.  If the stream has saved any characters from the
     * various write() methods in a buffer, write them immediately to their
     * intended destination.  Then, if that destination is another character or
     * byte stream, flush it.  Thus one flush() invocation will flush all the
     * buffers in a chain of Writers and OutputStreams.
     * <p>
     * <p> If the intended destination of this stream is an abstraction provided
     * by the underlying operating system, for example a file, then flushing the
     * stream guarantees only that bytes previously written to the stream are
     * passed to the operating system for writing; it does not guarantee that
     * they are actually written to a physical device such as a disk drive.
     *
     * @throws IOException If an I/O error occurs
     */
    @Override
    public void flush() throws IOException {
    }

    /**
     * Closes the stream, flushing it first. Once the stream has been closed,
     * further write() or flush() invocations will cause an IOException to be
     * thrown. Closing a previously closed stream has no effect.
     *
     * @throws IOException If an I/O error occurs
     */
    @Override
    public void close() throws IOException {
        textArea = null;
    }
}
