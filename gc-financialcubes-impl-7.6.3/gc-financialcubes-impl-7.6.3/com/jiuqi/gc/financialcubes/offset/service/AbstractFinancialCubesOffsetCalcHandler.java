/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult
 *  com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum
 *  com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor
 *  com.jiuqi.dc.taskscheduling.lockmgr.domain.TaskManageDO
 *  com.jiuqi.dc.taskscheduling.lockmgr.service.TaskManageService
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.DimType
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType
 *  com.jiuqi.gc.financialcubes.mergesummary.dto.FinancialCubesMergeSummaryTaskDto
 *  com.jiuqi.gcreport.offsetitem.dao.GcOffSetItemAdjustCoreDao
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gc.financialcubes.offset.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum;
import com.jiuqi.dc.taskscheduling.core.data.TaskHandleResult;
import com.jiuqi.dc.taskscheduling.core.enums.InstanceTypeEnum;
import com.jiuqi.dc.taskscheduling.core.enums.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.core.intf.ITaskProgressMonitor;
import com.jiuqi.dc.taskscheduling.lockmgr.domain.TaskManageDO;
import com.jiuqi.dc.taskscheduling.lockmgr.service.TaskManageService;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DimType;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;
import com.jiuqi.gc.financialcubes.common.mq.FinancilalCubesBaseTaskHandler;
import com.jiuqi.gc.financialcubes.common.utils.FinancialCubesOrgTypeUtils;
import com.jiuqi.gc.financialcubes.mergesummary.dto.FinancialCubesMergeSummaryTaskDto;
import com.jiuqi.gc.financialcubes.offset.dto.FinancialCubesOffsetTaskDto;
import com.jiuqi.gcreport.offsetitem.dao.GcOffSetItemAdjustCoreDao;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractFinancialCubesOffsetCalcHandler
extends FinancilalCubesBaseTaskHandler {
    public static final Long MAX_DELETE_WAIT = 120000L;
    public static final Long DELETE_WAIT = 300L;
    @Autowired
    private TaskManageService taskService;
    @Autowired
    private GcOffSetItemAdjustCoreDao offSetItemAdjustCoreDao;
    @Autowired
    private FinancialCubesOrgTypeUtils financialCubesOrgTypeUtils;

    public Boolean isDimSerialExecute() {
        return true;
    }

    public String getPreTask() {
        return "";
    }

    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.POST;
    }

    public InstanceTypeEnum getInstanceType() {
        return InstanceTypeEnum.NEW;
    }

    public IDimType getDimType() {
        return DimType.UNITCODE;
    }

    public Map<String, String> getHandleParams(String preParam) {
        HashMap<String, String> paramMap = new HashMap<String, String>();
        if (StringUtils.isEmpty((String)preParam)) {
            return paramMap;
        }
        List offsetTaskDtoList = (List)JsonUtils.readValue((String)preParam, (TypeReference)new TypeReference<List<FinancialCubesOffsetTaskDto>>(){});
        PeriodWrapper periodWrapper = new PeriodWrapper(((FinancialCubesOffsetTaskDto)offsetTaskDtoList.get(0)).getDataTime());
        String periodType = PeriodUtil.convertType2Str((int)periodWrapper.getType());
        for (FinancialCubesOffsetTaskDto dto : offsetTaskDtoList) {
            dto.setPeriodTypeEnum(FinancialCubesPeriodTypeEnum.valueOf((String)periodType));
            Set<String> orgTypeSet = this.financialCubesOrgTypeUtils.listOrgType(dto.getOrgType(), dto.getSystemId(), dto.getDataTime());
            for (String orgType : orgTypeSet) {
                String diffUnitId = this.getDiffUnitId(dto, orgType);
                if (StringUtils.isEmpty((String)diffUnitId)) continue;
                dto.setOrgType(orgType);
                dto.setDiffUnitId(diffUnitId);
                String lockCode = AbstractFinancialCubesOffsetCalcHandler.getLockCode(dto.getDiffUnitId(), dto.getOrgType(), dto.getPeriodTypeEnum());
                this.taskService.initTaskManageByUnitCodes(this.getName() + periodType, Collections.singletonList(lockCode), new Date());
                paramMap.put(JsonUtils.writeValueAsString((Object)dto), lockCode);
            }
        }
        return paramMap;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public TaskHandleResult handleTask(String param, ITaskProgressMonitor monitor) {
        FinancialCubesOffsetTaskDto offsetTaskDto = (FinancialCubesOffsetTaskDto)JsonUtils.readValue((String)param, FinancialCubesOffsetTaskDto.class);
        if (!StringUtils.isEmpty((String)offsetTaskDto.getDeleteId())) {
            this.deleteWait(offsetTaskDto.getDeleteId());
        }
        StringBuilder log = new StringBuilder();
        String lockCode = AbstractFinancialCubesOffsetCalcHandler.getLockCode(offsetTaskDto.getDiffUnitId(), offsetTaskDto.getOrgType(), offsetTaskDto.getPeriodTypeEnum());
        this.taskService.updateBeginHandle(this.getName(), lockCode, new Date());
        TaskManageDO task = this.taskService.getTaskManageByName(this.getName() + offsetTaskDto.getPeriodTypeEnum().getCode(), lockCode);
        offsetTaskDto.setBatchNum(task.getBatchNum() + 1);
        log.append("\u5f00\u59cb\u8ba1\u7b97\n");
        log.append(this.calcFinancialCubes(offsetTaskDto));
        log.append("\u5b8c\u6210\u62b5\u9500\u6570\u636e\u63d2\u5165\u5230\u5408\u5e76\u5e95\u7a3f\u8868\n");
        this.taskService.updateEndHandle(this.getName() + offsetTaskDto.getPeriodTypeEnum().getCode(), lockCode, offsetTaskDto.getBatchNum().intValue());
        FinancialCubesMergeSummaryTaskDto unitMergeSummaryTask = new FinancialCubesMergeSummaryTaskDto(offsetTaskDto.getDiffUnitId(), offsetTaskDto.getSystemId(), offsetTaskDto.getOrgType(), offsetTaskDto.getDataTime(), offsetTaskDto.getSubjectCodes(), offsetTaskDto.getPeriodTypeEnum());
        TaskHandleResult result = new TaskHandleResult();
        result.appendLog(log.toString());
        result.setPreParam(JsonUtils.writeValueAsString(Arrays.asList(unitMergeSummaryTask)));
        return result;
    }

    private void deleteWait(String deleteId) {
        Long currentTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - currentTime < MAX_DELETE_WAIT) {
            try {
                Thread.sleep(DELETE_WAIT);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new BusinessRuntimeException((Throwable)e);
            }
            if (this.getOffSetItemAdjustCoreDao().get((Serializable)((Object)deleteId)) != null) continue;
            break;
        }
    }

    private GcOffSetItemAdjustCoreDao getOffSetItemAdjustCoreDao() {
        if (this.offSetItemAdjustCoreDao == null) {
            this.offSetItemAdjustCoreDao = (GcOffSetItemAdjustCoreDao)SpringContextUtils.getBean(GcOffSetItemAdjustCoreDao.class);
        }
        return this.offSetItemAdjustCoreDao;
    }

    private static String getLockCode(String diffUnitId, String orgType, FinancialCubesPeriodTypeEnum periodTypeEnum) {
        return diffUnitId + "|" + orgType + "|" + periodTypeEnum.getCode();
    }

    protected abstract String calcFinancialCubes(FinancialCubesOffsetTaskDto var1);

    private String getDiffUnitId(FinancialCubesOffsetTaskDto offsetTaskDto, String orgType) {
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)new YearPeriodObject(null, offsetTaskDto.getDataTime()));
        GcOrgCacheVO unitOrg = tool.getOrgByCode(offsetTaskDto.getUnitCode());
        if (unitOrg == null) {
            return null;
        }
        GcOrgCacheVO oppUnitOrg = tool.getOrgByCode(offsetTaskDto.getOppUnitCode());
        if (oppUnitOrg == null) {
            return null;
        }
        GcOrgCacheVO commonUnit = tool.getCommonUnit(unitOrg, oppUnitOrg);
        if (commonUnit == null) {
            return null;
        }
        String diffUnitId = commonUnit.getDiffUnitId();
        return StringUtils.isEmpty((String)diffUnitId) ? null : diffUnitId;
    }
}

