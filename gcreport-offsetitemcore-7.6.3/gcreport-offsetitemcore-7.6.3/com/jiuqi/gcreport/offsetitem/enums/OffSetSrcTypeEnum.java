/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.enums;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum OffSetSrcTypeEnum {
    ADJUST_ITEM(0, "\u8c03\u6574\u5206\u5f55"),
    FAIRVALUE_ADJ(1, "\u516c\u5141\u4ef7\u503c\u8c03\u6574"),
    EQUITY_METHOD_ADJ(2, "\u6a21\u62df\u6743\u76ca\u6cd5\u8c03\u6574"),
    FAIRVALUE_ADJUST_ITEM_INIT(5, "\u516c\u5141\u4ef7\u503c\u8c03\u6574\u5206\u5f55\u521d\u59cb"),
    EQUITY_METHOD_ADJUST_ITEM_INIT(6, "\u6743\u76ca\u6cd5\u6a21\u62df\u8c03\u6574\u5206\u5f55\u521d\u59cb"),
    DATA_SYNC_INIT(7, "\u6570\u636e\u540c\u6b65\u521d\u59cb"),
    OFFSET_ITEM(20, "\u62b5\u9500\u5206\u5f55"),
    OFFSET_ITEM_INIT(21, "\u62b5\u9500\u5206\u5f55\u521d\u59cb"),
    INVEST_OFFSET_ITEM_INIT(22, "\u6295\u8d44\u62b5\u9500\u5206\u5f55\u521d\u59cb"),
    INVENTORY_OFFSET_ITEM_INIT(24, "\u5b58\u8d27\u62b5\u9500\u5206\u5f55\u521d\u59cb"),
    CONSOLIDATE(25, "\u5408\u5e76\u8ba1\u7b97"),
    PHS(26, "\u5e73\u8861\u6570"),
    BROUGHT_FORWARD_LOSS_GAIN(27, "\u7ed3\u8f6c\u635f\u76ca"),
    DEFERRED_INCOME_TAX(28, "\u9012\u5ef6\u6240\u5f97\u7a0e"),
    MANUAL_OFFSET(29, "\u624b\u52a8\u62b5\u9500"),
    MANUAL_OFFSET_INPUT(30, "\u624b\u52a8\u62b5\u9500\u8f93\u5165\u8c03\u6574"),
    MODIFIED_INPUT(31, "\u8f93\u5165\u8c03\u6574"),
    COPY_OFFSET(32, "\u590d\u5236\u62b5\u9500"),
    CARRY_OVER(33, "\u5e74\u521d\u7ed3\u8f6c"),
    CARRY_OVER_FAIRVALUE(34, "\u5e74\u521d\u7ed3\u8f6c"),
    WRITE_OFF(35, "\u51b2\u9500"),
    DEFERRED_INCOME_TAX_RULE(36, "\u89c4\u5219\u9012\u5ef6\u6240\u5f97\u7a0e"),
    MINORITY_LOSS_GAIN_RECOVERY(37, "\u5c11\u6570\u80a1\u4e1c\u635f\u76ca\u8fd8\u539f"),
    CONNECTED_TRANSACTION(40, "\u5173\u8054\u4ea4\u6613"),
    CONNECTED_TRANSACTION_NOCHECK(41, "\u5173\u8054\u4ea4\u6613\u672a\u5bf9\u8d26"),
    COPY_PRIOR(50, "\u590d\u5236\u4e0a\u4e00\u671f"),
    SUBJECT_RECLASSIFY(60, "\u4f59\u989d\u91cd\u5206\u7c7b"),
    DATA_SYNC(61, "\u6570\u636e\u540c\u6b65"),
    SUBJECT_REDUCE_RECLASSIFY(62, "\u62b5\u51cf\u91cd\u5206\u7c7b"),
    FINANCIAL_CHECK(70, "\u51ed\u8bc1\u7ea7\u5173\u8054\u4ea4\u6613"),
    FINANCIAL_CHECK_NOCHECK(71, "\u51ed\u8bc1\u7ea7\u5173\u8054\u4ea4\u6613\u672a\u5bf9\u8d26"),
    SCALE_SPLIT(90, "\u6bd4\u4f8b\u62c6\u5206"),
    RYHB_MODIFIED_INPUT(91, "\u4efb\u610f\u5408\u5e76\u8f93\u5165\u8c03\u6574"),
    KMFP(42, "\u79d1\u76ee\u5206\u914d");

    private int srcTypeValue;
    private String srcTypeName;
    private static Set<Integer> initOffSetSrcTypeValue;
    private static Set<Integer> allInitTermOffSetSrcTypeValue;

    private OffSetSrcTypeEnum(int srcTypeValue, String srcTypeName) {
        this.srcTypeValue = srcTypeValue;
        this.srcTypeName = srcTypeName;
    }

    public int getSrcTypeValue() {
        return this.srcTypeValue;
    }

    public String getSrcTypeName() {
        return this.srcTypeName;
    }

    public void setSrcTypeName(String srcTypeName) {
        this.srcTypeName = srcTypeName;
    }

    public static OffSetSrcTypeEnum getEnumByValue(int srcTypeValue) {
        for (OffSetSrcTypeEnum srcTypeEnum : OffSetSrcTypeEnum.values()) {
            if (srcTypeEnum.getSrcTypeValue() != srcTypeValue) continue;
            return srcTypeEnum;
        }
        return null;
    }

    public static int getEnumValue(OffSetSrcTypeEnum srcTypeEnum) {
        return srcTypeEnum == null ? OFFSET_ITEM.getSrcTypeValue() : srcTypeEnum.getSrcTypeValue();
    }

    public static String getNameByValue(int srcTypeValue) {
        for (OffSetSrcTypeEnum srcTypeEnum : OffSetSrcTypeEnum.values()) {
            if (srcTypeEnum.getSrcTypeValue() != srcTypeValue) continue;
            return srcTypeEnum.getSrcTypeName();
        }
        return null;
    }

    public static Set<Integer> getCommonInitOffSetSrcTypeValue() {
        return Collections.unmodifiableSet(initOffSetSrcTypeValue);
    }

    public static Set<Integer> getAllInitOffSetSrcTypeValue() {
        return Collections.unmodifiableSet(allInitTermOffSetSrcTypeValue);
    }

    static {
        initOffSetSrcTypeValue = new HashSet<Integer>();
        allInitTermOffSetSrcTypeValue = new HashSet<Integer>();
        initOffSetSrcTypeValue.add(FAIRVALUE_ADJUST_ITEM_INIT.getSrcTypeValue());
        initOffSetSrcTypeValue.add(EQUITY_METHOD_ADJUST_ITEM_INIT.getSrcTypeValue());
        initOffSetSrcTypeValue.add(OFFSET_ITEM_INIT.getSrcTypeValue());
        initOffSetSrcTypeValue.add(INVEST_OFFSET_ITEM_INIT.getSrcTypeValue());
        initOffSetSrcTypeValue.add(INVENTORY_OFFSET_ITEM_INIT.getSrcTypeValue());
        allInitTermOffSetSrcTypeValue.addAll(initOffSetSrcTypeValue);
        allInitTermOffSetSrcTypeValue.add(CARRY_OVER_FAIRVALUE.getSrcTypeValue());
        allInitTermOffSetSrcTypeValue.add(CARRY_OVER.getSrcTypeValue());
    }
}

