/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.common.util.NumberUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 */
package com.jiuqi.gcreport.invest.investbill.executor;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.common.util.NumberUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.invest.formula.function.rule.invest.compreequityratio.InvestCalculator;
import com.jiuqi.gcreport.invest.formula.function.rule.invest.compreequityratio.InvestFunctionException;
import com.jiuqi.gcreport.invest.investbill.dao.InvestBillDao;
import com.jiuqi.gcreport.invest.investbill.dto.GcInvestBillGroupDTO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompreEquityRatioTask {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private InvestBillDao investBillDao;

    public void writeCompreEquityRatioValue(GcCalcArgmentsDTO calcArgments, List<GcInvestBillGroupDTO> inDirectInvestmentGroups) throws InvestFunctionException {
        if (CollectionUtils.isEmpty(inDirectInvestmentGroups)) {
            return;
        }
        String baseUnitCode = this.getBaseUnit(calcArgments.getOrgType(), calcArgments.getPeriodStr(), calcArgments.getOrgId());
        if (StringUtils.isEmpty((String)baseUnitCode)) {
            return;
        }
        List<Map<String, Object>> records = this.listInvestRecords(calcArgments);
        InvestCalculator calc = this.getInvestCalculatorFromDb(records);
        calc.setBaseUnitId(baseUnitCode);
        for (GcInvestBillGroupDTO inDirectInvestmentGroup : inDirectInvestmentGroups) {
            DefaultTableEntity master = inDirectInvestmentGroup.getMaster();
            String unitCode = (String)master.getFieldValue("UNITCODE");
            String investedUnitCode = (String)master.getFieldValue("INVESTEDUNIT");
            this.logger.info("\u5f00\u59cb\u8ba1\u7b97\u3010\u5bf9\u6295\u8d44\u5355\u4f4d\u7684\u7efc\u5408\u6301\u80a1\u6bd4\u4f8b\u3011\uff1a\u6295\u8d44\u5355\u4f4d\uff1a[{}]\uff1b\u88ab\u6295\u8d44\u5355\u4f4d\uff1a[{}]", (Object)unitCode, (Object)investedUnitCode);
            this.compreEquityRatioHandle(baseUnitCode, calc, master, records, unitCode, calcArgments.getOrgType(), calcArgments.getPeriodStr(), "COMPREEQUITYRATIO");
            this.logger.info("\u5f00\u59cb\u8ba1\u7b97\u3010\u5bf9\u88ab\u6295\u8d44\u5355\u4f4d\u7684\u7efc\u5408\u6301\u80a1\u6bd4\u4f8b\u3011\uff1a\u6295\u8d44\u5355\u4f4d\uff1a[{}]\uff1b\u88ab\u6295\u8d44\u5355\u4f4d\uff1a[{}]", (Object)unitCode, (Object)investedUnitCode);
            this.compreEquityRatioHandle(baseUnitCode, calc, master, records, investedUnitCode, calcArgments.getOrgType(), calcArgments.getPeriodStr(), "INVESTEDCOMPREEQUITYRATIO");
        }
    }

    private List<Map<String, Object>> listInvestRecords(GcCalcArgmentsDTO calcArgments) {
        YearPeriodDO periodUtil = YearPeriodUtil.transform(null, (String)calcArgments.getPeriodStr());
        List<Map<String, Object>> records = this.queryData(periodUtil, calcArgments.getOrgId());
        YearPeriodObject yp = new YearPeriodObject(null, calcArgments.getPeriodStr());
        Calendar currPeriod = Calendar.getInstance();
        currPeriod.setTime(yp.formatYP().getEndDate());
        return records.stream().filter(record -> {
            Calendar disposeDate = Calendar.getInstance();
            disposeDate.setTime(record.get("DISPOSEDATE") == null ? currPeriod.getTime() : (Date)record.get("DISPOSEDATE"));
            boolean allowCalcCompreEquityRatio = record.get("DISPOSEFLAG") == null || String.valueOf(record.get("DISPOSEFLAG")).equals("0") || currPeriod.get(1) > disposeDate.get(1) || currPeriod.get(1) == disposeDate.get(1) && currPeriod.get(2) > disposeDate.get(2);
            return allowCalcCompreEquityRatio;
        }).collect(Collectors.toList());
    }

    public InvestCalculator getInvestCalculatorFromDb(String orgType, String periodStr, String mergeOrgCode, List<Map<String, Object>> investRecords) {
        String baseUnitCode = this.getBaseUnit(orgType, periodStr, mergeOrgCode);
        InvestCalculator calc = new InvestCalculator();
        if (StringUtils.isEmpty((String)baseUnitCode)) {
            return calc;
        }
        calc.setBaseUnitId(baseUnitCode);
        YearPeriodDO periodUtil = YearPeriodUtil.transform(null, (String)periodStr);
        List<Map<String, Object>> records = this.queryData(periodUtil, mergeOrgCode);
        investRecords.addAll(records);
        for (Map<String, Object> record : records) {
            String unitCode = (String)record.get("UNITCODE");
            String oppUnitCode = (String)record.get("INVESTEDUNIT");
            Double endEquityRatio = ConverterUtils.getAsDouble((Object)record.get("ENDEQUITYRATIO"));
            if (null == endEquityRatio || endEquityRatio == 0.0) continue;
            calc.addInvest(unitCode, oppUnitCode, endEquityRatio / 100.0);
        }
        return calc;
    }

    private void compreEquityRatioHandle(String baseUnitCode, InvestCalculator calc, DefaultTableEntity master, List<Map<String, Object>> records, String unitCode, String orgType, String periodStr, String fieldCode) {
        if (baseUnitCode.equals(unitCode)) {
            this.saveData(master, 100.0, fieldCode);
        } else {
            double compreEquityRatioValue = this.getCompreEquityRatioValue(baseUnitCode, calc, records, orgType, periodStr, unitCode);
            this.saveData(master, compreEquityRatioValue, fieldCode);
        }
    }

    public double getCompreEquityRatioValue(String baseUnitCode, InvestCalculator calc, List<Map<String, Object>> records, String orgType, String periodStr, String unitCode) {
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO currOrg = orgTool.getOrgByCode(unitCode);
        String[] orgParents = currOrg.getParents();
        if (null == baseUnitCode) {
            return 100.0;
        }
        for (int i = orgParents.length - 1; i >= 0; --i) {
            String currOrgCode = i == orgParents.length - 1 ? orgParents[i] : this.getBaseUnit(orgType, periodStr, orgParents[i]);
            if (baseUnitCode.equals(currOrgCode)) {
                return 100.0;
            }
            double compreEquityRatioValue = this.getCompreEquityRatioValue(calc, records, currOrgCode);
            if (BigDecimal.ZERO.compareTo(new BigDecimal(compreEquityRatioValue)) == 0) continue;
            return compreEquityRatioValue;
        }
        return 100.0;
    }

    private double getCompreEquityRatioValue(InvestCalculator calc, List<Map<String, Object>> records, String unitCode) {
        if (StringUtils.isEmpty((String)unitCode)) {
            return 0.0;
        }
        BigDecimal rate = BigDecimal.valueOf(calc.getRate(unitCode));
        String cycleUnitCode = this.getCycleUnitCode(records, unitCode);
        if (!StringUtils.isEmpty((String)cycleUnitCode)) {
            BigDecimal cycleInvestRate = this.getCycleInvestRate(calc.getBaseUnitId(), unitCode, records, cycleUnitCode);
            rate = rate.add(cycleInvestRate);
        }
        this.logger.info("[{}]\u7efc\u5408\u6301\u80a1\u6bd4\u4f8b\u8ba1\u7b97\u5b8c\u6210\uff1a[{}] ", (Object)unitCode, (Object)rate);
        return rate.setScale(16, 4).doubleValue() * 100.0;
    }

    private BigDecimal getCycleInvestRate(String baseUnitCode, String unitCode, List<Map<String, Object>> records, String cycleUnitCode) {
        Map<String, Object> base2CurrentUnitRecord = null;
        Map<String, Object> currentUnit2CycleUnitRecord = null;
        Map<String, Object> cycleUnit2CurrentUnitRecord = null;
        for (Map<String, Object> record : records) {
            if (baseUnitCode.equals(record.get("UNITCODE")) && unitCode.equals(record.get("INVESTEDUNIT"))) {
                base2CurrentUnitRecord = record;
            }
            if (unitCode.equals(record.get("UNITCODE")) && cycleUnitCode.equals(record.get("INVESTEDUNIT"))) {
                currentUnit2CycleUnitRecord = record;
            }
            if (!cycleUnitCode.equals(record.get("UNITCODE")) || !unitCode.equals(record.get("INVESTEDUNIT"))) continue;
            cycleUnit2CurrentUnitRecord = record;
        }
        if (base2CurrentUnitRecord == null) {
            return BigDecimal.ZERO;
        }
        if (currentUnit2CycleUnitRecord == null) {
            return BigDecimal.ZERO;
        }
        if (cycleUnit2CurrentUnitRecord == null) {
            return BigDecimal.ZERO;
        }
        Double baseEquityRatio = ConverterUtils.getAsDouble(base2CurrentUnitRecord.get("ENDEQUITYRATIO")) / 100.0;
        Double currentEquityRatio = ConverterUtils.getAsDouble(currentUnit2CycleUnitRecord.get("ENDEQUITYRATIO")) / 100.0;
        Double cycleEquityRatio = ConverterUtils.getAsDouble(cycleUnit2CurrentUnitRecord.get("ENDEQUITYRATIO")) / 100.0;
        BigDecimal cycleRatio = BigDecimal.valueOf(baseEquityRatio).multiply(BigDecimal.valueOf(currentEquityRatio)).multiply(BigDecimal.valueOf(cycleEquityRatio));
        this.logger.info("[{}]\u5355\u4f4d\u5b58\u5728\u5faa\u73af\u6295\u8d44\u8def\u5f84\uff0c\u5faa\u73af\u6295\u8d44\u8def\u5f84\u4fe1\u606f\uff1a[{}-->\u3010\u6301\u80a1\u6bd4\u4f8b{}\u3011{}-->\u3010\u6301\u80a1\u6bd4\u4f8b{}\u3011{}-->\u3010\u6301\u80a1\u6bd4\u4f8b{}\u3011{}],\u6b64\u8def\u5f84\u6301\u80a1\u6bd4\u4f8b\uff1a[{}]", unitCode, baseUnitCode, baseEquityRatio, unitCode, currentEquityRatio, cycleUnitCode, cycleEquityRatio, unitCode, cycleRatio);
        return cycleRatio;
    }

    private String getCycleUnitCode(List<Map<String, Object>> records, String unitCode) {
        if (CollectionUtils.isEmpty(records)) {
            return null;
        }
        HashSet<String> unitCodeInvestSet = new HashSet<String>();
        HashSet<String> unitCodeInvestedSet = new HashSet<String>();
        for (Map<String, Object> record : records) {
            Object investUnitObj = record.get("UNITCODE");
            if (investUnitObj == null) continue;
            String investUnit = investUnitObj.toString();
            Object investedUnitObj = record.get("INVESTEDUNIT");
            if (investedUnitObj == null) continue;
            String investedUnit = investedUnitObj.toString();
            if (unitCode.equals(investedUnit)) {
                if (unitCodeInvestSet.contains(investUnit)) {
                    return investUnit;
                }
                unitCodeInvestedSet.add(investUnit);
                continue;
            }
            if (!unitCode.equals(investUnit)) continue;
            if (unitCodeInvestedSet.contains(investedUnit)) {
                return investedUnit;
            }
            unitCodeInvestSet.add(investedUnit);
        }
        return null;
    }

    private void saveData(DefaultTableEntity master, Double compreEquityRatioValue, String fieldCode) {
        Double oldCompreEquityRatioValue = ConverterUtils.getAsDouble((Object)master.getFieldValue(fieldCode));
        boolean needUpdate = false;
        if (null == oldCompreEquityRatioValue) {
            oldCompreEquityRatioValue = 0.0;
            needUpdate = true;
        }
        if (null == compreEquityRatioValue) {
            compreEquityRatioValue = 0.0;
        }
        if (needUpdate || !NumberUtils.isZreo((Double)(compreEquityRatioValue - oldCompreEquityRatioValue))) {
            this.investBillDao.updateNumberFieldValue(master.getId(), fieldCode, compreEquityRatioValue);
            master.addFieldValue(fieldCode, (Object)compreEquityRatioValue);
        }
    }

    private InvestCalculator getInvestCalculatorFromDb(List<Map<String, Object>> records) {
        InvestCalculator calc = new InvestCalculator();
        for (Map<String, Object> record : records) {
            String unitCode = (String)record.get("UNITCODE");
            String oppUnitCode = (String)record.get("INVESTEDUNIT");
            Double endEquityRatio = ConverterUtils.getAsDouble((Object)record.get("ENDEQUITYRATIO"));
            if (null == endEquityRatio || endEquityRatio == 0.0) continue;
            calc.addInvest(unitCode, oppUnitCode, endEquityRatio / 100.0);
        }
        return calc;
    }

    private List<Map<String, Object>> queryData(YearPeriodDO periodUtil, String mergeOrgCode) {
        HashMap<String, Object> params = new HashMap<String, Object>(16);
        params.put("pageSize", -1);
        params.put("pageNum", -1);
        params.put("mergeUnit", mergeOrgCode);
        params.put("acctYear", periodUtil.getYear());
        params.put("acctPeriod", null == periodUtil.getEndDate() ? periodUtil.getPeriod() : periodUtil.getEndDate().getMonth() + 1);
        List<Map<String, Object>> records = this.investBillDao.listInvestBillsByPaging(params);
        return records;
    }

    private String getBaseUnit(String orgType, String periodStr, String mergeOrgCode) {
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        String baseUnitCode = orgTool.getDeepestBaseUnitId(mergeOrgCode);
        return baseUnitCode;
    }
}

