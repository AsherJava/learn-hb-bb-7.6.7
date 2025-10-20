/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.EnableTransactionManagement
 */
package com.jiuqi.gcreport.multicriteria.autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScans(value={@ComponentScan(value={"com.jiuqi.gcreport.multicriteria.web"}), @ComponentScan(value={"com.jiuqi.gcreport.multicriteria.dao"}), @ComponentScan(value={"com.jiuqi.gcreport.multicriteria.entity"}), @ComponentScan(value={"com.jiuqi.gcreport.multicriteria.service"}), @ComponentScan(value={"com.jiuqi.gcreport.multicriteria.function"})})
public class MultiCriteriaAutoConfiguration {
}

