/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 */
package com.jiuqi.gcreport.clbrbill.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.clbrbill.dispatcher.business.ClbrBusinessDispatcher;
import com.jiuqi.gcreport.clbrbill.dispatcher.business.ClbrBusinessHandler;
import com.jiuqi.gcreport.clbrbill.dto.ClbrOperatorDTO;
import com.jiuqi.gcreport.clbrbill.service.ClbrBillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

@Service
public class ClbrBillServiceImpl
implements ClbrBillService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClbrBillServiceImpl.class);
    @Autowired
    private ClbrBusinessDispatcher businessCodeHandlerFactory;

    @Override
    public Object clbrOperate(ClbrOperatorDTO params) {
        try {
            ClbrBusinessHandler handler = this.businessCodeHandlerFactory.dispatch(params.getMeta());
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            LOGGER.info("\u534f\u540c\u4e1a\u52a1\u7c7b\u578b\uff1a{}\uff0c\u6765\u6e90\u7cfb\u7edf\u4ee3\u7801\uff1a{}\uff0c\u5f00\u59cb\u8f6c\u6362\u6570\u636e\uff0c\u539f\u59cb\u6570\u636e\uff1a{}", handler.getBusinessCode(), handler.getSysCode(), JsonUtils.writeValueAsString((Object)params.getContent()));
            Object content = handler.beforeHandler(params.getContent());
            LOGGER.info("\u534f\u540c\u4e1a\u52a1\u7c7b\u578b\uff1a{}\uff0c\u6765\u6e90\u7cfb\u7edf\u4ee3\u7801\uff1a{}\uff0c\u5f00\u59cb\u5904\u7406\u6570\u636e\uff0c\u8f6c\u6362\u540e\u6570\u636e\uff1a{}", handler.getBusinessCode(), handler.getSysCode(), JsonUtils.writeValueAsString(content));
            Object result = handler.handler(content);
            LOGGER.info("\u534f\u540c\u4e1a\u52a1\u7c7b\u578b\uff1a{}\uff0c\u6765\u6e90\u7cfb\u7edf\u4ee3\u7801\uff1a{}\uff0c\u5f00\u59cb\u4e1a\u52a1\u5904\u7406\u540e\u903b\u8f91\u3002", (Object)handler.getBusinessCode(), (Object)handler.getSysCode());
            result = handler.afterHandler(content, result);
            stopWatch.stop();
            LOGGER.info("\u534f\u540c\u4e1a\u52a1\u7c7b\u578b\uff1a{}\uff0c\u6765\u6e90\u7cfb\u7edf\u4ee3\u7801\uff1a{}\uff0c\u6570\u636e\u5904\u7406\u5b8c\u6210\uff0c\u8017\u65f6\uff1a[{}]\u79d2\u3002", handler.getBusinessCode(), handler.getSysCode(), stopWatch.getTotalTimeSeconds());
            return result;
        }
        catch (Exception e) {
            LOGGER.error("\u63a5\u53e3\u6267\u884c\u5931\u8d25\uff0c\u8be6\u60c5: {}", (Object)e.getMessage(), (Object)e);
            String errorMessage = "\u63a5\u53e3\u6267\u884c\u5931\u8d25\u3002\u8be6\u60c5: " + e.getMessage();
            throw new BusinessRuntimeException(errorMessage, (Throwable)e);
        }
    }
}

