/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.bi.transfer.engine.TransferImportResult
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.listener.packet.IPacketImportContext
 *  com.jiuqi.bi.transfer.engine.listener.packet.IPacketImportListener
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.nrdx.adapter.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.bi.transfer.engine.TransferImportResult;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.listener.packet.IPacketImportContext;
import com.jiuqi.bi.transfer.engine.listener.packet.IPacketImportListener;
import com.jiuqi.nr.nrdx.adapter.dto.IParamVO;
import com.jiuqi.nr.nrdx.adapter.dto.IResultVO;
import com.jiuqi.nr.nrdx.adapter.listener.dto.HandlerParam;
import com.jiuqi.nr.nrdx.adapter.listener.handler.WorkflowHandler;
import com.jiuqi.util.StringUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NrdxImportListener
implements IPacketImportListener {
    @Autowired
    private WorkflowHandler workflowHandler;
    private static final Logger logger = LoggerFactory.getLogger(NrdxImportListener.class);

    public void beforeImportPackage(IPacketImportContext context) throws TransferException {
    }

    public void afterImportPackage(IPacketImportContext context) throws TransferException {
        IProgressMonitor progressMonitor = context.getProgressMonitor();
        Object param = context.getExtInfo().get("i_args");
        TransferImportResult transferImportResult = (TransferImportResult)((Map)context.getImportResult().getOrDefault("TASK_DATA", new HashMap())).get("i_detail");
        String resultString = Optional.ofNullable(transferImportResult).map(TransferImportResult::getMsg).orElse("");
        HandlerParam handlerParam = new HandlerParam();
        handlerParam.setProgressMonitor(progressMonitor);
        try {
            IResultVO resultObj;
            IParamVO paramObj;
            ObjectMapper objectMapper = new ObjectMapper();
            IParamVO iParamVO = paramObj = param != null ? (IParamVO)objectMapper.readValue(param.toString(), IParamVO.class) : null;
            if (paramObj != null) {
                handlerParam.setRecordKey(paramObj.getRecKey());
                handlerParam.setTaskKey(paramObj.getTaskKey());
                handlerParam.setFormSchemeKey(paramObj.getFormSchemeKey());
                handlerParam.setDoUpload(paramObj.isDoUpload());
                handlerParam.setAllowForceUpload(paramObj.isAllowForceUpload());
                handlerParam.setUploadDes(paramObj.getUploadDes());
            }
            IResultVO iResultVO = resultObj = StringUtils.isNotEmpty((String)resultString) ? (IResultVO)objectMapper.readValue(resultString, IResultVO.class) : null;
            if (resultObj != null) {
                handlerParam.getDimensionValueMap().putAll(resultObj.getDimensionValueMap());
                handlerParam.getForms().addAll(resultObj.getForms());
            }
        }
        catch (JsonProcessingException e) {
            logger.error("\u7ec4\u88c5\u5bfc\u5165\u5b8c\u6210\u4e8b\u4ef6\u6240\u9700\u53c2\u6570\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38\uff1a{}\uff01", (Object)e.getMessage(), (Object)e);
            throw new TransferException("\u7ec4\u88c5\u5bfc\u5165\u5b8c\u6210\u4e8b\u4ef6\u6240\u9700\u53c2\u6570\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38\uff1a" + e.getMessage(), (Throwable)e);
        }
        if (handlerParam.isDoUpload()) {
            this.workflowHandler.doWorkflowUpload(handlerParam);
        }
    }
}

