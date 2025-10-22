/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.EnableTransactionManagement
 */
package com.jiuqi.gcreport.asset.autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScans(value={@ComponentScan(value={"com.jiuqi.gcreport.asset.assetbill"}), @ComponentScan(value={"com.jiuqi.gcreport.asset.calculate.rule"}), @ComponentScan(value={"com.jiuqi.gcreport.asset.datatrace"}), @ComponentScan(value={"com.jiuqi.gcreport.asset.formula"}), @ComponentScan(value={"com.jiuqi.gcreport.asset.consts"}), @ComponentScan(value={"com.jiuqi.gcreport.asset.reportsync"})})
@EnableTransactionManagement
public class GcAssetBillAutoConfiguration {
}

