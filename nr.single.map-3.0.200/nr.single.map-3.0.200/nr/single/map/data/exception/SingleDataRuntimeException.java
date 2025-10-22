/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.data.exception;

public class SingleDataRuntimeException
extends RuntimeException {
    private static final long serialVersionUID = 5525702168988518380L;

    public SingleDataRuntimeException() {
    }

    public SingleDataRuntimeException(String message) {
        super(message);
    }

    public SingleDataRuntimeException(Throwable cause) {
        super(cause);
    }

    public SingleDataRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}

