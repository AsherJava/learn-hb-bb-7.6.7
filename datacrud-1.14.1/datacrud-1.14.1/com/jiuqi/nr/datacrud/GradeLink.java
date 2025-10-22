/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 */
package com.jiuqi.nr.datacrud;

import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import java.util.List;

public class GradeLink {
    private String linkKey;
    private String fieldKey;
    private List<Integer> gradeSetting;
    private boolean hideEnd0;
    private boolean needEnd0;
    private DataFieldGatherType dataFieldGatherType;

    public String getLinkKey() {
        return this.linkKey;
    }

    public void setLinkKey(String linkKey) {
        this.linkKey = linkKey;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public List<Integer> getGradeSetting() {
        return this.gradeSetting;
    }

    public void setGradeSetting(List<Integer> gradeSetting) {
        this.gradeSetting = gradeSetting;
    }

    public boolean isHideEnd0() {
        return this.hideEnd0;
    }

    public void setHideEnd0(boolean hideEnd0) {
        this.hideEnd0 = hideEnd0;
    }

    public DataFieldGatherType getDataFieldGatherType() {
        return this.dataFieldGatherType;
    }

    public void setDataFieldGatherType(DataFieldGatherType dataFieldGatherType) {
        this.dataFieldGatherType = dataFieldGatherType;
    }

    public boolean isNeedEnd0() {
        return this.needEnd0;
    }

    public void setNeedEnd0(boolean needEnd0) {
        this.needEnd0 = needEnd0;
    }
}

