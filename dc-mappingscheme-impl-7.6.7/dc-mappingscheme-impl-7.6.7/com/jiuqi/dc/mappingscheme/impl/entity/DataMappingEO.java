/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.definition.DcDefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.anno.DBTableGroup
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  javax.persistence.Column
 */
package com.jiuqi.dc.mappingscheme.impl.entity;

import com.jiuqi.dc.base.common.definition.DcDefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.anno.DBTableGroup;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import javax.persistence.Column;

@DBTable(name="DC_SCHEME_DATAMAPPING", title="\u6570\u636e\u6620\u5c04\u65b9\u6848\u8868", kind=TableModelKind.SYSTEM_EXTEND, ownerGroupID=@DBTableGroup(id="11000000-0000-0000-0000-000000000003", code="table_group_datacenter", title="\u4e00\u672c\u8d26"))
public class DataMappingEO
extends DcDefaultTableEntity {
    private static final long serialVersionUID = -8323329957201227717L;
    @Column(name="VER")
    @DBColumn(title="\u884c\u7248\u672c", dbType=DBColumn.DBType.Long, isRecver=true, isRequired=true, order=1)
    private Long ver;
    @DBColumn(nameInDB="CODE", title="\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=2, isRequired=true, order=2)
    private String code;
    @DBColumn(nameInDB="NAME", title="\u540d\u79f0", dbType=DBColumn.DBType.NVarchar, length=100, isRequired=true, order=3)
    private String name;
    @DBColumn(nameInDB="PLUGINTYPE", title="\u63d2\u4ef6\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true, order=4)
    private String pluginType;
    @DBColumn(nameInDB="DATASOURCECODE", title="\u6570\u636e\u6e90\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=60, order=5)
    private String dataSourceCode;
    @DBColumn(nameInDB="SOURCEDATATYPE", title="\u6e90\u6570\u636e\u6765\u6e90\u65b9\u5f0f", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true, defaultValue="DIRECT", order=6)
    private String sourceDataType;
    @DBColumn(nameInDB="CUSTOMCONFIG", title="\u81ea\u5b9a\u4e49\u914d\u7f6e", dbType=DBColumn.DBType.NVarchar, length=1000, order=7)
    private String customConfig;
    @DBColumn(nameInDB="STOPFLAG", title="\u505c\u7528\u6807\u8bc6", dbType=DBColumn.DBType.NVarchar, length=60, isRequired=true, order=8)
    private String stopFlag;
    @DBColumn(nameInDB="CREATETIME", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.DateTime, order=9)
    private String createTime;
    @DBColumn(nameInDB="ETLJOBID", title="ETL\u4efb\u52a1ID", dbType=DBColumn.DBType.NVarchar, length=60, order=10)
    private String etlJobId;
    @DBColumn(nameInDB="DATAMAPPING", title="\u6570\u636e\u6620\u5c04JSON", dbType=DBColumn.DBType.Text, isRequired=true, order=11)
    private String dataMapping;

    public Long getVer() {
        return this.ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPluginType() {
        return this.pluginType;
    }

    public void setPluginType(String pluginType) {
        this.pluginType = pluginType;
    }

    public String getDataSourceCode() {
        return this.dataSourceCode;
    }

    public void setDataSourceCode(String dataSourceCode) {
        this.dataSourceCode = dataSourceCode;
    }

    public String getSourceDataType() {
        return this.sourceDataType;
    }

    public void setSourceDataType(String sourceDataType) {
        this.sourceDataType = sourceDataType;
    }

    public String getCustomConfig() {
        return this.customConfig;
    }

    public void setCustomConfig(String customConfig) {
        this.customConfig = customConfig;
    }

    public String getStopFlag() {
        return this.stopFlag;
    }

    public void setStopFlag(String stopFlag) {
        this.stopFlag = stopFlag;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEtlJobId() {
        return this.etlJobId;
    }

    public void setEtlJobId(String etlJobId) {
        this.etlJobId = etlJobId;
    }

    public String getDataMapping() {
        return this.dataMapping;
    }

    public void setDataMapping(String dataMapping) {
        this.dataMapping = dataMapping;
    }
}

