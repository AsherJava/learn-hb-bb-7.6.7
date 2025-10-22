/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.service.IJtableCheckMontiorServiceDefault
 *  com.jiuqi.nr.jtable.service.impl.JtableCheckMontiorServiceImpl
 */
package com.jiuqi.nr.dataentry.config;

import com.jiuqi.nr.dataentry.gather.ISingletonGather;
import com.jiuqi.nr.dataentry.gather.condition.CheckMonitorCondition;
import com.jiuqi.nr.dataentry.gather.condition.DefaultTemplateCondition;
import com.jiuqi.nr.dataentry.internal.service.DefaultTemplateConfigProvider;
import com.jiuqi.nr.dataentry.util.ExcelImportThreadCount;
import com.jiuqi.nr.jtable.service.IJtableCheckMontiorServiceDefault;
import com.jiuqi.nr.jtable.service.impl.JtableCheckMontiorServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataEntryPlugInUnitConfig {
    @Bean
    @Conditional(value={CheckMonitorCondition.class})
    public IJtableCheckMontiorServiceDefault checkMonitor() {
        JtableCheckMontiorServiceImpl jtableCheckMontiorServiceDefault = new JtableCheckMontiorServiceImpl();
        return jtableCheckMontiorServiceDefault;
    }

    @Bean
    @Conditional(value={DefaultTemplateCondition.class})
    public ISingletonGather<String> defaultTemplateConfigProvider() {
        DefaultTemplateConfigProvider defaultTemplateConfig = new DefaultTemplateConfigProvider();
        return defaultTemplateConfig;
    }

    @Bean
    public ExcelImportThreadCount getExcelImportThreadCount() {
        return new ExcelImportThreadCount();
    }
}

