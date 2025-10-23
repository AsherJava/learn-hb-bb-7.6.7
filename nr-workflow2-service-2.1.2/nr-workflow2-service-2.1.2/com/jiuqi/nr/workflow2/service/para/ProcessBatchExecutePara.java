/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.workflow2.service.para;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jiuqi.nr.workflow2.service.para.ProcessExecutePara;
import com.jiuqi.nr.workflow2.service.para.ProcessRangeDims;
import com.jiuqi.nr.workflow2.service.para.ProcessRangeDimsDeserializer;
import com.jiuqi.util.StringUtils;
import java.util.HashSet;
import java.util.Set;

public final class ProcessBatchExecutePara
extends ProcessExecutePara {
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private Set<ProcessRangeDims> reportDimensions = new HashSet<ProcessRangeDims>();

    public Set<ProcessRangeDims> getReportDimensions() {
        return this.reportDimensions;
    }

    @JsonDeserialize(using=ProcessRangeDimsDeserializer.class)
    public void setReportDimensions(Set<ProcessRangeDims> reportDimensions) {
        this.reportDimensions = reportDimensions;
    }

    @JsonIgnore
    public ProcessRangeDims getOneReportDimension(String dimensionName) {
        if (StringUtils.isNotEmpty((String)dimensionName)) {
            return this.reportDimensions.stream().filter(e -> dimensionName.equals(e.getDimensionName())).findFirst().orElse(null);
        }
        return null;
    }
}

