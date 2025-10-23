/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.mapping2.service;

import com.jiuqi.nr.mapping2.web.vo.Result;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ZBMappingImpExpService {
    public Result importMapping(String var1, String var2, String var3, MultipartFile var4);

    public void export(String var1, String var2, List<String> var3, HttpServletResponse var4) throws Exception;
}

