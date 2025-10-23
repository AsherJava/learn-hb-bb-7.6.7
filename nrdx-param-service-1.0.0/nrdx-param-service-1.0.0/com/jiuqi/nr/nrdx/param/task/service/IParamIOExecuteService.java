/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.nrdx.adapter.dto.IParamVO
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.nrdx.param.task.service;

import com.jiuqi.nr.nrdx.adapter.dto.IParamVO;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IParamIOExecuteService {
    public void exportNRDXParam(NrdxTransferContext var1, HttpServletResponse var2) throws Exception;

    public String uploadNRDXParam(MultipartFile var1) throws Exception;

    public void importNRDXParam(IParamVO var1) throws Exception;
}

