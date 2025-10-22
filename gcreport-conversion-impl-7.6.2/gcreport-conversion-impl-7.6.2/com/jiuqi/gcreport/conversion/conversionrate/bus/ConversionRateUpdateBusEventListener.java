/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.bus.BusEventListener
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.gcreport.conversion.conversionrate.consts.PeriodDataTypeEnum
 *  com.jiuqi.gcreport.conversion.conversionrate.vo.ConversionRateItemVO
 *  com.jiuqi.gcreport.conversion.conversionrate.vo.RateTypeItemVO
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.rate.impl.service.CommonRateSchemeService
 *  com.jiuqi.gcreport.rate.impl.service.CommonRateService
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodConsts
 */
package com.jiuqi.gcreport.conversion.conversionrate.bus;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.bus.BusEventListener;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.gcreport.conversion.conversionrate.bus.ConversionRateSyncBusParam;
import com.jiuqi.gcreport.conversion.conversionrate.consts.PeriodDataTypeEnum;
import com.jiuqi.gcreport.conversion.conversionrate.service.ConversionRateService;
import com.jiuqi.gcreport.conversion.conversionrate.vo.ConversionRateItemVO;
import com.jiuqi.gcreport.conversion.conversionrate.vo.RateTypeItemVO;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.rate.impl.service.CommonRateSchemeService;
import com.jiuqi.gcreport.rate.impl.service.CommonRateService;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodConsts;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConversionRateUpdateBusEventListener
implements BusEventListener {
    @Autowired
    private ConversionRateService conversionRateService;
    @Autowired
    private CommonRateService rateService;
    @Autowired
    private CommonRateSchemeService rateSchemeService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConversionRateUpdateBusEventListener.class);

    public Object run(Object ... busEventParam) {
        if (busEventParam == null || busEventParam.length != 5 && busEventParam.length != 6) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.coversion.bus.rate.check.param.error"));
        }
        ConversionRateSyncBusParam busParam = new ConversionRateSyncBusParam();
        Integer year = ConverterUtils.getAsInteger((Object)busEventParam[0]);
        Objects.requireNonNull(year, GcI18nUtil.getMessage((String)"gc.coversion.bus.rate.check.year.error"));
        busParam.setYear(year);
        Integer period = ConverterUtils.getAsInteger((Object)busEventParam[1]);
        Objects.requireNonNull(period, GcI18nUtil.getMessage((String)"gc.coversion.bus.rate.check.period.error"));
        busParam.setPeriod(period);
        String srcCurrency = ConverterUtils.getAsString((Object)busEventParam[2]);
        Objects.requireNonNull(period, GcI18nUtil.getMessage((String)"gc.coversion.bus.rate.check.srcCurrency.error"));
        busParam.setSrcCurrency(srcCurrency);
        String targetCurrency = ConverterUtils.getAsString((Object)busEventParam[3]);
        Objects.requireNonNull(period, GcI18nUtil.getMessage((String)"gc.coversion.bus.rate.check.targetCurrency.error"));
        busParam.setTargetCurrency(targetCurrency);
        Object item = busEventParam[4];
        if (item == null) {
            return false;
        }
        busParam.setItem((Map)item);
        List rateSchemeList = this.rateSchemeService.listAllRateScheme();
        if (CollectionUtils.isEmpty((Collection)rateSchemeList)) {
            return false;
        }
        String periodId = PeriodDataTypeEnum.MONTH.getDataValue();
        if (busEventParam.length == 6) {
            periodId = ConverterUtils.getAsString((Object)busEventParam[5]);
        }
        String finalPeriodId = periodId;
        rateSchemeList.stream().forEach(vo -> {
            try {
                busParam.setRateSchemeCode(vo.getId());
                this.run(busParam, finalPeriodId);
            }
            catch (Exception e) {
                LOGGER.error("\u540c\u6b65\u4e00\u672c\u8d26\u6c47\u7387\u5931\u8d25, \u53c2\u6570:".concat("\u6c47\u7387\u65b9\u6848-").concat(vo.getName()).concat("\u5e74\u5ea6-").concat(busParam.getYear().toString()).concat("\u671f\u95f4-").concat(busParam.getPeriod().toString()).concat("\u6e90\u5e01\u79cd-").concat(busParam.getSrcCurrency()).concat("\u76ee\u6807\u5e01\u79cd-").concat(busParam.getTargetCurrency()), e);
            }
        });
        return true;
    }

    public Object run(ConversionRateSyncBusParam busEventParam, String periodId) {
        String currRateSchemeCode = busEventParam.getRateSchemeCode();
        String srcCurrency = busEventParam.getSrcCurrency();
        String targetCurrency = busEventParam.getTargetCurrency();
        Integer year = busEventParam.getYear();
        Integer period = busEventParam.getPeriod();
        Map<String, BigDecimal> item = busEventParam.getItem();
        ArrayList rateTypeItems = new ArrayList();
        item.forEach((rateTypeCode, rateValue) -> {
            RateTypeItemVO rateTypeItemVO = new RateTypeItemVO();
            rateTypeItemVO.setCode(rateTypeCode);
            rateTypeItemVO.setValue(rateValue.toString());
            rateTypeItems.add(rateTypeItemVO);
        });
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        ConversionRateItemVO vo = new ConversionRateItemVO();
        vo.setPeriodId(periodId);
        vo.setSourceCurrencyCode(srcCurrency);
        vo.setTargetCurrencyCode(targetCurrency);
        PeriodDataTypeEnum enumByDataValue = PeriodDataTypeEnum.getEnumByDataValue((String)periodId);
        char periodType = (char)PeriodConsts.typeToCode((int)enumByDataValue.getDataId());
        String periodTitle = new DefaultPeriodAdapter().getPeriodTitle(year.toString() + periodType + period.toString());
        vo.setPeriodStr(periodTitle);
        vo.setRateTypeItems(rateTypeItems);
        return null;
    }

    public String getEventType() {
        return "ConversionRateUpdateBusEventListener";
    }
}

