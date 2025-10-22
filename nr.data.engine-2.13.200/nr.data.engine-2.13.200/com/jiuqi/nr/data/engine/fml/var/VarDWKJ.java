/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 */
package com.jiuqi.nr.data.engine.fml.var;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.engine.fml.var.AbstractContextVar;
import org.springframework.util.StringUtils;

public class VarDWKJ
extends AbstractContextVar {
    private static final long serialVersionUID = -1128175785080034997L;

    public VarDWKJ() {
        super("DWKJ", "\u5355\u4f4d\u53e3\u5f84", 6);
    }

    public Object getVarValue(IContext context) throws Exception {
        String entityCode = "";
        String entityId = DsContextHolder.getDsContext().getContextEntityId();
        if (StringUtils.hasText(entityId)) {
            entityCode = entityId.substring(0, entityId.indexOf("@") == -1 ? entityId.length() : entityId.indexOf("@"));
        }
        return entityCode;
    }
}

