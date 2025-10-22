/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.nr.common.temptable.ITempTable
 */
package com.jiuqi.nr.io.tz;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable;
import com.jiuqi.nr.io.tz.listener.ChangeInfo;

public class TzParams {
    public String importType;
    public String datatime;
    public String sourceType;
    public String sourceData;
    public String destForm;
    public String fullOrAdd;
    private String mdCodeTable;
    private ITempTable mdCodeTempTable;
    private String tempTableName;
    private ITempTable tempTable;
    private String stateTableName;
    private ITempTable stateTempTable;
    private IDatabase iDatabase;
    private DataSchemeTmpTable tmpTable;
    private ChangeInfo changeInfo;

    public ChangeInfo getChangeInfo() {
        return this.changeInfo;
    }

    public void setChangeInfo(ChangeInfo changeInfo) {
        this.changeInfo = changeInfo;
    }

    public String getImportType() {
        return this.importType;
    }

    public void setImportType(String importType) {
        this.importType = importType;
    }

    public String getDatatime() {
        return this.datatime;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }

    public String getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceData() {
        return this.sourceData;
    }

    public void setSourceData(String sourceData) {
        this.sourceData = sourceData;
    }

    public String getDestForm() {
        return this.destForm;
    }

    public void setDestForm(String destForm) {
        this.destForm = destForm;
    }

    public String getFullOrAdd() {
        return this.fullOrAdd;
    }

    public void setFullOrAdd(String fullOrAdd) {
        this.fullOrAdd = fullOrAdd;
    }

    public String getTempTableName() {
        return this.tempTableName;
    }

    public void setTempTableName(String tempTableName) {
        this.tempTableName = tempTableName;
    }

    public String getMdCodeTable() {
        return this.mdCodeTable;
    }

    public void setMdCodeTable(String mdCodeTable) {
        this.mdCodeTable = mdCodeTable;
    }

    public IDatabase getiDatabase() {
        return this.iDatabase;
    }

    public void setiDatabase(IDatabase iDatabase) {
        this.iDatabase = iDatabase;
    }

    public String getStateTableName() {
        return this.stateTableName;
    }

    public void setStateTableName(String stateTableName) {
        this.stateTableName = stateTableName;
    }

    public ITempTable getMdCodeTempTable() {
        return this.mdCodeTempTable;
    }

    public void setMdCodeTempTable(ITempTable mdCodeTempTable) {
        this.mdCodeTempTable = mdCodeTempTable;
    }

    public ITempTable getTempTable() {
        return this.tempTable;
    }

    public void setTempTable(ITempTable tempTable) {
        this.tempTable = tempTable;
    }

    public ITempTable getStateTempTable() {
        return this.stateTempTable;
    }

    public void setStateTempTable(ITempTable stateTempTable) {
        this.stateTempTable = stateTempTable;
    }

    public TzParams(String importType, String datatime, String sourceType, String sourceData, String destForm, String fullOrAdd) {
        this.importType = importType;
        this.datatime = datatime;
        this.sourceType = sourceType;
        this.sourceData = sourceData;
        this.destForm = destForm;
        this.fullOrAdd = fullOrAdd;
    }

    public TzParams() {
    }

    public void setTmpTable(DataSchemeTmpTable tmpTable) {
        this.tmpTable = tmpTable;
    }

    public DataSchemeTmpTable getTmpTable() {
        return this.tmpTable;
    }
}

