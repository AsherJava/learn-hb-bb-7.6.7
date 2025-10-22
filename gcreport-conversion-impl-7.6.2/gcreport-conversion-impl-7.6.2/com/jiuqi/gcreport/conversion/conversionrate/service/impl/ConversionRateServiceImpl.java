/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.common.rate.client.vo.CommonRateInfoVO
 *  com.jiuqi.gcreport.conversion.conversionrate.consts.PeriodDataTypeEnum
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.rate.impl.service.CommonRateService
 *  com.jiuqi.gcreport.rate.impl.utils.CommonRateUtils
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.conversion.conversionrate.service.impl;

import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.common.rate.client.vo.CommonRateInfoVO;
import com.jiuqi.gcreport.conversion.conversionrate.consts.PeriodDataTypeEnum;
import com.jiuqi.gcreport.conversion.conversionrate.service.ConversionRateCurrencyCacheService;
import com.jiuqi.gcreport.conversion.conversionrate.service.ConversionRateGroupService;
import com.jiuqi.gcreport.conversion.conversionrate.service.ConversionRateService;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.rate.impl.service.CommonRateService;
import com.jiuqi.gcreport.rate.impl.utils.CommonRateUtils;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversionRateServiceImpl
implements ConversionRateService {
    @Autowired
    private CommonRateService rateService;
    @Autowired
    private ConversionRateGroupService groupService;
    @Autowired
    private ConversionRateCurrencyCacheService currencyCacheService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DataModelService dataModelService;
    private ThreadLocal<Integer> rateValueFieldFractionDigitsThreadLocal = new ThreadLocal();

    @Override
    public Map<String, BigDecimal> getRateInfos(String rateSchemeCode, String schemeId, String sourceCurrencyCode, String targetCurrencyCode, String periodStr) {
        return this.getRateInfosByRateTypeCode(rateSchemeCode, schemeId, sourceCurrencyCode, targetCurrencyCode, periodStr, null);
    }

    @Override
    public Map<String, BigDecimal> getRateInfosByRateTypeCode(String rateSchemeCode, String schemeId, String sourceCurrencyCode, String targetCurrencyCode, String periodStr, String rateTypeCode) {
        FormSchemeDefine schemeDefine = this.runTimeViewController.getFormScheme(schemeId);
        int periodType = schemeDefine.getPeriodType().type();
        PeriodDataTypeEnum periodTypeEnum = PeriodDataTypeEnum.getEnumByDataId((Integer)periodType);
        Objects.requireNonNull(periodTypeEnum, GcI18nUtil.getMessage((String)"gc.coversion.rate.periodType.notfound.error", (Object[])new Object[]{schemeDefine.getTitle(), periodType}));
        String periodId = String.valueOf(periodType);
        HashMap<String, BigDecimal> resultMap = new HashMap<String, BigDecimal>();
        boolean reverseFlag = false;
        CommonRateInfoVO vo = this.rateService.queryRateInfo(rateSchemeCode, periodStr, sourceCurrencyCode, targetCurrencyCode);
        if (vo == null) {
            vo = this.rateService.queryRateInfo(rateSchemeCode, periodStr, targetCurrencyCode, sourceCurrencyCode);
            reverseFlag = true;
        }
        if (vo == null || vo.getCode().equals("DEFAULT") && periodTypeEnum != PeriodDataTypeEnum.MONTH) {
            return resultMap;
        }
        if (!reverseFlag) {
            resultMap.putAll(vo.getRateInfo());
            return resultMap;
        }
        int rateValueFieldFractionDigits = CommonRateUtils.getRateValueFieldFractionDigits((String)rateTypeCode);
        for (String key : vo.getRateInfo().keySet()) {
            BigDecimal rateValue2;
            BigDecimal rateValue;
            BigDecimal bigDecimal = rateValue = vo.getRateInfo().get(key) == null ? new BigDecimal(0) : (BigDecimal)vo.getRateInfo().get(key);
            if (rateValue.compareTo(BigDecimal.ZERO) == 0) {
                rateValue2 = new BigDecimal(0);
            } else {
                rateValue2 = NumberUtils.div((BigDecimal)BigDecimal.ONE, (BigDecimal)rateValue);
                rateValue2.setScale(rateValueFieldFractionDigits, RoundingMode.HALF_UP);
            }
            resultMap.put(key, rateValue2);
        }
        return resultMap;
    }

    @Override
    public BigDecimal getSumAvgRateValueByRateTypeCode(String rateSchemeCode, String schemeId, String sourceCurrencyCode, String targetCurrencyCode, String periodStr, String rateTypeCode) {
        FormSchemeDefine schemeDefine = this.runTimeViewController.getFormScheme(schemeId);
        int periodType = schemeDefine.getPeriodType().type();
        PeriodDataTypeEnum periodTypeEnum = PeriodDataTypeEnum.getEnumByDataId((Integer)periodType);
        Objects.requireNonNull(periodTypeEnum, GcI18nUtil.getMessage((String)"gc.coversion.rate.periodType.notfound.error", (Object[])new Object[]{schemeDefine.getTitle(), periodType}));
        String periodId = String.valueOf(periodType);
        HashMap resultMap = new HashMap();
        List rateInfoList = this.rateService.queryRateByDataTime(rateSchemeCode, periodStr, sourceCurrencyCode, targetCurrencyCode);
        rateInfoList.forEach(item -> {
            String keyStr = item.getDataTime();
            BigDecimal value = (BigDecimal)item.getRateInfo().get(rateTypeCode);
            resultMap.put(keyStr, value);
        });
        List reverseRateInfoList = this.rateService.queryRateByDataTime(rateSchemeCode, periodStr, targetCurrencyCode, sourceCurrencyCode);
        int rateValueFieldFractionDigits = CommonRateUtils.getRateValueFieldFractionDigits((String)rateTypeCode);
        reverseRateInfoList.forEach(reverseitem -> {
            String keyStr = reverseitem.getDataTime();
            BigDecimal rateValue = (BigDecimal)resultMap.get(keyStr);
            if (rateValue == null) {
                BigDecimal rateValue2;
                BigDecimal reversrItem = (BigDecimal)reverseitem.getRateInfo().get(rateTypeCode);
                if (reversrItem.compareTo(new BigDecimal(0)) == 0) {
                    rateValue2 = BigDecimal.ZERO;
                } else {
                    rateValue2 = NumberUtils.div((BigDecimal)BigDecimal.ONE, (BigDecimal)reversrItem);
                    rateValue2.setScale(rateValueFieldFractionDigits, RoundingMode.HALF_UP);
                }
                resultMap.put(keyStr, rateValue2);
            }
        });
        if (resultMap.isEmpty()) {
            return BigDecimal.ONE;
        }
        BigDecimal sum = resultMap.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        int scale = CommonRateUtils.getRateValueFieldFractionDigits();
        return sum.divide(new BigDecimal(resultMap.size()), scale, RoundingMode.HALF_UP);
    }

    private String getPeriodTitle(String periodId) {
        return PeriodDataTypeEnum.getTitle((String)periodId);
    }

    private String getPeriodId(String periodTitle) {
        if (periodTitle == null) {
            return null;
        }
        periodTitle = periodTitle.trim();
        return PeriodDataTypeEnum.getId((String)periodTitle);
    }
}

