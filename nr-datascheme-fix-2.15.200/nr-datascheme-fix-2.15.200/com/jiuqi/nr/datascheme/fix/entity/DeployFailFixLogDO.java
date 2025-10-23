/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBField
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.datascheme.fix.entity;

import com.jiuqi.nr.datascheme.fix.core.DeployFixResultDTO;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import java.sql.Clob;
import java.sql.Timestamp;
import java.time.Instant;

@DBAnno.DBTable(dbTable="NR_DATASCHEME_DEPLOY_FIXLOG")
public class DeployFailFixLogDO {
    @DBAnno.DBField(dbField="DF_DS_KEY")
    private String dataSchemeKey;
    @DBAnno.DBField(dbField="DF_DT_KEY")
    private String dataTableKey;
    @DBAnno.DBField(dbField="DF_FIX_TIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class)
    private Instant deployFailFixTime;
    @DBAnno.DBField(dbField="DF_EXTYPE")
    private int exType;
    @DBAnno.DBField(dbField="DF_FIX_SCHEME")
    private int fixScheme;
    @DBAnno.DBField(dbField="DF_FIX_RESULT", tranWith="transClob", dbType=Clob.class, appType=String.class)
    private String fixResult;
    @DBAnno.DBField(dbField="DF_NEWTABLE_NAME", tranWith="transTableNames", dbType=String.class, appType=String[].class)
    private String[] newTableName;
    @DBAnno.DBField(dbField="DF_TRANSFER_DATA", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    private boolean isTransfer;

    public DeployFailFixLogDO() {
    }

    public DeployFailFixLogDO(DeployFixResultDTO fixResult, Instant fixTime) {
        this.dataSchemeKey = fixResult.getDataSchemeKey();
        this.dataTableKey = fixResult.getDataTableKey();
        this.exType = fixResult.getExType().getValue();
        this.fixScheme = fixResult.getFixType().getValue();
        this.fixResult = fixResult.getFixResultType().getTitle();
        this.deployFailFixTime = fixTime;
        this.newTableName = fixResult.getNewTableName() != null ? fixResult.getNewTableName().toArray(new String[fixResult.getNewTableName().size()]) : null;
        this.isTransfer = fixResult.isTransfer();
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    public void setDeployFailFixTime(Instant deployFailFixTime) {
        this.deployFailFixTime = deployFailFixTime;
    }

    public Instant getDeployFailFixTime() {
        return this.deployFailFixTime;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public String getDataTableKey() {
        return this.dataTableKey;
    }

    public int getExType() {
        return this.exType;
    }

    public void setExType(int exType) {
        this.exType = exType;
    }

    public int getFixScheme() {
        return this.fixScheme;
    }

    public void setFixScheme(int fixScheme) {
        this.fixScheme = fixScheme;
    }

    public String getFixResult() {
        return this.fixResult;
    }

    public void setFixResult(String fixResult) {
        this.fixResult = fixResult;
    }

    public String[] getNewTableName() {
        return this.newTableName;
    }

    public void setNewTableName(String[] newTableName) {
        this.newTableName = newTableName;
    }

    public boolean isTransfer() {
        return this.isTransfer;
    }

    public void setTransfer(boolean transfer) {
        this.isTransfer = transfer;
    }
}

