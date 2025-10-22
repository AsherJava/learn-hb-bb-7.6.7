/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.parallel.config.ParallelAutoConfiguration
 */
package com.jiuqi.nr.dataentry.config;

import com.jiuqi.nr.parallel.config.ParallelAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan(basePackages={"com.jiuqi.nr.dataentry.gather.impl", "com.jiuqi.nr.dataentry.web", "com.jiuqi.nr.dataentry.internal", "com.jiuqi.nr.dataentry.i18n", "com.jiuqi.nr.dataentry.deploy", "com.jiuqi.nr.dataentry.readwrite.impl", "com.jiuqi.nr.dataentry.readwrite.aop", "com.jiuqi.nr.dataentry.plantask", "com.jiuqi.nr.dataentry.funcVerificated", "com.jiuqi.nr.dataentry.asynctask", "com.jiuqi.nr.dataentry.config", "com.jiuqi.nr.dataentry.nlpr", "com.jiuqi.nr.dataentry.options", "com.jiuqi.nr.dataentry.print.impl", "com.jiuqi.nr.dataentry.util.entityUtil", "com.jiuqi.nr.dataentry.provider", "com.jiuqi.nr.dataentry.attachment.service.impl", "com.jiuqi.nr.dataentry.func", "com.jiuqi.nr.dataentry.report", "com.jiuqi.nr.dataentry.file", "com.jiuqi.nr.dataentry.copydes", "com.jiuqi.nr.dataentry.annotation", "com.jiuqi.nr.dataentry.aspect", "com.jiuqi.nr.dataentry.filter", "com.jiuqi.nr.dataentry.lockDetail", "com.jiuqi.nr.dataentry.util.authUtil", "com.jiuqi.nr.dataentry.taskOption", "com.jiuqi.nr.dataentry.templTransfer"})
@Configuration
@AutoConfigureAfter(value={ParallelAutoConfiguration.class})
@Import(value={ParallelAutoConfiguration.class})
public class DataEntryConfig {
}

