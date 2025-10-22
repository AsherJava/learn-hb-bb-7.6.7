/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.financialcheckapi.scheme.dto.FinancialCheckBilateralSubSettingDTO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.dto.FinancialCheckUnilateralSubSettingDTO
 *  com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeVO
 *  com.jiuqi.gcreport.financialcheckcore.scheme.dao.FinancialCheckSchemeDao
 *  com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO
 *  com.jiuqi.gcreport.financialcheckcore.utils.BaseDataUtils
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.financialcheckImpl.scheme.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.financialcheckImpl.checkconfig.utils.FinancialCheckConfigUtils;
import com.jiuqi.gcreport.financialcheckImpl.scheme.service.FinancialCheckSchemeExtendExecutor;
import com.jiuqi.gcreport.financialcheckapi.scheme.dto.FinancialCheckBilateralSubSettingDTO;
import com.jiuqi.gcreport.financialcheckapi.scheme.dto.FinancialCheckUnilateralSubSettingDTO;
import com.jiuqi.gcreport.financialcheckapi.scheme.vo.FinancialCheckSchemeVO;
import com.jiuqi.gcreport.financialcheckcore.scheme.dao.FinancialCheckSchemeDao;
import com.jiuqi.gcreport.financialcheckcore.scheme.entity.FinancialCheckSchemeEO;
import com.jiuqi.gcreport.financialcheckcore.utils.BaseDataUtils;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class FcOffsetVoucherModeSchemeExtendExecutor
implements FinancialCheckSchemeExtendExecutor {
    @Autowired
    private FinancialCheckSchemeDao schemeDao;

    @Override
    public void save(FinancialCheckSchemeVO financialCheckSchemeVO, FinancialCheckSchemeEO scheme) {
        String schemeJson = this.convert2SchemeJson(financialCheckSchemeVO);
        scheme.setJsonString(schemeJson);
        this.schemeDao.update((BaseEntity)scheme);
    }

    private String convert2SchemeJson(FinancialCheckSchemeVO financialCheckSchemeVO) {
        HashMap<String, Object> schemeMap = new HashMap<String, Object>(3);
        List bilateralSubSettingDTO = financialCheckSchemeVO.getBilateralSubSettings();
        ArrayList bilateralSubSettings = new ArrayList();
        if (!CollectionUtils.isEmpty(bilateralSubSettingDTO)) {
            for (FinancialCheckBilateralSubSettingDTO bilateralSubSetting : bilateralSubSettingDTO) {
                HashMap<String, Object> setting = new HashMap<String, Object>(2);
                setting.put("group", bilateralSubSetting.getGroup());
                List subjects = bilateralSubSetting.getSubjects();
                List debtSubjects = bilateralSubSetting.getDebtSubjects();
                Set allAssetSubjects = BaseDataUtils.getAllChildrenContainSelfByCodes((String)"MD_ACCTSUBJECT", (Collection)subjects);
                Set allDebtSubjects = BaseDataUtils.getAllChildrenContainSelfByCodes((String)"MD_ACCTSUBJECT", (Collection)debtSubjects);
                List subjectIntersection = allAssetSubjects.stream().filter(allDebtSubjects::contains).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(subjectIntersection)) {
                    GcBaseData groupItem = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CHECK_ATTRIBUTE", bilateralSubSetting.getGroup());
                    throw new BusinessRuntimeException(String.format("\u5bf9\u8d26\u65b9\u6848-\u53cc\u8fb9\u79d1\u76ee\u8bbe\u7f6e-%s\u4e2d,\u503a\u6743\u65b9/\u9500\u552e\u65b9/\u6d41\u5165\u65b9\u79d1\u76ee\u548c\u503a\u52a1\u65b9/\u91c7\u8d2d\u65b9/\u6d41\u51fa\u65b9\u79d1\u76ee\u4e2d\u9009\u62e9\u7684\u79d1\u76ee\u4e0d\u5141\u8bb8\u6709\u4ea4\u96c6\uff0c\u8bf7\u91cd\u65b0\u914d\u7f6e\uff01\uff0c\u4ea4\u96c6\u4e3a%s", groupItem.getTitle(), subjectIntersection));
                }
                List baseDataCodeOnlyParent = BaseDataUtils.getBaseDataCodeOnlyParent((List)subjects, (String)"MD_ACCTSUBJECT");
                setting.put("subjects", baseDataCodeOnlyParent);
                List debtSubjectsOnlyParent = BaseDataUtils.getBaseDataCodeOnlyParent((List)debtSubjects, (String)"MD_ACCTSUBJECT");
                setting.put("debtSubjects", debtSubjectsOnlyParent);
                bilateralSubSettings.add(setting);
            }
        }
        schemeMap.put("bilateralSubSettings", bilateralSubSettings);
        schemeMap.put("unilateralCondition", financialCheckSchemeVO.getUnilateralCondition());
        List unilateralSubSettingDTO = financialCheckSchemeVO.getUnilateralSubSettings();
        ArrayList unilateralSubSettings = new ArrayList();
        if (!CollectionUtils.isEmpty(unilateralSubSettingDTO)) {
            for (FinancialCheckUnilateralSubSettingDTO unilateralSubSetting : unilateralSubSettingDTO) {
                HashMap<String, Object> setting = new HashMap<String, Object>(2);
                List subjects = unilateralSubSetting.getSubjects();
                List baseDataCodeOnlyParent = BaseDataUtils.getBaseDataCodeOnlyParent((List)subjects, (String)"MD_ACCTSUBJECT");
                setting.put("subjects", baseDataCodeOnlyParent);
                setting.put("oppSubject", unilateralSubSetting.getOppSubject());
                unilateralSubSettings.add(setting);
            }
        }
        schemeMap.put("unilateralSubSettings", unilateralSubSettings);
        return JsonUtils.writeValueAsString(schemeMap);
    }

    @Override
    public FinancialCheckSchemeVO convertEO2VO(FinancialCheckSchemeEO schemeEO) {
        String jsonString;
        FinancialCheckSchemeVO financialCheckSchemeVO = new FinancialCheckSchemeVO();
        BeanUtils.copyProperties(schemeEO, financialCheckSchemeVO);
        if (Objects.nonNull(schemeEO.getToleranceEnable())) {
            financialCheckSchemeVO.setToleranceEnable(schemeEO.getToleranceEnable().toString());
        }
        if (Objects.nonNull(schemeEO.getSpecialCheck())) {
            financialCheckSchemeVO.setSpecialCheck(schemeEO.getSpecialCheck().toString());
        }
        if (Objects.nonNull(schemeEO.getCheckAttribute())) {
            GcBaseData gcBaseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CHECK_ATTRIBUTE", schemeEO.getCheckAttribute());
            HashMap<String, String> item = new HashMap<String, String>();
            if (Objects.nonNull(gcBaseData)) {
                item.put("code", gcBaseData.getCode());
                item.put("title", gcBaseData.getTitle());
                financialCheckSchemeVO.setCheckAttributeItem(item);
            }
        }
        if (!StringUtils.isEmpty((String)schemeEO.getCheckDimensions())) {
            String[] dims = schemeEO.getCheckDimensions().split(",");
            financialCheckSchemeVO.setCheckDimensions(new ArrayList<String>(Arrays.asList(dims)));
        }
        if (!StringUtils.isEmpty((String)schemeEO.getUnitCodes())) {
            String[] units = schemeEO.getUnitCodes().split(",");
            ArrayList unitList = new ArrayList(units.length);
            for (String unitCode : units) {
                GcOrgCacheVO gcOrgCacheVOS = GcOrgPublicTool.getInstance((String)FinancialCheckConfigUtils.getCheckOrgType()).getOrgByCode(unitCode);
                if (!Objects.nonNull(gcOrgCacheVOS)) continue;
                HashMap<String, String> item = new HashMap<String, String>();
                item.put("code", gcOrgCacheVOS.getCode());
                item.put("title", gcOrgCacheVOS.getTitle());
                unitList.add(item);
            }
            financialCheckSchemeVO.setUnits(unitList);
            financialCheckSchemeVO.setUnitCodes(new ArrayList<String>(Arrays.asList(units)));
        }
        if (!StringUtils.isEmpty((String)(jsonString = schemeEO.getJsonString()))) {
            Map settings = (Map)JsonUtils.readValue((String)jsonString, (TypeReference)new TypeReference<Map<String, Object>>(){});
            financialCheckSchemeVO.setUnilateralCondition(Objects.isNull(settings.get("unilateralCondition")) ? null : (String)settings.get("unilateralCondition"));
            financialCheckSchemeVO.setBilateralSubSettings(this.convertBilateralSubSetting((List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(settings.get("bilateralSubSettings")), (TypeReference)new TypeReference<List<Map<String, Object>>>(){})));
            financialCheckSchemeVO.setUnilateralSubSettings(this.convertUnilateralSubSetting((List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(settings.get("unilateralSubSettings")), (TypeReference)new TypeReference<List<Map<String, Object>>>(){})));
        }
        return financialCheckSchemeVO;
    }

    private List<FinancialCheckBilateralSubSettingDTO> convertBilateralSubSetting(List<Map<String, Object>> bilateralSubSettings) {
        ArrayList<FinancialCheckBilateralSubSettingDTO> bilateralSubSettingDTOS = new ArrayList<FinancialCheckBilateralSubSettingDTO>();
        if (!CollectionUtils.isEmpty(bilateralSubSettings)) {
            for (Map<String, Object> bilateralSubSetting : bilateralSubSettings) {
                GcBaseData groupItem;
                FinancialCheckBilateralSubSettingDTO bilateralSubSettingDTO = new FinancialCheckBilateralSubSettingDTO();
                bilateralSubSettingDTO.setGroup((String)bilateralSubSetting.get("group"));
                if (!StringUtils.isEmpty((String)bilateralSubSettingDTO.getGroup()) && Objects.nonNull(groupItem = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CHECK_ATTRIBUTE", bilateralSubSettingDTO.getGroup()))) {
                    HashMap<String, String> item = new HashMap<String, String>();
                    item.put("code", groupItem.getCode());
                    item.put("title", groupItem.getTitle());
                    bilateralSubSettingDTO.setGroupItem(item);
                }
                List subjects = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)bilateralSubSetting.get("subjects")), (TypeReference)new TypeReference<List<String>>(){});
                bilateralSubSettingDTO.setSubjects(subjects);
                if (!CollectionUtils.isEmpty(subjects)) {
                    ArrayList gcBaseData = new ArrayList();
                    subjects.forEach(subject -> {
                        GcBaseData baseDataSimpleItem = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_ACCTSUBJECT", subject);
                        if (Objects.nonNull(baseDataSimpleItem)) {
                            HashMap<String, String> item = new HashMap<String, String>();
                            item.put("code", baseDataSimpleItem.getCode());
                            item.put("title", baseDataSimpleItem.getTitle());
                            gcBaseData.add(item);
                        }
                    });
                    bilateralSubSettingDTO.setSubjectItems(gcBaseData);
                }
                List debtSubjects = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)bilateralSubSetting.get("debtSubjects")), (TypeReference)new TypeReference<List<String>>(){});
                bilateralSubSettingDTO.setDebtSubjects(debtSubjects);
                if (!CollectionUtils.isEmpty(debtSubjects)) {
                    ArrayList gcBaseData = new ArrayList();
                    debtSubjects.forEach(subject -> {
                        GcBaseData baseDataSimpleItem = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_ACCTSUBJECT", subject);
                        if (Objects.nonNull(baseDataSimpleItem)) {
                            HashMap<String, String> item = new HashMap<String, String>();
                            item.put("code", baseDataSimpleItem.getCode());
                            item.put("title", baseDataSimpleItem.getTitle());
                            gcBaseData.add(item);
                        }
                    });
                    bilateralSubSettingDTO.setDebtSubjectItems(gcBaseData);
                }
                bilateralSubSettingDTOS.add(bilateralSubSettingDTO);
            }
        }
        return bilateralSubSettingDTOS;
    }

    private List<FinancialCheckUnilateralSubSettingDTO> convertUnilateralSubSetting(List<Map<String, Object>> unilateralSubSettings) {
        ArrayList<FinancialCheckUnilateralSubSettingDTO> unilateralSubSettingDTOS = new ArrayList<FinancialCheckUnilateralSubSettingDTO>();
        if (!CollectionUtils.isEmpty(unilateralSubSettings)) {
            for (Map<String, Object> unilateralSubSetting : unilateralSubSettings) {
                FinancialCheckUnilateralSubSettingDTO unilateralSubSettingDTO = new FinancialCheckUnilateralSubSettingDTO();
                List subjects = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)unilateralSubSetting.get("subjects")), (TypeReference)new TypeReference<List<String>>(){});
                unilateralSubSettingDTO.setSubjects(subjects);
                if (!CollectionUtils.isEmpty(subjects)) {
                    ArrayList gcBaseData = new ArrayList();
                    subjects.forEach(subject -> {
                        GcBaseData baseDataSimpleItem = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_ACCTSUBJECT", subject);
                        if (Objects.nonNull(baseDataSimpleItem)) {
                            HashMap<String, String> item = new HashMap<String, String>();
                            item.put("code", baseDataSimpleItem.getCode());
                            item.put("title", baseDataSimpleItem.getTitle());
                            gcBaseData.add(item);
                        }
                    });
                    unilateralSubSettingDTO.setSubjectItems(gcBaseData);
                }
                if (Objects.nonNull(unilateralSubSetting.get("oppSubject"))) {
                    GcBaseData baseDataSimpleItem;
                    String oppSubject = (String)unilateralSubSetting.get("oppSubject");
                    unilateralSubSettingDTO.setOppSubject(oppSubject);
                    if (!StringUtils.isEmpty((String)oppSubject) && Objects.nonNull(baseDataSimpleItem = GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_ACCTSUBJECT", oppSubject))) {
                        HashMap<String, String> item = new HashMap<String, String>();
                        item.put("code", baseDataSimpleItem.getCode());
                        item.put("title", baseDataSimpleItem.getTitle());
                        unilateralSubSettingDTO.setOppSubjectItems(item);
                    }
                }
                unilateralSubSettingDTOS.add(unilateralSubSettingDTO);
            }
        }
        return unilateralSubSettingDTOS;
    }
}

