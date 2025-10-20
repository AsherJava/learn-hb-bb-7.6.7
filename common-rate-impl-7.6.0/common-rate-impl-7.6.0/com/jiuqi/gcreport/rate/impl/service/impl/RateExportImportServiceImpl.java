/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.ReflectionUtils
 *  com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet
 *  com.jiuqi.common.rate.client.vo.CommonRateInfoVO
 *  com.jiuqi.common.rate.client.vo.CommonRateSchemeVO
 *  com.jiuqi.common.rate.client.vo.RateQueryParam
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 */
package com.jiuqi.gcreport.rate.impl.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.ReflectionUtils;
import com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet;
import com.jiuqi.common.rate.client.vo.CommonRateInfoVO;
import com.jiuqi.common.rate.client.vo.CommonRateSchemeVO;
import com.jiuqi.common.rate.client.vo.RateQueryParam;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.rate.impl.service.CommonRateSchemeService;
import com.jiuqi.gcreport.rate.impl.service.CommonRateService;
import com.jiuqi.gcreport.rate.impl.service.RateExportImportService;
import com.jiuqi.gcreport.rate.impl.utils.CommonRateUtils;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class RateExportImportServiceImpl
implements RateExportImportService {
    @Autowired
    CommonRateService commonRateService;
    @Autowired
    CommonRateSchemeService commonRateSchemeService;

    @Override
    public Map<String, String> getRateExcelColumnTitleMap() {
        LinkedHashMap<String, String> titleMap = new LinkedHashMap<String, String>();
        titleMap.put("periodType", GcI18nUtil.getMessage((String)"gc.common.rate.excel.head.periodId.title"));
        titleMap.put("dataTime", GcI18nUtil.getMessage((String)"gc.common.rate.excel.head.periodValue.title"));
        titleMap.put("sourceCurrencyCode", GcI18nUtil.getMessage((String)"gc.common.rate.excel.head.sourceCurrencyCode.title"));
        titleMap.put("sourceCurrencyTitle", GcI18nUtil.getMessage((String)"gc.common.rate.excel.head.sourceCurrencyTitle.title"));
        titleMap.put("targetCurrencyCode", GcI18nUtil.getMessage((String)"gc.common.rate.excel.head.targetCurrencyCode.title"));
        titleMap.put("targetCurrencyTitle", GcI18nUtil.getMessage((String)"gc.common.rate.excel.head.targetCurrencyTitle.title"));
        List<BaseDataDO> rateType = CommonRateUtils.getAllNotVirtualRateTypes();
        for (BaseDataDO rate : rateType) {
            titleMap.put(rate.getCode(), rate.getName());
        }
        return titleMap;
    }

    @Override
    public List<Map<String, Object>> rateExport(String rateSchemeCode, String periodType, String periodStrStart, String periodStrEnd, String sourceCurrencyCode, String targetCurrencyCode) {
        RateQueryParam rateQueryParam = new RateQueryParam();
        rateQueryParam.setRateSchemeCode(rateSchemeCode);
        rateQueryParam.setPeriodStrStart(periodStrStart);
        rateQueryParam.setPeriodStrEnd(periodStrEnd);
        rateQueryParam.setSourceCurrencyCode(sourceCurrencyCode);
        rateQueryParam.setTargetCurrencyCode(targetCurrencyCode);
        rateQueryParam.setPageNum(1);
        rateQueryParam.setPageSize(1000000);
        List infoEOS = this.commonRateService.queryRateList(rateQueryParam).getList();
        ArrayList<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        if (!CollectionUtils.isEmpty((Collection)infoEOS)) {
            ArrayList<String> rowCodeList = new ArrayList<String>();
            for (CommonRateInfoVO vo : infoEOS) {
                if (rowCodeList.contains(vo.getCode())) continue;
                rowCodeList.add(vo.getCode());
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("periodType", CommonRateUtils.getPeriodTypeTitle(periodType));
                String periodTitle = new DefaultPeriodAdapter().getPeriodTitle(vo.getDataTime());
                map.put("dataTime", periodTitle);
                map.put("sourceCurrencyCode", vo.getSourceCurrencyCode());
                map.put("sourceCurrencyTitle", vo.getSourceCurrencyTitle());
                map.put("targetCurrencyCode", vo.getTargetCurrencyCode());
                map.put("targetCurrencyTitle", vo.getTargetCurrencyTitle());
                map.putAll(vo.getRateInfo());
                resultList.add(map);
            }
        }
        return resultList;
    }

    @Override
    public void rateUpload(String rateSchemeCode, List<ImportExcelSheet> excelSheets) {
        if (!StringUtils.isEmpty((String)rateSchemeCode)) {
            String currRateTitle = this.commonRateSchemeService.queryRateScheme(rateSchemeCode).getName();
            int matchNumber = (excelSheets = excelSheets.stream().filter(v -> v.getSheetName().equals(currRateTitle)).collect(Collectors.toList())).size();
            if (matchNumber == 0) {
                throw new BusinessRuntimeException("\u5bfc\u5165\u6587\u4ef6\u4e2d\u627e\u4e0d\u5230\u540d\u79f0\u4e3a[" + currRateTitle + "]\u7684\u6c47\u7387\u65b9\u6848\uff0c\u65e0\u6cd5\u5bfc\u5165\u3002");
            }
            if (matchNumber > 1) {
                throw new BusinessRuntimeException("\u5bfc\u5165\u6587\u4ef6\u4e2d\u5b58\u5728\u591a\u4e2a\u540d\u79f0\u4e3a[" + currRateTitle + "]\u7684\u6c47\u7387\u65b9\u6848\uff0c\u65e0\u6cd5\u786e\u5b9a\u5bfc\u5165\u54ea\u4e2a\uff0c\u8bf7\u4fee\u6539\u5bfc\u5165\u6587\u4ef6");
            }
        }
        boolean result = false;
        for (ImportExcelSheet importExcelSheet : excelSheets) {
            String title = importExcelSheet.getSheetName();
            CommonRateSchemeVO rateSchemeVO = this.commonRateSchemeService.getRateSchemeByTitle(title);
            if (rateSchemeVO == null) continue;
            List excelDatas = importExcelSheet.getExcelSheetDatas();
            CopyOnWriteArrayList<Map<Integer, String>> datas = new CopyOnWriteArrayList<Map<Integer, String>>();
            for (int i = 0; i < excelDatas.size(); ++i) {
                Object[] dataArr = (Object[])excelDatas.get(i);
                LinkedHashMap<Integer, String> rowMap = new LinkedHashMap<Integer, String>();
                for (int j = 0; j < dataArr.length; ++j) {
                    String value = ConverterUtils.getAsString((Object)dataArr[j], null);
                    rowMap.put(j, value);
                }
                datas.add(rowMap);
            }
            List<CommonRateInfoVO> infoVOS = this.convertToRateInfoVOs(datas, rateSchemeVO.getCode());
            this.commonRateService.saveRates(infoVOS);
            result = true;
        }
        if (!result) {
            throw new BusinessRuntimeException("\u5bfc\u5165\u6587\u4ef6\u9875\u7b7e\u548c\u6c47\u7387\u65b9\u6848\u540d\u79f0\u5339\u914d\u5931\u8d25\uff0c\u65e0\u6cd5\u5bfc\u5165");
        }
    }

    private List<CommonRateInfoVO> convertToRateInfoVOs(List<Map<Integer, String>> excelSheetDatas, String rateSchemeCode) {
        Map<String, String> titleMap = this.getRateExcelColumnTitleMap();
        CopyOnWriteArrayList<CommonRateInfoVO> resultList = new CopyOnWriteArrayList<CommonRateInfoVO>();
        Map<Integer, String> headMap = excelSheetDatas.get(0);
        ConcurrentSkipListMap columnFiledMap = new ConcurrentSkipListMap();
        titleMap.forEach((propertyName, propertyTitle) -> columnFiledMap.put(propertyTitle, propertyName));
        excelSheetDatas.remove(0);
        List<BaseDataDO> rateTypeInfosCache = CommonRateUtils.getAllRateTypes();
        excelSheetDatas.parallelStream().forEach(excelSheetData -> {
            CommonRateInfoVO rateItemVO = new CommonRateInfoVO();
            HashMap rateInfo = new HashMap();
            excelSheetData.forEach((columnNum, cellValue) -> {
                String columnTitle = (String)headMap.get(columnNum);
                String propertyName = (String)columnFiledMap.get(columnTitle);
                Objects.requireNonNull(propertyName, "\u7b2c" + columnNum + "\u5217\u6807\u9898" + columnTitle + "\u4e0d\u5408\u6cd5\u3002");
                BaseDataDO rateType = CommonRateUtils.findByCode(propertyName, rateTypeInfosCache);
                if (rateType != null) {
                    if (ObjectUtils.isEmpty(cellValue)) {
                        cellValue = "0.0";
                    }
                    rateInfo.put(propertyName, CommonRateUtils.formateRateValue(cellValue));
                } else {
                    Field field = ReflectionUtils.findField(CommonRateInfoVO.class, (String)propertyName);
                    if (field != null) {
                        Object fieldValue = ConverterUtils.cast((Object)cellValue, field.getType());
                        ReflectionUtils.setFieldValue((Object)rateItemVO, (String)field.getName(), (Object)fieldValue);
                    }
                }
            });
            try {
                PeriodWrapper periodWrapper = new PeriodWrapper();
                periodWrapper.parseTitleString(rateItemVO.getDataTime());
                String code = periodWrapper.toString();
                rateItemVO.setDataTime(code);
            }
            catch (IllegalArgumentException e) {
                throw new BusinessRuntimeException("\u5bfc\u5165\u7684\u65f6\u671f\u503c[" + rateItemVO.getDataTime() + "]\u4e0d\u6ee1\u8db3\u65f6\u671f\u683c\u5f0f\uff0c\u65e0\u6cd5\u5bfc\u5165\u3002");
            }
            rateItemVO.setRateInfo(rateInfo);
            CommonRateInfoVO rateInfoVO = this.commonRateService.queryRateInfo(rateSchemeCode, rateItemVO.getDataTime(), rateItemVO.getSourceCurrencyCode(), rateItemVO.getTargetCurrencyCode());
            if (rateInfoVO != null) {
                rateItemVO.setId(rateInfoVO.getId());
            }
            rateItemVO.setRateSchemeCode(rateSchemeCode);
            resultList.add(rateItemVO);
        });
        return resultList;
    }
}

