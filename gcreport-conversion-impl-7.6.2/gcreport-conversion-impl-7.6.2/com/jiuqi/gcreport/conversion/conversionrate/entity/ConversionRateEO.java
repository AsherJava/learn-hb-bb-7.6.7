/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.conversion.conversionrate.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.lang.reflect.Field;
import java.util.Date;

@DBTable(name="GC_CONV_RATE_V", title="\u6298\u7b97\u6c47\u7387\u4fe1\u606f\u8868", inStorage=true, indexs={@DBIndex(name="IDX_GC_CONV_RATE_V_COM1", columnsFields={"NODEID", "DATATIME"})})
public class ConversionRateEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_CONV_RATE_V";
    @DBColumn(nameInDB="nodeID", title="\u6c47\u7387\u8282\u70b9ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String nodeId;
    @DBColumn(nameInDB="DATATIME", title="\u65f6\u671f\u503c", dbType=DBColumn.DBType.NVarchar, length=20)
    private String dataTime;
    @DBColumn(nameInDB="ROWDATAID", title="\u6298\u7b97\u6c47\u7387\u884c", dbType=DBColumn.DBType.Varchar, length=36)
    private String rowDataId;
    @DBColumn(nameInDB="RATETYPECODE", title="\u6298\u7b97\u6c47\u7387\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=20)
    private String rateTypeCode;
    @DBColumn(nameInDB="RATEVALUE", title="\u6c47\u7387\u503c", dbType=DBColumn.DBType.Numeric, precision=19, scale=6)
    private Double rateValue;
    @DBColumn(nameInDB="CREATOR", title="\u521b\u5efa\u4eba", dbType=DBColumn.DBType.NVarchar, length=60)
    private String creator;
    @DBColumn(nameInDB="CREATETIME", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    private Date createTime;
    @DBColumn(nameInDB="UPDATETIME", title="\u4fee\u6539\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    private Date updateTime;
    private String periodId;
    private String groupId;
    private String groupName;
    private String sourceCurrencyCode;
    private String targetCurrencyCode;

    public static String getAllFieldSql(String tableAlias) {
        String prefix = "";
        String asStr = "";
        if (tableAlias != null && tableAlias.trim().length() > 0) {
            prefix = tableAlias + ".";
            asStr = "as";
        }
        String fieldSql = " " + prefix + "ID " + asStr + " id," + prefix + "RECVER " + asStr + " recver, ";
        Class<ConversionRateEO> clazz = ConversionRateEO.class;
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            if ("TABLENAME".equals(fields[i].getName()) || fields[i].getAnnotation(DBColumn.class) == null) continue;
            fieldSql = fieldSql + prefix + fields[i].getAnnotation(DBColumn.class).nameInDB().toLowerCase() + " " + asStr + " " + fields[i].getName() + ",";
        }
        return String.format(fieldSql.substring(0, fieldSql.length() - 1), new Object[0]);
    }

    public String getPeriodId() {
        return this.periodId;
    }

    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSourceCurrencyCode() {
        return this.sourceCurrencyCode;
    }

    public void setSourceCurrencyCode(String sourceCurrencyCode) {
        this.sourceCurrencyCode = sourceCurrencyCode;
    }

    public String getTargetCurrencyCode() {
        return this.targetCurrencyCode;
    }

    public void setTargetCurrencyCode(String targetCurrencyCode) {
        this.targetCurrencyCode = targetCurrencyCode;
    }

    public String getRowDataId() {
        return this.rowDataId;
    }

    public void setRowDataId(String rowDataId) {
        this.rowDataId = rowDataId;
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getRateTypeCode() {
        return this.rateTypeCode;
    }

    public void setRateTypeCode(String rateTypeCode) {
        this.rateTypeCode = rateTypeCode;
    }

    public Double getRateValue() {
        return this.rateValue;
    }

    public void setRateValue(Double rateValue) {
        this.rateValue = rateValue;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public static String getTablename() {
        return TABLENAME;
    }
}

