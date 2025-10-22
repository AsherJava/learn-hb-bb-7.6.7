/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.offsetitem.vo.query;

import com.jiuqi.common.base.util.StringUtils;

public class GcOffsetItemQueryCondi {
    public String srcOffsetGroupId;
    public String orgType;
    public int acctYear;
    public int acctPeriod;
    public int offSetSrcType;
    public String systemId;
    public String taskId;
    public String schemeId;
    public String unitCode;
    public String oppUnitCode;
    public boolean isFairValue;
    public String assetTitle;
    public String offSetCurr;

    public String getOffSetCurr() {
        return StringUtils.isEmpty((String)this.offSetCurr) ? "CNY" : this.offSetCurr;
    }
}

