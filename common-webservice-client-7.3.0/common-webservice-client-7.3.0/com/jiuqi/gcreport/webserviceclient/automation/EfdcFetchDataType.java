/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.automation.annotation.CommonAutomation
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.AsyncTaskTypeCollecter
 *  com.jiuqi.np.asynctask.NpAsyncTaskExecutor
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor
 *  com.jiuqi.nr.efdc.pojo.EfdcInfo
 *  com.jiuqi.nvwa.core.automation.annotation.AutomationType
 *  com.jiuqi.nvwa.core.automation.annotation.ExecuteOperation
 *  com.jiuqi.nvwa.core.automation.annotation.MetaOperation
 *  com.jiuqi.nvwa.framework.automation.api.AutomationFieldInfo
 *  com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo
 *  com.jiuqi.nvwa.framework.automation.api.AutomationParameter
 *  com.jiuqi.nvwa.framework.automation.bean.ExecuteContext
 *  com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum
 *  com.jiuqi.nvwa.framework.automation.exception.AutomationException
 *  com.jiuqi.nvwa.framework.automation.exception.AutomationExecuteException
 *  com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker
 *  com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker
 *  com.jiuqi.nvwa.framework.automation.result.JsonResult
 */
package com.jiuqi.gcreport.webserviceclient.automation;

import com.jiuqi.common.automation.annotation.CommonAutomation;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.webserviceclient.utils.DataIntegrationUtil;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.AsyncTaskTypeCollecter;
import com.jiuqi.np.asynctask.NpAsyncTaskExecutor;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import com.jiuqi.nvwa.core.automation.annotation.AutomationType;
import com.jiuqi.nvwa.core.automation.annotation.ExecuteOperation;
import com.jiuqi.nvwa.core.automation.annotation.MetaOperation;
import com.jiuqi.nvwa.framework.automation.api.AutomationFieldInfo;
import com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo;
import com.jiuqi.nvwa.framework.automation.api.AutomationParameter;
import com.jiuqi.nvwa.framework.automation.bean.ExecuteContext;
import com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum;
import com.jiuqi.nvwa.framework.automation.exception.AutomationException;
import com.jiuqi.nvwa.framework.automation.exception.AutomationExecuteException;
import com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker;
import com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker;
import com.jiuqi.nvwa.framework.automation.result.JsonResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@AutomationType(category="gcreport", id="efdc-fetch-data-auto-type", title="EFDC\u53d6\u6570", icon="icon-64-xxx")
@CommonAutomation(path="/\u6570\u636e\u5f55\u5165")
public class EfdcFetchDataType {
    private Logger logger = LoggerFactory.getLogger(EfdcFetchDataType.class);
    @Autowired
    private AsyncTaskTypeCollecter collecter;
    @Autowired
    private CacheObjectResourceRemote cacheObjectResourceRemote;

    @ExecuteOperation
    public IOperationInvoker<JsonResult> invoke() {
        return (instance, context) -> {
            try {
                EfdcInfo efdcInfo = this.getEfdcInfo(context);
                String taskPoolType = AsynctaskPoolType.ASYNCTASK_BATCHEFDC.getName();
                NpAsyncTaskExecutor executor = this.collecter.getExecutorByType(taskPoolType);
                executor.execute((Object)efdcInfo, (AsyncTaskMonitor)new SimpleAsyncProgressMonitor(UUIDUtils.newUUIDStr(), this.cacheObjectResourceRemote));
                ArrayList taskLogs = new ArrayList();
                this.logger.info(StringUtils.join((Object[])taskLogs.toArray(), (String)";\n"));
                return new JsonResult(null);
            }
            catch (Exception e) {
                this.logger.error("WS\u5ba2\u6237\u7aef\u81ea\u52a8\u5316\u6267\u884c\u5f02\u5e38\uff1a" + e.getMessage(), e);
                throw new AutomationExecuteException((Throwable)e);
            }
        };
    }

    private EfdcInfo getEfdcInfo(ExecuteContext context) throws AutomationException {
        EfdcInfo efdcInfo = new EfdcInfo();
        String taskKey = context.getParameterValue("TASKKEY");
        Map<String, DimensionValue> dimensionSet = DataIntegrationUtil.getDimensionSet(context, taskKey);
        efdcInfo.setDimensionSet(dimensionSet);
        String formSchemeKey = context.getParameterValue("SCHEMEKEY");
        efdcInfo.setTaskKey(taskKey);
        efdcInfo.setFormSchemeKey(formSchemeKey);
        efdcInfo.setFormKey("");
        efdcInfo.setContainsUnbVou(true);
        efdcInfo.setOverwrite(true);
        this.logger.info("EFDC\u53d6\u6570\u81ea\u52a8\u5316\u5bf9\u8c61\u6267\u884c\u53c2\u6570\uff1a{}", (Object)JsonUtils.writeValueAsString((Object)efdcInfo));
        return efdcInfo;
    }

    @MetaOperation
    public IMetaInvoker metaOperation() {
        return instance -> {
            AutomationMetaInfo metaInfo = new AutomationMetaInfo();
            ArrayList<AutomationFieldInfo> fieldInfoList = new ArrayList<AutomationFieldInfo>();
            fieldInfoList.add(new AutomationFieldInfo("TASKKEY", "\u4efb\u52a1", Integer.valueOf(6)));
            fieldInfoList.add(new AutomationFieldInfo("SCHEMEKEY", "\u62a5\u8868\u65b9\u6848", Integer.valueOf(6)));
            fieldInfoList.add(new AutomationFieldInfo("UNITCODE", "\u5355\u4f4d", Integer.valueOf(6)));
            fieldInfoList.add(new AutomationFieldInfo("DATATIME", "\u65f6\u671f", Integer.valueOf(6)));
            fieldInfoList.add(new AutomationFieldInfo("CURRENCY", "\u5e01\u79cd", Integer.valueOf(6)));
            fieldInfoList.add(new AutomationFieldInfo("ORGTYPE", "\u5355\u4f4d\u7c7b\u578b", Integer.valueOf(6)));
            metaInfo.setFieldInfoList(fieldInfoList);
            List parameterList = fieldInfoList.stream().map(fieldInfo -> new AutomationParameter(fieldInfo.getName(), fieldInfo.getTitle(), AutomationParameterDataTypeEnum.STRING, null, true)).collect(Collectors.toList());
            metaInfo.setParameterList(parameterList);
            return metaInfo;
        };
    }
}

