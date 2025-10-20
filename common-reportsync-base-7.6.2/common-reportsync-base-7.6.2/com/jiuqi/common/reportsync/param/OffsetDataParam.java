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
import java.util.List;
import java.util.stream.Collectors;

public class OffsetDataParam {
    private String taskId;
    private String schemeId;
    private String periodStr;
    private String adjustCode;
    private Integer periodOffset;
    private List<String> unitCodes;
    private List<GcOrgCacheVO> unitVos;
    private String systemId;
    private String orgType;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getPeriodOffset() {
        return this.periodOffset;
    }

    public void setPeriodOffset(Integer periodOffset) {
        this.periodOffset = periodOffset;
    }

    public List<String> getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(List<String> unitCodes) {
        this.unitCodes = unitCodes;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public void convert2EO() {
        if (!CollectionUtils.isEmpty(this.unitVos)) {
            this.setUnitCodes(this.unitVos.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList()));
        }
    }

    public void setUnitVos(List<GcOrgCacheVO> unitVos) {
        this.unitVos = unitVos;
    }

    public List<GcOrgCacheVO> getUnitVos() {
        return this.unitVos;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getAdjustCode() {
        return this.adjustCode;
    }

    public void setAdjustCode(String adjustCode) {
        this.adjustCode = adjustCode;
    }
}

