/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  tk.mybatis.spring.annotation.MapperScan
 */
package com.jiuqi.dc.base.impl.onlinePeriod.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.dc.base.impl.onlinePeriod"})
@MapperScan(basePackages={"com.jiuqi.dc.base.impl.onlinePeriod.mapper"})
public class OnlinePeriodAutoConfiguration {
}

