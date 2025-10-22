/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean.impl;

import com.jiuqi.nr.dataentry.bean.IDataEntryUpload;
import com.jiuqi.nr.dataentry.gather.ActionItem;
import java.util.List;

public class DataEntryUploadImpl
implements IDataEntryUpload {
    private int state;
    private int uploadActions;

    @Override
    public int getState() {
        return 0;
    }

    @Override
    public List<ActionItem> getUploadActions() {
        return null;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setUploadActions(int uploadActions) {
        this.uploadActions = uploadActions;
    }
}

