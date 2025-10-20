/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.feign.client.EnumDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.dc.bill.storage;

import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.feign.client.EnumDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;

public class DcBillAcctPeriodEnumStorage {
    public static void init(String tenantName) {
        EnumDataClient enumDataClient = (EnumDataClient)ApplicationContextRegister.getBean(EnumDataClient.class);
        String[] biztype = new String[]{"EM_ACCTPERIOD", "\u65f6\u671f"};
        DcBillAcctPeriodEnumStorage.initEnumData(enumDataClient, biztype, new String[]{"\u7b2c1\u671f", "1"}, tenantName);
        DcBillAcctPeriodEnumStorage.initEnumData(enumDataClient, biztype, new String[]{"\u7b2c2\u671f", "2"}, tenantName);
        DcBillAcctPeriodEnumStorage.initEnumData(enumDataClient, biztype, new String[]{"\u7b2c3\u671f", "3"}, tenantName);
        DcBillAcctPeriodEnumStorage.initEnumData(enumDataClient, biztype, new String[]{"\u7b2c4\u671f", "4"}, tenantName);
        DcBillAcctPeriodEnumStorage.initEnumData(enumDataClient, biztype, new String[]{"\u7b2c5\u671f", "5"}, tenantName);
        DcBillAcctPeriodEnumStorage.initEnumData(enumDataClient, biztype, new String[]{"\u7b2c6\u671f", "6"}, tenantName);
        DcBillAcctPeriodEnumStorage.initEnumData(enumDataClient, biztype, new String[]{"\u7b2c7\u671f", "7"}, tenantName);
        DcBillAcctPeriodEnumStorage.initEnumData(enumDataClient, biztype, new String[]{"\u7b2c8\u671f", "8"}, tenantName);
        DcBillAcctPeriodEnumStorage.initEnumData(enumDataClient, biztype, new String[]{"\u7b2c9\u671f", "9"}, tenantName);
        DcBillAcctPeriodEnumStorage.initEnumData(enumDataClient, biztype, new String[]{"\u7b2c10\u671f", "10"}, tenantName);
        DcBillAcctPeriodEnumStorage.initEnumData(enumDataClient, biztype, new String[]{"\u7b2c11\u671f", "11"}, tenantName);
        DcBillAcctPeriodEnumStorage.initEnumData(enumDataClient, biztype, new String[]{"\u7b2c12\u671f", "12"}, tenantName);
    }

    private static void initEnumData(EnumDataClient enumDataClient, String[] biz, String[] str, String tenantName) {
        EnumDataDO enumDataDO = new EnumDataDO();
        enumDataDO.setTenantName(tenantName);
        enumDataDO.setBiztype(biz[0]);
        enumDataDO.setDescription(biz[1]);
        enumDataDO.setTitle(str[0]);
        enumDataDO.setVal(str[1]);
        enumDataDO.setOrdernum(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        enumDataDO.setStatus(Integer.valueOf(0));
        enumDataClient.add(enumDataDO);
    }
}

