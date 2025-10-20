/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.vo.UnitParamDTO
 *  com.jiuqi.bde.common.constant.ComputationModelEnum
 *  com.jiuqi.bde.fetch.client.OrgMappingClient
 *  com.jiuqi.budget.component.domain.FormulaExeParam
 *  com.jiuqi.budget.component.service.FormulaExecService
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedAdaptSettingDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.enums.AdaptContextEnum
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingDesService
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.web.FetchSettingDesController
 *  com.jiuqi.gcreport.bde.penetrate.client.vo.BdeFormulaResultVO
 *  com.jiuqi.gcreport.bde.penetrate.client.vo.BdeQueryFormulaParamVO
 *  com.jiuqi.nr.period.common.utils.StringUtils
 *  com.jiuqi.va.domain.common.JSONUtil
 *  org.thymeleaf.util.MapUtils
 */
package com.jiuqi.gcreport.bde.penetrate.impl.pierce.service.impl;

import com.jiuqi.bde.bizmodel.client.vo.UnitParamDTO;
import com.jiuqi.bde.common.constant.ComputationModelEnum;
import com.jiuqi.bde.fetch.client.OrgMappingClient;
import com.jiuqi.budget.component.domain.FormulaExeParam;
import com.jiuqi.budget.component.service.FormulaExecService;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSettingCond;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedAdaptSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FixedFieldDefineSettingDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.enums.AdaptContextEnum;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSettingDesService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.web.FetchSettingDesController;
import com.jiuqi.gcreport.bde.penetrate.client.vo.BdeFormulaResultVO;
import com.jiuqi.gcreport.bde.penetrate.client.vo.BdeQueryFormulaParamVO;
import com.jiuqi.gcreport.bde.penetrate.impl.pierce.service.GcBdeFetchPierceService;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.MapUtils;

@Service
public class GcBdeFetchPierceServiceImpl
implements GcBdeFetchPierceService {
    private static final Logger log = LoggerFactory.getLogger(GcBdeFetchPierceServiceImpl.class);
    @Autowired
    FetchSettingDesController fetchSettingDesController;
    @Autowired
    private FetchSettingDesService fetchSettingDesService;
    @Autowired
    private FormulaExecService formulaExecService;
    @Autowired
    private OrgMappingClient orgMappingClient;

    @Override
    public BdeFormulaResultVO analysisBdeFetchSetting(BdeQueryFormulaParamVO bdeQueryFormulaParamVO) {
        BdeFormulaResultVO bdeFormulaResultVO = new BdeFormulaResultVO();
        ArrayList<String> subjectList = new ArrayList<String>();
        HashMap<String, List<String>> assistMap = new HashMap<String, List<String>>();
        FetchSettingCond fetchSettingCond = bdeQueryFormulaParamVO.getFetchSettingCond();
        Objects.requireNonNull(fetchSettingCond.getFormSchemeId(), "\u62a5\u8868\u65b9\u6848key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingCond.getFetchSchemeId(), "\u53d6\u6570\u65b9\u6848key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingCond.getFormId(), "\u8868\u5355key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingCond.getRegionId(), "\u533a\u57dfkey\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        Objects.requireNonNull(fetchSettingCond.getDataLinkId(), "\u94fe\u63a5key\u4e0d\u5141\u8bb8\u4e3a\u7a7a\u3002");
        FixedFieldDefineSettingDTO data = this.fetchSettingDesService.listDataLinkFixedSettingDesRowRecords(fetchSettingCond);
        if (Objects.isNull(data)) {
            bdeFormulaResultVO.setHaveSetting(Boolean.valueOf(false));
            return bdeFormulaResultVO;
        }
        bdeFormulaResultVO.setHaveSetting(Boolean.valueOf(true));
        List fixedSettingData = data.getFixedSettingData();
        for (FixedAdaptSettingDTO fixedSettingDatum : fixedSettingData) {
            if (!this.executeAdaptCond(fixedSettingDatum.getAdaptFormula(), bdeQueryFormulaParamVO.getDimensionSet())) continue;
            Map bizModelFormula = fixedSettingDatum.getBizModelFormula();
            for (List rowSettingMapList : bizModelFormula.values()) {
                for (Map rowSetting : rowSettingMapList) {
                    String dimensionSetting;
                    String bizModelCode = rowSetting.get("bizModelCode").toString();
                    if (ComputationModelEnum.XJLLBALANCE.getCode().equals(bizModelCode)) {
                        subjectList.addAll(Arrays.asList(rowSetting.get("cashCode").toString().split(",")));
                    } else {
                        subjectList.addAll(Arrays.asList(rowSetting.get("subjectCode").toString().split(",")));
                    }
                    if (Objects.isNull(rowSetting.get("dimensionSetting")) || StringUtils.isEmpty((String)(dimensionSetting = rowSetting.get("dimensionSetting").toString()))) continue;
                    List dimListMap = JSONUtil.parseMapArray((String)dimensionSetting, String.class, String.class);
                    for (Map dimMap : dimListMap) {
                        ArrayList<String> assistList;
                        String dimCode = (String)dimMap.get("dimCode");
                        String dimValue = (String)dimMap.get("dimValue");
                        LinkedList<String> dimValueList = new LinkedList<String>();
                        if (StringUtils.isNotEmpty((String)dimValue) && dimValue.contains(",")) {
                            dimValueList.addAll(Arrays.asList(dimValue.split(",")));
                        } else {
                            dimValueList.add(dimValue);
                        }
                        if (assistMap.containsKey(dimCode)) {
                            assistList = (ArrayList<String>)assistMap.get(dimCode);
                            assistList.addAll(dimValueList);
                            continue;
                        }
                        assistList = new ArrayList<String>(dimValueList);
                        assistMap.put(dimCode, assistList);
                    }
                }
            }
        }
        bdeFormulaResultVO.setSubjectCodes(subjectList);
        if (MapUtils.isEmpty(assistMap)) {
            bdeFormulaResultVO.setAssistMap(assistMap);
        } else {
            bdeFormulaResultVO.setAssistMap(this.mappingBdeAssist(assistMap, bdeQueryFormulaParamVO.getBblx(), bdeQueryFormulaParamVO.getOrgCode()));
        }
        return bdeFormulaResultVO;
    }

    private boolean executeAdaptCond(String adaptExpression, Map<String, String> dimensionMap) {
        if (StringUtils.isEmpty((String)adaptExpression)) {
            return true;
        }
        if (MapUtils.isEmpty(dimensionMap)) {
            log.warn("BDE\u7a7f\u900f-\u5e26\u9002\u5e94\u6761\u4ef6\u7684\u53d6\u6570\u8bbe\u7f6e \u5728\u5224\u65ad\u9002\u5e94\u6761\u4ef6\u65f6\uff0c\u7ef4\u5ea6\u4e3a\u7a7a\uff1a{}", (Object)JsonUtils.writeValueAsString(dimensionMap));
            return false;
        }
        String orgCode = dimensionMap.get("MD_ORG");
        Map<String, String> dimMap = dimensionMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> Objects.isNull(e.getValue()) ? "" : (String)e.getValue()));
        String datatime = dimMap.get("DATATIME");
        String gcOrgType = dimMap.get("MD_GCORGTYPE");
        HashMap<String, String> adaptContext = new HashMap<String, String>(8);
        adaptContext.put(AdaptContextEnum.MD_ORG.getKey(), orgCode);
        adaptContext.put(AdaptContextEnum.DATATIME.getKey(), datatime);
        adaptContext.put(gcOrgType, orgCode);
        FormulaExeParam formulaExeParam = new FormulaExeParam();
        formulaExeParam.setFormulaExpress(adaptExpression);
        formulaExeParam.setDimValMap(adaptContext);
        try {
            log.debug("\u5dee\u989d\u7a7f\u900f\u89e3\u6790\u9002\u5e94\u6761\u4ef6\u53c2\u6570\uff1a{}", (Object)JsonUtils.writeValueAsString((Object)formulaExeParam));
            return this.formulaExecService.getAdaptVal(formulaExeParam);
        }
        catch (Exception ex) {
            log.error("\u5dee\u989d\u7a7f\u900f\u65f6\u89e3\u6790\u9002\u5e94\u6761\u4ef6\u62a5\u9519", ex);
            return false;
        }
    }

    private Map<String, List<String>> mappingBdeAssist(Map<String, List<String>> bdeAssistMap, String bblx, String unitCode) {
        HashMap<String, List<String>> gcAssistMap = new HashMap<String, List<String>>();
        UnitParamDTO unitParam = new UnitParamDTO();
        unitParam.setUnitCode(unitCode);
        unitParam.setBblx(bblx);
        Map assistMappingMap = (Map)BdeClientUtil.parseResponse((BusinessResponseEntity)this.orgMappingClient.queryBeforeMappingAssistMap(unitParam));
        if (MapUtils.isEmpty((Map)assistMappingMap)) {
            String warn = String.format("\u5355\u4f4d%1$s\u5728BDE\u6ca1\u6709\u6620\u5c04\u6ca1\u6709\u8f85\u52a9\u6620\u5c04", unitCode);
            log.warn(warn);
            return bdeAssistMap;
        }
        for (Map.Entry<String, List<String>> bdeAssistEntry : bdeAssistMap.entrySet()) {
            String gcAssistName = (String)assistMappingMap.get(bdeAssistEntry.getKey());
            if (StringUtils.isNotEmpty((String)gcAssistName)) {
                gcAssistMap.put(gcAssistName, bdeAssistEntry.getValue());
                continue;
            }
            String warn = String.format("\u5355\u4f4d%1$s\u7684\u8f85\u52a9\u7ef4\u5ea6%2$ss\u5728BDE\u6ca1\u6709\u6620\u5c04", unitCode, gcAssistName);
            log.warn(warn);
        }
        return gcAssistMap;
    }
}

