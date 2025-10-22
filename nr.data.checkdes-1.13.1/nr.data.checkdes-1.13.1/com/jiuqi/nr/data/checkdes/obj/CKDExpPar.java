/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.checkdes.obj;

import com.jiuqi.nr.data.checkdes.obj.BasePar;
import java.util.List;

public class CKDExpPar
extends BasePar {
    private List<String> formulaKeys;
    private boolean expUserTime = true;

    public List<String> getFormulaKeys() {
        return this.formulaKeys;
    }

    public void setFormulaKeys(List<String> formulaKeys) {
        this.formulaKeys = formulaKeys;
    }

    public boolean isExpUserTime() {
        return this.expUserTime;
    }

    public void setExpUserTime(boolean expUserTime) {
        this.expUserTime = expUserTime;
    }
}

