/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.openfeign.EnableFeignClients
 */
package com.jiuqi.np.user.feign.config;

import com.jiuqi.np.user.feign.client.NvwaPasswordClient;
import com.jiuqi.np.user.feign.client.NvwaSystemUserClient;
import com.jiuqi.np.user.feign.client.NvwaUserAttributeClient;
import com.jiuqi.np.user.feign.client.NvwaUserAvatarClient;
import com.jiuqi.np.user.feign.client.NvwaUserClient;
import com.jiuqi.np.user.feign.client.NvwaUserOperationClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableFeignClients(clients={NvwaPasswordClient.class, NvwaSystemUserClient.class, NvwaUserAttributeClient.class, NvwaUserAvatarClient.class, NvwaUserClient.class, NvwaUserOperationClient.class})
@ComponentScan(basePackages={"com.jiuqi.np.user.feign"})
@PropertySource(value={"classpath:nvwa-user-feign.properties"})
public class NvwaUserFeignConfig {
}

