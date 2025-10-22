/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.beforeUPLOAD;

import com.jiuqi.nr.finalaccountsaudit.multcheck.beforeUPLOAD.FinalaccountsAuditCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.multcheck.beforeUPLOAD.FinalaccountsAuditExtraInfo;
import java.io.Serializable;

public class FinalaccountsAuditConfig
implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean beforeUPLOAD;
    private FinalaccountsAuditExtraInfo extraInfo;
    private FinalaccountsAuditCheckInfo checkInfo;

    public boolean isBeforeUPLOAD() {
        return this.beforeUPLOAD;
    }

    public void setBeforeUPLOAD(boolean beforeUPLOAD) {
        this.beforeUPLOAD = beforeUPLOAD;
    }

    public FinalaccountsAuditExtraInfo getExtraInfo() {
        return this.extraInfo;
    }

    public void setExtraInfo(FinalaccountsAuditExtraInfo extraInfo) {
        this.extraInfo = extraInfo;
    }

    public FinalaccountsAuditCheckInfo getCheckInfo() {
        return this.checkInfo;
    }

    public void setCheckInfo(FinalaccountsAuditCheckInfo checkInfo) {
        this.checkInfo = checkInfo;
    }
}

