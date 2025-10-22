/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.context.infc.INRContext
 *  com.jiuqi.nr.jtable.annotation.JtableLog
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.LogParam
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LogParam;
import com.jiuqi.util.StringUtils;
import java.util.List;
import java.util.Map;

public class BatchClearInfo
extends JtableLog
implements INRContext {
    private static final long serialVersionUID = 1L;
    private JtableContext context;
    private boolean dbTask = true;
    private String contextEntityId;
    private String contextFilterExpression;

    public List<Variable> getVariables() {
        return this.context.getVariables();
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getFormKeys() {
        return this.context.getFormKey();
    }

    public boolean isDbTask() {
        return this.dbTask;
    }

    public void setDbTask(boolean dbTask) {
        this.dbTask = dbTask;
    }

    public LogParam getLogParam() {
        LogParam logParam = new LogParam();
        logParam.setModule("\u6570\u636e\u5f55\u5165");
        logParam.setTitle("\u6267\u884c\u6279\u91cf\u6e05\u9664");
        Map other = logParam.getOrherMsg();
        if (StringUtils.isNotEmpty((String)this.getFormKeys())) {
            other.put("formKeys", this.getFormKeys());
        }
        return logParam;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}

