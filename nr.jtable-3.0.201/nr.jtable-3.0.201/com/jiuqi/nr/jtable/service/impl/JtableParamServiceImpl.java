/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.FormulaCallBack
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IColumnModelFinder
 *  com.jiuqi.np.dataengine.intf.IFormulaRunner
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.node.CalcExpression
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.common.exception.NotFoundTableDefineException
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.data.logic.facade.param.base.BaseEnv
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckParam
 *  com.jiuqi.nr.data.logic.internal.util.DimensionUtil
 *  com.jiuqi.nr.data.logic.internal.util.FormulaParseUtil
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormFoldingDirEnum
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.BigDataDefine
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormFoldingDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.RegionSettingDefine
 *  com.jiuqi.nr.definition.facade.RegionTabSettingDefine
 *  com.jiuqi.nr.definition.facade.RowNumberSetting
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDeployTimeService
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.FMDMAttributeDTO
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMAttributeService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.survey.model.Choice
 *  com.jiuqi.nr.survey.model.Element
 *  com.jiuqi.nr.survey.model.SurveyModel
 *  com.jiuqi.nr.survey.model.ValueBean
 *  com.jiuqi.nr.survey.model.common.QuestionType
 *  com.jiuqi.nr.survey.model.define.IChoicesQuestion
 *  com.jiuqi.nr.survey.model.link.SurveyModelLinkHelp
 *  com.jiuqi.nr.survey.model.link.SurveyQuestion
 *  com.jiuqi.nr.survey.model.link.SurveyQuestionLink
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 */
package com.jiuqi.nr.jtable.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.FormulaCallBack;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.dataengine.intf.IFormulaRunner;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.common.exception.NotFoundTableDefineException;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.logic.facade.param.base.BaseEnv;
import com.jiuqi.nr.data.logic.facade.param.input.CheckParam;
import com.jiuqi.nr.data.logic.internal.util.DimensionUtil;
import com.jiuqi.nr.data.logic.internal.util.FormulaParseUtil;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormFoldingDirEnum;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormFoldingDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.facade.RowNumberSetting;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDeployTimeService;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.jtable.exception.NotFoundFieldException;
import com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException;
import com.jiuqi.nr.jtable.params.base.CStyleFile;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.EnumLink;
import com.jiuqi.nr.jtable.params.base.EnumLinkData;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.FileLinkData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.FormulaConditionFile;
import com.jiuqi.nr.jtable.params.base.FormulaData;
import com.jiuqi.nr.jtable.params.base.FormulaSchemeData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RecordCardData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.base.RegionNumber;
import com.jiuqi.nr.jtable.params.base.RegionSettingData;
import com.jiuqi.nr.jtable.params.base.RegionTab;
import com.jiuqi.nr.jtable.params.base.TableData;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.input.FieldQueryInfo;
import com.jiuqi.nr.jtable.params.input.FormulaQueryInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.params.output.ExtractCellInfo;
import com.jiuqi.nr.jtable.params.output.FormTableFields;
import com.jiuqi.nr.jtable.params.output.FormTables;
import com.jiuqi.nr.jtable.service.ICSRunTimeService;
import com.jiuqi.nr.jtable.service.IExtractFormulaService;
import com.jiuqi.nr.jtable.service.IFCRunTimeService;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.service.impl.FCRunTimeServiceImpl;
import com.jiuqi.nr.jtable.util.FormulaUtil;
import com.jiuqi.nr.jtable.util.LinkDataFactory;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.survey.model.Choice;
import com.jiuqi.nr.survey.model.Element;
import com.jiuqi.nr.survey.model.SurveyModel;
import com.jiuqi.nr.survey.model.ValueBean;
import com.jiuqi.nr.survey.model.common.QuestionType;
import com.jiuqi.nr.survey.model.define.IChoicesQuestion;
import com.jiuqi.nr.survey.model.link.SurveyModelLinkHelp;
import com.jiuqi.nr.survey.model.link.SurveyQuestion;
import com.jiuqi.nr.survey.model.link.SurveyQuestionLink;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JtableParamServiceImpl
implements IJtableParamService {
    private static final Logger logger = LoggerFactory.getLogger(JtableParamServiceImpl.class);
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IRuntimeDeployTimeService runtimeDeployTimeService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IPeriodEntityAdapter periodAdapter;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IFMDMAttributeService FMDMAttributeService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private ICSRunTimeService icsRunTimeService;
    @Autowired
    private ITaskOptionController iTaskOptionController;
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private IFCRunTimeService ifcRunTimeService;
    @Autowired
    private FormulaParseUtil formulaParseUtil;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private IColumnModelFinder couModelFinder;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;

    @Override
    public FormData getReport(String formKey, String formSchemeKey) {
        FormDefine formDefine = this.runtimeView.queryFormById(formKey);
        if (null != formDefine) {
            FormData formInfo = new FormData();
            formInfo.init(formDefine);
            return formInfo;
        }
        return null;
    }

    @Override
    public Grid2Data getGridData(String formKey) {
        BigDataDefine formDefine = this.runtimeView.getReportDataFromForm(formKey);
        if (null != formDefine) {
            if (formDefine.getData() != null) {
                Grid2Data bytesToGrid = Grid2Data.bytesToGrid((byte[])formDefine.getData());
                String frontScriptFromForm = this.runtimeView.getFrontScriptFromForm(formDefine.getKey());
                if (StringUtils.isNotEmpty((String)frontScriptFromForm)) {
                    bytesToGrid.setScript(frontScriptFromForm);
                }
                return bytesToGrid;
            }
            Grid2Data griddata = new Grid2Data();
            griddata.setRowCount(10);
            griddata.setColumnCount(10);
            return griddata;
        }
        return null;
    }

    @Override
    public String getSurveyData(String formKey, String taskKey, String param) {
        String surveyJson = this.runtimeView.getSurveyDataFromForm(formKey);
        if (StringUtils.isNotEmpty((String)taskKey) && StringUtils.isNotEmpty((String)param) && StringUtils.isNotEmpty((String)surveyJson)) {
            String[] params;
            FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
            String formSchemeKey = formDefine.getFormScheme();
            JtableContext context = new JtableContext();
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
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
                SurveyModel surveyModel = (SurveyModel)objectMapper.readValue(surveyJson, SurveyModel.class);
                List allSurveyQuestion = SurveyModelLinkHelp.getAllSurveyQuestion((SurveyModel)surveyModel);
                if (null != allSurveyQuestion) {
                    boolean hasEnum = false;
                    IJtableEntityService entityService = (IJtableEntityService)SpringBeanUtils.getBean(IJtableEntityService.class);
                    for (SurveyQuestion surveyQuestion : allSurveyQuestion) {
                        List links = surveyQuestion.getLinks();
                        if (null == links) continue;
                        for (SurveyQuestionLink questionLink : links) {
                            ValueBean valueBean;
                            List<EntityData> entitys;
                            LinkData link;
                            QuestionType type = questionLink.getType();
                            if (QuestionType.RADIOGROUP != type && QuestionType.TAGBOX != type && QuestionType.CHECKBOX != type && QuestionType.DROPDOWN != type) continue;
                            String linkId = questionLink.getLinkId();
                            String filterFormula = questionLink.getFilterFormula();
                            if (!StringUtils.isNotEmpty((String)linkId) || StringUtils.isNotEmpty((String)filterFormula) || null == (link = this.getLink(linkId)) || !(link instanceof EnumLinkData)) continue;
                            ArrayList<Choice> choices = new ArrayList<Choice>();
                            EnumLinkData enumLinkData = (EnumLinkData)link;
                            String entityKey = enumLinkData.getEntityKey();
                            EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
                            entityQueryInfo.setAllChildren(true);
                            entityQueryInfo.setTreeToList(true);
                            entityQueryInfo.setContext(context);
                            entityQueryInfo.setDataLinkKey(linkId);
                            entityQueryInfo.setEntityViewKey(entityKey);
                            EntityReturnInfo entityReturnInfo = entityService.queryEntityData(entityQueryInfo);
                            if ("success".equals(entityReturnInfo.getMessage()) && null != (entitys = entityReturnInfo.getEntitys())) {
                                for (EntityData entityData : entitys) {
                                    Choice choice = new Choice();
                                    choice.setValue(entityData.getCode());
                                    choice.setText(entityData.getRowCaption());
                                    choices.add(choice);
                                }
                            }
                            if (null == (valueBean = questionLink.getQuestion()) || !(valueBean instanceof IChoicesQuestion)) continue;
                            hasEnum = true;
                            IChoicesQuestion choicesQuestion = (IChoicesQuestion)valueBean;
                            choicesQuestion.setChoices(choices);
                            choicesQuestion.setChoicesByUrl(null);
                            List formulas = choicesQuestion.getChoiceFormulas();
                            if (null == formulas || formulas.isEmpty()) continue;
                            Map<String, Choice> formulaMaps = formulas.stream().collect(Collectors.toMap(Choice::getValue, e -> e));
                            for (Choice choice : choices) {
                                if (!formulaMaps.containsKey(choice.getValue())) continue;
                                Choice formula = formulaMaps.get(choice.getValue());
                                choice.setVisibleIf(formula.getVisibleIf());
                                choice.setEnableIf(formula.getEnableIf());
                            }
                            choicesQuestion.setChoiceFormulas(null);
                        }
                    }
                    if (hasEnum) {
                        surveyJson = objectMapper.writeValueAsString((Object)surveyModel);
                    }
                }
            }
            catch (Exception e2) {
                logger.error("\u95ee\u5377\u8868\u6837\u8f6c\u6362\u62a5\u9519\uff01", e2);
            }
        }
        return surveyJson;
    }

    @Override
    public String getSurveyCardStyle(String formKey, String taskKey, String formulaSchemeKey, String regionKey, String param) {
        RegionSettingData regionSetting = this.getRegionSetting(regionKey);
        RecordCardData cardRecord = regionSetting.getCardRecord();
        String surveyJson = cardRecord.getSurveyCard();
        HashMap<String, ValueBean> linkageQuestion = new HashMap<String, ValueBean>();
        HashMap<String, EnumLinkData> linkageLinkMap = new HashMap<String, EnumLinkData>();
        if (StringUtils.isNotEmpty((String)taskKey) && StringUtils.isNotEmpty((String)param) && StringUtils.isNotEmpty((String)surveyJson)) {
            String[] params;
            FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
            String formSchemeKey = formDefine.getFormScheme();
            JtableContext context = new JtableContext();
            context.setTaskKey(taskKey);
            context.setFormSchemeKey(formSchemeKey);
            context.setFormKey(formKey);
            context.setFormulaSchemeKey(formulaSchemeKey);
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
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
                SurveyModel surveyModel = (SurveyModel)objectMapper.readValue(surveyJson, SurveyModel.class);
                List allSurveyQuestion = SurveyModelLinkHelp.getAllSurveyQuestion((SurveyModel)surveyModel);
                if (null != allSurveyQuestion) {
                    boolean hasEnum = false;
                    IJtableEntityService entityService = (IJtableEntityService)SpringBeanUtils.getBean(IJtableEntityService.class);
                    for (SurveyQuestion surveyQuestion : allSurveyQuestion) {
                        List links = surveyQuestion.getLinks();
                        if (null == links) continue;
                        for (SurveyQuestionLink questionLink : links) {
                            ValueBean valueBean;
                            List<EntityData> entitys;
                            LinkData link;
                            QuestionType type = questionLink.getType();
                            String linkId = questionLink.getLinkId();
                            String filterFormula = questionLink.getFilterFormula();
                            if (QuestionType.RADIOGROUP != type && QuestionType.TAGBOX != type && QuestionType.CHECKBOX != type && QuestionType.DROPDOWN != type || !StringUtils.isNotEmpty((String)linkId) || null == (link = this.getLink(linkId)) || !(link instanceof EnumLinkData)) continue;
                            if (((EnumLinkData)link).getEnumLink() != null) {
                                linkageQuestion.put(linkId, questionLink.getQuestion());
                                linkageLinkMap.put(linkId, (EnumLinkData)link);
                                continue;
                            }
                            if (StringUtils.isNotEmpty((String)filterFormula)) continue;
                            ArrayList<Choice> choices = new ArrayList<Choice>();
                            EnumLinkData enumLinkData = (EnumLinkData)link;
                            String entityKey = enumLinkData.getEntityKey();
                            EntityQueryByViewInfo entityQueryInfo = new EntityQueryByViewInfo();
                            entityQueryInfo.setAllChildren(true);
                            entityQueryInfo.setTreeToList(true);
                            entityQueryInfo.setContext(context);
                            entityQueryInfo.setDataLinkKey(linkId);
                            entityQueryInfo.setEntityViewKey(entityKey);
                            EntityReturnInfo entityReturnInfo = entityService.queryEntityData(entityQueryInfo);
                            if ("success".equals(entityReturnInfo.getMessage()) && null != (entitys = entityReturnInfo.getEntitys())) {
                                for (EntityData entityData : entitys) {
                                    Choice choice = new Choice();
                                    choice.setValue(entityData.getCode());
                                    choice.setText(entityData.getRowCaption());
                                    choices.add(choice);
                                }
                            }
                            if (null == (valueBean = questionLink.getQuestion()) || !(valueBean instanceof IChoicesQuestion)) continue;
                            hasEnum = true;
                            IChoicesQuestion choicesQuestion = (IChoicesQuestion)valueBean;
                            choicesQuestion.setChoices(choices);
                            choicesQuestion.setChoicesByUrl(null);
                            List formulas = choicesQuestion.getChoiceFormulas();
                            if (null == formulas || formulas.isEmpty()) continue;
                            Map<String, Choice> formulaMaps = formulas.stream().collect(Collectors.toMap(Choice::getValue, e -> e));
                            for (Choice choice : choices) {
                                if (!formulaMaps.containsKey(choice.getValue())) continue;
                                Choice formula = formulaMaps.get(choice.getValue());
                                choice.setVisibleIf(formula.getVisibleIf());
                                choice.setEnableIf(formula.getEnableIf());
                            }
                            choicesQuestion.setChoiceFormulas(null);
                        }
                    }
                    boolean lackEnum = false;
                    if (!linkageQuestion.isEmpty()) {
                        block7: for (Map.Entry entry : linkageQuestion.entrySet()) {
                            String key = (String)entry.getKey();
                            ValueBean valueBean = (ValueBean)entry.getValue();
                            EnumLinkData enumLinkData = (EnumLinkData)linkageLinkMap.get(key);
                            if (enumLinkData == null || enumLinkData.getEnumLink() == null) continue;
                            ArrayList<String> linkageLinks = new ArrayList<String>();
                            EnumLink enumLink = enumLinkData.getEnumLink();
                            List<String> preLinks = enumLink.getPreLinks();
                            List<String> nextLinks = enumLink.getNextLinks();
                            linkageLinks.addAll(nextLinks);
                            linkageLinks.addAll(preLinks);
                            for (String linkKey : linkageLinks) {
                                if (linkageQuestion.containsKey(linkKey)) continue;
                                Element element = (Element)valueBean;
                                element.setReadOnly(Boolean.valueOf(true));
                                lackEnum = true;
                                continue block7;
                            }
                        }
                    }
                    if (hasEnum || lackEnum) {
                        surveyJson = objectMapper.writeValueAsString((Object)surveyModel);
                    }
                }
            }
            catch (Exception e2) {
                logger.error("\u95ee\u5377\u8868\u6837\u8f6c\u6362\u62a5\u9519\uff01", e2);
            }
        }
        return surveyJson;
    }

    @Override
    public List<FormData> getAllReportsByFormScheme(String formSchemeKey) {
        ArrayList<FormData> formDatas = new ArrayList<FormData>();
        List formDefines = null;
        try {
            formDefines = this.runtimeView.queryAllFormDefinesByFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            throw new NotFoundFormSchemeException(new String[]{formSchemeKey});
        }
        for (FormDefine formDefine : formDefines) {
            FormData formInfo = new FormData();
            formInfo.init(formDefine);
            formDatas.add(formInfo);
        }
        return formDatas;
    }

    @Override
    public Map<String, Map<String, FormFoldingDefine>> getFormFoldData(String formKey) {
        List formFoldingDefines = this.runtimeView.listFormFoldingByFormKey(formKey);
        if (formFoldingDefines == null || formFoldingDefines.isEmpty()) {
            return null;
        }
        HashMap<String, Map<String, FormFoldingDefine>> formFoldData = new HashMap<String, Map<String, FormFoldingDefine>>();
        HashMap<String, FormFoldingDefine> rowFoldData = new HashMap<String, FormFoldingDefine>();
        HashMap<String, FormFoldingDefine> colFoldData = new HashMap<String, FormFoldingDefine>();
        for (FormFoldingDefine formFoldingDefine : formFoldingDefines) {
            FormFoldingDirEnum direction = formFoldingDefine.getDirection();
            if (direction.getValue() == FormFoldingDirEnum.ROW_DIRECTION.getValue()) {
                rowFoldData.put(formFoldingDefine.getStartIdx().toString(), formFoldingDefine);
                continue;
            }
            colFoldData.put(formFoldingDefine.getStartIdx().toString(), formFoldingDefine);
        }
        formFoldData.put("ROWFOLD", rowFoldData);
        formFoldData.put("COLFOLD", colFoldData);
        return formFoldData;
    }

    @Override
    public List<RegionData> getRegions(String formKey) {
        ArrayList<RegionData> regions = new ArrayList<RegionData>();
        List allRegionDefines = this.runtimeView.getAllRegionsInForm(formKey);
        for (DataRegionDefine dataRegionDefine : allRegionDefines) {
            RegionData regionData = new RegionData();
            regionData.initialize(dataRegionDefine);
            regions.add(regionData);
        }
        return regions;
    }

    @Override
    public RegionData getRegion(String regionKey) {
        DataRegionDefine dataRegionDefine = this.runtimeView.queryDataRegionDefine(regionKey);
        if (null != dataRegionDefine) {
            RegionData regionData = new RegionData();
            regionData.initialize(dataRegionDefine);
            return regionData;
        }
        return null;
    }

    @Override
    public List<LinkData> getLinks(String regionKey) {
        ArrayList<LinkData> linkDatas = new ArrayList<LinkData>();
        DataRegionDefine dataRegionDefine = this.runtimeView.queryDataRegionDefine(regionKey);
        if (null != dataRegionDefine) {
            List allLinkDefines = new ArrayList();
            try {
                allLinkDefines = this.runtimeView.getAllLinksInRegion(regionKey);
            }
            catch (Exception e) {
                logger.error("\u67e5\u627e\u533a\u57df\u6240\u6709\u94fe\u63a5\u51fa\u9519\uff01", e);
            }
            HashMap<String, DataLinkDefine> linkPosMap = new HashMap<String, DataLinkDefine>();
            FormDefine form = this.runtimeView.queryFormById(dataRegionDefine.getFormKey());
            List queryDataLinkMapping = this.runtimeView.queryDataLinkMapping(form.getKey());
            FormSchemeDefine formscheme = this.runtimeView.getFormScheme(form.getFormScheme());
            for (DataLinkDefine dataLinkDefine : allLinkDefines) {
                Position position;
                if (0 == dataLinkDefine.getPosX() || 0 == dataLinkDefine.getPosY() || dataLinkDefine.getPosX() < dataRegionDefine.getRegionLeft() || dataLinkDefine.getPosY() < dataRegionDefine.getRegionTop() || dataLinkDefine.getPosX() > dataRegionDefine.getRegionRight() || dataLinkDefine.getPosY() > dataRegionDefine.getRegionBottom()) continue;
                LinkData linkData = LinkDataFactory.linkData(formscheme, form, dataLinkDefine, queryDataLinkMapping);
                if (linkData != null) {
                    linkDatas.add(linkData);
                }
                if (linkPosMap.containsKey((position = new Position(dataLinkDefine.getPosX(), dataLinkDefine.getPosY())).toString())) {
                    String errorMessage = String.format("\u94fe\u63a5\u53c2\u6570\u6709\u8bef\uff01\u5b58\u5728\u91cd\u590d\u884c\u53f7\u5217\u53f7\u7684\u94fe\u63a5\uff01\u5f53\u524d\u68c0\u6d4b\u7684\u53c2\u6570\uff0c\u62a5\u8868\uff1a%s\uff0c\u62a5\u8868key\uff1a%s\uff0c\u533a\u57df\uff1a%s\uff0c\u533a\u57dfkey\uff1a%s\uff0c\u94fe\u63a5\uff1a%s\uff0c\u94fe\u63a5key\uff1a%s\uff0c\u94fe\u63a5\u5750\u6807\u3010%s\uff0c%s\u3011\uff0c\u6307\u6807\uff1a%s\uff0c\u6307\u6807key\uff1a%s \u8bf7\u68c0\u67e5\u94fe\u63a5\u53c2\u6570\u914d\u7f6e\uff01", form.getTitle(), form.getKey(), dataRegionDefine.getTitle(), regionKey, dataLinkDefine.getTitle(), dataLinkDefine.getKey(), dataLinkDefine.getPosY(), dataLinkDefine.getPosX(), linkData.getZbtitle(), linkData.getZbid());
                    logger.error(errorMessage);
                    throw new RuntimeException(errorMessage);
                }
                linkPosMap.put(position.toString(), dataLinkDefine);
            }
            final DataRegionKind regionKind = dataRegionDefine.getRegionKind();
            if (DataRegionKind.DATA_REGION_SIMPLE != regionKind) {
                Collections.sort(linkDatas, new Comparator<LinkData>(){

                    @Override
                    public int compare(LinkData linkData0, LinkData linkData1) {
                        if (DataRegionKind.DATA_REGION_ROW_LIST == regionKind) {
                            return linkData0.getCol() - linkData1.getCol();
                        }
                        if (DataRegionKind.DATA_REGION_COLUMN_LIST == regionKind) {
                            return linkData0.getRow() - linkData1.getRow();
                        }
                        return 0;
                    }
                });
            }
            ArrayList enumLinkFieldPosPathList = new ArrayList();
            for (LinkData linkData : linkDatas) {
                EnumLinkData enumLink;
                if (!(linkData instanceof EnumLinkData) || (enumLink = (EnumLinkData)linkData).getEnumFieldPosMap() == null) continue;
                ArrayList<String> deletePos = new ArrayList<String>();
                ArrayList<String> newPosPath = new ArrayList<String>();
                for (Map.Entry<String, String> posMap : enumLink.getEnumFieldPosMap().entrySet()) {
                    String fieldPos = posMap.getValue();
                    DataLinkDefine linkDefine = (DataLinkDefine)linkPosMap.get(fieldPos);
                    if (linkDefine == null) continue;
                    if (enumLink.getKey().equals(linkDefine.getKey())) {
                        deletePos.add(posMap.getKey());
                        continue;
                    }
                    boolean findEnumLink = false;
                    for (String enumLinkFieldPosPath : enumLinkFieldPosPathList) {
                        if (!enumLinkFieldPosPath.endsWith(enumLink.getKey())) continue;
                        findEnumLink = true;
                        if (enumLinkFieldPosPath.contains(linkDefine.getKey())) {
                            deletePos.add(posMap.getKey());
                            break;
                        }
                        newPosPath.add(enumLinkFieldPosPath + "->" + linkDefine.getKey());
                    }
                    if (findEnumLink) continue;
                    newPosPath.add(enumLink.getKey() + "->" + linkDefine.getKey());
                }
                if (!deletePos.isEmpty()) {
                    for (String fieldPos : deletePos) {
                        enumLink.getEnumFieldPosMap().remove(fieldPos);
                    }
                }
                if (newPosPath.isEmpty()) continue;
                enumLinkFieldPosPathList.addAll(newPosPath);
            }
        }
        return linkDatas;
    }

    @Override
    public LinkData getLink(String linkDataKey) {
        DataLinkDefine dataLinkDefine = this.runtimeView.queryDataLinkDefine(linkDataKey);
        if (null != dataLinkDefine) {
            LinkData linkData = LinkDataFactory.linkData(dataLinkDefine);
            return linkData;
        }
        return null;
    }

    @Override
    public EntityViewData getEntity(String entityID) {
        EntityViewData entityData = new EntityViewData();
        if ("ADJUST".equals(entityID)) {
            EntityViewData entityViewData = new EntityViewData();
            entityViewData.setKey(entityID);
            entityViewData.setTitle("\u8c03\u6574\u671f");
            entityViewData.setDimensionName("ADJUST");
            EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(entityID);
            entityViewData.setEntityViewDefine(entityView);
            return entityViewData;
        }
        if (this.periodAdapter.isPeriodEntity(entityID)) {
            IPeriodEntity periodEntity = this.periodAdapter.getPeriodEntity(entityID);
            entityData.initialize(periodEntity);
        } else {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityID);
            entityData.initialize(entityDefine);
        }
        return entityData;
    }

    @Override
    public EntityViewData getDwEntity(String formSchemeKey) {
        return this.getDwEntity(formSchemeKey, true);
    }

    @Override
    public EntityViewData getDwEntity(String formSchemeKey, boolean querySum) {
        FormSchemeDefine formSchemeDefine = null;
        try {
            formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            logger.error("\u62a5\u8868\u65b9\u6848\u672a\u627e\u5230:" + formSchemeKey, e);
            throw new NotFoundFormSchemeException(new String[]{formSchemeKey.toString()});
        }
        if (formSchemeDefine == null) {
            return null;
        }
        EntityViewData unitEntity = this.getEntity(formSchemeDefine.getDw());
        if (StringUtils.isNotEmpty((String)DsContextHolder.getDsContext().getContextEntityId())) {
            unitEntity = this.getEntity(DsContextHolder.getDsContext().getContextEntityId());
            EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(DsContextHolder.getDsContext().getContextEntityId(), DsContextHolder.getDsContext().getContextFilterExpression());
            unitEntity.setEntityViewDefine(entityViewDefine);
        }
        unitEntity.setMasterEntity(true);
        unitEntity.setKind("TABLE_KIND_ENTITY");
        if (querySum) {
            unitEntity.setMinusSum(this.getMinusSumFlag(unitEntity));
        }
        return unitEntity;
    }

    private boolean getMinusSumFlag(EntityViewData entityViewData) {
        IEntityModel entityModel = null;
        try {
            entityModel = this.entityMetaService.getEntityModel(entityViewData.getKey());
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return false;
        }
        IEntityAttribute bblxField = null;
        if (entityModel != null) {
            bblxField = entityModel.getBblxField();
        }
        return bblxField != null;
    }

    @Override
    public EntityViewData getDataTimeEntity(String formSchemeKey) {
        FormSchemeDefine formSchemeDefine = null;
        try {
            formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            logger.error("\u62a5\u8868\u65b9\u6848\u672a\u627e\u5230:" + formSchemeKey, e);
            throw new NotFoundFormSchemeException(new String[]{formSchemeKey.toString()});
        }
        if (formSchemeDefine == null) {
            return null;
        }
        return this.getEntity(formSchemeDefine.getDateTime());
    }

    @Override
    public List<EntityViewData> getDimEntityList(String formSchemeKey) {
        FormSchemeDefine formSchemeDefine = null;
        try {
            formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            logger.error("\u62a5\u8868\u65b9\u6848\u672a\u627e\u5230:" + formSchemeKey, e);
            throw new NotFoundFormSchemeException(new String[]{formSchemeKey.toString()});
        }
        if (formSchemeDefine == null) {
            return null;
        }
        ArrayList<EntityViewData> dimEntitys = new ArrayList<EntityViewData>();
        if (StringUtils.isNotEmpty((String)formSchemeDefine.getDims())) {
            String[] dimEntityIds;
            for (String dimEntityId : dimEntityIds = formSchemeDefine.getDims().split(";")) {
                EntityViewData entityViewData = this.getEntity(dimEntityId);
                if (!entityViewData.getDimensionName().equals("MD_CURRENCY")) {
                    entityViewData.setShowDimEntity(this.checkShowDimEntity(formSchemeDefine.getKey(), dimEntityId));
                }
                EntityViewDefine entityViewDefine = this.runTimeViewController.getDimensionViewByFormSchemeAndEntity(formSchemeDefine.getKey(), dimEntityId);
                entityViewData.setEntityViewDefine(entityViewDefine);
                dimEntitys.add(entityViewData);
            }
        }
        return dimEntitys;
    }

    private boolean checkShowDimEntity(String formSchemeKey, String entityKey) {
        String dimensionEntity = this.runTimeViewController.getFormScheme(formSchemeKey).getDw();
        String id = this.formSchemeService.getDimAttributeByReportDim(formSchemeKey, entityKey);
        if (org.springframework.util.StringUtils.hasText(id)) {
            return this.entityMetaService.getEntityModel(dimensionEntity).getAttribute(id).isMultival();
        }
        return true;
    }

    @Override
    public List<EntityViewData> getEntityList(String formSchemeKey) {
        ArrayList<EntityViewData> entitys = new ArrayList<EntityViewData>();
        if (StringUtils.isEmpty((String)formSchemeKey)) {
            return entitys;
        }
        FormSchemeDefine formSchemeDefine = null;
        try {
            formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            logger.error("\u62a5\u8868\u65b9\u6848\u672a\u627e\u5230:" + formSchemeKey, e);
            throw new NotFoundFormSchemeException(new String[]{formSchemeKey.toString()});
        }
        if (formSchemeDefine != null) {
            entitys.add(this.getDwEntity(formSchemeKey));
            entitys.add(this.getDataTimeEntity(formSchemeKey));
            entitys.addAll(this.getDimEntityList(formSchemeKey));
        } else {
            String[] entityIds;
            for (String entityId : entityIds = formSchemeKey.split(";")) {
                entitys.add(this.getEntity(entityId));
            }
        }
        return entitys;
    }

    @Override
    public List<EntityViewData> getEntityList(List<String> entityKeys) {
        ArrayList<EntityViewData> entitys = new ArrayList<EntityViewData>();
        for (String entityId : entityKeys) {
            entitys.add(this.getEntity(entityId));
        }
        return entitys;
    }

    @Override
    public FormulaSchemeData getFormulaScheme(String formulaSchemeKey) {
        FormulaSchemeDefine formulaSchemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(formulaSchemeKey);
        if (null != formulaSchemeDefine) {
            FormulaSchemeData formulaSchemeData = new FormulaSchemeData();
            formulaSchemeData.init(formulaSchemeDefine);
            return formulaSchemeData;
        }
        return null;
    }

    @Override
    public FormulaSchemeData getSoluctionByDimensions(JtableContext jtableContext) {
        IExtractFormulaService extractFormulaService = (IExtractFormulaService)BeanUtil.getBean(IExtractFormulaService.class);
        FormulaSchemeData formulaSchemeData = new FormulaSchemeData();
        FormulaSchemeDefine formulaSchemeDefine = extractFormulaService.getSoluctionByDimensions(jtableContext);
        if (formulaSchemeDefine != null) {
            formulaSchemeData.init(formulaSchemeDefine);
        }
        return formulaSchemeData;
    }

    @Override
    public List<FormulaSchemeData> getFormulaSchemeDatasByFormScheme(String formSchemeKey) {
        ArrayList<FormulaSchemeData> formulaSchemeDatas = new ArrayList<FormulaSchemeData>();
        List formulaSchemeDefines = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey);
        for (FormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefines) {
            FormulaSchemeData formulaSchemeData = new FormulaSchemeData();
            formulaSchemeData.init(formulaSchemeDefine);
            formulaSchemeDatas.add(formulaSchemeData);
        }
        return formulaSchemeDatas;
    }

    @Override
    public String getCalculateFormulaJs(String formulaSchemeKey, String formKey) {
        String preJsByForm;
        FormDefine formDefine = this.runtimeView.queryFormById(formKey);
        FormulaSchemeDefine formulaSchemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(formulaSchemeKey);
        if (formDefine != null && formulaSchemeDefine != null && StringUtils.isNotEmpty((String)(preJsByForm = this.formulaRunTimeController.getCalculateJsFormulasInForm(formulaSchemeDefine.getKey(), formDefine.getKey())))) {
            return preJsByForm;
        }
        return "";
    }

    @Override
    public FormTableFields getForm(String formKey, String search) {
        FormDefine formDefine = this.runtimeView.queryFormById(formKey);
        FormTableFields formFieldData = new FormTableFields();
        formFieldData.setKey(formDefine.getKey());
        formFieldData.setCode(formDefine.getFormCode());
        formFieldData.setTitle(formDefine.getTitle());
        try {
            List allRegionDefines = this.runtimeView.getAllRegionsInForm(formKey);
            if (allRegionDefines != null) {
                for (DataRegionDefine regionDefine : allRegionDefines) {
                    List allLinkDefines = this.runtimeView.getAllLinksInRegion(regionDefine.getKey());
                    if (allLinkDefines == null) continue;
                    for (DataLinkDefine linkDefine : allLinkDefines) {
                        FieldDefine fieldDefine;
                        if (linkDefine.getPosX() < regionDefine.getRegionLeft() || linkDefine.getPosY() < regionDefine.getRegionTop() || linkDefine.getPosX() > regionDefine.getRegionRight() || linkDefine.getPosY() > regionDefine.getRegionBottom() || linkDefine.getLinkExpression() == null || (fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(linkDefine.getLinkExpression())) == null) continue;
                        List deployInfos = this.dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{fieldDefine.getKey()});
                        boolean isContainSearch = StringUtils.isNotEmpty((String)fieldDefine.getCode()) && fieldDefine.getCode().contains(search) || StringUtils.isNotEmpty((String)fieldDefine.getTitle()) && fieldDefine.getTitle().contains(search) || StringUtils.isNotEmpty((String)((DataFieldDeployInfo)deployInfos.get(0)).getFieldName()) && ((DataFieldDeployInfo)deployInfos.get(0)).getFieldName().contains(search);
                        if (!isContainSearch) continue;
                        FieldData fieldData = new FieldData();
                        fieldData.init(fieldDefine, regionDefine, linkDefine);
                        formFieldData.getFields().add(fieldData);
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return formFieldData;
    }

    @Override
    public FieldData getField(String fieldKey) {
        FieldDefine fieldDefine = null;
        try {
            fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(fieldKey);
        }
        catch (Exception e) {
            logger.error("\u6307\u6807\u672a\u627e\u5230", e);
            throw new NotFoundFieldException(new String[]{fieldKey});
        }
        if (null != fieldDefine) {
            FieldData fieldData = new FieldData();
            fieldData.init(fieldDefine);
            return fieldData;
        }
        ColumnModelDefine columnModel = this.dataModelService.getColumnModelDefineByID(fieldKey);
        if (null != columnModel) {
            FieldData fieldData = new FieldData();
            fieldData.init(columnModel);
            return fieldData;
        }
        return null;
    }

    @Override
    public TableData getTable(String tableKey) {
        TableDefine define = null;
        try {
            define = this.dataDefinitionRuntimeController.queryTableDefine(tableKey);
        }
        catch (Exception e) {
            logger.error("\u5b58\u50a8\u8868\u672a\u627e\u5230", e);
            throw new NotFoundTableDefineException(new String[]{tableKey});
        }
        if (null != define) {
            TableData tableData = new TableData();
            tableData.init(define);
            return tableData;
        }
        return null;
    }

    @Override
    public TableData queryTableDefineByCode(String tableCode) {
        TableDefine define = null;
        try {
            define = this.dataDefinitionRuntimeController.queryTableDefineByCode(tableCode);
        }
        catch (Exception e) {
            logger.error("\u5b58\u50a8\u8868\u672a\u627e\u5230", e);
            throw new NotFoundTableDefineException(new String[]{tableCode});
        }
        if (null != define) {
            TableData tableData = new TableData();
            tableData.init(define);
            return tableData;
        }
        return null;
    }

    @Override
    public List<FieldData> getALLFileField(String formSchemeKey) {
        ArrayList<FieldData> fileFields = new ArrayList<FieldData>();
        List<FormData> formDatas = this.getAllReportsByFormScheme(formSchemeKey);
        for (FormData formData : formDatas) {
            List<RegionData> regions = this.getRegions(formData.getKey());
            for (RegionData regionData : regions) {
                List<LinkData> links = this.getLinks(regionData.getKey());
                for (LinkData linkData : links) {
                    if (!(linkData instanceof FileLinkData)) continue;
                    FieldData field = this.getField(linkData.getZbid());
                    field.setDataLinkKey(linkData.getKey());
                    field.setRegionKey(regionData.getKey());
                    field.setFormKey(formData.getKey());
                    field.setFormTitle(formData.getTitle());
                    fileFields.add(field);
                }
            }
        }
        return fileFields;
    }

    @Override
    public IFMDMAttribute getFmdmParentField(String entityId, String formschemeKey) {
        FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
        try {
            fmdmAttributeDTO.setEntityId(entityId);
            fmdmAttributeDTO.setFormSchemeKey(formschemeKey);
            IFMDMAttribute fmdmParentField = this.FMDMAttributeService.getFMDMParentField(fmdmAttributeDTO);
            return fmdmParentField;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<String> getCalcDataLinks(JtableContext jtableContext) {
        Collection calcCellInfosByForm;
        if (jtableContext.getFormulaSchemeKey() != null && null != jtableContext.getFormKey() && null != (calcCellInfosByForm = this.formulaRunTimeController.getCalcCellDataLinks(jtableContext.getFormulaSchemeKey(), jtableContext.getFormKey()))) {
            ArrayList<String> calcDataLinks = new ArrayList<String>(calcCellInfosByForm.size());
            for (String dataLinkKey : calcCellInfosByForm) {
                DataLinkDefine dataLinkDefine = this.runtimeView.queryDataLinkDefine(dataLinkKey);
                if (dataLinkDefine == null || dataLinkDefine.getPosX() == 0 || dataLinkDefine.getPosY() == 0 || dataLinkDefine.getType() == DataLinkType.DATA_LINK_TYPE_FORMULA) continue;
                calcDataLinks.add(dataLinkKey);
            }
            return calcDataLinks;
        }
        return Collections.emptyList();
    }

    @Override
    public List<String> getExtractDataLinkList(JtableContext jtableContext) {
        IExtractFormulaService extractFormulaService = (IExtractFormulaService)BeanUtil.getBean(IExtractFormulaService.class);
        List<String> extractDataLinks = new ArrayList<String>();
        if (null != extractFormulaService && jtableContext.getDimensionSet() != null) {
            try {
                extractDataLinks = extractFormulaService.getExtractDataLinkList(jtableContext);
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a\u83b7\u53d6\u53d6\u6570\u5355\u5143\u683c\u62a5\u9519\uff0csql\u62a5\u9519\u4f1a\u5bfc\u81f4\u4e8b\u52a1\u53ea\u80fd\u56de\u6eda\u3002" + e.getMessage(), e);
            }
        }
        return extractDataLinks;
    }

    @Override
    public List<ExtractCellInfo> getExtractDataLinkList(JtableContext jtableContext, String formulaSchemaKey) {
        IExtractFormulaService extractFormulaService = (IExtractFormulaService)BeanUtil.getBean(IExtractFormulaService.class);
        List<ExtractCellInfo> extractDataLinks = new ArrayList<ExtractCellInfo>();
        if (null != extractFormulaService) {
            try {
                extractDataLinks = extractFormulaService.getExtractDataLinkList(jtableContext, formulaSchemaKey);
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a\u83b7\u53d6\u53d6\u6570\u5355\u5143\u683c\u62a5\u9519\uff0csql\u62a5\u9519\u4f1a\u5bfc\u81f4\u4e8b\u52a1\u53ea\u80fd\u56de\u6eda\u3002" + e.getMessage(), e);
            }
        }
        return extractDataLinks;
    }

    @Override
    public List<RegionTab> getRegionTabs(String regionKey) {
        List regionTabSetting;
        ArrayList<RegionTab> regionTabs = new ArrayList<RegionTab>();
        RegionSettingDefine regionSetting = this.runtimeView.getRegionSetting(regionKey);
        if (regionSetting != null && null != (regionTabSetting = regionSetting.getRegionTabSetting())) {
            for (RegionTabSettingDefine regionTabSettingDefine : regionTabSetting) {
                RegionTab regionTab = new RegionTab(regionTabSettingDefine);
                regionTabs.add(regionTab);
            }
        }
        return regionTabs;
    }

    @Override
    public List<RegionNumber> getRegionNumbers(String regionKey) {
        List rowNumberSetting;
        ArrayList<RegionNumber> regionNumbers = new ArrayList<RegionNumber>();
        RegionSettingDefine regionSetting = this.runtimeView.getRegionSetting(regionKey);
        if (regionSetting != null && null != (rowNumberSetting = regionSetting.getRowNumberSetting()) && rowNumberSetting.size() > 0) {
            RegionNumber regionNumber = new RegionNumber((RowNumberSetting)rowNumberSetting.get(0));
            regionNumbers.add(regionNumber);
        }
        return regionNumbers;
    }

    @Override
    public RegionNumber getRegionNumber(String regionKey) {
        List rowNumberSetting;
        RegionNumber regionNumber = null;
        RegionSettingDefine regionSetting = this.runtimeView.getRegionSetting(regionKey);
        if (regionSetting != null && null != (rowNumberSetting = regionSetting.getRowNumberSetting()) && rowNumberSetting.size() > 0) {
            regionNumber = new RegionNumber((RowNumberSetting)rowNumberSetting.get(0));
        }
        return regionNumber;
    }

    @Override
    public List<TableData> getAllTableInRegion(String reginKey) {
        ArrayList<TableData> tables = new ArrayList<TableData>();
        try {
            List fieldKeys = this.runtimeView.getFieldKeysInRegion(reginKey);
            List defines = this.dataDefinitionRuntimeController.queryTableDefinesByFields((Collection)fieldKeys);
            for (TableDefine tableDefine : defines) {
                TableData tableData = new TableData();
                tableData.init(tableDefine);
                tables.add(tableData);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return tables;
    }

    @Override
    public FieldData getFieldByCodeInTable(String fieldCode, String tableKey) {
        FieldDefine fieldDefine = null;
        try {
            fieldDefine = this.dataDefinitionRuntimeController.queryFieldByCodeInTable(fieldCode, tableKey);
        }
        catch (Exception e) {
            throw new NotFoundFieldException(new String[]{fieldCode});
        }
        if (null != fieldDefine) {
            FieldData fieldData = new FieldData();
            fieldData.init(fieldDefine);
            return fieldData;
        }
        return null;
    }

    @Override
    public RegionSettingData getRegionSetting(String regionKey) {
        RegionSettingDefine regionSettingDefine = this.runtimeView.getRegionSetting(regionKey);
        if (null != regionSettingDefine) {
            RegionSettingData regionSettingData = new RegionSettingData();
            regionSettingData.init(regionSettingDefine);
            return regionSettingData;
        }
        return null;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public FormTables formfields(FieldQueryInfo fieldQueryInfo) {
        formFieldDataSet = new FormTables();
        if (StringUtils.isEmpty((String)fieldQueryInfo.getSearch())) {
            return formFieldDataSet;
        }
        formDefines = new ArrayList<FormDefine>();
        if (StringUtils.isNotEmpty((String)fieldQueryInfo.getFormKeys())) {
            idArray = fieldQueryInfo.getFormKeys().split(";");
            for (i = 0; i < idArray.length; ++i) {
                formKey = idArray[i];
                formDefine = this.runtimeView.queryFormById(formKey);
                formDefines.add(formDefine);
            }
        } else if (fieldQueryInfo.getContext().getFormSchemeKey() != null) {
            try {
                forms = this.runtimeView.queryAllFormDefinesByFormScheme(fieldQueryInfo.getContext().getFormSchemeKey());
                if (forms.isEmpty()) ** GOTO lbl23
                formDefines.addAll(forms);
            }
            catch (Exception e) {
                JtableParamServiceImpl.logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
lbl23:
        // 7 sources

        for (FormDefine form : formDefines) {
            formFieldData = this.getForm(form.getKey(), fieldQueryInfo.getSearch());
            formFieldDataSet.getTables().add(formFieldData);
        }
        return formFieldDataSet;
    }

    @Override
    public List<FormulaData> getFormulaList(FormulaQueryInfo formulaQueryInfo) {
        IExtractFormulaService extractFormulaService;
        Object formulaDefineList;
        ArrayList<FormulaData> formulas = new ArrayList<FormulaData>();
        if (formulaQueryInfo.getContext().getFormulaSchemeKey() == null) {
            return formulas;
        }
        FormulaSchemeDefine formulaSchemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(formulaQueryInfo.getContext().getFormulaSchemeKey());
        if (formulaSchemeDefine == null) {
            return formulas;
        }
        DataLinkDefine dataLinkDefine = null;
        if (formulaQueryInfo.getDataLinkKey() != null) {
            dataLinkDefine = this.runtimeView.queryDataLinkDefine(formulaQueryInfo.getDataLinkKey());
        }
        String balance = "balance";
        String check = "check";
        String calculate = "calculate";
        String EFDC = "EFDC";
        ArrayList<DataEngineConsts.FormulaType> formulaTypes = new ArrayList<DataEngineConsts.FormulaType>();
        String useType = formulaQueryInfo.getUseType();
        if (StringUtils.isNotEmpty((String)useType)) {
            if (useType.contains(balance) || useType.toLowerCase().contains(balance)) {
                formulaTypes.add(DataEngineConsts.FormulaType.BALANCE);
            }
            if (useType.contains(check) || useType.toLowerCase().contains(check)) {
                formulaTypes.add(DataEngineConsts.FormulaType.CHECK);
            }
            if (useType.contains(calculate) || useType.toLowerCase().contains(calculate)) {
                formulaTypes.add(DataEngineConsts.FormulaType.CALCULATE);
            }
        }
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formulaQueryInfo.getContext().getFormSchemeKey());
        for (DataEngineConsts.FormulaType formulaType : formulaTypes) {
            boolean isAnalysis = false;
            if (dataLinkDefine == null) {
                ArrayList<String> formKeys = new ArrayList<String>();
                if (formulaQueryInfo.getContext().getFormKey() != null) {
                    formKeys.add(formulaQueryInfo.getContext().getFormKey());
                }
                if (StringUtils.isNotEmpty((String)formulaQueryInfo.getFormKey())) {
                    String[] idArray = formulaQueryInfo.getFormKey().split(";");
                    for (int i = 0; i < idArray.length; ++i) {
                        String formKey = idArray[i];
                        formKeys.add(formKey);
                    }
                }
                if (formKeys.isEmpty()) {
                    List<FormulaData> formulaDatas = FormulaUtil.getFormulaDatas(formulaSchemeDefine.getKey(), null, formulaType);
                    if (formulaDatas == null) continue;
                    formulas.addAll(formulaDatas);
                    continue;
                }
                for (String formKey : formKeys) {
                    List<FormulaData> formulaDatas;
                    FormData formDefine = this.getReport(formKey, null);
                    if (formDefine == null || (formulaDatas = FormulaUtil.getFormulaDatas(formulaSchemeDefine.getKey(), formDefine.getKey(), formulaType)) == null) continue;
                    formulas.addAll(formulaDatas);
                }
                continue;
            }
            isAnalysis = true;
            ArrayList parsedExpressions = new ArrayList();
            parsedExpressions.addAll(this.formulaRunTimeController.getParsedExpressionByDataLink(dataLinkDefine.getUniqueCode(), formulaSchemeDefine.getKey(), null, formulaType));
            for (IParsedExpression parsedExpression : parsedExpressions) {
                FormulaData formulaInfo = new FormulaData(parsedExpression, this.runtimeView, isAnalysis, formulaQueryInfo.getShowType(), formulaSchemeDefine.getFormSchemeKey(), formulaQueryInfo.getAdjustorList(), this.dataDefinitionRuntimeController, environment);
                if (StringUtils.isNotEmpty((String)formulaInfo.getFormKey())) {
                    FormDefine formDefine = this.runtimeView.queryFormById(formulaInfo.getFormKey());
                    if (formDefine != null) {
                        formulaInfo.setFormTitle(formDefine.getTitle());
                    }
                } else {
                    formulaInfo.setFormTitle("\u8868\u95f4");
                }
                formulas.add(formulaInfo);
            }
        }
        if (useType.contains(EFDC) && (formulaDefineList = (extractFormulaService = (IExtractFormulaService)BeanUtil.getBean(IExtractFormulaService.class)).getEFDCFormulaInfo(formulaQueryInfo.getContext(), formulaQueryInfo.getDataLinkKey())) != null) {
            Iterator isAnalysis = formulaDefineList.iterator();
            while (isAnalysis.hasNext()) {
                FormulaDefine formulaDefine = (FormulaDefine)isAnalysis.next();
                FormulaData formulaData = new FormulaData(formulaDefine);
                formulaData.setType("EFDC");
                formulas.add(formulaData);
            }
        }
        ArrayList<String> formulaKeys = new ArrayList<String>();
        for (FormulaData formulaData : formulas) {
            formulaKeys.add(formulaData.getId());
        }
        Map formulaConditionMap = this.formulaRunTimeController.getParsedFormulaConditionExpression(formulaQueryInfo.getContext().getFormulaSchemeKey(), formulaKeys.toArray(new String[formulaKeys.size()]));
        if (!formulaConditionMap.isEmpty()) {
            LinkedHashSet execFml = new LinkedHashSet();
            for (String key : formulaConditionMap.keySet()) {
                execFml.addAll((Collection)formulaConditionMap.get(key));
            }
            FormulaCallBack callBack = this.formulaParseUtil.getFmlCallBack(new ArrayList(execFml), false);
            FCRunTimeServiceImpl.FormulaConditionMonitor formulaConditionMonitor = new FCRunTimeServiceImpl.FormulaConditionMonitor();
            IFormulaRunner runner = this.dataAccessProvider.newFormulaRunner(callBack);
            CheckParam checkParam = new CheckParam();
            checkParam.setFormulaSchemeKey(formulaQueryInfo.getContext().getFormulaSchemeKey());
            checkParam.setVariableMap(formulaQueryInfo.getContext().getVariableMap());
            DimensionValueSet dimExeC = DimensionUtil.getDimensionValueSet(formulaQueryInfo.getContext().getDimensionSet());
            ExecutorContext fmlExecutorContext = this.formulaParseUtil.getExecutorContext((BaseEnv)checkParam, dimExeC);
            try {
                runner.prepareCheck(fmlExecutorContext, null, null);
                runner.setMasterKeyValues(dimExeC);
                runner.run((IMonitor)formulaConditionMonitor);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            Iterator it = formulas.iterator();
            block11: while (it.hasNext()) {
                FormulaData formulaData = (FormulaData)it.next();
                if (!formulaConditionMap.containsKey(formulaData.getId()) || ((List)formulaConditionMap.get(formulaData.getId())).size() <= 0) continue;
                List formulaCondition = (List)formulaConditionMap.get(formulaData.getId());
                for (IParsedExpression checkExpression : formulaCondition) {
                    if (formulaConditionMonitor.getResult().containsKey(checkExpression.getKey())) {
                        it.remove();
                        continue block11;
                    }
                    if (!formulaConditionMonitor.getFloatError().containsKey(checkExpression.getKey()) || !formulaConditionMonitor.getFloatError().get(checkExpression.getKey()).contains(formulaQueryInfo.getFloatId())) continue;
                    it.remove();
                    continue block11;
                }
            }
        }
        String dataLinkKey = "";
        if (dataLinkDefine != null) {
            dataLinkKey = dataLinkDefine.getKey();
        }
        final String orderDataLinkKey = dataLinkKey;
        try {
            Collections.sort(formulas, new Comparator<FormulaData>(){

                @Override
                public int compare(FormulaData formula0, FormulaData formula1) {
                    if (formula0 == null && formula1 == null) {
                        return 0;
                    }
                    if (formula0 == null) {
                        return -1;
                    }
                    if (formula1 == null) {
                        return 1;
                    }
                    if (StringUtils.isNotEmpty((String)orderDataLinkKey)) {
                        String assignDataLinkKey0 = formula0.getAssignDataLinkKey();
                        String assignDataLinkKey1 = formula1.getAssignDataLinkKey();
                        if (StringUtils.isNotEmpty((String)assignDataLinkKey0) && orderDataLinkKey.contains(assignDataLinkKey0)) {
                            return -1;
                        }
                        if (StringUtils.isNotEmpty((String)assignDataLinkKey1) && orderDataLinkKey.contains(assignDataLinkKey1)) {
                            return 1;
                        }
                    }
                    if (!formula0.getCode().equals(formula1.getCode())) {
                        return formula0.getCode().compareTo(formula1.getCode());
                    }
                    return 0;
                }
            });
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return formulas;
    }

    @Override
    public HashMap<String, Integer> getFormulaListSize(FormulaQueryInfo formulaQueryInfo) {
        HashMap<String, Integer> formAndSize = new HashMap<String, Integer>(100);
        FormulaSchemeDefine formulaSchemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(formulaQueryInfo.getContext().getFormulaSchemeKey());
        if (formulaSchemeDefine == null) {
            return null;
        }
        List allFormDefines = new ArrayList();
        try {
            allFormDefines = this.runtimeView.queryAllFormDefinesByFormScheme(formulaSchemeDefine.getFormSchemeKey());
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        DataLinkDefine dataLinkDefine = null;
        if (formulaQueryInfo.getDataLinkKey() != null) {
            dataLinkDefine = this.runtimeView.queryDataLinkDefine(formulaQueryInfo.getDataLinkKey());
        }
        DataEngineConsts.FormulaType formulaType = DataEngineConsts.FormulaType.CALCULATE;
        String balance = "balance";
        String check = "check";
        String calculate = "calculate";
        if (balance.equals(formulaQueryInfo.getUseType())) {
            formulaType = DataEngineConsts.FormulaType.BALANCE;
        } else if (check.equals(formulaQueryInfo.getUseType())) {
            formulaType = DataEngineConsts.FormulaType.CHECK;
        } else if (calculate.equals(formulaQueryInfo.getUseType())) {
            formulaType = DataEngineConsts.FormulaType.CALCULATE;
        }
        if (dataLinkDefine == null) {
            List<FormulaData> formulaDatas = FormulaUtil.getFormulaDatas(formulaSchemeDefine.getKey(), null, formulaType);
            if (formulaDatas != null) {
                formAndSize.put("betweenParsed", formulaDatas.size());
            }
            for (FormDefine formDefine : allFormDefines) {
                List<FormulaData> formulaDatas2 = FormulaUtil.getFormulaDatas(formulaSchemeDefine.getKey(), formDefine.getKey(), formulaType);
                formAndSize.put(formDefine.getKey(), formulaDatas2.size());
            }
        }
        return formAndSize;
    }

    @Override
    public String getDeployUpdate(String checkKey) {
        try {
            return this.runtimeDeployTimeService.queryTime(checkKey);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public String getReportType(String formKey) {
        FormDefine formDefine = this.runtimeView.queryFormById(formKey);
        if (null != formDefine) {
            return formDefine.getFormType().name();
        }
        return null;
    }

    @Override
    public HashSet<String> getConditionFieldsByFormScheme(String formSchemeKey) {
        HashSet reloadFields = this.formulaRunTimeController.getConditionFieldsByFormScheme(formSchemeKey);
        return reloadFields;
    }

    @Override
    public HashMap<String, String> getFormulaMeanings(String taskKey, String formSchemeKey, String formulaSchemeKey, String formKey) throws Exception {
        HashMap<String, String> formulaMeaningsMap = new HashMap<String, String>();
        List<IParsedExpression> parsedExpressions = FormulaUtil.getParsedExpressions(formulaSchemeKey, formKey, DataEngineConsts.FormulaType.CALCULATE);
        if (parsedExpressions == null || parsedExpressions.size() == 0) {
            return formulaMeaningsMap;
        }
        ArrayList<FormulaData> formulaDatas = new ArrayList<FormulaData>();
        String value = this.taskOptionController.getValue(taskKey, "FORM_FX_SHOW");
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeKey);
        for (IParsedExpression parsedExpression : parsedExpressions) {
            if ("00000000-0000-0000-0000-000000000000".equals(parsedExpression.getFormKey()) || parsedExpression.getAssignNode() == null) continue;
            FormulaData formulaInfo = new FormulaData(parsedExpression, this.runtimeView, true, 0, formSchemeKey, null, this.dataDefinitionRuntimeController, environment);
            formulaDatas.add(formulaInfo);
        }
        for (FormulaData formulaData : formulaDatas) {
            if (!StringUtils.isNotEmpty((String)formulaData.getAssignDataLinkKey())) continue;
            if (value.equals("0")) {
                if (!StringUtils.isNotEmpty((String)formulaData.getMeanning())) continue;
                formulaMeaningsMap.put(formulaData.getAssignDataLinkKey(), formulaData.getMeanning());
                continue;
            }
            String formulaText = formulaData.getFormula();
            formulaMeaningsMap.put(formulaData.getAssignDataLinkKey(), formulaText);
        }
        return formulaMeaningsMap;
    }

    private HashMap<String, Set<String>> getDataLinksCacheByField(String formSchemeKey, String formKey) throws Exception {
        HashMap<String, Set<String>> dataLinksByField = new HashMap<String, Set<String>>();
        List dataRegionDefines = this.runTimeViewController.getAllRegionsInForm(formKey);
        for (DataRegionDefine dataRegionDefine : dataRegionDefines) {
            List dataLinkDefines = this.runTimeViewController.getAllLinksInRegion(dataRegionDefine.getKey());
            for (DataLinkDefine dataLinkDefine : dataLinkDefines) {
                FieldDefine fieldDefine;
                String fieldId = dataLinkDefine.getLinkExpression();
                if (dataLinkDefine.getType() == DataLinkType.DATA_LINK_TYPE_FMDM) {
                    FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
                    String entityId = formScheme.getDw();
                    IEntityModel entityModel = this.iEntityMetaService.getEntityModel(entityId);
                    IEntityAttribute attribute = entityModel.getAttribute(fieldId);
                    if (attribute == null) continue;
                    if (dataLinksByField.containsKey(attribute.getID())) {
                        dataLinksByField.get(attribute.getID()).add(dataLinkDefine.getKey());
                        continue;
                    }
                    LinkedHashSet<String> setValue = new LinkedHashSet<String>();
                    setValue.add(dataLinkDefine.getKey());
                    dataLinksByField.put(attribute.getID(), setValue);
                    continue;
                }
                if (dataLinkDefine.getType() != DataLinkType.DATA_LINK_TYPE_FIELD || (fieldDefine = this.dataDefinitionController.queryFieldDefine(fieldId)) == null) continue;
                ColumnModelDefine columnModelDefine = this.couModelFinder.findColumnModelDefine(fieldDefine);
                if (dataLinksByField.containsKey(columnModelDefine.getID())) {
                    dataLinksByField.get(columnModelDefine.getID()).add(dataLinkDefine.getKey());
                    continue;
                }
                LinkedHashSet<String> setValue = new LinkedHashSet<String>();
                setValue.add(dataLinkDefine.getKey());
                dataLinksByField.put(columnModelDefine.getID(), setValue);
            }
        }
        return dataLinksByField;
    }

    @Override
    public CStyleFile getStyleFormulaJs(String taskKey, String formKey) {
        return this.icsRunTimeService.getStyleFormulasInForm(taskKey, formKey);
    }

    @Override
    public int getDefaultDecimal(String taskKey) {
        String defaultDecimalString = this.iTaskOptionController.getValue(taskKey, "DEFAULT_DECIMAL");
        if (StringUtils.isNotEmpty((String)defaultDecimalString) && StringUtils.isNumeric((String)defaultDecimalString)) {
            return Integer.parseInt(defaultDecimalString);
        }
        return -1;
    }

    @Override
    public FormulaConditionFile getFormulaConditionJs(String taskKey, String formulaSchemeKey, String formKey) {
        return this.ifcRunTimeService.getFormulaConditionInForm(taskKey, formulaSchemeKey, formKey);
    }

    @Override
    public FormulaConditionFile getFormulaConditionJs(JtableContext jtableContext) {
        return this.ifcRunTimeService.getFormulaConditionInForm(jtableContext);
    }

    @Override
    public String getCurFormula(FormulaQueryInfo formulaQueryInfo) {
        FormulaSchemeDefine formulaSchemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(formulaQueryInfo.getContext().getFormulaSchemeKey());
        DataLinkDefine dataLinkDefine = null;
        if (formulaQueryInfo.getDataLinkKey() != null) {
            dataLinkDefine = this.runtimeView.queryDataLinkDefine(formulaQueryInfo.getDataLinkKey());
        }
        if (formulaSchemeDefine == null || dataLinkDefine == null) {
            return null;
        }
        List parsedExpressions = this.formulaRunTimeController.getParsedExpressionByDataLink(dataLinkDefine.getUniqueCode(), formulaSchemeDefine.getKey(), formulaQueryInfo.getFormKey(), DataEngineConsts.FormulaType.CALCULATE);
        if (parsedExpressions.size() == 0) {
            return null;
        }
        ArrayList<CalcExpression> calcExpressions = new ArrayList<CalcExpression>();
        HashMap<String, List> formulaConditionMap = new HashMap<String, List>();
        for (Object expression : parsedExpressions) {
            if (expression.getAssignNode().getDataModelLink() == null || !expression.getAssignNode().getDataModelLink().getDataLinkCode().equals(dataLinkDefine.getUniqueCode())) continue;
            if (expression.getConditions().size() > 0) {
                formulaConditionMap.put(expression.getSource().getId(), expression.getConditions());
            }
            calcExpressions.add((CalcExpression)expression);
        }
        Collections.sort(calcExpressions);
        if (!formulaConditionMap.isEmpty()) {
            LinkedHashSet execFml = new LinkedHashSet();
            for (String key : formulaConditionMap.keySet()) {
                execFml.addAll((Collection)formulaConditionMap.get(key));
            }
            FormulaCallBack callBack = this.formulaParseUtil.getFmlCallBack(new ArrayList(execFml), false);
            FCRunTimeServiceImpl.FormulaConditionMonitor formulaConditionMonitor = new FCRunTimeServiceImpl.FormulaConditionMonitor();
            IFormulaRunner runner = this.dataAccessProvider.newFormulaRunner(callBack);
            CheckParam checkParam = new CheckParam();
            checkParam.setFormulaSchemeKey(formulaQueryInfo.getContext().getFormulaSchemeKey());
            checkParam.setVariableMap(formulaQueryInfo.getContext().getVariableMap());
            DimensionValueSet dimExeC = DimensionUtil.getDimensionValueSet(formulaQueryInfo.getContext().getDimensionSet());
            ExecutorContext fmlExecutorContext = this.formulaParseUtil.getExecutorContext((BaseEnv)checkParam, dimExeC);
            try {
                runner.prepareCheck(fmlExecutorContext, null, null);
                runner.setMasterKeyValues(dimExeC);
                runner.run((IMonitor)formulaConditionMonitor);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            Iterator it = calcExpressions.iterator();
            block4: while (it.hasNext()) {
                CalcExpression calcExpression = (CalcExpression)it.next();
                if (!formulaConditionMap.containsKey(calcExpression.getSource().getId()) || ((List)formulaConditionMap.get(calcExpression.getSource().getId())).size() <= 0) continue;
                List formulaCondition = (List)formulaConditionMap.get(calcExpression.getSource().getId());
                for (IParsedExpression checkExpression : formulaCondition) {
                    if (formulaConditionMonitor.getResult().containsKey(checkExpression.getKey())) {
                        it.remove();
                        continue block4;
                    }
                    if (!formulaConditionMonitor.getFloatError().containsKey(checkExpression.getKey()) || !formulaConditionMonitor.getFloatError().get(checkExpression.getKey()).contains(formulaQueryInfo.getFloatId())) continue;
                    it.remove();
                    continue block4;
                }
            }
        }
        if (calcExpressions.size() == 0) {
            return null;
        }
        return ((CalcExpression)calcExpressions.get(calcExpressions.size() - 1)).getSource().getFormula();
    }
}

