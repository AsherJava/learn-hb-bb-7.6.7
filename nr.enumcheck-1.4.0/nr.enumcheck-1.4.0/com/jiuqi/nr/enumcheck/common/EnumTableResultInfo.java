/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.enumcheck.common;

import com.jiuqi.nr.enumcheck.common.EnumTableItem;
import java.io.Serializable;
import java.util.List;

public class EnumTableResultInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<EnumTableItem> enumDataCheckResult;

    public List<EnumTableItem> getEnumDataCheckResult() {
        return this.enumDataCheckResult;
    }

    public void setEnumDataCheckResult(List<EnumTableItem> enumDataCheckResult) {
        this.enumDataCheckResult = enumDataCheckResult;
    }
}

