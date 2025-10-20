/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.action;

import com.jiuqi.va.biz.intf.action.ActionReturnModalMessage;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import java.util.List;

public class ActionReturnCheckMessage
extends ActionReturnModalMessage {
    private List<CheckResult> messages;

    @Override
    public String getType() {
        return "CheckMessage";
    }

    public List<CheckResult> getMessages() {
        return this.messages;
    }

    public void setMessages(List<CheckResult> messages) {
        this.messages = messages;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }
}

