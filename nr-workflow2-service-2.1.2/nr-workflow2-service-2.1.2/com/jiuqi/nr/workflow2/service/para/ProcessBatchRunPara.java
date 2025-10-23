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
 *  org.json.JSONObject
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
import com.jiuqi.nr.workflow2.service.para.ProcessRangeDims;
import com.jiuqi.nr.workflow2.service.para.ProcessRangeDimsDeserializer;
import com.jiuqi.nr.workflow2.service.para.ProcessRunPara;
import com.jiuqi.util.StringUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

public final class ProcessBatchRunPara
extends ProcessRunPara
implements Cloneable {
    @JsonIgnore
    private Map<String, Integer> dimensionCountMap = new HashMap<String, Integer>();
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private Set<ProcessRangeDims> reportDimensions = new HashSet<ProcessRangeDims>();

    public Set<ProcessRangeDims> getReportDimensions() {
        return this.reportDimensions;
    }

    @JsonDeserialize(using=ProcessRangeDimsDeserializer.class)
    public void setReportDimensions(Set<ProcessRangeDims> reportDimensions) {
        this.reportDimensions = reportDimensions;
    }

    @Override
    public OperateStateCode checkPara() {
        DataDimension unitDimension;
        OperateStateCode operateStateCode = super.checkPara();
        if (OperateStateCode.OPT_SUCCESS != operateStateCode) {
            return operateStateCode;
        }
        for (ProcessRangeDims reportDimension : this.reportDimensions) {
            operateStateCode = reportDimension.checkPara();
            if (reportDimension.checkPara() == OperateStateCode.OPT_SUCCESS) continue;
            return operateStateCode;
        }
        IReportDimensionHelper reportDimensionHelper = (IReportDimensionHelper)SpringBeanUtils.getBean(ReportDimensionHelper.class);
        ProcessRangeDims unitRangeDim = this.getOneReportDimension(reportDimensionHelper.getDimensionName(unitDimension = reportDimensionHelper.getReportUnitDimension(this.getTaskKey())));
        if (unitRangeDim == null) {
            return OperateStateCode.ERR_UNIT_DIM_VALUE_CAN_NOT_BE_NULL;
        }
        return operateStateCode;
    }

    public ProcessBatchRunPara clone() {
        try {
            ProcessBatchRunPara clone = (ProcessBatchRunPara)super.clone();
            HashSet<ProcessRangeDims> cloneRangeDims = new HashSet<ProcessRangeDims>();
            clone.setReportDimensions(cloneRangeDims);
            for (ProcessRangeDims dims : this.reportDimensions) {
                cloneRangeDims.add(dims.clone());
            }
            clone.setEnvVariables(new JSONObject(this.getCustomVariable().getValue().toMap()));
            return clone;
        }
        catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public ProcessBatchRunPara cloneAndRestRangeDims(ProcessRangeDims processRangeDims) {
        ProcessBatchRunPara clone = this.clone();
        clone.appendReportDimension(processRangeDims);
        return clone;
    }

    public void appendReportDimension(ProcessRangeDims reportDimension) {
        this.reportDimensions.add(reportDimension);
    }

    @JsonIgnore
    public ProcessRangeDims getOneReportDimension(String dimensionName) {
        if (StringUtils.isNotEmpty((String)dimensionName)) {
            return this.reportDimensions.stream().filter(e -> dimensionName.equals(e.getDimensionName())).findFirst().orElse(null);
        }
        return null;
    }
}

