/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.designer.helper;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.designer.web.facade.FormulaObj;
import com.jiuqi.nr.designer.web.service.TaskDesignerService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveFormulasHelper {
    @Autowired
    private TaskDesignerService taskDesignerService;

    public void saveFormulaObj(Map<String, FormulaObj> formulas) throws JQException, ParseException {
        if (formulas != null) {
            FormulaObj[] formulaArr = new FormulaObj[formulas.size()];
            int idx = 0;
            for (Map.Entry<String, FormulaObj> entry : formulas.entrySet()) {
                formulaArr[idx++] = entry.getValue();
            }
            this.taskDesignerService.saveFormulas(formulaArr);
        }
    }
}

