/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.EnableTransactionManagement
 */
package com.jiuqi.budget.autoconfigure;

import com.jiuqi.budget.init.BudInitialization;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.budget"}, includeFilters={@ComponentScan.Filter(value={BudInitialization.class})})
@ServletComponentScan(basePackages={"com.jiuqi.budget"})
@EnableAsync
@EnableTransactionManagement
public class BudConfig {
}

