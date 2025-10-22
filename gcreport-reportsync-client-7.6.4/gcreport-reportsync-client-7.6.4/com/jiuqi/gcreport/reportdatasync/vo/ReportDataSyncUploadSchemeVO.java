/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.reportsync.param.ConversionRateDataParam
 *  com.jiuqi.common.reportsync.param.InvestDataParam
 *  com.jiuqi.common.reportsync.param.OffsetDataParam
 *  com.jiuqi.common.reportsync.param.ReportDataParam
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.reportdatasync.vo;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.reportsync.param.ConversionRateDataParam;
import com.jiuqi.common.reportsync.param.InvestDataParam;
import com.jiuqi.common.reportsync.param.OffsetDataParam;
import com.jiuqi.common.reportsync.param.ReportDataParam;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReportDataSyncUploadSchemeVO {
    private String id;
    private String title;
    private String parentId;
    private String groupDescribe;
    private Integer schemeGroup;
    private List<ReportDataSyncUploadSchemeVO> children = new ArrayList<ReportDataSyncUploadSchemeVO>();
    private String taskId;
    private String taskTitle;
    private String dataScheme;
    private String adjustCode;
    private String periodType;
    private String periodStr;
    private String periodStrTitle;
    private Boolean applicationMode;
    private List<GcOrgCacheVO> unitVos;
    private Boolean syncUnit;
    private String syncType;
    private ReportDataParam reportData;
    private InvestDataParam investData;
    private List<String> assetUnitCodes;
    private List<GcOrgCacheVO> assetUnitVos;
    private List<String> lessorUnitCodes;
    private List<GcOrgCacheVO> lessorUnitVos;
    private List<String> tenantryUnitCodes;
    private List<GcOrgCacheVO> tenantryUnitVos;
    private OffsetDataParam offsetData;
    private OffsetDataParam offsetInitData;
    private ConversionRateDataParam conversionRateData;

    public String getSyncType() {
        return this.syncType;
    }

    public void setSyncType(String syncType) {
        this.syncType = syncType;
    }

    public ReportDataParam getReportData() {
        return this.reportData;
    }

    public void setReportData(ReportDataParam reportData) {
        this.reportData = reportData;
    }

    public InvestDataParam getInvestData() {
        return this.investData;
    }

    public void setInvestData(InvestDataParam investData) {
        this.investData = investData;
    }

    public List<String> getAssetUnitCodes() {
        return this.assetUnitCodes;
    }

    public void setAssetUnitCodes(List<String> assetUnitCodes) {
        this.assetUnitCodes = assetUnitCodes;
    }

    public List<GcOrgCacheVO> getAssetUnitVos() {
        return this.assetUnitVos;
    }

    public void setAssetUnitVos(List<GcOrgCacheVO> assetUnitVos) {
        this.assetUnitVos = assetUnitVos;
    }

    public List<String> getLessorUnitCodes() {
        return this.lessorUnitCodes;
    }

    public void setLessorUnitCodes(List<String> lessorUnitCodes) {
        this.lessorUnitCodes = lessorUnitCodes;
    }

    public List<GcOrgCacheVO> getLessorUnitVos() {
        return this.lessorUnitVos;
    }

    public void setLessorUnitVos(List<GcOrgCacheVO> lessorUnitVos) {
        this.lessorUnitVos = lessorUnitVos;
    }

    public List<String> getTenantryUnitCodes() {
        return this.tenantryUnitCodes;
    }

    public void setTenantryUnitCodes(List<String> tenantryUnitCodes) {
        this.tenantryUnitCodes = tenantryUnitCodes;
    }

    public List<GcOrgCacheVO> getTenantryUnitVos() {
        return this.tenantryUnitVos;
    }

    public void setTenantryUnitVos(List<GcOrgCacheVO> tenantryUnitVos) {
        this.tenantryUnitVos = tenantryUnitVos;
    }

    public OffsetDataParam getOffsetData() {
        return this.offsetData;
    }

    public void setOffsetData(OffsetDataParam offsetData) {
        this.offsetData = offsetData;
    }

    public OffsetDataParam getOffsetInitData() {
        return this.offsetInitData;
    }

    public void setOffsetInitData(OffsetDataParam offsetInitData) {
        this.offsetInitData = offsetInitData;
    }

    public ConversionRateDataParam getConversionRateData() {
        return this.conversionRateData;
    }

    public void setConversionRateData(ConversionRateDataParam conversionRateData) {
        this.conversionRateData = conversionRateData;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public void convert2EO() {
        if (null != this.reportData) {
            this.reportData.convert2EO();
        }
        if (null != this.investData) {
            this.investData.convert2EO();
        }
        if (null != this.offsetData) {
            this.offsetData.convert2EO();
        }
        this.setAssetUnitCodes(!CollectionUtils.isEmpty(this.assetUnitVos) ? this.assetUnitVos.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList()) : null);
        this.setLessorUnitCodes(!CollectionUtils.isEmpty(this.lessorUnitVos) ? this.lessorUnitVos.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList()) : null);
        this.setTenantryUnitCodes(!CollectionUtils.isEmpty(this.tenantryUnitVos) ? this.tenantryUnitVos.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList()) : null);
    }

    public Boolean getApplicationMode() {
        return this.applicationMode;
    }

    public void setApplicationMode(Boolean applicationMode) {
        this.applicationMode = applicationMode;
    }

    public List<GcOrgCacheVO> getUnitVos() {
        return this.unitVos;
    }

    public void setUnitVos(List<GcOrgCacheVO> unitVos) {
        this.unitVos = unitVos;
    }

    public Boolean getSyncUnit() {
        return this.syncUnit;
    }

    public void setSyncUnit(Boolean syncUnit) {
        this.syncUnit = syncUnit;
    }

    public String getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(String dataScheme) {
        this.dataScheme = dataScheme;
    }

    public String getAdjustCode() {
        return this.adjustCode;
    }

    public void setAdjustCode(String adjustCode) {
        this.adjustCode = adjustCode;
    }

    public String getPeriodStrTitle() {
        return this.periodStrTitle;
    }

    public void setPeriodStrTitle(String periodStrTitle) {
        this.periodStrTitle = periodStrTitle;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getGroupDescribe() {
        return this.groupDescribe;
    }

    public void setGroupDescribe(String groupDescribe) {
        this.groupDescribe = groupDescribe;
    }

    public Integer getSchemeGroup() {
        return this.schemeGroup == null ? 0 : this.schemeGroup;
    }

    public void setSchemeGroup(Integer schemeGroup) {
        this.schemeGroup = schemeGroup;
    }

    public List<ReportDataSyncUploadSchemeVO> getChildren() {
        return this.children == null ? new ArrayList() : this.children;
    }

    public void setChildren(List<ReportDataSyncUploadSchemeVO> children) {
        this.children = children;
    }
}

