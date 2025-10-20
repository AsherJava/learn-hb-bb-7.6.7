/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel
 *  com.jiuqi.bde.bizmodel.client.intf.IBizDataModel
 */
package com.jiuqi.bde.bizmodel.define;

import com.jiuqi.bde.bizmodel.client.intf.IBizComputationModel;
import com.jiuqi.bde.bizmodel.client.intf.IBizDataModel;
import com.jiuqi.bde.bizmodel.define.IBizModelExecute;
import com.jiuqi.bde.bizmodel.define.gather.IBizModelGather;
import com.jiuqi.bde.bizmodel.define.gather.impl.BdeBizModelGather;
import com.jiuqi.bde.bizmodel.define.gather.impl.BizModelExecuteGather;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.bde.bizmodel.define"})
public class BdeBizModelDefineAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(value={IBizModelGather.class})
    @Lazy
    public IBizModelGather getBizModelGather(@Autowired(required=false) List<IBizDataModel> registeredDataModelList, @Autowired(required=false) List<IBizComputationModel> registeredComputationModelList) {
        return new BdeBizModelGather(registeredDataModelList, registeredComputationModelList);
    }

    @Bean
    @ConditionalOnMissingBean(value={IBizModelGather.class})
    @Lazy
    public BizModelExecuteGather getBizModelExecuteGather(@Autowired(required=false) List<IBizModelExecute> registeredModelExecuteList) {
        return new BizModelExecuteGather(registeredModelExecuteList);
    }
}

