/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.infc.INRContext
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;
import java.util.Map;

public class FmlMonitorAndDebugParam
implements INRContext {
    private static final long serialVersionUID = 1L;
    private JtableContext context;
    private String fmlType;
    private List<String> zbList;
    private Map<String, List<String>> form_formula;
    private String dataLinkKey;
    private String fieldKey;
    private String expressionKey;
    private Map<String, String> rowDimMap;
    private String contextTaskKey;
    private String contextEntityId;
    private String contextFormSchemeKey;
    private String contextFilterExpression;

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getFmlType() {
        return this.fmlType;
    }

    public void setFmlType(String fmlType) {
        this.fmlType = fmlType;
    }

    public List<String> getZbList() {
        return this.zbList;
    }

    public void setZbList(List<String> zbList) {
        this.zbList = zbList;
    }

    public Map<String, List<String>> getForm_formula() {
        return this.form_formula;
    }

    public void setForm_formula(Map<String, List<String>> form_formula) {
        this.form_formula = form_formula;
    }

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getExpressionKey() {
        return this.expressionKey;
    }

    public void setExpressionKey(String expressionKey) {
        this.expressionKey = expressionKey;
    }

    public Map<String, String> getRowDimMap() {
        return this.rowDimMap;
    }

    public void setRowDimMap(Map<String, String> rowDimMap) {
        this.rowDimMap = rowDimMap;
    }
}

