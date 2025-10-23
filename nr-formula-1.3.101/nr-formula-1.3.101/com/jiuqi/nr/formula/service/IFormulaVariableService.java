/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.service;

import com.jiuqi.nr.formula.dto.VariableDTO;
import java.util.List;

public interface IFormulaVariableService {
    public void insertVariable(VariableDTO var1);

    public void updateVariable(VariableDTO var1);

    public void deleteVariable(String var1);

    public boolean existVariable(String var1, String var2, String var3);

    public List<VariableDTO> listVariablesByFormScheme(String var1);
}

