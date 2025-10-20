/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.integration.execute.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.dc.integration.execute.impl"})
@ConditionalOnExpression(value="#{'true'.equals(environment.getProperty('jiuqi.bde.server.standalone'))}")
public class DataIntegrationExecuteAutoConfiguration {
}

