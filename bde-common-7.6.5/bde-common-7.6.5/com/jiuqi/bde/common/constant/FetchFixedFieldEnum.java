/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.constant;

public enum FetchFixedFieldEnum {
    SIGN("sign", "\u8ba1\u7b97\u89c4\u5219"),
    FETCHTYPE("fetchType", "\u53d6\u6570\u7c7b\u578b"),
    SUBJECTCODE("subjectCode", "\u79d1\u76ee"),
    EXCLUDESUBJECTCODE("excludeSubjectCode", "\u6392\u9664\u79d1\u76ee"),
    DIMTYPE("dimType", "\u91cd\u5206\u7c7b\u7ef4\u5ea6"),
    SUMTYPE("sumType", "\u6c47\u603b\u7c7b\u578b"),
    RECLASSSUBJCODE("reclassSubjCode", "\u91cd\u5206\u7c7b\u540e\u79d1\u76ee"),
    RECLASSSRCSUBJCODE("reclassSrcSubjCode", "\u91cd\u5206\u7c7b\u524d\u79d1\u76ee"),
    AGINGRANGETYPE("agingRangeType", "\u533a\u95f4\u7c7b\u578b"),
    AGINGRANGESTART("agingRangeStart", "\u8d77\u59cb\u533a\u95f4"),
    AGINGRANGEEND("agingRangeEnd", "\u622a\u6b62\u533a\u95f4"),
    CASHCODE("cashCode", "\u73b0\u6d41\u9879\u76ee"),
    FORMULA("formula", "\u53d6\u6570SQL"),
    AGEGROUP("ageGroup", "\u8d26\u9f84\u6bb5"),
    VCHRUNIQUECODE("vchrUniqueCode", "\u51ed\u8bc1\u552f\u4e00\u6807\u8bc6"),
    INVESTEDUNIT("investedUnit", "\u88ab\u6295\u8d44\u5355\u4f4d");

    private String code;
    private String name;

    private FetchFixedFieldEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static FetchFixedFieldEnum getEnumByCode(String code) {
        for (FetchFixedFieldEnum fixedFieldEnum : FetchFixedFieldEnum.values()) {
            if (!fixedFieldEnum.getCode().equals(code)) continue;
            return fixedFieldEnum;
        }
        throw new RuntimeException("\u65e0\u6b64\u7c7b\u578b\u7684FetchFixedFieldEnum\u679a\u4e3e code=" + code);
    }
}

