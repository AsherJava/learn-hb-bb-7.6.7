/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.treestore.fmdmdisplay.bean;

import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.EntityFiledInfo;

public class FMDMCaptionFiledInfo
extends EntityFiledInfo {
    private String dataType;
    private boolean checked;

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

