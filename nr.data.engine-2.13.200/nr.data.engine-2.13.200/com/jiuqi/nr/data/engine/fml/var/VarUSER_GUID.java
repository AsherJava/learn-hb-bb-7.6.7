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

public class VarUSER_GUID
extends AbstractContextVar {
    private static final long serialVersionUID = 990065745246388488L;

    public VarUSER_GUID() {
        super("USER_GUID", "\u7528\u6237\u7684GUID", 33);
    }

    public Object getVarValue(IContext context) {
        ContextUser user = this.getUser();
        if (user == null) {
            return null;
        }
        return user.getId();
    }

    public void setVarValue(Object value) {
    }
}

