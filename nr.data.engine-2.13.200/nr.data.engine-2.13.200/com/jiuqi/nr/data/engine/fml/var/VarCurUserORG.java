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

public class VarCurUserORG
extends AbstractContextVar {
    private static final long serialVersionUID = -5508219813540296670L;

    public VarCurUserORG() {
        super("CUR_USER_ORG", "\u5f53\u524d\u7528\u6237\u6240\u5c5e\u673a\u6784", 6);
    }

    public Object getVarValue(IContext context) throws Exception {
        ContextUser user = this.getUser();
        if (user == null) {
            return null;
        }
        return user.getOrgCode();
    }
}

