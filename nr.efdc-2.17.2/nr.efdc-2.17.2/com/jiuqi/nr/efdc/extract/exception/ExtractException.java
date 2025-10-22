/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.NpRollbackException
 */
package com.jiuqi.nr.efdc.extract.exception;

import com.jiuqi.np.util.NpRollbackException;
import java.util.List;

public class ExtractException
extends NpRollbackException {
    private static final long serialVersionUID = 755134748859694410L;
    private List<String> details;

    public ExtractException() {
    }

    public ExtractException(String message) {
        super(message);
    }

    public ExtractException(Throwable cause) {
        super(cause);
    }

    public ExtractException(String message, Throwable cause) {
        super(message, cause);
    }

    public List<String> getDetails() {
        return this.details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public String getMessage() {
        return this.toString();
    }

    public String toString() {
        String detailMsg = super.getMessage();
        StringBuilder buf = detailMsg != null && (this.details == null || this.details.size() == 0) ? new StringBuilder(detailMsg + "\n") : new StringBuilder();
        if (this.details != null) {
            int showNum = Math.min(5, this.details.size());
            for (int i = 0; i < showNum; ++i) {
                buf.append(this.details.get(i).toString()).append("\n");
            }
            if (this.details.size() > 5) {
                buf.append("\u66f4\u591a\u9519\u8bef...");
            }
        }
        return buf.toString();
    }
}

