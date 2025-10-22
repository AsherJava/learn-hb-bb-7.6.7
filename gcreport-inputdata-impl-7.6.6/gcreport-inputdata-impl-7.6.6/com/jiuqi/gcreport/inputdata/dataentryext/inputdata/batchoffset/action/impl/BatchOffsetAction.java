/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.common.action.env.GcActionItemEnv
 *  com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.util.ConsolidatedSystemUtils
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.inputdata.offset.OffsetParam
 *  com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.nr.impl.util.GcPeriodAssistUtil
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.dataentry.bean.DataEntryContext
 */
package com.jiuqi.gcreport.inputdata.dataentryext.inputdata.batchoffset.action.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.common.action.env.GcActionItemEnv;
import com.jiuqi.gcreport.common.action.impl.AbstractGcActionItem;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.util.ConsolidatedSystemUtils;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.offset.OffsetParam;
import com.jiuqi.gcreport.inputdata.util.InputDataConver;
import com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.nr.impl.util.GcPeriodAssistUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.dataentry.bean.DataEntryContext;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class BatchOffsetAction
extends AbstractGcActionItem {
    private AsyncTaskManager asyncTaskManager;
    private ConsolidatedTaskService consolidatedTaskService;

    protected BatchOffsetAction(AsyncTaskManager asyncTaskManager, ConsolidatedTaskService consolidatedTaskService) {
        super("gcBatchOffset", "\u81ea\u52a8\u62b5\u9500", "", "#icon-16_GJ_A_GC_zidongduizhang");
        this.asyncTaskManager = asyncTaskManager;
        this.consolidatedTaskService = consolidatedTaskService;
    }

    public Object execute(GcActionItemEnv actionItemEnv) {
        Map actionParam = (Map)JsonUtils.readValue((String)actionItemEnv.getActionParam(), (TypeReference)new TypeReference<Map<String, Object>>(){});
        DataEntryContext dataEntryContext = (DataEntryContext)JsonUtils.readValue((String)JsonUtils.writeValueAsString(actionParam.get("envContext")), DataEntryContext.class);
        Map<String, String> dimensions = InputDataConver.getDimFieldValueMap(dataEntryContext.getDimensionSet(), dataEntryContext.getTaskKey());
        ConsolidatedTaskVO taskVO = this.consolidatedTaskService.getTaskBySchemeId(dataEntryContext.getFormSchemeKey(), dimensions.get("DATATIME"));
        if (null == taskVO) {
            return new OffsetError(GcI18nUtil.getMessage((String)"gc.inputdata.batchoffset.notsystemerrormsg"));
        }
        List allInputSchemeList = ConsolidatedSystemUtils.listAllInputSchemeByConTaskVO((ConsolidatedTaskVO)taskVO);
        boolean isCorporate = allInputSchemeList.contains(dataEntryContext.getFormSchemeKey());
        if (!(isCorporate || null != taskVO.getManageCalcUnitCodes() && taskVO.getManageCalcUnitCodes().contains(dimensions.get("MDCODE")))) {
            return new OffsetError(GcI18nUtil.getMessage((String)"gc.inputdata.batchoffset.notcorporateerrormsg"));
        }
        try {
            String orgCategory = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)dataEntryContext.getTaskKey());
            YearPeriodDO yp = GcPeriodAssistUtil.getPeriodObject((DataEntryContext)dataEntryContext, (String)dimensions.get("DATATIME"));
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgCategory, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodDO)yp);
            GcOrgCacheVO org = tool.getOrgByCode(dimensions.get("MDCODE"));
            if (!EnumSet.of(GcOrgKindEnum.BASE, GcOrgKindEnum.SINGLE).contains(org.getOrgKind())) {
                return new OffsetError(GcI18nUtil.getMessage((String)"gc.inputdata.batchoffset.notbaseorsingleerrormsg"));
            }
        }
        catch (Exception e) {
            return new OffsetError(GcI18nUtil.getMessage((String)"gc.inputdata.batchoffset.dimexceptionmsg") + e.getMessage());
        }
        if (Boolean.TRUE.equals(JsonUtils.readValue((String)JsonUtils.writeValueAsString(actionParam.get("checkParam")), Boolean.class))) {
            return new OffsetError("");
        }
        boolean isAllFormOffset = Boolean.valueOf(String.valueOf(actionParam.get("isAllForm")));
        List formIds = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(actionParam.get("formIds")), (TypeReference)new TypeReference<List<String>>(){});
        OffsetParam offsetParam = new OffsetParam(dataEntryContext, isAllFormOffset, formIds);
        String asynTaskId = this.asyncTaskManager.publishTask((Object)offsetParam, GcAsyncTaskPoolType.OFFSET.getName());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        asyncTaskInfo.setId(asynTaskId);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    static class OffsetError {
        private final String errMsg;

        OffsetError(String errMsg) {
            this.errMsg = errMsg;
        }

        public String getErrMsg() {
            return this.errMsg;
        }
    }
}

