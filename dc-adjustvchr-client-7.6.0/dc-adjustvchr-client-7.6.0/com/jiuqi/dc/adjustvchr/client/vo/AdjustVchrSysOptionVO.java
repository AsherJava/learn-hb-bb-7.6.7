/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.adjustvchr.client.vo;

import java.io.Serializable;
import java.util.List;

public class AdjustVchrSysOptionVO
implements Serializable {
    private static final long serialVersionUID = -14513355235241129L;
    private boolean digestNotNull;
    private List<String> extraFields;
    private boolean notLeafUnitCreateEnable;
    private boolean enableOrgnCurr;
    private boolean remarkEnable;

    public boolean isDigestNotNull() {
        return this.digestNotNull;
    }

    public void setDigestNotNull(boolean digestNotNull) {
        this.digestNotNull = digestNotNull;
    }

    public List<String> getExtraFields() {
        return this.extraFields;
    }

    public void setExtraFields(List<String> extraFields) {
        this.extraFields = extraFields;
    }

    public boolean isNotLeafUnitCreateEnable() {
        return this.notLeafUnitCreateEnable;
    }

    public void setNotLeafUnitCreateEnable(boolean notLeafUnitCreateEnable) {
        this.notLeafUnitCreateEnable = notLeafUnitCreateEnable;
    }

    public boolean isEnableOrgnCurr() {
        return this.enableOrgnCurr;
    }

    public void setEnableOrgnCurr(boolean enableOrgnCurr) {
        this.enableOrgnCurr = enableOrgnCurr;
    }

    public boolean isRemarkEnable() {
        return this.remarkEnable;
    }

    public void setRemarkEnable(boolean remarkEnable) {
        this.remarkEnable = remarkEnable;
    }
}

