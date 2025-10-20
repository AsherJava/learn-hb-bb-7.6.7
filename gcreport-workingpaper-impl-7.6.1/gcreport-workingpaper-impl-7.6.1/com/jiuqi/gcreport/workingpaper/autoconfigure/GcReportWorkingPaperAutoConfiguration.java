/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.EnableTransactionManagement
 */
package com.jiuqi.gcreport.workingpaper.autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages={"com.jiuqi.gcreport.workingpaper"})
@PropertySource(value={"classpath:gcreport-workingpaper.properties"})
public class GcReportWorkingPaperAutoConfiguration {
}

