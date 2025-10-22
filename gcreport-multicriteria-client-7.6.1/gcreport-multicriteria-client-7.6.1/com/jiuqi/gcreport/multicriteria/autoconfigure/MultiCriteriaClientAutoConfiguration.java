/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.openfeign.EnableFeignClients
 *  org.springframework.transaction.annotation.EnableTransactionManagement
 */
package com.jiuqi.gcreport.multicriteria.autoconfigure;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(value={"com.jiuqi.gcreport.multicriteria.client"})
@EnableFeignClients(value={"com.jiuqi.gcreport.multicriteria.client"})
@EnableTransactionManagement
public class MultiCriteriaClientAutoConfiguration {
}

