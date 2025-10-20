/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.bde.penetrate.impl.service;

import com.jiuqi.bde.penetrate.impl.expimp.common.PenetrateExcelExportParam;
import javax.servlet.http.HttpServletResponse;

public interface CommonPenetrateExportExcelService {
    public String exportExcel(HttpServletResponse var1, boolean var2, PenetrateExcelExportParam var3);
}

