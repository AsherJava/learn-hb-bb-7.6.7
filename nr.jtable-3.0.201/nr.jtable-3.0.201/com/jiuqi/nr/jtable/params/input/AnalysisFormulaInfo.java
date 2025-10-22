/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.context.infc.INRContext
 */
package com.jiuqi.nr.jtable.params.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AnalysisFormulaInfo
implements Serializable,
INRContext {
    private static final long serialVersionUID = 1L;
    private JtableContext context;
    private List<String> formulas;
    private boolean zoneRetrun;
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

    public List<String> getFormulas() {
        return this.formulas;
    }

    public void setFormulas(List<String> formulas) {
        this.formulas = formulas;
    }

    public boolean isZoneRetrun() {
        return this.zoneRetrun;
    }

    public void setZoneRetrun(boolean zoneRetrun) {
        this.zoneRetrun = zoneRetrun;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}

