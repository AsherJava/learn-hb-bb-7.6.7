/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.dc.taskscheduling.api"})
@PropertySource(value={"classpath:taskhandler.properties"})
public class DcTaskClientAutoConfiguration {
}

