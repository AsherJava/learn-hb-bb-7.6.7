/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessDefinitionException
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus$DataAccessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus$DataReportStatus
 */
package com.jiuqi.nr.workflow2.engine.common.definition.model;

import com.jiuqi.nr.workflow2.engine.common.definition.model.ProcessStatusTemplate;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessDefinitionException;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.dflt.utils.StringUtils;

public class ProcessStatus
implements IProcessStatus {
    private ProcessStatusTemplate statusTemplate;
    private String alias;

    public ProcessStatus(String statusCode, String alias) {
        this.statusTemplate = ProcessStatusTemplate.get(statusCode);
        if (this.statusTemplate == null) {
            throw new ProcessDefinitionException("jiuqi.nr.default", null, "ProcessStatus statusTemplate must not be empty.");
        }
        this.alias = StringUtils.isEmpty(alias) ? this.getTitle() : alias;
    }

    public ProcessStatus(ProcessStatusTemplate statusTemplate, String alias) {
        if (statusTemplate == null) {
            throw new ProcessDefinitionException("jiuqi.nr.default", null, "ProcessStatus statusTemplate must not be empty.");
        }
        this.statusTemplate = statusTemplate;
        this.alias = StringUtils.isEmpty(alias) ? this.getTitle() : alias;
    }

    public String getCode() {
        return this.statusTemplate.getCode();
    }

    public String getTitle() {
        return this.statusTemplate.getTitle();
    }

    public String getAlias() {
        return this.alias;
    }

    public IProcessStatus.DataAccessStatus getDataAccessStatus() {
        return this.statusTemplate.getDataAccessStatus();
    }

    public IProcessStatus.DataReportStatus getDataReportStatus() {
        return this.statusTemplate.getDataReportStatus();
    }

    public String getIcon() {
        return null;
    }

    public String getColor() {
        return null;
    }
}

