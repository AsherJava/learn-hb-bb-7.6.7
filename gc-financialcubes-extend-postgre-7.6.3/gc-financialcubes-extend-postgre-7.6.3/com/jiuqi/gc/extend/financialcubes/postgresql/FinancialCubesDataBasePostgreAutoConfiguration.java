/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.extend.financialcubes.postgresql;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name={"spring.datasource.dbType"}, havingValue="postgresql")
@ComponentScan(basePackages={"com.jiuqi.gc.extend.financialcubes.postgresql"})
public class FinancialCubesDataBasePostgreAutoConfiguration {
}

