/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.interpret.ScriptInfo
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.nr.data.engine.fml.var;

import com.jiuqi.bi.syntax.interpret.ScriptInfo;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.data.engine.fml.var.AbstractContextVar;

public class VarCUR_PERIODTITLE
extends AbstractContextVar {
    private static final long serialVersionUID = -4831035251643363088L;

    public VarCUR_PERIODTITLE() {
        super("CUR_PERIODTITLE", "\u5f53\u524d\u65f6\u671f\u6807\u9898", 6);
    }

    public Object getVarValue(IContext context) {
        QueryContext qContext = (QueryContext)context;
        PeriodWrapper period = this.getPeriod(context);
        if (period == null) {
            return null;
        }
        ExecutorContext exeContext = qContext.getExeContext();
        return exeContext.getEnv().getPeriodAdapter(exeContext).getPeriodTitle(period);
    }

    public void setVarValue(Object value) {
    }

    public void toJavaScript(IContext context, StringBuilder buffer, ScriptInfo info) {
        buffer.append("var ").append(info.getNodeName(VarCUR_PERIODTITLE.class)).append(" = new CUR_PERIOD(");
        buffer.append("\"").append(this.getVarName()).append("\",").append("\"").append(this.getVarTitle()).append("\",");
        buffer.append("\"").append(this.getDataType()).append("\"");
        buffer.append(");");
    }
}

