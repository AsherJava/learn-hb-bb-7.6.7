/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.onekeymerge.vo;

import com.jiuqi.gcreport.onekeymerge.vo.GcBaseTaskStateVO;

public class GcRelationToMergeVO
extends GcBaseTaskStateVO {
    private String orgName;
    private String result;
    private String useTime;

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUseTime() {
        return this.useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }
}

