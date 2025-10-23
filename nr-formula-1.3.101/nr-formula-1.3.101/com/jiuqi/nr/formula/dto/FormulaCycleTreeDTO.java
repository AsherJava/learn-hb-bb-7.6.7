/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.formulamapping.facade.Data
 *  com.jiuqi.nr.definition.formulamapping.facade.TreeObj
 */
package com.jiuqi.nr.formula.dto;

import com.jiuqi.nr.definition.formulamapping.facade.Data;
import com.jiuqi.nr.definition.formulamapping.facade.TreeObj;
import com.jiuqi.nr.formula.dto.FormulaCycleDataDTO;

public class FormulaCycleTreeDTO
extends TreeObj {
    private FormulaCycleDataDTO data;

    public Data getData() {
        return this.data;
    }

    public void setData(Data data) {
        this.data = (FormulaCycleDataDTO)data;
    }
}

