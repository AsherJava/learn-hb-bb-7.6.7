/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.common.subject.impl.subject.service;

import com.jiuqi.common.subject.impl.subject.expimp.intf.impl.SubjectImpMode;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface SubjectExpImpService {
    public String exportExcel(HttpServletResponse var1, boolean var2);

    public String importExcel(MultipartFile var1, SubjectImpMode var2);
}

