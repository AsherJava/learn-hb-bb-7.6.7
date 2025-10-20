/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.ai.dify.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value={"classpath:bud-ai-dify.properties"})
@ComponentScan(basePackages={"com.jiuqi.budget.ai.dify"})
public class BudAiDifyConfig {
}

