/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.action;

import com.jiuqi.va.biz.intf.action.ActionReturnModalMessage;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import java.util.ArrayList;
import java.util.List;

public class ActionReturnConfirmMessage
extends ActionReturnModalMessage {
    private String confirmFrom;
    private Object confirms;
    private List<CheckResult> checkResults;

    public List<CheckResult> getCheckResults() {
        return this.checkResults;
    }

    public void setCheckResults(List<CheckResult> checkResults) {
        this.checkResults = checkResults;
    }

    public ActionReturnConfirmMessage() {
        this.type = "ConfirmMessage";
        this.messageType = "confirm";
    }

    public ActionReturnConfirmMessage(String confirmFrom) {
        this.type = "ConfirmMessage";
        this.messageType = "confirm";
        this.confirmFrom = confirmFrom;
        this.confirms = new ArrayList();
    }

    public String getConfirmFrom() {
        return this.confirmFrom;
    }

    public void setConfirmFrom(String confirmFrom) {
        this.confirmFrom = confirmFrom;
    }

    public Object getConfirms() {
        return this.confirms;
    }

    public void setConfirms(Object confirms) {
        this.confirms = confirms;
    }
}

