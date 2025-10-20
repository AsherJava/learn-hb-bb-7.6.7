/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.analysisreport.dto.req;

import com.jiuqi.gcreport.analysisreport.dto.AnalysisReportDimensionDTO;
import java.util.List;

public class ReqQueryAnalysisReportDatasDTO {
    Integer pageSize;
    Integer pageNum;
    private List<AnalysisReportDimensionDTO> dimensions;
    private Boolean showAllChilds;
    private Boolean showLastestVersion;
    private Boolean onlyShowConfirmVersion;
    private String templateId;

    public List<AnalysisReportDimensionDTO> getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(List<AnalysisReportDimensionDTO> value) {
        this.dimensions = value;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Boolean getShowAllChilds() {
        return this.showAllChilds;
    }

    public void setShowAllChilds(Boolean showAllChilds) {
        this.showAllChilds = showAllChilds;
    }

    public Boolean getShowLastestVersion() {
        return this.showLastestVersion;
    }

    public void setShowLastestVersion(Boolean showLastestVersion) {
        this.showLastestVersion = showLastestVersion;
    }

    public Boolean getOnlyShowConfirmVersion() {
        return this.onlyShowConfirmVersion;
    }

    public void setOnlyShowConfirmVersion(Boolean onlyShowConfirmVersion) {
        this.onlyShowConfirmVersion = onlyShowConfirmVersion;
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
}

