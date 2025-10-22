/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.convert.pdf.converter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Converter {
    private static final Logger logger = LoggerFactory.getLogger(Converter.class);
    private final String LOADING_FORMAT = "\nLoading stream\n\n";
    private final String PROCESSING_FORMAT = "Load completed in %1$dms, now converting...\n\n";
    private final String SAVING_FORMAT = "Conversion took %1$dms.\n\nTotal: %2$dms\n";
    private long startTime;
    private long startOfProcessTime;
    protected InputStream inStream;
    protected OutputStream outStream;
    protected boolean showOutputMessages = false;
    protected boolean closeStreamsWhenComplete = true;

    public Converter(InputStream inStream, OutputStream outStream, boolean showMessages, boolean closeStreamsWhenComplete) {
        this.inStream = inStream;
        this.outStream = outStream;
        this.showOutputMessages = showMessages;
        this.closeStreamsWhenComplete = closeStreamsWhenComplete;
    }

    public abstract void convert() throws Exception;

    public void convert(String fileTitle) throws Exception {
    }

    private void startTime() {
        this.startOfProcessTime = this.startTime = System.currentTimeMillis();
    }

    protected void loading() {
        this.sendToOutputOrNot(String.format("\nLoading stream\n\n", new Object[0]));
        this.startTime();
    }

    protected void processing() {
        long currentTime = System.currentTimeMillis();
        long prevProcessTook = currentTime - this.startOfProcessTime;
        this.sendToOutputOrNot(String.format("Load completed in %1$dms, now converting...\n\n", prevProcessTook));
        this.startOfProcessTime = System.currentTimeMillis();
    }

    protected void finished() {
        long currentTime = System.currentTimeMillis();
        long timeTaken = currentTime - this.startTime;
        long prevProcessTook = currentTime - this.startOfProcessTime;
        this.startOfProcessTime = System.currentTimeMillis();
        if (this.closeStreamsWhenComplete) {
            try {
                this.inStream.close();
                this.outStream.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        this.sendToOutputOrNot(String.format("Conversion took %1$dms.\n\nTotal: %2$dms\n", prevProcessTook, timeTaken));
    }

    private void sendToOutputOrNot(String toBePrinted) {
        if (this.showOutputMessages) {
            this.actuallySendToOutput(toBePrinted);
        }
    }

    protected void actuallySendToOutput(String toBePrinted) {
        logger.info(toBePrinted);
    }

    public OutputStream getOutStream() {
        return this.outStream;
    }
}

