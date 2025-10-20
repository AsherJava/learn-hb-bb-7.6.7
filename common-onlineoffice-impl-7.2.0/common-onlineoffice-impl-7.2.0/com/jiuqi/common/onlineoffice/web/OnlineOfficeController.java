/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.file.service.CommonFileService
 *  com.jiuqi.common.onlineoffice.api.OnlineOfficeClient
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.common.onlineoffice.web;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.file.service.CommonFileService;
import com.jiuqi.common.onlineoffice.api.OnlineOfficeClient;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class OnlineOfficeController
implements OnlineOfficeClient {
    @Autowired
    private CommonFileService commonFileService;

    public void download(HttpServletResponse response, HttpServletRequest request, String docFileKey) {
        try {
            this.commonFileService.downloadOssFile("ONLINE_OFFICE", request, response, docFileKey);
        }
        catch (Exception e) {
            Throwable cause = e.getCause();
            String exMsg = cause == null ? e.getMessage() : cause.getMessage();
            throw new BusinessRuntimeException(exMsg, (Throwable)e);
        }
    }
}

