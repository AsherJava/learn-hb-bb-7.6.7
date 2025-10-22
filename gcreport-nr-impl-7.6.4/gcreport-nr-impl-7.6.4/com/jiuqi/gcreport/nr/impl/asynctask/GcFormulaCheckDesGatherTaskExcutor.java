/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.nr.vo.GcFormulaCheckDesGatherParam
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.gcreport.nr.impl.asynctask;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.nr.impl.constant.GcAsyncTaskPoolType;
import com.jiuqi.gcreport.nr.impl.service.GcFormulaCheckDesGatherService;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.nr.vo.GcFormulaCheckDesGatherParam;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.BeanUtils;

@RealTimeJob(group="ASYNCTASK_CHECKDESGATHER", groupTitle="\u51fa\u9519\u8bf4\u660e\u6c47\u603b")
public class GcFormulaCheckDesGatherTaskExcutor
extends NpRealTimeTaskExecutor {
    public void executeWithNpContext(JobContext jobContext) {
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor monitor = new RealTimeTaskMonitor(taskId, GcAsyncTaskPoolType.ASYNCTASK_CHECKDESGATHER.getName(), jobContext);
        String resultStr = GcI18nUtil.getMessage((String)"gc.org.nr.formulaCheckDes.gather.success");
        if (Objects.isNull(params)) {
            monitor.finish(resultStr, (Object)GcI18nUtil.getMessage((String)"gc.org.nr.formulaCheckDes.gather.paramsEmpty"));
            return;
        }
        String args = (String)params.get("NR_ARGS");
        if (Objects.isNull(args)) {
            monitor.finish(resultStr, (Object)GcI18nUtil.getMessage((String)"gc.org.nr.formulaCheckDes.gather.contextEmpty"));
            return;
        }
        GcFormulaCheckDesGatherParam gcFormulaCheckDesGatherParam = (GcFormulaCheckDesGatherParam)SimpleParamConverter.SerializationUtils.deserialize((String)args);
        monitor.progressAndMessage(0.02, GcI18nUtil.getMessage((String)"gc.org.nr.formulaCheckDes.gather.start"));
        GcOrgCacheVO targetOrg = this.getTargetOrg(gcFormulaCheckDesGatherParam.getJtableContext(), gcFormulaCheckDesGatherParam.getTargetUnitCode());
        Map<String, List<String>> orgCodeGroupByParentCode = this.getAllChildrenOrgCodeGroupByParentCode(gcFormulaCheckDesGatherParam.isAllChildOrgFlag(), targetOrg);
        ListIterator<Map.Entry<String, List<String>>> i = new ArrayList<Map.Entry<String, List<String>>>(orgCodeGroupByParentCode.entrySet()).listIterator(orgCodeGroupByParentCode.size());
        monitor.progressAndMessage(0.1, GcI18nUtil.getMessage((String)"gc.org.nr.formulaCheckDes.gather.getGatherOrg"));
        double currTaskProgress = 0.1;
        double weight = 0.8 / (double)orgCodeGroupByParentCode.size();
        int totalProgress = 1;
        while (i.hasPrevious()) {
            Map.Entry<String, List<String>> entry = i.previous();
            String targetOrgStr = GcI18nUtil.getMessage((String)"gc.org.nr.formulaCheckDes.gather.getGatherOrgSuccess", (Object[])new Object[]{entry.getKey()});
            monitor.progressAndMessage(currTaskProgress += (double)totalProgress * weight, targetOrgStr);
            this.gatherFormulaCheckDes(gcFormulaCheckDesGatherParam, entry.getKey(), entry.getValue());
            ++totalProgress;
            if (!monitor.isCancel()) continue;
            monitor.canceled(GcI18nUtil.getMessage((String)"gc.org.nr.formulaCheckDes.gather.taskCancel"), (Object)GcI18nUtil.getMessage((String)"gc.org.nr.formulaCheckDes.gather.taskCancelMessage"));
        }
        monitor.finish(resultStr, (Object)resultStr);
    }

    public String getTaskPoolType() {
        return GcAsyncTaskPoolType.ASYNCTASK_CHECKDESGATHER.getName();
    }

    private void gatherFormulaCheckDes(GcFormulaCheckDesGatherParam formulaCheckDesGatherParam, String targetOrgCode, List<String> orgCodes) {
        GcFormulaCheckDesGatherParam gcFormulaCheckDesGatherParam = new GcFormulaCheckDesGatherParam();
        BeanUtils.copyProperties(formulaCheckDesGatherParam, gcFormulaCheckDesGatherParam);
        gcFormulaCheckDesGatherParam.setTargetUnitCode(targetOrgCode);
        gcFormulaCheckDesGatherParam.setOrgIds(orgCodes);
        GcFormulaCheckDesGatherService gcFormulaCheckDesGatherService = (GcFormulaCheckDesGatherService)SpringContextUtils.getBean(GcFormulaCheckDesGatherService.class);
        gcFormulaCheckDesGatherService.formulaCheckDesGather(gcFormulaCheckDesGatherParam, null);
    }

    private GcOrgCacheVO getTargetOrg(JtableContext jtableContext, String targetOrgCode) {
        Map dimensionSet = jtableContext.getDimensionSet();
        String dataTime = ((DimensionValue)dimensionSet.get("DATATIME")).getValue();
        String orgCategory = GcOrgTypeUtils.getOrgTypeByContextOrTaskId(jtableContext.getTaskKey());
        YearPeriodObject yearPeriodObject = new YearPeriodObject(jtableContext.getFormSchemeKey(), dataTime);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)Objects.requireNonNull(orgCategory), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yearPeriodObject);
        GcOrgCacheVO targetOrg = tool.getOrgByCode(targetOrgCode);
        return targetOrg;
    }

    private Map<String, List<String>> getAllChildrenOrgCodeGroupByParentCode(boolean isAllChildOrgFlag, GcOrgCacheVO targetOrg) {
        LinkedHashMap<String, List<String>> orgCodeGroupByParentCode = new LinkedHashMap<String, List<String>>();
        List childrenOrg = targetOrg.getChildren();
        if (CollectionUtils.isEmpty((Collection)childrenOrg)) {
            return orgCodeGroupByParentCode;
        }
        this.getAllChildrenOrgCodeGroupByParentCode(childrenOrg, orgCodeGroupByParentCode, isAllChildOrgFlag);
        return orgCodeGroupByParentCode;
    }

    private void getAllChildrenOrgCodeGroupByParentCode(List<GcOrgCacheVO> currentChildrenOrgs, Map<String, List<String>> orgCodeGroupByParentCode, boolean isAllChildOrgFlag) {
        for (GcOrgCacheVO cacheVO : currentChildrenOrgs) {
            List childrenOrg = cacheVO.getChildren();
            List codes = orgCodeGroupByParentCode.computeIfAbsent(cacheVO.getParentId(), v -> new ArrayList());
            codes.add(cacheVO.getCode());
            if (!isAllChildOrgFlag || CollectionUtils.isEmpty((Collection)childrenOrg)) continue;
            this.getAllChildrenOrgCodeGroupByParentCode(childrenOrg, orgCodeGroupByParentCode, isAllChildOrgFlag);
        }
    }
}

