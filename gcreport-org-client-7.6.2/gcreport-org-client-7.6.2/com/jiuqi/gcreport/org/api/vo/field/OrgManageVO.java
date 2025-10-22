/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.api.vo.field;

import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;

public class OrgManageVO {
    private OrgToJsonVO currentUnit;
    private OrgToJsonVO splitUnit;
    private OrgToJsonVO baseUnit;
    private OrgToJsonVO diffUnit;
    private OrgToJsonVO parentUnit;
    private String orgTypeName;

    public OrgToJsonVO getCurrentUnit() {
        return this.currentUnit;
    }

    public void setCurrentUnit(OrgToJsonVO currentUnit) {
        this.currentUnit = currentUnit;
    }

    public OrgToJsonVO getSplitUnit() {
        return this.splitUnit;
    }

    public void setSplitUnit(OrgToJsonVO splitUnit) {
        this.splitUnit = splitUnit;
    }

    public OrgToJsonVO getBaseUnit() {
        return this.baseUnit;
    }

    public void setBaseUnit(OrgToJsonVO baseUnit) {
        this.baseUnit = baseUnit;
    }

    public OrgToJsonVO getDiffUnit() {
        return this.diffUnit;
    }

    public void setDiffUnit(OrgToJsonVO diffUnit) {
        this.diffUnit = diffUnit;
    }

    public String getOrgTypeName() {
        return this.orgTypeName;
    }

    public void setOrgTypeName(String orgTypeName) {
        this.orgTypeName = orgTypeName;
    }

    public OrgToJsonVO getParentUnit() {
        return this.parentUnit;
    }

    public void setParentUnit(OrgToJsonVO parentUnit) {
        this.parentUnit = parentUnit;
    }
}

