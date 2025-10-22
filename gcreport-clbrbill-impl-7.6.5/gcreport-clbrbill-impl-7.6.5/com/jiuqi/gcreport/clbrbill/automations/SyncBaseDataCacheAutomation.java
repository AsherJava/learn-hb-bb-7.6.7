/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.automation.annotation.CommonAutomation
 *  com.jiuqi.nvwa.core.automation.annotation.AutomationType
 *  com.jiuqi.nvwa.core.automation.annotation.MetaOperation
 *  com.jiuqi.nvwa.core.automation.annotation.WriteOperation
 *  com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo
 *  com.jiuqi.nvwa.framework.automation.api.AutomationParameter
 *  com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum
 *  com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker
 *  com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker
 *  com.jiuqi.nvwa.framework.automation.result.JsonResult
 *  com.jiuqi.va.domain.basedata.BaseDataCacheDTO
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.gcreport.clbrbill.automations;

import com.jiuqi.common.automation.annotation.CommonAutomation;
import com.jiuqi.nvwa.core.automation.annotation.AutomationType;
import com.jiuqi.nvwa.core.automation.annotation.MetaOperation;
import com.jiuqi.nvwa.core.automation.annotation.WriteOperation;
import com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo;
import com.jiuqi.nvwa.framework.automation.api.AutomationParameter;
import com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum;
import com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker;
import com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker;
import com.jiuqi.nvwa.framework.automation.result.JsonResult;
import com.jiuqi.va.domain.basedata.BaseDataCacheDTO;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.ArrayList;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@AutomationType(category="gcreport", id="Etl-SyncBaseDataCache", title="\u540c\u6b65\u57fa\u7840\u6570\u636e\u7f13\u5b58", icon="icon-64-xxx")
@CommonAutomation(path="/\u4e1a\u52a1\u534f\u540c")
public class SyncBaseDataCacheAutomation {
    private final Logger logger = LoggerFactory.getLogger(SyncBaseDataCacheAutomation.class);
    @Autowired
    private BaseDataClient baseDataClient;

    @MetaOperation
    public IMetaInvoker metaOperation() {
        return instance -> {
            AutomationMetaInfo metaInfo = new AutomationMetaInfo();
            ArrayList<AutomationParameter> parameterList = new ArrayList<AutomationParameter>();
            metaInfo.setParameterList(parameterList);
            parameterList.add(new AutomationParameter("tableName", "\u57fa\u7840\u6570\u636e\u8868\u540d", AutomationParameterDataTypeEnum.STRING, null, true));
            return metaInfo;
        };
    }

    @WriteOperation
    public IOperationInvoker<JsonResult> write() {
        return (instance, context) -> {
            this.logger.info("\u5f00\u59cb\u6267\u884c\u540c\u6b65\u57fa\u7840\u6570\u636e\u7f13\u5b58\u81ea\u52a8\u5316\u5bf9\u8c61\u3002");
            BaseDataCacheDTO baseDataCacheDTO = new BaseDataCacheDTO();
            Map parameterMap = context.getParameterMap();
            baseDataCacheDTO.setTableName(parameterMap.get("tableName").toString());
            this.baseDataClient.cleanCache(baseDataCacheDTO);
            this.logger.info("\u540c\u6b65\u57fa\u7840\u6570\u636e\u7f13\u5b58\u81ea\u52a8\u5316\u5bf9\u8c61\u6267\u884c\u5b8c\u6210\u3002");
            return new JsonResult("\u53d6\u6570\u6210\u529f");
        };
    }
}

