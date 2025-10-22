/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.finalaccountsaudit.zbquerycheck.bean;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ZBQueryParamInfo
implements Serializable {
    private static final long serialVersionUID = -2514666849407625270L;
    private List<String> zbQueryModelIds = new ArrayList<String>();
    private JtableContext context;

    public List<String> getZbQueryModelIds() {
        return this.zbQueryModelIds;
    }

    public void setZbQueryModelIds(List<String> zbQueryModelIds) {
        this.zbQueryModelIds = zbQueryModelIds;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }
}

