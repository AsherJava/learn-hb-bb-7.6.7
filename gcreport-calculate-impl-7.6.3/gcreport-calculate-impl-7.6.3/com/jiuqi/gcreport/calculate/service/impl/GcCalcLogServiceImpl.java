/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.impl.ConsolidatedTaskServiceImpl
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.va.feign.util.LogUtil
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.calculate.service.impl;

import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.calculate.common.GcCalcLogOperateEnum;
import com.jiuqi.gcreport.calculate.common.GcConcurrentLogRuntimeException;
import com.jiuqi.gcreport.calculate.dao.GcCalcLogDao;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.entity.GcCalcLogEO;
import com.jiuqi.gcreport.calculate.env.impl.GcCalcEnvContextImpl;
import com.jiuqi.gcreport.calculate.service.GcCalcLogService;
import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.consolidatedsystem.service.task.impl.ConsolidatedTaskServiceImpl;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.va.feign.util.LogUtil;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GcCalcLogServiceImpl
implements GcCalcLogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GcCalcLogServiceImpl.class);
    private static final Long TIMEOUT = 300000L;
    @Autowired
    private GcCalcLogDao gcCalcLogDao;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;

    @Transactional(readOnly=true, rollbackFor={Exception.class})
    public GcCalcLogEO queryCalcLogEO(String logId) {
        GcCalcLogEO calcLogInfoEO = this.gcCalcLogDao.queryLogById(logId);
        return calcLogInfoEO;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public GcCalcLogEO insertCalcLogEO(Long unLockTimeOut, GcCalcLogOperateEnum gcCalcLogOperateEnum, String taskId, String currency, String periodStr, String orgType, String orgId, String selectAdjustCode) {
        this.unLockTimeOutLogOperate(gcCalcLogOperateEnum, taskId, currency, periodStr, orgType, orgId, unLockTimeOut, selectAdjustCode);
        GcCalcLogEO insertCalcLogEO = new GcCalcLogEO();
        insertCalcLogEO.setOperate(gcCalcLogOperateEnum.getName());
        insertCalcLogEO.setId(UUIDUtils.newUUIDStr());
        insertCalcLogEO.setBegintime(DateUtils.now().getTime());
        insertCalcLogEO.setEndtime(null);
        ContextUser currentUser = NpContextHolder.getContext().getUser();
        if (currentUser != null) {
            insertCalcLogEO.setUsername(currentUser.getName());
            insertCalcLogEO.setUserId(currentUser.getId());
        }
        insertCalcLogEO.setOrgId(orgId);
        insertCalcLogEO.setTaskId(taskId);
        insertCalcLogEO.setCurrency(currency);
        insertCalcLogEO.setOrgType(orgType);
        insertCalcLogEO.setPeriod(periodStr);
        insertCalcLogEO.setSelectAdjustCode(StringUtils.isEmpty((String)selectAdjustCode) ? "0" : selectAdjustCode);
        insertCalcLogEO.setLockFlag(1);
        insertCalcLogEO.setLatestFlag(1);
        this.gcCalcLogDao.save(insertCalcLogEO);
        this.updateLogToUnLatestState(gcCalcLogOperateEnum, taskId, currency, periodStr, orgType, orgId, insertCalcLogEO.getId(), selectAdjustCode);
        return insertCalcLogEO;
    }

    @Override
    public Integer updateLogToUnLatestState(GcCalcLogOperateEnum operateEnum, String taskId, String currency, String periodStr, String orgType, String orgId, String excludeLogId, String selectAdjustCode) {
        Integer count = this.gcCalcLogDao.updateLogToUnLatestState(operateEnum, taskId, currency, periodStr, orgType, orgId, excludeLogId, selectAdjustCode);
        return count;
    }

    @Override
    public void unLockTimeOutLogOperate(GcCalcLogOperateEnum operateEnum, String taskId, String currency, String periodStr, String orgType, String orgId, Long timeOut, String selectAdjustCode) {
        Long lockLogBeginTime;
        if (timeOut == null) {
            timeOut = TIMEOUT;
        }
        if ((lockLogBeginTime = this.gcCalcLogDao.queryLockLogBeginTimeByDim(operateEnum, taskId, currency, periodStr, orgType, orgId, selectAdjustCode)) == null) {
            return;
        }
        long currentDate = System.currentTimeMillis();
        if (currentDate - lockLogBeginTime >= timeOut) {
            LOGGER.warn("\u5b58\u5728\u8d85\u65f6{}\u6beb\u79d2\u7684\u5408\u5e76\u8ba1\u7b97\u65e7\u4efb\u52a1\uff0c\u81ea\u52a8\u89e3\u9501\u3002\u65e7\u4efb\u52a1\u64cd\u4f5c\u5f00\u59cb\u65f6\u95f4\u6233[{}]; \u5f53\u524d\u4efb\u52a1\u64cd\u4f5c\u7c7b\u578b[{}]\uff0c\u4efb\u52a1[{}]\uff0c\u5e01\u79cd[{}]\uff0c\u65f6\u671f[{}]\uff0c\u5355\u4f4d\u7c7b\u578b[{}]\uff0c\u5355\u4f4dID[{}]\u3002", timeOut, lockLogBeginTime, operateEnum.getTitle(), taskId, currency, periodStr, orgType, orgId);
            this.gcCalcLogDao.unLockLogByDim(operateEnum, taskId, currency, periodStr, orgType, orgId, selectAdjustCode);
            return;
        }
        String message = GcI18nUtil.getMessage((String)"gc.calculate.calc.log.concurrent.error", (Object[])new Object[]{operateEnum.getTitle()});
        throw new GcConcurrentLogRuntimeException(message);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public Integer updateCalcLogEO(String logId, String loginfo, TaskStateEnum taskStateEnum) {
        Integer count = this.gcCalcLogDao.updateCalcLog(logId, loginfo, taskStateEnum);
        return count;
    }

    @Override
    public Integer updateCalcLogEO(GcCalcLogOperateEnum gcCalcLogOperateEnum, String taskId, String currency, String periodStr, String orgType, String orgId, String loginfo, TaskStateEnum taskStateEnum, String selectAdjustCode) {
        Integer count = this.gcCalcLogDao.updateCalcLog(gcCalcLogOperateEnum, taskId, currency, periodStr, orgType, orgId, loginfo, taskStateEnum, selectAdjustCode);
        return count;
    }

    @Override
    public GcCalcLogEO queryLatestCalcLogEO(GcCalcLogOperateEnum operateEnum, String taskId, String currency, String periodStr, String orgType, String orgId, String selectAdjustCode) {
        GcCalcLogEO latestCalcLogEO = this.gcCalcLogDao.queryLatestLogs(operateEnum, taskId, currency, periodStr, orgType, orgId, selectAdjustCode);
        return latestCalcLogEO;
    }

    @Override
    public List<GcCalcLogEO> queryLatestCalcLogEOs(GcCalcLogOperateEnum operateEnum, String taskId, String currency, String periodStr, String selectAdjustCode) {
        List<GcCalcLogEO> latestCalcLogEOs = this.gcCalcLogDao.queryLatestLogs(operateEnum, taskId, currency, periodStr, selectAdjustCode);
        return latestCalcLogEOs;
    }

    @Override
    public List<GcCalcLogEO> queryLatestCalcLogEOs(GcCalcLogOperateEnum operateEnum, String taskId, String periodStr, String selectAdjustCode) {
        List<GcCalcLogEO> latestCalcLogEOs = this.gcCalcLogDao.queryLatestLogs(operateEnum, taskId, periodStr, selectAdjustCode);
        return latestCalcLogEOs;
    }

    @Override
    public Integer updateCalcLog(String logId, TaskStateEnum taskStateEnum) {
        return this.gcCalcLogDao.updateCalcLog(logId, taskStateEnum);
    }

    @Override
    public GcCalcLogEO queryCurrOrgLatestCalcLogEO(GcCalcLogOperateEnum completeCalc, String taskId, String orgId, String currency, String periodStr, String selectAdjustCode) {
        ConsolidatedTaskVO consolidatedTaskVO = ((ConsolidatedTaskServiceImpl)SpringContextUtils.getBean(ConsolidatedTaskServiceImpl.class)).getTaskByTaskKeyAndPeriodStr(taskId, periodStr);
        if (consolidatedTaskVO == null) {
            return null;
        }
        ArrayList<String> taskIdList = new ArrayList<String>();
        taskIdList.add(consolidatedTaskVO.getTaskKey());
        taskIdList.addAll(consolidatedTaskVO.getManageTaskKeys());
        return this.gcCalcLogDao.queryCurrOrgLatestCalcLogEO(completeCalc, taskIdList, orgId, currency, periodStr, selectAdjustCode);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public void startCalcLog(GcCalcEnvContextImpl env) {
        GcBaseData currencyData;
        TaskDefine taskDefine;
        GcCalcArgmentsDTO calcArgments = env.getCalcArgments();
        String operateTitle = GcI18nUtil.getMessage((String)"gc.calculate.calc.service.start");
        String periodStr = new DefaultPeriodAdapter().getPeriodTitle(calcArgments.getPeriodStr());
        String taskTitle = null;
        String schemeTitle = null;
        String orgTitle = null;
        String currency = null;
        String errorInfo = "";
        if (!StringUtils.isEmpty((String)calcArgments.getOrgId())) {
            YearPeriodObject yp = new YearPeriodObject(null, calcArgments.getPeriodStr());
            GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)calcArgments.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            GcOrgCacheVO org = tool.getOrgByCode(calcArgments.getOrgId());
            if (org != null) {
                orgTitle = org.getCode() + "|" + org.getTitle();
            }
        }
        if (!StringUtils.isEmpty((String)calcArgments.getTaskId()) && (taskDefine = this.iRunTimeViewController.queryTaskDefine(calcArgments.getTaskId())) != null) {
            taskTitle = taskDefine.getTitle();
        }
        if (!StringUtils.isEmpty((String)calcArgments.getTaskId())) {
            try {
                FormSchemeDefine schemeDefine;
                SchemePeriodLinkDefine schemePeriodLinkDefine = this.iRunTimeViewController.querySchemePeriodLinkByPeriodAndTask(calcArgments.getPeriodStr(), calcArgments.getTaskId());
                if (schemePeriodLinkDefine != null && (schemeDefine = this.iRunTimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey())) != null) {
                    schemeTitle = schemeDefine.getTitle();
                }
            }
            catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        if (calcArgments.getCurrency() != null && (currencyData = GcBaseDataCenterTool.getInstance().queryBaseDataSimpleItem("MD_CURRENCY", calcArgments.getCurrency())) != null) {
            currency = currencyData.getTitle();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\u52a8\u4f5c\u540d\u79f0\uff1a").append(operateTitle).append("\uff1b\n");
        sb.append("\u6267\u884c\u4efb\u52a1\uff1a").append(taskTitle).append("\uff1b\n");
        sb.append("\u62a5\u8868\u65b9\u6848\uff1a").append(schemeTitle).append("\uff1b\n");
        sb.append("\u65f6\u671f\uff1a").append(periodStr).append("\uff1b\n");
        sb.append("\u5e01\u79cd\uff1a").append(currency).append("\uff1b\n");
        sb.append("\u5408\u5e76\u5355\u4f4d\uff1a").append(orgTitle).append("\uff1b\n");
        sb.append("\u6267\u884c\u7ed3\u679c\uff1a").append(env.isSuccessFlag() ? "\u6210\u529f" : "\u5931\u8d25").append("\uff1b\n");
        if (!StringUtils.isEmpty((String)errorInfo)) {
            sb.append("\u5931\u8d25\u539f\u56e0\uff1a").append(errorInfo).append("\uff1b\n");
        }
        LogUtil.add((String)"\u5408\u5e76\u8ba1\u7b97", (String)"\u5f00\u59cb\u5408\u5e76\u8ba1\u7b97", (String)"\u5408\u5e76\u8ba1\u7b97", (String)env.getSn(), (String)sb.toString());
    }
}

