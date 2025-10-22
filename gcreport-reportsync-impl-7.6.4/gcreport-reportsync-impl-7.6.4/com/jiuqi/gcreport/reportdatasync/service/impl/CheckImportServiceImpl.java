/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.param.transfer.definition.check.CheckImportService
 *  com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaDTO
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.gcreport.reportdatasync.service.impl;

import com.jiuqi.nr.param.transfer.definition.check.CheckImportService;
import com.jiuqi.nr.param.transfer.definition.dto.formula.FormulaDTO;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckImportServiceImpl
implements CheckImportService {
    @Autowired
    private INvwaSystemOptionService nvwaSystemOptionService;

    public boolean checkImportLanguage() {
        String option = this.nvwaSystemOptionService.get("GC_MULTILEVEL_OPTION_DECLARE", "MULTILEVEL_IMPORT_MULTILANGUAGE");
        return "1".equals(option);
    }

    public List<FormulaDTO> checkImportFormula(List<FormulaDTO> formulaDefines, String formulaSchemeKey, String formKey) {
        return null;
    }
}

