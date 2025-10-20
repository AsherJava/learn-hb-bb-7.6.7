/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigCategoryDTO
 */
package com.jiuqi.gcreport.formulaschemeconfig.service;

import com.jiuqi.gcreport.formulaschemeconfig.dto.FormulaSchemeConfigCategoryDTO;
import java.util.List;

public interface FormulaSchemeConfigCategoryService {
    public List<FormulaSchemeConfigCategoryDTO> listAllCategoryAppInfo();

    public List<FormulaSchemeConfigCategoryDTO> listCategory();
}

