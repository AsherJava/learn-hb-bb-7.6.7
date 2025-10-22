/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.batch.summary.database.intf.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.batch.summary.database.intf.GatherDataBase;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_BS_DATABASE")
public class GatherDataBaseImpl
implements GatherDataBase {
    @DBAnno.DBField(dbField="BSD_DS_KEY", isPk=true)
    private String dataSchemeKey;
    @DBAnno.DBField(dbField="BSD_CREATETIME", tranWith="transTimeStamp", autoDate=true, dbType=Timestamp.class, appType=Date.class)
    private Date createTime;

    public GatherDataBaseImpl() {
    }

    public GatherDataBaseImpl(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    @Override
    public String getDataScheme() {
        return null;
    }

    @Override
    public Date getCreateTime() {
        if (this.createTime == null) {
            this.createTime = new Date();
        }
        return this.createTime;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

