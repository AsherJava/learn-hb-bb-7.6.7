/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.exception;

import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.exception.BpmException;

public class ProcessInstanceNotFound
extends BpmException {
    private static final long serialVersionUID = 7853861445728405928L;
    private String processInstanceId;
    private BusinessKey businessKey;

    public ProcessInstanceNotFound(String processInstanceId) {
        super(String.format("can not find process instance.", new Object[0]));
        this.processInstanceId = processInstanceId;
    }

    public ProcessInstanceNotFound(BusinessKey businessKey) {
        super(String.format("can not find process instance.", new Object[0]));
        this.businessKey = businessKey;
    }

    public String getProcessInstanceId() {
        return this.processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public BusinessKey getBusinessKey() {
        return this.businessKey;
    }

    public void setBusinessKey(BusinessKey businessKey) {
        this.businessKey = businessKey;
    }
}

