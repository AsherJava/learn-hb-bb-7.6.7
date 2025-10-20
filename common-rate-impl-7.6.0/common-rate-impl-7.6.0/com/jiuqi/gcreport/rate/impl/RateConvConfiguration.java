/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  tk.mybatis.spring.annotation.MapperScan
 */
package com.jiuqi.gcreport.rate.impl;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.gcreport.rate.impl"})
@MapperScan(basePackages={"com.jiuqi.gcreport.rate.impl.mapper"})
public class RateConvConfiguration {
}

