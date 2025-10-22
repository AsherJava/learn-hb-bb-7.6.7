/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.bean;

import com.jiuqi.nr.data.engine.version.IRegionCompareDifference;
import java.util.List;

public class FormCompareDifference {
    private String formName;
    private String formCode;
    private String formKey;
    private List<IRegionCompareDifference> updateRegions;

    public String getFormName() {
        return this.formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public List<IRegionCompareDifference> getUpdateRegions() {
        return this.updateRegions;
    }

    public void setUpdateRegions(List<IRegionCompareDifference> updateRegions) {
        this.updateRegions = updateRegions;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }
}

