/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.INRContext
 */
package com.jiuqi.nr.jtable.params.input;

import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.io.Serializable;

public class LinkImgOptionInfo
implements Serializable,
INRContext {
    private static final long serialVersionUID = 1L;
    private JtableContext context;
    private String groupKey;
    private boolean small;
    private String contextTaskKey;
    private String contextEntityId;
    private String contextFormSchemeKey;
    private String contextFilterExpression;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public boolean isSmall() {
        return this.small;
    }

    public void setSmall(boolean small) {
        this.small = small;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}

