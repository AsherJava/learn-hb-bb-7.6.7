/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.conversion.conversionrate.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.lang.reflect.Field;
import java.util.Date;

@DBTable(name="GC_CONV_RATE_G", title="\u6c47\u7387\u5206\u7ec4", inStorage=true)
public class ConversionRateGroupEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_CONV_RATE_G";
    @DBColumn(nameInDB="PERIODID", title="\u65f6\u671f\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=20)
    private String periodId;
    @DBColumn(nameInDB="SYSTEMID", title="\u6298\u7b97\u4f53\u7cfbID", dbType=DBColumn.DBType.Varchar, length=36)
    private String systemId;
    @DBColumn(nameInDB="GROUPNAME", title="\u6c47\u7387\u5206\u7ec4\u540d\u79f0", dbType=DBColumn.DBType.NVarchar, length=60)
    private String groupName;
    @DBColumn(nameInDB="DESCRIPTION", title="\u6c47\u7387\u5206\u7ec4\u63cf\u8ff0", dbType=DBColumn.DBType.NVarchar, length=200)
    private String description;
    @DBColumn(nameInDB="CREATOR", title="\u521b\u5efa\u4eba", dbType=DBColumn.DBType.NVarchar, length=60)
    private String creator;
    @DBColumn(nameInDB="CREATETIME", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    private Date createTime;
    @DBColumn(nameInDB="UPDATETIME", title="\u4fee\u6539\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    private Date updateTime;

    public static String getAllFieldSql(String tableAlias) {
        String prefix = "";
        String asStr = "";
        if (tableAlias != null && tableAlias.trim().length() > 0) {
            prefix = tableAlias + ".";
            asStr = "as";
        }
        String fieldSql = " " + prefix + "ID " + asStr + " id," + prefix + "RECVER " + asStr + " recver, ";
        Class<ConversionRateGroupEO> clazz = ConversionRateGroupEO.class;
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            if ("TABLENAME".equals(fields[i].getName()) || fields[i].getAnnotation(DBColumn.class) == null) continue;
            fieldSql = fieldSql + prefix + fields[i].getAnnotation(DBColumn.class).nameInDB().toLowerCase() + " " + asStr + " " + fields[i].getName() + ",";
        }
        return String.format(fieldSql.substring(0, fieldSql.length() - 1), new Object[0]);
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getPeriodId() {
        return this.periodId;
    }

    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}

