/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.interpret.ScriptInfo
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.nr.data.engine.fml.var;

import com.jiuqi.bi.syntax.interpret.ScriptInfo;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.data.engine.fml.var.AbstractContextVar;

public class VarCUR_PERIOD
extends AbstractContextVar {
    private static final long serialVersionUID = -4831035251643363088L;

    public VarCUR_PERIOD() {
        super("CUR_PERIOD", "\u5f53\u524d\u65f6\u671f\u7c7b\u578b", 6);
    }

    public Object getVarValue(IContext context) {
        PeriodWrapper period = this.getPeriod(context);
        if (period == null) {
            return null;
        }
        int type = period.getType();
        return String.valueOf((char)PeriodConsts.typeToCode((int)type));
    }

    public void setVarValue(Object value) {
    }

    public void toJavaScript(IContext context, StringBuilder buffer, ScriptInfo info) {
        buffer.append("var ").append(info.getNodeName(VarCUR_PERIOD.class)).append(" = new CUR_PERIOD(");
        buffer.append("\"").append(this.getVarName()).append("\",").append("\"").append(this.getVarTitle()).append("\",");
        buffer.append("\"").append(this.getDataType()).append("\"");
        buffer.append(");");
    }
}

