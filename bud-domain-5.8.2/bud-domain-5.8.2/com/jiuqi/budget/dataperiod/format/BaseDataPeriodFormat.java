/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.dataperiod.format;

import com.jiuqi.budget.dataperiod.DataPeriod;
import com.jiuqi.budget.dataperiod.DataPeriodType;
import java.util.Locale;
import org.springframework.beans.factory.BeanNameAware;

public abstract class BaseDataPeriodFormat
implements BeanNameAware {
    private String beanName;

    public abstract String getTitle();

    public abstract String format(Locale var1, DataPeriod var2);

    public abstract DataPeriodType adaptType();

    public boolean isDefault() {
        return false;
    }

    public String getName() {
        return this.beanName;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
}

