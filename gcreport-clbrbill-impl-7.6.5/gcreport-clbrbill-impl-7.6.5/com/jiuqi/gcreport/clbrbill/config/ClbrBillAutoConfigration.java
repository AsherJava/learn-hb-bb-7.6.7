/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.retry.annotation.EnableRetry
 */
package com.jiuqi.gcreport.clbrbill.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@ComponentScan(value={"com.jiuqi.gcreport.clbrbill.**"})
@PropertySource(value={"classpath:gcreport-clbrbill.properties"})
@EnableRetry
public class ClbrBillAutoConfigration {
}

