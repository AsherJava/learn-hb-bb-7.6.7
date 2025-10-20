/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.common;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class QueryTemplateConst {
    public static final String FIXED_COLUMN_WIDTH = "200px";
    public static final String OTHER_COLUMN_WIDTH = "128px";
    public static final String DATE_COLUMN_WIDTH = "122px";
    private static final String U_UNITCODE = "UNITCODE";
    private static final String U_DEPTCODE = "DEPTCODE";
    private static final String U_BILLCODE = "BILLCODE";
    private static final Set<String> fixedColumnSet = new HashSet<String>(3);

    private QueryTemplateConst() {
    }

    public static Set<String> getFixedColumn() {
        return Collections.unmodifiableSet(fixedColumnSet);
    }

    static {
        fixedColumnSet.add(U_BILLCODE);
        fixedColumnSet.add(U_UNITCODE);
        fixedColumnSet.add(U_DEPTCODE);
    }
}

