/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.workflow2.service.para;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.workflow2.service.exception.OperateStateCode;
import com.jiuqi.nr.workflow2.service.helper.IReportDimensionHelper;
import com.jiuqi.nr.workflow2.service.helper.ReportDimensionHelper;
import com.jiuqi.nr.workflow2.service.para.ProcessOneDim;
import com.jiuqi.nr.workflow2.service.para.ProcessOneDimsDeserializer;
import com.jiuqi.nr.workflow2.service.para.ProcessRunPara;
import com.jiuqi.util.StringUtils;
import java.util.HashSet;
import java.util.Set;

public final class ProcessOneRunPara
extends ProcessRunPara {
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private Set<ProcessOneDim> reportDimensions = new HashSet<ProcessOneDim>();

    public Set<ProcessOneDim> getReportDimensions() {
        return this.reportDimensions;
    }

    @JsonDeserialize(using=ProcessOneDimsDeserializer.class)
    public void setReportDimensions(Set<ProcessOneDim> reportDimensions) {
        this.reportDimensions = reportDimensions;
    }

    public ProcessOneDim getOneReportDimension(String dimensionName) {
        if (StringUtils.isNotEmpty((String)dimensionName)) {
            return this.reportDimensions.stream().filter(e -> dimensionName.equals(e.getDimensionName())).findFirst().orElse(null);
        }
        return null;
    }

    @Override
    @JsonIgnore
    public OperateStateCode checkPara() {
        DataDimension unitDimension;
        OperateStateCode operateStateCode = super.checkPara();
        if (operateStateCode != OperateStateCode.OPT_SUCCESS) {
            return operateStateCode;
        }
        for (ProcessOneDim reportDimension : this.reportDimensions) {
            operateStateCode = reportDimension.checkPara();
            if (reportDimension.checkPara() == OperateStateCode.OPT_SUCCESS) continue;
            return operateStateCode;
        }
        IReportDimensionHelper reportDimensionHelper = (IReportDimensionHelper)SpringBeanUtils.getBean(ReportDimensionHelper.class);
        ProcessOneDim unitDimValue = this.getOneReportDimension(reportDimensionHelper.getDimensionName(unitDimension = reportDimensionHelper.getReportUnitDimension(this.getTaskKey())));
        if (unitDimValue == null) {
            return OperateStateCode.ERR_UNIT_DIM_VALUE_CAN_NOT_BE_NULL;
        }
        return OperateStateCode.OPT_SUCCESS;
    }
}

