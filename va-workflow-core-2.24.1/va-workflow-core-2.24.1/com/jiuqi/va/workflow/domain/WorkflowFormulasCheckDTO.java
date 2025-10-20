/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.domain.workflow.ProcessParam
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.workflow.domain;

import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.domain.workflow.ProcessParam;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;

public class WorkflowFormulasCheckDTO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private List<ProcessParam> processParams;
    private List<FormulaImpl> formulas;

    public List<ProcessParam> getProcessParams() {
        return this.processParams;
    }

    public void setProcessParams(List<ProcessParam> processParams) {
        this.processParams = processParams;
    }

    public List<FormulaImpl> getFormulas() {
        return this.formulas;
    }

    public void setFormulas(List<FormulaImpl> formulas) {
        this.formulas = formulas;
    }
}

