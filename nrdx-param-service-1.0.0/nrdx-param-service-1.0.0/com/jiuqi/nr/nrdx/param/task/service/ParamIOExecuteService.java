/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.Desc
 *  com.jiuqi.bi.transfer.engine.TransferEngine
 *  com.jiuqi.bi.transfer.engine.TransferUtils
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IFileRecorder
 *  com.jiuqi.bi.transfer.engine.intf.ITransferContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.nrdx.adapter.dto.IParamVO
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext
 *  com.jiuqi.nr.nrdx.adapter.param.service.IParamIOService
 *  com.jiuqi.nr.nrdx.adapter.utils.FileHelper
 *  com.jiuqi.nvwa.transfer.TransferContext
 *  com.jiuqi.nvwa.transfer.TransferFileRecorder
 *  javax.servlet.http.HttpServletResponse
 *  org.json.JSONArray
 *  org.json.JSONObject
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.nrdx.param.task.service;

import com.jiuqi.bi.transfer.engine.Desc;
import com.jiuqi.bi.transfer.engine.TransferEngine;
import com.jiuqi.bi.transfer.engine.TransferUtils;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IFileRecorder;
import com.jiuqi.bi.transfer.engine.intf.ITransferContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.nrdx.adapter.dto.IParamVO;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext;
import com.jiuqi.nr.nrdx.adapter.param.service.IParamIOService;
import com.jiuqi.nr.nrdx.adapter.utils.FileHelper;
import com.jiuqi.nr.nrdx.param.task.service.IParamIOExecuteService;
import com.jiuqi.nvwa.transfer.TransferContext;
import com.jiuqi.nvwa.transfer.TransferFileRecorder;
import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ParamIOExecuteService
implements IParamIOExecuteService {
    @Autowired
    IParamIOService paramIOService;

    @Override
    public void exportNRDXParam(NrdxTransferContext transferContext, HttpServletResponse response) throws Exception {
        File file = this.paramIOService.exportFile(transferContext, null);
        FileHelper.exportFile((File)file, (String)file.getName(), (HttpServletResponse)response);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String uploadNRDXParam(MultipartFile file) throws Exception {
        String userId = NpContextHolder.getContext().getUserId();
        JSONObject result = new JSONObject();
        TransferContext context = new TransferContext(userId);
        try (InputStream inputStream = file.getInputStream();){
            TransferFileRecorder recorder = new TransferFileRecorder(inputStream, (ITransferContext)context);
            TransferEngine engine = new TransferEngine();
            Desc desc = engine.getDescInfo((IFileRecorder)recorder);
            JSONArray dataArray = TransferUtils.buildTreeTableData((ITransferContext)context, (Desc)desc, (IFileRecorder)recorder);
            result.put("code", 200);
            result.put("data", (Object)dataArray);
            result.put("fileId", (Object)recorder.getFilekey());
            result.put("exportTime", (Object)desc.getExportTime());
            result.put("productLines", (Collection)desc.getProductLineBeanList());
            String string = result.toString();
            return string;
        }
        catch (Exception e) {
            throw new TransferException("\u8bfb\u53d6\u6587\u4ef6\u6d41\u5931\u8d25", (Throwable)e);
        }
    }

    @Override
    public void importNRDXParam(IParamVO paramVO) throws Exception {
        this.paramIOService.importFile(paramVO, null);
    }
}

