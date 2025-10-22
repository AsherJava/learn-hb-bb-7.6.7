/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.clbr.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_CLBR_SCHEME", title="\u534f\u540c\u65b9\u6848\u8868")
public class ClbrSchemeEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_CLBR_SCHEME";
    private static final long serialVersionUID = 4305699584985931413L;
    @DBColumn(title="\u7236\u7ea7ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String parentId;
    @DBColumn(title="\u662f\u5426\u53f6\u5b50\u8282\u70b9", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer leafFlag;
    @DBColumn(title="\u65b9\u6848\u540d\u79f0", dbType=DBColumn.DBType.Varchar, length=60)
    private String title;
    @DBColumn(title="\u65b9\u6848\u63cf\u8ff0", dbType=DBColumn.DBType.Varchar, length=60)
    private String schemeDesc;
    @DBColumn(title="\u534f\u540c\u65b9\u6848\u4fe1\u606f", dbType=DBColumn.DBType.Text)
    private String clbrInfo;
    @DBColumn(title="\u6d41\u7a0b\u63a7\u5236\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, length=60)
    private String flowControlType;
    @DBColumn(title="\u51ed\u8bc1\u63a7\u5236", dbType=DBColumn.DBType.Varchar, length=60)
    private String vchrControlType;
    @DBColumn(title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date createTime;

    public String getClbrInfo() {
        return this.clbrInfo;
    }

    public void setClbrInfo(String clbrInfo) {
        this.clbrInfo = clbrInfo;
    }

    public String getFlowControlType() {
        return this.flowControlType;
    }

    public void setFlowControlType(String flowControlType) {
        this.flowControlType = flowControlType;
    }

    public String getVchrControlType() {
        return this.vchrControlType;
    }

    public void setVchrControlType(String vchrControlType) {
        this.vchrControlType = vchrControlType;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getLeafFlag() {
        return this.leafFlag;
    }

    public void setLeafFlag(Integer leafFlag) {
        this.leafFlag = leafFlag;
    }

    public String getSchemeDesc() {
        return this.schemeDesc;
    }

    public void setSchemeDesc(String schemeDesc) {
        this.schemeDesc = schemeDesc;
    }
}

