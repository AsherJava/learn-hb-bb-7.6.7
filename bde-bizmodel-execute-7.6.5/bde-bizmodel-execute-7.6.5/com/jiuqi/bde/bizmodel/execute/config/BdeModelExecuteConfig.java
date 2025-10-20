/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.execute.config;

import com.jiuqi.bde.bizmodel.execute.model.assaging.fetch.AssAgingBalanceModelExecute;
import com.jiuqi.bde.bizmodel.execute.model.assbalance.fetch.AssBalanceModelExecute;
import com.jiuqi.bde.bizmodel.execute.model.asscfl.fetch.AssCflBalanceModelExecute;
import com.jiuqi.bde.bizmodel.execute.model.balance.fetch.BalanceModelExecute;
import com.jiuqi.bde.bizmodel.execute.model.cfl.fetch.CflBalanceModelExecute;
import com.jiuqi.bde.bizmodel.execute.model.djye.fetch.DjyeBalanceModelExecute;
import com.jiuqi.bde.bizmodel.execute.model.invest.fetch.InvestBillModelExecute;
import com.jiuqi.bde.bizmodel.execute.model.xjll.fetch.XjllBalanceModelExecute;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BdeModelExecuteConfig {
    @Bean
    @ConditionalOnMissingBean(value={XjllBalanceModelExecute.class})
    public XjllBalanceModelExecute getXjllBalanceModelExecute() {
        return new XjllBalanceModelExecute();
    }

    @Bean
    @ConditionalOnMissingBean(value={DjyeBalanceModelExecute.class})
    public DjyeBalanceModelExecute getDjyeBalanceModelExecute() {
        return new DjyeBalanceModelExecute();
    }

    @Bean
    @ConditionalOnMissingBean(value={AssBalanceModelExecute.class})
    public AssBalanceModelExecute getAssBalanceModelExecute() {
        return new AssBalanceModelExecute();
    }

    @Bean
    @ConditionalOnMissingBean(value={CflBalanceModelExecute.class})
    public CflBalanceModelExecute getCflBalanceModelExecute() {
        return new CflBalanceModelExecute();
    }

    @Bean
    @ConditionalOnMissingBean(value={AssCflBalanceModelExecute.class})
    public AssCflBalanceModelExecute getAssCflBalanceModelExecute() {
        return new AssCflBalanceModelExecute();
    }

    @Bean
    @ConditionalOnMissingBean(value={AssAgingBalanceModelExecute.class})
    public AssAgingBalanceModelExecute getAssAgingBalanceModelExecute() {
        return new AssAgingBalanceModelExecute();
    }

    @Bean
    @ConditionalOnMissingBean(value={BalanceModelExecute.class})
    public BalanceModelExecute getBalanceModelExecute() {
        return new BalanceModelExecute();
    }

    @Bean
    @ConditionalOnMissingBean(value={InvestBillModelExecute.class})
    public InvestBillModelExecute getInvestBillModelExecute() {
        return new InvestBillModelExecute();
    }
}

