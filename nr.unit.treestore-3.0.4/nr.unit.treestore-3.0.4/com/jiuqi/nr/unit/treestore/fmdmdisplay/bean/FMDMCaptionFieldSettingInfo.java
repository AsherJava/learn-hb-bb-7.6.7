/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.treestore.fmdmdisplay.bean;

import com.jiuqi.nr.unit.treestore.fmdmdisplay.bean.FMDMCaptionFiledInfo;
import java.util.List;

public class FMDMCaptionFieldSettingInfo {
    private boolean sysUser;
    private List<FMDMCaptionFiledInfo> captionFields;

    public boolean isSysUser() {
        return this.sysUser;
    }

    public void setSysUser(boolean sysUser) {
        this.sysUser = sysUser;
    }

    public List<FMDMCaptionFiledInfo> getCaptionFields() {
        return this.captionFields;
    }

    public void setCaptionFields(List<FMDMCaptionFiledInfo> captionFields) {
        this.captionFields = captionFields;
    }
}

