/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.BoolData
 *  com.jiuqi.np.dataengine.data.StringData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datacrud.RegionGradeInfo
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.EnumDisplayMode
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormFoldingDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.util.EntityDefaultValue
 *  com.jiuqi.nr.definition.util.EntityValueType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.survey.model.SurveyModel
 *  com.jiuqi.nr.survey.model.link.SurveyModelLinkHelp
 *  com.jiuqi.nr.survey.model.link.SurveyQuestion
 *  com.jiuqi.nr.survey.model.link.SurveyQuestionLink
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.jtable.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.BoolData;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datacrud.RegionGradeInfo;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.EnumDisplayMode;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormFoldingDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import com.jiuqi.nr.definition.util.EntityValueType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.common.BatchSummaryConst;
import com.jiuqi.nr.jtable.filter.RegionTabFilter;
import com.jiuqi.nr.jtable.params.base.EnumLinkData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.FormulaLinkData;
import com.jiuqi.nr.jtable.params.base.FormulaSchemeData;
import com.jiuqi.nr.jtable.params.base.FromGridData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.JtableData;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RecordCardData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.base.RegionNumber;
import com.jiuqi.nr.jtable.params.base.RegionSettingData;
import com.jiuqi.nr.jtable.params.base.RegionSimpleData;
import com.jiuqi.nr.jtable.params.base.RegionTab;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.input.GradeCellInfo;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.service.ICustomRegionsGradeService;
import com.jiuqi.nr.jtable.service.IExtractExtensionCollector;
import com.jiuqi.nr.jtable.service.IJtableContextService;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DataCrudUtil;
import com.jiuqi.nr.jtable.util.DataFormaterCache;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.RegionSettingUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.survey.model.SurveyModel;
import com.jiuqi.nr.survey.model.link.SurveyModelLinkHelp;
import com.jiuqi.nr.survey.model.link.SurveyQuestion;
import com.jiuqi.nr.survey.model.link.SurveyQuestionLink;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JtableContextServiceImpl
implements IJtableContextService {
    private static final Logger logger = LoggerFactory.getLogger(JtableContextServiceImpl.class);
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Autowired
    private IJtableDataEngineService jtableDataEngineService;
    @Autowired
    private IFormulaRunTimeController iFormulaRunTimeController;
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Autowired(required=false)
    private ICustomRegionsGradeService iCustomRegionsGradeService;
    @Autowired
    private IPeriodEntityAdapter iPeriodEntityAdapter;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private IExtractExtensionCollector extractExtensionCollector;

    @Override
    public JtableData getReportFormData(JtableContext jtableContext) {
        List formulaDefineList;
        FormulaSchemeData formulaSchemeData;
        Object gradeRegionKeys;
        JtableData jtableData = new JtableData();
        FormData form = this.jtableParamService.getReport(jtableContext.getFormKey(), jtableContext.getFormSchemeKey());
        jtableData.setForm(form);
        FromGridData fromGridData = new FromGridData();
        jtableData.setStructure(fromGridData);
        Map<String, Map<String, FormFoldingDefine>> formFoldData = this.jtableParamService.getFormFoldData(jtableContext.getFormKey());
        fromGridData.setFormFoldData(formFoldData);
        DimensionValueSet dimensionValueSet = this.getDimensionValueSet(jtableContext);
        HashSet<String> entityKeys = new HashSet<String>();
        DataFormaterCache dataFormaterCache = new DataFormaterCache(jtableContext);
        ArrayList<RegionSimpleData> regions = new ArrayList<RegionSimpleData>();
        fromGridData.setRegions(regions);
        HashSet<String> fieldKeys = new HashSet<String>();
        RegionTabFilter regionTabSettingFilter = new RegionTabFilter(jtableContext, dimensionValueSet);
        List<RegionData> regionDatas = this.jtableParamService.getRegions(jtableContext.getFormKey());
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(jtableContext.getFormSchemeKey());
        String dataTimeDimensionName = this.iPeriodEntityAdapter.getPeriodEntity(formScheme.getDateTime()).getDimensionName();
        String dataTime = jtableContext.getDimensionSet().get(dataTimeDimensionName).getValue();
        Map<Object, Object> customRegionsGrade = new HashMap();
        if (this.iCustomRegionsGradeService != null && (gradeRegionKeys = regionDatas.stream().filter(r -> r.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue() && r.getGrade() != null && r.getGrade().getGradeCells() != null && !r.getGrade().getGradeCells().isEmpty()).map(r -> r.getKey()).collect(Collectors.toList())) != null && !gradeRegionKeys.isEmpty()) {
            customRegionsGrade = this.iCustomRegionsGradeService.getCustomRegionsGrade((List<String>)gradeRegionKeys, dataTime);
        }
        for (RegionData region : regionDatas) {
            RegionGradeInfo customRegionGrade;
            RegionNumber regionNumber;
            List<RegionTab> regionTabs = this.jtableParamService.getRegionTabs(region.getKey());
            if (null != regionTabs) {
                for (RegionTab regionTab : regionTabs) {
                    if (!regionTabSettingFilter.accept(regionTab)) continue;
                    region.getTabs().add(regionTab);
                }
            }
            if (null != (regionNumber = this.jtableParamService.getRegionNumber(region.getKey()))) {
                region.setRegionNumber(regionNumber);
            }
            if (StringUtils.isNotEmpty((String)region.getReadOnlyCondition())) {
                region.setReadOnly(this.executeExpression(region.getReadOnlyCondition(), jtableContext, dimensionValueSet));
            }
            List<LinkData> links = this.jtableParamService.getLinks(region.getKey());
            region.setDataLinks(links);
            com.jiuqi.nr.jtable.params.input.RegionGradeInfo grade = region.getGrade();
            if (customRegionsGrade != null && !customRegionsGrade.isEmpty() && customRegionsGrade.containsKey(region.getKey()) && (customRegionGrade = (RegionGradeInfo)customRegionsGrade.get(region.getKey())) != null) {
                grade = DataCrudUtil.getRegionGradeInfo((RegionGradeInfo)customRegionsGrade.get(region.getKey()));
                region.setGrade(grade);
            }
            customRegionGrade = links.iterator();
            while (customRegionGrade.hasNext()) {
                LinkData link = customRegionGrade.next();
                if (StringUtils.isNotEmpty((String)link.getZbid())) {
                    fieldKeys.add(link.getZbid());
                    if (StringUtils.isNotEmpty((String)link.getDefaultValue())) {
                        if (link.getDataLinkType().equals((Object)DataLinkType.DATA_LINK_TYPE_FMDM)) {
                            link.setDefaultValue(link.getDefaultValue());
                        } else {
                            AbstractData expressionEvaluat = this.jtableDataEngineService.expressionEvaluat(link.getDefaultValue(), jtableContext, dimensionValueSet);
                            if (expressionEvaluat != null) {
                                Object fieldValue = link.getFormatData(expressionEvaluat, dataFormaterCache, jtableContext);
                                if (fieldValue == null) {
                                    link.setDefaultValue(link.getDefaultValue());
                                } else {
                                    link.setDefaultValue(fieldValue.toString());
                                }
                            } else {
                                link.setDefaultValue(link.getDefaultValue());
                            }
                        }
                    }
                }
                if (link instanceof EnumLinkData) {
                    EnumLinkData enumLink = (EnumLinkData)link;
                    if (enumLink.getDisplayMode() == EnumDisplayMode.DISPLAY_MODE_IN_CELL && !entityKeys.contains(enumLink.getEntityKey())) {
                        entityKeys.add(enumLink.getEntityKey());
                    }
                    if (grade != null && grade.getGradeCells() != null && !grade.getGradeCells().isEmpty()) {
                        List<GradeCellInfo> gradeCells = grade.getGradeCells();
                        for (GradeCellInfo gradeCell : gradeCells) {
                            if (!gradeCell.getZbid().equals(enumLink.getZbid())) continue;
                            gradeCell.setGradeStruct(this.jtableParamService.getEntity(enumLink.getEntityKey()).getTreeStruct());
                        }
                    }
                    if (region.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                        enumLink.setMaxDepth(1);
                    }
                }
                if (link instanceof FormulaLinkData) {
                    fromGridData.getFormulaDataLinks().add(link.getKey());
                }
                this.setLinkRegionEntityDva(region, link, dataFormaterCache, jtableContext);
            }
            if (form.getFormType().equals(FormType.FORM_TYPE_NEWFMDM.name())) {
                this.modifyFmdmZbTitle(links, formScheme);
            }
            RegionSimpleData simpleRegion = new RegionSimpleData(region);
            if (region.getType() != DataRegionKind.DATA_REGION_SIMPLE.getValue()) {
                ArrayList<String> dimensionNames = new ArrayList<String>();
                simpleRegion.setDimensionNames(dimensionNames);
                ArrayList<String> bizFieldKeys = new ArrayList<String>();
                simpleRegion.setBizFieldKeys(bizFieldKeys);
                List<List<FieldData>> bizKeyOrderFieldList = this.jtableDataEngineService.getBizKeyOrderFieldList(region.getKey(), jtableContext);
                if (bizKeyOrderFieldList != null && bizKeyOrderFieldList.size() > 0) {
                    for (FieldData fieldData : bizKeyOrderFieldList.get(0)) {
                        String dimensionName = this.jtableDataEngineService.getDimensionName(fieldData);
                        dimensionNames.add(dimensionName);
                        bizFieldKeys.add(fieldData.getFieldKey());
                    }
                }
            }
            regions.add(simpleRegion);
        }
        for (String entityKey : entityKeys) {
            EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
            entityQueryInfo.setEntityViewKey(entityKey);
            entityQueryInfo.setAllChildren(false);
            entityQueryInfo.setSorted(true);
            entityQueryInfo.setContext(jtableContext);
            EntityReturnInfo queryEntityData = this.jtableEntityService.queryEntityData(entityQueryInfo);
            fromGridData.getEntityData().put(entityKey, queryEntityData.getEntitys());
        }
        fromGridData.setCalcDataLinks(this.jtableParamService.getCalcDataLinks(jtableContext));
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(jtableContext.getTaskKey());
        if (taskDefine != null && taskDefine.getEfdcSwitch() && !BatchSummaryConst.isBatchSummaryEntry(jtableContext.getVariableMap()) && (formulaSchemeData = this.jtableParamService.getSoluctionByDimensions(jtableContext)) != null && formulaSchemeData.getKey() != null && (formulaDefineList = this.iFormulaRunTimeController.getAllFormulasInScheme(formulaSchemeData.getKey())).size() > 0) {
            jtableData.setFormulaSchemeData(formulaSchemeData);
            fromGridData.setExtractDataLinks(this.jtableParamService.getExtractDataLinkList(jtableContext, formulaSchemeData.getKey()));
        }
        fromGridData.getExtractDataLinks().addAll(this.extractExtensionCollector.getExtractDataLinkList(jtableContext));
        HashSet<String> reloadFields = this.jtableParamService.getConditionFieldsByFormScheme(jtableContext.getFormSchemeKey());
        if (reloadFields.size() > 0) {
            fieldKeys.retainAll(reloadFields);
            if (fieldKeys.size() > 0) {
                form.setNeedReload(true);
            }
        }
        return jtableData;
    }

    private void setLinkRegionEntityDva(RegionData regionData, LinkData link, DataFormaterCache dataFormaterCache, JtableContext jtableContext) {
        List<EntityDefaultValue> regionEntityDefaultValue = regionData.getRegionEntityDefaultValue();
        Object val = RegionSettingUtil.checkRegionDefaultValueGetIfAbsent(regionData, link);
        if (Objects.nonNull(val)) {
            if (link instanceof EnumLinkData) {
                EnumLinkData enumLinkData = (EnumLinkData)link;
                String entityKey = enumLinkData.getEntityKey();
                Optional<EntityDefaultValue> enumEntityDefault = regionEntityDefaultValue.stream().filter(e -> entityKey.equals(e.getEntityId()) && link.getZbid().equals(e.getFieldKey())).findAny();
                if (enumEntityDefault.isPresent()) {
                    if (enumEntityDefault.get().getEntityValueType() == EntityValueType.DATA_ITEM_CODE) {
                        Object formatValue = link.getFormatData((AbstractData)new StringData(val.toString()), dataFormaterCache, jtableContext);
                        Map<String, EntityReturnInfo> entityDataMap = dataFormaterCache.getEntityDataMap();
                        EntityReturnInfo entityReturnInfo = entityDataMap.get(entityKey);
                        if (entityReturnInfo != null && entityReturnInfo.getEntitys() != null && entityReturnInfo.getEntitys().size() > 0 && formatValue != null && StringUtils.isNotEmpty((String)formatValue.toString())) {
                            link.setDefaultValue(formatValue.toString());
                        }
                    } else {
                        link.setDefaultValue(val.toString());
                    }
                }
            } else {
                link.setDefaultValue(val.toString());
            }
        }
    }

    @Override
    public boolean isFormCondition(JtableContext jtableContext) {
        FormDefine formDefine = this.runTimeViewController.queryFormById(jtableContext.getFormKey());
        if (StringUtils.isNotEmpty((String)formDefine.getFormCondition())) {
            DimensionValueSet dimensionValueSet = this.getDimensionValueSet(jtableContext);
            return this.executeExpression(formDefine.getFormCondition(), jtableContext, dimensionValueSet);
        }
        return true;
    }

    private boolean executeExpression(String expression, JtableContext jtableContext, DimensionValueSet dimensionValueSet) {
        boolean evaluat = false;
        AbstractData expressionEvaluat = this.jtableDataEngineService.expressionEvaluat(expression, jtableContext, dimensionValueSet);
        if (expressionEvaluat != null && expressionEvaluat instanceof BoolData) {
            try {
                evaluat = expressionEvaluat.getAsBool();
            }
            catch (DataTypeException e) {
                logger.error("\u516c\u5f0f%s\u89e3\u6790\u53d1\u751f\u9519\u8bef:%s", (Object)expression, (Object)e.getMessage());
            }
        }
        return evaluat;
    }

    @Override
    public DimensionValueSet getDimensionValueSet(JtableContext jtableContext) {
        if (jtableContext == null) {
            return new DimensionValueSet();
        }
        return DimensionValueSetUtil.getDimensionValueSet(jtableContext);
    }

    @Override
    public FormulaSchemeData getFormulaSchemeData(JtableContext jtableContext) {
        return this.jtableParamService.getFormulaScheme(jtableContext.getFormulaSchemeKey());
    }

    private void modifyFmdmZbTitle(List<LinkData> links, FormSchemeDefine formScheme) {
        String dw;
        IEntityModel entityModel;
        Map fmdmLinkMap;
        if (links != null && !links.isEmpty() && formScheme != null && (fmdmLinkMap = links.stream().filter(l -> l.getDataLinkType().equals((Object)DataLinkType.DATA_LINK_TYPE_FMDM)).collect(Collectors.toMap(LinkData::getZbid, Function.identity(), (key1, key2) -> key2))) != null && !fmdmLinkMap.isEmpty() && (entityModel = this.iEntityMetaService.getEntityModel(dw = formScheme.getDw())) != null) {
            Iterator attributes = entityModel.getAttributes();
            while (attributes.hasNext()) {
                IEntityAttribute attribute = (IEntityAttribute)attributes.next();
                if (!fmdmLinkMap.containsKey(attribute.getID())) continue;
                LinkData fmdmLink = (LinkData)fmdmLinkMap.get(attribute.getID());
                fmdmLink.setZbtitle(attribute.getTitle());
            }
        }
    }

    @Override
    public JtableData initSurveryCardData(String formKey, String taskKey, String formulaSchemeKey, String regionKey, String param) {
        RegionSettingData regionSetting = this.jtableParamService.getRegionSetting(regionKey);
        JtableData jtableData = new JtableData();
        FromGridData fromGridData = new FromGridData();
        jtableData.setStructure(fromGridData);
        ArrayList<RegionSimpleData> regions = new ArrayList<RegionSimpleData>();
        RecordCardData cardRecord = regionSetting.getCardRecord();
        String surveyJson = cardRecord.getSurveyCard();
        if (StringUtils.isNotEmpty((String)taskKey) && StringUtils.isNotEmpty((String)param) && StringUtils.isNotEmpty((String)surveyJson)) {
            String[] params;
            RegionData region = this.jtableParamService.getRegion(regionKey);
            region.setType(0);
            FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
            String formSchemeKey = formDefine.getFormScheme();
            fromGridData.setRegions(regions);
            JtableContext context = new JtableContext();
            context.setFormulaSchemeKey(formulaSchemeKey);
            context.setTaskKey(taskKey);
            context.setFormSchemeKey(formSchemeKey);
            context.setFormKey(formKey);
            HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
            context.setDimensionSet(dimensionSet);
            for (String paramStr : params = param.split(";")) {
                String[] paramValues = paramStr.split(":");
                if (paramValues.length != 2) continue;
                DimensionValue dimensionValue = new DimensionValue();
                dimensionValue.setName(paramValues[0]);
                dimensionValue.setValue(paramValues[1]);
                dimensionSet.put(paramValues[0], dimensionValue);
            }
            try {
                DataFormaterCache dataFormaterCache = new DataFormaterCache(context);
                ArrayList<LinkData> cardLinkDatas = new ArrayList<LinkData>();
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
                SurveyModel surveyModel = (SurveyModel)objectMapper.readValue(surveyJson, SurveyModel.class);
                List allSurveyQuestion = SurveyModelLinkHelp.getAllSurveyQuestion((SurveyModel)surveyModel);
                if (null != allSurveyQuestion) {
                    for (SurveyQuestion surveyQuestion : allSurveyQuestion) {
                        List links = surveyQuestion.getLinks();
                        if (null == links) continue;
                        for (SurveyQuestionLink questionLink : links) {
                            String linkId = questionLink.getLinkId();
                            LinkData link = this.jtableParamService.getLink(linkId);
                            if (link == null) continue;
                            if (StringUtils.isNotEmpty((String)link.getDefaultValue())) {
                                AbstractData expressionEvaluat = this.jtableDataEngineService.expressionEvaluat(link.getDefaultValue(), context, this.getDimensionValueSet(context));
                                if (expressionEvaluat != null) {
                                    Object fieldValue = link.getFormatData(expressionEvaluat, dataFormaterCache, context);
                                    if (fieldValue == null) {
                                        link.setDefaultValue(link.getDefaultValue());
                                    } else {
                                        link.setDefaultValue(fieldValue.toString());
                                    }
                                } else {
                                    link.setDefaultValue(link.getDefaultValue());
                                }
                            }
                            cardLinkDatas.add(link);
                        }
                    }
                }
                region.setDataLinks(cardLinkDatas);
                RegionSimpleData regionSimpleData = new RegionSimpleData(region);
                regions.add(regionSimpleData);
                return jtableData;
            }
            catch (Exception e) {
                logger.error("\u95ee\u5377\u8868\u6837\u8f6c\u6362\u62a5\u9519\uff01", e);
            }
        }
        return jtableData;
    }
}

