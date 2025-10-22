/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.conversion.conversionsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_CONV_SYSTEM", title="\u6298\u7b97\u4f53\u7cfb", inStorage=true)
public class ConversionSystemEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_CONV_SYSTEM";
    @DBColumn(nameInDB="SYSTEMNAME", title="\u4f53\u7cfb\u540d\u79f0", dbType=DBColumn.DBType.NVarchar, length=60)
    private String systemName;
    @DBColumn(nameInDB="SYSTEMCODE", title="\u4f53\u7cfb\u7f16\u7801", dbType=DBColumn.DBType.NVarchar, length=60)
    private String systemCode;
    @DBColumn(nameInDB="DESCRIPTION", title="\u4f53\u7cfb\u63cf\u8ff0", dbType=DBColumn.DBType.NVarchar, length=200)
    private String description;
    @DBColumn(nameInDB="CREATOR", title="\u521b\u5efa\u4eba", dbType=DBColumn.DBType.NVarchar, length=60)
    private String creator;
    @DBColumn(nameInDB="CREATETIME", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;
    @DBColumn(nameInDB="UPDATETIME", title="\u4fee\u6539\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;
    @DBColumn(nameInDB="SORTORDER", title="\u5e8f\u53f7", dbType=DBColumn.DBType.Int)
    private Integer sortOrder;
    @DBColumn(nameInDB="PARENTID", title="\u7236\u8282\u70b9\u6807\u8bc6", dbType=DBColumn.DBType.Varchar, length=36)
    private String parentId;

    public String getSystemName() {
        return this.systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemCode() {
        return this.systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
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

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}

