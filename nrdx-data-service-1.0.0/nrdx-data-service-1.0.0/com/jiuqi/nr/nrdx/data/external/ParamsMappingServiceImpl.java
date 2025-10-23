/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.data.common.service.dto.DataFieldMp
 *  com.jiuqi.nr.data.common.service.dto.FormulaMp
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormulaSchemeService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.io.service.ParamsMappingService
 *  com.jiuqi.nr.io.tsd.dto.AnalysisParam
 *  com.jiuqi.nr.io.tsd.dto.Form
 *  com.jiuqi.nr.io.tsd.dto.PackageData
 *  com.jiuqi.nr.mapping2.bean.FormulaMapping
 *  com.jiuqi.nr.mapping2.bean.ZBMapping
 *  com.jiuqi.nr.mapping2.service.FormulaMappingService
 *  com.jiuqi.nr.mapping2.service.PeriodMappingService
 *  com.jiuqi.nr.mapping2.service.ZBMappingService
 *  com.jiuqi.nr.nrdx.adapter.exception.NrdxParamsMappingException
 *  com.jiuqi.nvwa.mapping.bean.BaseDataItemMapping
 *  com.jiuqi.nvwa.mapping.service.IBaseDataMappingService
 *  com.jiuqi.nvwa.mapping.service.IOrgMappingService
 */
package com.jiuqi.nr.nrdx.data.external;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.data.common.service.dto.DataFieldMp;
import com.jiuqi.nr.data.common.service.dto.FormulaMp;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormulaSchemeService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.io.service.ParamsMappingService;
import com.jiuqi.nr.io.tsd.dto.AnalysisParam;
import com.jiuqi.nr.io.tsd.dto.Form;
import com.jiuqi.nr.io.tsd.dto.PackageData;
import com.jiuqi.nr.mapping2.bean.FormulaMapping;
import com.jiuqi.nr.mapping2.bean.ZBMapping;
import com.jiuqi.nr.mapping2.service.FormulaMappingService;
import com.jiuqi.nr.mapping2.service.PeriodMappingService;
import com.jiuqi.nr.mapping2.service.ZBMappingService;
import com.jiuqi.nr.nrdx.adapter.exception.NrdxParamsMappingException;
import com.jiuqi.nr.nrdx.data.external.NrdxParamsMapping;
import com.jiuqi.nvwa.mapping.bean.BaseDataItemMapping;
import com.jiuqi.nvwa.mapping.service.IBaseDataMappingService;
import com.jiuqi.nvwa.mapping.service.IOrgMappingService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParamsMappingServiceImpl
implements ParamsMappingService {
    @Autowired
    protected PeriodMappingService periodMappingService;
    @Autowired
    protected IOrgMappingService orgMappingService;
    @Autowired
    protected IBaseDataMappingService baseDataMappingService;
    @Autowired
    protected ZBMappingService zbMappingService;
    @Autowired
    protected FormulaMappingService formulaMappingService;
    @Autowired
    protected IEntityMetaService entityMetaService;
    @Autowired
    protected IRuntimeFormulaSchemeService runtimeFormulaSchemeService;
    @Autowired
    protected IRunTimeViewController runTimeViewController;
    private static final Logger log = LoggerFactory.getLogger(ParamsMappingServiceImpl.class);

    public ParamsMapping getParamsMapping(String mappingSchemeKey) {
        NrdxParamsMapping nrdxParamsMapping = new NrdxParamsMapping();
        if (StringUtils.isEmpty((String)mappingSchemeKey)) {
            return nrdxParamsMapping;
        }
        Map<String, String> periodMappingMap = nrdxParamsMapping.getPeriodMappingMap();
        List periodMappings = this.periodMappingService.findByMS(mappingSchemeKey);
        periodMappings.forEach(e -> periodMappingMap.put(e.getPeriod(), e.getMapping()));
        Map<String, String> orgCodeMappingMap = nrdxParamsMapping.getOrgCodeMappingMap();
        List orgMappings = this.orgMappingService.getOrgMappingByMS(mappingSchemeKey);
        orgMappings.forEach(e -> orgCodeMappingMap.put(e.getCode(), e.getMapping()));
        Map<String, Map<String, String>> baseDataMappingMap = nrdxParamsMapping.getBaseDataMappingMap();
        this.baseDataMappingService.getAllBaseDataItem(mappingSchemeKey).stream().collect(Collectors.groupingBy(BaseDataItemMapping::getBaseDataCode)).forEach((key, value) -> {
            String entityId = this.entityMetaService.getEntityIdByCode(key);
            Map<String, String> baseDataMapping = value.stream().collect(Collectors.toMap(BaseDataItemMapping::getBaseDataItemCode, BaseDataItemMapping::getMappingCode));
            baseDataMappingMap.put(entityId, baseDataMapping);
        });
        Map<String, Map<String, DataFieldMp>> dataFeildMappingMap = nrdxParamsMapping.getDataFieldMappingMap();
        for (ZBMapping zbMapping : this.zbMappingService.findByMS(mappingSchemeKey)) {
            String tableCode = zbMapping.getTable();
            String fieldCode = zbMapping.getZbCode();
            String mapping = zbMapping.getMapping();
            if (StringUtils.isEmpty((String)mapping)) continue;
            int startIndex = mapping.indexOf("[");
            int endIndex = mapping.indexOf("]");
            if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex) {
                throw new NrdxParamsMappingException("\u83b7\u53d6\u6620\u5c04\u6570\u636e\u65f6\u53d1\u751f\u5f02\u5e38\uff0c\u62a5\u8868\u6307\u6807\u6620\u5c04\u6709\u8bef\uff01");
            }
            String mappingTableCode = mapping.substring(0, startIndex);
            String mappingFieldCode = mapping.substring(startIndex + 1, endIndex);
            Map innerMap = dataFeildMappingMap.computeIfAbsent(tableCode, k -> new HashMap());
            DataFieldMp dataFieldMp = new DataFieldMp();
            dataFieldMp.setTableCode(mappingTableCode);
            dataFieldMp.setCode(mappingFieldCode);
            innerMap.put(fieldCode, dataFieldMp);
        }
        Map<String, Map<String, Map<String, FormulaMp>>> formulaMappingMap = nrdxParamsMapping.getFormulaMappingMap();
        List formulaMappings = this.formulaMappingService.findByMS(mappingSchemeKey);
        HashMap<String, String> formulaDefineKey2Title = new HashMap<String, String>();
        for (FormulaMapping formulaMapping : formulaMappings) {
            String formulaSchemeKey = formulaMapping.getFormulaScheme();
            String formulaSchemeTitle = formulaDefineKey2Title.computeIfAbsent(formulaSchemeKey, k -> {
                FormulaSchemeDefine scheme = this.runtimeFormulaSchemeService.queryFormulaScheme(k);
                return scheme != null ? scheme.getTitle() : null;
            });
            if (formulaSchemeTitle == null) continue;
            String formCode = formulaMapping.getFormCode();
            Map formCodeMap = formulaMappingMap.computeIfAbsent(formulaSchemeTitle, k -> new HashMap());
            Map formulaMpMap = formCodeMap.computeIfAbsent(formCode, k -> new HashMap());
            formulaMpMap.put(formulaMapping.getFormulaCode(), new FormulaMp(formulaMapping.getmFormulaCode(), formCode, formulaMapping.getmFormulaScheme()));
        }
        return nrdxParamsMapping;
    }

    public ParamsMapping getParamsMapping(AnalysisParam analysisParam, PackageData packageData) {
        String mappingSchemeKey;
        NrdxParamsMapping nrdxParamsMapping = new NrdxParamsMapping();
        TaskDefine taskDefine = Optional.ofNullable(analysisParam.getTaskKey()).map(e -> this.runTimeViewController.queryTaskDefine(e)).orElse(this.resolveTaskDefine(packageData));
        if (taskDefine != null) {
            nrdxParamsMapping.getTaskKeyMappingMap().put(packageData.getTaskKey(), taskDefine.getKey());
            nrdxParamsMapping.getTaskCodeMappingMap().put(packageData.getTaskCode(), taskDefine.getTaskCode());
            FormSchemeDefine formSchemeDefine = Optional.ofNullable(analysisParam.getFormSchemeKey()).map(e -> this.runTimeViewController.getFormScheme(e)).orElse(this.resolveFormSchemeDefine(packageData, taskDefine.getKey()));
            if (formSchemeDefine != null) {
                nrdxParamsMapping.getFormSchemeKeyMappingMap().put(packageData.getFormSchemeKey(), formSchemeDefine.getKey());
                nrdxParamsMapping.getFormSchemeCodeMappingMap().put(packageData.getFormSchemeCode(), formSchemeDefine.getFormSchemeCode());
                List forms = packageData.getForms();
                List formDefines = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeDefine.getKey());
                for (Form form : forms) {
                    FormDefine formDefine = this.resolveFormDefine(form, formDefines);
                    if (formDefine == null) continue;
                    nrdxParamsMapping.getFormKeyMappingMap().put(form.getKey(), formDefine.getKey());
                    nrdxParamsMapping.getFormCodeMappingMap().put(form.getCode(), formDefine.getFormCode());
                }
            }
        }
        if (StringUtils.isEmpty((String)(mappingSchemeKey = analysisParam.getMappingKey()))) {
            return nrdxParamsMapping;
        }
        Map<String, String> periodMappingMap = nrdxParamsMapping.getPeriodMappingMap();
        this.periodMappingService.findByMS(mappingSchemeKey).stream().filter(e -> StringUtils.isNotEmpty((String)e.getMapping())).forEach(e -> periodMappingMap.put(e.getMapping(), e.getPeriod()));
        Map<String, String> orgCodeMappingMap = nrdxParamsMapping.getOrgCodeMappingMap();
        this.orgMappingService.getOrgMappingByMS(mappingSchemeKey).stream().filter(e -> StringUtils.isNotEmpty((String)e.getMapping())).forEach(e -> orgCodeMappingMap.put(e.getMapping(), e.getCode()));
        Map<String, Map<String, String>> baseDataMappingMap = nrdxParamsMapping.getBaseDataMappingMap();
        this.baseDataMappingService.getAllBaseDataItem(mappingSchemeKey).stream().filter(e -> StringUtils.isNotEmpty((String)e.getMappingCode())).collect(Collectors.groupingBy(BaseDataItemMapping::getBaseDataCode)).forEach((key, value) -> {
            String entityId = this.entityMetaService.getEntityIdByCode(key);
            Map<String, String> baseDataMapping = value.stream().collect(Collectors.toMap(BaseDataItemMapping::getMappingCode, BaseDataItemMapping::getBaseDataItemCode));
            baseDataMappingMap.put(entityId, baseDataMapping);
        });
        Map<String, Map<String, DataFieldMp>> dataFeildMappingMap = nrdxParamsMapping.getDataFieldMappingMap();
        List zbMappingList = this.zbMappingService.findByMS(mappingSchemeKey);
        for (ZBMapping zbMapping : zbMappingList) {
            String mapping = zbMapping.getMapping();
            String tableCode = zbMapping.getTable();
            if (StringUtils.isEmpty((String)mapping)) continue;
            int startIndex = mapping.indexOf("[");
            int endIndex = mapping.indexOf("]");
            if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex) {
                throw new NrdxParamsMappingException("\u83b7\u53d6\u6620\u5c04\u6570\u636e\u65f6\u53d1\u751f\u5f02\u5e38\uff0c\u62a5\u8868\u6307\u6807\u6620\u5c04\u6709\u8bef\uff01");
            }
            String dataTableCode = mapping.substring(0, startIndex);
            String dataFieldCode = mapping.substring(startIndex + 1, endIndex);
            Map fieldCodeMap = dataFeildMappingMap.computeIfAbsent(dataTableCode, k -> new HashMap());
            DataFieldMp dataFieldMp = new DataFieldMp();
            dataFieldMp.setCode(zbMapping.getZbCode());
            dataFieldMp.setTableCode(tableCode);
            fieldCodeMap.put(dataFieldCode, dataFieldMp);
        }
        Map<String, Map<String, Map<String, FormulaMp>>> formulaMappingMap = nrdxParamsMapping.getFormulaMappingMap();
        this.formulaMappingService.findByMS(mappingSchemeKey).stream().filter(e -> StringUtils.isNotEmpty((String)e.getmFormulaCode())).forEach(e -> Optional.ofNullable(this.runtimeFormulaSchemeService.queryFormulaScheme(e.getFormulaScheme())).ifPresent(mSchemeDefine -> formulaMappingMap.computeIfAbsent(e.getmFormulaScheme(), k -> new HashMap()).computeIfAbsent(e.getFormCode(), k -> new HashMap()).put(e.getmFormulaCode(), new FormulaMp(e.getFormulaCode(), e.getFormCode(), mSchemeDefine.getTitle()))));
        return nrdxParamsMapping;
    }

    public Map<String, String> getOrgMapping(String mappingSchemeKey) {
        HashMap<String, String> orgMapping = new HashMap<String, String>();
        if (StringUtils.isNotEmpty((String)mappingSchemeKey)) {
            List orgMappings = this.orgMappingService.getOrgMappingByMS(mappingSchemeKey);
            orgMappings.forEach(e -> orgMapping.put(e.getMapping(), e.getCode()));
        }
        return orgMapping;
    }

    public TaskDefine resolveTaskDefine(PackageData packageData) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(packageData.getTaskKey());
        if (taskDefine == null && (taskDefine = this.runTimeViewController.queryTaskDefineByCode(packageData.getTaskCode())) == null) {
            taskDefine = this.runTimeViewController.getAllTaskDefines().stream().filter(e -> e.getTitle().equals(packageData.getTaskTitle())).findFirst().orElse(null);
        }
        return taskDefine;
    }

    public FormSchemeDefine resolveFormSchemeDefine(PackageData packageData, String taskKey) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(packageData.getFormSchemeKey());
        if (formSchemeDefine == null && (formSchemeDefine = this.runTimeViewController.getFormschemeByCode(packageData.getFormSchemeCode())) == null) {
            try {
                formSchemeDefine = this.runTimeViewController.queryFormSchemeByTask(taskKey).stream().filter(e -> e.getTitle().equals(packageData.getFormSchemeTitle())).findFirst().orElse(null);
            }
            catch (Exception e2) {
                log.error("\u6839\u636e\u6570\u636e\u5305\u5185\u62a5\u8868\u65b9\u6848title\u3010{}\u3011\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u662f\u53d1\u751f\u82e1\u7a0b\uff1a{}", packageData.getFormSchemeTitle(), e2.getMessage(), e2);
            }
        }
        return formSchemeDefine;
    }

    public FormDefine resolveFormDefine(Form form, List<FormDefine> formDefines) {
        for (FormDefine formDefine : formDefines) {
            if (!formDefine.getKey().equals(form.getKey())) continue;
            return formDefine;
        }
        for (FormDefine formDefine : formDefines) {
            if (!formDefine.getFormCode().equals(form.getCode())) continue;
            return formDefine;
        }
        for (FormDefine formDefine : formDefines) {
            if (!formDefine.getTitle().equals(form.getTitle())) continue;
            return formDefine;
        }
        return null;
    }
}

