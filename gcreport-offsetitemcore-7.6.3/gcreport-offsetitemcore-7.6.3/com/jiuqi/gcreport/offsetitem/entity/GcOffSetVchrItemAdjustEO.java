/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndexs
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend
 *  com.jiuqi.gcreport.dimension.internal.utils.DimensionManagerUtil
 */
package com.jiuqi.gcreport.offsetitem.entity;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBIndexs;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionFieldV;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.basic.intf.ITableExtend;
import com.jiuqi.gcreport.dimension.internal.utils.DimensionManagerUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DBTable(name="GC_OFFSETVCHRITEM", title="\u62b5\u9500\u5206\u5f55", inStorage=true, convertToBudModel=true, extendFieldDefaultVal=false)
@DBIndexs(value={@DBIndex(name="IDX_OFFSET_MRECID", columnsFields={"MRECID"}), @DBIndex(name="IDX_OFFSET_SRCID", columnsFields={"SRCID"}), @DBIndex(name="IDX_OFFSET_DATATIME_OPP_UNITID", columnsFields={"DATATIME", "OPPUNITID", "UNITID"}), @DBIndex(name="IDX_OFFSET_SUBJECT_SYSTEMID", columnsFields={"SUBJECTCODE", "SYSTEMID"}), @DBIndex(name="IDX_OFFSET_SRCOFFSETGROUPID", columnsFields={"SRCOFFSETGROUPID"}), @DBIndex(name="IDX_OFFSET_DATATIME_SUBJECT", columnsFields={"DATATIME", "SUBJECTCODE"})})
public class GcOffSetVchrItemAdjustEO
extends DefaultTableEntity
implements ITableExtend {
    public static final String TABLENAME = "GC_OFFSETVCHRITEM";
    public static final String SRCOFFSETGROUPID = "SRCOFFSETGROUPID";
    @DBColumn(nameInDB="MRECID", title="\u5206\u7ec4ID", dbType=DBColumn.DBType.Varchar, length=36)
    protected String mRecid;
    @DBColumn(nameInDB="TASKID", title="\u4efb\u52a1id", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    protected String taskId;
    @DBColumn(nameInDB="FETCHSETGROUPID", title="\u53d6\u6570\u8bbe\u7f6e\u5206\u7ec4id", dbType=DBColumn.DBType.Varchar)
    protected String fetchSetGroupId;
    @DBColumn(nameInDB="SRCOFFSETGROUPID", title="\u6765\u6e90\u62b5\u9500\u5206\u7ec4id", dbType=DBColumn.DBType.Varchar, length=36)
    protected String srcOffsetGroupId;
    @DBColumn(length=20, nameInDB="DATATIME", title="\u65f6\u671f\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar)
    protected String defaultPeriod;
    @DBColumn(nameInDB="GCBUSINESSTYPECODE", title="\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar)
    protected String gcBusinessTypeCode;
    @DBColumn(nameInDB="UNITID", title="\u672c\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    protected String unitId;
    @DBColumn(nameInDB="OPPUNITID", title="\u5bf9\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    protected String oppUnitId;
    @DBColumn(length=36, nameInDB="SYSTEMID", title="\u5408\u5e76\u4f53\u7cfbID", dbType=DBColumn.DBType.Varchar)
    protected String systemId;
    @DBColumn(nameInDB="SUBJECTCODE", title="\u79d1\u76ee", dbType=DBColumn.DBType.NVarchar, isRequired=true)
    protected String subjectCode;
    @DBColumn(nameInDB="SUBJECTORIENT", title="\u79d1\u76ee\u501f\u8d37\u65b9\u5411", dbType=DBColumn.DBType.Int)
    private Integer subjectOrient;
    @DBColumn(nameInDB="MEMO", title="\u63cf\u8ff0\u4fe1\u606f", dbType=DBColumn.DBType.NVarchar, length=200, description="\u5ba2\u6237\u539f\u59cb\u5f55\u5165\u7684\u63cf\u8ff0")
    protected String memo;
    @DBColumn(nameInDB="MODIFYTIME", title="\u4fee\u6539\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    protected Date modifyTime;
    @DBColumn(nameInDB="OFFSETCURR", title="\u62b5\u9500\u5e01\u79cd\u4ee3\u7801", dbType=DBColumn.DBType.NVarchar, length=50, description="\u53c2\u4e0e\u6838\u5bf9\u7684\u5e01\u79cd\u4ee3\u7801\uff0c\u5bf9\u5e94\u5e01\u79cd\u57fa\u7840\u6570\u636e\u8868")
    protected String offSetCurr;
    @DBColumn(nameInDB="OFFSET_DEBIT", title="\u62b5\u9500\u501f\u65b9\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    protected Double offSetDebit;
    @DBColumn(nameInDB="OFFSET_CREDIT", title="\u62b5\u9500\u8d37\u65b9\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    protected Double offSetCredit;
    @DBColumn(nameInDB="DIFFD", title="\u501f\u65b9\u5dee\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    protected Double diffd;
    @DBColumn(nameInDB="DIFFC", title="\u8d37\u65b9\u5dee\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    protected Double diffc;
    @DBColumn(nameInDB="MD_GCORGTYPE", title="\u5355\u4f4d\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String orgType;
    @DBColumn(nameInDB="ELMMODE", title="\u62b5\u9500\u6a21\u5f0f", dbType=DBColumn.DBType.Int, isRequired=true, description="\u679a\u4e3e\u7c7b\uff0c0-5\u6570\u5b57\uff0c\u5b9a\u4e49\u4e0d\u540c\u62b5\u9500\u65b9\u5f0f")
    protected Integer elmMode;
    @DBColumn(nameInDB="RULEID", title="\u62b5\u9500\u89c4\u5219ID", dbType=DBColumn.DBType.Varchar, length=36)
    protected String ruleId;
    @DBColumn(nameInDB="CREATETIME", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    protected Date createTime;
    @DBColumn(nameInDB="OFFSETSRCTYPE", title="\u62b5\u9500\u6765\u6e90\u7c7b\u578b", dbType=DBColumn.DBType.Int, isRequired=true, description="\u679a\u4e3e\u7c7b\uff0c0-99\u6570\u5b57\uff0c\u5b9a\u4e49\u4e0d\u540c\u6765\u6e90\u7c7b\u578b")
    protected Integer offSetSrcType;
    @DBColumn(nameInDB="SRCID", title="\u62b5\u9500\u6765\u6e90ID", dbType=DBColumn.DBType.Varchar, length=36, description="\u5bf9\u5e94\u91c7\u96c6\u65b9\u7684id\u6216\u8005\u5206\u7ec4id")
    protected String srcId;
    @DBColumn(nameInDB="EFFECTTYPE", title="\u5f71\u54cd\u671f\u95f4", dbType=DBColumn.DBType.NVarchar, length=20)
    private String effectType;
    @DBColumn(nameInDB="DISABLEFLAG", title="\u7981\u7528\u72b6\u6001", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer disableFlag = 0;
    @DBColumn(nameInDB="CREATEUSER", title="\u64cd\u4f5c\u4eba", dbType=DBColumn.DBType.NVarchar, length=100)
    private String createUser;
    @DBColumn(nameInDB="CHKSTATE", title="\u5bf9\u8d26\u72b6\u6001", dbType=DBColumn.DBType.NVarchar, length=50, refTabField="MD_CHKSTATE.CODE")
    private String chkState;
    @DBColumn(nameInDB="CHKSTATECHANGE", title="\u5bf9\u8d26\u53d8\u52a8\u4fe1\u606f", dbType=DBColumn.DBType.NVarchar, length=30, refTabField="MD_CHKSTATE_CHANGE.CODE")
    private String chkStateChange;
    @DBColumn(nameInDB="ADJUST", title="\u8c03\u6574\u671f", dbType=DBColumn.DBType.NVarchar, length=100)
    private String adjust;
    @DBColumn(nameInDB="VCHRCODE", title="\u62b5\u9500\u5206\u5f55\u7f16\u53f7", dbType=DBColumn.DBType.NVarchar, length=50)
    private String vchrCode;

    public String getVchrCode() {
        return this.vchrCode;
    }

    public void setVchrCode(String vchrCode) {
        this.vchrCode = vchrCode;
    }

    public String getAdjust() {
        return this.adjust;
    }

    public void setAdjust(String adjust) {
        this.adjust = adjust;
    }

    public String getmRecid() {
        return this.mRecid;
    }

    public void setmRecid(String mRecid) {
        this.addFieldValue("MRECID", mRecid);
        this.mRecid = mRecid;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.addFieldValue("TASKID", taskId);
        this.taskId = taskId;
    }

    public String getFetchSetGroupId() {
        return this.fetchSetGroupId;
    }

    public void setFetchSetGroupId(String fetchSetGroupId) {
        this.addFieldValue("FETCHSETGROUPID", fetchSetGroupId);
        this.fetchSetGroupId = fetchSetGroupId;
    }

    public String getSrcOffsetGroupId() {
        return this.srcOffsetGroupId;
    }

    public void setSrcOffsetGroupId(String srcOffsetGroupId) {
        this.addFieldValue(SRCOFFSETGROUPID, srcOffsetGroupId);
        this.srcOffsetGroupId = srcOffsetGroupId;
    }

    public String getDefaultPeriod() {
        return this.defaultPeriod;
    }

    public void setDefaultPeriod(String defaultPeriod) {
        this.addFieldValue("DEFAULTPERIOD", defaultPeriod);
        this.defaultPeriod = defaultPeriod;
    }

    public String getGcBusinessTypeCode() {
        return this.gcBusinessTypeCode;
    }

    public void setGcBusinessTypeCode(String gcBusinessTypeCode) {
        this.addFieldValue("GCBUSINESSTYPECODE", gcBusinessTypeCode);
        this.gcBusinessTypeCode = gcBusinessTypeCode;
    }

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.addFieldValue("UNITID", unitId);
        this.unitId = unitId;
    }

    public String getOppUnitId() {
        return this.oppUnitId;
    }

    public void setOppUnitId(String oppUnitId) {
        this.addFieldValue("OPPUNITID", oppUnitId);
        this.oppUnitId = oppUnitId;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.addFieldValue("SYSTEMID", systemId);
        this.systemId = systemId;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.addFieldValue("SUBJECTCODE", subjectCode);
        this.subjectCode = subjectCode;
    }

    public Integer getSubjectOrient() {
        return this.subjectOrient;
    }

    public void setSubjectOrient(Integer orient) {
        this.addFieldValue("SUBJECTORIENT", orient);
        this.subjectOrient = orient;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.addFieldValue("MEMO", memo);
        this.memo = memo;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.addFieldValue("MODIFYTIME", modifyTime);
        this.modifyTime = modifyTime;
    }

    public String getOffSetCurr() {
        return this.offSetCurr;
    }

    public void setOffSetCurr(String offSetCurr) {
        this.addFieldValue("OFFSETCURR", offSetCurr);
        this.offSetCurr = offSetCurr;
    }

    public Double getOffSetDebit() {
        return this.offSetDebit;
    }

    public void setOffSetDebit(Double offSetDebit) {
        this.addFieldValue("OFFSETDEBIT", offSetDebit);
        this.offSetDebit = offSetDebit;
    }

    public Double getOffSetCredit() {
        return this.offSetCredit;
    }

    public void setOffSetCredit(Double offSetCredit) {
        this.addFieldValue("OFFSETCREDIT", offSetCredit);
        this.offSetCredit = offSetCredit;
    }

    public Double getDiffd() {
        return this.diffd;
    }

    public void setDiffd(Double diffd) {
        this.addFieldValue("DIFFD", diffd);
        this.diffd = diffd;
    }

    public Double getDiffc() {
        return this.diffc;
    }

    public void setDiffc(Double diffc) {
        this.addFieldValue("DIFFC", diffc);
        this.diffc = diffc;
    }

    public Integer getElmMode() {
        return this.elmMode;
    }

    public void setElmMode(Integer elmMode) {
        this.addFieldValue("ELMMODE", elmMode);
        this.elmMode = elmMode;
    }

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.addFieldValue("RULEID", ruleId);
        this.ruleId = ruleId;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.addFieldValue("CREATETIME", createTime);
        this.createTime = createTime;
    }

    public Integer getOffSetSrcType() {
        return this.offSetSrcType;
    }

    public void setOffSetSrcType(Integer offSetSrcType) {
        this.addFieldValue("OFFSETSRCTYPE", offSetSrcType);
        this.offSetSrcType = offSetSrcType;
    }

    public String getSrcId() {
        return this.srcId;
    }

    public void setSrcId(String srcId) {
        this.addFieldValue("SRCID", srcId);
        this.srcId = srcId;
    }

    public String getOrgType() {
        if (null == this.orgType) {
            this.orgType = "NONE";
        }
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        if (null == orgType) {
            orgType = "NONE";
        }
        this.addFieldValue("ORGTYPE", orgType);
        this.orgType = orgType;
    }

    public String getEffectType() {
        return this.effectType;
    }

    public void setEffectType(String effectType) {
        this.effectType = effectType;
    }

    public Integer getDisableFlag() {
        return this.disableFlag;
    }

    public void setDisableFlag(Integer disableFlag) {
        this.disableFlag = disableFlag;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getChkState() {
        return this.chkState;
    }

    public void setChkState(String chkState) {
        this.chkState = chkState;
    }

    public String getChkStateChange() {
        return this.chkStateChange;
    }

    public void setChkStateChange(String chkStateChange) {
        this.chkStateChange = chkStateChange;
    }

    public Double getOffsetAmt() {
        if (ConverterUtils.getAsDouble((Object)this.getOffSetDebit()) != 0.0) {
            return ConverterUtils.getAsDouble((Object)this.getOffSetDebit());
        }
        return ConverterUtils.getAsDouble((Object)this.getOffSetCredit());
    }

    public Integer getOrient() {
        if (this.offSetDebit != null && this.offSetDebit != 0.0) {
            return 1;
        }
        if (this.offSetCredit != null && this.offSetCredit != 0.0) {
            return -1;
        }
        return -this.subjectOrient.intValue();
    }

    public List<DefinitionFieldV> getExtendFieldList(String param) {
        ArrayList fields = CollectionUtils.newArrayList();
        List extendColumn = DimensionManagerUtil.getExtendColumn((String)TABLENAME);
        fields.addAll(extendColumn);
        return fields;
    }
}

