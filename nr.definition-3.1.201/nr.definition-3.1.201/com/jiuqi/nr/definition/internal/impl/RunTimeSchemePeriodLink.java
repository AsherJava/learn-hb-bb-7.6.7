/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_PARAM_SCHEMEPERIODLINK")
public class RunTimeSchemePeriodLink
implements SchemePeriodLinkDefine {
    private static final long serialVersionUID = 1L;
    @DBAnno.DBField(dbField="sp_scheme_key")
    private String schemeKey;
    @DBAnno.DBField(dbField="sp_period_key")
    private String periodKey;
    private String level;
    private Date updateTime;
    private boolean isdefault;

    @Override
    public boolean getIsdefault() {
        return this.isdefault;
    }

    public void setIsdefault(boolean isdefault) {
        this.isdefault = isdefault;
    }

    @Override
    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    @Override
    public String getPeriodKey() {
        return this.periodKey;
    }

    public void setPeriodKey(String periodKey) {
        this.periodKey = periodKey;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getKey() {
        return null;
    }

    public String getTitle() {
        return null;
    }

    public String getOrder() {
        return null;
    }

    public String getVersion() {
        return null;
    }

    public String getOwnerLevelAndId() {
        return null;
    }
}

