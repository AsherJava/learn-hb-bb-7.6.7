/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 */
package com.jiuqi.nr.datascheme.internal.entity;

import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

@DBAnno.DBTable(dbTable="NR_DATASCHEME_DEPLOY_INFO")
public class DataFieldDeployInfoDO
implements DataFieldDeployInfo,
Serializable {
    private static final long serialVersionUID = 1695330859179942352L;
    @DBAnno.DBField(dbField="DS_DS_KEY", isPk=true)
    private String dataSchemeKey;
    @DBAnno.DBField(dbField="DS_DT_KEY")
    private String dataTableKey;
    @DBAnno.DBField(dbField="DS_SDT_KEY")
    private String sourceTableKey;
    @DBAnno.DBField(dbField="DS_DF_KEY", isPk=true)
    private String dataFieldKey;
    @DBAnno.DBField(dbField="DS_TM_KEY")
    private String tableModelKey;
    @DBAnno.DBField(dbField="DS_CM_KEY", isPk=true)
    private String columnModelKey;
    @DBAnno.DBField(dbField="DS_FIELD_NAME")
    private String fieldName;
    @DBAnno.DBField(dbField="DS_TABLE_NAME")
    private String tableName;
    @DBAnno.DBField(dbField="DS_VERSION")
    private String version;
    @DBAnno.DBField(dbField="DS_UPDATE_TIME", tranWith="transTimeStamp", dbType=Timestamp.class, appType=Instant.class, autoDate=true)
    private Instant updateTime;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getDataTableKey() {
        return this.dataTableKey;
    }

    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    public String getSourceTableKey() {
        return this.sourceTableKey;
    }

    public void setSourceTableKey(String sourceTableKey) {
        this.sourceTableKey = sourceTableKey;
    }

    public String getDataFieldKey() {
        return this.dataFieldKey;
    }

    public void setDataFieldKey(String dataFieldKey) {
        this.dataFieldKey = dataFieldKey;
    }

    public String getTableModelKey() {
        return this.tableModelKey;
    }

    public void setTableModelKey(String tableModelKey) {
        this.tableModelKey = tableModelKey;
    }

    public String getColumnModelKey() {
        return this.columnModelKey;
    }

    public void setColumnModelKey(String columnModelKey) {
        this.columnModelKey = columnModelKey;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public static DataFieldDeployInfoDO valueOf(DataFieldDeployInfo info) {
        DataFieldDeployInfoDO infoDo = new DataFieldDeployInfoDO();
        infoDo.setDataSchemeKey(info.getDataSchemeKey());
        infoDo.setDataTableKey(info.getDataTableKey());
        infoDo.setSourceTableKey(info.getSourceTableKey());
        infoDo.setDataFieldKey(info.getDataFieldKey());
        infoDo.setTableModelKey(info.getTableModelKey());
        infoDo.setColumnModelKey(info.getColumnModelKey());
        infoDo.setTableName(info.getTableName());
        infoDo.setFieldName(info.getFieldName());
        infoDo.setVersion(info.getVersion());
        infoDo.setUpdateTime(info.getUpdateTime());
        return infoDo;
    }
}

