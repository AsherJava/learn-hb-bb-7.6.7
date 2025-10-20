/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.datatrace.enums.GcDataTraceTypeEnum
 */
package com.jiuqi.gcreport.billcore.offsetcheck.enums;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.datatrace.enums.GcDataTraceTypeEnum;

public enum OffsetCheckInfoEnum {
    TYPE_MISMATCH(1, "\u3010\u672a\u751f\u6210\u3011\u53f0\u8d26\u5408\u5e76\u7c7b\u578b\u548c\u89c4\u5219\u7c7b\u578b\u4e0d\u5339\u914d"),
    NO_FAIR_VALUE_LEDGER(2, "\u3010\u672a\u751f\u6210\u3011\u65e0\u516c\u5141\u4ef7\u503c\u53f0\u8d26\uff0c\u4e0d\u751f\u6210\u516c\u5141\u4ef7\u503c\u8c03\u6574\u89c4\u5219\u5206\u5f55"),
    RULE_CONDITION_NOT_MET(3, "\u3010\u672a\u751f\u6210\u3011\u53f0\u8d26\u4e0d\u6ee1\u8db3\u89c4\u5219\u9002\u7528\u6761\u4ef6"),
    NO_SCENARIO_DATA(4, "\u3010\u672a\u751f\u6210\u3011\u53f0\u8d26\u65e0\u8be5\u89c4\u5219\u5bf9\u5e94\u7684\u53d8\u52a8\u573a\u666f\u6570\u636e"),
    CALCULATION_RESULT_ZERO(5, "\u3010\u672a\u751f\u6210\u3011\u89c4\u5219\u53d6\u6570\u7684\u6574\u7ec4\u8ba1\u7b97\u7ed3\u679c\u4e3a0\u6216\u53f0\u8d26\u4e0d\u6ee1\u8db3\u89c4\u5219\u53d6\u6570\u516c\u5f0f\u6761\u4ef6"),
    DEBIT_CREDIT_MISMATCH(6, "\u3010\u68c0\u67e5\u4e0d\u4e00\u81f4\u3011\u89c4\u5219\u53d6\u6570\u7684\u8ba1\u7b97\u7ed3\u679c\u501f\u8d37\u65b9\u91d1\u989d\u4e0d\u76f8\u7b49"),
    MERGE_CALC_NOT_EXECUTED(7, "\u3010\u68c0\u67e5\u4e0d\u4e00\u81f4\u3011\u672a\u6267\u884c\u5408\u5e76\u8ba1\u7b97\uff0c\u6216\u8005\u5408\u5e76\u8ba1\u7b97\u540e\u4fee\u6539%1$s\u53f0\u8d26\u6216\u89c4\u5219,\u9700\u8981\u91cd\u65b0\u6267\u884c\u5408\u5e76\u8ba1\u7b97"),
    CHECK_CONSISTENT(8, "\u3010\u68c0\u67e5\u4e00\u81f4\u3011\u68c0\u67e5\u5206\u5f55\u4e0e\u62b5\u9500\u5206\u5f55\u91d1\u989d\u4e00\u81f4"),
    CHECK_INCONSISTENT(9, "\u3010\u68c0\u67e5\u4e0d\u4e00\u81f4\u3011\u68c0\u67e5\u5206\u5f55\u4e0e\u62b5\u9500\u5206\u5f55\u91d1\u989d\u4e0d\u76f8\u7b49\uff0c\u53ef\u80fd\u662f%1$s\u53f0\u8d26\u6216\u5408\u5e76\u89c4\u5219\u53d1\u751f\u53d8\u5316"),
    TYPE_MISMATCH_AFTER_CHANGE(10, "\u3010\u68c0\u67e5\u4e0d\u4e00\u81f4\u3011\u62a5\u8868\u6570\u636e\u3001\u53f0\u8d26\u6216\u5408\u5e76\u89c4\u5219\u53d1\u751f\u53d8\u5316\uff0c\u53f0\u8d26\u5408\u5e76\u7c7b\u578b\u548c\u89c4\u5219\u7c7b\u578b\u4e0d\u5339\u914d"),
    NO_FAIR_VALUE_LEDGER_AFTER_CHANGE(11, "\u3010\u68c0\u67e5\u4e0d\u4e00\u81f4\u3011 \u62a5\u8868\u6570\u636e\u3001\u53f0\u8d26\u6216\u5408\u5e76\u89c4\u5219\u53d1\u751f\u53d8\u5316\uff0c\u65e0\u516c\u5141\u4ef7\u503c\u53f0\u8d26\uff0c\u4e0d\u751f\u6210\u516c\u5141\u4ef7\u503c\u8c03\u6574\u89c4\u5219\u5206\u5f55"),
    RULE_CONDITION_NOT_MET_AFTER_CHANGE(12, "\u3010\u68c0\u67e5\u4e0d\u4e00\u81f4\u3011%1$s\u53f0\u8d26\u6216\u5408\u5e76\u89c4\u5219\u53d1\u751f\u53d8\u5316\uff0c\u53f0\u8d26\u4e0d\u6ee1\u8db3\u89c4\u5219\u9002\u7528\u6761\u4ef6"),
    NO_SCENARIO_DATA_AFTER_CHANGE(13, "\u3010\u68c0\u67e5\u4e0d\u4e00\u81f4\u3011\u62a5\u8868\u6570\u636e\u3001\u53f0\u8d26\u6216\u5408\u5e76\u89c4\u5219\u53d1\u751f\u53d8\u5316\uff0c\u53f0\u8d26\u65e0\u8be5\u89c4\u5219\u5bf9\u5e94\u7684\u53d8\u52a8\u573a\u666f\u6570\u636e"),
    CALCULATION_RESULT_ZERO_AFTER_CHANGE(14, "\u3010\u68c0\u67e5\u4e0d\u4e00\u81f4\u3011%1$s\u53f0\u8d26\u6216\u5408\u5e76\u89c4\u5219\u53d1\u751f\u53d8\u5316\uff0c\u89c4\u5219\u53d6\u6570\u7684\u6574\u7ec4\u8ba1\u7b97\u7ed3\u679c\u4e3a0\u6216\u53f0\u8d26\u4e0d\u6ee1\u8db3\u89c4\u5219\u53d6\u6570\u516c\u5f0f\u6761\u4ef6"),
    DEBIT_CREDIT_MISMATCH_AFTER_CHANGE(15, "\u3010\u68c0\u67e5\u4e0d\u4e00\u81f4\u3011%1$s\u53f0\u8d26\u6216\u5408\u5e76\u89c4\u5219\u53d1\u751f\u53d8\u5316\uff0c\u89c4\u5219\u53d6\u6570\u7684\u8ba1\u7b97\u7ed3\u679c\u501f\u8d37\u65b9\u91d1\u989d\u4e0d\u76f8\u7b49");

    private int offsetCheckSceneValue;
    private String offsetCheckSceneTypeName;

    private OffsetCheckInfoEnum(int offsetCheckSceneValue, String offsetCheckSceneTypeName) {
        this.offsetCheckSceneValue = offsetCheckSceneValue;
        this.offsetCheckSceneTypeName = offsetCheckSceneTypeName;
    }

    public int getOffsetCheckSceneValue() {
        return this.offsetCheckSceneValue;
    }

    public void setOffsetCheckSceneValue(int offsetCheckSceneValue) {
        this.offsetCheckSceneValue = offsetCheckSceneValue;
    }

    public String getOffsetCheckSceneTypeName() {
        return this.offsetCheckSceneTypeName;
    }

    public String getOffsetCheckSceneTypeName(String gcDataTraceType) {
        if (StringUtils.isEmpty((String)gcDataTraceType)) {
            return this.offsetCheckSceneTypeName;
        }
        if (GcDataTraceTypeEnum.INVEST.getType().equals(gcDataTraceType)) {
            return String.format(this.offsetCheckSceneTypeName, "\u62a5\u8868\u6570\u636e\u3001");
        }
        return String.format(this.offsetCheckSceneTypeName, "");
    }

    public void setOffsetCheckSceneTypeName(String offsetCheckSceneTypeName) {
        this.offsetCheckSceneTypeName = offsetCheckSceneTypeName;
    }
}

