/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.task.i18n.service;

import com.jiuqi.nr.task.i18n.bean.vo.I18nExportVO;
import com.jiuqi.nr.task.i18n.exception.I18nException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface I18nIOService {
    public void i18nImport(String var1) throws I18nException;

    public void i18nExport(I18nExportVO var1, HttpServletResponse var2) throws I18nException;

    public String i18nUpload(MultipartFile var1) throws I18nException;
}

