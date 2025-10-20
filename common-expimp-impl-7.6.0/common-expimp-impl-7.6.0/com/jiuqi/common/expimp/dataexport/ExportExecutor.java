/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.common.expimp.dataexport;

import com.jiuqi.common.expimp.common.ExpImpFileTypeEnum;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ExportExecutor {
    public String getName();

    public ExpImpFileTypeEnum getFileType();

    public Object dataExport(HttpServletRequest var1, HttpServletResponse var2, ExportContext var3) throws Exception;
}

