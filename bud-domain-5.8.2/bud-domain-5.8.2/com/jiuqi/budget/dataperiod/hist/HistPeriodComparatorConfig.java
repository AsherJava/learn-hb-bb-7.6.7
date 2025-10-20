/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.dataperiod.hist;

import com.jiuqi.budget.dataperiod.hist.HistPeriodComparator;
import com.jiuqi.budget.dataperiod.hist.IHistPeriodComparator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HistPeriodComparatorConfig {
    @Bean
    @ConditionalOnMissingBean(value={IHistPeriodComparator.class})
    public IHistPeriodComparator budDefaultHistPeriodComparator() {
        return new HistPeriodComparator();
    }
}

