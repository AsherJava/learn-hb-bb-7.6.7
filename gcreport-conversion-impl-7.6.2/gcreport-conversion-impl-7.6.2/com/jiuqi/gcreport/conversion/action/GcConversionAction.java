/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.common.action.env.GcActionItemEnv
 *  com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem
 *  com.jiuqi.gcreport.conversion.common.GcConversionContextEnv
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 */
package com.jiuqi.gcreport.conversion.action;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.common.action.env.GcActionItemEnv;
import com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem;
import com.jiuqi.gcreport.conversion.action.param.GcConversionActionParam;
import com.jiuqi.gcreport.conversion.common.GcConversionContextEnv;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcConversionAction
extends AbstractGcActionItem {
    private static final Logger LOGGER = LoggerFactory.getLogger(GcConversionAction.class);
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    protected GcConversionAction() {
        super("gcConversionAction", "\u6298\u7b97", "\u5408\u5e76\u6298\u7b97\u52a8\u4f5c\u63cf\u8ff0", "#icon-16_DH_A_GC_wbzs");
    }

    public Object execute(GcActionItemEnv actionItemEnv) {
        GcConversionActionParam actionParam = (GcConversionActionParam)JsonUtils.readValue((String)actionItemEnv.getActionParam(), GcConversionActionParam.class);
        DataEntryContext envContext = actionParam.getEnvContext();
        Map dimensionSet = envContext.getDimensionSet();
        DimensionValue defaultPeriodValue = (DimensionValue)dimensionSet.get("DATATIME");
        DimensionValue gcorgtypeValue = (DimensionValue)dimensionSet.get("MD_GCORGTYPE");
        String periodStr = defaultPeriodValue == null ? null : defaultPeriodValue.getValue();
        String orgTypeId = gcorgtypeValue == null ? null : gcorgtypeValue.getValue();
        String beforeCurrencyId = actionParam.getBeforeCurrencyId();
        String taskId = envContext.getTaskKey();
        String schemeId = envContext.getFormSchemeKey();
        String afterCurrencyId = actionParam.getAfterCurrencyId();
        List orgIds = actionParam.getOrgIds().stream().filter(Objects::nonNull).collect(Collectors.toList());
        List formIds = actionParam.getFormIds().stream().filter(Objects::nonNull).collect(Collectors.toList());
        Boolean afterConversionoperation = actionParam.getAfterConversionoperation();
        Boolean afterConversionRealTimeOffset = actionParam.getAfterConversionRealTimeOffset();
        Boolean allowConversionBWB = actionParam.getAllowConversionBWB();
        Boolean conversionInputData = actionParam.getConversionInputData();
        String orgVersionType = actionParam.getOrgVersionType();
        DimensionValue adjustValue = (DimensionValue)dimensionSet.get("ADJUST");
        String adjustCode = adjustValue == null ? "0" : adjustValue.getValue();
        GcConversionContextEnv conversionContextEnv = new GcConversionContextEnv(actionParam.getSn(), taskId, schemeId, orgIds, formIds, orgVersionType, orgTypeId, periodStr, beforeCurrencyId, afterCurrencyId, afterConversionoperation, afterConversionRealTimeOffset, allowConversionBWB, conversionInputData, adjustCode);
        String asynTaskID = this.asyncTaskManager.publishTask((Object)conversionContextEnv, "GC_ASYNCTASK_CONVERSION");
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }
}

