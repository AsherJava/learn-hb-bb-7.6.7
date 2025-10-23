/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.print.service;

import com.jiuqi.nr.print.web.vo.ExcelPrintSettingVO;

public interface IExcelPrintSettingService {
    public ExcelPrintSettingVO get(String var1, String var2);

    public void save(ExcelPrintSettingVO var1);

    public ExcelPrintSettingVO reset(String var1, String var2);

    public void delete(String var1);
}

