/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.workflow2.service.para;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jiuqi.nr.workflow2.service.para.ProcessExecutePara;
import com.jiuqi.nr.workflow2.service.para.ProcessOneDim;
import com.jiuqi.nr.workflow2.service.para.ProcessOneDimsDeserializer;
import com.jiuqi.util.StringUtils;
import java.util.HashSet;
import java.util.Set;

public class ProcessOneExecutePara
extends ProcessExecutePara {
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
}

