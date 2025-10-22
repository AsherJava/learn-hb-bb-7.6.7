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

public class VarCUR_YEAR
extends AbstractContextVar {
    private static final long serialVersionUID = 3514488197839309526L;

    public VarCUR_YEAR() {
        super("CUR_YEAR", "\u5f53\u524d\u5e74\u5ea6", 6);
    }

    public VarCUR_YEAR(String varName, String varTitle, int dataType) {
        super(varName, varTitle, dataType);
    }

    public Object getVarValue(IContext context) {
        PeriodWrapper period = this.getPeriod(context);
        if (period == null) {
            return null;
        }
        int year = period.getYear();
        return String.valueOf(year);
    }

    public void setVarValue(Object value) {
    }

    public void toJavaScript(IContext context, StringBuilder buffer, ScriptInfo info) {
        buffer.append("var ").append(info.getNodeName(VarCUR_YEAR.class)).append(" = new CUR_YEAR(");
        buffer.append("\"").append(this.getVarName()).append("\",").append("\"").append(this.getVarTitle()).append("\",");
        buffer.append("\"").append(this.getDataType()).append("\"");
        buffer.append(");");
    }
}

