/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.execute.config;

import com.jiuqi.bde.bizmodel.execute.model.aging.AgingBalanceComputationModel;
import com.jiuqi.bde.bizmodel.execute.model.assaging.AssAgingBalanceComputationModel;
import com.jiuqi.bde.bizmodel.execute.model.assbalance.AssBalanceComputationModel;
import com.jiuqi.bde.bizmodel.execute.model.asscfl.AssCflBalanceComputationModel;
import com.jiuqi.bde.bizmodel.execute.model.balance.BalanceComputationModel;
import com.jiuqi.bde.bizmodel.execute.model.basedata.BaseDataComputationModel;
import com.jiuqi.bde.bizmodel.execute.model.cfl.CflBalanceComputationModel;
import com.jiuqi.bde.bizmodel.execute.model.custommade.model.CustomFetchComputationModel;
import com.jiuqi.bde.bizmodel.execute.model.djye.DjyeBalanceComputationModel;
import com.jiuqi.bde.bizmodel.execute.model.tfv.single.TfvComputationModel;
import com.jiuqi.bde.bizmodel.execute.model.voucher.VoucherComputationModel;
import com.jiuqi.bde.bizmodel.execute.model.xjll.XjllBalanceComputationModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BdeComputationModelConfig {
    @Bean
    @ConditionalOnMissingBean(value={BalanceComputationModel.class})
    public BalanceComputationModel getBalanceComputationModel() {
        return new BalanceComputationModel();
    }

    @Bean
    @ConditionalOnMissingBean(value={AssBalanceComputationModel.class})
    public AssBalanceComputationModel getAssBalanceComputationModel() {
        return new AssBalanceComputationModel();
    }

    @Bean
    @ConditionalOnMissingBean(value={AgingBalanceComputationModel.class})
    public AgingBalanceComputationModel getAgingBalanceComputationModel() {
        return new AgingBalanceComputationModel();
    }

    @Bean
    @ConditionalOnMissingBean(value={AssAgingBalanceComputationModel.class})
    public AssAgingBalanceComputationModel getAssAgingBalanceComputationModel() {
        return new AssAgingBalanceComputationModel();
    }

    @Bean
    @ConditionalOnMissingBean(value={AssCflBalanceComputationModel.class})
    public AssCflBalanceComputationModel getAssCflBalanceComputationModel() {
        return new AssCflBalanceComputationModel();
    }

    @Bean
    @ConditionalOnMissingBean(value={CflBalanceComputationModel.class})
    public CflBalanceComputationModel getCflBalanceComputationModel() {
        return new CflBalanceComputationModel();
    }

    @Bean
    @ConditionalOnMissingBean(value={DjyeBalanceComputationModel.class})
    public DjyeBalanceComputationModel getDjyeBalanceComputationModel() {
        return new DjyeBalanceComputationModel();
    }

    @Bean
    @ConditionalOnMissingBean(value={XjllBalanceComputationModel.class})
    public XjllBalanceComputationModel getXjllBalanceComputationModel() {
        return new XjllBalanceComputationModel();
    }

    @Bean
    @ConditionalOnMissingBean(value={VoucherComputationModel.class})
    public VoucherComputationModel getVoucherComputationModel() {
        return new VoucherComputationModel();
    }

    @Bean
    @ConditionalOnMissingBean(value={TfvComputationModel.class})
    public TfvComputationModel getTfvComputationModel() {
        return new TfvComputationModel();
    }

    @Bean
    @ConditionalOnMissingBean(value={CustomFetchComputationModel.class})
    public CustomFetchComputationModel getCustomComputationModel() {
        return new CustomFetchComputationModel();
    }

    @Bean
    @ConditionalOnMissingBean(value={BaseDataComputationModel.class})
    public BaseDataComputationModel getBaseDataComputationModel() {
        return new BaseDataComputationModel();
    }
}

