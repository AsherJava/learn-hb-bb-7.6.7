/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndexs
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.offsetitem.init.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBIndexs;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_OFFSETVCHRITEM_INIT", title="\u521d\u59cb\u62b5\u9500\u5206\u5f55\u8868", inStorage=true, sourceTable="GC_OFFSETVCHRITEM")
@DBIndexs(value={@DBIndex(name="INDEX_INITOFFSET_MRECID", columnsFields={"MRECID"}), @DBIndex(name="INDEX_INITOFFSET_SRCID", columnsFields={"SRCID"}), @DBIndex(name="INDEX_INITOFFSET_UNITID", columnsFields={"OPPUNITID", "UNITID"})})
public class GcOffSetVchrItemInitEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_OFFSETVCHRITEM_INIT";
    @DBColumn(nameInDB="MRECID", title="\u5206\u7ec4ID", dbType=DBColumn.DBType.Varchar)
    protected String mRecid;
    @DBColumn(nameInDB="ACCTYEAR", title="\u5e74\u5ea6", dbType=DBColumn.DBType.Int, isRequired=true)
    protected Integer acctYear;
    @DBColumn(nameInDB="SRCOFFSETGROUPID", title="\u6765\u6e90\u62b5\u9500\u5206\u7ec4id", dbType=DBColumn.DBType.Varchar)
    protected String srcOffsetGroupId;
    @DBColumn(nameInDB="GCBUSINESSTYPECODE", title="\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar)
    protected String gcBusinessTypeCode;
    @DBColumn(nameInDB="UNITID", title="\u672c\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, isRequired=true)
    protected String unitId;
    @DBColumn(nameInDB="OPPUNITID", title="\u5bf9\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, isRequired=true)
    protected String oppUnitId;
    @DBColumn(nameInDB="SYSTEMID", title="\u5408\u5e76\u4f53\u7cfbID", dbType=DBColumn.DBType.Varchar)
    protected String systemId;
    @DBColumn(nameInDB="SUBJECTCODE", title="\u79d1\u76ee", dbType=DBColumn.DBType.NVarchar, isRequired=true)
    protected String subjectCode;
    @DBColumn(nameInDB="ORIENT", title="\u501f\u8d37\u65b9\u5411", dbType=DBColumn.DBType.Int, description="\u6570\u636e\u7684\u501f\u8d37\u65b9\u5411==\u79d1\u76ee\u653e\u89c4\u5219\u7684\u501f\u65b9\u8fd8\u662f\u8d37\u65b9")
    private Integer orient;
    @DBColumn(nameInDB="OFFSET_DEBIT", title="\u62b5\u9500\u501f\u65b9\u91d1\u989d", dbType=DBColumn.DBType.Numeric)
    protected Double offSetDebit;
    @DBColumn(nameInDB="OFFSET_CREDIT", title="\u62b5\u9500\u8d37\u65b9\u91d1\u989d", dbType=DBColumn.DBType.Numeric)
    protected Double offSetCredit;
    @DBColumn(nameInDB="DIFFD", title="\u501f\u65b9\u5dee\u989d", dbType=DBColumn.DBType.Numeric)
    protected Double diffd;
    @DBColumn(nameInDB="DIFFC", title="\u8d37\u65b9\u5dee\u989d", dbType=DBColumn.DBType.Numeric)
    protected Double diffc;
    @DBColumn(nameInDB="MEMO", title="\u63cf\u8ff0\u4fe1\u606f", dbType=DBColumn.DBType.NVarchar, length=200, description="\u5ba2\u6237\u539f\u59cb\u5f55\u5165\u7684\u63cf\u8ff0")
    protected String memo;
    @DBColumn(nameInDB="MODIFYTIME", title="\u4fee\u6539\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    protected Date modifyTime;
    @DBColumn(nameInDB="SORTORDER", title="\u6392\u5e8f\u5b57\u6bb5", dbType=DBColumn.DBType.Numeric)
    protected Double sortOrder;
    @DBColumn(nameInDB="OFFSETCURR", title="\u62b5\u9500\u5e01\u79cd\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=50, description="\u53c2\u4e0e\u6838\u5bf9\u7684\u5e01\u79cd\u4ee3\u7801\uff0c\u5bf9\u5e94\u5e01\u79cd\u57fa\u7840\u6570\u636e\u8868")
    protected String offSetCurr;
    @DBColumn(nameInDB="ELMMODE", title="\u62b5\u9500\u6a21\u5f0f", dbType=DBColumn.DBType.Int, isRequired=true, description="\u679a\u4e3e\u7c7b\uff0c0-5\u6570\u5b57\uff0c\u5b9a\u4e49\u4e0d\u540c\u62b5\u9500\u65b9\u5f0f")
    protected Integer elmMode;
    @DBColumn(nameInDB="RULEID", title="\u62b5\u9500\u89c4\u5219ID", dbType=DBColumn.DBType.Varchar)
    protected String ruleId;
    @DBColumn(nameInDB="CREATETIME", title="\u65f6\u95f4\u6233", dbType=DBColumn.DBType.Date)
    protected Date createTime;
    @DBColumn(nameInDB="OFFSETSRCTYPE", title="\u62b5\u9500\u6765\u6e90\u7c7b\u578b", dbType=DBColumn.DBType.Int, isRequired=true, description="\u679a\u4e3e\u7c7b\uff0c0-99\u6570\u5b57\uff0c\u5b9a\u4e49\u4e0d\u540c\u6765\u6e90\u7c7b\u578b")
    protected Integer offSetSrcType;
    @DBColumn(nameInDB="SRCID", title="\u62b5\u9500\u6765\u6e90ID", dbType=DBColumn.DBType.Varchar, description="\u5bf9\u5e94\u91c7\u96c6\u65b9\u7684id\u6216\u8005\u5206\u7ec4id")
    protected String srcId;
    @DBColumn(nameInDB="ASSETTITLE", title="\u8d44\u4ea7\u540d\u79f0", dbType=DBColumn.DBType.NVarchar, length=100)
    private String assetTitle;
    @DBColumn(nameInDB="SUBJECTORIENT", title="\u79d1\u76ee\u501f\u8d37\u65b9\u5411", dbType=DBColumn.DBType.Int, description="\u5197\u4f59\u79d1\u76ee\u7684\u501f\u8d37\u65b9\u5411\uff0c\u8f85\u52a9\u5904\u7406\u6570\u636e\u5f55\u5165")
    private Integer subjectOrient;
    @DBColumn(nameInDB="DISABLEFLAG", title="\u662f\u5426\u7981\u7528", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer disableFlag = 0;
    @DBColumn(nameInDB="EFFECTTYPE", title="\u5f71\u54cd\u671f\u95f4", dbType=DBColumn.DBType.NVarchar, length=40)
    private String effectType;
    @DBColumn(nameInDB="FETCHSETGROUPID", title="\u53d6\u6570\u8bbe\u7f6e\u5206\u7ec4id", dbType=DBColumn.DBType.Varchar)
    protected String fetchSetGroupId;
    @DBColumn(nameInDB="VCHRCODE", title="\u62b5\u9500\u5206\u5f55\u7f16\u53f7", dbType=DBColumn.DBType.NVarchar, length=50)
    private String vchrCode;

    public String getFetchSetGroupId() {
        return this.fetchSetGroupId;
    }

    public void setFetchSetGroupId(String fetchSetGroupId) {
        this.fetchSetGroupId = fetchSetGroupId;
    }

    public String getVchrCode() {
        return this.vchrCode;
    }

    public void setVchrCode(String vchrCode) {
        this.vchrCode = vchrCode;
    }

    public String getEffectType() {
        return this.effectType;
    }

    public void setEffectType(String effectType) {
        this.effectType = effectType;
    }

    public String getmRecid() {
        return this.mRecid;
    }

    public void setmRecid(String mRecid) {
        this.mRecid = mRecid;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public String getSrcOffsetGroupId() {
        return this.srcOffsetGroupId;
    }

    public void setSrcOffsetGroupId(String srcOffsetGroupId) {
        this.srcOffsetGroupId = srcOffsetGroupId;
    }

    public String getGcBusinessTypeCode() {
        return this.gcBusinessTypeCode;
    }

    public void setGcBusinessTypeCode(String gcBusinessTypeCode) {
        this.gcBusinessTypeCode = gcBusinessTypeCode;
    }

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getOppUnitId() {
        return this.oppUnitId;
    }

    public void setOppUnitId(String oppUnitId) {
        this.oppUnitId = oppUnitId;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Integer getOrient() {
        return this.orient;
    }

    public void setOrient(Integer orient) {
        this.orient = orient;
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

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Double getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Double sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getOffSetCurr() {
        return this.offSetCurr;
    }

    public void setOffSetCurr(String offSetCurr) {
        this.offSetCurr = offSetCurr;
    }

    public Integer getElmMode() {
        return this.elmMode;
    }

    public void setElmMode(Integer elmMode) {
        this.elmMode = elmMode;
    }

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getOffSetSrcType() {
        return this.offSetSrcType;
    }

    public void setOffSetSrcType(Integer offSetSrcType) {
        this.offSetSrcType = offSetSrcType;
    }

    public String getSrcId() {
        return this.srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public String getAssetTitle() {
        return this.assetTitle;
    }

    public void setAssetTitle(String assetTitle) {
        this.assetTitle = assetTitle;
    }

    public Integer getSubjectOrient() {
        return this.subjectOrient;
    }

    public void setSubjectOrient(Integer subjectOrient) {
        this.subjectOrient = subjectOrient;
    }

    public Integer getDisableFlag() {
        return this.disableFlag;
    }

    public void setDisableFlag(Integer disableFlag) {
        this.disableFlag = disableFlag;
    }
}

