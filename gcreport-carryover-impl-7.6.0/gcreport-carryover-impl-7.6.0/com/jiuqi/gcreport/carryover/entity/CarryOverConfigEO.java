/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.carryover.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_CARRYOVER_CONFIG", title="\u5e74\u7ed3\u8bbe\u7f6e\u8868")
public class CarryOverConfigEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_CARRYOVER_CONFIG";
    @DBColumn(nameInDB="TITLE", title="\u65b9\u6848\u540d\u79f0", dbType=DBColumn.DBType.Varchar, length=100, isRequired=true)
    private String title;
    @DBColumn(nameInDB="TYPECODE", title="\u65b9\u6848\u7c7b\u578bcode", dbType=DBColumn.DBType.Varchar, length=50, isRequired=true)
    private String typeCode;
    @DBColumn(nameInDB="CREATOR", title="\u521b\u5efa\u8005", dbType=DBColumn.DBType.Varchar, length=80, isRequired=true)
    private String creator;
    @DBColumn(nameInDB="CREATETIME", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date createTime;
    @DBColumn(nameInDB="UPDATETIME", title="\u66f4\u65b0\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date updateTime;
    @DBColumn(nameInDB="PARENTID", title="\u7236\u8282\u70b9id", dbType=DBColumn.DBType.Varchar)
    private String parentId;
    @DBColumn(nameInDB="LEAFFLAG", title="\u662f\u5426\u53f6\u5b50\u8282\u70b9", dbType=DBColumn.DBType.Numeric, length=1)
    private Integer leafFlag;
    @DBColumn(nameInDB="ORDINAL", title="\u6392\u5e8f\u5b57\u6bb5", dbType=DBColumn.DBType.Numeric)
    private Double ordinal;
    @DBColumn(nameInDB="OPTIONDATA", title="\u9009\u9879\u6570\u636e", dbType=DBColumn.DBType.NText)
    private String optionData;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeCode() {
        return this.typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLeafFlag() {
        return this.leafFlag;
    }

    public void setLeafFlag(Integer leafFlag) {
        this.leafFlag = leafFlag;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Double getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(Double ordinal) {
        this.ordinal = ordinal;
    }

    public String getOptionData() {
        return this.optionData;
    }

    public void setOptionData(String optionData) {
        this.optionData = optionData;
    }
}

