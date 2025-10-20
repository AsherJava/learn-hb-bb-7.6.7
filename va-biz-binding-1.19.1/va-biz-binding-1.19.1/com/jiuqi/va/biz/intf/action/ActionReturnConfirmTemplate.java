/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.action;

import com.jiuqi.va.biz.intf.action.ActionReturnModalTemplate;
import java.util.ArrayList;

public class ActionReturnConfirmTemplate
extends ActionReturnModalTemplate {
    private String confirmFrom;
    private Object confirms;

    public ActionReturnConfirmTemplate() {
        this.type = "ConfirmTemplate";
        this.messageType = "confirm";
    }

    public ActionReturnConfirmTemplate(String confirmFrom) {
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

