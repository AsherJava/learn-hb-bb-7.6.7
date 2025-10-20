/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum
 *  com.jiuqi.common.financialcubes.dto.FinancialCubesAdjustTaskDTO
 *  com.jiuqi.common.financialcubes.fincubeadjustvchr.FincubeCommonAdjustVchrDao
 *  com.jiuqi.common.subject.impl.subject.dto.SubjectDTO
 *  com.jiuqi.common.subject.impl.subject.enums.SubjectClassEnum
 *  com.jiuqi.common.subject.impl.subject.service.SubjectService
 *  com.jiuqi.dc.adjustvchr.impl.entity.AdjustVchrItemEO
 *  com.jiuqi.dc.adjustvchr.impl.monitor.IAdjustVoucherItemMonitor
 *  com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  javax.annotation.Resource
 */
package com.jiuqi.gc.financialcubes.adjustcal;

import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum;
import com.jiuqi.common.financialcubes.dto.FinancialCubesAdjustTaskDTO;
import com.jiuqi.common.financialcubes.fincubeadjustvchr.FincubeCommonAdjustVchrDao;
import com.jiuqi.common.subject.impl.subject.dto.SubjectDTO;
import com.jiuqi.common.subject.impl.subject.enums.SubjectClassEnum;
import com.jiuqi.common.subject.impl.subject.service.SubjectService;
import com.jiuqi.dc.adjustvchr.impl.entity.AdjustVchrItemEO;
import com.jiuqi.dc.adjustvchr.impl.monitor.IAdjustVoucherItemMonitor;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.gc.financialcubes.common.dao.TempCodeDao;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class FinancialCubesAdjustVchrMonitor
implements IAdjustVoucherItemMonitor {
    @Resource
    FincubeCommonAdjustVchrDao fincubeCommonAdjustVchrDao;
    @Resource
    private TaskHandlerFactory taskHandlerFactory;
    @Resource
    private SubjectService subjectService;
    @Autowired
    private TempCodeDao dcTempCodeDao;
    @Resource
    private INvwaSystemOptionService sysOptionService;
    private final Logger logger = LoggerFactory.getLogger(FinancialCubesAdjustVchrMonitor.class);

    public String monitorName() {
        return "FinCubesAdjustVchrMonitor";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @OuterTransaction
    public void beforeDelete(Collection<String> vchrIdList) {
        if (!"1".equals(this.sysOptionService.findValueById("FINANCIAL_CUBES_ENABLE"))) {
            return;
        }
        try {
            List agingDtoList;
            if (CollectionUtils.isEmpty(vchrIdList)) {
                return;
            }
            String deleteId = this.fincubeCommonAdjustVchrDao.getDeleteIdByVchrIdList(vchrIdList);
            List masterDimList = this.fincubeCommonAdjustVchrDao.listVchrMasterDimByVchrId(vchrIdList, deleteId);
            if (CollectionUtils.isEmpty((Collection)masterDimList)) {
                return;
            }
            String periodType = ((FinancialCubesAdjustTaskDTO)masterDimList.get(0)).getPeriodType();
            FinancialCubesPeriodTypeEnum periodTypeEnum = FinancialCubesPeriodTypeEnum.getByCode((String)periodType);
            if (periodTypeEnum == null) {
                return;
            }
            if (!FinancialCubesPeriodTypeEnum.Y.getCode().equals(periodType)) {
                masterDimList.forEach(item -> item.setAcctPeriod(Integer.valueOf(periodTypeEnum.converterToMonth(item.getAcctPeriod().intValue()))));
            }
            this.sendTaskMsg(masterDimList, "FinancialCubesAdjustDimCalcHandler");
            ArrayList<FinancialCubesAdjustTaskDTO> cfDtoList = new ArrayList();
            try {
                List subjectBaseDataList = this.subjectService.list(new BaseDataDTO());
                List<String> cashSubjectCodes = subjectBaseDataList.stream().filter(subject -> SubjectClassEnum.CASH.getCode().equals(subject.getGeneralType())).map(BaseDataDO::getCode).collect(Collectors.toList());
                this.dcTempCodeDao.batchInsert(cashSubjectCodes);
                cfDtoList = this.fincubeCommonAdjustVchrDao.listVchrMasterCfByVchrId(vchrIdList, deleteId);
            }
            finally {
                this.dcTempCodeDao.cleanTemp();
            }
            if (!CollectionUtils.isEmpty(cfDtoList)) {
                this.sendTaskMsg(cfDtoList, "FinancialCubesAdjustCfCalcHandler");
            }
            if (!CollectionUtils.isEmpty((Collection)(agingDtoList = this.fincubeCommonAdjustVchrDao.listAgingVchrMasterDimByVchrId(vchrIdList, deleteId)))) {
                this.sendTaskMsg(agingDtoList, "FinancialCubesAdjustAgingCalcHandler");
            }
        }
        catch (Exception e) {
            this.logger.error("\u8d22\u52a1\u591a\u7ef4\u8c03\u6574\u5904\u7406\u5f02\u5e38", e);
        }
    }

    @Async(value="fincubes-offset-executor")
    public void afterSave(List<AdjustVchrItemEO> items) {
        if (!"1".equals(this.sysOptionService.findValueById("FINANCIAL_CUBES_ENABLE"))) {
            return;
        }
        try {
            if (CollectionUtils.isEmpty(items)) {
                return;
            }
            String periodType = items.get(0).getPeriodType();
            FinancialCubesPeriodTypeEnum periodTypeEnum = FinancialCubesPeriodTypeEnum.getByCode((String)periodType);
            if (periodTypeEnum == null) {
                return;
            }
            HashMap<String, FinancialCubesAdjustTaskDTO> equals2AdjustTaskMap = new HashMap<String, FinancialCubesAdjustTaskDTO>();
            HashMap<String, FinancialCubesAdjustTaskDTO> equals2CfAdjustTaskMap = new HashMap<String, FinancialCubesAdjustTaskDTO>();
            HashMap<String, FinancialCubesAdjustTaskDTO> equals2AgingAdjustTaskMap = new HashMap<String, FinancialCubesAdjustTaskDTO>();
            for (AdjustVchrItemEO itemDO : items) {
                SubjectDTO subjectDTO;
                FinancialCubesAdjustTaskDTO dto = new FinancialCubesAdjustTaskDTO();
                dto.setUnitCode(itemDO.getUnitCode());
                dto.setAcctYear(itemDO.getAcctYear());
                dto.setAcctPeriod(Integer.valueOf(periodTypeEnum.converterToMonth(itemDO.getAcctPeriod().intValue())));
                dto.setPeriodType(itemDO.getPeriodType());
                String equalsString = this.getEqualsString(dto);
                if (!equals2AdjustTaskMap.containsKey(equalsString)) {
                    equals2AdjustTaskMap.put(equalsString, dto);
                }
                if (!((subjectDTO = this.subjectService.getByCode(itemDO.getSubjectCode())) == null || !SubjectClassEnum.CASH.getCode().equals(subjectDTO.getGeneralType()) && StringUtils.isEmpty((String)itemDO.getCfItemCode()) || equals2CfAdjustTaskMap.containsKey(equalsString))) {
                    equals2CfAdjustTaskMap.put(equalsString, dto);
                }
                if (itemDO.getBizDate() == null || equals2AgingAdjustTaskMap.containsKey(equalsString)) continue;
                equals2AgingAdjustTaskMap.put(equalsString, dto);
            }
            if (CollectionUtils.isEmpty(equals2AdjustTaskMap.values())) {
                return;
            }
            this.sendTaskMsg(equals2AdjustTaskMap.values(), "FinancialCubesAdjustDimCalcHandler");
            if (!CollectionUtils.isEmpty(equals2CfAdjustTaskMap.values())) {
                this.sendTaskMsg(equals2CfAdjustTaskMap.values(), "FinancialCubesAdjustCfCalcHandler");
            }
            if (!CollectionUtils.isEmpty(equals2AgingAdjustTaskMap.values())) {
                this.sendTaskMsg(equals2AgingAdjustTaskMap.values(), "FinancialCubesAdjustAgingCalcHandler");
            }
        }
        catch (Exception e) {
            this.logger.error("\u8d22\u52a1\u591a\u7ef4\u8c03\u6574\u5904\u7406\u5f02\u5e38", e);
        }
    }

    private void sendTaskMsg(Collection<FinancialCubesAdjustTaskDTO> vchrMasterDimList, String taskName) {
        String message = JsonUtils.writeValueAsString(vchrMasterDimList);
        this.taskHandlerFactory.getMainTaskHandlerClient().startTask(taskName, message);
    }

    private String getEqualsString(FinancialCubesAdjustTaskDTO dim) {
        return dim.getUnitCode() + "|" + dim.getAcctYear() + "|" + dim.getAcctPeriod();
    }
}

