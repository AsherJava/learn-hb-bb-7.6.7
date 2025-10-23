/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.print.service;

import org.springframework.web.multipart.MultipartFile;

public interface IPrintTemplateIOService {
    public void printTemplateImport(MultipartFile var1, String var2);

    public void printTemplateIncrementImport(MultipartFile var1, String var2);

    public String printTemplateExport(String var1);

    public String printTemplateExportName(String var1);

    public String printTemplateExportName(String var1, String var2);
}

