/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.exception;

import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.exception.BpmException;

public class ProcessInstanceDuplicated
extends BpmException {
    private static final long serialVersionUID = -6376472400659139881L;
    private BusinessKey businessKey;

    public ProcessInstanceDuplicated(BusinessKey businessKey) {
        super("process instance with given business key is exists.");
        this.businessKey = businessKey;
    }

    public BusinessKey getBusinessKey() {
        return this.businessKey;
    }

    public void setBusinessKey(BusinessKey businessKey) {
        this.businessKey = businessKey;
    }
}

