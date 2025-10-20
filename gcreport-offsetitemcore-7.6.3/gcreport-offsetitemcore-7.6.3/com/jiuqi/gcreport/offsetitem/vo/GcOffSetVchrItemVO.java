/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.offsetitem.vo;

import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotNull;

public class GcOffSetVchrItemVO {
    private String id;
    private Long recver;
    private String mRecid;
    private String taskId;
    private String defaultPeriod;
    private String schemeId;
    private String systemId;
    private String gcBusinessTypeCode;
    private int affectStrategy;
    @NotNull
    private Integer acctYear;
    @NotNull
    private Integer acctPeriod;
    @NotNull
    private String unitId;
    private GcOrgCacheVO unitVo;
    private String unitCode;
    private String unitName;
    @NotNull
    private String oppUnitId;
    private GcOrgCacheVO oppUnitVo;
    private String oppUnitCode;
    private String oppUnitName;
    @NotNull
    private GcBaseDataVO subjectVo;
    private String subjectCode;
    private String subjectName;
    private String digest;
    private String memo;
    private Date modifyTime;
    private Double sortOrder;
    private Boolean postFlag = Boolean.FALSE;
    private Double debit;
    private Double credit;
    @NotNull
    private String offSetCurr;
    private String offSetCurrName;
    private Double bfOffSetDebit;
    private Double bfOffSetCredit;
    private Double offSetDebit;
    private Double offSetCredit;
    private Double diffd;
    private Double diffc;
    private String inputUnitId;
    private String inputUnitName;
    private String unitVersion;
    private String orgType;
    private int elmMode;
    private String ruleId;
    private String ruleTitle;
    private String ruleParentId;
    private String gcNumber;
    private Date createTime;
    private int offSetSrcType;
    private String offSetSrcTypeName;
    private String srcOffsetGroupId;
    private String srcId;
    private BaseDataVO areaVo;
    private BaseDataVO ywbkVo;
    private BaseDataVO gcywlxVo;
    private BaseDataVO tzyzmsVo;
    private Map<String, Object> unSysFields = new HashMap<String, Object>();
    private String projectTitle;
    private String assetTitle;
    private String effectType;
    private String elmModeTitle;
    private String gcBusinessType;
    private Boolean disableFlag;
    private Boolean relDiffInitDataFlag = true;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getRecver() {
        return this.recver;
    }

    public void setRecver(Long recver) {
        this.recver = recver;
    }

    public String getmRecid() {
        return this.mRecid;
    }

    public void setmRecid(String mRecid) {
        this.mRecid = mRecid;
    }

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

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
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

    public String getGcBusinessTypeCode() {
        return this.gcBusinessTypeCode;
    }

    public void setGcBusinessTypeCode(String gcBusinessTypeCode) {
        this.gcBusinessTypeCode = gcBusinessTypeCode;
    }

    public int getAffectStrategy() {
        return this.affectStrategy;
    }

    public void setAffectStrategy(int affectStrategy) {
        this.affectStrategy = affectStrategy;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public Integer getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(Integer acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public GcOrgCacheVO getUnitVo() {
        return this.unitVo;
    }

    public void setUnitVo(GcOrgCacheVO unitVo) {
        this.unitVo = unitVo;
    }

    public GcOrgCacheVO getOppUnitVo() {
        return this.oppUnitVo;
    }

    public void setOppUnitVo(GcOrgCacheVO oppUnitVo) {
        this.oppUnitVo = oppUnitVo;
    }

    public String getOppUnitId() {
        return this.oppUnitId;
    }

    public void setOppUnitId(String oppUnitId) {
        this.oppUnitId = oppUnitId;
    }

    public String getOppUnitName() {
        return this.oppUnitName;
    }

    public void setOppUnitName(String oppUnitName) {
        this.oppUnitName = oppUnitName;
    }

    public GcBaseDataVO getSubjectVo() {
        return this.subjectVo;
    }

    public void setSubjectVo(GcBaseDataVO subjectVo) {
        this.subjectVo = subjectVo;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return this.subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getDigest() {
        return this.digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
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

    public Boolean getPostFlag() {
        return this.postFlag;
    }

    public void setPostFlag(Boolean postFlag) {
        this.postFlag = postFlag;
    }

    public Double getDebit() {
        return this.debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public Double getCredit() {
        return this.credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public String getOffSetCurr() {
        return this.offSetCurr;
    }

    public void setOffSetCurr(String offSetCurr) {
        this.offSetCurr = offSetCurr;
    }

    public String getOffSetCurrName() {
        return this.offSetCurrName;
    }

    public void setOffSetCurrName(String offSetCurrName) {
        this.offSetCurrName = offSetCurrName;
    }

    public Double getBfOffSetDebit() {
        return this.bfOffSetDebit;
    }

    public void setBfOffSetDebit(Double bfOffSetDebit) {
        this.bfOffSetDebit = bfOffSetDebit;
    }

    public Double getBfOffSetCredit() {
        return this.bfOffSetCredit;
    }

    public void setBfOffSetCredit(Double bfOffSetCredit) {
        this.bfOffSetCredit = bfOffSetCredit;
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

    public String getInputUnitId() {
        return this.inputUnitId;
    }

    public void setInputUnitId(String inputUnitId) {
        this.inputUnitId = inputUnitId;
    }

    public String getInputUnitName() {
        return this.inputUnitName;
    }

    public void setInputUnitName(String inputUnitName) {
        this.inputUnitName = inputUnitName;
    }

    public String getUnitVersion() {
        return this.unitVersion;
    }

    public void setUnitVersion(String unitVersion) {
        this.unitVersion = unitVersion;
    }

    public int getElmMode() {
        return this.elmMode;
    }

    public void setElmMode(int elmMode) {
        this.elmMode = elmMode;
    }

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleTitle() {
        return this.ruleTitle;
    }

    public void setRuleTitle(String ruleTitle) {
        this.ruleTitle = ruleTitle;
    }

    public String getRuleParentId() {
        return this.ruleParentId;
    }

    public void setRuleParentId(String ruleParentId) {
        this.ruleParentId = ruleParentId;
    }

    public String getGcNumber() {
        return this.gcNumber;
    }

    public void setGcNumber(String gcNumber) {
        this.gcNumber = gcNumber;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getOffSetSrcType() {
        return this.offSetSrcType;
    }

    public void setOffSetSrcType(int offSetSrcType) {
        this.offSetSrcType = offSetSrcType;
    }

    public String getOffSetSrcTypeName() {
        return this.offSetSrcTypeName;
    }

    public void setOffSetSrcTypeName(String offSetSrcTypeName) {
        this.offSetSrcTypeName = offSetSrcTypeName;
    }

    public String getSrcOffsetGroupId() {
        return this.srcOffsetGroupId;
    }

    public void setSrcOffsetGroupId(String srcOffsetGroupId) {
        this.srcOffsetGroupId = srcOffsetGroupId;
    }

    public String getDefaultPeriod() {
        return this.defaultPeriod;
    }

    public void setDefaultPeriod(String defaultPeriod) {
        this.defaultPeriod = defaultPeriod;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public BaseDataVO getAreaVo() {
        return this.areaVo;
    }

    public void setAreaVo(BaseDataVO areaVo) {
        this.areaVo = areaVo;
    }

    public BaseDataVO getYwbkVo() {
        return this.ywbkVo;
    }

    public void setYwbkVo(BaseDataVO ywbkVo) {
        this.ywbkVo = ywbkVo;
    }

    public BaseDataVO getGcywlxVo() {
        return this.gcywlxVo;
    }

    public void setGcywlxVo(BaseDataVO gcywlxVo) {
        this.gcywlxVo = gcywlxVo;
    }

    public BaseDataVO getTzyzmsVo() {
        return this.tzyzmsVo;
    }

    public void setTzyzmsVo(BaseDataVO tzyzmsVo) {
        this.tzyzmsVo = tzyzmsVo;
    }

    public String getProjectTitle() {
        return this.projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getAssetTitle() {
        return this.assetTitle;
    }

    public void setAssetTitle(String assetTitle) {
        this.assetTitle = assetTitle;
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

    public String getElmModeTitle() {
        return this.elmModeTitle;
    }

    public void setElmModeTitle(String elmModeTitle) {
        this.elmModeTitle = elmModeTitle;
    }

    public String getGcBusinessType() {
        return this.gcBusinessType;
    }

    public void setGcBusinessType(String gcBusinessType) {
        this.gcBusinessType = gcBusinessType;
    }

    public Boolean getDisableFlag() {
        return this.disableFlag;
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
}

