/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.datamodel.AgingDataModel
 *  com.jiuqi.bde.bizmodel.define.datamodel.AssBalanceDataModel
 *  com.jiuqi.bde.bizmodel.define.datamodel.InvestBillDataModel
 *  com.jiuqi.bde.bizmodel.define.datamodel.XjllDataModel
 */
package com.jiuqi.bde.bizmodel.execute.config;

import com.jiuqi.bde.bizmodel.define.datamodel.AgingDataModel;
import com.jiuqi.bde.bizmodel.define.datamodel.AssBalanceDataModel;
import com.jiuqi.bde.bizmodel.define.datamodel.InvestBillDataModel;
import com.jiuqi.bde.bizmodel.define.datamodel.XjllDataModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BdeBizDataModelConfig {
    @Bean
    @ConditionalOnMissingBean(value={XjllDataModel.class})
    public XjllDataModel getXjllDataModel() {
        return new XjllDataModel();
    }

    @Bean
    @ConditionalOnMissingBean(value={AgingDataModel.class})
    public AgingDataModel getAgingDataModel() {
        return new AgingDataModel();
    }

    @Bean
    @ConditionalOnMissingBean(value={AssBalanceDataModel.class})
    public AssBalanceDataModel getAssBalanceDataModel() {
        return new AssBalanceDataModel();
    }

    @Bean
    @ConditionalOnMissingBean(value={InvestBillDataModel.class})
    public InvestBillDataModel getInvestBillDataModel() {
        return new InvestBillDataModel();
    }
}

