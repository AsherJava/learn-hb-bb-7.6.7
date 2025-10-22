/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.context.infc.INRContext
 *  com.jiuqi.nr.jtable.annotation.JtableLog
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.LogParam
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LogParam;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BatchPrintInfo
extends JtableLog
implements Serializable,
INRContext {
    private static final long serialVersionUID = 1L;
    private JtableContext context;
    private String printSchemeKey;
    private String printerID;
    private boolean printEmptyTable;
    private boolean exportZero;
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

    public List<String> getFormKeys() {
        String formKey = this.context.getFormKey();
        if (null == formKey || "".equals(formKey)) {
            return new ArrayList<String>();
        }
        return Arrays.asList(formKey.split(";"));
    }

    public String getPrintSchemeKey() {
        return this.printSchemeKey;
    }

    public void setPrintSchemeKey(String printSchemeKey) {
        this.printSchemeKey = printSchemeKey;
    }

    public String getPrinterID() {
        return this.printerID;
    }

    public void setPrinterID(String printerID) {
        this.printerID = printerID;
    }

    public boolean isPrintEmptyTable() {
        return this.printEmptyTable;
    }

    public void setPrintEmptyTable(boolean printEmptyTable) {
        this.printEmptyTable = printEmptyTable;
    }

    public boolean isExportZero() {
        return this.exportZero;
    }

    public void setExportZero(boolean exportZero) {
        this.exportZero = exportZero;
    }

    public LogParam getLogParam() {
        LogParam logParam = new LogParam();
        logParam.setModule("\u6570\u636e\u5f55\u5165");
        logParam.setTitle("\u6267\u884c\u6279\u91cf\u6253\u5370");
        Map other = logParam.getOrherMsg();
        if (!this.getFormKeys().isEmpty()) {
            other.put("formKeys", this.getFormKeys());
        } else {
            other.put("formKeys", "all");
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

