/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.integritycheck.common.IntegrityCheckResInfo
 */
package com.jiuqi.nr.integritycheck.adapter.common;

import com.jiuqi.nr.integritycheck.common.IntegrityCheckResInfo;
import java.io.Serializable;

public class CheckResultInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String batchId;
    private IntegrityCheckResInfo integrityCheckResInfo;

    public String getBatchId() {
        return this.batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public IntegrityCheckResInfo getIntegrityCheckResInfo() {
        return this.integrityCheckResInfo;
    }

    public void setIntegrityCheckResInfo(IntegrityCheckResInfo integrityCheckResInfo) {
        this.integrityCheckResInfo = integrityCheckResInfo;
    }
}

