/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.settings.entity;

import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInDaysConfig;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.TimeControlType;
import java.util.Objects;

public class FillInDaysConfigImpl
implements FillInDaysConfig {
    private TimeControlType type;
    private int dayNum;

    @Override
    public TimeControlType getType() {
        return this.type;
    }

    public void setType(TimeControlType type) {
        this.type = type;
    }

    @Override
    public int getDayNum() {
        return this.dayNum;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        FillInDaysConfigImpl that = (FillInDaysConfigImpl)o;
        return this.dayNum == that.dayNum && this.type == that.type;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.type, this.dayNum});
    }
}

