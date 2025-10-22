/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.data.engine.fml.var;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.data.engine.fml.var.AbstractContextVar;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;

public class VarSYS_UNITKEY
extends AbstractContextVar {
    private static final long serialVersionUID = -2697430353700209184L;

    public VarSYS_UNITKEY() {
        super("SYS_UNITKEY", "\u5f53\u524d\u5355\u4f4d\u4e1a\u52a1\u4e3b\u952e", 6);
    }

    public Object getVarValue(IContext context) throws Exception {
        IEntityRow unit = this.getUnitEntityRow(context);
        if (unit == null) {
            return null;
        }
        return unit.getEntityKeyData();
    }

    public void setVarValue(Object value) {
    }
}

