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
package com.jiuqi.gcreport.inputdata.inputdata.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_INPUTDATATEMPLATE", title="\u5185\u90e8\u5f55\u5165\u6a21\u677f\u8868", indexs={@DBIndex(name="IDX_GC_INPUTDATATEMPLATE_OFGP", columnsFields={"OFFSETGROUPID"}), @DBIndex(name="IDX_GC_INPUTDATATEMPLATE_CKGP", columnsFields={"CHECKGROUPID"}), @DBIndex(name="IDX_GC_INPUTDATATEMPLATE_CNGP", columnsFields={"CONVERTGROUPID"}), @DBIndex(name="IDX_GC_INPUTDATATEMPLATE_COM1", columnsFields={"DATATIME", "SUBJECTCODE"}), @DBIndex(name="IDX_GC_INPUTDATATEMPLATE_COM2", columnsFields={"MDCODE", "DATATIME"}), @DBIndex(name="IDX_GC_INPUTDATATEMPLATE_COM3", columnsFields={"DATATIME", "UNIONRULEID"})})
public class InputDataEO
extends DefaultTableEntity {
    private static final long serialVersionUID = -6888811524527895389L;
    public static final String GC_INPUTDATATEMPLATE = "GC_INPUTDATATEMPLATE";
    public static final String TABLENAME = "GC_INPUTDATA";
    public static final String FIELDNAME_OPPUNIT = "OPPUNITID";
    public static final String FIELDNAME_DC = "DC";
    public static final String FIELDNAME_SUBJECTCODE = "SUBJECTCODE";
    public static final String FIELDNAME_SUBJECTOBJ = "SUBJECTOBJ";
    public static final String FIELDNAME_ORGCODE = "ORGCODE";
    @DBColumn(title="\u884c\u6807\u8bc6", nameInDB="ID", dbType=DBColumn.DBType.Varchar, isRecid=true)
    private String id;
    @DBColumn(nameInDB="MDCODE", title="\u7ec4\u7ec7\u5b9e\u4f53", dbType=DBColumn.DBType.NVarchar, length=50)
    private String mdOrg;
    @DBColumn(nameInDB="ORGCODE", title="\u672c\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.NVarchar, length=50, refTabField="MD_ORG_CORPORATE@ORG")
    private String orgCode;
    @DBColumn(nameInDB="MD_CURRENCY", title="\u5e01\u79cd", dbType=DBColumn.DBType.NVarchar, length=100, isRequired=true)
    private String currency;
    @DBColumn(nameInDB="MD_GCORGTYPE", title="\u5408\u5e76\u5355\u4f4d\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=50, isRequired=true)
    private String orgType;
    @DBColumn(nameInDB="DATATIME", title="\u9ed8\u8ba4\u65f6\u671f\u4e3b\u4f53", dbType=DBColumn.DBType.NVarchar, length=9, isRequired=true)
    private String period;
    @DBColumn(nameInDB="BIZKEYORDER", title="BIZKEYORDER", dbType=DBColumn.DBType.NVarchar, length=50, fieldValueType=13, isRequired=true)
    private String bizkeyOrder;
    @DBColumn(nameInDB="FLOATORDER", title="FLOATORDER", dbType=DBColumn.DBType.Numeric, fieldValueType=10, isRequired=true)
    private Double floatOrder;
    @DBColumn(nameInDB="OPPUNITID", title="\u5bf9\u65b9\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true, refTabField="MD_ORG_CORPORATE@ORG")
    private String oppUnitId;
    @DBColumn(nameInDB="SUBJECTCODE", title="\u79d1\u76ee\u4ee3\u7801\uff08\u8fc7\u6ee4\uff09", dbType=DBColumn.DBType.NVarchar, length=200)
    private String subjectCode;
    @DBColumn(nameInDB="SUBJECTOBJ", title="\u79d1\u76ee\uff08\u4e0b\u62c9\u9009\u62e9\uff09", dbType=DBColumn.DBType.NVarchar, length=200, isRequired=true, refTabField="MD_GCSUBJECT@BASE")
    private String subjectObjCode;
    @DBColumn(nameInDB="DC", title="\u501f\u8d37\u65b9\u5411", dbType=DBColumn.DBType.Int, precision=10)
    private Integer dc;
    @DBColumn(nameInDB="MEMO", title="\u63cf\u8ff0\u4fe1\u606f", dbType=DBColumn.DBType.NVarchar, length=100)
    private String memo;
    @DBColumn(nameInDB="CREATEUSER", title="\u521b\u5efa\u4eba", dbType=DBColumn.DBType.NVarchar, length=100)
    private String createUser;
    @DBColumn(nameInDB="CREATETIME", title="\u65f6\u95f4\u6233", dbType=DBColumn.DBType.DateTime)
    private Date createTime;
    @DBColumn(nameInDB="SRCTYPE", title="\u6570\u636e\u6765\u6e90\u7c7b\u578b", dbType=DBColumn.DBType.Int)
    private Integer srcType;
    @DBColumn(nameInDB="OFFSETSTATE", title="\u6838\u5bf9\u72b6\u6001", dbType=DBColumn.DBType.Varchar, length=2, refTabField="MD_REPORTOFFSETSTATE@BASE")
    private String offsetState;
    @DBColumn(nameInDB="OFFSETTIME", title="\u6838\u5bf9\u65e5\u671f", dbType=DBColumn.DBType.DateTime)
    private Date offsetTime;
    @DBColumn(nameInDB="OFFSETPERSON", title="\u62b5\u9500\u4eba", dbType=DBColumn.DBType.NVarchar, length=100)
    private String offsetPerson;
    @DBColumn(nameInDB="OFFSETGROUPID", title="\u62b5\u9500\u5206\u7ec4ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String offsetGroupId;
    @DBColumn(nameInDB="UNIONRULEID", title="\u5408\u5e76\u89c4\u5219ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String unionRuleId;
    @DBColumn(nameInDB="TASKID", title="\u4efb\u52a1ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String taskId;
    @DBColumn(nameInDB="FORMID", title="\u62a5\u8868\u8868\u5355ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String formId;
    @DBColumn(nameInDB="REPORTSYSTEMID", title="\u4f53\u7cfbID", dbType=DBColumn.DBType.Varchar, length=36)
    private String reportSystemId;
    @DBColumn(nameInDB="AMT", title="\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2, isRequired=true)
    private Double amt;
    @DBColumn(nameInDB="OFFSETAMT", title="\u5df2\u62b5\u9500\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    private Double offsetAmt;
    @DBColumn(nameInDB="DIFFAMT", title="\u5dee\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    private Double diffAmt;
    @DBColumn(nameInDB="UPDATETIME", title="\u66f4\u65b0\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    private Date updateTime;
    @DBColumn(nameInDB="RECORDTIMESTAMP", title="\u65f6\u95f4\u6233", dbType=DBColumn.DBType.Numeric, precision=19, scale=0)
    private Long recordTimestamp;
    @DBColumn(nameInDB="EFDCMOREFILTERFML", title="efdc\u7a7f\u900f\u8ffd\u52a0\u7b5b\u9009\u516c\u5f0f", dbType=DBColumn.DBType.NVarchar, length=1000)
    private String efdcMoreFilterFml;
    @DBColumn(nameInDB="CONVERTSRCID", title="\u6298\u7b97\u6765\u6e90ID", dbType=DBColumn.DBType.NVarchar, length=36)
    private String convertSrcId;
    @DBColumn(nameInDB="CONVERTGROUPID", title="\u6298\u7b97\u5206\u7ec4ID", dbType=DBColumn.DBType.NVarchar, length=36)
    private String convertGroupId;
    @DBColumn(nameInDB="CHECKGROUPID", title="\u5bf9\u8d26\u5206\u7ec4id", dbType=DBColumn.DBType.NVarchar, length=36)
    private String checkGroupId;
    @DBColumn(nameInDB="CHECKSTATE", title="\u5bf9\u8d26\u72b6\u6001", dbType=DBColumn.DBType.NVarchar, length=2, refTabField="MD_INPUTDATACHECKSTATE@BASE")
    private String checkState;
    @DBColumn(nameInDB="CHECKAMT", title="\u5bf9\u8d26\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    private Double checkAmt;
    @DBColumn(nameInDB="UNCHECKAMT", title="\u672a\u5bf9\u8d26\u91d1\u989d", dbType=DBColumn.DBType.Numeric, precision=19, scale=2)
    private Double unCheckAmt;
    @DBColumn(nameInDB="CHECKTYPE", title="\u5bf9\u8d26\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, length=2)
    private String checkType;
    @DBColumn(nameInDB="CHECKTIME", title="\u5bf9\u8d26\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    private Date checkTime;
    @DBColumn(nameInDB="CHECKUSER", title="\u5bf9\u8d26\u4eba", dbType=DBColumn.DBType.NVarchar, length=100)
    private String checkUser;
    private Boolean convertOffsetOrgFlag = false;
    private String offsetOrgCode;
    private String offsetOppUnitId;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMdOrg() {
        return this.mdOrg;
    }

    public void setMdOrg(String mdOrg) {
        this.addFieldValue("MDCODE", mdOrg);
        this.mdOrg = mdOrg;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.addFieldValue(FIELDNAME_ORGCODE, orgCode);
        this.orgCode = orgCode;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.addFieldValue("MD_CURRENCY", currency);
        this.currency = currency;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.addFieldValue("MD_GCORGTYPE", orgType);
        this.orgType = orgType;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.addFieldValue("DATATIME", period);
        this.period = period;
    }

    public String getBizkeyOrder() {
        return this.bizkeyOrder;
    }

    public void setBizkeyOrder(String bizkeyOrder) {
        this.bizkeyOrder = bizkeyOrder;
    }

    public Double getFloatOrder() {
        return this.floatOrder;
    }

    public void setFloatOrder(Double floatOrder) {
        this.floatOrder = floatOrder;
    }

    public String getUnitId() {
        return String.valueOf(this.getFieldValue("MDCODE"));
    }

    public String getOppUnitId() {
        return this.oppUnitId;
    }

    public void setOppUnitId(String oppUnitId) {
        this.addFieldValue(FIELDNAME_OPPUNIT, oppUnitId);
        this.oppUnitId = oppUnitId;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.addFieldValue(FIELDNAME_SUBJECTCODE, subjectCode);
        this.subjectCode = subjectCode;
    }

    public String getSubjectObjCode() {
        return this.subjectObjCode;
    }

    public void setSubjectObjCode(String subjectObjCode) {
        this.addFieldValue("SUBJECTOBJCODE", subjectObjCode);
        this.subjectObjCode = subjectObjCode;
    }

    public Integer getDc() {
        return this.dc;
    }

    public void setDc(Integer dc) {
        this.addFieldValue(FIELDNAME_DC, dc);
        this.dc = dc;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSrcType() {
        return this.srcType;
    }

    public void setSrcType(Integer srcType) {
        this.srcType = srcType;
    }

    public String getOffsetState() {
        return this.offsetState;
    }

    public void setOffsetState(String offsetState) {
        this.offsetState = offsetState;
    }

    public Date getOffsetTime() {
        return this.offsetTime;
    }

    public void setOffsetTime(Date offsetTime) {
        this.offsetTime = offsetTime;
    }

    public String getOffsetPerson() {
        return this.offsetPerson;
    }

    public void setOffsetPerson(String offsetPerson) {
        this.offsetPerson = offsetPerson;
    }

    public String getOffsetGroupId() {
        return this.offsetGroupId;
    }

    public void setOffsetGroupId(String offsetGroupId) {
        this.offsetGroupId = offsetGroupId;
    }

    public String getUnionRuleId() {
        return this.unionRuleId;
    }

    public void setUnionRuleId(String unionRuleId) {
        this.unionRuleId = unionRuleId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getReportSystemId() {
        return this.reportSystemId;
    }

    public void setReportSystemId(String reportSystemId) {
        this.reportSystemId = reportSystemId;
    }

    public Double getAmt() {
        return this.amt;
    }

    public void setAmt(Double amt) {
        this.addFieldValue("AMT", amt);
        this.amt = amt;
    }

    public Double getOffsetAmt() {
        return this.offsetAmt;
    }

    public void setOffsetAmt(Double offsetAmt) {
        this.addFieldValue("OFFSETAMT", offsetAmt);
        this.offsetAmt = offsetAmt;
    }

    public Double getDiffAmt() {
        return this.diffAmt;
    }

    public void setDiffAmt(Double diffAmt) {
        this.diffAmt = diffAmt;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getRecordTimestamp() {
        return this.recordTimestamp;
    }

    public void setRecordTimestamp(Long recordTimestamp) {
        this.recordTimestamp = recordTimestamp;
    }

    public String getCheckGroupId() {
        return this.checkGroupId;
    }

    public void setCheckGroupId(String checkGroupId) {
        this.checkGroupId = checkGroupId;
    }

    public String getCheckState() {
        return this.checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public Double getCheckAmt() {
        return this.checkAmt;
    }

    public void setCheckAmt(Double checkAmt) {
        this.checkAmt = checkAmt;
    }

    public Double getUnCheckAmt() {
        return this.unCheckAmt;
    }

    public void setUnCheckAmt(Double unCheckAmt) {
        this.unCheckAmt = unCheckAmt;
    }

    public String getCheckType() {
        return this.checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public Date getCheckTime() {
        return this.checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckUser() {
        return this.checkUser;
    }

    public void setCheckUser(String checkUser) {
        this.checkUser = checkUser;
    }

    public String getEfdcMoreFilterFml() {
        return this.efdcMoreFilterFml;
    }

    public void setEfdcMoreFilterFml(String efdcMoreFilterFml) {
        this.efdcMoreFilterFml = efdcMoreFilterFml;
    }

    public String getConvertSrcId() {
        return this.convertSrcId;
    }

    public void setConvertSrcId(String convertSrcId) {
        this.addFieldValue("CONVERTSRCID", convertSrcId);
        this.convertSrcId = convertSrcId;
    }

    public String getConvertGroupId() {
        return this.convertGroupId;
    }

    public void setConvertGroupId(String convertGroupId) {
        this.addFieldValue("CONVERTGROUPID", convertGroupId);
        this.convertGroupId = convertGroupId;
    }

    public String getOffsetOrgCode() {
        return this.offsetOrgCode;
    }

    public void setOffsetOrgCode(String offsetOrgCode) {
        this.offsetOrgCode = offsetOrgCode;
    }

    public String getOffsetOppUnitId() {
        return this.offsetOppUnitId;
    }

    public void setOffsetOppUnitId(String offsetOppUnitId) {
        this.offsetOppUnitId = offsetOppUnitId;
    }

    public Boolean getConvertOffsetOrgFlag() {
        return this.convertOffsetOrgFlag;
    }

    public void setConvertOffsetOrgFlag(Boolean convertOffsetOrgFlag) {
        this.convertOffsetOrgFlag = convertOffsetOrgFlag;
    }

    public String toString() {
        return "InputDataEO{id='" + this.id + '\'' + ", mdOrg='" + this.mdOrg + '\'' + ", orgCode='" + this.orgCode + '\'' + ", currency='" + this.currency + '\'' + ", orgType='" + this.orgType + '\'' + ", period='" + this.period + '\'' + ", bizkeyOrder='" + this.bizkeyOrder + '\'' + ", floatOrder=" + this.floatOrder + ", oppUnitId='" + this.oppUnitId + '\'' + ", subjectCode='" + this.subjectCode + '\'' + ", subjectObjCode='" + this.subjectObjCode + '\'' + ", dc=" + this.dc + ", memo='" + this.memo + '\'' + ", createUser='" + this.createUser + '\'' + ", createTime=" + this.createTime + ", srcType=" + this.srcType + ", offsetState='" + this.offsetState + '\'' + ", offsetTime=" + this.offsetTime + ", offsetPerson='" + this.offsetPerson + '\'' + ", offsetGroupId='" + this.offsetGroupId + '\'' + ", unionRuleId='" + this.unionRuleId + '\'' + ", taskId='" + this.taskId + '\'' + ", formId='" + this.formId + '\'' + ", reportSystemId='" + this.reportSystemId + '\'' + ", amt=" + this.amt + ", offsetAmt=" + this.offsetAmt + ", diffAmt=" + this.diffAmt + ", updateTime=" + this.updateTime + ", recordTimestamp=" + this.recordTimestamp + ", efdcMoreFilterFml='" + this.efdcMoreFilterFml + '\'' + ", convertSrcId='" + this.convertSrcId + '\'' + ", convertGroupId='" + this.convertGroupId + '\'' + ", groupCheckId='" + this.checkGroupId + '\'' + ", checkState='" + this.checkState + '\'' + ", checkAmt=" + this.checkAmt + ", unCheckAmt=" + this.unCheckAmt + ", checkType='" + this.checkType + '\'' + ", checkTime=" + this.checkTime + ", checkUser=" + this.checkUser + '}';
    }
}

