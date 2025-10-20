/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.action;

import com.jiuqi.va.biz.ruler.intf.CheckResult;
import java.util.ArrayList;
import java.util.List;

public class ActionResponse {
    private boolean success = true;
    private Object returnValue;
    private Object returnMessage;
    private List<CheckResult> checkMessages = new ArrayList<CheckResult>();

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getReturnValue() {
        return this.returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    public Object getReturnMessage() {
        return this.returnMessage;
    }

    public void setReturnMessage(Object returnMessage) {
        this.returnMessage = returnMessage;
    }

    public List<CheckResult> getCheckMessages() {
        return this.checkMessages;
    }

    public void setCheckMessages(List<CheckResult> checkMessages) {
        this.checkMessages = checkMessages;
    }
}

