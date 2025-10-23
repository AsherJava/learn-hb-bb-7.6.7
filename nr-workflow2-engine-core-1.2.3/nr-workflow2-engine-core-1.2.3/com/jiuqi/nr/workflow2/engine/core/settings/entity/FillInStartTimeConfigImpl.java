/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.settings.entity;

import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInDaysConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInStartTimeConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.StartTimeStrategy;
import java.util.Objects;

public class FillInStartTimeConfigImpl
implements FillInStartTimeConfig {
    private boolean enable;
    private StartTimeStrategy type;
    private FillInDaysConfig fillInDaysConfig;

    @Override
    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public StartTimeStrategy getType() {
        return this.type;
    }

    public void setType(StartTimeStrategy type) {
        this.type = type;
    }

    @Override
    public FillInDaysConfig getFillInDaysConfig() {
        return this.fillInDaysConfig;
    }

    public void setFillInDaysConfig(FillInDaysConfig fillInDaysConfig) {
        this.fillInDaysConfig = fillInDaysConfig;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        FillInStartTimeConfigImpl that = (FillInStartTimeConfigImpl)o;
        return this.enable == that.enable && this.type == that.type && Objects.equals(this.fillInDaysConfig, that.fillInDaysConfig);
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.enable, this.type, this.fillInDaysConfig});
    }
}

