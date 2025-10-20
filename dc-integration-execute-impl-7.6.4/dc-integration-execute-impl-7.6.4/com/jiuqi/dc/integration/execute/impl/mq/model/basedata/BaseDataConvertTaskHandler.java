/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService
 *  com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult
 *  com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.intf.impl.DcBaseTaskHandler
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.DimType
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 */
package com.jiuqi.dc.integration.execute.impl.mq.model.basedata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.integration.execute.impl.gather.BaseDataConvertHandlerGather;
import com.jiuqi.dc.integration.execute.impl.handler.BaseDataConvertHandler;
import com.jiuqi.dc.integration.execute.impl.mq.BaseDataExecuteParam;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.impl.service.BaseDataRefDefineService;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum;
import com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.core.intf.impl.DcBaseTaskHandler;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DimType;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseDataConvertTaskHandler
extends DcBaseTaskHandler {
    @Autowired
    private BaseDataConvertHandlerGather gather;
    @Autowired
    private BaseDataRefDefineService defineService;
    @Autowired
    private BaseDataConvertHandler handler;

    public String getName() {
        return "BaseDataConvert";
    }

    public String getTitle() {
        return "\u3010\u6570\u636e\u6574\u5408\u3011\u57fa\u7840\u6570\u636e\u8f6c\u6362";
    }

    public String getPreTask() {
        return null;
    }

    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.LEVEL;
    }

    public Map<String, String> getHandleParams(String preParam) {
        List executeParamList = (List)JsonUtils.readValue((String)preParam, (TypeReference)new TypeReference<List<BaseDataExecuteParam>>(){});
        HashMap<String, String> handleParams = new HashMap<String, String>();
        for (BaseDataExecuteParam executeParam : executeParamList) {
            handleParams.put(JsonUtils.writeValueAsString((Object)executeParam), String.format("%1$s %2$s", executeParam.getDefineCode(), executeParam.getDefineName()));
        }
        return handleParams;
    }

    public TaskHandleResult handleTask(String param) {
        BaseDataExecuteParam executeParam = (BaseDataExecuteParam)JsonUtils.readValue((String)param, BaseDataExecuteParam.class);
        BaseDataMappingDefineDTO define = this.defineService.getByCode(executeParam.getDataSchemeCode(), executeParam.getDefineCode());
        String log = this.handler.doConvert(executeParam.getDataSchemeCode(), define);
        HashMap<String, String> preParam = new HashMap<String, String>(1);
        preParam.put(JsonUtils.writeValueAsString((Object)executeParam), executeParam.getDefineCode());
        TaskHandleResult result = new TaskHandleResult();
        result.setPreParam(JsonUtils.writeValueAsString(preParam));
        result.appendLog(log);
        return result;
    }

    public InstanceTypeEnum getInstanceType() {
        return InstanceTypeEnum.NEW;
    }

    public IDimType getDimType() {
        return DimType.BASEDATADEFINE;
    }
}

