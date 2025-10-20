/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.annotation.ComponentStandaloneScan
 */
package com.jiuqi.bde.penetrate.impl;

import com.jiuqi.bde.common.annotation.ComponentStandaloneScan;
import com.jiuqi.bde.penetrate.impl.core.dao.IPenetrateContentBaseDao;
import com.jiuqi.bde.penetrate.impl.core.model.dao.PenetrateContentBaseDao;
import com.jiuqi.bde.penetrate.impl.gather.IPenetrateModelGather;
import com.jiuqi.bde.penetrate.impl.gather.impl.PenetrateModelGather;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentStandaloneScan(basePackages={"com.jiuqi.bde.penetrate.impl"})
public class BdePenetrateImplAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(value={IPenetrateContentBaseDao.class})
    public IPenetrateContentBaseDao getPenetrateContentBaseDao() {
        return new PenetrateContentBaseDao();
    }

    @Bean
    @ConditionalOnMissingBean(value={IPenetrateModelGather.class})
    public IPenetrateModelGather getPenetrateModelGather() {
        return new PenetrateModelGather();
    }
}

