/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.period;

import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodWrapper;

public class CustomPeriodModifier
extends PeriodModifier {
    private static final long serialVersionUID = -3996555494065698871L;
    private String periodCode;
    private PeriodModifier modifier;

    public CustomPeriodModifier(String periodCode) {
        this.periodCode = periodCode;
        this.parsePeriodModifier(periodCode);
    }

    @Override
    public boolean isEmpty() {
        if (this.modifier != null) {
            return this.modifier.isEmpty();
        }
        return this.periodCode == null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.modifier != null) {
            return this.modifier.equals(obj);
        }
        return this.periodCode.equals(obj.toString());
    }

    @Override
    public int hashCode() {
        if (this.modifier != null) {
            return this.modifier.hashCode();
        }
        return this.periodCode.hashCode();
    }

    @Override
    public int compareTo(Object another) {
        if (this.modifier != null) {
            return this.modifier.compareTo(another);
        }
        return this.periodCode.compareTo(another.toString());
    }

    @Override
    public int compareTo(PeriodModifier another) {
        if (this.modifier != null) {
            return this.modifier.compareTo(another);
        }
        return this.periodCode.compareTo(another.toString());
    }

    @Override
    public String modify(String periodString) {
        if (this.modifier != null) {
            return this.modifier.modify(periodString);
        }
        return this.periodCode;
    }

    @Override
    public boolean modify(PeriodWrapper periodWrapper) {
        if (this.modifier != null) {
            return this.modifier.modify(periodWrapper);
        }
        PeriodWrapper newPeriodWrapper = new PeriodWrapper(this.periodCode);
        periodWrapper.assign(newPeriodWrapper);
        return true;
    }

    @Override
    public String combineAbsolutePeriod(PeriodWrapper periodWrapper) {
        if (this.modifier != null) {
            return this.modifier.combineAbsolutePeriod(periodWrapper);
        }
        this.periodCode = periodWrapper.toString();
        return this.periodCode;
    }

    @Override
    public boolean isRelative() {
        if (this.modifier != null) {
            return super.isRelative();
        }
        return false;
    }

    @Override
    public String parsePeriodModifier(String modifierString) {
        PeriodModifier newModifier = PeriodModifier.parse(modifierString);
        if (newModifier != null && !newModifier.isEmpty()) {
            this.modifier = newModifier;
        } else {
            this.periodCode = modifierString;
        }
        return null;
    }

    @Override
    public void union(PeriodModifier another) {
        if (this.modifier != null) {
            this.modifier.union(another);
        }
    }

    @Override
    public String toString() {
        if (this.modifier != null) {
            return this.modifier.toString();
        }
        return this.periodCode;
    }
}

