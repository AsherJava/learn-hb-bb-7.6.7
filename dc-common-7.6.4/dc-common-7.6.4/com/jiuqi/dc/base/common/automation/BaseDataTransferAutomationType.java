/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.automation.annotation.CommonAutomation
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.nvwa.core.automation.annotation.AutomationType
 *  com.jiuqi.nvwa.core.automation.annotation.ExecuteOperation
 *  com.jiuqi.nvwa.core.automation.annotation.MetaOperation
 *  com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo
 *  com.jiuqi.nvwa.framework.automation.enums.AutomationValueResultDataTypeEnum
 *  com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker
 *  com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker
 *  com.jiuqi.nvwa.framework.automation.result.ValueResult
 */
package com.jiuqi.dc.base.common.automation;

import com.jiuqi.common.automation.annotation.CommonAutomation;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.dc.base.common.basedata.BaseDataDefineSyncService;
import com.jiuqi.nvwa.core.automation.annotation.AutomationType;
import com.jiuqi.nvwa.core.automation.annotation.ExecuteOperation;
import com.jiuqi.nvwa.core.automation.annotation.MetaOperation;
import com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo;
import com.jiuqi.nvwa.framework.automation.enums.AutomationValueResultDataTypeEnum;
import com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker;
import com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker;
import com.jiuqi.nvwa.framework.automation.result.ValueResult;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AutomationType(category="datacenter", id="datacenter-baseDataTransfer-auto", title="\u6570\u636e\u8fc1\u79fb", icon="icon-64-xxx")
@CommonAutomation(name="baseDataTransfer", title="\u57fa\u7840\u6570\u636e\u8fc1\u79fb", path="/\u6570\u636e\u8fc1\u79fb")
public class BaseDataTransferAutomationType {
    private final Logger logger = LoggerFactory.getLogger(BaseDataTransferAutomationType.class);

    @ExecuteOperation
    public IOperationInvoker<ValueResult> refSync() {
        return (instance, context) -> {
            BaseDataDefineSyncService baseDataDefineSyncService = (BaseDataDefineSyncService)SpringContextUtils.getBean(BaseDataDefineSyncService.class);
            baseDataDefineSyncService.baseDataDefineSync();
            return new ValueResult((Object)"\u57fa\u7840\u6570\u636e\u8fc1\u79fb\u7ed3\u675f", AutomationValueResultDataTypeEnum.STRING);
        };
    }

    @MetaOperation
    public IMetaInvoker meta() {
        return instance -> {
            AutomationMetaInfo metaInfo = new AutomationMetaInfo();
            metaInfo.setParameterList((List)CollectionUtils.newArrayList());
            ArrayList fieldInfoList = new ArrayList();
            metaInfo.setFieldInfoList(fieldInfoList);
            return metaInfo;
        };
    }
}

