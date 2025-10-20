/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.conditionalstyle.controller.IDesignConditionalStyleController
 *  com.jiuqi.nr.conditionalstyle.facade.DesignConditionalStyle
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.controller.IPrintDesignTimeController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignAnalysisSchemeParamDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkMappingDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintComTemDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignRegionSettingDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskLinkDefine
 *  com.jiuqi.nr.definition.facade.FormulaVariDefine
 *  com.jiuqi.nr.definition.facade.ReportTagDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule
 *  com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink
 *  com.jiuqi.nr.definition.facade.report.TransformReportDefine
 *  com.jiuqi.nr.definition.facade.report.TransportReportTemplateDefineImpl
 *  com.jiuqi.nr.definition.internal.dao.DesignFormGroupDefineDao
 *  com.jiuqi.nr.definition.internal.dao.DesignFormGroupLinkDao
 *  com.jiuqi.nr.definition.internal.dao.DesignRegionSettingDefineDao
 *  com.jiuqi.nr.definition.internal.impl.DesignBigDataTable
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil
 *  com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink
 *  com.jiuqi.nr.definition.internal.service.DesignBigDataService
 *  com.jiuqi.nr.definition.internal.service.DesignDataLinkMappingDefineService
 *  com.jiuqi.nr.definition.internal.service.DesignFormDefineService
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  com.jiuqi.util.OrderGenerator
 *  nr.single.map.configurations.bean.ISingleMappingConfig
 *  nr.single.map.configurations.bean.SingleFileInfo
 *  nr.single.map.configurations.dao.ConfigDao
 *  nr.single.map.configurations.service.MappingFileService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.designer.web.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.conditionalstyle.controller.IDesignConditionalStyleController;
import com.jiuqi.nr.conditionalstyle.facade.DesignConditionalStyle;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.controller.IPrintDesignTimeController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignAnalysisSchemeParamDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkMappingDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintComTemDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;
import com.jiuqi.nr.definition.facade.DesignTaskLinkDefine;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.facade.ReportTagDefine;
import com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink;
import com.jiuqi.nr.definition.facade.report.TransformReportDefine;
import com.jiuqi.nr.definition.facade.report.TransportReportTemplateDefineImpl;
import com.jiuqi.nr.definition.internal.dao.DesignFormGroupDefineDao;
import com.jiuqi.nr.definition.internal.dao.DesignFormGroupLinkDao;
import com.jiuqi.nr.definition.internal.dao.DesignRegionSettingDefineDao;
import com.jiuqi.nr.definition.internal.impl.DesignBigDataTable;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nr.definition.internal.service.DesignBigDataService;
import com.jiuqi.nr.definition.internal.service.DesignDataLinkMappingDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormDefineService;
import com.jiuqi.nr.designer.helper.RegionSurveyHelper;
import com.jiuqi.nr.designer.web.service.TaskDesignerService;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.util.OrderGenerator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.SingleFileInfo;
import nr.single.map.configurations.dao.ConfigDao;
import nr.single.map.configurations.service.MappingFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class FormSchemeService {
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IFormulaDesignTimeController formulaDesignTimeController;
    @Autowired
    private TaskDesignerService taskDesignerService;
    @Autowired
    private DesignBigDataService bigDataService;
    @Autowired
    private DesignRegionSettingDefineDao regionSettingDefineDao;
    @Autowired
    private IPrintDesignTimeController printDesignTimeController;
    @Autowired
    private IDesignTimePrintController designTimePrintController;
    @Autowired
    private DesignFormGroupLinkDao formGroupLinkDao;
    @Autowired
    private DesignFormDefineService designFormDefineService;
    @Autowired
    private DesignFormGroupDefineDao formGroupDao;
    @Autowired
    private DesignFormDefineService formService;
    @Autowired
    private DesignDataLinkMappingDefineService dataLinkMappingDefineService;
    @Autowired
    private ConfigDao configDao;
    @Autowired
    private MappingFileService mappingFileService;
    @Autowired
    private IDesignConditionalStyleController conditionalStyleController;
    @Autowired
    private RegionSurveyHelper regionSurveyHelper;
    private static final Logger logger = LoggerFactory.getLogger(FormSchemeService.class);
    private static final String[] REGION_BIG_DATA_CODE = new String[]{"REGION_TAB", "REGION_ORDER", "REGION_LT_ROW_STYLES", "REGION_LT_COLUMN_STYLES", "BIG_REGION_CARD"};
    private static final String[] FORM_BIG_DATA_CODE = new String[]{"FORM_DATA", "FILLING_GUIDE", "BIG_SURVEY_DATA", "BIG_SCRIPT_EDITOR"};

    @Transactional(rollbackFor={Exception.class})
    public String createFormScheme(String taskId, String origin, String formSchemeTitle) throws Exception {
        if (origin == null) {
            return this.taskDesignerService.addFormSchemeInTask(taskId, formSchemeTitle);
        }
        return this.copyFormScheme(origin, formSchemeTitle);
    }

    private String getKey() {
        return UUID.randomUUID().toString();
    }

    private String getKey(Map<Enum<ParamType>, Map<String, String>> keyMap, ParamType type, String key) {
        return keyMap.getOrDefault((Object)type, Collections.emptyMap()).getOrDefault(key, null);
    }

    private void putKey(Map<Enum<ParamType>, Map<String, String>> keyMap, ParamType type, String origin, String target) {
        keyMap.compute(type, (k, v) -> {
            if (v == null) {
                v = new HashMap<String, String>();
            }
            v.put(origin, target);
            return v;
        });
    }

    private String copyFormScheme(String originKey, String targetTitle) {
        String targetKey = this.getKey();
        HashMap<Enum<ParamType>, Map<String, String>> keyMap = new HashMap<Enum<ParamType>, Map<String, String>>();
        try {
            this.copyFormSchemeDefine(originKey, targetKey, targetTitle);
            this.copyAnalysisSchemeParam(originKey, targetKey);
            this.copyTaskLink(originKey, targetKey);
            this.copyReport(originKey, targetKey);
            this.copyFormulaVal(originKey, targetKey);
            this.copyFormGroup(originKey, targetKey, keyMap);
            this.copyForm(originKey, targetKey, keyMap);
            this.copyFormLink(keyMap);
            Map<String, DesignRegionSettingDefine> regionToSetMap = this.copyRegionSetting(keyMap);
            this.copyDataRegion(keyMap);
            this.copyDataLink(keyMap, regionToSetMap);
            this.copyConditionalStyle(keyMap);
            this.copyLinkMapping(keyMap);
            this.copyFormulaScheme(originKey, targetKey, keyMap);
            this.copyFormula(keyMap);
            this.copyFormulaCondition(keyMap);
            this.copyPrintScheme(originKey, targetKey, keyMap);
            this.copyPrintTemplate(keyMap);
            this.copyMapConfig(originKey, targetKey, keyMap);
        }
        catch (Exception e) {
            throw new RuntimeException("\u521b\u5efa\u62a5\u8868\u65b9\u6848\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
        return targetKey;
    }

    private void copyConditionalStyle(Map<Enum<ParamType>, Map<String, String>> keyMap) throws JQException {
        Map orDefault = keyMap.getOrDefault((Object)ParamType.FORM, Collections.emptyMap());
        ArrayList list = new ArrayList(orDefault.size());
        for (Map.Entry entry : orDefault.entrySet()) {
            List styleList = this.conditionalStyleController.getAllCSInForm((String)entry.getKey());
            if (CollectionUtils.isEmpty(styleList)) continue;
            list.addAll(styleList);
            for (DesignConditionalStyle style : styleList) {
                style.setKey(this.getKey());
                style.setFormKey((String)entry.getValue());
                style.setLinkKey(this.getKey(keyMap, ParamType.DATA_LINK, style.getLinkKey()));
            }
        }
        this.conditionalStyleController.insertCS(list);
    }

    private void copyMapConfig(String originKey, String targetKey, Map<Enum<ParamType>, Map<String, String>> keyMap) {
        List singleMappingConfigs = this.configDao.getConfigByScheme(originKey);
        if (singleMappingConfigs == null) {
            return;
        }
        for (ISingleMappingConfig config : singleMappingConfigs) {
            String key = this.getKey();
            this.putKey(keyMap, ParamType.SINGLE_MAPPING, config.getMappingConfigKey(), key);
            config.setSchemeKey(targetKey);
            config.setMappingConfigKey(key);
            this.configDao.insert(config);
        }
        List singleFileInfos = this.mappingFileService.queryFileInfoInScheme(originKey);
        for (SingleFileInfo info : singleFileInfos) {
            info.setKey(this.getKey(keyMap, ParamType.SINGLE_MAPPING, info.getKey()));
            info.setFormulaKey(this.getKey(keyMap, ParamType.FORMULA, info.getFormulaKey()));
        }
        this.mappingFileService.insertMappingFileInfo(singleFileInfos);
    }

    private Map<String, DesignRegionSettingDefine> copyRegionSetting(Map<Enum<ParamType>, Map<String, String>> keyMap) throws Exception {
        HashMap<String, DesignRegionSettingDefine> regionToSetMap = new HashMap<String, DesignRegionSettingDefine>();
        Map orDefault = keyMap.getOrDefault((Object)ParamType.FORM, Collections.emptyMap());
        ArrayList regionSettingDefines = new ArrayList(orDefault.size());
        ArrayList bigDatas = new ArrayList(orDefault.size());
        for (Map.Entry entry : orDefault.entrySet()) {
            List regions = this.designTimeViewController.getAllRegionsInForm((String)entry.getKey());
            List regionSettingKeys = regions.stream().map(DataRegionDefine::getRegionSettingKey).filter(Objects::nonNull).collect(Collectors.toList());
            Map<String, DesignDataRegionDefine> regionSettingKeyToRegionMap = regions.stream().filter(a -> StringUtils.hasText(a.getRegionSettingKey())).collect(Collectors.toMap(DataRegionDefine::getRegionSettingKey, a -> a, (k1, k2) -> k1));
            List settingDefines = this.regionSettingDefineDao.listByKeys(regionSettingKeys);
            if (settingDefines.isEmpty()) continue;
            for (DesignRegionSettingDefine regionSetting : settingDefines) {
                try {
                    byte[] bigData = this.formService.getBigData(regionSetting.getKey(), "BIG_REGION_SURVEY");
                    if (null == bigData) continue;
                    String regionSurvey = DesignFormDefineBigDataUtil.bytesToString((byte[])bigData);
                    DesignDataRegionDefine designDataRegionDefine = regionSettingKeyToRegionMap.get(regionSetting.getKey());
                    String regionKey = designDataRegionDefine.getKey();
                    regionSetting.setRegionSurvey(regionSurvey);
                    regionToSetMap.put(regionKey, regionSetting);
                }
                catch (Exception e) {
                    logger.error("\u590d\u5236\u62a5\u8868\u65b9\u6848\uff0cregionSurvey\u67e5\u8be2\u5931\u8d25\uff0c\u5176regionSettingKey\u662f\uff1a" + regionSetting.getKey(), e);
                }
            }
            regionSettingDefines.addAll(settingDefines);
            for (DesignRegionSettingDefine settingDefine : settingDefines) {
                String oSettingKey = settingDefine.getKey();
                String nSettingKey = this.getKey();
                settingDefine.setKey(nSettingKey);
                this.putKey(keyMap, ParamType.REGION_SETTING, oSettingKey, nSettingKey);
            }
        }
        Map settingMap = keyMap.getOrDefault((Object)ParamType.REGION_SETTING, Collections.emptyMap());
        for (Map.Entry stringEntry : settingMap.entrySet()) {
            for (String s : REGION_BIG_DATA_CODE) {
                List datas = this.bigDataService.getBigDatas((String)stringEntry.getKey(), s);
                datas.forEach(v -> v.setKey((String)stringEntry.getValue()));
                bigDatas.addAll(datas);
            }
        }
        this.regionSettingDefineDao.insert((Object[])regionSettingDefines.toArray(new DesignRegionSettingDefine[0]));
        this.bigDataService.insertBigDatas(bigDatas);
        return regionToSetMap;
    }

    private void copyFormLink(Map<Enum<ParamType>, Map<String, String>> keyMap) throws Exception {
        Map orDefault = keyMap.getOrDefault((Object)ParamType.FORM, Collections.emptyMap());
        ArrayList formGroupLinks = new ArrayList(orDefault.size());
        for (Map.Entry entry : orDefault.entrySet()) {
            List groupLinks = this.formGroupLinkDao.getFormGroupLinksByFormId((String)entry.getKey());
            formGroupLinks.addAll(groupLinks);
            for (DesignFormGroupLink formGroupLink : groupLinks) {
                formGroupLink.setFormKey((String)entry.getValue());
                formGroupLink.setGroupKey(this.getKey(keyMap, ParamType.FORM_GROUP, formGroupLink.getGroupKey()));
            }
        }
        this.formGroupLinkDao.insert((Object[])formGroupLinks.toArray(new DesignFormGroupLink[0]));
    }

    private void copyDataRegion(Map<Enum<ParamType>, Map<String, String>> keyMap) {
        Map orDefault = keyMap.getOrDefault((Object)ParamType.FORM, Collections.emptyMap());
        ArrayList regionDefines = new ArrayList(orDefault.size());
        for (Map.Entry entry : orDefault.entrySet()) {
            List regions = this.designTimeViewController.getAllRegionsInForm((String)entry.getKey());
            regionDefines.addAll(regions);
            for (DesignDataRegionDefine region : regions) {
                String oRegionKey = region.getKey();
                String nRegionKey = this.getKey();
                this.putKey(keyMap, ParamType.REGION, oRegionKey, nRegionKey);
                region.setKey(nRegionKey);
                region.setFormKey((String)entry.getValue());
                region.setRegionSettingKey(this.getKey(keyMap, ParamType.REGION_SETTING, region.getRegionSettingKey()));
            }
        }
        this.designTimeViewController.insertDataRegionDefines(regionDefines.toArray(new DesignDataRegionDefine[0]));
    }

    private void copyDataLink(Map<Enum<ParamType>, Map<String, String>> keyMap, Map<String, DesignRegionSettingDefine> regionToSetMap) throws Exception {
        Map orDefault = keyMap.getOrDefault((Object)ParamType.FORM, Collections.emptyMap());
        ArrayList<DesignDataLinkDefine> dataLinkDefines = new ArrayList<DesignDataLinkDefine>();
        for (Map.Entry entry : orDefault.entrySet()) {
            List dataLinks = this.designTimeViewController.getAllLinksInForm((String)entry.getKey());
            dataLinkDefines.addAll(dataLinks);
            Map<String, List<DesignDataLinkDefine>> regionToLinkMaps = dataLinks.stream().collect(Collectors.groupingBy(DataLinkDefine::getRegionKey));
            for (Map.Entry<String, List<DesignDataLinkDefine>> linkEntry : regionToLinkMaps.entrySet()) {
                String regionKey = linkEntry.getKey();
                HashMap<String, DesignDataLinkDefine> srcToDesLinks = new HashMap<String, DesignDataLinkDefine>();
                List<DesignDataLinkDefine> linkForRegions = linkEntry.getValue();
                for (DesignDataLinkDefine dataLink : linkForRegions) {
                    String oKey = dataLink.getKey();
                    String nKey = this.getKey();
                    dataLink.setKey(nKey);
                    srcToDesLinks.put(oKey, dataLink);
                    this.putKey(keyMap, ParamType.DATA_LINK, oKey, nKey);
                    dataLink.setRegionKey(this.getKey(keyMap, ParamType.REGION, dataLink.getRegionKey()));
                }
                DesignRegionSettingDefine regionSettingDefine = regionToSetMap.get(regionKey);
                if (regionSettingDefine == null || !StringUtils.hasText(regionSettingDefine.getRegionSurvey())) continue;
                String newSurvey = this.regionSurveyHelper.formCopyRegionSurvey3(regionSettingDefine.getRegionSurvey(), srcToDesLinks);
                byte[] bytes = DesignFormDefineBigDataUtil.StringToBytes((String)newSurvey);
                this.formService.updateBigDataDefine(regionSettingDefine.getKey(), "BIG_REGION_SURVEY", 1, bytes);
            }
        }
        this.setEnumLinkageData(dataLinkDefines, keyMap);
        this.designTimeViewController.insertDataLinkDefines(dataLinkDefines.toArray(new DesignDataLinkDefine[0]));
    }

    private void setEnumLinkageData(List<DesignDataLinkDefine> dataLinkDefines, Map<Enum<ParamType>, Map<String, String>> keyMap) {
        if (dataLinkDefines.isEmpty()) {
            return;
        }
        Map linkMap = keyMap.getOrDefault((Object)ParamType.DATA_LINK, Collections.emptyMap());
        for (DesignDataLinkDefine dataLink : dataLinkDefines) {
            List list = (List)JacksonUtils.jsonToObject((String)dataLink.getEnumLinkage(), (TypeReference)new TypeReference<List<Map<String, String>>>(){});
            if (list == null) continue;
            list.forEach(map -> new HashSet(map.keySet()).forEach(k -> {
                String v = (String)map.get(k);
                if (linkMap.containsKey(v)) {
                    map.put(k, linkMap.get(v));
                }
            }));
            dataLink.setEnumLinkage(JacksonUtils.objectToJson((Object)list));
        }
    }

    private void copyForm(String originKey, String targetKey, Map<Enum<ParamType>, Map<String, String>> keyMap) throws Exception {
        List designFormDefines = this.designFormDefineService.listFormByFormScheme(originKey);
        if (designFormDefines.isEmpty()) {
            return;
        }
        ArrayList<DesignBigDataTable> bigDatas = new ArrayList<DesignBigDataTable>();
        for (DesignFormDefine designFormDefine : designFormDefines) {
            String nKey = this.getKey();
            String oKey = designFormDefine.getKey();
            this.putKey(keyMap, ParamType.FORM, oKey, nKey);
            designFormDefine.setKey(nKey);
            designFormDefine.setFormScheme(targetKey);
            for (String s : FORM_BIG_DATA_CODE) {
                for (DesignBigDataTable bigData : this.bigDataService.getBigDatas(oKey, s)) {
                    bigData.setKey(nKey);
                    bigDatas.add(bigData);
                }
            }
        }
        this.designFormDefineService.insertFormDefines(designFormDefines.toArray(new DesignFormDefine[0]));
        this.bigDataService.insertBigDatas(bigDatas, false);
    }

    private void copyLinkMapping(Map<Enum<ParamType>, Map<String, String>> keyMap) throws Exception {
        Map orDefault = keyMap.getOrDefault((Object)ParamType.FORM, Collections.emptyMap());
        ArrayList dataLinkMappings = new ArrayList(orDefault.size());
        for (Map.Entry entry : orDefault.entrySet()) {
            List dataLinkMapping = this.dataLinkMappingDefineService.getDataLinkMapping((String)entry.getKey());
            dataLinkMappings.addAll(dataLinkMapping);
            for (DesignDataLinkMappingDefine define : dataLinkMapping) {
                define.setId(this.getKey());
                define.setRightDataLinkKey(this.getKey(keyMap, ParamType.DATA_LINK, define.getRightDataLinkKey()));
                define.setLeftDataLinkKey(this.getKey(keyMap, ParamType.DATA_LINK, define.getLeftDataLinkKey()));
                define.setFormKey((String)entry.getValue());
            }
        }
        this.dataLinkMappingDefineService.insertDataLinkMappingDefine(dataLinkMappings.toArray(new DesignDataLinkMappingDefine[0]));
    }

    private void copyFormGroup(String originKey, String targetKey, Map<Enum<ParamType>, Map<String, String>> keyMap) throws Exception {
        List designFormGroupDefines = this.formGroupDao.queryDefinesByFormScheme(originKey);
        if (CollectionUtils.isEmpty(designFormGroupDefines)) {
            return;
        }
        HashMap<String, String> map = new HashMap<String, String>();
        keyMap.put(ParamType.FORM_GROUP, map);
        for (DesignFormGroupDefine define : designFormGroupDefines) {
            String key = UUID.randomUUID().toString();
            map.put(define.getKey(), key);
            define.setKey(key);
            define.setFormSchemeKey(targetKey);
        }
        this.formGroupDao.insert((Object[])designFormGroupDefines.toArray(new DesignFormGroupDefine[0]));
    }

    private void copyPrintScheme(String originKey, String targetKey, Map<Enum<ParamType>, Map<String, String>> keyMap) throws Exception {
        List schemeDefines = this.printDesignTimeController.getAllPrintSchemeByFormScheme(originKey);
        for (DesignPrintTemplateSchemeDefine schemeDefine : schemeDefines) {
            String oSchemeKey = schemeDefine.getKey();
            String nSchemeKey = this.getKey();
            this.putKey(keyMap, ParamType.PRINT_SCHEME, oSchemeKey, nSchemeKey);
            schemeDefine.setKey(nSchemeKey);
            schemeDefine.setFormSchemeKey(targetKey);
            this.printDesignTimeController.insertPrintTemplateSchemeDefine(schemeDefine);
            List srcComTemDefines = this.designTimePrintController.listPrintComTemByScheme(oSchemeKey);
            ArrayList<DesignPrintComTemDefine> comTemDefines = new ArrayList<DesignPrintComTemDefine>();
            for (DesignPrintComTemDefine srcComTemDefine : srcComTemDefines) {
                comTemDefines.add(this.designTimePrintController.copyPrintComTem(srcComTemDefine, nSchemeKey));
            }
            this.designTimePrintController.insertPrintComTem(comTemDefines);
        }
    }

    private void copyPrintTemplate(Map<Enum<ParamType>, Map<String, String>> keyMap) throws Exception {
        ArrayList templates = new ArrayList();
        Map orDefault = keyMap.getOrDefault((Object)ParamType.PRINT_SCHEME, Collections.emptyMap());
        for (Map.Entry entry : orDefault.entrySet()) {
            List printTemplateDefines = this.printDesignTimeController.getAllPrintTemplateInScheme((String)entry.getKey());
            if (null == printTemplateDefines) continue;
            templates.addAll(printTemplateDefines);
            for (DesignPrintTemplateDefine template : printTemplateDefines) {
                template.setKey(this.getKey());
                template.setPrintSchemeKey((String)entry.getValue());
                template.setFormKey(this.getKey(keyMap, ParamType.FORM, template.getFormKey()));
            }
        }
        this.printDesignTimeController.insertTemplates(templates.toArray(new DesignPrintTemplateDefine[0]));
    }

    private void copyFormulaScheme(String originKey, String targetKey, Map<Enum<ParamType>, Map<String, String>> keyMap) {
        List schemeDefines = this.formulaDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(originKey);
        for (DesignFormulaSchemeDefine schemeDefine : schemeDefines) {
            String oSchemeKey = schemeDefine.getKey();
            String nSchemeKey = this.getKey();
            this.putKey(keyMap, ParamType.FORMULA_SCHEME, oSchemeKey, nSchemeKey);
            schemeDefine.setKey(nSchemeKey);
            schemeDefine.setFormSchemeKey(targetKey);
            this.formulaDesignTimeController.insertFormulaSchemeDefine(schemeDefine);
        }
    }

    private void copyFormulaCondition(Map<Enum<ParamType>, Map<String, String>> keyMap) {
        Map orDefault = keyMap.getOrDefault((Object)ParamType.FORMULA_SCHEME, Collections.emptyMap());
        ArrayList conditionLinks = new ArrayList();
        for (Map.Entry entry : orDefault.entrySet()) {
            List links = this.formulaDesignTimeController.listConditionLinkByScheme((String)entry.getKey());
            conditionLinks.addAll(links);
            for (DesignFormulaConditionLink link : links) {
                link.setFormulaKey(this.getKey(keyMap, ParamType.FORMULA, link.getFormulaKey()));
                link.setFormulaSchemeKey(this.getKey(keyMap, ParamType.FORMULA_SCHEME, link.getFormulaSchemeKey()));
            }
        }
        this.formulaDesignTimeController.insertFormulaConditionLinks(conditionLinks);
    }

    private void copyFormula(Map<Enum<ParamType>, Map<String, String>> keyMap) throws JQException {
        ArrayList formulaDefines = new ArrayList();
        Map orDefault = keyMap.getOrDefault((Object)ParamType.FORMULA_SCHEME, Collections.emptyMap());
        for (Map.Entry entry : orDefault.entrySet()) {
            List formulas = this.formulaDesignTimeController.getAllFormulasInScheme((String)entry.getKey());
            formulaDefines.addAll(formulas);
            for (DesignFormulaDefine formula : formulas) {
                String oFormulaKey = formula.getKey();
                String nFormulaKey = this.getKey();
                this.putKey(keyMap, ParamType.FORMULA, oFormulaKey, nFormulaKey);
                formula.setKey(nFormulaKey);
                formula.setFormulaSchemeKey((String)entry.getValue());
                if (null == formula.getFormKey()) continue;
                formula.setFormKey(this.getKey(keyMap, ParamType.FORM, formula.getFormKey()));
            }
        }
        this.formulaDesignTimeController.insertFormulaDefines(formulaDefines.toArray(new DesignFormulaDefine[0]));
    }

    private void copyFormulaVal(String originKey, String targetKey) throws JQException {
        List defines = this.formulaDesignTimeController.queryAllFormulaVariable(originKey);
        if (CollectionUtils.isEmpty(defines)) {
            return;
        }
        for (FormulaVariDefine v : defines) {
            v.setKey(this.getKey());
            v.setFormSchemeKey(targetKey);
            this.formulaDesignTimeController.addFormulaVariable(v);
        }
    }

    private void copyReport(String originKey, String targetKey) throws JQException {
        Map tagMap;
        TransformReportDefine transformReportDefine = this.designTimeViewController.exportReport(originKey);
        if (transformReportDefine == null) {
            return;
        }
        List templateDefines = transformReportDefine.getDesignReportTemplateDefines();
        List designReportTagDefines = transformReportDefine.getDesignReportTagDefines();
        Map<Object, Object> map = tagMap = CollectionUtils.isEmpty(designReportTagDefines) ? Collections.emptyMap() : designReportTagDefines.stream().collect(Collectors.groupingBy(ReportTagDefine::getKey));
        if (CollectionUtils.isEmpty(templateDefines)) {
            return;
        }
        for (TransportReportTemplateDefineImpl templateDefine : templateDefines) {
            String key = this.getKey();
            List tags = (List)tagMap.get(templateDefine.getKey());
            if (!CollectionUtils.isEmpty(tags)) {
                tags.forEach(v -> v.setRptKey(key));
            }
            templateDefine.setKey(key);
            templateDefine.setFormSchemeKey(targetKey);
        }
        this.designTimeViewController.importReport(transformReportDefine, Boolean.valueOf(true));
    }

    private void copyTaskLink(String originKey, String targetKey) {
        List designTaskLinkDefines = this.designTimeViewController.queryLinksByCurrentFormScheme(originKey);
        if (CollectionUtils.isEmpty(designTaskLinkDefines)) {
            return;
        }
        for (DesignTaskLinkDefine l : designTaskLinkDefines) {
            l.setKey(this.getKey());
            l.setCurrentFormSchemeKey(targetKey);
            List orgMappingRules = l.getOrgMappingRules();
            for (TaskLinkOrgMappingRule mappingRule : orgMappingRules) {
                mappingRule.setTaskLinkKey(l.getKey());
            }
        }
        this.designTimeViewController.insertTaskLinkDefines(designTaskLinkDefines);
    }

    private void copyAnalysisSchemeParam(String originKey, String targetKey) throws Exception {
        DesignAnalysisSchemeParamDefine designAnalysisSchemeParamDefine = this.designTimeViewController.queryAnalysisSchemeParamDefine(originKey);
        if (designAnalysisSchemeParamDefine == null) {
            return;
        }
        designAnalysisSchemeParamDefine.setSrcFormSchemeKey(targetKey);
        this.designTimeViewController.updataAnalysisSchemeParamDefine(targetKey, designAnalysisSchemeParamDefine);
    }

    private void copyFormSchemeDefine(String originKey, String targetKey, String targetTitle) {
        DesignFormSchemeDefine schemeDefine = this.designTimeViewController.queryFormSchemeDefine(originKey);
        schemeDefine.setKey(targetKey);
        schemeDefine.setTitle(targetTitle);
        schemeDefine.setFormSchemeCode(OrderGenerator.newOrder());
        schemeDefine.setOrder(OrderGenerator.newOrder());
        if (StringUtils.hasLength(schemeDefine.getTaskPrefix())) {
            schemeDefine.setTaskPrefix(null);
        }
        this.designTimeViewController.insertFormSchemeDefine(schemeDefine);
    }

    private static enum ParamType {
        FORM_SCHEME,
        FORM,
        FORM_GROUP,
        FORMULA_SCHEME,
        FORMULA,
        FORMULA_VAL,
        TASK_LINK,
        ANALYSIS_SCHEME,
        PRINT_SCHEME,
        PRINT_TEMPLATE,
        REPORT,
        REGION,
        DATA_LINK,
        REGION_SETTING,
        SINGLE_MAPPING;

    }
}

