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

public class VarSYS_UNITTITLE
extends AbstractContextVar {
    private static final long serialVersionUID = -6477037415803270867L;

    public VarSYS_UNITTITLE() {
        super("SYS_UNITTITLE", "\u5f53\u524d\u5355\u4f4d\u540d\u79f0", 6);
    }

    public Object getVarValue(IContext context) throws Exception {
        IEntityRow unit = this.getUnitEntityRow(context);
        if (unit == null) {
            return null;
        }
        return unit.getTitle();
    }

    public void setVarValue(Object value) {
    }
}

