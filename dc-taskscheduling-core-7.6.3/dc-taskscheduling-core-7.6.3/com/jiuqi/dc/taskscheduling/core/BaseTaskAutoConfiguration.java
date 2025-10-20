/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  tk.mybatis.spring.annotation.MapperScan
 */
package com.jiuqi.dc.taskscheduling.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.dc.taskscheduling.core"})
@MapperScan(basePackages={"com.jiuqi.dc.taskscheduling.core.mapper"})
public class BaseTaskAutoConfiguration {
}

