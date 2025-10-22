/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.EnableTransactionManagement
 */
package com.jiuqi.gcreport.invest.autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScans(value={@ComponentScan(value={"com.jiuqi.gcreport.invest.calculate"}), @ComponentScan(value={"com.jiuqi.gcreport.invest.consolidatedsystem"}), @ComponentScan(value={"com.jiuqi.gcreport.invest.common"}), @ComponentScan(value={"com.jiuqi.gcreport.invest.formula"}), @ComponentScan(value={"com.jiuqi.gcreport.invest.investbill"}), @ComponentScan(value={"com.jiuqi.gcreport.invest.investworkpaper"}), @ComponentScan(value={"com.jiuqi.gcreport.invest.monthcalcscheme"}), @ComponentScan(value={"com.jiuqi.gcreport.invest.calculate.rule"}), @ComponentScan(value={"com.jiuqi.gcreport.invest.datatrace"}), @ComponentScan(value={"com.jiuqi.gcreport.invest.investbillcarryover"}), @ComponentScan(value={"com.jiuqi.gcreport.invest.offsetitem"}), @ComponentScan(value={"com.jiuqi.gcreport.invest.reportsync"}), @ComponentScan(value={"com.jiuqi.gcreport.invest.relation"}), @ComponentScan(value={"com.jiuqi.gcreport.invest.fetchdata"}), @ComponentScan(value={"com.jiuqi.gcreport.invest.restapi"})})
@EnableTransactionManagement
public class GcInvestBillAutoConfiguration {
}

