/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.clbrbill.dispatcher.business;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.clbrbill.dispatcher.business.ClbrBusinessHandler;
import com.jiuqi.gcreport.clbrbill.dto.ClbrCancelQuoteDTO;
import com.jiuqi.gcreport.clbrbill.service.ClbrBillBusinessService;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractCancelQuoteHandler
implements ClbrBusinessHandler<ClbrCancelQuoteDTO, Object> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCancelQuoteHandler.class);
    @Autowired
    ClbrBillBusinessService clbrBillBusinessService;

    @Override
    public final String getBusinessCode() {
        return "CANCELQUOTE";
    }

    @Override
    public ClbrCancelQuoteDTO beforeHandler(Object content) {
        ClbrCancelQuoteDTO dto = new ClbrCancelQuoteDTO();
        Map map = (Map)content;
        Map<String, Object> upperKeyMap = map.entrySet().stream().collect(Collectors.toMap(entry -> ((String)entry.getKey()).toUpperCase(Locale.ROOT), Map.Entry::getValue));
        String clbrCode = ConverterUtils.getAsString((Object)upperKeyMap.get("CLBRCODE"));
        if (StringUtils.isEmpty((String)clbrCode)) {
            LOGGER.info("\u534f\u540c\u4e1a\u52a1\u7c7b\u578b\uff1a{}\uff0c\u8bf7\u6c42\u53c2\u6570\u4e2dclbrCode\u4e3a\u7a7a\uff0c\u539f\u59cb\u6570\u636e\uff1a{}", (Object)this.getBusinessCode(), (Object)JsonUtils.writeValueAsString((Object)content));
            throw new BusinessRuntimeException("\u8bf7\u6c42\u53c2\u6570\u4e2dclbrCode\u4e0d\u80fd\u4e3a\u7a7a");
        }
        dto.setClbrCode(clbrCode);
        String srcBillCode = ConverterUtils.getAsString((Object)upperKeyMap.get("SRCBILLCODE"));
        if (StringUtils.isEmpty((String)srcBillCode)) {
            LOGGER.info("\u534f\u540c\u4e1a\u52a1\u7c7b\u578b\uff1a{}\uff0c\u8bf7\u6c42\u53c2\u6570\u4e2dsrcBillCode\u4e3a\u7a7a\uff0c\u539f\u59cb\u6570\u636e\uff1a{}", (Object)this.getBusinessCode(), (Object)JsonUtils.writeValueAsString((Object)content));
            throw new BusinessRuntimeException("\u8bf7\u6c42\u53c2\u6570\u4e2dsrcBillCode\u4e0d\u80fd\u4e3a\u7a7a");
        }
        dto.setSrcBillCode(srcBillCode);
        return dto;
    }

    @Override
    public final Object handler(ClbrCancelQuoteDTO dto) {
        this.clbrBillBusinessService.cancelQuote(dto);
        return null;
    }

    @Override
    public Object afterHandler(ClbrCancelQuoteDTO content, Object result) {
        return result;
    }
}

