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

public class VarUSER_TITLE
extends AbstractContextVar {
    private static final long serialVersionUID = -694848282571007382L;

    public VarUSER_TITLE() {
        super("USER_TITLE", "\u7528\u6237\u7684\u59d3\u540d", 6);
    }

    public Object getVarValue(IContext context) {
        ContextUser user = this.getUser();
        if (user == null) {
            return null;
        }
        return user.getFullname();
    }

    public void setVarValue(Object value) {
    }
}

