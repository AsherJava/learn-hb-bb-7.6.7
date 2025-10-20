/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.nvwa.sf.adapter.spring.naming.nacos;

import com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

@Order(value=-2147483648)
public class NacosConfig
implements ApplicationRunner {
    private NacosAutoServiceRegistration registration;
    @Value(value="${server.port}")
    Integer port;

    public NacosConfig(NacosAutoServiceRegistration registration) {
        this.registration = registration;
    }

    @Override
    public void run(ApplicationArguments args) {
        String nacosPortStr = SpringBeanUtils.getApplicationContext().getEnvironment().getProperty("spring.cloud.nacos.discovery.port");
        Integer nacosPort = null;
        if (StringUtils.isNotEmpty((String)nacosPortStr)) {
            nacosPort = Integer.parseInt(nacosPortStr);
        }
        if (this.registration != null) {
            if (nacosPort != null) {
                this.registration.setPort(nacosPort.intValue());
                this.registration.start();
            } else if (this.port != null) {
                this.registration.setPort(this.port.intValue());
                this.registration.start();
            }
        }
    }
}

