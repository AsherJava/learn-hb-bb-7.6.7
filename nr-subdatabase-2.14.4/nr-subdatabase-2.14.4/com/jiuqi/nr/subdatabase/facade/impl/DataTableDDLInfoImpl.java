/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.subdatabase.facade.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.subdatabase.facade.DataTableDDLInfo;
import java.sql.Timestamp;
import java.util.Date;

@DBAnno.DBTable(dbTable="NR_SD_DATATABLE_DEPLOY_INFO")
public class DataTableDDLInfoImpl
implements DataTableDDLInfo {
    @DBAnno.DBField(dbField="DS_KEY", isPk=true)
    private String dataSchemeKey;
    @DBAnno.DBField(dbField="TM_KEY", isPk=true)
    private String tableModelKey;
    @DBAnno.DBField(dbField="DDL_TIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Date.class)
    private Date ddlTime;
    @DBAnno.DBField(dbField="SYNC_ORDER")
    private Integer syncOrder;

    @Override
    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    @Override
    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    @Override
    public String getTableModelKey() {
        return this.tableModelKey;
    }

    @Override
    public void setTableModelKey(String tableModelKey) {
        this.tableModelKey = tableModelKey;
    }

    @Override
    public Date getDDLTime() {
        return this.ddlTime;
    }

    @Override
    public void setDDLTime(Date ddlTime) {
        this.ddlTime = ddlTime;
    }

    @Override
    public Integer getSyncOrder() {
        return this.syncOrder;
    }

    @Override
    public void setSyncOrder(Integer syncOrder) {
        this.syncOrder = syncOrder;
    }
}

