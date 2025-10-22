/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.dataentry.gather.ActionItem;
import java.util.List;

public interface IDataEntryUpload {
    public int getState();

    public List<ActionItem> getUploadActions();
}

