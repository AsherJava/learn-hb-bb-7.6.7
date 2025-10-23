/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.param.transfer.definition.check;

import com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaDTO;
import java.util.List;

public interface CheckImportService {
    public boolean checkImportLanguage();

    public List<FormulaDTO> checkImportFormula(List<FormulaDTO> var1, String var2, String var3);
}

