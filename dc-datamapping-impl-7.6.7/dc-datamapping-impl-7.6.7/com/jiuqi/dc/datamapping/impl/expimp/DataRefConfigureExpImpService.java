/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.datamapping.client.dto.DataRefListDTO
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.dc.datamapping.impl.expimp;

import com.jiuqi.dc.datamapping.client.dto.DataRefListDTO;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface DataRefConfigureExpImpService {
    public String importExcel(MultipartFile var1, DataRefListDTO var2);

    public String exportExcel(HttpServletResponse var1, boolean var2, DataRefListDTO var3);
}

