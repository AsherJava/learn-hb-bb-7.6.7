/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.datamodel.exchange.nvwa.base;

import java.util.UUID;

public interface DataModelConst {
    public static final String VIEW_BASEDATA_VA = "VA_USED_ONLY";
    public static final String PACKAGE_VA_DATAMODEL = new UUID(11L, 160L).toString();
    public static final String PACKAGE_VA_DATAMODEL_CODE = "VA_DATAMODEL";
    public static final String PACKAGE_VA_DATAMODEL_TITLE = "VA\u6570\u636e\u5efa\u6a21";
    public static final String PACKAGE_VA_UNGROUPED = new UUID(22L, 50L).toString();
    public static final String PACKAGE_VA_UNGROUPED_CODE = "VA_UNGROUPED";
    public static final String PACKAGE_VA_UNGROUPED_TITLE = "\u672a\u5206\u7ec4";

    public static String EXTENSION_KEY_BASEDATA_TABLEKEY(String tableName) {
        return "BASEDATA-TABLEMODEL-ID-" + tableName;
    }
}

