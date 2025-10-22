/*
 * Decompiled with CFR 0.152.
 */
package nr.midstore.core.definition.db;

public class MidstoreException
extends Exception {
    private static final long serialVersionUID = -6357834248137645138L;

    public MidstoreException(String message) {
        super(message);
    }

    public MidstoreException(Throwable cause) {
        super(cause);
    }

    public MidstoreException(String message, Throwable cause) {
        super(message, cause);
    }
}

