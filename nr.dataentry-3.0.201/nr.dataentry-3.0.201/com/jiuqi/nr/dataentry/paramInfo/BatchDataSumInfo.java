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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BatchDataSumInfo
extends JtableLog
implements INRContext {
    private static final long serialVersionUID = 1L;
    private JtableContext context;
    private String formKeys;
    private List<String> sourceKeys = new ArrayList<String>();
    private boolean recursive;
    private boolean difference;
    private boolean uploadAfterDataSum;
    private boolean ignoreWorkFlow = false;
    private boolean ignoreAuth = false;
    private String periodRegionInfo;
    private String contextTaskKey;
    private String contextEntityId;
    private String contextFormSchemeKey;
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
        return this.formKeys;
    }

    public void setFormKeys(String formKeys) {
        this.formKeys = formKeys;
    }

    public List<String> getSourceKeys() {
        return this.sourceKeys;
    }

    public void setSourceKeys(List<String> sourceKeys) {
        this.sourceKeys = sourceKeys;
    }

    public boolean isRecursive() {
        return this.recursive;
    }

    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    public boolean isDifference() {
        return this.difference;
    }

    public void setDifference(boolean difference) {
        this.difference = difference;
    }

    public boolean isUploadAfterDataSum() {
        return this.uploadAfterDataSum;
    }

    public void setUploadAfterDataSum(boolean uploadAfterDataSum) {
        this.uploadAfterDataSum = uploadAfterDataSum;
    }

    public boolean isIgnoreAuth() {
        return this.ignoreAuth;
    }

    public void setIgnoreAuth(boolean ignoreAuth) {
        this.ignoreAuth = ignoreAuth;
    }

    public LogParam getLogParam() {
        LogParam logParam = new LogParam();
        logParam.setModule("\u6570\u636e\u5f55\u5165");
        logParam.setTitle("\u6267\u884c\u6c47\u603b");
        logParam.setKeyInfo("\u6c47\u603b");
        Map other = logParam.getOrherMsg();
        if (!this.sourceKeys.isEmpty()) {
            logParam.setTitle("\u6267\u884c\u9009\u62e9\u6c47\u603b");
            logParam.setKeyInfo("\u9009\u62e9\u6c47\u603b");
            StringBuilder sour = new StringBuilder();
            this.sourceKeys.forEach(e -> sour.append((String)e).append(";"));
            other.put("sourceKeys", sour.substring(0, sour.length() - 1));
        }
        if (StringUtils.isNotEmpty((String)this.getFormKeys())) {
            other.put("formKeys", this.getFormKeys());
        } else {
            other.put("formKeys", "all");
        }
        if (this.recursive) {
            other.put("recursive", "\u6240\u6709\u4e0b\u7ea7");
        } else {
            other.put("recursive", "\u76f4\u63a5\u4e0b\u7ea7");
        }
        if (this.difference) {
            logParam.setTitle("\u6267\u884c\u5dee\u989d\u6c47\u603b");
        }
        return logParam;
    }

    public boolean isIgnoreWorkFlow() {
        return this.ignoreWorkFlow;
    }

    public void setIgnoreWorkFlow(boolean ignoreWorkFlow) {
        this.ignoreWorkFlow = ignoreWorkFlow;
    }

    public String getPeriodRegionInfo() {
        return this.periodRegionInfo;
    }

    public void setPeriodRegionInfo(String periodRegionInfo) {
        this.periodRegionInfo = periodRegionInfo;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}

