/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.service;

import com.jiuqi.nr.designer.web.rest.vo.PrintSchemeVo;
import java.util.List;

public interface IPrintSchemeService {
    public PrintSchemeVo queryDefaultPrintScheme(String var1, String var2) throws Exception;

    public String createDefaultPrintScheme(String var1, String var2) throws Exception;

    public List<PrintSchemeVo> queryPrintSchemes(String var1) throws Exception;

    public String addPrintScheme(PrintSchemeVo var1) throws Exception;

    public void updatePrintScheme(PrintSchemeVo var1) throws Exception;

    public String copyPrintScheme(PrintSchemeVo var1) throws Exception;

    public void deletePrintScheme(String var1) throws Exception;

    public void deleteAllPrintSchemeByFormScheme(String var1) throws Exception;

    public void exchangeOrder(String var1, String var2) throws Exception;

    public void deleteAllPrintSchemeByTask(String var1) throws Exception;

    public void deleteTempleteByPrintScheme(String var1) throws Exception;
}

