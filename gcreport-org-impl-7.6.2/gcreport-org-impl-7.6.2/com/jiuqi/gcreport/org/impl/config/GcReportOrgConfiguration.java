/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.org.impl.config;

import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.impl.cache.service.FGcOrgEditService;
import com.jiuqi.gcreport.org.impl.cache.service.FGcOrgQueryService;
import com.jiuqi.gcreport.org.impl.cache.service.impl.GcOrgEditServiceImpl;
import com.jiuqi.gcreport.org.impl.cache.service.impl.GcOrgQueryCacheServiceImpl;
import com.jiuqi.gcreport.org.impl.cache.service.impl.GcOrgQueryServiceImpl;
import com.jiuqi.gcreport.org.impl.init.GcTableConstructor;
import com.jiuqi.gcreport.org.impl.util.bean.DeployGcOrgModelConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.gcreport.org.impl"})
@Import(value={DeployGcOrgModelConfiguration.class})
public class GcReportOrgConfiguration {
    @Bean
    @ConditionalOnMissingBean(value={GcTableConstructor.class})
    public GcTableConstructor initGcTableConstructor() {
        return new GcTableConstructor();
    }

    @ConditionalOnProperty(name={"jiuqi.gcreport.env.orglocalcache"}, havingValue="true", matchIfMissing=true)
    @Bean(value={"OrgQueryService"})
    public FGcOrgQueryService<GcOrgCacheVO> initOrgCacheQueryService() {
        return new GcOrgQueryCacheServiceImpl();
    }

    @ConditionalOnProperty(name={"jiuqi.gcreport.env.orglocalcache"}, havingValue="false")
    @Bean(value={"OrgQueryService"})
    public FGcOrgQueryService<GcOrgCacheVO> initOrgQueryService() {
        return new GcOrgQueryServiceImpl();
    }

    @Bean(value={"com.jiuqi.gcreport.org.impl.cache.dao.FGcOrgEditDao"})
    public FGcOrgEditService initEditService() {
        return new GcOrgEditServiceImpl();
    }
}

