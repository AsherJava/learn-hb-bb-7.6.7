/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.task.IMinorityLossGainRecoveryTask
 *  com.jiuqi.gcreport.offsetitem.utils.GcStructureVchrItemUtil
 *  com.jiuqi.gcreport.offsetitem.vo.EndCarryForwardDataPoolVO
 *  com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryRowVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.gcreport.invest.offsetitem.task;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.invest.formula.function.rule.invest.compreequityratio.InvestCalculator;
import com.jiuqi.gcreport.invest.investbill.executor.CompreEquityRatioTask;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.task.IMinorityLossGainRecoveryTask;
import com.jiuqi.gcreport.offsetitem.utils.GcStructureVchrItemUtil;
import com.jiuqi.gcreport.offsetitem.vo.EndCarryForwardDataPoolVO;
import com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryRowVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MinorityLossGainRecoveryTaskImpl
implements IMinorityLossGainRecoveryTask {
    private static final Logger logger = LoggerFactory.getLogger(MinorityLossGainRecoveryTaskImpl.class);
    @Autowired
    private CompreEquityRatioTask compreEquityRatioTask;

    public void calculate(EndCarryForwardDataPoolVO dataPool, QueryParamsVO queryParamsVO, ConsolidatedOptionVO optionVO) {
        List rowData = dataPool.getMinorityRecoveryGroup().getRowData();
        if (!CollectionUtils.isEmpty((Collection)rowData)) {
            List fieldCodes = optionVO.getManagementAccountingFieldCodes();
            HashMap<ArrayKey, Double> groupByFieldCode = new HashMap<ArrayKey, Double>();
            YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
            GcOrgCenterService orgCenterService = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            String baseUnitCode = orgCenterService.getDeepestBaseUnitId(queryParamsVO.getOrgId());
            this.appendEquityRatio(rowData, queryParamsVO);
            for (MinorityRecoveryRowVO rowVO : rowData) {
                if (StringUtils.isEmpty((CharSequence)baseUnitCode) || !baseUnitCode.equals(rowVO.getUnitCode()) && !baseUnitCode.equals(rowVO.getOppUnitCode())) {
                    rowVO.setMinorityType(Integer.valueOf(2));
                } else if (baseUnitCode.equals(rowVO.getUnitCode())) {
                    rowVO.setMinorityType(Integer.valueOf(1));
                } else {
                    rowVO.setMinorityType(Integer.valueOf(0));
                }
                rowVO.setOffsetType(Integer.valueOf(0));
                BigDecimal minorityOffsetAmt = rowVO.getOffsetAmt().multiply(BigDecimal.ONE.subtract(rowVO.getOppUnitEquityRatio())).setScale(2, 4);
                BigDecimal minorityDiTaxAmt = rowVO.getDiTaxAmt().multiply(rowVO.getOppUnitEquityRatio().subtract(BigDecimal.ONE)).setScale(2, 4);
                BigDecimal minorityTotalAmt = minorityOffsetAmt.add(minorityDiTaxAmt).setScale(2, 4);
                BigDecimal lossGainAmt = rowVO.getOffsetAmt().subtract(rowVO.getDiTaxAmt()).subtract(minorityTotalAmt).setScale(2, 4);
                rowVO.setMinorityOffsetAmt(minorityOffsetAmt);
                rowVO.setMinorityDiTaxAmt(minorityDiTaxAmt);
                rowVO.setMinorityTotalAmt(minorityTotalAmt);
                rowVO.setLossGainAmt(lossGainAmt);
                ArrayKey fieldValues = GcStructureVchrItemUtil.getFieldValues((List)fieldCodes, (MinorityRecoveryRowVO)rowVO);
                groupByFieldCode.merge(fieldValues, rowVO.getMinorityTotalAmt().doubleValue(), Double::sum);
            }
            List gcOffSetVchrItemDTOS = GcStructureVchrItemUtil.convert2offsetRecord((QueryParamsVO)queryParamsVO, groupByFieldCode, (ConsolidatedOptionVO)optionVO, (OffSetSrcTypeEnum)OffSetSrcTypeEnum.MINORITY_LOSS_GAIN_RECOVERY);
            dataPool.getMinorityRecoveryGroup().setEndCarryForwarditem(gcOffSetVchrItemDTOS);
        }
    }

    public int getCompreEquityRatioFractionDigits() {
        DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        try {
            TableModelDefine tableDefine = dataModelService.getTableModelDefineByCode("GC_INVESTBILL");
            ColumnModelDefine define = dataModelService.getColumnModelDefineByCode(tableDefine.getID(), "COMPREEQUITYRATIO");
            return define.getDecimal();
        }
        catch (Exception e) {
            logger.error("\u6295\u8d44\u5355\u636e\u8868\u672a\u627e\u5230\u7efc\u5408\u6301\u80a1\u6bd4\u4f8b\u5b57\u6bb5\u3002", e);
            return 14;
        }
    }

    private void appendEquityRatio(List<MinorityRecoveryRowVO> minorityRecoveryRowList, QueryParamsVO queryParamsVO) {
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        ArrayList<Map<String, Object>> investRecords = new ArrayList<Map<String, Object>>();
        InvestCalculator calculator = this.compreEquityRatioTask.getInvestCalculatorFromDb(queryParamsVO.getOrgType(), queryParamsVO.getPeriodStr(), queryParamsVO.getOrgId(), investRecords);
        HashMap<String, Double> unitCode2RatioCacheMap = new HashMap<String, Double>(64);
        for (MinorityRecoveryRowVO rowVO : minorityRecoveryRowList) {
            Double unitEquityRatio = this.calcCompreEquityRatio(unitCode2RatioCacheMap, rowVO.getUnitCode(), calculator, queryParamsVO.getOrgType(), queryParamsVO.getPeriodStr(), investRecords);
            rowVO.setUnitEquityRatio(BigDecimal.valueOf(unitEquityRatio));
            Double oppUnitEquityRatio = this.calcCompreEquityRatio(unitCode2RatioCacheMap, rowVO.getOppUnitCode(), calculator, queryParamsVO.getOrgType(), queryParamsVO.getPeriodStr(), investRecords);
            rowVO.setOppUnitEquityRatio(BigDecimal.valueOf(oppUnitEquityRatio));
        }
        this.resetBaseUnitEquityRatio(minorityRecoveryRowList, queryParamsVO);
    }

    private Double calcCompreEquityRatio(Map<String, Double> unitCode2RatioCacheMap, String unitCode, InvestCalculator calculator, String orgType, String periodStr, List<Map<String, Object>> investRecords) {
        if (null == unitCode) {
            return 1.0;
        }
        if (unitCode2RatioCacheMap.containsKey(unitCode)) {
            return unitCode2RatioCacheMap.get(unitCode);
        }
        double ratio = this.compreEquityRatioTask.getCompreEquityRatioValue(calculator.getBaseUnitId(), calculator, investRecords, orgType, periodStr, unitCode);
        ratio = new BigDecimal(String.valueOf(ratio)).divide(new BigDecimal(100), 16, 4).doubleValue();
        unitCode2RatioCacheMap.put(unitCode, ratio);
        return ratio;
    }

    private void resetBaseUnitEquityRatio(List<MinorityRecoveryRowVO> minorityRecoveryRowList, QueryParamsVO queryParamsVO) {
        YearPeriodObject yp = new YearPeriodObject(null, queryParamsVO.getPeriodStr());
        GcOrgCenterService orgCenterService = GcOrgPublicTool.getInstance((String)queryParamsVO.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        String baseUnitCode = orgCenterService.getDeepestBaseUnitId(queryParamsVO.getOrgId());
        if (StringUtils.isEmpty((CharSequence)baseUnitCode)) {
            return;
        }
        for (MinorityRecoveryRowVO rowVO : minorityRecoveryRowList) {
            if (baseUnitCode.equals(rowVO.getUnitCode())) {
                rowVO.setUnitEquityRatio(BigDecimal.ONE);
                continue;
            }
            if (!baseUnitCode.equals(rowVO.getOppUnitCode())) continue;
            rowVO.setOppUnitEquityRatio(BigDecimal.ONE);
        }
    }
}

