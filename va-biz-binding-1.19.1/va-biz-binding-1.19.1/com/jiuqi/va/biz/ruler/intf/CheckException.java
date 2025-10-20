/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.ruler.intf;

import com.jiuqi.va.biz.ruler.intf.CheckResult;
import java.util.List;

public class CheckException
extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private List<CheckResult> checkMessages;

    public CheckException() {
    }

    public CheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckException(String message) {
        super(message);
    }

    public CheckException(Throwable cause) {
        super(cause);
    }

    public CheckException(String message, List<CheckResult> checkMessages) {
        super(message);
        this.checkMessages = checkMessages;
    }

    public List<CheckResult> getCheckMessages() {
        return this.checkMessages;
    }

    public void setCheckMessages(List<CheckResult> checkMessages) {
        this.checkMessages = checkMessages;
    }
}

