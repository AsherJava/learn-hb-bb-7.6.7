/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.nvwa.sf.Framework
 */
package com.jiuqi.dc.base.common.utils;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.nvwa.sf.Framework;
import java.util.List;

public class CommonUtil {
    static final int MAX_WAIT_MINUTE = 10;

    private CommonUtil() {
        throw new IllegalStateException();
    }

    public static String getAssBalanceTableName(int acctYear) {
        return "DC_PREASSBALANCE_" + acctYear;
    }

    public static String getVoucherTableName(int acctYear) {
        return "DC_VOUCHER_" + acctYear;
    }

    public static String getVoucherItemAssTableName(int acctYear) {
        return "DC_VOUCHERITEMASS_" + acctYear;
    }

    public static String getCfVoucherItemAssTableName(int acctYear) {
        return "DC_CFVOUCHERITEMASS_" + acctYear;
    }

    public static String getReclassifyProcessItemTable(int acctYear) {
        return "DC_CFLPROCESSITEM_" + acctYear;
    }

    public static String getReclassifyBalanceTable(int acctYear) {
        return "DC_CFLASSBALANCE_" + acctYear;
    }

    public static String getCfBalanceTableName(int acctYear) {
        return String.format("%1$s%2$d", "DC_CFBALANCE_", acctYear);
    }

    public static void listToString(List<String> stringList, StringBuilder stringBuilder) {
        if (null == stringList) {
            return;
        }
        for (String string : stringList) {
            stringBuilder.append("'");
            stringBuilder.append(string);
            stringBuilder.append("',");
        }
    }

    public static String lpad(String str, String prefix, int length) {
        if (StringUtils.isEmpty((String)str)) {
            return "";
        }
        if (str.length() >= length) {
            return str;
        }
        while (str.length() < length) {
            str = prefix + str;
        }
        return str;
    }

    public static boolean serverStartUp() {
        return Framework.getInstance().startSuccessful();
    }

    public static void waitServerStartUp() {
        for (int waitMinute = 0; !CommonUtil.serverStartUp() && waitMinute < 10; ++waitMinute) {
            try {
                Thread.sleep(60000L);
                continue;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }
}

