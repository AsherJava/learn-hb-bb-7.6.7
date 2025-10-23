/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.conditionalstyle.controller.IDesignConditionalStyleController
 *  com.jiuqi.nr.conditionalstyle.facade.DesignConditionalStyle
 *  com.jiuqi.nr.definition.api.IDesignTimeFormulaController
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.api.IDesignTimeReportController
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
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
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskLinkDefine
 *  com.jiuqi.nr.definition.facade.FormulaVariDefine
 *  com.jiuqi.nr.definition.facade.ReportTagDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule
 *  com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink
 *  com.jiuqi.nr.definition.facade.report.TransformReportDefine
 *  com.jiuqi.nr.definition.facade.report.TransportReportTemplateDefineImpl
 *  com.jiuqi.nr.definition.internal.controller2.DesignTimeViewController
 *  com.jiuqi.nr.definition.internal.dao.DesignRegionSettingDefineDao
 *  com.jiuqi.nr.definition.internal.impl.DesignBigDataTable
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil
 *  com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink
 *  com.jiuqi.nr.definition.internal.service.DesignBigDataService
 *  com.jiuqi.nr.definition.internal.service.DesignDataLinkMappingDefineService
 *  com.jiuqi.nr.definition.internal.service.DesignFormDefineService
 *  com.jiuqi.nr.definition.paramcheck.IDesignParamCheckService
 *  com.jiuqi.nr.formula.service.IFormulaSchemeService
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  com.jiuqi.nr.print.service.IPrintSchemeService
 *  com.jiuqi.nr.task.form.service.IFormService
 *  com.jiuqi.util.OrderGenerator
 *  com.jiuqi.xlib.runtime.Assert
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.task.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.conditionalstyle.controller.IDesignConditionalStyleController;
import com.jiuqi.nr.conditionalstyle.facade.DesignConditionalStyle;
import com.jiuqi.nr.definition.api.IDesignTimeFormulaController;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.api.IDesignTimeReportController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
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
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.DesignTaskLinkDefine;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.facade.ReportTagDefine;
import com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink;
import com.jiuqi.nr.definition.facade.report.TransformReportDefine;
import com.jiuqi.nr.definition.facade.report.TransportReportTemplateDefineImpl;
import com.jiuqi.nr.definition.internal.controller2.DesignTimeViewController;
import com.jiuqi.nr.definition.internal.dao.DesignRegionSettingDefineDao;
import com.jiuqi.nr.definition.internal.impl.DesignBigDataTable;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nr.definition.internal.service.DesignBigDataService;
import com.jiuqi.nr.definition.internal.service.DesignDataLinkMappingDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormDefineService;
import com.jiuqi.nr.definition.paramcheck.IDesignParamCheckService;
import com.jiuqi.nr.formula.service.IFormulaSchemeService;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.nr.print.service.IPrintSchemeService;
import com.jiuqi.nr.task.auth.IAuthCheckService;
import com.jiuqi.nr.task.dto.FormSchemeDTO;
import com.jiuqi.nr.task.form.service.IFormService;
import com.jiuqi.nr.task.internal.async.DeleteFormSchemeExecutor;
import com.jiuqi.nr.task.service.IFormSchemeReleaseService;
import com.jiuqi.nr.task.service.IFormSchemeService;
import com.jiuqi.nr.task.service.IValidateTimeService;
import com.jiuqi.nr.task.service.help.RegionSurveyHelper;
import com.jiuqi.nr.task.tools.ParamCheck;
import com.jiuqi.nr.task.web.param.SchemeQueryParam;
import com.jiuqi.nr.task.web.vo.DefaultSchemeVO;
import com.jiuqi.nr.task.web.vo.FormSchemeItemVO;
import com.jiuqi.nr.task.web.vo.FormSchemeVO;
import com.jiuqi.nr.task.web.vo.TaskItemVO;
import com.jiuqi.nr.task.web.vo.ValidateTimeCheckResultVO;
import com.jiuqi.nr.task.web.vo.ValidateTimeVO;
import com.jiuqi.util.OrderGenerator;
import com.jiuqi.xlib.runtime.Assert;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class FormSchemeServiceImpl
implements IFormSchemeService {
    private static final Logger logger = LoggerFactory.getLogger(FormSchemeServiceImpl.class);
    @Autowired
    private DesignTimeViewController designTimeViewController;
    @Autowired
    private IAuthCheckService taskAuthCheckService;
    @Autowired
    private IFormService formService;
    @Autowired
    private DesignFormDefineService formDefineService;
    @Autowired
    private IPrintSchemeService iPrintSchemeService;
    @Autowired
    private IFormulaSchemeService formulaSchemeService;
    @Autowired
    private DefinitionAuthorityProvider definitionAuthority;
    @Autowired
    private IValidateTimeService validateTimeService;
    @Autowired
    private IFormSchemeReleaseService releaseService;
    @Autowired
    private IDesignParamCheckService paramCheckService;
    @Autowired
    private IDesignTimeReportController reportController;
    @Autowired
    private IDesignTimeFormulaController formulaController;
    @Autowired
    private DesignBigDataService bigDataService;
    @Autowired
    private DesignRegionSettingDefineDao regionSettingDefineDao;
    @Autowired
    private RegionSurveyHelper regionSurveyHelper;
    @Autowired
    private IDesignConditionalStyleController conditionalStyleController;
    @Autowired
    private DesignDataLinkMappingDefineService dataLinkMappingDefineService;
    @Autowired
    private IDesignTimePrintController printController;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    private final ParamCheck paramCheck = new ParamCheck();
    private static final String[] REGION_BIG_DATA_CODE = new String[]{"REGION_TAB", "REGION_ORDER", "REGION_LT_ROW_STYLES", "REGION_LT_COLUMN_STYLES", "BIG_REGION_CARD"};
    private static final String[] FORM_BIG_DATA_CODE = new String[]{"FORM_DATA", "FILLING_GUIDE", "BIG_SURVEY_DATA", "BIG_SCRIPT_EDITOR"};
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public String insertDefaultFormScheme(String taskKey) {
        Assert.notNull((Object)taskKey, (String)"task must not be null");
        DesignFormSchemeDefine newFormScheme = this.designTimeViewController.initFormScheme();
        newFormScheme.setTaskKey(taskKey);
        newFormScheme.setTitle("\u9ed8\u8ba4\u62a5\u8868\u65b9\u6848");
        this.designTimeViewController.insertFormScheme(newFormScheme);
        this.insertDefaultObj(taskKey, newFormScheme.getKey());
        return newFormScheme.getKey();
    }

    @Override
    public FormSchemeVO init(String taskKey) {
        DesignFormSchemeDefine formSchemeDefine = this.designTimeViewController.initFormScheme();
        formSchemeDefine.setTaskKey(taskKey);
        return new FormSchemeVO(formSchemeDefine);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public FormSchemeItemVO insert(FormSchemeVO formScheme) {
        DesignFormSchemeDefine newFormScheme;
        Assert.notNull((Object)formScheme.getTitle(), (String)"title must not be null");
        Assert.notNull((Object)formScheme.getCode(), (String)"code must not be null");
        Assert.notNull((Object)formScheme.getTaskKey(), (String)"taskKey must not be null");
        this.taskAuthCheckService.checkTaskAuth(formScheme.getTaskKey());
        this.paramCheck.formSchemeParamCheck(formScheme.getTitle());
        if (StringUtils.hasText(formScheme.getCopyFormScheme())) {
            String newFormSchemeKey = this.copyFromScheme(formScheme);
            newFormScheme = this.designTimeViewController.getFormScheme(newFormSchemeKey);
        } else {
            newFormScheme = this.designTimeViewController.initFormScheme();
            newFormScheme.setTaskKey(formScheme.getTaskKey());
            newFormScheme.setTitle(formScheme.getTitle());
            newFormScheme.setFormSchemeCode(formScheme.getCode());
            this.insertDefaultObj(formScheme.getTaskKey(), newFormScheme.getKey());
            this.designTimeViewController.insertFormScheme(newFormScheme);
        }
        this.definitionAuthority.grantAllPrivilegesToFormScheme(newFormScheme.getKey());
        return this.getFormSchemeItemVO(newFormScheme);
    }

    private void insertDefaultObj(String taskKey, String formSchemeKey) {
        String defaultFormGroup = this.formService.insertDefaultGroup(formSchemeKey);
        this.formService.insertDefaultForm(formSchemeKey, defaultFormGroup);
        this.formulaSchemeService.insertDefaultFormulaScheme(formSchemeKey);
        this.iPrintSchemeService.initDefaultPrintScheme(taskKey, formSchemeKey);
    }

    private String getKey() {
        return UUID.randomUUID().toString();
    }

    private String getKey(Map<Enum<ParamType>, Map<String, String>> keyMap, ParamType type, String key) {
        return keyMap.getOrDefault((Object)type, Collections.emptyMap()).getOrDefault(key, null);
    }

    private String copyFromScheme(FormSchemeVO formScheme) {
        String targetKey = this.getKey();
        String originKey = formScheme.getCopyFormScheme();
        HashMap<Enum<ParamType>, Map<String, String>> keyMap = new HashMap<Enum<ParamType>, Map<String, String>>();
        try {
            this.copyFormSchemeDefine(originKey, targetKey, formScheme.getTitle(), formScheme.getCode());
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
        }
        catch (Exception e) {
            throw new RuntimeException("\u521b\u5efa\u62a5\u8868\u65b9\u6848\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
        return targetKey;
    }

    private void copyFormSchemeDefine(String originKey, String targetKey, String targetTitle, String code) {
        DesignFormSchemeDefine schemeDefine = this.designTimeViewController.getFormScheme(originKey);
        schemeDefine.setKey(targetKey);
        schemeDefine.setTitle(targetTitle);
        schemeDefine.setFormSchemeCode(code);
        schemeDefine.setOrder(OrderGenerator.newOrder());
        if (StringUtils.hasLength(schemeDefine.getTaskPrefix())) {
            schemeDefine.setTaskPrefix(null);
        }
        this.designTimeViewController.insertFormScheme(schemeDefine);
    }

    private void copyAnalysisSchemeParam(String originKey, String targetKey) {
        DesignAnalysisSchemeParamDefine designAnalysisSchemeParamDefine = this.designTimeViewController.getAnalysisSchemeParamDefine(originKey);
        if (designAnalysisSchemeParamDefine == null) {
            return;
        }
        designAnalysisSchemeParamDefine.setSrcFormSchemeKey(targetKey);
        this.designTimeViewController.updateAnalysisSchemeParamDefine(targetKey, designAnalysisSchemeParamDefine);
    }

    private void copyTaskLink(String originKey, String targetKey) {
        List designTaskLinkDefines = this.designTimeViewController.listTaskLinkByFormScheme(originKey);
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
        this.designTimeViewController.insertTaskLink(designTaskLinkDefines.toArray(new DesignTaskLinkDefine[0]));
    }

    private void copyReport(String originKey, String targetKey) {
        Map tagMap;
        TransformReportDefine transformReportDefine = this.reportController.exportReportTemplate(originKey);
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
        this.reportController.importReportTemplate(transformReportDefine, Boolean.valueOf(true));
    }

    private void copyFormulaVal(String originKey, String targetKey) {
        List defines = this.formulaController.listFormulaVariByFormScheme(originKey);
        if (CollectionUtils.isEmpty(defines)) {
            return;
        }
        for (FormulaVariDefine v : defines) {
            v.setKey(this.getKey());
            v.setFormSchemeKey(targetKey);
            this.formulaController.insertFormulaVariable(v);
        }
    }

    private void copyFormGroup(String originKey, String targetKey, Map<Enum<ParamType>, Map<String, String>> keyMap) throws Exception {
        List designFormGroupDefines = this.designTimeViewController.listFormGroupByFormScheme(originKey);
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
        this.designTimeViewController.insertFormGroups(designFormGroupDefines.toArray(new DesignFormGroupDefine[0]));
    }

    private void copyForm(String originKey, String targetKey, Map<Enum<ParamType>, Map<String, String>> keyMap) throws Exception {
        List designFormDefines = this.formDefineService.listFormByFormScheme(originKey);
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
            designFormDefine.setUpdateUser(NpContextHolder.getContext().getUserName());
            for (String s : FORM_BIG_DATA_CODE) {
                for (DesignBigDataTable bigData : this.bigDataService.getBigDatas(oKey, s)) {
                    bigData.setKey(nKey);
                    bigDatas.add(bigData);
                }
            }
        }
        this.designTimeViewController.insertForms(designFormDefines.toArray(new DesignFormDefine[0]));
        this.bigDataService.insertBigDatas(bigDatas, false);
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

    private void copyFormLink(Map<Enum<ParamType>, Map<String, String>> keyMap) {
        Map orDefault = keyMap.getOrDefault((Object)ParamType.FORM, Collections.emptyMap());
        ArrayList formGroupLinks = new ArrayList(orDefault.size());
        for (Map.Entry entry : orDefault.entrySet()) {
            List groupLinks = this.designTimeViewController.listFormGroupLinkByForm((String)entry.getKey());
            formGroupLinks.addAll(groupLinks);
            for (DesignFormGroupLink formGroupLink : groupLinks) {
                formGroupLink.setFormKey((String)entry.getValue());
                formGroupLink.setGroupKey(this.getKey(keyMap, ParamType.FORM_GROUP, formGroupLink.getGroupKey()));
            }
        }
        this.designTimeViewController.insertFormGroupLink(formGroupLinks.toArray(new DesignFormGroupLink[0]));
    }

    private Map<String, DesignRegionSettingDefine> copyRegionSetting(Map<Enum<ParamType>, Map<String, String>> keyMap) throws Exception {
        HashMap<String, DesignRegionSettingDefine> regionToSetMap = new HashMap<String, DesignRegionSettingDefine>();
        Map orDefault = keyMap.getOrDefault((Object)ParamType.FORM, Collections.emptyMap());
        ArrayList regionSettingDefines = new ArrayList(orDefault.size());
        ArrayList bigDatas = new ArrayList(orDefault.size());
        for (Map.Entry entry : orDefault.entrySet()) {
            List regions = this.designTimeViewController.listDataRegionByForm((String)entry.getKey());
            List regionSettingKeys = regions.stream().map(DataRegionDefine::getRegionSettingKey).filter(Objects::nonNull).collect(Collectors.toList());
            Map<String, DesignDataRegionDefine> regionSettingKeyToRegionMap = regions.stream().filter(a -> StringUtils.hasText(a.getRegionSettingKey())).collect(Collectors.toMap(DataRegionDefine::getRegionSettingKey, a -> a, (k1, k2) -> k1));
            List settingDefines = this.regionSettingDefineDao.listByKeys(regionSettingKeys);
            if (settingDefines.isEmpty()) continue;
            for (DesignRegionSettingDefine regionSetting : settingDefines) {
                try {
                    byte[] bigData = this.bigDataService.getBigData(regionSetting.getKey(), "BIG_REGION_SURVEY");
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

    private void copyDataRegion(Map<Enum<ParamType>, Map<String, String>> keyMap) {
        Map orDefault = keyMap.getOrDefault((Object)ParamType.FORM, Collections.emptyMap());
        ArrayList regionDefines = new ArrayList(orDefault.size());
        for (Map.Entry entry : orDefault.entrySet()) {
            List regions = this.designTimeViewController.listDataRegionByForm((String)entry.getKey());
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
        this.designTimeViewController.insertDataRegion(regionDefines.toArray(new DesignDataRegionDefine[0]));
    }

    private void copyDataLink(Map<Enum<ParamType>, Map<String, String>> keyMap, Map<String, DesignRegionSettingDefine> regionToSetMap) throws Exception {
        Map orDefault = keyMap.getOrDefault((Object)ParamType.FORM, Collections.emptyMap());
        ArrayList<DesignDataLinkDefine> dataLinkDefines = new ArrayList<DesignDataLinkDefine>();
        for (Map.Entry entry : orDefault.entrySet()) {
            List dataLinks = this.designTimeViewController.listDataLinkByForm((String)entry.getKey());
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
                this.formDefineService.updateBigDataDefine(regionSettingDefine.getKey(), "BIG_REGION_SURVEY", 1, bytes);
            }
        }
        this.setEnumLinkageData(dataLinkDefines, keyMap);
        this.designTimeViewController.insertDataLink(dataLinkDefines.toArray(new DesignDataLinkDefine[0]));
        this.setLevelSetting(dataLinkDefines, keyMap);
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

    private void setLevelSetting(List<DesignDataLinkDefine> dataLinkDefines, Map<Enum<ParamType>, Map<String, String>> keyMap) {
        if (dataLinkDefines.isEmpty()) {
            return;
        }
        ArrayList dataRegions = new ArrayList();
        Map orDefault = keyMap.getOrDefault((Object)ParamType.FORM, Collections.emptyMap());
        for (Map.Entry entry : orDefault.entrySet()) {
            List designDataRegionDefines = this.designTimeViewController.listDataRegionByForm((String)entry.getValue());
            dataRegions.addAll(designDataRegionDefines);
        }
        ArrayList<DesignDataRegionDefine> needUpdateList = new ArrayList<DesignDataRegionDefine>();
        for (DesignDataRegionDefine dataRegion : dataRegions) {
            String[] displayLinks;
            if (!StringUtils.hasText(dataRegion.getDisplayLevel())) continue;
            ArrayList<String> newDisplayLinks = new ArrayList<String>();
            for (String displayLink : displayLinks = dataRegion.getDisplayLevel().split(";")) {
                newDisplayLinks.add(this.getKey(keyMap, ParamType.DATA_LINK, displayLink));
            }
            if (!CollectionUtils.isEmpty(newDisplayLinks)) {
                dataRegion.setDisplayLevel(String.join((CharSequence)";", newDisplayLinks));
            } else {
                dataRegion.setDisplayLevel(null);
            }
            needUpdateList.add(dataRegion);
        }
        if (!needUpdateList.isEmpty()) {
            this.designTimeViewController.updateDataRegion(needUpdateList.toArray(new DesignDataRegionDefine[0]));
        }
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

    private void copyFormulaScheme(String originKey, String targetKey, Map<Enum<ParamType>, Map<String, String>> keyMap) {
        List schemeDefines = this.formulaController.listFormulaSchemeByFormScheme(originKey);
        for (DesignFormulaSchemeDefine schemeDefine : schemeDefines) {
            String oSchemeKey = schemeDefine.getKey();
            String nSchemeKey = this.getKey();
            this.putKey(keyMap, ParamType.FORMULA_SCHEME, oSchemeKey, nSchemeKey);
            schemeDefine.setKey(nSchemeKey);
            schemeDefine.setFormSchemeKey(targetKey);
            this.formulaController.insertFormulaScheme(schemeDefine);
        }
    }

    private void copyFormula(Map<Enum<ParamType>, Map<String, String>> keyMap) throws JQException {
        ArrayList formulaDefines = new ArrayList();
        Map orDefault = keyMap.getOrDefault((Object)ParamType.FORMULA_SCHEME, Collections.emptyMap());
        for (Map.Entry entry : orDefault.entrySet()) {
            List formulas = this.formulaController.listFormulaByScheme((String)entry.getKey());
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
        this.formulaController.insertFormula(formulaDefines.toArray(new DesignFormulaDefine[0]));
    }

    private void copyFormulaCondition(Map<Enum<ParamType>, Map<String, String>> keyMap) {
        Map orDefault = keyMap.getOrDefault((Object)ParamType.FORMULA_SCHEME, Collections.emptyMap());
        ArrayList conditionLinks = new ArrayList();
        for (Map.Entry entry : orDefault.entrySet()) {
            List links = this.formulaController.listFormulaConditionLinkByScheme((String)entry.getKey());
            conditionLinks.addAll(links);
            for (DesignFormulaConditionLink link : links) {
                link.setFormulaKey(this.getKey(keyMap, ParamType.FORMULA, link.getFormulaKey()));
                link.setFormulaSchemeKey(this.getKey(keyMap, ParamType.FORMULA_SCHEME, link.getFormulaSchemeKey()));
            }
        }
        this.formulaController.insertFormulaConditionLinks(conditionLinks);
    }

    private void copyPrintScheme(String originKey, String targetKey, Map<Enum<ParamType>, Map<String, String>> keyMap) throws Exception {
        List schemeDefines = this.printController.listPrintTemplateSchemeByFormScheme(originKey);
        for (DesignPrintTemplateSchemeDefine schemeDefine : schemeDefines) {
            String oSchemeKey = schemeDefine.getKey();
            String nSchemeKey = this.getKey();
            this.putKey(keyMap, ParamType.PRINT_SCHEME, oSchemeKey, nSchemeKey);
            DesignPrintTemplateSchemeDefine define = this.printController.copyPrintTemplateScheme(schemeDefine, schemeDefine.getTaskKey(), targetKey);
            define.setKey(nSchemeKey);
            define.setFormSchemeKey(targetKey);
            this.printController.insertPrintTemplateScheme(define);
            List srcComTemDefines = this.printController.listPrintComTemByScheme(oSchemeKey);
            ArrayList<DesignPrintComTemDefine> comTemDefines = new ArrayList<DesignPrintComTemDefine>();
            for (DesignPrintComTemDefine srcComTemDefine : srcComTemDefines) {
                comTemDefines.add(this.printController.copyPrintComTem(srcComTemDefine, nSchemeKey));
            }
            this.printController.insertPrintComTem(comTemDefines);
        }
    }

    private void copyPrintTemplate(Map<Enum<ParamType>, Map<String, String>> keyMap) throws Exception {
        ArrayList<DesignPrintTemplateDefine> templates = new ArrayList<DesignPrintTemplateDefine>();
        Map orDefault = keyMap.getOrDefault((Object)ParamType.PRINT_SCHEME, Collections.emptyMap());
        for (Map.Entry entry : orDefault.entrySet()) {
            List printTemplateDefines = this.printController.listPrintTemplateByScheme((String)entry.getKey());
            if (null == printTemplateDefines) continue;
            for (DesignPrintTemplateDefine template : printTemplateDefines) {
                DesignPrintTemplateDefine define = this.printController.copyPrintTemplate(template, (String)entry.getValue(), this.getKey(keyMap, ParamType.FORM, template.getFormKey()));
                define.setKey(this.getKey());
                define.setPrintSchemeKey((String)entry.getValue());
                define.setFormKey(this.getKey(keyMap, ParamType.FORM, template.getFormKey()));
                templates.add(define);
            }
        }
        this.printController.insertPrintTemplate(templates.toArray(new DesignPrintTemplateDefine[0]));
    }

    @Override
    public String delete(String formSchemeKey) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKet must not be null");
        this.taskAuthCheckService.checkFormSchemeAuth(formSchemeKey);
        NpRealTimeTaskInfo info = new NpRealTimeTaskInfo();
        info.setFormSchemeKey(formSchemeKey);
        info.setAbstractRealTimeJob((AbstractRealTimeJob)new DeleteFormSchemeExecutor());
        return this.asyncTaskManager.publishTask(info);
    }

    @Override
    public FormSchemeVO getFormScheme(String formSchemeKey) {
        Assert.notNull((Object)formSchemeKey, (String)"formSchemeKet must not be null");
        this.taskAuthCheckService.checkFormSchemeAuth(formSchemeKey);
        DesignFormSchemeDefine formSchemeDefine = this.designTimeViewController.getFormScheme(formSchemeKey);
        FormSchemeVO formSchemeVO = new FormSchemeVO(formSchemeDefine);
        formSchemeVO.setDateTime(this.designTimeViewController.getTask(formSchemeDefine.getTaskKey()).getDateTime());
        formSchemeVO.setValidateTime(this.validateTimeService.queryByFormScheme(formSchemeKey));
        return formSchemeVO;
    }

    @Override
    public DefaultSchemeVO getDefaultFormScheme(String taskKey) {
        List<ValidateTimeVO> validateTimes = this.validateTimeService.queryByTask(taskKey);
        ValidateTimeVO validateTimeVO = validateTimes.get(validateTimes.size() - 1);
        DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(validateTimeVO.getFormSchemeKey());
        FormSchemeItemVO formSchemeItemVO = this.getFormSchemeItemVO(formScheme);
        DefaultSchemeVO defaultSchemeVO = new DefaultSchemeVO();
        BeanUtils.copyProperties(formSchemeItemVO, defaultSchemeVO);
        TaskItemVO itemVO = new TaskItemVO();
        DesignTaskDefine task = this.designTimeViewController.getTask(taskKey);
        itemVO.setKey(taskKey);
        itemVO.setCode(task.getTaskCode());
        itemVO.setTitle(task.getTitle());
        defaultSchemeVO.setTaskInformation(itemVO);
        return defaultSchemeVO;
    }

    @Override
    public FormSchemeItemVO update(FormSchemeVO formScheme) {
        Assert.notNull((Object)formScheme.getKey(), (String)"key must not be null");
        Assert.notNull((Object)formScheme.getTitle(), (String)"title must not be null");
        Assert.notNull((Object)formScheme.getCode(), (String)"code must not be null");
        this.paramCheck.formSchemeParamCheck(formScheme.getTitle());
        DesignFormSchemeDefine originFormScheme = this.designTimeViewController.getFormScheme(formScheme.getKey());
        originFormScheme.setTitle(formScheme.getTitle());
        originFormScheme.setFormSchemeCode(formScheme.getCode());
        this.designTimeViewController.updateFormScheme(originFormScheme);
        return this.getFormSchemeItemVO(originFormScheme);
    }

    @Override
    public List<FormSchemeItemVO> queryByTask(String taskKey) {
        List formSchemes = this.designTimeViewController.listFormSchemeByTask(taskKey);
        ArrayList<FormSchemeItemVO> formSchemeItems = new ArrayList<FormSchemeItemVO>();
        formSchemes.forEach(formScheme -> {
            if (this.definitionAuthority.canFormSchemeModeling(formScheme.getKey())) {
                FormSchemeItemVO formSchemeItem = this.getFormSchemeItemVO((DesignFormSchemeDefine)formScheme);
                formSchemeItems.add(formSchemeItem);
            }
        });
        return formSchemeItems;
    }

    @Override
    public List<FormSchemeDTO> query(SchemeQueryParam queryParam) {
        List<ValidateTimeVO> validateTimes = this.validateTimeService.queryByTask(queryParam.getTaskKey());
        ArrayList<FormSchemeDTO> formSchemeItems = new ArrayList<FormSchemeDTO>();
        List formSchemeDefines = this.designTimeViewController.listFormSchemeByTask(queryParam.getTaskKey());
        if (CollectionUtils.isEmpty(validateTimes) || CollectionUtils.isEmpty(formSchemeDefines)) {
            return formSchemeItems;
        }
        String defaultKey = validateTimes.get(validateTimes.size() - 1).getFormSchemeKey();
        formSchemeDefines.forEach(e -> {
            if (this.definitionAuthority.canFormSchemeModeling(e.getKey())) {
                FormSchemeDTO dto = new FormSchemeDTO();
                dto.setKey(e.getKey());
                dto.setCode(e.getFormSchemeCode());
                dto.setTitle(e.getTitle());
                dto.setDefaultScheme(defaultKey.equals(e.getKey()));
                formSchemeItems.add(dto);
            }
        });
        return formSchemeItems;
    }

    private FormSchemeItemVO getFormSchemeItemVO(DesignFormSchemeDefine formScheme) {
        FormSchemeItemVO formSchemeItem = new FormSchemeItemVO();
        formSchemeItem.setKey(formScheme.getKey());
        formSchemeItem.setCode(formScheme.getFormSchemeCode());
        formSchemeItem.setTitle(formScheme.getTitle());
        formSchemeItem.setTask(formScheme.getTaskKey());
        formSchemeItem.setUpdateTime(this.sdf.format(formScheme.getUpdateTime()));
        formSchemeItem.setStatus(this.releaseService.getStatus(formScheme.getKey()));
        return formSchemeItem;
    }

    @Override
    public void formSchemeCodeCheck(String formSchemeKey, String formSchemeCode) {
        DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(formSchemeKey);
        if (formScheme != null) {
            formScheme.setFormSchemeCode(formSchemeCode);
        } else {
            formScheme = this.designTimeViewController.initFormScheme();
            formScheme.setFormSchemeCode(formSchemeCode);
        }
        this.paramCheckService.checkFormSchemeCode(formScheme);
    }

    @Override
    public boolean formSchemeTitleCheck(FormSchemeVO formSchemeVO) {
        if (!StringUtils.hasText(formSchemeVO.getTitle())) {
            return false;
        }
        String title = formSchemeVO.getTitle().trim();
        List designFormSchemeDefines = this.designTimeViewController.listFormSchemeByTask(formSchemeVO.getTaskKey());
        List filterDesignFormSchemeDefines = designFormSchemeDefines.stream().filter(a -> a.getTitle().equals(title)).collect(Collectors.toList());
        if (StringUtils.hasText(formSchemeVO.getKey())) {
            if (!CollectionUtils.isEmpty(filterDesignFormSchemeDefines)) {
                List sameTitleDifKey = filterDesignFormSchemeDefines.stream().filter(a -> !a.getKey().equals(formSchemeVO.getKey())).collect(Collectors.toList());
                return sameTitleDifKey.size() > 0;
            }
            return false;
        }
        return !CollectionUtils.isEmpty(filterDesignFormSchemeDefines);
    }

    public ValidateTimeCheckResultVO checkValidateTime(String taskKey, String formSchemeKey, List<ValidateTimeVO> validateTimes) {
        List<ValidateTimeVO> allValidateTimes = this.validateTimeService.queryByTask(taskKey);
        List<ValidateTimeVO> filterValidateTimes = allValidateTimes.stream().filter(validateTimeVO -> !validateTimeVO.getFormSchemeKey().equals(formSchemeKey)).collect(Collectors.toList());
        filterValidateTimes.addAll(validateTimes);
        ValidateTimeCheckResultVO checkResult = this.validateTimeService.check(filterValidateTimes);
        if (checkResult.getCheckResult() == "error") {
            return checkResult;
        }
        checkResult = this.validateTimeService.checkEmptyPeriod(filterValidateTimes);
        return checkResult;
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

