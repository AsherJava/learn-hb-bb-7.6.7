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

@DBTable(name="GC_SAMECTRLOFFSETITEM", title="\u62b5\u9500\u5206\u5f55\u540c\u63a7", sourceTable="GC_OFFSETVCHRITEM")
public class SameCtrlOffSetItemEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_SAMECTRLOFFSETITEM";
    @DBColumn(title="\u4efb\u52a1id", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String taskId;
    @DBColumn(title="\u62a5\u8868\u65b9\u6848", dbType=DBColumn.DBType.Varchar, length=36)
    private String schemeId;
    @DBColumn(length=20, title="\u65f6\u671f\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar)
    private String defaultPeriod;
    @DBColumn(title="\u62b5\u9500\u89c4\u5219\u540d\u79f0", dbType=DBColumn.DBType.Varchar, length=36)
    private String ruleTitle;
    @DBColumn(title="\u62b5\u9500\u6a21\u5f0f", dbType=DBColumn.DBType.Int, isRequired=true, description="\u679a\u4e3e\u7c7b\uff0c0-5\u6570\u5b57\uff0c\u5b9a\u4e49\u4e0d\u540c\u62b5\u9500\u65b9\u5f0f")
    private Integer elmMode;
    @DBColumn(title="\u5206\u7ec4ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String mRecid;
    @DBColumn(title="\u5355\u4f4d\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String orgType;
    @DBColumn(title="\u5f55\u5165/\u5408\u5e76\u7ec4\u7ec7/\u6240\u5c5e\u865a\u62df\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String inputUnitCode;
    @DBColumn(title="\u5f55\u5165/\u5408\u5e76\u7ec4\u7ec7\u8def\u5f84", dbType=DBColumn.DBType.Varchar, length=610)
    private String inputUnitParents;
    @DBColumn(title="\u540c\u63a7\u53d8\u52a8\u524d\u540e\u5171\u540c\u4e0a\u7ea7\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=36, description="GC_SAMECTRLCHGORG\u8868\u7684sameParentCode")
    private String sameParentCode;
    @DBColumn(title="\u540c\u63a7\u53d8\u52a8\u524d\u540e\u6536\u8d2d\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=36, description="GC_SAMECTRLCHGORG\u8868\u7684changedParentCode")
    private String changedParentCode;
    @DBColumn(title="\u672c\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=36)
    private String unitCode;
    @DBColumn(title="\u5bf9\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=36)
    private String oppUnitCode;
    @DBColumn(title="\u79d1\u76ee", dbType=DBColumn.DBType.NVarchar, isRequired=true)
    private String subjectCode;
    @DBColumn(title="\u62b5\u9500\u501f\u65b9\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    private Double offSetDebit;
    @DBColumn(title="\u62b5\u9500\u8d37\u65b9\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    private Double offSetCredit;
    @DBColumn(title="\u62b5\u9500\u5e01\u79cd\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=50)
    private String offSetCurr;
    @DBColumn(title="\u501f\u65b9\u5dee\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    private Double diffd;
    @DBColumn(title="\u8d37\u65b9\u5dee\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=4)
    private Double diffc;
    @DBColumn(title="\u501f\u8d37\u65b9\u5411", dbType=DBColumn.DBType.Int)
    private Integer orient;
    @DBColumn(title="\u65f6\u95f4\u6233", dbType=DBColumn.DBType.Date)
    private Date createTime;
    @DBColumn(title="\u6765\u6e90\u62b5\u9500\u5206\u7ec4id", dbType=DBColumn.DBType.Varchar, length=36)
    private String srcOffsetGroupId;
    @DBColumn(title="\u5355\u4f4d\u53d8\u52a8\u5e74", description="GC_SAMECTRLCHGORG\u8868\u7684disposalDate\u63d0\u53d6\u51fa\u5e74", dbType=DBColumn.DBType.Int, isRequired=true)
    private Integer unitChangeYear;
    @DBColumn(title="\u540c\u63a7\u53d8\u52a8\u5355\u4f4d\u8868\u7684id", description="GC_SAMECTRLCHGORG\u8868\u7684id", dbType=DBColumn.DBType.Varchar, isRequired=true, length=36)
    private String sameCtrlChgId;
    @DBColumn(title="\u63cf\u8ff0\u4fe1\u606f", dbType=DBColumn.DBType.NVarchar, length=100, description="\u5ba2\u6237\u539f\u59cb\u5f55\u5165\u7684\u63cf\u8ff0")
    private String memo;
    @DBColumn(title="\u62b5\u9500\u6765\u6e90ID", dbType=DBColumn.DBType.Varchar, length=36, description="\u5bf9\u5e94\u91c7\u96c6\u65b9\u7684id\u6216\u8005\u5206\u7ec4id")
    private String srcId;
    @DBColumn(title="\u5f71\u54cd\u671f\u95f4", dbType=DBColumn.DBType.NVarchar, length=20)
    private String effectType;
    @DBColumn(title="\u540c\u63a7\u6765\u6e90\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=50)
    private String sameCtrlSrcType;
    @DBColumn(title="\u521b\u5efa\u4eba", dbType=DBColumn.DBType.NVarchar, length=50)
    private String createUser;
    @DBColumn(nameInDB="ADJUST", title="\u8c03\u6574\u671f", dbType=DBColumn.DBType.NVarchar, length=100)
    private String adjust;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getDefaultPeriod() {
        return this.defaultPeriod;
    }

    public void setDefaultPeriod(String defaultPeriod) {
        this.defaultPeriod = defaultPeriod;
    }

    public String getRuleTitle() {
        return this.ruleTitle;
    }

    public void setRuleTitle(String ruleTitle) {
        this.ruleTitle = ruleTitle;
    }

    public Integer getElmMode() {
        return this.elmMode;
    }

    public void setElmMode(Integer elmMode) {
        this.elmMode = elmMode;
    }

    public String getmRecid() {
        return this.mRecid;
    }

    public void setmRecid(String mRecid) {
        this.mRecid = mRecid;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getInputUnitCode() {
        return this.inputUnitCode;
    }

    public void setInputUnitCode(String inputUnitCode) {
        this.inputUnitCode = inputUnitCode;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getOppUnitCode() {
        return this.oppUnitCode;
    }

    public void setOppUnitCode(String oppUnitCode) {
        this.oppUnitCode = oppUnitCode;
    }

    public String getSameParentCode() {
        return this.sameParentCode;
    }

    public void setSameParentCode(String sameParentCode) {
        this.sameParentCode = sameParentCode;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Double getOffSetDebit() {
        return this.offSetDebit;
    }

    public void setOffSetDebit(Double offSetDebit) {
        this.offSetDebit = offSetDebit;
    }

    public Double getOffSetCredit() {
        return this.offSetCredit;
    }

    public void setOffSetCredit(Double offSetCredit) {
        this.offSetCredit = offSetCredit;
    }

    public String getOffSetCurr() {
        return this.offSetCurr;
    }

    public void setOffSetCurr(String offSetCurr) {
        this.offSetCurr = offSetCurr;
    }

    public Double getDiffd() {
        return this.diffd;
    }

    public void setDiffd(Double diffd) {
        this.diffd = diffd;
    }

    public Double getDiffc() {
        return this.diffc;
    }

    public void setDiffc(Double diffc) {
        this.diffc = diffc;
    }

    public Integer getOrient() {
        return this.orient;
    }

    public void setOrient(Integer orient) {
        this.orient = orient;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSrcOffsetGroupId() {
        return this.srcOffsetGroupId;
    }

    public void setSrcOffsetGroupId(String srcOffsetGroupId) {
        this.srcOffsetGroupId = srcOffsetGroupId;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getSrcId() {
        return this.srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public String getEffectType() {
        return this.effectType;
    }

    public void setEffectType(String effectType) {
        this.effectType = effectType;
    }

    public String getSameCtrlSrcType() {
        return this.sameCtrlSrcType;
    }

    public void setSameCtrlSrcType(String sameCtrlSrcType) {
        this.sameCtrlSrcType = sameCtrlSrcType;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public void setInputUnitParents(String inputUnitParents) {
        this.inputUnitParents = inputUnitParents;
    }

    public String getInputUnitParents() {
        return this.inputUnitParents;
    }

    public String getSameCtrlChgId() {
        return this.sameCtrlChgId;
    }

    public void setSameCtrlChgId(String sameCtrlChgId) {
        this.sameCtrlChgId = sameCtrlChgId;
    }

    public Integer getUnitChangeYear() {
        return this.unitChangeYear;
    }

    public void setUnitChangeYear(Integer unitChangeYear) {
        this.unitChangeYear = unitChangeYear;
    }

    public String getChangedParentCode() {
        return this.changedParentCode;
    }

    public void setChangedParentCode(String changedParentCode) {
        this.changedParentCode = changedParentCode;
    }

    public String getAdjust() {
        return this.adjust;
    }

    public void setAdjust(String adjust) {
        this.adjust = adjust;
    }
}

