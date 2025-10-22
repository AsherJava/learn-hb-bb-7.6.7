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
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LogParam;
import com.jiuqi.util.StringUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NodeCheckInfo
extends JtableLog
implements Serializable,
INRContext {
    private static final long serialVersionUID = 1L;
    private JtableContext context;
    private boolean recursive;
    private BigDecimal errorRange;
    private String formKeys;
    private String contextTaskKey;
    private String contextEntityId;
    private String contextFormSchemeKey;
    private String contextFilterExpression;

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(String formKeys) {
        this.formKeys = formKeys;
    }

    public boolean isRecursive() {
        return this.recursive;
    }

    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    public BigDecimal getErrorRange() {
        return this.errorRange;
    }

    public void setErrorRange(BigDecimal errorRange) {
        this.errorRange = errorRange;
    }

    public LogParam getLogParam() {
        LogParam logParam = new LogParam();
        logParam.setModule("\u6570\u636e\u5f55\u5165");
        logParam.setKeyInfo("\u8282\u70b9\u68c0\u67e5");
        logParam.setTitle("\u6267\u884c\u8282\u70b9\u68c0\u67e5");
        Map other = logParam.getOrherMsg();
        other.put("errorRange", this.errorRange);
        if (StringUtils.isNotEmpty((String)this.getFormKeys())) {
            other.put("formKeys", this.getFormKeys());
        } else {
            other.put("formKeys", "all");
        }
        if (this.recursive) {
            logParam.setTitle("\u6267\u884c\u5c42\u5c42\u8282\u70b9\u68c0\u67e5");
        }
        return logParam;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }

    public List<Variable> getVariables() {
        if (this.context != null) {
            return this.context.getVariables();
        }
        return new ArrayList<Variable>();
    }
}

