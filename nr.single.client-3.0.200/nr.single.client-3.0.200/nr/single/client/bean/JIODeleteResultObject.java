/*
 * Decompiled with CFR 0.152.
 */
package nr.single.client.bean;

import java.io.Serializable;

public class JIODeleteResultObject
implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean success;
    private String message;

    public boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

