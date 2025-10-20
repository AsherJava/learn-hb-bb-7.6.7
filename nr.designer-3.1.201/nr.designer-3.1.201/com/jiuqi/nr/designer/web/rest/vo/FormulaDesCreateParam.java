/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.jiuqi.nr.designer.web.facade.FormulaCheckObj;
import java.util.List;

public class FormulaDesCreateParam {
    private String schemeKey;
    private List<FormulaCheckObj> formuObj;

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public List<FormulaCheckObj> getFormuObj() {
        return this.formuObj;
    }

    public void setFormuObj(List<FormulaCheckObj> formuObj) {
        this.formuObj = formuObj;
    }
}

