/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.config.CacheManagerConfiguration
 *  com.jiuqi.np.cache.config.CacheManagerProperties
 *  com.jiuqi.np.cache.config.CacheType
 *  com.jiuqi.np.cache.config.NpCacheProperties
 */
package com.jiuqi.nr.common.cachemanager;

import com.jiuqi.np.cache.config.CacheManagerConfiguration;
import com.jiuqi.np.cache.config.CacheManagerProperties;
import com.jiuqi.np.cache.config.CacheType;
import com.jiuqi.np.cache.config.NpCacheProperties;
import com.jiuqi.nr.common.cachemanager.DeployEnv;
import com.jiuqi.nr.common.cachemanager.DeployEnvCacheManagerProperties;
import com.jiuqi.nr.common.cachemanager.IDeployEnvCfg;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Order(value=-2147483643)
public class DeployEnvCfgImpl
implements IDeployEnvCfg,
BeanPostProcessor {
    private static final Logger log = LoggerFactory.getLogger(DeployEnvCfgImpl.class);
    @Autowired
    private NpCacheProperties cacheProperties;
    @Autowired(required=false)
    private List<CacheManagerConfiguration> buildinCacheManagerConfiguration;
    @Autowired(required=false)
    private List<DeployEnvCacheManagerProperties> deployEnvCacheManagerProperties;
    @Value(value="${jiuqi.nr.app.deploy-env:#{null}}")
    private String deployEvn;

    @Override
    public void init() {
        if (!StringUtils.isEmpty(this.deployEvn)) {
            DeployEnv[] constants = (DeployEnv[])DeployEnv.class.getEnumConstants();
            boolean defaultCfg = true;
            for (DeployEnv deploy : constants) {
                if (!this.deployEvn.toLowerCase().contains(deploy.toString())) continue;
                this.deployEvn = deploy.toString();
                defaultCfg = false;
                break;
            }
            if (defaultCfg) {
                return;
            }
            for (DeployEnvCacheManagerProperties ccmf : this.deployEnvCacheManagerProperties) {
                ccmf.setProperties(ccmf.getProperties(), DeployEnv.valueOf(this.deployEvn));
            }
            for (CacheManagerConfiguration ccmf : this.buildinCacheManagerConfiguration) {
                log.info("CacheName\u4e3a{}-->\u7684type\u503c\u4e3a:{}", (Object)ccmf.getProperties().getName(), (Object)ccmf.getProperties().getType());
            }
            if (this.deployEvn.equals(DeployEnv.SINGLE.name())) {
                this.cacheProperties.setType(CacheType.local);
                List managers = this.cacheProperties.getManagers();
                for (CacheManagerProperties cmp : managers) {
                    this.setLocation(cmp);
                }
                for (CacheManagerConfiguration ccmf : this.buildinCacheManagerConfiguration) {
                    ccmf.getProperties().setType(CacheType.local);
                }
            }
        }
    }

    private void setLocation(CacheManagerProperties cmp) {
        DeployEnv env = DeployEnv.valueOf(this.deployEvn);
        switch (env) {
            case SINGLE: {
                cmp.setType(CacheType.local);
                break;
            }
            case CLUSTER: {
                cmp.setType(CacheType.redis);
                break;
            }
            case SAAS: {
                cmp.setType(CacheType.rediscaffeine);
                break;
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}

