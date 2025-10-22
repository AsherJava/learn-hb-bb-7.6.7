/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.NumberUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.conversion.conversionrate.service.ConversionRateService
 *  com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemTaskDao
 *  com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO
 *  com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO
 *  com.jiuqi.gcreport.rate.impl.domain.ConvertRateSchemeDO
 *  com.jiuqi.gcreport.rate.impl.mapper.RateSchemeMapper
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.xlib.utils.StringUtil
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.relationtomerge.impl;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.NumberUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.conversion.conversionrate.service.ConversionRateService;
import com.jiuqi.gcreport.conversion.conversionsystem.dao.ConversionSystemTaskDao;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO;
import com.jiuqi.gcreport.financialcheckImpl.util.BaseDataUtils;
import com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO;
import com.jiuqi.gcreport.rate.impl.domain.ConvertRateSchemeDO;
import com.jiuqi.gcreport.rate.impl.mapper.RateSchemeMapper;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.xlib.utils.StringUtil;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class MemoryConversionExecutor {
    @Autowired
    private ConversionRateService conversionRateService;
    @Autowired
    private ConversionSystemTaskDao conversionSystemTaskDao;
    @Autowired
    private RateSchemeMapper mapper;

    public List<GcFcRuleUnOffsetDataDTO> sum(List<GcFcRuleUnOffsetDataDTO> records) {
        Map<String, List<String>> subjectCode2assTypeListMap = BaseDataUtils.getSubjectCode2assTypeListMap();
        HashMap<ArrayKey, GcFcRuleUnOffsetDataDTO> combinedKey2RecordMap = new HashMap<ArrayKey, GcFcRuleUnOffsetDataDTO>(16);
        for (GcFcRuleUnOffsetDataDTO record : records) {
            List assTypeList = (List)MapUtils.getVal(subjectCode2assTypeListMap, (Object)record.getSubjectCode(), (Object)Collections.EMPTY_LIST);
            ArrayKey key = this.getCombinedKey(record, assTypeList);
            this.sumNumberIntoMap(combinedKey2RecordMap, key, record);
        }
        return new ArrayList<GcFcRuleUnOffsetDataDTO>(combinedKey2RecordMap.values());
    }

    public void memoryConversion(List<GcFcRuleUnOffsetDataDTO> records, GcCalcArgmentsDTO calcArgs) {
        ConversionSystemTaskEO conversionSystemTaskEO = this.conversionSystemTaskDao.queryByTaskAndScheme(calcArgs.getTaskId(), calcArgs.getSchemeId());
        Map<String, String> subjectCode2RateTypeMap = this.getSubjectCode2RateTypeMap();
        Map<String, String> accountSubjectCode2ParentCodeMap = BaseDataUtils.getCode2ParentCodeMap("MD_ACCTSUBJECT");
        DefaultPeriodAdapter defaultPeriodAdapter = new DefaultPeriodAdapter();
        for (GcFcRuleUnOffsetDataDTO unOffsetData : records) {
            unOffsetData.addFieldValue("beforeConvertAmt", (Object)unOffsetData.getAmt());
            if (StringUtil.equals((String)unOffsetData.getOffsetCurrency(), (String)calcArgs.getCurrency())) continue;
            if (Objects.isNull(conversionSystemTaskEO)) {
                throw new BusinessRuntimeException(MessageFormat.format("\u6839\u636e \u4efb\u52a1-{0}, \u62a5\u8868\u65b9\u6848-{1}\u83b7\u53d6\u4e0d\u5230\u6298\u7b97\u4f53\u7cfb", calcArgs.getTaskId(), calcArgs.getSchemeId()));
            }
            String subjectCode = unOffsetData.getSubjectCode();
            String rateType = this.getSubjectRateType(subjectCode, subjectCode2RateTypeMap, accountSubjectCode2ParentCodeMap);
            if (StringUtil.isEmpty((String)rateType)) {
                throw new BusinessRuntimeException("\u7edf\u4e00\u79d1\u76ee\uff1a" + subjectCode + "\u672a\u914d\u7f6e\u6c47\u7387\u7c7b\u578b");
            }
            Map rateInfos = this.conversionRateService.getRateInfosByRateTypeCode(conversionSystemTaskEO.getRateSchemeCode(), calcArgs.getSchemeId(), unOffsetData.getOffsetCurrency(), calcArgs.getCurrency(), calcArgs.getPeriodStr(), rateType);
            BigDecimal rate = (BigDecimal)rateInfos.get(rateType);
            if (rate == null) {
                throw new BusinessRuntimeException(MessageFormat.format("\u65f6\u671f\uff1a{0} {1}\u8f6c{2}\u65e0\u5bf9\u5e94\u6c47\u7387\uff0c\u5e01\u79cd\u6298\u7b97\u5931\u8d25", defaultPeriodAdapter.getPeriodTitle(calcArgs.getPeriodStr()), unOffsetData.getOffsetCurrency(), calcArgs.getCurrency()));
            }
            unOffsetData.setAmt(Double.valueOf(BigDecimal.valueOf(unOffsetData.getAmt()).multiply(rate).doubleValue()));
            unOffsetData.setOffsetCurrency(calcArgs.getCurrency());
            unOffsetData.setConversionRate(Double.valueOf(rate.doubleValue()));
            if (!unOffsetData.hasField("AGINGRANGE")) continue;
            Map ageRangeData = (Map)unOffsetData.getFieldValue("AGINGRANGE");
            ageRangeData.forEach((key, value) -> ageRangeData.compute(key, (k, v) -> BigDecimal.valueOf(v).multiply(rate).doubleValue()));
        }
    }

    private Map<String, String> getSubjectCode2RateTypeMap() {
        List schemes = this.mapper.select((Object)new ConvertRateSchemeDO());
        HashMap<String, String> subjectCode2RateTypeMap = new HashMap<String, String>();
        if (null == schemes) {
            return subjectCode2RateTypeMap;
        }
        for (ConvertRateSchemeDO scheme : schemes) {
            subjectCode2RateTypeMap.put(scheme.getSubjectCode(), scheme.getCfRateType());
        }
        return subjectCode2RateTypeMap;
    }

    private void sumNumberIntoMap(Map<ArrayKey, GcFcRuleUnOffsetDataDTO> combinedKey2RecordMap, ArrayKey key, GcFcRuleUnOffsetDataDTO newRecord) {
        GcFcRuleUnOffsetDataDTO oldRecord = combinedKey2RecordMap.get(key);
        if (null == oldRecord) {
            newRecord.setId(DigestUtils.md5DigestAsHex(key.toString().getBytes(Charset.defaultCharset())));
            combinedKey2RecordMap.put(key, newRecord);
        } else {
            this.sumVal2OldRecord(oldRecord, newRecord, "DEBITORIG");
            this.sumVal2OldRecord(oldRecord, newRecord, "CREDITORIG");
            this.sumVal2OldRecord(oldRecord, newRecord, "DEBIT");
            this.sumVal2OldRecord(oldRecord, newRecord, "CREDIT");
            if (oldRecord.getDc().equals(newRecord.getDc())) {
                oldRecord.setAmt(Double.valueOf(NumberUtils.sum((Double)oldRecord.getAmt(), (Double)newRecord.getAmt())));
            } else {
                oldRecord.setAmt(Double.valueOf(NumberUtils.sub((Double)oldRecord.getAmt(), (Double)newRecord.getAmt())));
            }
            List oldVchrOffsetRelEOS = (List)oldRecord.getFieldValue("VCHROFFSETRELS");
            List newVchrOffsetRelEOS = (List)newRecord.getFieldValue("VCHROFFSETRELS");
            oldVchrOffsetRelEOS.addAll(newVchrOffsetRelEOS);
            if (newRecord.hasField("AGINGRANGE")) {
                Map<String, Double> newRecordAgeRangeData = (Map<String, Double>)newRecord.getFieldValue("AGINGRANGE");
                if (oldRecord.hasField("AGINGRANGE")) {
                    Map oldRecordAgeRangeData = (Map)newRecord.getFieldValue("AGINGRANGE");
                    newRecordAgeRangeData = Stream.concat(newRecordAgeRangeData.entrySet().stream(), oldRecordAgeRangeData.entrySet().stream()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (d1, d2) -> {
                        if (oldRecord.getDc().equals(newRecord.getDc())) {
                            return NumberUtils.sum((Double)d1, (Double)d2);
                        }
                        return NumberUtils.sub((Double)d1, (Double)d2);
                    }));
                }
                oldRecord.addFieldValue("AGINGRANGE", (Object)newRecordAgeRangeData);
            }
        }
    }

    private void sumVal2OldRecord(GcFcRuleUnOffsetDataDTO oldRecord, GcFcRuleUnOffsetDataDTO newRecord, String fieldCode) {
        Double oldVal = ConverterUtils.getAsDouble((Object)oldRecord.getFieldValue(fieldCode));
        Double newVal = ConverterUtils.getAsDouble((Object)newRecord.getFieldValue(fieldCode));
        newRecord.addFieldValue(fieldCode, (Object)NumberUtils.sum((Double)oldVal, (Double)newVal));
    }

    private ArrayKey getCombinedKey(GcFcRuleUnOffsetDataDTO record, List<String> assTypeList) {
        Object originalCurr = record.getFieldValue("ORIGINALCURR");
        ArrayList<Object> keys = new ArrayList<Object>(16);
        Collections.addAll(keys, record.getUnitId(), record.getOppUnitId(), record.getSubjectCode(), originalCurr, record.getCurrency());
        for (String assTypeFieldCode : assTypeList) {
            Object dimValue = record.getFieldValue(assTypeFieldCode);
            keys.add(dimValue);
        }
        return ArrayKey.of(keys);
    }

    private String getSubjectRateType(String subjectCode, Map<String, String> subjectCode2RateTypeMap, Map<String, String> accountSubjectCode2ParentCodeMap) {
        String rateType = subjectCode2RateTypeMap.get(subjectCode);
        if (StringUtil.isEmpty((String)rateType)) {
            String parentCode = accountSubjectCode2ParentCodeMap.get(subjectCode);
            if (StringUtil.isEmpty((String)parentCode) || StringUtil.equals((String)"-", (String)parentCode)) {
                return null;
            }
            rateType = this.getSubjectRateType(parentCode, subjectCode2RateTypeMap, accountSubjectCode2ParentCodeMap);
            subjectCode2RateTypeMap.put(subjectCode, rateType);
            return rateType;
        }
        int lastIndex = rateType.lastIndexOf(95);
        if (lastIndex != -1) {
            rateType = rateType.substring(0, lastIndex);
        }
        return rateType;
    }
}

