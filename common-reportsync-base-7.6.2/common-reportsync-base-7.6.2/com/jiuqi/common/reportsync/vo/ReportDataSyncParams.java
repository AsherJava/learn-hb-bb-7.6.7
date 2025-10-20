/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeTreeVO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 */
package com.jiuqi.common.reportsync.vo;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.reportsync.param.ConsSystemParam;
import com.jiuqi.common.reportsync.param.ConversionRateDataParam;
import com.jiuqi.common.reportsync.param.InvestDataParam;
import com.jiuqi.common.reportsync.param.OffsetDataParam;
import com.jiuqi.common.reportsync.param.ReportDataParam;
import com.jiuqi.common.reportsync.param.ReportParam;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.OrgTypeTreeVO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import java.util.List;
import java.util.stream.Collectors;

public class ReportDataSyncParams {
    private String id;
    private String title;
    private String syncDesAttachId;
    private String syncDesAttachTitle;
    private String paramPackageTitle;
    private Boolean onlyMergeParams;
    private String syncType;
    private ReportParam reportParam;
    private ConsSystemParam consSystemParam;
    private String conversionSystemId;
    private List<OrgTypeTreeVO> unitVersionVos;
    private List<String> unitVersionIds;
    private List<BaseDataDefineDO> baseDataVos;
    private List<String> baseDataTables;
    private Boolean exportOrg;
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
    private List<String> logs;

    public String getSyncType() {
        return this.syncType;
    }

    public void setSyncType(String syncType) {
        this.syncType = syncType;
    }

    public ReportParam getReportParam() {
        return this.reportParam;
    }

    public void setReportParam(ReportParam reportParam) {
        this.reportParam = reportParam;
    }

    public ConsSystemParam getConsSystemParam() {
        return this.consSystemParam;
    }

    public void setConsSystemParam(ConsSystemParam consSystemParam) {
        this.consSystemParam = consSystemParam;
    }

    public List<String> getUnitVersionIds() {
        return this.unitVersionIds;
    }

    public void setUnitVersionIds(List<String> unitVersionIds) {
        this.unitVersionIds = unitVersionIds;
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

    public List<String> getLessorUnitCodes() {
        return this.lessorUnitCodes;
    }

    public void setLessorUnitCodes(List<String> lessorUnitCodes) {
        this.lessorUnitCodes = lessorUnitCodes;
    }

    public List<String> getTenantryUnitCodes() {
        return this.tenantryUnitCodes;
    }

    public void setTenantryUnitCodes(List<String> tenantryUnitCodes) {
        this.tenantryUnitCodes = tenantryUnitCodes;
    }

    public OffsetDataParam getOffsetData() {
        return this.offsetData;
    }

    public void setOffsetData(OffsetDataParam offsetData) {
        this.offsetData = offsetData;
    }

    public ConversionRateDataParam getConversionRateData() {
        return this.conversionRateData;
    }

    public void setConversionRateData(ConversionRateDataParam conversionRateData) {
        this.conversionRateData = conversionRateData;
    }

    public String getConversionSystemId() {
        return this.conversionSystemId;
    }

    public void setConversionSystemId(String conversionSystemId) {
        this.conversionSystemId = conversionSystemId;
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
        this.setUnitVersionIds(!CollectionUtils.isEmpty(this.unitVersionVos) ? this.unitVersionVos.stream().map(OrgTypeTreeVO::getId).collect(Collectors.toList()) : null);
        this.setBaseDataTables(!CollectionUtils.isEmpty(this.baseDataVos) ? this.baseDataVos.stream().map(BaseDataDefineDO::getName).collect(Collectors.toList()) : null);
    }

    public List<GcOrgCacheVO> getAssetUnitVos() {
        return this.assetUnitVos;
    }

    public void setAssetUnitVos(List<GcOrgCacheVO> assetUnitVos) {
        this.assetUnitVos = assetUnitVos;
    }

    public List<GcOrgCacheVO> getLessorUnitVos() {
        return this.lessorUnitVos;
    }

    public void setLessorUnitVos(List<GcOrgCacheVO> lessorUnitVos) {
        this.lessorUnitVos = lessorUnitVos;
    }

    public List<GcOrgCacheVO> getTenantryUnitVos() {
        return this.tenantryUnitVos;
    }

    public void setTenantryUnitVos(List<GcOrgCacheVO> tenantryUnitVos) {
        this.tenantryUnitVos = tenantryUnitVos;
    }

    public List<OrgTypeTreeVO> getUnitVersionVos() {
        return this.unitVersionVos;
    }

    public void setUnitVersionVos(List<OrgTypeTreeVO> unitVersionVos) {
        this.unitVersionVos = unitVersionVos;
    }

    public List<String> getBaseDataTables() {
        return this.baseDataTables;
    }

    public void setBaseDataTables(List<String> baseDataTables) {
        this.baseDataTables = baseDataTables;
    }

    public List<BaseDataDefineDO> getBaseDataVos() {
        return this.baseDataVos;
    }

    public void setBaseDataVos(List<BaseDataDefineDO> baseDataVos) {
        this.baseDataVos = baseDataVos;
    }

    public Boolean getExportOrg() {
        return this.exportOrg;
    }

    public void setExportOrg(Boolean exportOrg) {
        this.exportOrg = exportOrg;
    }

    public OffsetDataParam getOffsetInitData() {
        return this.offsetInitData;
    }

    public void setOffsetInitData(OffsetDataParam offsetInitData) {
        this.offsetInitData = offsetInitData;
    }

    public String getParamPackageTitle() {
        return this.paramPackageTitle;
    }

    public void setParamPackageTitle(String paramPackageTitle) {
        this.paramPackageTitle = paramPackageTitle;
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

    public Boolean getOnlyMergeParams() {
        return this.onlyMergeParams;
    }

    public void setOnlyMergeParams(Boolean onlyMergeParams) {
        this.onlyMergeParams = onlyMergeParams;
    }

    public List<String> getLogs() {
        return this.logs;
    }

    public void setLogs(List<String> logs) {
        this.logs = logs;
    }

    public String getSyncDesAttachId() {
        return this.syncDesAttachId;
    }

    public void setSyncDesAttachId(String syncDesAttachId) {
        this.syncDesAttachId = syncDesAttachId;
    }

    public String getSyncDesAttachTitle() {
        return this.syncDesAttachTitle;
    }

    public void setSyncDesAttachTitle(String syncDesAttachTitle) {
        this.syncDesAttachTitle = syncDesAttachTitle;
    }
}

