/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping2.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum NvdataParamType {
    BASE_DATA_DEFINE(Arrays.asList("BaseDataDefine"), "\u57fa\u7840\u6570\u636e", "BaseDataDefine"),
    ORG_CATEGORY(Arrays.asList("OrgCategory"), "\u7ec4\u7ec7\u673a\u6784", "OrgData"),
    FORMULA_FORM(Arrays.asList("FORMULA_FORM", "FORMULA_SCHEME"), "\u62a5\u8868\u516c\u5f0f", "DEFINITION_TRANSFER_FACTORY_ID"),
    FORM(Arrays.asList("FORM", "FORM_SCHEME"), "\u62a5\u8868", "DEFINITION_TRANSFER_FACTORY_ID"),
    FORM_FIELD(Arrays.asList("SCHEME", "TABLE", "MD_INFO", "ACCOUNT_TABLE", "DETAIL_TABLE", "MUL_DIM_TABLE", "MUL_DIM_TABLE"), "\u62a5\u8868\u6307\u6807", "DATASCHEME_TRANSFER_FACTORY_ID"),
    PERIOD(null, "\u65f6\u671f", "PERIOD_TRANSFER_FACTORY_ID");

    private final List<String> value;
    private final String title;
    private final String factoryId;

    private NvdataParamType(List<String> value, String title, String factoryId) {
        this.value = value;
        this.title = title;
        this.factoryId = factoryId;
    }

    public List<String> getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public String getFactoryId() {
        return this.factoryId;
    }

    public static Map<String, List<String>> titleOf(List<NvdataParamType> paramType) {
        HashMap<String, List<String>> result = new HashMap<String, List<String>>();
        for (NvdataParamType nvdataParamType : paramType) {
            result.computeIfAbsent(nvdataParamType.getFactoryId(), key -> new ArrayList()).addAll(nvdataParamType.getValue());
        }
        return result;
    }
}

