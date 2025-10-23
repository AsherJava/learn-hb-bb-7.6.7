/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 */
package com.jiuqi.nr.nrdx.adapter.param.common;

import com.jiuqi.bi.transfer.engine.ex.TransferException;

public class NrdxFormulaAndPrintGuidKeyParse {
    public static String INTER_TABLE_FORMULA = "::";
    public static String INTER_TABLE_FORMULA_KEY = "0000";
    public static String INTER_TABLE_FORMULA_TITLE = "\u8868\u95f4\u516c\u5f0f";

    public static String[] parseKey(String key) throws TransferException {
        String[] split = key.split(INTER_TABLE_FORMULA, 2);
        if (split.length < 2) {
            throw new TransferException("\u67e5\u8be2\u8d44\u6e90\u5931\u8d25");
        }
        return split;
    }

    public static String toFormulaId(String formulaSchemeKey, String key) {
        return formulaSchemeKey + INTER_TABLE_FORMULA + key;
    }

    public static String toVNodeFormulaId(String formulaSchemeKey) {
        return formulaSchemeKey + INTER_TABLE_FORMULA + INTER_TABLE_FORMULA_KEY;
    }
}

