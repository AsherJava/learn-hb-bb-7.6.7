/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.carryover.entity.CarryOverLogEO
 *  com.jiuqi.gcreport.carryover.enums.CarryOverTypeEnum
 *  com.jiuqi.gcreport.carryover.task.GcCarryOverTaskExecutor
 *  com.jiuqi.gcreport.carryover.utils.CarryOverLogUtil
 *  com.jiuqi.gcreport.carryover.utils.CarryOverUtil
 *  com.jiuqi.gcreport.carryover.vo.QueryParamsVO
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 */
package com.jiuqi.gcreport.offsetitem.init.carryover.task;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.carryover.entity.CarryOverLogEO;
import com.jiuqi.gcreport.carryover.enums.CarryOverTypeEnum;
import com.jiuqi.gcreport.carryover.task.GcCarryOverTaskExecutor;
import com.jiuqi.gcreport.carryover.utils.CarryOverLogUtil;
import com.jiuqi.gcreport.carryover.utils.CarryOverUtil;
import com.jiuqi.gcreport.carryover.vo.QueryParamsVO;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.offsetitem.init.carryover.asynctask.GcCarryOverOffsetAsyncTaskExecutor;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcCarryOverTaskOffsetExecutorImpl
implements GcCarryOverTaskExecutor {
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private ConsolidatedSystemService consolidatedSystemService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;

    public String getName() {
        return CarryOverTypeEnum.OFFSET.getCode();
    }

    public void checkParam(QueryParamsVO queryParamsVO) {
        String periodStr = CarryOverUtil.convertPeriod((String)queryParamsVO.getPeriodStr(), (int)queryParamsVO.getAcctYear(), (int)queryParamsVO.getPeriodType());
        ConsolidatedTaskVO taskVO = this.consolidatedTaskService.getTaskByTaskKeyAndPeriodStr(queryParamsVO.getTaskId(), periodStr);
        if (taskVO == null) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.carryover.not.system.id.message"));
        }
        ConsolidatedSystemEO targetSystemEO = this.consolidatedSystemService.getConsolidatedSystemEO(queryParamsVO.getConsSystemId());
        List consolidatedTasks = this.consolidatedTaskService.getConsolidatedTasks(queryParamsVO.getConsSystemId());
        Optional<ConsolidatedTaskVO> optional = consolidatedTasks.stream().filter(task -> ConverterUtils.getAsInteger((Object)task.getFromPeriod().substring(0, 4)) <= queryParamsVO.getAcctYear() && queryParamsVO.getAcctYear() <= ConverterUtils.getAsInteger((Object)task.getToPeriod().substring(0, 4))).findFirst();
        if (!optional.isPresent()) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.offset.typeStr.carryover.not.task", (Object[])new Object[]{targetSystemEO.getSystemName(), queryParamsVO.getAcctYear()}));
        }
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        queryParamsVO.setOrgList(queryParamsVO.getOrgList().stream().map(org -> tool.getOrgByCode(org.getCode())).filter(org -> GcOrgKindEnum.UNIONORG.equals((Object)org.getOrgKind())).collect(Collectors.toList()));
        queryParamsVO.setTaskVO(taskVO);
        if (DimensionUtils.isExistAdjust((String)queryParamsVO.getTaskId()) && StringUtils.isEmpty((String)queryParamsVO.getSelectAdjustCode())) {
            queryParamsVO.setSelectAdjustCode("0");
        }
    }

    public String publishTask(QueryParamsVO queryParamsVO) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setTaskKey(queryParamsVO.getTaskId());
        npRealTimeTaskInfo.setFormSchemeKey(queryParamsVO.getSchemeId());
        npRealTimeTaskInfo.setArgs(JsonUtils.writeValueAsString((Object)queryParamsVO));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new GcCarryOverOffsetAsyncTaskExecutor());
        return this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
    }

    public List<CarryOverLogEO> createTasks(QueryParamsVO queryParamsVO) {
        String periodStr = CarryOverUtil.convertPeriod((String)queryParamsVO.getPeriodStr(), (int)queryParamsVO.getAcctYear(), (int)queryParamsVO.getPeriodType());
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        queryParamsVO.setOrgList(queryParamsVO.getOrgList().stream().map(org -> tool.getOrgByCode(org.getCode())).filter(org -> GcOrgKindEnum.UNIONORG.equals((Object)org.getOrgKind())).collect(Collectors.toList()));
        List orgList = queryParamsVO.getOrgList();
        ArrayList<CarryOverLogEO> eos = new ArrayList<CarryOverLogEO>();
        orgList.forEach(org -> {
            CarryOverLogEO eo = CarryOverLogUtil.initCarryOverLogEO((GcOrgCacheVO)org, (QueryParamsVO)queryParamsVO);
            eos.add(eo);
        });
        return eos;
    }
}

