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
import com.jiuqi.gcreport.clbrbill.dispatcher.operate.ClbrUpdateBusinessDataOperateDispatcher;
import com.jiuqi.gcreport.clbrbill.dispatcher.operate.ClbrUpdateBusinessDataOperateHandler;
import com.jiuqi.gcreport.clbrbill.dto.ClbrUpdateDataDTO;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

public abstract class AbstractUpdateBusinessDataHandler
implements ClbrBusinessHandler<ClbrUpdateDataDTO, Object> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractUpdateBusinessDataHandler.class);
    @Autowired
    ClbrUpdateBusinessDataOperateDispatcher clbrUpdateBusinessDataOperateDispatcher;

    @Override
    public final String getBusinessCode() {
        return "UPDATEBUSINESSDATA";
    }

    @Override
    public ClbrUpdateDataDTO beforeHandler(Object content) {
        Map map = (Map)content;
        Map<String, Object> upperKeyMap = map.entrySet().stream().collect(Collectors.toMap(entry -> ((String)entry.getKey()).toUpperCase(Locale.ROOT), Map.Entry::getValue));
        String clbrCode = ConverterUtils.getAsString((Object)upperKeyMap.get("CLBRCODE"));
        if (StringUtils.isEmpty((String)clbrCode)) {
            LOGGER.info("\u534f\u540c\u4e1a\u52a1\u7c7b\u578b\uff1a{}\uff0c\u8bf7\u6c42\u53c2\u6570\u4e2dclbrCode\u4e3a\u7a7a\uff0c\u539f\u59cb\u6570\u636e\uff1a{}", (Object)this.getBusinessCode(), (Object)JsonUtils.writeValueAsString((Object)content));
            throw new BusinessRuntimeException("clbrCode\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String srcBillCode = ConverterUtils.getAsString((Object)upperKeyMap.get("SRCBILLCODE"));
        if (StringUtils.isEmpty((String)srcBillCode)) {
            LOGGER.info("\u534f\u540c\u4e1a\u52a1\u7c7b\u578b\uff1a{}\uff0c\u8bf7\u6c42\u53c2\u6570\u4e2dsrcBillCode\u4e3a\u7a7a\uff0c\u539f\u59cb\u6570\u636e\uff1a{}", (Object)this.getBusinessCode(), (Object)JsonUtils.writeValueAsString((Object)content));
            throw new BusinessRuntimeException("srcBillCode\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String unitCode = ConverterUtils.getAsString((Object)upperKeyMap.get("UNITCODE"));
        if (StringUtils.isEmpty((String)unitCode)) {
            LOGGER.info("\u534f\u540c\u4e1a\u52a1\u7c7b\u578b\uff1a{}\uff0c\u8bf7\u6c42\u53c2\u6570\u4e2dunitCode\u4e3a\u7a7a\uff0c\u539f\u59cb\u6570\u636e\uff1a{}", (Object)this.getBusinessCode(), (Object)JsonUtils.writeValueAsString((Object)content));
            throw new BusinessRuntimeException("unitCode\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String operateType = ConverterUtils.getAsString((Object)upperKeyMap.get("OPERATETYPE"));
        if (StringUtils.isEmpty((String)operateType)) {
            LOGGER.info("\u534f\u540c\u4e1a\u52a1\u7c7b\u578b\uff1a{}\uff0c\u8bf7\u6c42\u53c2\u6570\u4e2doperateType\u4e3a\u7a7a\uff0c\u539f\u59cb\u6570\u636e\uff1a{}", (Object)this.getBusinessCode(), (Object)JsonUtils.writeValueAsString((Object)content));
            throw new BusinessRuntimeException("operateType\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String operateTime = ConverterUtils.getAsString((Object)upperKeyMap.get("OPERATETIME"));
        if (StringUtils.isEmpty((String)operateTime)) {
            LOGGER.info("\u534f\u540c\u4e1a\u52a1\u7c7b\u578b\uff1a{}\uff0c\u8bf7\u6c42\u53c2\u6570\u4e2doperateTime\u4e3a\u7a7a\uff0c\u539f\u59cb\u6570\u636e\uff1a{}", (Object)this.getBusinessCode(), (Object)JsonUtils.writeValueAsString((Object)content));
            throw new BusinessRuntimeException("operateTime\u4e0d\u80fd\u4e3a\u7a7a");
        }
        Map params = (Map)upperKeyMap.get("PARAMS");
        if (ObjectUtils.isEmpty(params)) {
            LOGGER.info("\u534f\u540c\u4e1a\u52a1\u7c7b\u578b\uff1a{}\uff0c\u8bf7\u6c42\u53c2\u6570\u4e2dparams\u4e3a\u7a7a\uff0c\u539f\u59cb\u6570\u636e\uff1a{}", (Object)this.getBusinessCode(), (Object)JsonUtils.writeValueAsString((Object)content));
            throw new BusinessRuntimeException("params\u4e0d\u80fd\u4e3a\u7a7a");
        }
        Map<String, Object> paramUpperKeyMap = params.entrySet().stream().collect(Collectors.toMap(entry -> ((String)entry.getKey()).toUpperCase(Locale.ROOT), Map.Entry::getValue));
        ClbrUpdateDataDTO dto = new ClbrUpdateDataDTO();
        dto.setClbrCode(clbrCode);
        dto.setSrcBillCode(srcBillCode);
        dto.setUnitCode(unitCode);
        dto.setOperateType(operateType);
        dto.setOperateTime(operateTime);
        dto.setParams(paramUpperKeyMap);
        return dto;
    }

    @Override
    public final Object handler(ClbrUpdateDataDTO dto) {
        ClbrUpdateBusinessDataOperateHandler operateTypeHandler = this.clbrUpdateBusinessDataOperateDispatcher.dispatch(dto.getOperateType());
        operateTypeHandler.handler(dto);
        return null;
    }

    @Override
    public Object afterHandler(ClbrUpdateDataDTO content, Object result) {
        return result;
    }
}

