/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.openfeign.EnableFeignClients
 */
package com.jiuqi.common.file.autoconfigure;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value={"com.jiuqi.common.file.client"})
@EnableFeignClients(value={"com.jiuqi.common.file.client"})
public class CommonFileClientAutoConfiguration {
}

