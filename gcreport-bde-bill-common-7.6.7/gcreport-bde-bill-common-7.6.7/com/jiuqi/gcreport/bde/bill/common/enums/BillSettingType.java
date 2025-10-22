/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.bde.bill.common.enums;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;

public enum BillSettingType {
    BILL("BILL", "\u5355\u636e"),
    BILLLIST("BILLLIST", "\u5355\u636e\u5217\u8868");

    private final String code;
    private final String name;

    private BillSettingType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static BillSettingType getEnumByCode(String code) {
        if (StringUtils.isEmpty((String)code)) {
            return null;
        }
        BillSettingType val = BillSettingType.findEnumByCode(code);
        if (val == null) {
            throw new BusinessRuntimeException(String.format("\u5355\u636e\u8bbe\u7f6e\u7c7b\u578b\u3010%1$s\u3011\u6ca1\u6709\u5bf9\u5e94\u7684\u6570\u636e\u9879", code));
        }
        return val;
    }

    private static BillSettingType findEnumByCode(String code) {
        if (StringUtils.isEmpty((String)code)) {
            return null;
        }
        for (BillSettingType BillSettingType2 : BillSettingType.values()) {
            if (!BillSettingType2.code.equals(code.toUpperCase())) continue;
            return BillSettingType2;
        }
        return null;
    }

    public static BillSettingType getOppBillType(String code) {
        BillSettingType settingType = BillSettingType.getEnumByCode(code);
        return BILL == settingType ? BILLLIST : BILL;
    }
}

