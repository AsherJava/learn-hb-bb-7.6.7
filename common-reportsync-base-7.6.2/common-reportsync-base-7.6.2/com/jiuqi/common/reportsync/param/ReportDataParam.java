/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.common.reportsync.param;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;

public class ReportDataParam {
    private String taskId;
    private String fileFormat;
    private String schemeId;
    private String dataScheme;
    private String mappingScheme;
    private String periodStr;
    private String adjustCode;
    private String periodStrTitle;
    private Integer periodOffset;
    private List<GcOrgCacheVO> unitVos;
    private List<String> unitCodes;
    private GcOrgCacheVO rootUnitVo;
    private String rootUnitCode;
    private List<String> formKeys;
    private String orgType;
    private Boolean executeUpload = Boolean.FALSE;
    private Boolean allowForceUpload = Boolean.FALSE;
    private Boolean syncUnit = Boolean.FALSE;
    private String currency;

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

    public List<String> getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(List<String> unitCodes) {
        this.unitCodes = unitCodes;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public Integer getPeriodOffset() {
        return this.periodOffset;
    }

    public void setPeriodOffset(Integer periodOffset) {
        this.periodOffset = periodOffset;
    }

    public List<GcOrgCacheVO> getUnitVos() {
        return this.unitVos;
    }

    public void setUnitVos(List<GcOrgCacheVO> unitVos) {
        this.unitVos = unitVos;
    }

    public GcOrgCacheVO getRootUnitVo() {
        return this.rootUnitVo;
    }

    public void setRootUnitVo(GcOrgCacheVO rootUnitVo) {
        this.rootUnitVo = rootUnitVo;
    }

    public String getRootUnitCode() {
        return this.rootUnitCode;
    }

    public void setRootUnitCode(String rootUnitCode) {
        this.rootUnitCode = rootUnitCode;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public void convert2EO() {
        if (!CollectionUtils.isEmpty(this.unitVos)) {
            this.setUnitCodes(this.unitVos.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList()));
        }
        if (null != this.rootUnitVo) {
            this.setRootUnitCode(this.rootUnitVo.getCode());
        }
    }

    public ReportDataParam clone() {
        ReportDataParam dest = new ReportDataParam();
        BeanUtils.copyProperties(this, dest);
        this.setFormKeys(this.copyList(this.getFormKeys()));
        return dest;
    }

    private <T> List<T> copyList(List<T> src) {
        ArrayList<T> dest = new ArrayList<T>();
        if (src != null) {
            dest.addAll(src);
        }
        return dest;
    }

    public Boolean getExecuteUpload() {
        return this.executeUpload;
    }

    public void setExecuteUpload(Boolean executeUpload) {
        this.executeUpload = executeUpload;
    }

    public Boolean getAllowForceUpload() {
        return this.allowForceUpload;
    }

    public void setAllowForceUpload(Boolean allowForceUpload) {
        this.allowForceUpload = allowForceUpload;
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

    public String getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(String dataScheme) {
        this.dataScheme = dataScheme;
    }

    public Boolean getSyncUnit() {
        return this.syncUnit;
    }

    public void setSyncUnit(Boolean syncUnit) {
        this.syncUnit = syncUnit;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFileFormat() {
        return this.fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getMappingScheme() {
        return this.mappingScheme;
    }

    public void setMappingScheme(String mappingScheme) {
        this.mappingScheme = mappingScheme;
    }
}

