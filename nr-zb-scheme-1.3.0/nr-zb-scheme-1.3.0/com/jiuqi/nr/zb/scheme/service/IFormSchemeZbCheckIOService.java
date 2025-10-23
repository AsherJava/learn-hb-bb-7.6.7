/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.service;

import com.jiuqi.nr.zb.scheme.web.vo.ZbCheckExportParam;
import org.apache.poi.ss.usermodel.Workbook;

public interface IFormSchemeZbCheckIOService {
    public void exportExcel(ZbCheckExportParam var1, Workbook var2);
}

