/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.print.service;

import com.jiuqi.nr.print.common.PrintSchemeMoveType;
import com.jiuqi.nr.print.web.vo.PrintSchemeVo;
import java.util.List;

public interface IPrintSchemeService {
    public PrintSchemeVo getDefaultPrintScheme(String var1, String var2);

    public void initDefaultPrintScheme(String var1, String var2);

    public List<PrintSchemeVo> listPrintSchemeByFormScheme(String var1);

    public String insertPrintScheme(PrintSchemeVo var1) throws Exception;

    public void updatePrintScheme(PrintSchemeVo var1);

    public String copyPrintScheme(PrintSchemeVo var1);

    public void deletePrintScheme(String var1);

    public void deletePrintSchemeByFormScheme(String var1);

    public void printSchemeMove(String var1, String var2, PrintSchemeMoveType var3);

    public void exchangeOrder(String var1, String var2);

    public void deleteTemplateByPrintScheme(String var1);

    public void checkTitle(PrintSchemeVo var1);
}

