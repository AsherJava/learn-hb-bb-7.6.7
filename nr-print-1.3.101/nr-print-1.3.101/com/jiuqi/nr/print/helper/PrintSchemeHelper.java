/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 */
package com.jiuqi.nr.print.helper;

import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrintSchemeHelper {
    @Autowired
    private IDesignTimePrintController printDesignTimeService;

    public String getCopyPrintSchemeTitle(String originTitle) {
        int index = 0;
        String newPrintSchemeBaseTitle = originTitle;
        String newPrintSchemeTitle = newPrintSchemeBaseTitle + "_\u590d\u5236";
        List printSchemes = this.printDesignTimeService.listAllPrintTemplateScheme();
        if (printSchemes != null && printSchemes.size() > 0) {
            boolean flag = true;
            while (flag) {
                int oldIndex = index;
                for (DesignPrintTemplateSchemeDefine printScheme : printSchemes) {
                    String title = printScheme.getTitle();
                    if (!newPrintSchemeTitle.equals(title)) continue;
                    newPrintSchemeTitle = newPrintSchemeBaseTitle + "_\u590d\u5236" + ++index;
                    break;
                }
                if (oldIndex != index) continue;
                break;
            }
        }
        return newPrintSchemeTitle;
    }
}

