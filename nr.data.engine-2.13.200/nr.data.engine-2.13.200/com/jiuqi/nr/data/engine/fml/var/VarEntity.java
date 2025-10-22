/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.data.engine.fml.var;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.data.engine.fml.var.AbstractContextVar;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;

public class VarEntity
extends AbstractContextVar {
    private static final long serialVersionUID = 7226594476171197357L;
    private final String dimensionName;
    private final FieldDefine fieldDefine;

    public VarEntity(String dimensionName, String varName, FieldDefine fieldDefine, int dataType) {
        super(varName, varName, dataType);
        this.dimensionName = dimensionName;
        this.fieldDefine = fieldDefine;
    }

    public Object getVarValue(IContext context) throws Exception {
        IEntityRow dimRow = this.getEntityRow(context, this.dimensionName);
        if (dimRow == null) {
            return null;
        }
        AbstractData value = dimRow.getValue(this.fieldDefine.getCode());
        return value == null ? null : value.getAsObject();
    }

    public void setVarValue(Object value) {
    }

    public String getDimensionName() {
        return this.dimensionName;
    }
}

