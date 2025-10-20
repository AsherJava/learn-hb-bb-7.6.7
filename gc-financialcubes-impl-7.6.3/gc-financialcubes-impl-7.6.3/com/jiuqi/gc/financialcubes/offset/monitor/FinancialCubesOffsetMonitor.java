/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum
 *  com.jiuqi.common.subject.impl.subject.dto.SubjectDTO
 *  com.jiuqi.common.subject.impl.subject.enums.SubjectClassEnum
 *  com.jiuqi.common.subject.impl.subject.service.SubjectService
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.systemoption.util.GcSystermOptionTool
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.monitor.IOffsetCoreMonitor
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  javax.annotation.Resource
 */
package com.jiuqi.gc.financialcubes.offset.monitor;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum;
import com.jiuqi.common.subject.impl.subject.dto.SubjectDTO;
import com.jiuqi.common.subject.impl.subject.enums.SubjectClassEnum;
import com.jiuqi.common.subject.impl.subject.service.SubjectService;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.gc.financialcubes.offset.dao.FinancialCubesOffsetDao;
import com.jiuqi.gc.financialcubes.offset.dto.FinancialCubesOffsetTaskDto;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.systemoption.util.GcSystermOptionTool;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.monitor.IOffsetCoreMonitor;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class FinancialCubesOffsetMonitor
implements IOffsetCoreMonitor {
    private final String AGE_FIELD_PREFIX = "AGE_";
    @Resource
    private FinancialCubesOffsetDao financialCubesOffsetDao;
    @Autowired
    private TaskHandlerFactory taskHandlerFactory;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private DataModelService dataModelService;

    public String monitorName() {
        return "FinancialCubesOffsetMonitor";
    }

    public void beforeDelete(Collection<String> idList) {
        List<String> ageFieldCodeList;
        if (!this.enable()) {
            return;
        }
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        List<FinancialCubesOffsetTaskDto> dtoList = this.financialCubesOffsetDao.listFinancialCubesOffsetTaskDtoByIdList(idList, false);
        if (CollectionUtils.isEmpty(dtoList)) {
            return;
        }
        String dataTime = dtoList.get(0).getDataTime();
        String periodTypeStr = dataTime.substring(4, 5);
        if (!this.isSupportDataTimeType(periodTypeStr)) {
            return;
        }
        this.sendTaskMsg(dtoList, "FinancialCubesOffsetDimCalcHandler");
        List<FinancialCubesOffsetTaskDto> cfDtoList = this.financialCubesOffsetDao.listFinancialCubesOffsetTaskDtoByIdList(idList, true);
        if (!CollectionUtils.isEmpty(cfDtoList)) {
            this.sendTaskMsg(cfDtoList, "FinancialCubesOffsetCfCalcHandler");
        }
        if (CollectionUtils.isEmpty(ageFieldCodeList = this.listOffsetAgeFieldCode())) {
            return;
        }
        List<FinancialCubesOffsetTaskDto> agingDtoList = this.financialCubesOffsetDao.listFinancialCubesOffsetTaskDtoAgingByIdList(idList, ageFieldCodeList);
        if (!CollectionUtils.isEmpty(agingDtoList)) {
            this.sendTaskMsg(agingDtoList, "FinancialCubesOffsetAgingCalcHandler");
        }
    }

    private List<String> listOffsetAgeFieldCode() {
        GcBaseDataCenterTool tool = GcBaseDataCenterTool.getInstance();
        List ageList = tool.queryBasedataItems("MD_AGING");
        if (CollectionUtils.isEmpty((Collection)ageList)) {
            return Collections.emptyList();
        }
        String offsetTableId = this.dataModelService.getTableModelDefineByCode("GC_OFFSETVCHRITEM").getID();
        Set offsetColumnCodeSet = this.dataModelService.getColumnModelDefinesByTable(offsetTableId).stream().map(IModelDefineItem::getCode).collect(Collectors.toSet());
        List<String> ageFieldCodeList = ageList.stream().map(item -> "AGE_" + item.getCode()).filter(item -> offsetColumnCodeSet.contains(item)).collect(Collectors.toList());
        return ageFieldCodeList;
    }

    @Async(value="fincubes-offset-executor")
    public void afterSave(List<GcOffSetVchrItemAdjustEO> eoList) {
        String dataTime = eoList.get(0).getDefaultPeriod();
        String periodTypeStr = dataTime.substring(4, 5);
        if (!this.needHandlerFinancialCubes(eoList, periodTypeStr)) {
            return;
        }
        HashMap<String, FinancialCubesOffsetTaskDto> equals2OffsetTaskMap = new HashMap<String, FinancialCubesOffsetTaskDto>();
        HashMap<String, FinancialCubesOffsetTaskDto> equals2CfOffsetTaskMap = new HashMap<String, FinancialCubesOffsetTaskDto>();
        HashMap<String, FinancialCubesOffsetTaskDto> equals2AgingOffsetTaskMap = new HashMap<String, FinancialCubesOffsetTaskDto>();
        GcBaseDataCenterTool tool = GcBaseDataCenterTool.getInstance();
        Set ageFieldCodeSet = tool.queryBasedataItems("MD_AGING").stream().map(GcBaseData::getCode).collect(Collectors.toSet());
        block0: for (GcOffSetVchrItemAdjustEO eo : eoList) {
            SubjectDTO subjectDTO;
            FinancialCubesOffsetTaskDto dto = new FinancialCubesOffsetTaskDto();
            dto.setUnitCode(eo.getUnitId());
            dto.setOppUnitCode(eo.getOppUnitId());
            dto.setDataTime(eo.getDefaultPeriod());
            dto.setOrgType(eo.getOrgType());
            dto.setSystemId(eo.getSystemId());
            String equalsString = dto.getEqualsString();
            if (!equals2OffsetTaskMap.containsKey(equalsString)) {
                equals2OffsetTaskMap.put(equalsString, dto);
            }
            if ((subjectDTO = this.subjectService.findByCode(eo.getSubjectCode())) != null && SubjectClassEnum.CASH.getCode().equals(subjectDTO.getGeneralType()) && !equals2CfOffsetTaskMap.containsKey(equalsString)) {
                equals2CfOffsetTaskMap.put(equalsString, dto);
            }
            for (String ageField : ageFieldCodeSet) {
                if (eo.getFieldValue("AGE_" + ageField) == null) continue;
                if (equals2AgingOffsetTaskMap.containsKey(equalsString)) continue block0;
                equals2AgingOffsetTaskMap.put(equalsString, dto);
                continue block0;
            }
        }
        this.afterSaveSendMq(equals2OffsetTaskMap, equals2CfOffsetTaskMap, equals2AgingOffsetTaskMap);
    }

    private boolean needHandlerFinancialCubes(List<GcOffSetVchrItemAdjustEO> eoList, String periodTypeStr) {
        if (!this.enable()) {
            return false;
        }
        if (CollectionUtils.isEmpty(eoList)) {
            return false;
        }
        return this.isSupportDataTimeType(periodTypeStr);
    }

    private void afterSaveSendMq(Map<String, FinancialCubesOffsetTaskDto> equals2OffsetTaskMap, Map<String, FinancialCubesOffsetTaskDto> equals2CfOffsetTaskMap, Map<String, FinancialCubesOffsetTaskDto> equals2AgingOffsetTaskMap) {
        if (CollectionUtils.isEmpty(equals2OffsetTaskMap.values())) {
            return;
        }
        this.sendTaskMsg(equals2OffsetTaskMap.values(), "FinancialCubesOffsetDimCalcHandler");
        if (!CollectionUtils.isEmpty(equals2CfOffsetTaskMap.values())) {
            this.sendTaskMsg(equals2CfOffsetTaskMap.values(), "FinancialCubesOffsetCfCalcHandler");
        }
        if (!CollectionUtils.isEmpty(equals2AgingOffsetTaskMap.values())) {
            this.sendTaskMsg(equals2AgingOffsetTaskMap.values(), "FinancialCubesOffsetAgingCalcHandler");
        }
    }

    private boolean isSupportDataTimeType(String periodTypeStr) {
        Set supportedPeriodTypeSet = Arrays.stream(FinancialCubesPeriodTypeEnum.values()).map(FinancialCubesPeriodTypeEnum::getCode).collect(Collectors.toSet());
        return supportedPeriodTypeSet.contains(periodTypeStr);
    }

    private void sendTaskMsg(Collection<FinancialCubesOffsetTaskDto> dtoList, String taskName) {
        String message = JsonUtils.writeValueAsString(dtoList);
        this.taskHandlerFactory.getMainTaskHandlerClient().startTask(taskName, message);
    }

    private boolean enable() {
        String optionValue = GcSystermOptionTool.getOptionValue((String)"FINANCIAL_CUBES_ENABLE");
        return "1".equals(optionValue);
    }
}

