/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.archive.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jmx.support.RegistrationPolicy;

@Configuration
@EnableMBeanExport(registration=RegistrationPolicy.IGNORE_EXISTING)
@ComponentScan(basePackages={"com.jiuqi.gcreport.archive"})
@PropertySource(value={"classpath:gc-efs.properties"})
public class ArchiveImplAutoConfiguration {
}

