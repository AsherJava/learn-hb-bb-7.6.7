/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.common;

import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.EntityCheckUpUnitInfo;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.common.MistakeUnitInfo;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;

public class EntityCheckContext {
    private JtableContext context;
    private List<EntityCheckUpUnitInfo> unitInfo;
    private List<MistakeUnitInfo> mistakeUnitInfo;
    private String associatedFormSchemeKey;
    private String associatedperiod;

    public String getAssociatedFormSchemeKey() {
        return this.associatedFormSchemeKey;
    }

    public void setAssociatedFormSchemeKey(String associatedFormSchemeKey) {
        this.associatedFormSchemeKey = associatedFormSchemeKey;
    }

    public String getAssociatedperiod() {
        return this.associatedperiod;
    }

    public void setAssociatedperiod(String associatedperiod) {
        this.associatedperiod = associatedperiod;
    }

    public List<MistakeUnitInfo> getMistakeUnitInfo() {
        return this.mistakeUnitInfo;
    }

    public void setMistakeUnitInfo(List<MistakeUnitInfo> mistakeUnitInfo) {
        this.mistakeUnitInfo = mistakeUnitInfo;
    }

    public List<EntityCheckUpUnitInfo> getUnitInfo() {
        return this.unitInfo;
    }

    public void setUnitInfo(List<EntityCheckUpUnitInfo> unitInfo) {
        this.unitInfo = unitInfo;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }
}

