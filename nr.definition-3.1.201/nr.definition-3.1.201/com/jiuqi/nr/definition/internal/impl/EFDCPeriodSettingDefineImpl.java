/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.internal.impl.MouldDataDefineImpl;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_EFDC_SETTING")
public class EFDCPeriodSettingDefineImpl {
    @DBAnno.DBField(dbField="FC_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="FC_FS_KEY")
    private String formulaSchemeKey;
    @DBAnno.DBField(dbField="FC_PERIOD_SETTING", tranWith="transMouldExtensions", dbType=Clob.class, appType=MouldDataDefineImpl.class)
    private MouldDataDefineImpl mouldDataDefine;
    @DBAnno.DBField(dbField="FC_UPDATETIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class, autoDate=true)
    private Date updateTime;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public MouldDataDefineImpl getMouldDataDefine() {
        return this.mouldDataDefine;
    }

    public void setMouldDataDefine(MouldDataDefineImpl mouldDataDefine) {
        this.mouldDataDefine = mouldDataDefine;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

