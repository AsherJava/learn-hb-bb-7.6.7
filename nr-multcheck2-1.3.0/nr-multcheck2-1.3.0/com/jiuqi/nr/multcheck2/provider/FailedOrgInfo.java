/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.provider;

import com.jiuqi.nr.multcheck2.provider.FailedOrgDimInfo;
import java.io.Serializable;
import java.util.List;

public class FailedOrgInfo
implements Serializable {
    private String desc;
    private List<FailedOrgDimInfo> dimInfo;

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<FailedOrgDimInfo> getDimInfo() {
        return this.dimInfo;
    }

    public void setDimInfo(List<FailedOrgDimInfo> dimInfo) {
        this.dimInfo = dimInfo;
    }
}

