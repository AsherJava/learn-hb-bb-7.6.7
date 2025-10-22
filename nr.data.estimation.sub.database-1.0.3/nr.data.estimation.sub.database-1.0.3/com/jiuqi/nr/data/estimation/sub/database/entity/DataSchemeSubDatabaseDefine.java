/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.sub.database.entity;

import com.jiuqi.nr.data.estimation.sub.database.entity.IDataSchemeSubDatabaseDefine;
import java.util.Date;

public class DataSchemeSubDatabaseDefine
implements IDataSchemeSubDatabaseDefine {
    private String dataSchemeKey;
    private String databaseCode;
    private String databaseTitle;
    private Date createTime;

    @Override
    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    @Override
    public String getDatabaseCode() {
        return this.databaseCode;
    }

    public void setDatabaseCode(String databaseCode) {
        this.databaseCode = databaseCode;
    }

    @Override
    public Date getCreateTime() {
        return this.createTime != null ? this.createTime : new Date();
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getDatabaseTitle() {
        return this.databaseTitle;
    }

    public void setDatabaseTitle(String databaseTitle) {
        this.databaseTitle = databaseTitle;
    }
}

