/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.EnableTransactionManagement
 */
package com.jiuqi.gcreport.lease.autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScans(value={@ComponentScan(value={"com.jiuqi.gcreport.lease.calculate.rule"}), @ComponentScan(value={"com.jiuqi.gcreport.lease.datatrace"}), @ComponentScan(value={"com.jiuqi.gcreport.lease.formula"}), @ComponentScan(value={"com.jiuqi.gcreport.lease.leasebill"}), @ComponentScan(value={"com.jiuqi.gcreport.lease.rule"}), @ComponentScan(value={"com.jiuqi.gcreport.lease.consts"}), @ComponentScan(value={"com.jiuqi.gcreport.lease.reportsync"})})
@EnableTransactionManagement
public class GcLeaseBillAutoConfiguration {
}

