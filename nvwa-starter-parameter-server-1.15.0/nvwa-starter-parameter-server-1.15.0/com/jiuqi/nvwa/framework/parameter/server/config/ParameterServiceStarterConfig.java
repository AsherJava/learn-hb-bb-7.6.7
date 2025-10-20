/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ComponentScan(basePackages={"com.jiuqi.nvwa.framework.parameter", "com.jiuqi.nvwa.datav.parameter.remote", "com.jiuqi.nvwa.dataanalysis.bi.config.systemoption"})
@Configuration
@PropertySource(value={"classpath:parameter-whiteList.properties"})
public class ParameterServiceStarterConfig {
}

