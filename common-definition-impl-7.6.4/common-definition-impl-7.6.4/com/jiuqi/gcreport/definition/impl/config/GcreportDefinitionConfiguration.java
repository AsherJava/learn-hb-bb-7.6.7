/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.config;

import com.jiuqi.gcreport.definition.impl.basic.config.GcReportDefinitionInnerConfiguration;
import com.jiuqi.gcreport.definition.impl.sqlutil.config.GcSQLConfiguration;
import com.jiuqi.gcreport.definition.impl.test.conf.GcTestDefineConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value={GcReportDefinitionInnerConfiguration.class, GcTestDefineConfig.class, GcSQLConfiguration.class})
public class GcreportDefinitionConfiguration {
}

