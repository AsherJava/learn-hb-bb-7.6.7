/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.NumberUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator
 *  com.jiuqi.gcreport.offsetitem.dto.PentrationDataJsonSerializer
 *  com.jiuqi.gcreport.offsetitem.enums.AffectBalanceStrategyEnum
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.unionrule.enums.FetchRangeEnum
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.workingpaper.querytask.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.NumberUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.AbstractFieldDynamicDeclarator;
import com.jiuqi.gcreport.offsetitem.dto.PentrationDataJsonSerializer;
import com.jiuqi.gcreport.offsetitem.enums.AffectBalanceStrategyEnum;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.unionrule.enums.FetchRangeEnum;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotNull;
import org.springframework.util.StringUtils;

public class ArbitrarilyMergeOffSetVchrItemDTO
extends AbstractFieldDynamicDeclarator {
    private String id;
    private Long recver;
    private String mRecid;
    private String taskId;
    private String gcBusinessTypeCode;
    private String defaultPeriod;
    private String schemeId;
    private String formId;
    private String fetchSetGroupId;
    private String srcOffsetGroupId;
    private String originSrcOffsetGroupId;
    private String systemId;
    @NotNull
    private Integer acctYear;
    @NotNull
    private Integer acctPeriod;
    @NotNull
    private String unitId;
    @NotNull
    private String oppUnitId;
    @NotNull
    private String subjectCode;
    private String origSubjectCode;
    private String srcSubjectCode;
    private OrientEnum subjectOrient;
    private OrientEnum orient;
    private AffectBalanceStrategyEnum affectStrategy = AffectBalanceStrategyEnum.DEFAUT_AFFECT_DETAIL;
    private String digest;
    private String memo;
    private Date modifyTime;
    private Double sortOrder;
    private Boolean postFlag = Boolean.FALSE;
    private Double debit;
    private Double credit;
    @NotNull
    private String offSetCurr;
    private Double bfOffSetDebit;
    private Double bfOffSetCredit;
    private Double offSetDebit;
    private Double offSetCredit;
    private Double diffd;
    private Double diffc;
    private String inputUnitId;
    private String unitVersion;
    private String orgType;
    private String adjustType;
    private Integer elmMode;
    private String selectAdjustCode;
    private String ruleId;
    private String gcNumber;
    private Date createTime;
    private OffSetSrcTypeEnum offSetSrcType = OffSetSrcTypeEnum.RYHB_MODIFIED_INPUT;
    private String srcId;
    private String originSrcId;
    private String areaCode;
    private String ywbkCode;
    private String gcywlxCode;
    private String tzyzmsCode;
    private String projectTitle;
    private String assetTitle;
    private Map<String, Object> unSysFields = new HashMap<String, Object>();
    private FetchRangeEnum fetchRange;
    private String effectType;
    @JsonSerialize(using=PentrationDataJsonSerializer.class)
    private Map<String, String> otherColumnsValueMap;
    private Boolean disableFlag;
    private Boolean relDiffInitDataFlag = true;
    private String createUser;

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.addFieldValue("ID", id);
        this.id = id;
    }

    public Long getRecver() {
        return this.recver;
    }

    public void setRecver(Long recver) {
        this.addFieldValue("RECVER", recver);
        this.recver = recver;
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

    public String getGcBusinessTypeCode() {
        return this.gcBusinessTypeCode;
    }

    public void setGcBusinessTypeCode(String gcBusinessTypeCode) {
        this.addFieldValue("GCBUSINESSTYPECODE", gcBusinessTypeCode);
        this.gcBusinessTypeCode = gcBusinessTypeCode;
    }

    public String getDefaultPeriod() {
        return this.defaultPeriod;
    }

    public void setDefaultPeriod(String defaultPeriod) {
        this.addFieldValue("DEFAULTPERIOD", defaultPeriod);
        this.defaultPeriod = defaultPeriod;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.addFieldValue("SCHEMEID", schemeId);
        this.schemeId = schemeId;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.addFieldValue("FORMID", formId);
        this.formId = formId;
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
        this.addFieldValue("SRCOFFSETGROUPID", srcOffsetGroupId);
        this.srcOffsetGroupId = srcOffsetGroupId;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.addFieldValue("SYSTEMID", systemId);
        this.systemId = systemId;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.addFieldValue("ACCTYEAR", acctYear);
        this.acctYear = acctYear;
    }

    public Integer getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(Integer acctPeriod) {
        this.addFieldValue("ACCTPERIOD", acctPeriod);
        this.acctPeriod = acctPeriod;
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

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.addFieldValue("SUBJECTCODE", subjectCode);
        this.subjectCode = subjectCode;
    }

    public OrientEnum getSubjectOrient() {
        return this.subjectOrient;
    }

    public void setSubjectOrient(OrientEnum subjectOrient) {
        this.addFieldValue("SUBJECTORIENT", subjectOrient);
        this.subjectOrient = subjectOrient;
    }

    public String getOrigSubjectCode() {
        return this.origSubjectCode;
    }

    public void setOrigSubjectCode(String origSubjectCode) {
        this.addFieldValue("ORIGSUBJECTCODE", this.subjectCode);
        this.origSubjectCode = origSubjectCode;
    }

    public String getSrcSubjectCode() {
        return this.srcSubjectCode;
    }

    public void setSrcSubjectCode(String srcSubjectCode) {
        this.addFieldValue("SRCSUBJECTCODE", this.subjectCode);
        this.srcSubjectCode = srcSubjectCode;
    }

    public AffectBalanceStrategyEnum getAffectStrategy() {
        return this.affectStrategy;
    }

    public void setAffectStrategy(AffectBalanceStrategyEnum affectStrategy) {
        this.addFieldValue("AFFECTSTRATEGY", affectStrategy);
        this.affectStrategy = affectStrategy;
    }

    public String getDigest() {
        return this.digest;
    }

    public void setDigest(String digest) {
        this.addFieldValue("DIGEST", digest);
        this.digest = digest;
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

    public Double getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Double sortOrder) {
        this.addFieldValue("SORTORDER", sortOrder);
        this.sortOrder = sortOrder;
    }

    public Boolean getPostFlag() {
        return this.postFlag;
    }

    public void setPostFlag(Boolean postFlag) {
        this.addFieldValue("POSTFLAG", postFlag);
        this.postFlag = postFlag;
    }

    public Double getDebit() {
        return this.debit;
    }

    public void setDebit(Double debit) {
        this.addFieldValue("DEBIT", debit);
        this.debit = debit;
    }

    public Double getCredit() {
        return this.credit;
    }

    public void setCredit(Double credit) {
        this.addFieldValue("CREDIT", credit);
        this.credit = credit;
    }

    public String getOffSetCurr() {
        return this.offSetCurr;
    }

    public void setOffSetCurr(String offSetCurr) {
        this.addFieldValue("OFFSETCURR", offSetCurr);
        this.offSetCurr = offSetCurr;
    }

    public Double getBfOffSetDebit() {
        return this.bfOffSetDebit;
    }

    public void setBfOffSetDebit(Double bfOffSetDebit) {
        this.addFieldValue("BFOFFSETDEBIT", bfOffSetDebit);
        this.bfOffSetDebit = bfOffSetDebit;
    }

    public Double getBfOffSetCredit() {
        return this.bfOffSetCredit;
    }

    public void setBfOffSetCredit(Double bfOffSetCredit) {
        this.addFieldValue("BFOFFSETCREDIT", bfOffSetCredit);
        this.bfOffSetCredit = bfOffSetCredit;
    }

    public Double getOffSetDebit() {
        return this.offSetDebit;
    }

    public void setOffSetDebit(Double offSetDebit) {
        this.addFieldValue("OFFSETDEBIT", offSetDebit);
        this.offSetDebit = offSetDebit;
        if (!NumberUtils.isZreo((Double)offSetDebit) || null == this.orient) {
            this.setOrient(OrientEnum.D);
        }
    }

    public Double getOffSetCredit() {
        return this.offSetCredit;
    }

    public void setOffSetCredit(Double offSetCredit) {
        this.addFieldValue("OFFSETCREDIT", offSetCredit);
        this.offSetCredit = offSetCredit;
        if (!NumberUtils.isZreo((Double)offSetCredit) || null == this.orient) {
            this.setOrient(OrientEnum.C);
        }
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

    public String getInputUnitId() {
        return this.inputUnitId;
    }

    public void setInputUnitId(String inputUnitId) {
        this.addFieldValue("INPUTUNITID", inputUnitId);
        this.inputUnitId = inputUnitId;
    }

    public String getUnitVersion() {
        return this.unitVersion;
    }

    public void setUnitVersion(String unitVersion) {
        this.addFieldValue("UNITVERSION", unitVersion);
        this.unitVersion = unitVersion;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.addFieldValue("ORGTYPE", orgType);
        this.orgType = orgType;
    }

    public String getAdjustType() {
        return this.adjustType;
    }

    public void setAdjustType(String adjustType) {
        this.addFieldValue("ADJUSTTYPE", adjustType);
        this.adjustType = adjustType;
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

    public String getGcNumber() {
        return this.gcNumber;
    }

    public void setGcNumber(String gcNumber) {
        this.addFieldValue("GCNUMBER", gcNumber);
        this.gcNumber = gcNumber;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.addFieldValue("CREATETIME", createTime);
        this.createTime = createTime;
    }

    public OffSetSrcTypeEnum getOffSetSrcType() {
        return this.offSetSrcType;
    }

    public void setOffSetSrcType(OffSetSrcTypeEnum offSetSrcType) {
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

    public String getAreaCode() {
        return this.areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.addFieldValue("AREACODE", areaCode);
        this.areaCode = areaCode;
    }

    public String getYwbkCode() {
        return this.ywbkCode;
    }

    public void setYwbkCode(String ywbkCode) {
        this.addFieldValue("YWBKCODE", ywbkCode);
        this.ywbkCode = ywbkCode;
    }

    public String getGcywlxCode() {
        return this.gcywlxCode;
    }

    public void setGcywlxCode(String gcywlxCode) {
        this.addFieldValue("GCYWLXCODE", gcywlxCode);
        this.gcywlxCode = gcywlxCode;
    }

    public String getTzyzmsCode() {
        return this.tzyzmsCode;
    }

    public void setTzyzmsCode(String tzyzmsCode) {
        this.addFieldValue("TZYZMSCODE", tzyzmsCode);
        this.tzyzmsCode = tzyzmsCode;
    }

    public String getProjectTitle() {
        return this.projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.addFieldValue("PROJECTTITLE", projectTitle);
        this.projectTitle = projectTitle;
    }

    public FetchRangeEnum getFetchRange() {
        return this.fetchRange;
    }

    public void setFetchRange(FetchRangeEnum fetchRange) {
        this.fetchRange = fetchRange;
    }

    public OrientEnum getOrient() {
        return this.orient;
    }

    public void setOrient(OrientEnum orient) {
        this.orient = orient;
    }

    public String getAssetTitle() {
        return this.assetTitle;
    }

    public void setAssetTitle(String assetTitle) {
        this.addFieldValue("ASSETTITLE", assetTitle);
        this.assetTitle = assetTitle;
    }

    public String getOriginSrcOffsetGroupId() {
        return this.originSrcOffsetGroupId;
    }

    public void setOriginSrcOffsetGroupId(String originSrcOffsetGroupId) {
        this.originSrcOffsetGroupId = originSrcOffsetGroupId;
    }

    public String getOriginSrcId() {
        return this.originSrcId;
    }

    public void setOriginSrcId(String originSrcId) {
        this.originSrcId = originSrcId;
    }

    public String check() {
        if (null == this.getTaskId()) {
            return "\u4efb\u52a1\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002";
        }
        if (null == this.getAcctYear() || null == this.getAcctPeriod() || null == this.getDefaultPeriod()) {
            return "\u65f6\u671f\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002";
        }
        if (StringUtils.isEmpty(this.getOffSetCurr())) {
            return "\u5e01\u522b\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002";
        }
        if (this.getOffSetCurr().length() > 10) {
            return "\u5e01\u522b\u975e\u6cd5\u3002";
        }
        if (StringUtils.isEmpty(this.getSubjectCode())) {
            return "\u79d1\u76ee\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002";
        }
        if (null == this.getOrient()) {
            return "\u6570\u636e\u501f\u8d37\u65b9\u5411\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002";
        }
        return null;
    }

    public void setUnSysFields(Map<String, Object> unSysFields) {
        this.unSysFields = unSysFields;
    }

    public Map<String, Object> getUnSysFields() {
        return this.unSysFields;
    }

    public void addUnSysFieldValue(String field, Object value) {
        if (this.unSysFields == null) {
            this.unSysFields = new HashMap<String, Object>();
        }
        this.unSysFields.put(field, value);
    }

    public Object getUnSysFieldValue(String field) {
        if (this.unSysFields == null) {
            return null;
        }
        return this.unSysFields.get(field);
    }

    public String getEffectType() {
        return this.effectType;
    }

    public void setEffectType(String effectType) {
        this.effectType = effectType;
    }

    public Map<String, String> getOtherColumnsValueMap() {
        return this.otherColumnsValueMap;
    }

    public void setOtherColumnsValueMap(Map<String, String> otherColumnsValueMap) {
        this.otherColumnsValueMap = otherColumnsValueMap;
    }

    public Boolean getDisableFlag() {
        return this.disableFlag == null ? false : this.disableFlag;
    }

    public void setDisableFlag(Boolean disableFlag) {
        this.disableFlag = disableFlag;
    }

    public Boolean getRelDiffInitDataFlag() {
        return this.relDiffInitDataFlag;
    }

    public void setRelDiffInitDataFlag(Boolean relDiffInitDataFlag) {
        this.relDiffInitDataFlag = relDiffInitDataFlag;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String toString() {
        return "GcOffSetVchrItemDTO{id='" + this.id + '\'' + ", recver=" + this.recver + ", mRecid='" + this.mRecid + '\'' + ", taskId='" + this.taskId + '\'' + ", gcBusinessTypeCode='" + this.gcBusinessTypeCode + '\'' + ", defaultPeriod='" + this.defaultPeriod + '\'' + ", schemeId='" + this.schemeId + '\'' + ", formId='" + this.formId + '\'' + ", fetchSetGroupId='" + this.fetchSetGroupId + '\'' + ", srcOffsetGroupId='" + this.srcOffsetGroupId + '\'' + ", originSrcOffsetGroupId='" + this.originSrcOffsetGroupId + '\'' + ", systemId='" + this.systemId + '\'' + ", acctYear=" + this.acctYear + ", acctPeriod=" + this.acctPeriod + ", unitId='" + this.unitId + '\'' + ", oppUnitId='" + this.oppUnitId + '\'' + ", subjectCode='" + this.subjectCode + '\'' + ", origSubjectCode='" + this.origSubjectCode + '\'' + ", srcSubjectCode='" + this.srcSubjectCode + '\'' + ", subjectOrient=" + this.subjectOrient + ", orient=" + this.orient + ", affectStrategy=" + this.affectStrategy + ", digest='" + this.digest + '\'' + ", memo='" + this.memo + '\'' + ", modifyTime=" + this.modifyTime + ", sortOrder=" + this.sortOrder + ", postFlag=" + this.postFlag + ", debit=" + this.debit + ", credit=" + this.credit + ", offSetCurr='" + this.offSetCurr + '\'' + ", bfOffSetDebit=" + this.bfOffSetDebit + ", bfOffSetCredit=" + this.bfOffSetCredit + ", offSetDebit=" + this.offSetDebit + ", offSetCredit=" + this.offSetCredit + ", diffd=" + this.diffd + ", diffc=" + this.diffc + ", inputUnitId='" + this.inputUnitId + '\'' + ", unitVersion='" + this.unitVersion + '\'' + ", orgType='" + this.orgType + '\'' + ", adjustType='" + this.adjustType + '\'' + ", elmMode=" + this.elmMode + ", ruleId='" + this.ruleId + '\'' + ", gcNumber='" + this.gcNumber + '\'' + ", createTime=" + this.createTime + ", offSetSrcType=" + this.offSetSrcType + ", srcId='" + this.srcId + '\'' + ", originSrcId='" + this.originSrcId + '\'' + ", areaCode='" + this.areaCode + '\'' + ", ywbkCode='" + this.ywbkCode + '\'' + ", gcywlxCode='" + this.gcywlxCode + '\'' + ", tzyzmsCode='" + this.tzyzmsCode + '\'' + ", projectTitle='" + this.projectTitle + '\'' + ", assetTitle='" + this.assetTitle + '\'' + ", unSysFields=" + this.unSysFields + ", fetchRange=" + this.fetchRange + ", effectType='" + this.effectType + '\'' + ", otherColumnsValueMap=" + this.otherColumnsValueMap + '}';
    }
}

