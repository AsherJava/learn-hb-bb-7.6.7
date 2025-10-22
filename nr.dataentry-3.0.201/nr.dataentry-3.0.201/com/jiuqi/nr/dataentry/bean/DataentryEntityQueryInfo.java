/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.impl.NRContext
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.context.infc.impl.NRContext;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.io.Serializable;

public class DataentryEntityQueryInfo
extends NRContext
implements Serializable {
    private String entityViewKey;
    private JtableContext jtableContext;

    public String getEntityViewKey() {
        return this.entityViewKey;
    }

    public void setEntityViewKey(String entityViewKey) {
        this.entityViewKey = entityViewKey;
    }

    public JtableContext getJtableContext() {
        return this.jtableContext;
    }

    public void setJtableContext(JtableContext jtableContext) {
        this.jtableContext = jtableContext;
    }
}

