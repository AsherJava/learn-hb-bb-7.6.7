/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nr.definition.controller.IPrintDesignTimeController
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.print.vo.PrintAttributeVo
 */
package com.jiuqi.nr.query.print;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.print.vo.PrintAttributeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryPrintSchemeService {
    private static final String DEFAULT_PRINTSCHEME_TITLE = "\u9ed8\u8ba4\u6253\u5370\u65b9\u6848";
    @Autowired
    private IPrintDesignTimeController printController;

    public DesignPrintTemplateSchemeDefine createDefaultPrintScheme() throws Exception {
        DesignPrintTemplateSchemeDefine designPirntSchemeDefine = this.printController.createPrintTemplateSchemeDefine();
        designPirntSchemeDefine.setTitle(DEFAULT_PRINTSCHEME_TITLE);
        designPirntSchemeDefine.setOrder(OrderGenerator.newOrder());
        this.printController.setPrintSchemeAttribute(designPirntSchemeDefine, PrintAttributeVo.defaultAttributeDefine());
        return designPirntSchemeDefine;
    }
}

