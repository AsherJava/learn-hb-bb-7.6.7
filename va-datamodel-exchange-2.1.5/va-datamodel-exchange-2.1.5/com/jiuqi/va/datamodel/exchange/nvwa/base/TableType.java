/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 */
package com.jiuqi.va.datamodel.exchange.nvwa.base;

import com.jiuqi.va.domain.datamodel.DataModelType;

public enum TableType {
    ORG("00000000-FFFF-0000-1111-000000000000", "VADM_ORG", "\u7ec4\u7ec7\u673a\u6784"),
    BASEDATA("00000000-FFFF-0000-2222-000000000000", "VADM_BASEDATA", "\u57fa\u7840\u6570\u636e"),
    BILL("00000000-FFFF-0000-3333-000000000000", "VADM_BILL", "\u5355\u636e"),
    OTHER("00000000-FFFF-0000-4444-000000000000", "VADM_OTHER", "\u5176\u4ed6\u8868");

    private String id;
    private String code;
    private String title;

    private TableType(String id, String code, String title) {
        this.id = id;
        this.code = code;
        this.title = title;
    }

    public String getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static TableType formatTableType(DataModelType.BizType biztype, String tableName) {
        if (biztype == DataModelType.BizType.BILL) {
            return BILL;
        }
        if (biztype == DataModelType.BizType.BASEDATA) {
            if (tableName.equalsIgnoreCase("MD_ORG") || tableName.startsWith("MD_ORG_")) {
                return ORG;
            }
            return BASEDATA;
        }
        return OTHER;
    }
}

