/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.formulaschemeconfig.enums.FormulaSchemeConfigCategoryEnum
 *  com.jiuqi.gcreport.formulaschemeconfig.intf.IFormulaSchemeConfigCategory
 */
package com.jiuqi.gcreport.formulaschemeconfig.category;

import com.jiuqi.gcreport.formulaschemeconfig.enums.FormulaSchemeConfigCategoryEnum;
import com.jiuqi.gcreport.formulaschemeconfig.intf.IFormulaSchemeConfigCategory;
import org.springframework.stereotype.Component;

@Component
public class NrFormulaSchemeConfigCategory
implements IFormulaSchemeConfigCategory {
    public String getCode() {
        return FormulaSchemeConfigCategoryEnum.REPORT_FETCH.getCode();
    }

    public String getName() {
        return FormulaSchemeConfigCategoryEnum.REPORT_FETCH.getName();
    }

    public String getProdLine() {
        return "@gc";
    }

    public String getAppName() {
        return "gcreport-formulaschemeconfig";
    }

    public Integer getOrder() {
        return 1;
    }
}

