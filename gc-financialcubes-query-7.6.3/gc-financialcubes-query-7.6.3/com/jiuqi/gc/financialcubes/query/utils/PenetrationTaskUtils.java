/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.util.Pair
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.TaskInfoVO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 */
package com.jiuqi.gc.financialcubes.query.utils;

import com.jiuqi.bde.common.util.Pair;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gc.financialcubes.query.dto.PenetrationContextInfo;
import com.jiuqi.gc.financialcubes.query.dto.PenetrationParamDTO;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.TaskInfoVO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PenetrationTaskUtils {
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;

    public String getSchemeIdByPeriodAndTaskId(String dataTime, String taskId) throws Exception {
        SchemePeriodLinkDefine schemePeriodLinkDefine = this.iRunTimeViewController.querySchemePeriodLinkByPeriodAndTask(dataTime, taskId);
        if (schemePeriodLinkDefine != null) {
            return schemePeriodLinkDefine.getSchemeKey();
        }
        throw new BusinessRuntimeException("\u62a5\u8868\u65b9\u6848\u4e3anull\uff0c\u62b5\u6d88\u5206\u5f55\u65e0\u6cd5\u6b63\u5e38\u7a7f\u900f");
    }

    public Pair<String, String> getTaskIdByPeriodAndOrgType(PenetrationContextInfo context, PenetrationParamDTO dto) {
        List allBoundTaskVOs = this.consolidatedTaskService.getAllBoundTaskVOs();
        for (ConsolidatedTaskVO consolidatedTaskVO : allBoundTaskVOs) {
            Pair<String, String> taskInfo;
            if (!this.isPeriodValid(consolidatedTaskVO, context, dto) || StringUtils.isEmpty((String)((String)(taskInfo = this.findTaskInfoByOrgType(consolidatedTaskVO, dto)).getFirst()))) continue;
            return taskInfo;
        }
        throw new BusinessRuntimeException("\u7a7f\u900f\u62b5\u9500\u5206\u5f55\u65f6\u672a\u5728\u5408\u5e76\u4f53\u7cfb\u4e2d\u627e\u5230\u5bf9\u5e94\u7684\u4efb\u52a1");
    }

    public String getSchemeNameById(String dataSchemeKey) {
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(dataSchemeKey);
        String schemeCode = dataScheme.getCode();
        if (StringUtils.isEmpty((String)schemeCode)) {
            throw new BusinessRuntimeException("\u67e5\u8be2\u6570\u636e\u65b9\u6848\u4e3a\u7a7a");
        }
        return schemeCode;
    }

    private boolean isPeriodValid(ConsolidatedTaskVO consolidatedTaskVO, PenetrationContextInfo context, PenetrationParamDTO dto) {
        String fromPeriod = consolidatedTaskVO.getFromPeriod();
        String toPeriod = consolidatedTaskVO.getToPeriod();
        String dataTime = dto.getDataTime();
        Date fromPeriodDate = this.transformPeriodToDate(context, fromPeriod);
        Date toPeriodDate = this.transformPeriodToDate(context, toPeriod);
        Date targetDateBegin = this.transformPeriodToDate(context, dataTime);
        Date targetDateEnd = this.transformPeriodToDate(context, dataTime);
        return !(fromPeriodDate != null && targetDateBegin != null && targetDateBegin.before(fromPeriodDate) || toPeriodDate != null && targetDateEnd != null && targetDateEnd.after(toPeriodDate));
    }

    private Date transformPeriodToDate(PenetrationContextInfo context, String period) {
        if (StringUtils.isEmpty((String)period)) {
            throw new BusinessRuntimeException("\u65f6\u95f4\u8f6c\u6362\u5f02\u5e38");
        }
        return YearPeriodUtil.transformByDataSchemeKey((String)context.getDataSchemeKey(), (String)period).getBeginDate();
    }

    private Pair<String, String> findTaskInfoByOrgType(ConsolidatedTaskVO consolidatedTaskVO, PenetrationParamDTO dto) {
        String taskId = "";
        String title = "";
        String periodTypeStr = PeriodUtil.convertType2Str((int)new PeriodWrapper(dto.getDataTime()).getType());
        TaskInfoVO inputTaskInfo = consolidatedTaskVO.getInputTaskInfo();
        if (inputTaskInfo.getUnitDefine().equals(dto.getMdGcOrgType()) && inputTaskInfo.getPeriodTypeChar().equals(periodTypeStr)) {
            taskId = StringUtils.toViewString((Object)inputTaskInfo.getTask().getValue());
            title = inputTaskInfo.getTaskTitle();
            return Pair.of((Object)taskId, (Object)title);
        }
        List manageTaskInfos = consolidatedTaskVO.getManageTaskInfos();
        for (TaskInfoVO taskInfo : manageTaskInfos) {
            if (!taskInfo.getUnitDefine().equals(dto.getMdGcOrgType()) || !taskInfo.getPeriodTypeChar().equals(periodTypeStr)) continue;
            taskId = StringUtils.toViewString((Object)taskInfo.getTask().getValue());
            title = taskInfo.getTaskTitle();
            return Pair.of((Object)taskId, (Object)title);
        }
        return Pair.of((Object)taskId, (Object)title);
    }
}

