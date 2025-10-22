/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.bpm.impl.single.event;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.common.TaskContext;
import com.jiuqi.nr.bpm.event.ISingleFormRejectEvent;
import java.util.Set;

public class SingleFormRejectEvent
implements ISingleFormRejectEvent {
    private String formSchemeKey;
    private DimensionValueSet dimensionValueSet;
    private Set<String> formKeys;
    private String actionId;
    private String actorId;
    private TaskContext context;

    public SingleFormRejectEvent() {
    }

    public SingleFormRejectEvent(String formSchemeKey, DimensionValueSet dimensionValueSet, Set<String> formKeys) {
        this.formSchemeKey = formSchemeKey;
        this.dimensionValueSet = dimensionValueSet;
        this.formKeys = formKeys;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public Set<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(Set<String> formKeys) {
        this.formKeys = formKeys;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public void setContext(TaskContext context) {
        this.context = context;
    }

    @Override
    public String getActionId() {
        return this.actionId;
    }

    @Override
    public String getActorId() {
        return this.actorId;
    }

    @Override
    public TaskContext getContext() {
        return this.context;
    }
}

