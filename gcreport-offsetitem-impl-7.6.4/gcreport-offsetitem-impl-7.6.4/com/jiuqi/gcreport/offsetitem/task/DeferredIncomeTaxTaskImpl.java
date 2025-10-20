/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.vo.EndCarryForwardDataPoolVO
 *  com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryRowVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.gcreport.offsetitem.task;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.task.IGcEndCarryForwardTask;
import com.jiuqi.gcreport.offsetitem.utils.GcStructureVchrItemUtil;
import com.jiuqi.gcreport.offsetitem.vo.EndCarryForwardDataPoolVO;
import com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryRowVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class DeferredIncomeTaxTaskImpl
implements IGcEndCarryForwardTask {
    private ConsolidatedOptionVO optionVO;

    public DeferredIncomeTaxTaskImpl() {
    }

    public DeferredIncomeTaxTaskImpl(ConsolidatedOptionVO optionVO) {
        this();
        this.optionVO = optionVO;
    }

    @Override
    public void calculate(EndCarryForwardDataPoolVO dataPool, QueryParamsVO queryParamsVO, ConsolidatedOptionVO optionVO) {
        List deferredRowData = dataPool.getDeferredIncomeTaxGroup().getRowData();
        List minorityRecoveryRowData = dataPool.getMinorityRecoveryGroup().getRowData();
        List fieldCodes = optionVO.getManagementAccountingFieldCodes();
        HashMap<ArrayKey, Double> groupByFieldCode = new HashMap<ArrayKey, Double>();
        this.processDeferredIncomeTax(deferredRowData, fieldCodes, groupByFieldCode, optionVO);
        this.processDeferredIncomeTax(minorityRecoveryRowData, fieldCodes, groupByFieldCode, optionVO);
        List<GcOffSetVchrItemDTO> gcOffSetVchrItemDTOS = GcStructureVchrItemUtil.convert2offsetRecord(queryParamsVO, groupByFieldCode, optionVO, OffSetSrcTypeEnum.DEFERRED_INCOME_TAX);
        dataPool.getDeferredIncomeTaxGroup().setEndCarryForwarditem(gcOffSetVchrItemDTOS);
    }

    private void processDeferredIncomeTax(List<MinorityRecoveryRowVO> rowData, List<String> fieldCodes, HashMap<ArrayKey, Double> groupByFieldCode, ConsolidatedOptionVO optionVO) {
        if (!CollectionUtils.isEmpty(rowData)) {
            this.appendDiTaxRatio(rowData, optionVO);
            for (MinorityRecoveryRowVO rowVO : rowData) {
                rowVO.setOffsetType(Integer.valueOf(1));
                rowVO.setDiTaxAmt(rowVO.getOffsetAmt().multiply(rowVO.getDiTaxRate()).setScale(2, 4));
                rowVO.setLossGainAmt(rowVO.getOffsetAmt().subtract(rowVO.getDiTaxAmt()).setScale(2, 4));
                ArrayKey fieldValues = GcStructureVchrItemUtil.getFieldValues(fieldCodes, rowVO);
                groupByFieldCode.merge(fieldValues, rowVO.getDiTaxAmt().doubleValue(), Double::sum);
            }
        }
    }

    private void appendDiTaxRatio(List<MinorityRecoveryRowVO> minorityRecoveryRowList, ConsolidatedOptionVO optionVO) {
        BigDecimal rate = org.apache.commons.lang3.StringUtils.isNumeric((CharSequence)optionVO.getDiTax().getZbCode()) ? new BigDecimal(optionVO.getDiTax().getZbCode()).divide(new BigDecimal(100)) : BigDecimal.ZERO;
        minorityRecoveryRowList.forEach(row -> row.setDiTaxRate(rate));
    }

    public BigDecimal getRateZbValue(String unitCode, Map<String, BigDecimal> unitCode2rateMap, QueryParamsVO queryParamsVO) {
        if (StringUtils.isEmpty((String)unitCode)) {
            return BigDecimal.ZERO;
        }
        if (unitCode2rateMap.containsKey(unitCode)) {
            return unitCode2rateMap.get(unitCode);
        }
        BigDecimal zbValue = this.getZbValue(queryParamsVO, unitCode);
        unitCode2rateMap.put(unitCode, zbValue);
        return zbValue;
    }

    private BigDecimal getZbValue(QueryParamsVO condition, String unitCode) {
        String zbRegex = "(.*?)\\[(.*?)\\]";
        Pattern zbPattern = Pattern.compile(zbRegex);
        Matcher matcher = zbPattern.matcher(this.optionVO.getDiTax().getZbCode());
        if (!matcher.find()) {
            return BigDecimal.ZERO;
        }
        String sql = "select %1$s\n from %2$s t\nwhere t.MDCODE = '" + unitCode + "'\n   and t." + "DATATIME" + " = '" + condition.getPeriodStr() + "'\n   and t." + "MD_CURRENCY" + " = '" + condition.getCurrency() + "'\n   and t." + "MD_GCORGTYPE" + " in ('" + condition.getOrgType() + "')\n";
        sql = String.format(sql, matcher.group(2), matcher.group(1));
        String result = (String)EntNativeSqlDefaultDao.getInstance().selectFirst(String.class, sql, new Object[0]);
        if (null != result) {
            return new BigDecimal(result).divide(new BigDecimal(100));
        }
        return BigDecimal.ZERO;
    }
}

