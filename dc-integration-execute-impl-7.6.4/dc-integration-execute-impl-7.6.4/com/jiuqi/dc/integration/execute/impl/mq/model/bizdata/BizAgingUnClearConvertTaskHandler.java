/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather
 *  com.jiuqi.dc.mappingscheme.impl.service.BizDataRefDefineService
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 *  com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult
 */
package com.jiuqi.dc.integration.execute.impl.mq.model.bizdata;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.integration.execute.impl.data.DataConvertResult;
import com.jiuqi.dc.integration.execute.impl.intf.IBizDataConvertHandler;
import com.jiuqi.dc.integration.execute.impl.intf.IBizDataConvertHandlerGather;
import com.jiuqi.dc.integration.execute.impl.mq.BizDataExecuteParam;
import com.jiuqi.dc.integration.execute.impl.mq.BizModelExecuteParam;
import com.jiuqi.dc.integration.execute.impl.mq.model.bizdata.AbstractDcBizBaseTaskHandler;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather;
import com.jiuqi.dc.mappingscheme.impl.service.BizDataRefDefineService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BizAgingUnClearConvertTaskHandler
extends AbstractDcBizBaseTaskHandler {
    @Autowired
    private IPluginTypeGather pluginGather;
    @Autowired
    private IBizDataConvertHandlerGather gather;
    @Autowired
    private BizDataRefDefineService defineService;
    @Autowired
    private DataSchemeService dataSchemeService;

    public String getName() {
        return "BizAgingUnClearConvert";
    }

    public String getTitle() {
        return "\u3010\u6570\u636e\u6574\u5408\u3011\u8d26\u9f84\u672a\u6e05\u9879\u8f6c\u6362";
    }

    public TaskHandleResult handleTask(String param) {
        BizModelExecuteParam modelExecuteParam = (BizModelExecuteParam)JsonUtils.readValue((String)param, BizModelExecuteParam.class);
        BizDataExecuteParam executeParam = (BizDataExecuteParam)JsonUtils.readValue((String)modelExecuteParam.getExecuteParam(), BizDataExecuteParam.class);
        DataSchemeDTO dataScheme = this.dataSchemeService.findByCode(executeParam.getDataSchemeCode());
        DataMappingDefineDTO define = this.defineService.getByCode(executeParam.getDataSchemeCode(), executeParam.getDefineCode());
        IBizDataConvertHandler handler = this.gather.getHandler(this.pluginGather.getPluginType(define.getPluginType()), define.getCode(), dataScheme.getSourceDataType());
        String log = "";
        if (handler == null) {
            log = String.format("\u63d2\u4ef6\u7c7b\u578b\u3010%1$s\u3011\u4e1a\u52a1\u7c7b\u578b\u3010%2$s\u3011\u6570\u636e\u65b9\u6848\u3010%3$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u5bf9\u5e94\u7684\u4e1a\u52a1\u6570\u636e\u5904\u7406\u5668", define.getPluginType(), define.getCode(), define.getDataSchemeCode());
            TaskHandleResult result = new TaskHandleResult();
            result.appendLog(log);
            result.setPreParam(param);
            return result;
        }
        DataConvertResult convertResult = handler.doConvert(executeParam.getDataSchemeCode(), modelExecuteParam.getConvertParam());
        TaskHandleResult result = new TaskHandleResult();
        result.appendLog(convertResult.getLog());
        result.setPreParam(convertResult.getParam());
        return result;
    }
}

