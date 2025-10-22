/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.checkdes.internal.io;

public enum CsvHeaders {
    MD_CODE("MD_C"),
    PERIOD("P"),
    FORMULA_SCHEME_TITLE("FLS_T"),
    FORM_CODE("FM_C"),
    FORMULA_CODE("FL_C"),
    GLOB_ROW("R"),
    GLOB_COL("C"),
    DIM_STR("BN_DIM"),
    CONTENT("CONT"),
    USER_ID("U_ID"),
    USER_NICK_NAME("U_N_NAME"),
    UPDATE_TIME("U_T");

    private final String value;

    private CsvHeaders(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static String getEntityHeader(String entityId) {
        return "E_" + entityId;
    }

    public static String getEntityIdByHeader(String header) {
        return header.substring("E_".length());
    }
}

