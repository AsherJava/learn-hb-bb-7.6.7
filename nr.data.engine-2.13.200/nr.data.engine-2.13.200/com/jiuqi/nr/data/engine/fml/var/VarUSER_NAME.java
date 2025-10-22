/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.core.context.ContextUser
 */
package com.jiuqi.nr.data.engine.fml.var;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.nr.data.engine.fml.var.AbstractContextVar;

public class VarUSER_NAME
extends AbstractContextVar {
    private static final long serialVersionUID = 2032269387767338342L;

    public VarUSER_NAME() {
        super("USER_NAME", "\u7528\u6237\u767b\u5f55\u540d", 6);
    }

    public Object getVarValue(IContext context) {
        ContextUser user = this.getUser();
        if (user == null) {
            return null;
        }
        return user.getName();
    }

    public void setVarValue(Object value) {
    }
}

