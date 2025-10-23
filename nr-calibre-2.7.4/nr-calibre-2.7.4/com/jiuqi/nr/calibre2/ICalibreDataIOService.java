/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.calibre2;

import com.jiuqi.np.common.exception.JQException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ICalibreDataIOService {
    public void exportCalibreData(String var1, HttpServletResponse var2) throws JQException;

    public void importCalibreData(MultipartFile var1, String var2) throws JQException;
}

