/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.adjustvchr.impl.enums;

public enum AdjustVoucherColumnEnum {
    INDEXNUM("INDEXNUM", "\u5e8f\u53f7", false, "\u975e\u5fc5\u586b\uff0c\u53ea\u7528\u4e8e\u6807\u8bb0\u51ed\u8bc1\u884c\u53f7\uff0c\u5bfc\u5165\u65f6\u5c06\u5ffd\u7565\u6b64\u5217", "", ""),
    UNITCODE("UNITCODE", "\u7ec4\u7ec7\u673a\u6784", true, "\u5fc5\u586b\uff0c\u8f93\u5165\u8c03\u6574\u51ed\u8bc1\u6240\u5c5e\u7ec4\u7ec7\u673a\u6784\u7684\u4ee3\u7801\u3001\u540d\u79f0\u6216\u8005\u4ee3\u7801|\u540d\u79f0", "JQ01", "JQ01"),
    VCHRNUM("VCHRNUM", "\u51ed\u8bc1\u7f16\u53f7", true, "\u5fc5\u586b\uff0c\u4e00\u4e2a\u51ed\u8bc1\u4e00\u4e2a\u7f16\u53f7\uff0c10\u4f4d\uff0c\u5e74\u6708+\u56db\u4f4d\u6d41\u6c34\u53f7\uff0c\u5982\uff1a2023030001", "2023030001", "2023030001"),
    ADJUSTTYPE("ADJUSTTYPE", "\u8c03\u6574\u7c7b\u578b", true, "\u5fc5\u586b\uff0c\u8c03\u6574\u51ed\u8bc1\u7c7b\u578b\u7684\u4ee3\u7801\u6216\u8005\u540d\u79f0\u6216\u8005\u4ee3\u7801|\u540d\u79f0", "\u603b\u8d26\u8c03\u6574", "\u603b\u8d26\u8c03\u6574"),
    STARTPERIOD("STARTPERIOD", "\u8c03\u6574\u671f\u95f4_\u8d77", true, "\u5fc5\u586b\uff0c\u5404\u671f\u95f4\u7c7b\u578b\u4e0b\u8c03\u6574\u51ed\u8bc1\u7684\u8d77\u59cb\u548c\u622a\u6b62\u65f6\u671f\uff1a\r\n\u671f\u95f4\u7c7b\u578b\u4e3a\u201c\u6708\u201d\u65f6\uff0c\u8f93\u5165\u8303\u56f4\u201c1-12\u201d\uff0c\u4f8b\u5982\u8f93\u5165\u201c5\u6708\u201d\uff1b\r\n\u671f\u95f4\u7c7b\u578b\u4e3a\u201c\u5b63\u201d\u65f6\uff0c\u8f93\u5165\u8303\u56f4\u201c1\u5b63\u5ea6-4\u5b63\u5ea6\u201d\uff0c\u4f8b\u5982\u8f93\u5165\u201c3\u5b63\u5ea6\u201d\uff1b\r\n\u671f\u95f4\u7c7b\u578b\u4e3a\u201c\u534a\u5e74\u201d\u65f6\uff0c\u8f93\u5165\u201c\u4e0a\u534a\u5e74\u201d\u6216\u8005\u4e0b\u534a\u5e74\uff0c\u4f8b\u5982\u8f93\u5165\u201c\u4e0b\u534a\u5e74\u201d\uff1b\r\n\u671f\u95f4\u7c7b\u578b\u4e3a\u201c\u5e74\u201d\u65f6\uff0c\u8f93\u5165\u8c03\u6574\u51ed\u8bc1\u7684\u5e74\u4efd\uff0c\u4f8b\u5982\u8f93\u5165\u201c2023\u5e74\u201d", "1\u6708", "1\u6708"),
    ENDPERIOD("ENDPERIOD", "\u8c03\u6574\u671f\u95f4_\u6b62", true, "\u5fc5\u586b\uff0c\u5404\u671f\u95f4\u7c7b\u578b\u4e0b\u8c03\u6574\u51ed\u8bc1\u7684\u8d77\u59cb\u548c\u622a\u6b62\u65f6\u671f\uff1a\r\n\u671f\u95f4\u7c7b\u578b\u4e3a\u201c\u6708\u201d\u65f6\uff0c\u8f93\u5165\u8303\u56f4\u201c1\u6708-12\u6708\u201d\uff0c\u4f8b\u5982\u8f93\u5165\u201c5\u6708\u201d\uff1b\r\n\u671f\u95f4\u7c7b\u578b\u4e3a\u201c\u5b63\u201d\u65f6\uff0c\u8f93\u5165\u8303\u56f4\u201c1\u5b63\u5ea6-4\u5b63\u5ea6\u201d\uff0c\u4f8b\u5982\u8f93\u5165\u201c3\u5b63\u5ea6\u201d\uff1b\r\n\u671f\u95f4\u7c7b\u578b\u4e3a\u201c\u534a\u5e74\u201d\u65f6\uff0c\u8f93\u5165\u201c\u4e0a\u534a\u5e74\u201d\u6216\u8005\u4e0b\u534a\u5e74\uff0c\u4f8b\u5982\u8f93\u5165\u201c\u4e0b\u534a\u5e74\u201d\uff1b\r\n\u671f\u95f4\u7c7b\u578b\u4e3a\u201c\u5e74\u201d\u65f6\uff0c\u8f93\u5165\u8c03\u6574\u51ed\u8bc1\u7684\u5e74\u4efd\uff0c\u4f8b\u5982\u8f93\u5165\u201c2023\u5e74\u201d", "12\u6708", "12\u6708"),
    ITEMORDER("ITEMORDER", "\u51ed\u8bc1\u884c\u53f7", true, "\u5fc5\u586b\uff0c\u540c\u4e00\u4e2a\u51ed\u8bc1\u7f16\u53f7\u4e0b\u6309\u884c\u6570\u6392\u5e8f", "1", "2"),
    SUBJECT("SUBJECT", "\u79d1\u76ee", true, "\u5fc5\u586b\uff0c\u8f93\u5165\u672b\u7ea7\u79d1\u76ee\u4ee3\u7801\u6216\u672b\u7ea7\u79d1\u76ee\u540d\u79f0\u6216\u672b\u7ea7\u79d1\u76ee\u4ee3\u7801|\u540d\u79f0", "100101", "112201"),
    DIGEST("DIGEST", "\u6458\u8981", false, "\u975e\u5fc5\u586b\uff0c\u8f93\u5165\u6458\u8981", "", ""),
    REMARK("REMARK", "\u5907\u6ce8", false, "\u975e\u5fc5\u586b\uff0c\u8f93\u5165\u5907\u6ce8", "", ""),
    CURRENCYCODE("CURRENCYCODE", "\u4ea4\u6613\u5e01\u79cd", false, "\u975e\u5fc5\u586b\uff0c\u5f55\u5165\u539f\u5e01\u5e01\u79cd\u4ee3\u7801\u6216\u8005\u539f\u5e01\u5e01\u79cd\u540d\u79f0\u6216\u539f\u5e01\u5e01\u79cd\u4ee3\u7801|\u540d\u79f0", "HKD", ""),
    EXCHANGERATE("EXCHANGERATE", "\u6c47\u7387", false, "\u975e\u5fc5\u586b\uff0c\u6709\u539f\u5e01\u65f6\u586b\u5199\u6c47\u7387", "0.127400", ""),
    ORGND("ORGND", "\u539f\u5e01\u501f\u65b9\u91d1\u989d", false, "\u51ed\u8bc1\u539f\u5e01\u501f\u65b9\u91d1\u989d\uff0c\u975e\u5fc5\u586b", "784.76", ""),
    ORGNC("ORGNC", "\u539f\u5e01\u8d37\u65b9\u91d1\u989d", false, "\u51ed\u8bc1\u539f\u5e01\u8d37\u65b9\u91d1\u989d\uff0c\u975e\u5fc5\u586b", "", ""),
    DEBIT("DEBIT", "\u672c\u4f4d\u5e01\u501f\u65b9\u91d1\u989d", false, "\u51ed\u8bc1\u672c\u4f4d\u5e01\u501f\u65b9\u91d1\u989d\uff0c\u975e\u5fc5\u586b", "99.98", ""),
    CREDIT("CREDIT(", "\u672c\u4f4d\u5e01\u8d37\u65b9\u91d1\u989d", false, "\u51ed\u8bc1\u672c\u4f4d\u5e01\u8d37\u65b9\u91d1\u989d\uff0c\u975e\u5fc5\u586b", "", "99.98"),
    EXTRAFIELDS("EXTRAFIELDS", "\u62d3\u5c55\u5217", false, "", "", ""),
    ASSISTSTR("ASSISTSTR", "\u8f85\u52a9\u9879", false, "\u8c03\u6574\u51ed\u8bc1\u8f85\u52a9\u7ef4\u5ea6\u4fe1\u606f\uff0c\u6839\u636e\u79d1\u76ee\u8bbe\u7f6e\u7684\u8f85\u52a9\u9879\u586b\u5199\u5bf9\u5e94\u5217", "", "");

    private String code;
    private String name;
    private Boolean required;
    private String remark;
    private String example1;
    private String example2;

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public Boolean getRequired() {
        return this.required;
    }

    public String getRemark() {
        return this.remark;
    }

    public String getExample1() {
        return this.example1;
    }

    public String getExample2() {
        return this.example2;
    }

    private AdjustVoucherColumnEnum(String code, String name, Boolean required, String remark, String example1, String example2) {
        this.code = code;
        this.name = name;
        this.required = required;
        this.remark = remark;
        this.example1 = example1;
        this.example2 = example2;
    }

    public static AdjustVoucherColumnEnum getColumnByCode(String code) {
        for (AdjustVoucherColumnEnum adjustVoucherColumnEnum : AdjustVoucherColumnEnum.values()) {
            if (!adjustVoucherColumnEnum.getCode().equals(code)) continue;
            return adjustVoucherColumnEnum;
        }
        return null;
    }

    public static AdjustVoucherColumnEnum getColumnByName(String name) {
        for (AdjustVoucherColumnEnum adjustVoucherColumnEnum : AdjustVoucherColumnEnum.values()) {
            if (!adjustVoucherColumnEnum.getName().equals(name)) continue;
            return adjustVoucherColumnEnum;
        }
        return EXTRAFIELDS;
    }
}

