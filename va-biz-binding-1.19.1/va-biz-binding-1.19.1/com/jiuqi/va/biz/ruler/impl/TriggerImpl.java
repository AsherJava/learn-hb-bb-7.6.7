/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonAutoDetect
 *  com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility
 */
package com.jiuqi.va.biz.ruler.impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.jiuqi.va.biz.ruler.intf.Trigger;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@JsonAutoDetect(getterVisibility=JsonAutoDetect.Visibility.NONE, isGetterVisibility=JsonAutoDetect.Visibility.NONE, fieldVisibility=JsonAutoDetect.Visibility.ANY)
public class TriggerImpl
implements Trigger {
    private String triggerType;
    private String triggerTitle;
    private List<UUID> formulaList;

    @Override
    public String getTriggerType() {
        return this.triggerType;
    }

    @Override
    public Stream<UUID> getFormulaList() {
        return this.formulaList.stream();
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public void setFormulaList(List<UUID> formulaList) {
        this.formulaList = formulaList;
    }

    @Override
    public String getTriggerTitle() {
        return this.triggerTitle;
    }

    public void setTriggerTitle(String triggerTitle) {
        this.triggerTitle = triggerTitle;
    }
}

