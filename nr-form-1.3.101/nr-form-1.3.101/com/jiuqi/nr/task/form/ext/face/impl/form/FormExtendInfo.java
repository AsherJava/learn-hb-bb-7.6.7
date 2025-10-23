/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.ext.face.impl.form;

import com.jiuqi.nr.task.form.ext.face.IExtendInfo;

public class FormExtendInfo
implements IExtendInfo {
    private boolean enableCopyForm = true;
    private boolean enableAdd = true;
    private boolean enableCopy = true;
    private boolean enableSync = true;

    public boolean isEnableCopyForm() {
        return this.enableCopyForm;
    }

    public void setEnableCopyForm(boolean enableCopyForm) {
        this.enableCopyForm = enableCopyForm;
    }

    public boolean isEnableAdd() {
        return this.enableAdd;
    }

    public void setEnableAdd(boolean enableAdd) {
        this.enableAdd = enableAdd;
    }

    public boolean isEnableSync() {
        return this.enableSync;
    }

    public void setEnableSync(boolean enableSync) {
        this.enableSync = enableSync;
    }

    public boolean isEnableCopy() {
        return this.enableCopy;
    }

    public void setEnableCopy(boolean enableCopy) {
        this.enableCopy = enableCopy;
    }
}

