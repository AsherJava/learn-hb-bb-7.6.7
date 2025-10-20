/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.biz.domain;

import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FormulasCheckDTO
extends TenantDO {
    private static final long serialVersionUID = -8508193974477856063L;
    private ModelDefine modelDefine;
    private List<FormulaImpl> formulas;
    private Map<UUID, Integer> resultTypes;

    public ModelDefine getModelDefine() {
        return this.modelDefine;
    }

    public void setModelDefine(ModelDefine modelDefine) {
        this.modelDefine = modelDefine;
    }

    public List<FormulaImpl> getFormulas() {
        return this.formulas;
    }

    public void setFormulas(List<FormulaImpl> formulas) {
        this.formulas = formulas;
    }

    public int getResultTypes(UUID uuid) {
        if (this.resultTypes == null || this.resultTypes.get(uuid) == null) {
            return 0;
        }
        return this.resultTypes.get(uuid);
    }

    public void setResultTypes(Map<UUID, Integer> resultTypes) {
        this.resultTypes = resultTypes;
    }
}

