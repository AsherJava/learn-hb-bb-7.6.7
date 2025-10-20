/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.openfeign.EnableFeignClients
 */
package com.jiuqi.common.datasync.autoconfigure;

import com.jiuqi.common.datasync.properties.CommonDataSyncProperties;
import java.util.Objects;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(value={"com.jiuqi.common.datasync"})
@ComponentScan(value={"com.jiuqi.common.datasync"})
@EnableConfigurationProperties(value={CommonDataSyncProperties.class})
public class CommonDataSyncAutoConfiguration {
    private CommonDataSyncProperties dataSyncProperties;

    public CommonDataSyncAutoConfiguration(CommonDataSyncProperties dataSyncProperties) {
        Objects.requireNonNull(dataSyncProperties.getServiceName(), "\u6570\u636e\u540c\u6b65\u961f\u5217\u540d\u79f0\u4e0d\u5141\u8bb8\u4e3a\u7a7a\uff0c\u8bf7\u68c0\u67e5\u3010spring.application.name\u3011\u53c2\u6570\u914d\u7f6e\u3002");
        this.dataSyncProperties = dataSyncProperties;
    }

    public CommonDataSyncProperties getDataSyncProperties() {
        return this.dataSyncProperties;
    }

    public void setDataSyncProperties(CommonDataSyncProperties dataSyncProperties) {
        this.dataSyncProperties = dataSyncProperties;
    }
}

