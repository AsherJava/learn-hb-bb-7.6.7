/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.EnableTransactionManagement
 */
package com.jiuqi.gcreport.billcore.autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScans(value={@ComponentScan(value={"com.jiuqi.gcreport.billcore.common"}), @ComponentScan(value={"com.jiuqi.gcreport.billcore.dao"}), @ComponentScan(value={"com.jiuqi.gcreport.billcore.dto"}), @ComponentScan(value={"com.jiuqi.gcreport.billcore.entity"}), @ComponentScan(value={"com.jiuqi.gcreport.billcore.enums"}), @ComponentScan(value={"com.jiuqi.gcreport.billcore.fetchdata"}), @ComponentScan(value={"com.jiuqi.gcreport.billcore.formula"}), @ComponentScan(value={"com.jiuqi.gcreport.billcore.reportsync"}), @ComponentScan(value={"com.jiuqi.gcreport.billcore.service"}), @ComponentScan(value={"com.jiuqi.gcreport.billcore.task"}), @ComponentScan(value={"com.jiuqi.gcreport.billcore.transaction"}), @ComponentScan(value={"com.jiuqi.gcreport.billcore.util"}), @ComponentScan(value={"com.jiuqi.gcreport.billcore.vo"}), @ComponentScan(value={"com.jiuqi.gcreport.billcore.web"}), @ComponentScan(value={"com.jiuqi.gcreport.billcore.model"}), @ComponentScan(value={"com.jiuqi.gcreport.billcore.offsetcheck"})})
@EnableTransactionManagement
@Lazy(value=false)
@DependsOn(value={"com.jiuqi.va.bizmeta.domain.multimodule.Modules", "com.jiuqi.va.formula.provider.ModelFunctionProvider", "com.jiuqi.va.biz.utils.BaseDataUtils", "com.jiuqi.va.biz.utils.BizBindingI18nUtil"})
public class GcBillCoreAutoConfiguration {
}

