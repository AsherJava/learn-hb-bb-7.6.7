/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.EnableTransactionManagement
 */
package com.jiuqi.gcreport.reportdatasync.autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScans(value={@ComponentScan(basePackages={"com.jiuqi.gcreport.reportdatasync"})})
@PropertySource(value={"classpath:reportDataSyncMulti.properties"})
public class ReportDataSyncAutoConfiguration {
}

