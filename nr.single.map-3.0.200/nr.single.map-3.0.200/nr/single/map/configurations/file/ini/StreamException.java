/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.file.ini;

public class StreamException
extends Exception {
    private static final long serialVersionUID = 1L;
    public static final String STREAM_READERROR = "Stream read error";
    public static final String STREAM_WRITEERROR = "Stream write error";
    public static final String STREAM_SIZEERROR = "Stream size error";
    public static final String STREAM_SEEKERROR = "Stream seek error";

    public StreamException() {
    }

    public StreamException(String message) {
        super(message);
    }

    public StreamException(String message, Throwable cause) {
        super(message, cause);
    }

    public StreamException(Throwable cause) {
        super(cause);
    }
}

