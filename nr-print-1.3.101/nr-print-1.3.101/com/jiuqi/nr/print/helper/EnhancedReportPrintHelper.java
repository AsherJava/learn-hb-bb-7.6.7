/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.print.helper;

import com.jiuqi.nr.print.service.IPrintCommonTemService;
import com.jiuqi.nr.print.service.IPrintDesignExtendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class EnhancedReportPrintHelper {
    private static IPrintDesignExtendService reportPrintExtendService;
    private static IPrintCommonTemService attributeService;

    @Autowired
    public void setAttributeService(IPrintCommonTemService attributeService) {
        EnhancedReportPrintHelper.attributeService = attributeService;
    }

    @Autowired
    @Qualifier(value="PrintBaseImpl")
    public void IReportPrintExtendService(IPrintDesignExtendService reportPrintDesignExtendService) {
        reportPrintExtendService = reportPrintDesignExtendService;
    }

    public static IPrintDesignExtendService getReportPrintExtendService() {
        return reportPrintExtendService;
    }

    public static IPrintCommonTemService getAttributeService() {
        return attributeService;
    }
}

