/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.settings.entity;

import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInDaysConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInEndTimeConfig;

public class FillInEndTimeConfigImpl
implements FillInEndTimeConfig {
    private boolean enable;
    private FillInDaysConfig fillInDaysConfig;
    private boolean hierarchicalControl;

    @Override
    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public FillInDaysConfig getFillInDaysConfig() {
        return this.fillInDaysConfig;
    }

    public void setFillInDaysConfig(FillInDaysConfig fillInDaysConfig) {
        this.fillInDaysConfig = fillInDaysConfig;
    }

    @Override
    public boolean isHierarchicalControl() {
        return this.hierarchicalControl;
    }

    public void setHierarchicalControl(boolean hierarchicalControl) {
        this.hierarchicalControl = hierarchicalControl;
    }
}

