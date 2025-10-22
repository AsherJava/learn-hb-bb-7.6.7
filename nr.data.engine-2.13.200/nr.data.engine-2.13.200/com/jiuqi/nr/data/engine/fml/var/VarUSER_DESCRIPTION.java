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

public class VarUSER_DESCRIPTION
extends AbstractContextVar {
    private static final long serialVersionUID = 6004595371473236670L;

    public VarUSER_DESCRIPTION() {
        super("USER_DESCRIPTION", "\u7528\u6237\u7684\u63cf\u8ff0\u4fe1\u606f", 6);
    }

    public Object getVarValue(IContext context) {
        ContextUser user = this.getUser();
        if (user == null) {
            return null;
        }
        return user.getDescription();
    }

    public void setVarValue(Object value) {
    }
}

