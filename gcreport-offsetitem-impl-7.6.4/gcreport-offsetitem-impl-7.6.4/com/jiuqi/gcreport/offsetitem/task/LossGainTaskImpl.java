/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.vo.EndCarryForwardDataPoolVO
 *  com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryRowVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.offsetitem.task;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.task.IGcEndCarryForwardTask;
import com.jiuqi.gcreport.offsetitem.utils.GcStructureVchrItemUtil;
import com.jiuqi.gcreport.offsetitem.vo.EndCarryForwardDataPoolVO;
import com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryRowVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class LossGainTaskImpl
implements IGcEndCarryForwardTask {
    private final String DEFFERED_INCOME_TAX_REVERSE = "\u9012\u5ef6\u6240\u5f97\u7a0e-\u51b2\u9500";
    private final String MINORITY_LOSS_GAIN_RECOVERY_REVERSE = "\u5c11\u6570\u80a1\u4e1c\u635f\u76ca\u8fd8\u539f-\u51b2\u9500";

    public static MinorityRecoveryParamsVO lossGainSimpleParam(QueryParamsVO queryParamsVO) {
        MinorityRecoveryParamsVO newQueryParamsVO = new MinorityRecoveryParamsVO();
        newQueryParamsVO.setTaskId(queryParamsVO.getTaskId());
        newQueryParamsVO.setSchemeId(queryParamsVO.getSchemeId());
        newQueryParamsVO.setSystemId(queryParamsVO.getSystemId());
        newQueryParamsVO.setPeriodStr(queryParamsVO.getPeriodStr());
        newQueryParamsVO.setAcctYear(queryParamsVO.getAcctYear());
        newQueryParamsVO.setAcctPeriod(queryParamsVO.getAcctPeriod());
        newQueryParamsVO.setCurrency(queryParamsVO.getCurrency());
        newQueryParamsVO.setOrgId(queryParamsVO.getOrgId());
        newQueryParamsVO.setOrgIds(queryParamsVO.getOrgIds());
        newQueryParamsVO.setOrgType(queryParamsVO.getOrgType());
        newQueryParamsVO.setSelectAdjustCode(queryParamsVO.getSelectAdjustCode());
        newQueryParamsVO.setArbitrarilyMerge(queryParamsVO.getArbitrarilyMerge());
        return newQueryParamsVO;
    }

    @Override
    public void calculate(EndCarryForwardDataPoolVO dataPool, QueryParamsVO queryParamsVO, ConsolidatedOptionVO optionVO) {
        List deferredRowData = dataPool.getDeferredIncomeTaxGroup().getRowData();
        List minorityRecoveryRowData = dataPool.getMinorityRecoveryGroup().getRowData();
        List lossGainRowData = dataPool.getLossGainGroup().getRowData();
        if (!CollectionUtils.isEmpty(lossGainRowData)) {
            String subjectCode = ((MinorityRecoveryRowVO)lossGainRowData.get(0)).getSubjectCode();
            String subjectTitle = ((MinorityRecoveryRowVO)lossGainRowData.get(0)).getSubjectTitle();
            List fieldCodes = optionVO.getManagementAccountingFieldCodes();
            HashMap<ArrayKey, Double> groupByFieldCode = new HashMap<ArrayKey, Double>();
            this.processLossGain(lossGainRowData, fieldCodes, groupByFieldCode);
            List<MinorityRecoveryRowVO> deferredLossGain = this.structureLossGain(deferredRowData, "\u9012\u5ef6\u6240\u5f97\u7a0e-\u51b2\u9500", subjectCode, subjectTitle, fieldCodes, groupByFieldCode);
            List<MinorityRecoveryRowVO> minorityLossGain = this.structureLossGain(minorityRecoveryRowData, "\u5c11\u6570\u80a1\u4e1c\u635f\u76ca\u8fd8\u539f-\u51b2\u9500", subjectCode, subjectTitle, fieldCodes, groupByFieldCode);
            lossGainRowData.addAll(deferredLossGain);
            lossGainRowData.addAll(minorityLossGain);
            List<GcOffSetVchrItemDTO> gcOffSetVchrItemDTOS = GcStructureVchrItemUtil.convert2offsetRecord(queryParamsVO, groupByFieldCode, optionVO, OffSetSrcTypeEnum.BROUGHT_FORWARD_LOSS_GAIN);
            dataPool.getLossGainGroup().setEndCarryForwarditem(gcOffSetVchrItemDTOS);
        }
    }

    private void processLossGain(List<MinorityRecoveryRowVO> rowData, List<String> fieldCodes, HashMap<ArrayKey, Double> groupByFieldCode) {
        for (MinorityRecoveryRowVO rowVO : rowData) {
            rowVO.setOffsetType(Integer.valueOf(2));
            rowVO.setLossGainAmt(rowVO.getOffsetAmt().setScale(2, 4));
            ArrayKey fieldValues = GcStructureVchrItemUtil.getFieldValues(fieldCodes, rowVO);
            groupByFieldCode.merge(fieldValues, rowVO.getLossGainAmt().doubleValue(), Double::sum);
        }
    }

    private List<MinorityRecoveryRowVO> structureLossGain(List<MinorityRecoveryRowVO> rowDatas, String unitTitle, String subjectCode, String subjectTitle, List<String> fieldCodes, HashMap<ArrayKey, Double> groupByFieldCode) {
        HashMap<ArrayKey, MinorityRecoveryRowVO> rowMap = new HashMap<ArrayKey, MinorityRecoveryRowVO>();
        if (!CollectionUtils.isEmpty(rowDatas)) {
            for (MinorityRecoveryRowVO rowVO : rowDatas) {
                ArrayKey fieldValues = GcStructureVchrItemUtil.getFieldValues(fieldCodes, rowVO);
                if (rowMap.containsKey(fieldValues)) {
                    MinorityRecoveryRowVO valueRow = (MinorityRecoveryRowVO)rowMap.get(fieldValues);
                    valueRow.setOffsetAmt(valueRow.getLossGainAmt().subtract(rowVO.getOffsetAmt()));
                    valueRow.setLossGainAmt(valueRow.getLossGainAmt().subtract(rowVO.getOffsetAmt()));
                } else {
                    MinorityRecoveryRowVO newRow = new MinorityRecoveryRowVO();
                    newRow.setOppUnitTitle(unitTitle);
                    newRow.setSubjectCode(subjectCode);
                    newRow.setSubjectTitle(subjectTitle);
                    newRow.setOffsetAmt(BigDecimal.ZERO.subtract(rowVO.getOffsetAmt()));
                    newRow.setLossGainAmt(BigDecimal.ZERO.subtract(rowVO.getOffsetAmt()));
                    rowVO.getFields().forEach((key, value) -> newRow.addFieldValue(key, value));
                    rowMap.put(fieldValues, newRow);
                }
                groupByFieldCode.merge(fieldValues, -rowVO.getDiTaxAmt().doubleValue(), Double::sum);
                groupByFieldCode.merge(fieldValues, -rowVO.getMinorityTotalAmt().doubleValue(), Double::sum);
            }
        }
        return new ArrayList<MinorityRecoveryRowVO>(rowMap.values());
    }
}

