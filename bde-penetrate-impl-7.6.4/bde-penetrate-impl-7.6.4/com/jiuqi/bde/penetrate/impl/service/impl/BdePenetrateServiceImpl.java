/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum
 *  com.jiuqi.bde.bizmodel.impl.dimension.service.FetchDimensionService
 *  com.jiuqi.bde.bizmodel.impl.model.service.BizModelService
 *  com.jiuqi.bde.bizmodel.impl.orgmapping.service.IOrgMappingServiceProvider
 *  com.jiuqi.bde.common.dto.DimensionValue
 *  com.jiuqi.bde.common.dto.FixedAdaptSettingVO
 *  com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.util.BdeCommonUtil
 *  com.jiuqi.bde.common.util.Pair
 *  com.jiuqi.bde.penetrate.client.dto.FetchSettingInfoDTO
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateFetchSettingInfo
 *  com.jiuqi.bde.penetrate.client.dto.PenetrateOffsetedDate
 *  com.jiuqi.bde.penetrate.client.dto.PenetratePeriodOffsetDTO
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.BeanConvertUtil
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.apache.commons.collections4.MapUtils
 */
package com.jiuqi.bde.penetrate.impl.service.impl;

import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.client.enums.BizModelCategoryEnum;
import com.jiuqi.bde.bizmodel.impl.dimension.service.FetchDimensionService;
import com.jiuqi.bde.bizmodel.impl.model.service.BizModelService;
import com.jiuqi.bde.bizmodel.impl.orgmapping.service.IOrgMappingServiceProvider;
import com.jiuqi.bde.common.dto.DimensionValue;
import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;
import com.jiuqi.bde.common.dto.FixedFetchSourceRowSettingVO;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.util.BdeCommonUtil;
import com.jiuqi.bde.common.util.Pair;
import com.jiuqi.bde.penetrate.client.dto.FetchSettingInfoDTO;
import com.jiuqi.bde.penetrate.client.dto.PenetrateFetchSettingInfo;
import com.jiuqi.bde.penetrate.client.dto.PenetrateOffsetedDate;
import com.jiuqi.bde.penetrate.client.dto.PenetratePeriodOffsetDTO;
import com.jiuqi.bde.penetrate.impl.gather.IPenetrateBaseDataInfoProviderGather;
import com.jiuqi.bde.penetrate.impl.service.BdePenetrateService;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.BeanConvertUtil;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.va.domain.common.JSONUtil;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BdePenetrateServiceImpl
implements BdePenetrateService {
    private static final String DIM_MAP_DATATIME = "DATATIME";
    @Autowired
    private IOrgMappingServiceProvider orgMappingProvider;
    @Autowired
    private IPenetrateBaseDataInfoProviderGather providerGather;
    @Autowired
    private BizModelService bizModelService;
    @Autowired
    private FetchDimensionService dimensionService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String FN_TMPL = "%1$s|%2$s";
    private static final String FN_DELIMITER_COLON = ":";
    private static final String FN_DELIMITER_COMMA = ",";

    @Override
    public PenetrateOffsetedDate offsetPeriodAndYear(PenetratePeriodOffsetDTO periodOffsetCondi) {
        PenetrateOffsetedDate resultPeriod = new PenetrateOffsetedDate();
        resultPeriod.setEndDate(periodOffsetCondi.getEndDate());
        resultPeriod.setBeginDate(periodOffsetCondi.getBeginDate());
        String periodOffset = periodOffsetCondi.getPeriodOffset();
        String yearOffset = periodOffsetCondi.getYearOffset();
        if (StringUtils.isEmpty((String)periodOffset) && StringUtils.isEmpty((String)yearOffset)) {
            return resultPeriod;
        }
        int periodType = this.getPeriodType(periodOffsetCondi.getDimensionSet());
        this.offsetDate(resultPeriod, yearOffset, 1);
        this.offsetDate(resultPeriod, periodOffset, periodType);
        return resultPeriod;
    }

    @Override
    public Map<String, Map<String, String>> queryFetchSettingInfo(FetchSettingInfoDTO fetchSettingInfo) {
        if (fetchSettingInfo == null) {
            return CollectionUtils.newHashMap();
        }
        FixedAdaptSettingVO fixedSettingData = fetchSettingInfo.getFixedSettingData();
        if (fixedSettingData == null || fixedSettingData.getBizModelFormula() == null || fixedSettingData.getBizModelFormula().isEmpty()) {
            return CollectionUtils.newHashMap();
        }
        Map<String, String> dimensionMap = this.dimensionService.listAllDimension().stream().filter(item -> !StringUtils.isEmpty((String)item.getDictTableName())).collect(Collectors.toMap(DimensionVO::getCode, DimensionVO::getDictTableName, (k1, k2) -> k2));
        Set finBizModelSet = this.bizModelService.listByCategory(BizModelCategoryEnum.BIZMODEL_FINDATA.getCode()).stream().map(BizModelDTO::getCode).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(finBizModelSet)) {
            return CollectionUtils.newHashMap();
        }
        HashMap<String, Set> baseDataInfoQueryCondi = new HashMap<String, Set>();
        for (Map.Entry bizModelFormulaEntry : fixedSettingData.getBizModelFormula().entrySet()) {
            if (!finBizModelSet.contains(bizModelFormulaEntry.getKey())) continue;
            for (FixedFetchSourceRowSettingVO rowSetting : (List)bizModelFormulaEntry.getValue()) {
                if (!StringUtils.isEmpty((String)rowSetting.getSubjectCode())) {
                    baseDataInfoQueryCondi.computeIfAbsent("MD_ACCTSUBJECT", key -> new HashSet());
                    ((Set)baseDataInfoQueryCondi.get("MD_ACCTSUBJECT")).addAll(this.calcSubjectCode(rowSetting.getSubjectCode()));
                }
                if (!StringUtils.isEmpty((String)rowSetting.getExcludeSubjectCode())) {
                    baseDataInfoQueryCondi.computeIfAbsent("MD_ACCTSUBJECT", key -> new HashSet());
                    ((Set)baseDataInfoQueryCondi.get("MD_ACCTSUBJECT")).addAll(this.calcSubjectCode(rowSetting.getExcludeSubjectCode()));
                }
                if (!StringUtils.isEmpty((String)rowSetting.getReclassSubjCode())) {
                    baseDataInfoQueryCondi.computeIfAbsent("MD_ACCTSUBJECT", key -> new HashSet());
                    ((Set)baseDataInfoQueryCondi.get("MD_ACCTSUBJECT")).addAll(this.calcSubjectCode(rowSetting.getReclassSubjCode()));
                }
                if (!StringUtils.isEmpty((String)rowSetting.getReclassSrcSubjCode())) {
                    baseDataInfoQueryCondi.computeIfAbsent("MD_ACCTSUBJECT", key -> new HashSet());
                    ((Set)baseDataInfoQueryCondi.get("MD_ACCTSUBJECT")).addAll(this.calcSubjectCode(rowSetting.getReclassSrcSubjCode()));
                }
                if (!StringUtils.isEmpty((String)rowSetting.getCashCode())) {
                    baseDataInfoQueryCondi.computeIfAbsent("MD_CFITEM", key -> new HashSet());
                    ((Set)baseDataInfoQueryCondi.get("MD_CFITEM")).addAll(this.calcSubjectCode(rowSetting.getCashCode()));
                }
                if (!StringUtils.isEmpty((String)rowSetting.getCurrencyCode())) {
                    baseDataInfoQueryCondi.computeIfAbsent("MD_CURRENCY", key -> new HashSet());
                    ((Set)baseDataInfoQueryCondi.get("MD_CURRENCY")).add(rowSetting.getCurrencyCode());
                }
                if (!StringUtils.isEmpty((String)rowSetting.getOrgCode())) {
                    baseDataInfoQueryCondi.computeIfAbsent("MD_ORG", key -> new HashSet());
                    ((Set)baseDataInfoQueryCondi.get("MD_ORG")).add(rowSetting.getOrgCode());
                }
                if (CollectionUtils.isEmpty((Collection)rowSetting.getDimComb()) || StringUtils.isEmpty((String)rowSetting.getDimensionSetting())) continue;
                List dimJsonArray = JSONUtil.parseMapArray((String)rowSetting.getDimensionSetting());
                for (String dimCode : rowSetting.getDimComb()) {
                    for (Map jsonMap : dimJsonArray) {
                        if (!dimCode.equalsIgnoreCase(jsonMap.get("dimCode").toString()) || jsonMap.get("dimValue") == null || StringUtils.isEmpty((String)((String)jsonMap.get("dimValue"))) || StringUtils.isEmpty((String)dimensionMap.get(dimCode))) continue;
                        String baseData = dimensionMap.get(dimCode);
                        baseDataInfoQueryCondi.computeIfAbsent(baseData, key -> new HashSet());
                        ((Set)baseDataInfoQueryCondi.get(baseData)).addAll(this.calcSubjectCode((String)jsonMap.get("dimValue")));
                    }
                }
            }
        }
        Map<Object, Object> baseDataInfoMap = new HashMap();
        PenetrateFetchSettingInfo condi = (PenetrateFetchSettingInfo)BeanConvertUtil.convert((Object)fetchSettingInfo, PenetrateFetchSettingInfo.class, (String[])new String[0]);
        condi.setFetchSettingInfoMap(baseDataInfoQueryCondi);
        try {
            baseDataInfoMap = this.queryBaseDataInfo(condi);
        }
        catch (Exception e) {
            this.logger.error("\u900f\u89c6\u65f6\u8c03\u7528\u57fa\u7840\u6570\u636e\u540d\u79f0\u67e5\u8be2\u63a5\u53e3\u51fa\u73b0\u9519\u8bef", e);
        }
        if (baseDataInfoMap == null || baseDataInfoMap.isEmpty()) {
            return CollectionUtils.newHashMap();
        }
        HashMap<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
        for (Map.Entry bizModelFormulaEntry : fixedSettingData.getBizModelFormula().entrySet()) {
            if (!finBizModelSet.contains(bizModelFormulaEntry.getKey())) continue;
            for (FixedFetchSourceRowSettingVO rowSetting : (List)bizModelFormulaEntry.getValue()) {
                String code;
                result.computeIfAbsent(rowSetting.getId(), key -> new HashMap());
                if (!StringUtils.isEmpty((String)rowSetting.getSubjectCode())) {
                    code = this.transSubjectShowName((Map)baseDataInfoMap.get("MD_ACCTSUBJECT"), rowSetting.getSubjectCode());
                    ((Map)result.get(rowSetting.getId())).put("subjectCode@Vo", code);
                }
                if (!StringUtils.isEmpty((String)rowSetting.getExcludeSubjectCode())) {
                    code = this.transSubjectShowName((Map)baseDataInfoMap.get("MD_ACCTSUBJECT"), rowSetting.getExcludeSubjectCode());
                    ((Map)result.get(rowSetting.getId())).put("excludeSubjectCode@Vo", code);
                }
                if (!StringUtils.isEmpty((String)rowSetting.getReclassSubjCode())) {
                    code = this.transSubjectShowName((Map)baseDataInfoMap.get("MD_ACCTSUBJECT"), rowSetting.getReclassSubjCode());
                    ((Map)result.get(rowSetting.getId())).put("reclassSubjCode@Vo", code);
                }
                if (!StringUtils.isEmpty((String)rowSetting.getReclassSrcSubjCode())) {
                    code = this.transSubjectShowName((Map)baseDataInfoMap.get("MD_ACCTSUBJECT"), rowSetting.getReclassSrcSubjCode());
                    ((Map)result.get(rowSetting.getId())).put("reclassSrcSubjCode@Vo", code);
                }
                if (!StringUtils.isEmpty((String)rowSetting.getCashCode())) {
                    code = this.transShowName((Map)baseDataInfoMap.get("MD_CFITEM"), rowSetting.getCashCode());
                    ((Map)result.get(rowSetting.getId())).put("cashCode@Vo", code);
                }
                if (!StringUtils.isEmpty((String)rowSetting.getCurrencyCode())) {
                    code = this.transShowName((Map)baseDataInfoMap.get("MD_CURRENCY"), rowSetting.getCurrencyCode());
                    ((Map)result.get(rowSetting.getId())).put("currencyCode@Vo", code);
                }
                if (!StringUtils.isEmpty((String)rowSetting.getOrgCode())) {
                    code = this.transShowName((Map)baseDataInfoMap.get("MD_ORG"), rowSetting.getOrgCode());
                    ((Map)result.get(rowSetting.getId())).put("orgCode@Vo", code);
                }
                if (CollectionUtils.isEmpty((Collection)rowSetting.getDimComb()) || StringUtils.isEmpty((String)rowSetting.getDimensionSetting())) continue;
                List dimJsonArray = JSONUtil.parseMapArray((String)rowSetting.getDimensionSetting());
                for (String dimCode : rowSetting.getDimComb()) {
                    for (Map jsonMap : dimJsonArray) {
                        if (!dimCode.equalsIgnoreCase(jsonMap.get("dimCode").toString()) || jsonMap.get("dimValue") == null || StringUtils.isEmpty((String)((String)jsonMap.get("dimValue"))) || StringUtils.isEmpty((String)dimensionMap.get(dimCode))) continue;
                        String baseData = dimensionMap.get(dimCode);
                        String code2 = this.transSubjectShowName((Map)baseDataInfoMap.get(baseData), (String)jsonMap.get("dimValue"));
                        ((Map)result.get(rowSetting.getId())).put(dimCode + "@Vo", code2);
                    }
                }
            }
        }
        return result;
    }

    private String transSubjectShowName(Map<String, String> baseDataMap, String subjectCode) {
        if (subjectCode.contains(FN_DELIMITER_COLON)) {
            String[] codeArr = subjectCode.split(FN_DELIMITER_COLON);
            StringBuilder codeBuilder = new StringBuilder();
            codeBuilder.append(this.transShowName(baseDataMap, codeArr[0])).append(FN_DELIMITER_COLON);
            codeBuilder.append(this.transShowName(baseDataMap, codeArr[1]));
            return codeBuilder.toString();
        }
        if (subjectCode.contains(FN_DELIMITER_COMMA)) {
            String[] codeArr = subjectCode.split(FN_DELIMITER_COMMA);
            StringBuilder codeBuilder = new StringBuilder();
            for (String code : codeArr) {
                codeBuilder.append(this.transShowName(baseDataMap, code)).append(FN_DELIMITER_COMMA);
            }
            if (codeBuilder.length() > 1) {
                codeBuilder.delete(codeBuilder.length() - 1, codeBuilder.length());
            }
            return codeBuilder.toString();
        }
        return this.transShowName(baseDataMap, subjectCode);
    }

    private String transShowName(Map<String, String> baseDataMap, String code) {
        if (baseDataMap == null) {
            return code;
        }
        if (baseDataMap.get(code) == null) {
            return code;
        }
        return String.format(FN_TMPL, code, baseDataMap.get(code));
    }

    private List<String> calcSubjectCode(String subjectCode) {
        Assert.isNotEmpty((String)subjectCode);
        if (subjectCode.contains(FN_DELIMITER_COLON)) {
            return CollectionUtils.newArrayList((Object[])subjectCode.split(FN_DELIMITER_COLON));
        }
        if (subjectCode.contains(FN_DELIMITER_COMMA)) {
            return CollectionUtils.newArrayList((Object[])subjectCode.split(FN_DELIMITER_COMMA));
        }
        return CollectionUtils.newArrayList((Object[])new String[]{subjectCode});
    }

    @Override
    public Map<String, Map<String, String>> queryBaseDataInfo(PenetrateFetchSettingInfo fetchSettingInfo) {
        OrgMappingDTO orgMapping = null;
        try {
            orgMapping = this.orgMappingProvider.getByCode(fetchSettingInfo.getBblx()).getOrgMapping(fetchSettingInfo.getUnitCode());
            return this.providerGather.getProvider(orgMapping.getPluginType()).provideBaseDataInfo(fetchSettingInfo);
        }
        catch (Exception e) {
            this.logger.debug("BDE\u67e5\u8be2\u57fa\u7840\u6570\u636e\u4fe1\u606f\u51fa\u73b0\u5f02\u5e38", e);
            return CollectionUtils.newHashMap();
        }
    }

    private void offsetDate(PenetrateOffsetedDate resultPeriod, String periodOffset, int type) {
        DateFormat formatter = DateUtils.createFormatter((String)DateUtils.DEFAULT_DATE_FORMAT);
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = formatter.parse(resultPeriod.getBeginDate());
        }
        catch (ParseException e) {
            throw new BusinessRuntimeException(String.format("\u5b57\u7b26\u4e32\u3010%1$s\u3011\u89e3\u6790\u4e3a\u65e5\u671f\u51fa\u73b0\u9519\u8bef", resultPeriod.getBeginDate()), (Throwable)e);
        }
        try {
            endDate = formatter.parse(resultPeriod.getEndDate());
        }
        catch (ParseException e) {
            throw new BusinessRuntimeException(String.format("\u5b57\u7b26\u4e32\u3010%1$s\u3011\u89e3\u6790\u4e3a\u65e5\u671f\u51fa\u73b0\u9519\u8bef", resultPeriod.getEndDate()), (Throwable)e);
        }
        Pair<Date, Date> handleOptimizeDateOffset = this.handleDateOffset((Pair<Date, Date>)Pair.of((Object)startDate, (Object)endDate), periodOffset, type);
        resultPeriod.setBeginDate(formatter.format((Date)handleOptimizeDateOffset.getFirst()));
        resultPeriod.setEndDate(formatter.format((Date)handleOptimizeDateOffset.getSecond()));
    }

    private Pair<Date, Date> handleDateOffset(Pair<Date, Date> dateRange, String offsetVlue, int npPeriodType) {
        Pair handleOptimizeDateOffset = null;
        switch (npPeriodType) {
            case 1: {
                handleOptimizeDateOffset = BdeCommonUtil.handleOptimizeDateOffset(dateRange, (String)offsetVlue, (int)1);
                break;
            }
            case 2: {
                handleOptimizeDateOffset = BdeCommonUtil.handleOptimizeDateOffset(dateRange, (String)String.valueOf(Integer.valueOf(offsetVlue) * 6), (int)2);
                break;
            }
            case 3: {
                handleOptimizeDateOffset = BdeCommonUtil.handleOptimizeDateOffset(dateRange, (String)String.valueOf(Integer.valueOf(offsetVlue) * 3), (int)2);
                break;
            }
            case 4: {
                handleOptimizeDateOffset = BdeCommonUtil.handleOptimizeDateOffset(dateRange, (String)offsetVlue, (int)2);
                break;
            }
            case 5: {
                handleOptimizeDateOffset = BdeCommonUtil.handleOptimizeDateOffset(dateRange, (String)String.valueOf(Integer.valueOf(offsetVlue) * 10), (int)5);
                break;
            }
            case 6: {
                handleOptimizeDateOffset = BdeCommonUtil.handleOptimizeDateOffset(dateRange, (String)offsetVlue, (int)5);
                break;
            }
            default: {
                handleOptimizeDateOffset = BdeCommonUtil.handleOptimizeDateOffset(dateRange, (String)offsetVlue, (int)2);
            }
        }
        if (handleOptimizeDateOffset == null) {
            return dateRange;
        }
        return handleOptimizeDateOffset;
    }

    private int getPeriodType(Map<String, DimensionValue> dimensionSet) {
        if (MapUtils.isEmpty(dimensionSet)) {
            return 4;
        }
        Map<String, String> dimMap = dimensionSet.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> Objects.isNull(((DimensionValue)e.getValue()).getValue()) ? "" : ((DimensionValue)e.getValue()).getValue()));
        String datatime = dimMap.get(DIM_MAP_DATATIME);
        PeriodWrapper periodWrapper = new PeriodWrapper(datatime);
        return periodWrapper.getType();
    }
}

