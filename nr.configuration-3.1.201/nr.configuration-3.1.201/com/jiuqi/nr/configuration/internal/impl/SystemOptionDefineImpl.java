/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.configuration.internal.impl;

import com.jiuqi.nr.configuration.facade.SystemOptionBase;
import com.jiuqi.nr.configuration.facade.SystemOptionDefine;
import com.jiuqi.nr.configuration.internal.impl.SystemOptionBaseImpl;

@Deprecated
public class SystemOptionDefineImpl
extends SystemOptionBaseImpl
implements SystemOptionDefine {
    private String taskKey;
    private String formSchemeKey;
    private Object value;
    private boolean selected;

    public SystemOptionDefineImpl() {
    }

    public SystemOptionDefineImpl(SystemOptionBase optionBase) {
        super(optionBase.getKey(), optionBase.getGroup(), optionBase.getTitle(), optionBase.getDefaultValue(), optionBase.getOptionType(), optionBase.getOptionEditMode(), optionBase.getOptionItems(), optionBase.getRegex(), optionBase.getOrder());
    }

    @Override
    public String getTaskKey() {
        return this.taskKey;
    }

    @Override
    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

