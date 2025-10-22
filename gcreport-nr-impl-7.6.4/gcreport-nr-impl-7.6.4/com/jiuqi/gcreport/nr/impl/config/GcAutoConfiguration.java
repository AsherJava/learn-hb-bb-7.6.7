/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.openfeign.EnableFeignClients
 */
package com.jiuqi.gcreport.nr.impl.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScans(value={@ComponentScan(value={"com.jiuqi.gcreport.nr.impl"})})
@EnableFeignClients(value={"com.jiuqi.gcreport.nr.api"})
public class GcAutoConfiguration {
}

