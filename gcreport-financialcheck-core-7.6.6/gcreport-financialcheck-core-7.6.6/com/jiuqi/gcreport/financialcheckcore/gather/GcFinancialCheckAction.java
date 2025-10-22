/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.financialcheckcore.gather;

import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;

public interface GcFinancialCheckAction {
    public String code();

    public String title();

    default public String icon() {
        return null;
    }

    public Object execute(Object var1);

    public boolean isVisible(QueryParamsVO var1);

    public boolean isEnable(QueryParamsVO var1);
}

