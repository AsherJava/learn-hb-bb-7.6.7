/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financialcubes;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.gc.financialcubes"})
@PropertySource(value={"classpath:financialcubes-shiro.properties"})
public class GcFinancialCubesAutoConfiguration {
}

