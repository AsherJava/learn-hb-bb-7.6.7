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

@DBTable(name="GC_CONV_RATE_T", title="\u6298\u7b97\u6c47\u7387\u4fe1\u606f\u8868", inStorage=true, indexs={@DBIndex(name="IDX_GC_CONV_RATE_T_COM1", columnsFields={"RATEGROUPID", "SOURCECURRENCYCODE", "TARGETCURRENCYCODE"})})
public class ConversionRateNodeEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_CONV_RATE_T";
    @DBColumn(nameInDB="RATEGROUPID", title="\u6c47\u7387\u5206\u7ec4", dbType=DBColumn.DBType.Varchar, length=36)
    private String rateGroupId;
    @DBColumn(nameInDB="SOURCECURRENCYCODE", title="\u6e90\u5e01\u79cd\u7f16\u7801", dbType=DBColumn.DBType.NVarchar, length=20)
    private String sourceCurrencyCode;
    @DBColumn(nameInDB="TARGETCURRENCYCODE", title="\u76ee\u6807\u5e01\u79cd\u7f16\u7801", dbType=DBColumn.DBType.NVarchar, length=20)
    private String targetCurrencyCode;
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
        Class<ConversionRateNodeEO> clazz = ConversionRateNodeEO.class;
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            if ("TABLENAME".equals(fields[i].getName()) || fields[i].getAnnotation(DBColumn.class) == null) continue;
            fieldSql = fieldSql + prefix + fields[i].getAnnotation(DBColumn.class).nameInDB().toLowerCase() + " " + asStr + " " + fields[i].getName() + ",";
        }
        return String.format(fieldSql.substring(0, fieldSql.length() - 1), new Object[0]);
    }

    public String getRateGroupId() {
        return this.rateGroupId;
    }

    public void setRateGroupId(String rateGroupId) {
        this.rateGroupId = rateGroupId;
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
}

