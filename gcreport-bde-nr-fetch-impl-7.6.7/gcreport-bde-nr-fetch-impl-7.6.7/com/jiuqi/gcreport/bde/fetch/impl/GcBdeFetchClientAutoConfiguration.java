/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetch.impl;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.gcreport.bde.fetch.impl"})
@PropertySource(value={"classpath:bde-fetch.properties"})
public class GcBdeFetchClientAutoConfiguration {
}

