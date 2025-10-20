/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.service.impl;

import java.io.Serializable;

public class ProgressInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String saveMessage;
    private boolean finished;

    public String getSaveMessage() {
        return this.saveMessage;
    }

    public void setSaveMessage(String saveMessage) {
        this.saveMessage = saveMessage;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}

