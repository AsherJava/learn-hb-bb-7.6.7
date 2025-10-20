/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling.lockmgr.common;

public enum TaskTypeEnum {
    BIZVCHRINTEGRATION("bizVchrIntegration", "\u63d2\u4ef6\u51ed\u8bc1\u6574\u5408"),
    BIZVOUCHERCONVERT("BizVoucherConvert", "\u63d2\u4ef6\u51ed\u8bc1\u8f6c\u6362"),
    VCHRFIELDHANDLE("VchrFieldHandle", "\u51ed\u8bc1\u5b57\u6bb5\u5904\u7406"),
    ETLCUSTOMAFTERVCHRHANDLE("EtlCustomAfterVchrHandle", "ETL\u81ea\u5b9a\u4e49\u540e\u7f6e\u51ed\u8bc1\u52a0\u5de5"),
    BALANCECALCULATE("BalanceCalculate", "\u9884\u8bb0\u8d26\u8f85\u52a9\u4f59\u989d\u8868\u8ba1\u7b97"),
    DCBALANCECAL("DcBalanceCal", "\u79d1\u76ee\u4f59\u989d\u8868\u8ba1\u7b97"),
    CFBALANCECALCULATE("CfBalanceCal", "\u73b0\u91d1\u6d41\u91cf\u4f59\u989d\u8868\u8ba1\u7b97"),
    RECLASSIFYRULECHANGEPROCESS("ReclassifyRuleChangeProcess", "\u3010\u91cd\u5206\u7c7b\u3011\u91cd\u5206\u7c7b\u89c4\u5219\u53d8\u66f4\u5904\u7406");

    private String name;
    private String descript;

    private TaskTypeEnum(String name, String descript) {
        this.name = name;
        this.descript = descript;
    }

    public String getName() {
        return this.name;
    }

    public String getDescript() {
        return this.descript;
    }
}

