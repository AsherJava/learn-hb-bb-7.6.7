/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.common.logger;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class LoggerPartInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Set<String> unitCodes = new HashSet<String>();
    private final Set<String> reportCodes = new HashSet<String>();
    private final Set<String> errorMessages = new HashSet<String>();

    public Set<String> getUnitCodes() {
        return this.unitCodes;
    }

    public Set<String> getReportCodes() {
        return this.reportCodes;
    }

    public Set<String> getErrorMessages() {
        return this.errorMessages;
    }
}

