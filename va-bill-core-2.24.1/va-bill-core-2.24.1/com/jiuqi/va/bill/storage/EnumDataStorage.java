/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.OrderNumUtil
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.feign.client.EnumDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.common.JDialectUtil
 *  com.jiuqi.va.mapper.common.JTableModel
 */
package com.jiuqi.va.bill.storage;

import com.jiuqi.va.domain.common.OrderNumUtil;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.feign.client.EnumDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.common.JDialectUtil;
import com.jiuqi.va.mapper.common.JTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnumDataStorage {
    private static final Logger log = LoggerFactory.getLogger(EnumDataStorage.class);

    public static void init(String tenantName) {
        JTableModel jtm = new JTableModel(tenantName, "ENUMDATA_INFO");
        JDialectUtil jDialect = JDialectUtil.getInstance();
        while (!jDialect.hasTable(jtm)) {
            try {
                Thread.sleep(5000L);
            }
            catch (InterruptedException e) {
                log.error(e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }
        EnumDataClient enumDataClient = (EnumDataClient)ApplicationContextRegister.getBean(EnumDataClient.class);
        String[] biztype = new String[]{"EM_BILLSTATE", "\u5355\u636e\u72b6\u6001"};
        EnumDataStorage.initEnumData(enumDataClient, biztype, new String[]{"\u5df2\u4fdd\u5b58", "0", "\u5355\u636e\u4fdd\u5b58\u52a8\u4f5c\u6267\u884c\u540e\u7684\u5355\u636e\u72b6\u6001"}, tenantName);
        EnumDataStorage.initEnumData(enumDataClient, biztype, new String[]{"\u5df2\u6682\u5b58", "1", "\u5355\u636e\u6682\u5b58\u52a8\u4f5c\u6267\u884c\u540e\u7684\u5355\u636e\u72b6\u6001"}, tenantName);
        EnumDataStorage.initEnumData(enumDataClient, biztype, new String[]{"\u5df2\u786e\u8ba4", "2", "\u5df2\u786e\u8ba4"}, tenantName);
        EnumDataStorage.initEnumData(enumDataClient, biztype, new String[]{"\u5df2\u5e9f\u6b62", "4", "\u5355\u636e\u5e9f\u6b62\u52a8\u4f5c\u6267\u884c\u540e\u7684\u5355\u636e\u72b6\u6001"}, tenantName);
        EnumDataStorage.initEnumData(enumDataClient, biztype, new String[]{"\u5df2\u63d0\u4ea4", "8", "\u5355\u636e\u63d0\u4ea4\u52a8\u4f5c\u6267\u884c\u540e\u7684\u5355\u636e\u72b6\u6001"}, tenantName);
        EnumDataStorage.initEnumData(enumDataClient, biztype, new String[]{"\u5ba1\u6279\u4e2d", "24", "\u5355\u636e\u540c\u610f\u52a8\u4f5c\u6267\u884c\u540e\u7684\u5355\u636e\u72b6\u6001"}, tenantName);
        EnumDataStorage.initEnumData(enumDataClient, biztype, new String[]{"\u9a73\u56de\u53ef\u63d0\u4ea4", "33", "\u5355\u636e\u9a73\u56de\u52a8\u4f5c\u6267\u884c\u540e\u7684\u5355\u636e\u72b6\u6001"}, tenantName);
        EnumDataStorage.initEnumData(enumDataClient, biztype, new String[]{"\u9a73\u56de\u4e0d\u53ef\u63d0\u4ea4", "68", "\u9a73\u56de\u4e0d\u53ef\u63d0\u4ea4"}, tenantName);
        EnumDataStorage.initEnumData(enumDataClient, biztype, new String[]{"\u5ba1\u6279\u901a\u8fc7\u53ef\u4fee\u6539", "129", "\u5171\u4eab\u5e73\u53f0\u5ba1\u6838\u5c97\u9a73\u56de\u4e0d\u91cd\u8d70\u5de5\u4f5c\u6d41\u52a8\u4f5c\u6267\u884c\u540e\u7684\u5355\u636e\u72b6\u6001"}, tenantName);
        EnumDataStorage.initEnumData(enumDataClient, biztype, new String[]{"\u5ba1\u6279\u901a\u8fc7", "130", "\u5355\u636e\u5de5\u4f5c\u6d41\u6d41\u7a0b\u5ba1\u6279\u7ed3\u675f\u540e\u7684\u5355\u636e\u72b6\u6001"}, tenantName);
        EnumDataStorage.initEnumData(enumDataClient, biztype, new String[]{"\u90e8\u5206\u901a\u8fc7", "258", "\u90e8\u5206\u901a\u8fc7"}, tenantName);
    }

    private static void initEnumData(EnumDataClient enumDataClient, String[] biz, String[] str, String tenantName) {
        EnumDataDO enumDataDO = new EnumDataDO();
        enumDataDO.setTenantName(tenantName);
        enumDataDO.setBiztype(biz[0]);
        enumDataDO.setDescription(biz[1]);
        enumDataDO.setTitle(str[0]);
        enumDataDO.setVal(str[1]);
        enumDataDO.setRemark(str[2]);
        enumDataDO.setOrdernum(OrderNumUtil.getOrderNumByCurrentTimeMillis());
        enumDataDO.setStatus(Integer.valueOf(0));
        enumDataClient.add(enumDataDO);
    }
}

