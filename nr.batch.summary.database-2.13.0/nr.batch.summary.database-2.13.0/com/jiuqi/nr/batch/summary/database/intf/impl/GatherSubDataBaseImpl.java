/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.batch.summary.database.intf.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.batch.summary.database.intf.GatherSubDataBase;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_BS_SUB_DATABASE")
public class GatherSubDataBaseImpl
implements GatherSubDataBase {
    @DBAnno.DBField(dbField="BSSD_CODE", isPk=true)
    private String code;
    @DBAnno.DBField(dbField="BSSD_DS_KEY", isPk=true)
    private String dataSchemeKey;
    @DBAnno.DBField(dbField="BSSD_CREATETIME", tranWith="transTimeStamp", autoDate=true, dbType=Timestamp.class, appType=Date.class)
    private Date createTime;

    public GatherSubDataBaseImpl() {
    }

    public GatherSubDataBaseImpl(String code, String dataSchemeKey) {
        this.code = code;
        this.dataSchemeKey = dataSchemeKey;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getSubDataBaseCode() {
        return this.code;
    }

    @Override
    public String getDataScheme() {
        return this.dataSchemeKey;
    }

    @Override
    public Date getCreateTime() {
        if (this.createTime == null) {
            this.createTime = new Date();
        }
        return this.createTime;
    }
}

