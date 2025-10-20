/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.bde.common.dto.BizModelExtFieldInfo
 *  com.jiuqi.bde.common.dto.DimensionValue
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.intf.ConditionMatchRule
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.bde.bizmodel.execute.intf;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.common.dto.BizModelExtFieldInfo;
import com.jiuqi.bde.common.dto.DimensionValue;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.intf.ConditionMatchRule;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class BalanceCondition {
    private String requestTaskId;
    private String callBackIp;
    private String bblX;
    private String rpUnitType;
    private String computationModel;
    private String unitCode;
    private Integer acctYear;
    private Integer startPeriod;
    private Integer endPeriod;
    private Date startDate;
    private Date endDate;
    private List<Dimension> assTypeList = CollectionUtils.newArrayList();
    private Map<String, String> assTypeMap;
    private Boolean includeUncharged;
    private Boolean includeAdjustVchr;
    private String startAdjustPeriod;
    private String endAdjustPeriod;
    @JsonIgnore
    private String bizCombId;
    private OrgMappingDTO orgMapping;
    private Map<String, String> otherEntity;
    private Map<String, DimensionValue> dimensionValueMap;
    private Map<String, String> extParam;
    private ConditionMatchRule conditionMatchRule;
    private boolean enableDirectFilter;
    private boolean enableTransTable;
    private BizModelExtFieldInfo bizModelExtFieldInfo;

    public BalanceCondition() {
    }

    public BalanceCondition(String taskId, String unitCode, Date startDate, Date endDate, OrgMappingDTO orgMapping, Boolean includeUncharged) {
        this(taskId, unitCode, startDate, endDate, null, orgMapping, includeUncharged);
    }

    public BalanceCondition(String taskId, String unitCode, Date startDate, Date endDate, List<Dimension> assTypeList, OrgMappingDTO orgMapping, Boolean includeUncharged) {
        this.requestTaskId = taskId;
        this.unitCode = unitCode;
        this.acctYear = DateUtils.getYearOfDate((Date)endDate);
        this.startPeriod = DateUtils.getDateFieldValue((Date)startDate, (int)2);
        this.endPeriod = DateUtils.getDateFieldValue((Date)endDate, (int)2);
        this.startDate = startDate;
        this.endDate = endDate;
        this.assTypeList = assTypeList == null ? CollectionUtils.newArrayList() : assTypeList;
        this.assTypeMap = assTypeList == null ? new HashMap<String, String>() : assTypeList.stream().collect(Collectors.toMap(Dimension::getDimCode, Dimension::getDimCode, (K1, K2) -> K1));
        this.orgMapping = orgMapping;
        this.enableTransTable = StringUtils.isEmpty((String)orgMapping.getDataSchemeCode()) ? false : ModelExecuteUtil.getEnableTransTable(orgMapping.getDataSchemeCode());
        this.includeUncharged = Boolean.TRUE.equals(includeUncharged);
    }

    public String getRequestTaskId() {
        return this.requestTaskId;
    }

    public void setRequestTaskId(String requestTaskId) {
        this.requestTaskId = requestTaskId;
    }

    public String getCallBackIp() {
        return this.callBackIp;
    }

    public void setCallBackIp(String callBackIp) {
        this.callBackIp = callBackIp;
    }

    public String getBblX() {
        return this.bblX;
    }

    public void setBblX(String bblX) {
        this.bblX = bblX;
    }

    public String getRpUnitType() {
        return this.rpUnitType;
    }

    public void setRpUnitType(String rpUnitType) {
        this.rpUnitType = rpUnitType;
    }

    public String getComputationModel() {
        return this.computationModel;
    }

    public void setComputationModel(String computationModel) {
        this.computationModel = computationModel;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public Integer getStartPeriod() {
        return this.startPeriod;
    }

    public void setStartPeriod(Integer startPeriod) {
        this.startPeriod = startPeriod;
    }

    public Integer getEndPeriod() {
        return this.endPeriod;
    }

    public void setEndPeriod(Integer endPeriod) {
        this.endPeriod = endPeriod;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Dimension> getAssTypeList() {
        return this.assTypeList;
    }

    public void setAssTypeList(List<Dimension> assTypeList) {
        this.assTypeList = assTypeList;
        this.assTypeMap = assTypeList == null ? new HashMap<String, String>() : assTypeList.stream().collect(Collectors.toMap(Dimension::getDimCode, Dimension::getDimCode, (K1, K2) -> K1));
    }

    public Map<String, String> getAssTypeMap() {
        return this.assTypeMap;
    }

    public void setAssTypeMap(Map<String, String> assTypeMap) {
        this.assTypeMap = assTypeMap;
    }

    public void setIncludeUncharged(Boolean includeUncharged) {
        this.includeUncharged = includeUncharged;
    }

    public Boolean isIncludeUncharged() {
        return this.includeUncharged;
    }

    public Boolean getIncludeUncharged() {
        return this.includeUncharged;
    }

    public String getStartAdjustPeriod() {
        return this.startAdjustPeriod;
    }

    public void setStartAdjustPeriod(String startAdjustPeriod) {
        this.startAdjustPeriod = startAdjustPeriod;
    }

    public String getEndAdjustPeriod() {
        return this.endAdjustPeriod;
    }

    public void setEndAdjustPeriod(String endAdjustPeriod) {
        this.endAdjustPeriod = endAdjustPeriod;
    }

    public OrgMappingDTO getOrgMapping() {
        return this.orgMapping;
    }

    public void setOrgMapping(OrgMappingDTO orgMapping) {
        this.orgMapping = orgMapping;
    }

    public Map<String, String> getOtherEntity() {
        return this.otherEntity;
    }

    public void setOtherEntity(Map<String, String> otherEntity) {
        this.otherEntity = otherEntity;
    }

    public Map<String, DimensionValue> getDimensionValueMap() {
        return this.dimensionValueMap;
    }

    public void setDimensionValueMap(Map<String, DimensionValue> dimensionValueMap) {
        this.dimensionValueMap = dimensionValueMap;
    }

    public Map<String, String> getExtParam() {
        return this.extParam;
    }

    public void setExtParam(Map<String, String> extParam) {
        this.extParam = extParam;
    }

    public ConditionMatchRule getConditionMatchRule() {
        return this.conditionMatchRule;
    }

    public void setConditionMatchRule(ConditionMatchRule conditionMatchRule) {
        this.conditionMatchRule = conditionMatchRule;
    }

    public boolean isEnableDirectFilter() {
        return this.enableDirectFilter;
    }

    public void setEnableDirectFilter(boolean enableDirectFilter) {
        this.enableDirectFilter = enableDirectFilter;
    }

    public boolean isEnableTransTable() {
        return this.enableTransTable;
    }

    public BizModelExtFieldInfo getBizModelExtFieldInfo() {
        return this.bizModelExtFieldInfo;
    }

    public void setBizModelExtFields(BizModelExtFieldInfo bizModelExtFieldInfo) {
        this.bizModelExtFieldInfo = bizModelExtFieldInfo;
    }

    public Boolean getIncludeAdjustVchr() {
        return this.includeAdjustVchr;
    }

    public void setIncludeAdjustVchr(Boolean includeAdjustVchr) {
        this.includeAdjustVchr = includeAdjustVchr;
    }

    public String getBizCombId() {
        if (this.bizCombId != null) {
            return this.bizCombId;
        }
        StringBuilder cacheKeyBuilder = new StringBuilder();
        cacheKeyBuilder.append(StringUtils.isEmpty((String)this.rpUnitType) ? "#" : this.rpUnitType).append("|");
        cacheKeyBuilder.append(ModelExecuteUtil.getValByDefault(this.getUnitCode())).append("|");
        cacheKeyBuilder.append(ModelExecuteUtil.getValByDefault(this.getAcctYear())).append("|");
        cacheKeyBuilder.append(ModelExecuteUtil.getValByDefault(this.getEndPeriod())).append("|");
        cacheKeyBuilder.append(ModelExecuteUtil.getIncludeUncharged(this.isIncludeUncharged())).append("|");
        cacheKeyBuilder.append(ModelExecuteUtil.getIncludeAdjustVoucher(this.getIncludeAdjustVchr())).append("|");
        cacheKeyBuilder.append(this.orgMapping.getDataSchemeCode()).append("|");
        this.bizCombId = cacheKeyBuilder.toString();
        return this.bizCombId;
    }

    public String toString() {
        return "BalanceCondition [requestTaskId=" + this.requestTaskId + ", callBackIp=" + this.callBackIp + ", bblX=" + this.bblX + ", rpUnitType=" + this.rpUnitType + ", computationModel=" + this.computationModel + ", unitCode=" + this.unitCode + ", acctYear=" + this.acctYear + ", startPeriod=" + this.startPeriod + ", endPeriod=" + this.endPeriod + ", startDate=" + this.startDate + ", endDate=" + this.endDate + ", assTypeList=" + this.assTypeList + ", assTypeMap=" + this.assTypeMap + ", includeUncharged=" + this.includeUncharged + ", includeAdjustVchr=" + this.includeAdjustVchr + ", startAdjustPeriod=" + this.startAdjustPeriod + ", endAdjustPeriod=" + this.endAdjustPeriod + ", bizCombId=" + this.bizCombId + ", orgMapping=" + this.orgMapping + ", otherEntity=" + this.otherEntity + ", dimensionValueMap=" + this.dimensionValueMap + ", extParam=" + this.extParam + ", conditionMatchRule=" + this.conditionMatchRule + ", enableDirectFilter=" + this.enableDirectFilter + ", enableTransTable=" + this.enableTransTable + "]";
    }
}

