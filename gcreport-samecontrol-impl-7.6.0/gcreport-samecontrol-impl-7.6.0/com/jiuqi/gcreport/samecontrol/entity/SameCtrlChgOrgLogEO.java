/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.samecontrol.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_SAMECTRLCHGORGLOG", title="\u540c\u63a7\u53d8\u52a8\u5355\u4f4d")
public class SameCtrlChgOrgLogEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_SAMECTRLCHGORGLOG";
    @DBColumn(title="\u865a\u62df\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=60)
    private String virtualCode;
    @DBColumn(title="\u865a\u62df\u5355\u4f4d\u4e0a\u7ea7", dbType=DBColumn.DBType.Varchar, length=60)
    private String virtualParentCode;
    @DBColumn(title="\u865a\u62df\u5355\u4f4d\u8def\u5f84", dbType=DBColumn.DBType.Varchar, length=610)
    private String virtualParents;
    @DBColumn(title="\u53d8\u52a8\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=60, isRequired=true)
    private String changedCode;
    @DBColumn(title="\u53d8\u52a8\u5355\u4f4d\u4e0a\u7ea7", dbType=DBColumn.DBType.Varchar, length=60)
    private String changedParentCode;
    @DBColumn(title="\u53d8\u52a8\u5355\u4f4d\u8def\u5f84", dbType=DBColumn.DBType.Varchar, length=610)
    private String changedParents;
    @DBColumn(title="\u5171\u540c\u4e0a\u7ea7", dbType=DBColumn.DBType.Varchar, length=60, isRequired=true)
    private String sameParentCode;
    @DBColumn(title="\u5355\u4f4d\u7248\u672cID", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String orgVersionId;
    @DBColumn(title="\u53d8\u52a8\u65e5\u671f", dbType=DBColumn.DBType.Numeric, precision=19, scale=0, isRequired=true)
    private Long changeDate;
    @DBColumn(title="\u53f0\u8d26\u5904\u7f6e\u65e5\u671f", dbType=DBColumn.DBType.Date)
    private Date disposalDate;
    @DBColumn(title="\u64cd\u4f5c", dbType=DBColumn.DBType.NVarchar, isRequired=true)
    private String operating;
    @DBColumn(title="\u662f\u5426\u5220\u9664", dbType=DBColumn.DBType.Numeric, precision=1, scale=0, isRequired=true)
    private Integer deleteFlag;
    @DBColumn(title="\u540c\u63a7/\u975e\u540c\u63a7", dbType=DBColumn.DBType.Int, isRequired=true)
    private Integer changedOrgType;
    @DBColumn(title="\u64cd\u4f5c\u4eba", dbType=DBColumn.DBType.NVarchar, isRequired=true)
    private String operatorId;
    @DBColumn(title="\u64cd\u4f5c\u65f6\u95f4", dbType=DBColumn.DBType.Numeric, precision=19, scale=0, isRequired=true)
    private Long operateTime;

    public String getVirtualCode() {
        return this.virtualCode;
    }

    public void setVirtualCode(String virtualCode) {
        this.virtualCode = virtualCode;
    }

    public String getVirtualParentCode() {
        return this.virtualParentCode;
    }

    public void setVirtualParentCode(String virtualParentCode) {
        this.virtualParentCode = virtualParentCode;
    }

    public String getVirtualParents() {
        return this.virtualParents;
    }

    public void setVirtualParents(String virtualParents) {
        this.virtualParents = virtualParents;
    }

    public String getChangedCode() {
        return this.changedCode;
    }

    public void setChangedCode(String changedCode) {
        this.changedCode = changedCode;
    }

    public String getChangedParentCode() {
        return this.changedParentCode;
    }

    public void setChangedParentCode(String changedParentCode) {
        this.changedParentCode = changedParentCode;
    }

    public String getChangedParents() {
        return this.changedParents;
    }

    public void setChangedParents(String changedParents) {
        this.changedParents = changedParents;
    }

    public String getSameParentCode() {
        return this.sameParentCode;
    }

    public void setSameParentCode(String sameParentCode) {
        this.sameParentCode = sameParentCode;
    }

    public String getOrgVersionId() {
        return this.orgVersionId;
    }

    public void setOrgVersionId(String orgVersionId) {
        this.orgVersionId = orgVersionId;
    }

    public Long getChangeDate() {
        return this.changeDate;
    }

    public void setChangeDate(Long changeDate) {
        this.changeDate = changeDate;
    }

    public Date getDisposalDate() {
        return this.disposalDate;
    }

    public void setDisposalDate(Date disposalDate) {
        this.disposalDate = disposalDate;
    }

    public String getOperating() {
        return this.operating;
    }

    public void setOperating(String operating) {
        this.operating = operating;
    }

    public String getOperatorId() {
        return this.operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Long getOperateTime() {
        return this.operateTime;
    }

    public void setOperateTime(Long operateTime) {
        this.operateTime = operateTime;
    }

    public Integer getDeleteFlag() {
        return this.deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getChangedOrgType() {
        return this.changedOrgType;
    }

    public void setChangedOrgType(Integer changedOrgType) {
        this.changedOrgType = changedOrgType;
    }

    public String toString() {
        return "SameCtrlChgOrgLogEO{virtualCode='" + this.virtualCode + '\'' + ", virtualParentCode='" + this.virtualParentCode + '\'' + ", virtualParents='" + this.virtualParents + '\'' + ", changedCode='" + this.changedCode + '\'' + ", changedParentCode='" + this.changedParentCode + '\'' + ", changedParents='" + this.changedParents + '\'' + ", sameParentCode='" + this.sameParentCode + '\'' + ", orgVersionId='" + this.orgVersionId + '\'' + ", changeDate=" + this.changeDate + ", disposalDate=" + this.disposalDate + ", operating='" + this.operating + '\'' + ", operatorId='" + this.operatorId + '\'' + ", operateTime=" + this.operateTime + '}';
    }
}

