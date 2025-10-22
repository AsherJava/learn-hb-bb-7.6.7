/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.interpret.ScriptInfo
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.nr.data.engine.fml.var;

import com.jiuqi.bi.syntax.interpret.ScriptInfo;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.data.engine.fml.var.AbstractContextVar;

public class VarCUR_TIME
extends AbstractContextVar {
    private static final long serialVersionUID = 2877687104709175310L;

    public VarCUR_TIME() {
        super("CUR_TIME", "\u5f53\u524d\u65f6\u671f", 4);
    }

    public Object getVarValue(IContext context) {
        PeriodWrapper period = this.getPeriod(context);
        if (period == null) {
            return null;
        }
        int time = period.getPeriod();
        return time;
    }

    public void setVarValue(Object value) {
    }

    public void toJavaScript(IContext context, StringBuilder buffer, ScriptInfo info) {
        buffer.append("var ").append(info.getNodeName(VarCUR_TIME.class)).append(" = new CUR_TIME(");
        buffer.append("\"").append(this.getVarName()).append("\",").append("\"").append(this.getVarTitle()).append("\",");
        buffer.append("\"").append(this.getDataType()).append("\"");
        buffer.append(");");
    }
}

