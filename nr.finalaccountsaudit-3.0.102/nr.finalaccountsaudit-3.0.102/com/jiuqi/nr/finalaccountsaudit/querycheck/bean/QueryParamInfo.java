/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.finalaccountsaudit.querycheck.bean;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QueryParamInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> queryDefines = new ArrayList<String>();
    private JtableContext context;

    public List<String> getQueryDefines() {
        return this.queryDefines;
    }

    public void setQueryDefines(List<String> queryDefines) {
        this.queryDefines = queryDefines;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }
}

