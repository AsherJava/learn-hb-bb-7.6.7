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

public class VarDWDM
extends AbstractContextVar {
    private static final long serialVersionUID = -1128175785080034997L;

    public VarDWDM() {
        super("DWDM", "\u5f53\u524d\u5355\u4f4d\u4ee3\u7801", 6);
    }

    public Object getVarValue(IContext context) throws Exception {
        IEntityRow unit = this.getUnitEntityRow(context);
        if (unit == null) {
            return null;
        }
        return unit.getCode();
    }

    public void setVarValue(Object value) {
    }
}

