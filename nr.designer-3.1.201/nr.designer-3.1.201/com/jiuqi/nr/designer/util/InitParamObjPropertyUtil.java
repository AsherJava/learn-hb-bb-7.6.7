/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.operator.OperatorNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.definition.impl.common.DefinitionHelper
 *  com.jiuqi.np.definition.impl.controller.DataDefinitionDesignTimeController2
 *  com.jiuqi.np.definition.impl.internal.FieldDefineImpl
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignAnalysisFormParamDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignEntityLinkageDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormFoldingDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignRegionSettingDefine
 *  com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.RegionEdgeStyleDefine
 *  com.jiuqi.nr.definition.facade.RegionTabSettingDefine
 *  com.jiuqi.nr.definition.facade.RowNumberSetting
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.impl.DesignFieldDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil
 *  com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink
 *  com.jiuqi.nr.definition.internal.impl.DesignFormSchemeDefineGetterImpl
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue$Type
 *  com.jiuqi.nr.definition.internal.impl.FormSchemeExtendPropsDefaultValue
 *  com.jiuqi.nr.definition.internal.impl.TaskOrgLinkDefineImpl
 *  com.jiuqi.nr.definition.internal.service.DesignFormDefineService
 *  com.jiuqi.nr.definition.internal.service.DesignFormFoldingService
 *  com.jiuqi.nr.definition.internal.service.TaskOrgLinkService
 *  com.jiuqi.nr.definition.util.EntityDefaultValue
 *  com.jiuqi.nr.definition.util.EntityValueType
 *  com.jiuqi.nr.definition.util.RecordCard
 *  com.jiuqi.nr.definition.util.ServeCodeService
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO
 *  com.jiuqi.nr.filterTemplate.service.IFilterTemplateService
 *  com.jiuqi.nr.period.common.utils.PeriodPropertyGroup
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nr.period.util.JacksonUtils
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  io.netty.util.internal.StringUtil
 */
package com.jiuqi.nr.designer.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.operator.OperatorNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.definition.impl.common.DefinitionHelper;
import com.jiuqi.np.definition.impl.controller.DataDefinitionDesignTimeController2;
import com.jiuqi.np.definition.impl.internal.FieldDefineImpl;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignAnalysisFormParamDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignEntityLinkageDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormFoldingDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;
import com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.RegionEdgeStyleDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.facade.RowNumberSetting;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.impl.DesignFieldDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.impl.DesignFormGroupLink;
import com.jiuqi.nr.definition.internal.impl.DesignFormSchemeDefineGetterImpl;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.definition.internal.impl.FormSchemeExtendPropsDefaultValue;
import com.jiuqi.nr.definition.internal.impl.TaskOrgLinkDefineImpl;
import com.jiuqi.nr.definition.internal.service.DesignFormDefineService;
import com.jiuqi.nr.definition.internal.service.DesignFormFoldingService;
import com.jiuqi.nr.definition.internal.service.TaskOrgLinkService;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import com.jiuqi.nr.definition.util.EntityValueType;
import com.jiuqi.nr.definition.util.RecordCard;
import com.jiuqi.nr.definition.util.ServeCodeService;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.helper.CommonHelper;
import com.jiuqi.nr.designer.helper.SaveSchemePeriodHelper;
import com.jiuqi.nr.designer.util.EntityDefaultValueObj;
import com.jiuqi.nr.designer.web.facade.AnalysisFormParamObj;
import com.jiuqi.nr.designer.web.facade.EntityLinkageObject;
import com.jiuqi.nr.designer.web.facade.EntityTables;
import com.jiuqi.nr.designer.web.facade.FlowsObj;
import com.jiuqi.nr.designer.web.facade.FormFoldingObj;
import com.jiuqi.nr.designer.web.facade.FormObj;
import com.jiuqi.nr.designer.web.facade.FormSchemeObj;
import com.jiuqi.nr.designer.web.facade.FunctionObj;
import com.jiuqi.nr.designer.web.facade.LinkObj;
import com.jiuqi.nr.designer.web.facade.NoTimePeriod;
import com.jiuqi.nr.designer.web.facade.OperatorObj;
import com.jiuqi.nr.designer.web.facade.ParameterObj;
import com.jiuqi.nr.designer.web.facade.RegionEdgeStyleObj;
import com.jiuqi.nr.designer.web.facade.RegionObj;
import com.jiuqi.nr.designer.web.facade.RegionTabSettingObj;
import com.jiuqi.nr.designer.web.facade.TaskObj;
import com.jiuqi.nr.designer.web.facade.TaskOrgListVO;
import com.jiuqi.nr.designer.web.facade.TaskOrgVO;
import com.jiuqi.nr.designer.web.facade.unusual.TableSupportDatedVersion;
import com.jiuqi.nr.designer.web.treebean.FieldObject;
import com.jiuqi.nr.designer.web.treebean.FormGroupObject;
import com.jiuqi.nr.designer.web.treebean.TableObject;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.filterTemplate.facade.FilterTemplateDTO;
import com.jiuqi.nr.filterTemplate.service.IFilterTemplateService;
import com.jiuqi.nr.period.common.utils.PeriodPropertyGroup;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.util.JacksonUtils;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class InitParamObjPropertyUtil {
    private static final Logger logger = LoggerFactory.getLogger(InitParamObjPropertyUtil.class);
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    @Qualifier(value="DataDefinitionDesignTimeController2")
    private DataDefinitionDesignTimeController2 npDesignTimeController;
    @Autowired
    private IDataDefinitionRuntimeController npRunTimeController;
    @Autowired
    private ServeCodeService serveCodeService;
    @Autowired
    private DesignFormDefineService formDefineService;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired
    private CommonHelper commonHelper;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private SaveSchemePeriodHelper schemePeriodHelper;
    @Autowired
    private IDesignTimeViewController iDesignTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    private IFilterTemplateService filterTemplateService;
    @Autowired
    private DesignFormFoldingService designFormFoldingService;
    @Autowired
    private TaskOrgLinkService taskOrgLinkService;

    public TaskObj setTaskObjProperty(DesignTaskDefine designTaskDefine) throws Exception {
        String tkMasterKey;
        TaskObj taskObject = new TaskObj();
        taskObject.setDw(designTaskDefine.getDw());
        taskObject.setDatetime(designTaskDefine.getDateTime());
        taskObject.setDims(designTaskDefine.getDims());
        taskObject.setDataScheme(designTaskDefine.getDataScheme());
        taskObject.setMeasureUnit(designTaskDefine.getMeasureUnit());
        taskObject.setID(designTaskDefine.getKey().toString());
        taskObject.setCode(designTaskDefine.getTaskCode());
        taskObject.setTitle(designTaskDefine.getTitle());
        taskObject.setFromPeriod(designTaskDefine.getFromPeriod());
        taskObject.setToPeriod(designTaskDefine.getToPeriod());
        taskObject.setDescription(designTaskDefine.getDescription());
        taskObject.setGatherType(designTaskDefine.getTaskGatherType().getValue());
        taskObject.setTaskPeriodOffset(designTaskDefine.getTaskPeriodOffset());
        taskObject.setFillInAutomaticallyDue(designTaskDefine.getFillInAutomaticallyDue());
        taskObject.setTaskType(designTaskDefine.getTaskType().getValue());
        taskObject.setOwnerLevelAndId(designTaskDefine.getOwnerLevelAndId());
        taskObject.setSameServeCode(this.serveCodeService.isSameServeCode(designTaskDefine.getOwnerLevelAndId()));
        taskObject.setEntityViewInEfdc(StringUtils.trim((String)(designTaskDefine.getEntityViewsInEFDC() == null ? "" : designTaskDefine.getEntityViewsInEFDC())));
        if (designTaskDefine.getFormulaSyntaxStyle() != null) {
            taskObject.setFormulaSyntaxStyle(designTaskDefine.getFormulaSyntaxStyle().getValue() - 1);
        }
        if (!StringUtils.isEmpty((String)(tkMasterKey = designTaskDefine.getMasterEntitiesKey()))) {
            List<EntityTables> entityTablesObjList = this.queryEntityFromView(tkMasterKey);
            taskObject.setEntityList(entityTablesObjList);
        }
        List<NoTimePeriod> noTimePeriodList = this.getNoTimePeriodListForTask(taskObject);
        taskObject.setNoTimePeriod(noTimePeriodList);
        taskObject.setFunctionList(this.getFunctionObjs());
        taskObject.setOperatorList(this.getOperatorObjs());
        TaskFlowsDefine designTaskFlowsDefine = designTaskDefine.getFlowsSetting();
        FlowsObj flowSObj = this.designFlowsDefineTransToFlowsObj(designTaskFlowsDefine, designTaskDefine.getKey());
        taskObject.setFlowsObj(flowSObj);
        taskObject.setEfdcSwitch(designTaskDefine.getEfdcSwitch());
        taskObject.setTableSupportDatedVersion(new TableSupportDatedVersion());
        if (designTaskDefine.getFilterTemplate() != null) {
            taskObject.setFilterSettingType(2);
            FilterTemplateDTO filterTemplate = this.filterTemplateService.getFilterTemplate(designTaskDefine.getFilterTemplate());
            taskObject.setFilterTemplate(filterTemplate.getFilterTemplateID());
        } else {
            taskObject.setFilterSettingType(1);
            taskObject.setFilterExpression(designTaskDefine.getFilterExpression());
        }
        taskObject.setFillingDateType(designTaskDefine.getFillingDateType().getValue());
        taskObject.setFillingDateDays(designTaskDefine.getFillingDateDays());
        this.setOrgList(taskObject);
        return taskObject;
    }

    private void setOrgList(TaskObj taskParam) throws DBParaException {
        ArrayList<TaskOrgVO> result = new ArrayList<TaskOrgVO>();
        List orgList = this.taskOrgLinkService.getByTask(taskParam.getID());
        if (CollectionUtils.isEmpty(orgList)) {
            TaskOrgLinkDefineImpl orgLinkDefine = new TaskOrgLinkDefineImpl();
            orgLinkDefine.setKey(UUIDUtils.getKey());
            orgLinkDefine.setTask(taskParam.getID());
            orgLinkDefine.setEntity(taskParam.getDw());
            orgLinkDefine.setEntityAlias(null);
            orgLinkDefine.setOrder(OrderGenerator.newOrder());
            this.taskOrgLinkService.insertTaskOrgLink(new TaskOrgLinkDefine[]{orgLinkDefine});
            result.add(new TaskOrgVO((TaskOrgLinkDefine)orgLinkDefine, this.iEntityMetaService));
        } else {
            for (TaskOrgLinkDefine org : orgList) {
                TaskOrgVO vo = new TaskOrgVO(org, this.iEntityMetaService);
                result.add(vo);
            }
        }
        taskParam.setOrgList(new TaskOrgListVO(result));
    }

    public FormSchemeObj setFormSchemeObjProperty(DesignFormSchemeDefine designFormSchemeDefine, DesignTaskDefine designTaskDefine) throws Exception {
        FormSchemeObj formSchemeObj = new FormSchemeObj();
        formSchemeObj.setID(designFormSchemeDefine.getKey());
        formSchemeObj.setDw(designFormSchemeDefine.getDw());
        formSchemeObj.setDatetime(designFormSchemeDefine.getDateTime());
        formSchemeObj.setDims(designFormSchemeDefine.getDims());
        formSchemeObj.setTaskId(designFormSchemeDefine.getTaskKey());
        formSchemeObj.setTitle(designFormSchemeDefine.getTitle());
        formSchemeObj.setOrder(designFormSchemeDefine.getOrder());
        formSchemeObj.setTableSupportDatedVersion(new TableSupportDatedVersion());
        formSchemeObj.setOwnerLevelAndId(designFormSchemeDefine.getOwnerLevelAndId());
        formSchemeObj.setSameServeCode(this.serveCodeService.isSameServeCode(designFormSchemeDefine.getOwnerLevelAndId()));
        this.fixSchemeObjEntity(designFormSchemeDefine, formSchemeObj, designTaskDefine);
        formSchemeObj.setFlowsObj(new FlowsObj());
        this.fixSchemeObjPeriod(formSchemeObj, designFormSchemeDefine);
        this.fixSchemeObjMeasureUnit(formSchemeObj, designFormSchemeDefine);
        this.fixFSFillInAutomaticallyDueIsExtend(formSchemeObj, designFormSchemeDefine);
        formSchemeObj.setEffectivePeriods(this.schemePeriodHelper.queryEffectivePeriods(formSchemeObj.getID()));
        formSchemeObj.setFilterExpression(designFormSchemeDefine.getFilterExpression());
        return formSchemeObj;
    }

    private void fixSchemeObjEntity(DesignFormSchemeDefine designFormSchemeDefine, FormSchemeObj formSchemeObj, DesignTaskDefine designTaskDefine) throws Exception {
        formSchemeObj.setEntitiesIsExtend(StringUtils.isEmpty((String)designFormSchemeDefine.getMasterEntitiesKey()));
        DesignFormSchemeDefineGetterImpl dfs = new DesignFormSchemeDefineGetterImpl(designFormSchemeDefine, designTaskDefine);
        String tkMasterKey = dfs.getMasterEntitiesKey();
        if (!StringUtils.isEmpty((String)tkMasterKey)) {
            List<EntityTables> entityTablesObjList = this.queryEntityFromView(tkMasterKey);
            formSchemeObj.setEntityList(entityTablesObjList);
        }
    }

    public FormGroupObject transFormGroupObject(DesignTaskDefine taskdefine, DesignFormGroupDefine designFormGroupDefine) throws JQException {
        FormGroupObject formGroup = new FormGroupObject();
        formGroup.setID(designFormGroupDefine.getKey());
        formGroup.setTitle(designFormGroupDefine.getTitle() == null ? "\u9ed8\u8ba4\u5206\u7ec4" : designFormGroupDefine.getTitle());
        formGroup.setTaskId(taskdefine.getKey());
        formGroup.setParentGroupID("");
        formGroup.setCondition(designFormGroupDefine.getCondition());
        formGroup.setFormSchemeKey(designFormGroupDefine.getFormSchemeKey());
        formGroup.setOrder(designFormGroupDefine.getOrder());
        formGroup.setCode(designFormGroupDefine.getCode());
        formGroup.setOwnerLevelAndId(designFormGroupDefine.getOwnerLevelAndId());
        formGroup.setSameServeCode(this.serveCodeService.isSameServeCode(designFormGroupDefine.getOwnerLevelAndId()));
        String measureUnit = designFormGroupDefine.getMeasureUnit();
        if (StringUtils.isEmpty((String)measureUnit)) {
            DesignFormSchemeDefine formScheme = this.nrDesignTimeController.queryFormSchemeDefine(formGroup.getFormSchemeKey());
            DesignFormSchemeDefineGetterImpl designFormSchemeDefineUtil = new DesignFormSchemeDefineGetterImpl(formScheme, taskdefine);
            formGroup.setMeasureUnit(designFormSchemeDefineUtil.getMeasureUnit());
            formGroup.setMeasureUnitIsExtend(true);
        } else {
            formGroup.setMeasureUnit(measureUnit);
            formGroup.setMeasureUnitIsExtend(true);
        }
        return formGroup;
    }

    public FormObj setFormObjProperty(DesignFormDefine formDefine, String taskDefineKey, String formGroupKey, boolean bigDataChanged, boolean needLoadFormData) throws Exception {
        FormObj formObject = new FormObj();
        formObject.setID(formDefine.getKey());
        formObject.setTitle(formDefine.getTitle());
        formObject.setCode(formDefine.getFormCode());
        formObject.setOrder(formDefine.getOrder());
        formObject.setTaskId(taskDefineKey);
        List formGroups = this.nrDesignTimeController.getFormGroupsByFormId(formDefine.getKey());
        if (formGroups.size() > 1) {
            formObject.setOwnGroupIdJoint(formGroups.stream().map(group -> group.getKey()).collect(Collectors.joining(";")));
        } else {
            formObject.setOwnGroupIdJoint(formGroupKey);
        }
        formObject.setOwnGroupId(formGroupKey);
        FormType formType = formDefine.getFormType();
        int typeValue = FormType.FORM_TYPE_FIX.getValue();
        if (formType != null && formType != FormType.FORM_TYPE_ENTITY && formType != FormType.FORM_TYPE_FMDM && formType != FormType.FORM_TYPE_INTERMEDIATE) {
            typeValue = formType.getValue();
        }
        formObject.setFormType(typeValue);
        formObject.setUnGather(formDefine.getIsGather());
        formObject.setMobileView(String.valueOf(formDefine.getFormViewType() == null ? 0 : formDefine.getFormViewType().getValue()));
        formObject.setSerialNumber(formDefine.getSerialNumber());
        this.dealFormEntities(formObject, formDefine, formDefine.getKey(), formGroupKey, true);
        this.dealFormMeasureUnit(formObject, formDefine, formGroupKey);
        this.dealFillInAutomaticallyDue(formObject, formDefine, formGroupKey);
        formObject.setActiveCondition(formDefine.getFormCondition());
        formObject.setFillingGuide(formDefine.getFillingGuide());
        formObject.setSecretRank(formDefine.getSecretLevel());
        formObject.setScriptEditor(formDefine.getScriptEditor());
        formObject.setformOnlyReadExp(formDefine.getReadOnlyCondition());
        if (needLoadFormData) {
            formObject.setFormStyle(Grid2Data.bytesToGrid((byte[])formDefine.getBinaryData()));
            if (formDefine.getFormType() == FormType.FORM_TYPE_SURVEY) {
                formObject.setSurveyData(formDefine.getSurveyData());
            }
            formObject.setRegions(this.getFormObjRegions(formDefine.getKey()));
        }
        formObject.setFormStatus(needLoadFormData ? 0 : 1);
        formObject.setOwnerLevelAndId(formDefine.getOwnerLevelAndId());
        formObject.setSameServeCode(this.serveCodeService.isSameServeCode(formDefine.getOwnerLevelAndId()));
        formObject.setQuoteType(formDefine.getQuoteType());
        List links = this.nrDesignTimeController.getFormGroupLinksByFormId(formObject.getID());
        HashMap<String, String> orderBygroup = new HashMap<String, String>();
        for (DesignFormGroupLink link : links) {
            if (StringUtils.isEmpty((String)link.getFormOrder())) {
                orderBygroup.put(link.getGroupKey(), formObject.getOrder());
                continue;
            }
            orderBygroup.put(link.getGroupKey(), link.getFormOrder());
        }
        formObject.setOrdersBygroup(orderBygroup);
        String order = formObject.getOrdersBygroup().get(formGroupKey);
        if (StringUtils.isNotEmpty((String)order)) {
            formObject.setOrder(order);
        }
        formObject.setAnalysisForm(formDefine.isAnalysisForm());
        if (formObject.isAnalysisForm()) {
            DesignAnalysisFormParamDefine paramDefine = this.nrDesignTimeController.queryAnalysisFormParamDefine(formDefine.getKey());
            AnalysisFormParamObj analysisParamObj = new AnalysisFormParamObj();
            AnalysisFormParamObj.toObj(analysisParamObj, paramDefine);
            formObject.setAnalysisFormParam(analysisParamObj);
        }
        formObject.setLedgerForm(formDefine.getLedgerForm());
        if (formDefine.getFormType() == FormType.FORM_TYPE_ANALYSISREPORT) {
            formObject.setAnalysisReportKey((String)formDefine.getExtensionProp("reportKey"));
        }
        formObject.setFormScheme(formDefine.getFormScheme());
        formObject.setReferFieldLinkData(this.iDesignTimeViewController.getDataLinkMappingVO(formDefine.getKey()));
        if (formDefine.getFormType() == FormType.FORM_TYPE_INSERTANALYSIS) {
            formObject.setInsertAnalysisGuid((String)formDefine.getExtensionProp("analysisGuid"));
            formObject.setInsertAnalysisTitle((String)formDefine.getExtensionProp("analysisTitle"));
        }
        formObject.setFormFoldingObjs(this.formFoldingConvert2VO(this.designFormFoldingService.getByFormKey(formDefine.getKey())));
        return formObject;
    }

    private List<FormFoldingObj> formFoldingConvert2VO(List<DesignFormFoldingDefine> formFoldingDefines) {
        ArrayList<FormFoldingObj> formFoldingObjs = new ArrayList<FormFoldingObj>();
        for (DesignFormFoldingDefine define : formFoldingDefines) {
            FormFoldingObj formFoldingObj = new FormFoldingObj();
            formFoldingObj.setKey(define.getKey());
            formFoldingObj.setFormKey(define.getFormKey());
            formFoldingObj.setStartIdx(define.getStartIdx());
            formFoldingObj.setEndIdx(define.getEndIdx());
            formFoldingObj.setHiddenRegion(define.getHiddenRegion());
            formFoldingObj.setDirection(define.getDirection().getValue());
            formFoldingObj.setFolding(define.isFolding());
            formFoldingObjs.add(formFoldingObj);
        }
        return formFoldingObjs;
    }

    public Map<String, RegionObj> getFormObjRegions(String formKey) throws Exception {
        List regionList;
        HashMap<String, RegionObj> regionMap = null;
        if (StringUtils.isNotEmpty((String)formKey) && (regionList = this.nrDesignTimeController.getAllRegionsInForm(formKey)) != null) {
            regionMap = new HashMap<String, RegionObj>();
            for (DesignDataRegionDefine regionDefine : regionList) {
                regionMap.put(regionDefine.getKey(), this.setRegionObjProperty(regionDefine));
            }
        }
        return regionMap;
    }

    public RegionObj setRegionObjProperty(DesignDataRegionDefine designDataRegionDefine) throws Exception {
        String regionSurveyData;
        RegionObj regionObj = new RegionObj();
        HashMap<String, Object> regionExtensionMap = new HashMap<String, Object>();
        regionObj.setCode(designDataRegionDefine.getCode());
        regionObj.setFormID(designDataRegionDefine.getFormKey());
        regionObj.setRegionKind(designDataRegionDefine.getRegionKind().getValue());
        regionObj.setInputOrderFieldID(designDataRegionDefine.getInputOrderFieldKey() == null ? "0" : designDataRegionDefine.getInputOrderFieldKey().toString());
        regionObj.setRowsInFloatRegion(designDataRegionDefine.getRowsInFloatRegion());
        regionObj.setRegionLeft(designDataRegionDefine.getRegionLeft());
        regionObj.setRegionRight(designDataRegionDefine.getRegionRight());
        regionObj.setRegionTop(designDataRegionDefine.getRegionTop());
        regionObj.setRegionBottom(designDataRegionDefine.getRegionBottom());
        regionObj.setRegionEnterNext(designDataRegionDefine.getRegionEnterNext().getValue());
        regionObj.setDisplayLevel(designDataRegionDefine.getDisplayLevel());
        List allLinksInRegion = this.nrDesignTimeController.getAllLinksInRegion(designDataRegionDefine.getKey());
        LinkedHashMap<String, LinkObj> linksMap = new LinkedHashMap<String, LinkObj>();
        ArrayList<String> allExpressionInLinks = new ArrayList<String>();
        for (DesignDataLinkDefine dataLinkDefine : allLinksInRegion) {
            allExpressionInLinks.add(dataLinkDefine.getLinkExpression());
            LinkObj linkObject = this.setLinkObjectProperty(dataLinkDefine);
            linkObject.setIsNew(false);
            linkObject.setIsDirty(false);
            linkObject.setIsDeleted(false);
            linksMap.put(dataLinkDefine.getKey(), linkObject);
        }
        regionObj.setLinks(linksMap);
        ArrayList<Object> tableIds = new ArrayList<Object>();
        List defines = new ArrayList();
        String[] fieldKeys = (String[])allLinksInRegion.stream().map(DataLinkDefine::getLinkExpression).filter(StringUtils::isNotEmpty).toArray(String[]::new);
        List feilds = this.npDesignTimeController.queryFieldDefines(fieldKeys);
        for (int i = 0; i < feilds.size(); ++i) {
            String id = ((DesignFieldDefine)feilds.get(i)).getOwnerTableKey();
            if (tableIds.contains(id)) continue;
            tableIds.add(id);
        }
        if (tableIds.size() > 0) {
            defines = this.npDesignTimeController.queryTableDefines(tableIds.toArray(new String[0]));
        }
        HashMap<String, TableObject> tableMap = new HashMap<String, TableObject>();
        for (DesignTableDefine tableDefine : defines) {
            boolean isFloatTable = false;
            DataTable dataTable = DefinitionHelper.toDataTable((TableDefine)tableDefine);
            if (dataTable.getDataTableType() == DataTableType.DETAIL || dataTable.getDataTableType() == DataTableType.ACCOUNT) {
                isFloatTable = true;
            }
            TableObject tableObject = new TableObject();
            tableObject.setID(tableDefine.getKey());
            String gatherType = String.valueOf(tableDefine.getGatherType().getValue());
            regionExtensionMap.put("GatherType", gatherType);
            tableObject.setBizKeyFieldsStr(tableDefine.getBizKeyFieldsStr());
            tableObject.setCode(tableDefine.getCode());
            tableObject.setTitle(tableDefine.getTitle());
            tableObject.setKind(tableDefine.getKind().getValue());
            tableObject.setOwnerLevelAndId(tableDefine.getOwnerLevelAndId());
            tableObject.setSameServeCode(this.serveCodeService.isSameServeCode(tableDefine.getOwnerLevelAndId()));
            tableObject.setTableType(dataTable.getDataTableType().getValue() + "");
            tableObject.setIdRecord(true);
            HashMap<String, FieldObject> fieldMap = new HashMap<String, FieldObject>();
            List fields = this.npDesignTimeController.getAllFieldsInTable(tableDefine.getKey());
            for (DesignFieldDefine field : fields) {
                FieldObject fieldObj = this.setFieldObjectProperty((FieldDefine)field);
                if (isFloatTable) {
                    fieldObj.setBizKeyOrder(fieldObj.getOwnerTableID());
                }
                fieldMap.put(fieldObj.getID(), fieldObj);
            }
            tableObject.setFields(fieldMap);
            tableMap.put(tableObject.getCode(), tableObject);
        }
        regionObj.setTables(tableMap);
        regionObj.setID(designDataRegionDefine.getKey());
        regionObj.setTitle(designDataRegionDefine.getTitle());
        regionObj.setOrder(designDataRegionDefine.getOrder());
        regionObj.setOwnerLevelAndId(designDataRegionDefine.getOwnerLevelAndId());
        regionExtensionMap.put("OwnerLevelAndId", designDataRegionDefine.getOwnerLevelAndId());
        regionExtensionMap.put("SameServeCode", this.serveCodeService.isSameServeCode(designDataRegionDefine.getOwnerLevelAndId()));
        regionObj.setSameServeCode(this.serveCodeService.isSameServeCode(designDataRegionDefine.getOwnerLevelAndId()));
        String sortFieldsList = designDataRegionDefine.getSortFieldsList();
        regionExtensionMap.put("SortFieldsList", sortFieldsList);
        String pageSize = String.valueOf(designDataRegionDefine.getPageSize());
        regionExtensionMap.put("PageSize", pageSize);
        String showGatherDetailRows = String.valueOf(designDataRegionDefine.getShowGatherDetailRows());
        regionExtensionMap.put("ShowGatherDetailRows", showGatherDetailRows);
        String showGatherDetailRowByOne = String.valueOf(designDataRegionDefine.getShowGatherDetailRowByOne());
        regionExtensionMap.put("ShowGatherDetailRowByOne", showGatherDetailRowByOne);
        String showGatherSummaryRow = String.valueOf(designDataRegionDefine.getShowGatherSummaryRow());
        regionExtensionMap.put("ShowGatherSummaryRow", showGatherSummaryRow);
        String filterCondition = designDataRegionDefine.getFilterCondition();
        regionExtensionMap.put("FilterCondition", filterCondition);
        String readOnlyCondition = designDataRegionDefine.getReadOnlyCondition();
        regionExtensionMap.put("ReadOnlyCondition", readOnlyCondition);
        boolean canChangeRow = designDataRegionDefine.getCanInsertRow();
        regionExtensionMap.put("CanChangeRow", canChangeRow);
        String maxRowCount = String.valueOf(designDataRegionDefine.getMaxRowCount());
        regionExtensionMap.put("MaxRowCount", maxRowCount);
        String rowsInFloatRegion = String.valueOf(designDataRegionDefine.getRowsInFloatRegion());
        regionExtensionMap.put("RowsInFloatRegion", rowsInFloatRegion);
        String gatherFields = "";
        if (designDataRegionDefine.getGatherFields() != null && !"".equals(designDataRegionDefine.getGatherFields())) {
            gatherFields = String.valueOf(designDataRegionDefine.getGatherFields());
        }
        regionExtensionMap.put("GatherFields", gatherFields);
        String gatherFieldRanks = "";
        if (designDataRegionDefine.getLevelSetting() != null && !"".equals(designDataRegionDefine.getLevelSetting())) {
            gatherFieldRanks = String.valueOf(designDataRegionDefine.getLevelSetting().toString());
        }
        regionExtensionMap.put("GatherFieldRanks", gatherFieldRanks);
        String hideGatherFields = StringUtils.isEmpty((String)designDataRegionDefine.getHideZeroGatherFields()) ? null : designDataRegionDefine.getHideZeroGatherFields();
        regionExtensionMap.put("HideGatherFields", hideGatherFields);
        String dictionaryFillLinks = "";
        DesignRegionSettingDefine designRegionSettingDefine = null;
        if (designDataRegionDefine.getRegionSettingKey() != null) {
            designRegionSettingDefine = this.nrDesignTimeController.getRegionSetting(designDataRegionDefine.getRegionSettingKey());
        }
        if (designRegionSettingDefine != null) {
            dictionaryFillLinks = designRegionSettingDefine.getDictionaryFillLinks();
            List regionTabSettingLists = designRegionSettingDefine.getRegionTabSetting();
            List lastRowStyles = designRegionSettingDefine.getLastRowStyles();
            if (regionTabSettingLists != null && regionTabSettingLists.size() > 0) {
                ArrayList<RegionTabSettingObj> regionTabSettingObjList = new ArrayList<RegionTabSettingObj>();
                for (RegionTabSettingDefine regionTabSettingData : regionTabSettingLists) {
                    RegionTabSettingObj regionTabSettingObj = new RegionTabSettingObj();
                    regionTabSettingObj.setID(regionTabSettingData.getId());
                    regionTabSettingObj.setTitle(regionTabSettingData.getTitle());
                    regionTabSettingObj.setDisplayCondition(regionTabSettingData.getDisplayCondition());
                    regionTabSettingObj.setFilterCondition(regionTabSettingData.getFilterCondition());
                    regionTabSettingObj.setBindingExpression(regionTabSettingData.getBindingExpression());
                    regionTabSettingObj.setOrder(regionTabSettingData.getOrder());
                    regionTabSettingObjList.add(regionTabSettingObj);
                }
                regionExtensionMap.put("RegionTabSettingDefines", regionTabSettingObjList);
            }
            if (lastRowStyles != null && lastRowStyles.size() > 0) {
                ArrayList<RegionEdgeStyleObj> regionEdgeStyleObjList = new ArrayList<RegionEdgeStyleObj>();
                for (RegionEdgeStyleDefine regionEdgeStyleData : lastRowStyles) {
                    RegionEdgeStyleObj regionEdgeStyleObj = new RegionEdgeStyleObj();
                    regionEdgeStyleObj.setStartIndex(regionEdgeStyleData.getStartIndex());
                    regionEdgeStyleObj.setEndIndex(regionEdgeStyleData.getEndIndex());
                    regionEdgeStyleObj.setEdgeStyle(regionEdgeStyleData.getEdgeLineStyle());
                    regionEdgeStyleObj.setEdgeLineColor(regionEdgeStyleObj.getEdgeLineColorToString(regionEdgeStyleData.getEdgeLineColor()));
                    regionEdgeStyleObjList.add(regionEdgeStyleObj);
                }
                regionExtensionMap.put("RegionEdgeStyleObjects", regionEdgeStyleObjList);
            }
            List rowNumberSettingList = designRegionSettingDefine.getRowNumberSetting();
            regionExtensionMap.put("RowNumberSettings", rowNumberSettingList);
            RecordCard cardRecord = null;
            cardRecord = designRegionSettingDefine.getCardRecord();
            String jsonString = JacksonUtils.objectToJson((Object)cardRecord);
            regionExtensionMap.put("CardRecord", jsonString);
            String showAddress = "";
            if (designDataRegionDefine.getShowGatherSummaryRow()) {
                showAddress = this.getShowAddress(designDataRegionDefine, designRegionSettingDefine.getRowNumberSetting());
            }
            regionExtensionMap.put("ShowAddress", showAddress);
        }
        if (designRegionSettingDefine != null) {
            List entityDefaultValueList = designRegionSettingDefine.getEntityDefaultValue();
            List<EntityDefaultValueObj> list = this.fillNode(entityDefaultValueList, designDataRegionDefine.getFormKey());
            String entityDefaultValue = JacksonUtils.objectToJson(list);
            regionExtensionMap.put("EntityDefaultValue", entityDefaultValue);
        }
        String showIsFold = String.valueOf(designDataRegionDefine.getIsCanFold());
        regionExtensionMap.put("IsCanFold", showIsFold);
        if (StringUtils.isNotEmpty((String)dictionaryFillLinks)) {
            if (allExpressionInLinks != null && allExpressionInLinks.size() > 0) {
                String[] split = dictionaryFillLinks.split(";");
                if (split.length == 0) {
                    dictionaryFillLinks = "";
                } else {
                    ArrayList<String> filterFill = new ArrayList<String>();
                    for (String s : split) {
                        for (int i = 0; i < allExpressionInLinks.size(); ++i) {
                            if (!allExpressionInLinks.contains(s) || filterFill.contains(s)) continue;
                            filterFill.add(s);
                        }
                    }
                    dictionaryFillLinks = filterFill.size() == 0 ? "" : filterFill.stream().collect(Collectors.joining(";"));
                }
            } else {
                dictionaryFillLinks = "";
            }
            if (dictionaryFillLinks.equals("")) {
                designRegionSettingDefine.setDictionaryFillLinks("");
                this.nrDesignTimeController.updateRegionSetting(designRegionSettingDefine);
            }
        }
        if (null != designRegionSettingDefine && null != (regionSurveyData = designRegionSettingDefine.getRegionSurvey())) {
            regionObj.setRegionCardSurvey(regionSurveyData);
        }
        regionObj.setDictionaryFillLinks(dictionaryFillLinks);
        regionExtensionMap.put("DictionaryFillLinks", dictionaryFillLinks);
        ObjectMapper mapper = new ObjectMapper();
        String regionExtension = mapper.writeValueAsString(regionExtensionMap);
        regionObj.setRegionExtension(regionExtension);
        regionObj.setIsNew(false);
        regionObj.setIsDirty(false);
        regionObj.setIsDeleted(false);
        return regionObj;
    }

    private List<EntityDefaultValueObj> fillNode(List<EntityDefaultValue> entityDefaultValueList, String formKey) {
        DesignFormDefine designFormDefine = this.iDesignTimeViewController.querySoftFormDefine(formKey);
        String formScheme = designFormDefine.getFormScheme();
        ArrayList<EntityDefaultValueObj> list = new ArrayList<EntityDefaultValueObj>();
        try {
            List designSchemePeriodLinkDefines = this.iDesignTimeViewController.querySchemePeriodLinkByScheme(formScheme);
            String periodKey = null;
            if (!designSchemePeriodLinkDefines.isEmpty()) {
                periodKey = ((DesignSchemePeriodLinkDefine)designSchemePeriodLinkDefines.get(0)).getPeriodKey();
            }
            for (EntityDefaultValue entityDefaultValue : entityDefaultValueList) {
                EntityDefaultValueObj defaultValueObj = new EntityDefaultValueObj(entityDefaultValue);
                if (entityDefaultValue.getEntityValueType() == EntityValueType.DATA_ITEM) {
                    String title = this.queryEntityTitle(entityDefaultValue.getEntityId(), entityDefaultValue.getItemValue(), periodKey);
                    defaultValueObj.setTitle(title);
                } else {
                    defaultValueObj.setTitle(entityDefaultValue.getItemValue());
                }
                list.add(defaultValueObj);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return list;
    }

    private String queryEntityTitle(String key, String code, String periodKey) throws Exception {
        if (periodKey == null) {
            return null;
        }
        IEntityDataService entityDataService = (IEntityDataService)SpringBeanUtils.getBean(IEntityDataService.class);
        IEntityViewRunTimeController runTimeController = (IEntityViewRunTimeController)SpringBeanUtils.getBean(IEntityViewRunTimeController.class);
        IDataDefinitionRuntimeController definitionRuntimeController = (IDataDefinitionRuntimeController)SpringBeanUtils.getBean(IDataDefinitionRuntimeController.class);
        EntityViewDefine entityViewDefine = runTimeController.buildEntityView(key);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)periodKey);
        IEntityQuery entityQuery = entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityViewDefine);
        entityQuery.setMasterKeys(dimensionValueSet);
        ExecutorContext executorContext = new ExecutorContext(definitionRuntimeController);
        IEntityTable iEntityTable = entityQuery.executeReader((IContext)executorContext);
        IEntityRow byCode = iEntityTable.findByCode(code);
        if (byCode != null) {
            return byCode.getTitle();
        }
        return null;
    }

    public LinkObj setLinkObjectProperty(DesignDataLinkDefine designDataLinkDefine) throws Exception {
        LinkObj linkObject = new LinkObj();
        linkObject.setPosX(designDataLinkDefine.getPosX());
        linkObject.setPosY(designDataLinkDefine.getPosY());
        linkObject.setLinkExpression(designDataLinkDefine.getLinkExpression());
        linkObject.setColNum(designDataLinkDefine.getColNum());
        linkObject.setRowNum(designDataLinkDefine.getRowNum());
        linkObject.setDataRegionID(designDataLinkDefine.getRegionKey().toString());
        linkObject.setEditMode(designDataLinkDefine.getEditMode() == null ? 0 : designDataLinkDefine.getEditMode().getValue());
        linkObject.setUniqueCode(designDataLinkDefine.getUniqueCode());
        linkObject.setDisplayMode(designDataLinkDefine.getDisplayMode() == null ? 0 : designDataLinkDefine.getDisplayMode().getValue());
        linkObject.setEnumCount(designDataLinkDefine.getEnumCount());
        linkObject.setLinkExpression(designDataLinkDefine.getLinkExpression());
        linkObject.setIsFormulaOrField(designDataLinkDefine.getType().getValue());
        linkObject.setEnumTitleField(designDataLinkDefine.getEnumTitleField());
        linkObject.setEnumLinkage(designDataLinkDefine.getEnumLinkage());
        linkObject.setEnumLinkageStatus(designDataLinkDefine.getEnumLinkageStatus());
        linkObject.setID(designDataLinkDefine.getKey().toString());
        linkObject.setTitle(designDataLinkDefine.getTitle());
        linkObject.setOrder(designDataLinkDefine.getOrder());
        if (designDataLinkDefine.getDisplayMode() != null) {
            linkObject.setDisplayMode(designDataLinkDefine.getDisplayMode().getValue());
        }
        if (designDataLinkDefine.getCaptionFieldsString() != null) {
            linkObject.setCaptionFieldsKeys(designDataLinkDefine.getCaptionFieldsString());
        }
        if (designDataLinkDefine.getDropDownFieldsString() != null) {
            linkObject.setDropDownFieldsString(designDataLinkDefine.getDropDownFieldsString());
        }
        linkObject.setAllowNotLeafNodeRefer(designDataLinkDefine.getAllowNotLeafNodeRefer());
        linkObject.setAllowUndefinedCode(designDataLinkDefine.getAllowUndefinedCode());
        linkObject.setAllowNullAble(designDataLinkDefine.getAllowNullAble());
        linkObject.setShowFullPath(designDataLinkDefine.getEnumShowFullPath());
        linkObject.setFormatProperties(designDataLinkDefine.getFormatProperties());
        linkObject.setOwnerLevelAndId(designDataLinkDefine.getOwnerLevelAndId());
        linkObject.setSameServeCode(this.serveCodeService.isSameServeCode(designDataLinkDefine.getOwnerLevelAndId()));
        linkObject.setEnumPosMap(designDataLinkDefine.getEnumPosMap());
        linkObject.setLinkExpression(designDataLinkDefine.getLinkExpression());
        linkObject.setIgnorePermissions(designDataLinkDefine.isIgnorePermissions());
        if (designDataLinkDefine.getFilterTemplate() != null) {
            linkObject.setFilterSettingType(2);
            linkObject.setFilterTemplate(designDataLinkDefine.getFilterTemplate());
        } else {
            linkObject.setFilterSettingType(1);
            linkObject.setFilterExpression(designDataLinkDefine.getFilterExpression());
        }
        linkObject.setMeasureUnit(designDataLinkDefine.getMeasureUnit());
        return linkObject;
    }

    public FieldObject setFieldObjectProperty(FieldDefine field) throws JQException {
        FieldObject fieldObj = new FieldObject();
        fieldObj.setCode(field.getCode());
        fieldObj.setTitle(field.getTitle());
        fieldObj.setSize(field.getSize());
        fieldObj.setID(field.getKey());
        fieldObj.setType(field.getType() == FieldType.FIELD_TYPE_FILE ? FieldType.FIELD_TYPE_BINARY.getValue() : field.getType().getValue());
        fieldObj.setValueType(field.getValueType().getValue());
        fieldObj.setFractionDigits(field.getFractionDigits());
        if (StringUtils.isNotEmpty((String)field.getOwnerTableKey())) {
            fieldObj.setOwnerTableID(field.getOwnerTableKey());
        } else {
            fieldObj.setOwnerTableID(null);
        }
        fieldObj.setGatherType(field.getGatherType().getValue());
        fieldObj.setDefaultValue(field.getDefaultValue());
        fieldObj.setDescription(field.getDescription());
        fieldObj.setFormatProperties(field.getFormatProperties());
        fieldObj.setNullable(field.getNullable());
        fieldObj.setFractionDigits(field.getFractionDigits());
        fieldObj.setMeasureUnit(field.getMeasureUnit());
        fieldObj.setAllowUndefinedCode(field.getAllowUndefinedCode());
        fieldObj.setUseAuthority(field.getUseAuthority());
        fieldObj.setOwnerLevelAndId(field.getOwnerLevelAndId());
        fieldObj.setSameServeCode(this.serveCodeService.isSameServeCode(field.getOwnerLevelAndId()));
        if (StringUtils.isNotEmpty((String)field.getEntityKey())) {
            if (field instanceof DesignFieldDefineImpl) {
                fieldObj.setReferField(((DesignFieldDefineImpl)field).getReferFieldKey());
            } else if (field instanceof FieldDefineImpl) {
                fieldObj.setReferField(((FieldDefineImpl)field).getReferFieldKey());
            }
            try {
                IEntityDefine queryEntity = this.iEntityMetaService.queryEntity(field.getEntityKey());
                fieldObj.setReferFieldName(queryEntity.getTitle());
                fieldObj.setReferFieldCode(queryEntity.getCode());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        fieldObj.setEntityKey(field.getEntityKey());
        return fieldObj;
    }

    public FlowsObj designFlowsDefineTransToFlowsObj(TaskFlowsDefine designTaskFlowsDefine, String key) throws Exception {
        FlowsObj flowSObj = new FlowsObj();
        if (designTaskFlowsDefine != null) {
            flowSObj.setSubmitEntityTables(designTaskFlowsDefine.getDesignTableDefines());
            flowSObj.setTaskId(key.toString());
            flowSObj.setFlowType(designTaskFlowsDefine.getFlowsType().getValue());
            flowSObj.setSubTable(designTaskFlowsDefine.isSubTable());
            flowSObj.setDataConfirm(designTaskFlowsDefine.isDataConfirm());
            flowSObj.setSubmitExplain(designTaskFlowsDefine.isSubmitExplain());
            flowSObj.setForceSubmitExplain(designTaskFlowsDefine.isForceSubmitExplain());
            flowSObj.setReturnVersion(designTaskFlowsDefine.isReturnVersion());
            flowSObj.setAllowTakeBack(designTaskFlowsDefine.isAllowTakeBack());
            flowSObj.setAllowModifyData(designTaskFlowsDefine.isAllowModifyData());
            flowSObj.setUnitSubmitForCensorship(designTaskFlowsDefine.isUnitSubmitForCensorship());
            flowSObj.setSelectedRoleId(designTaskFlowsDefine.getSelectedRoleKey());
            flowSObj.setCheckBeforeReporting(designTaskFlowsDefine.isCheckBeforeReporting());
            flowSObj.setCheckBeforeReportingType(designTaskFlowsDefine.getCheckBeforeReportingType().getValue());
            flowSObj.setCheckBeforeReportingCustom(designTaskFlowsDefine.getCheckBeforeReportingCustom());
            flowSObj.setBackDescriptionNeedWrite(designTaskFlowsDefine.isBackDescriptionNeedWrite());
            flowSObj.setAllowFormBack(designTaskFlowsDefine.isAllowFormBack());
            flowSObj.setStepByStepReport(designTaskFlowsDefine.getStepByStepReport());
            flowSObj.setStepByStepBack(designTaskFlowsDefine.getStepByStepBack());
            flowSObj.setUnitSubmitForForce(designTaskFlowsDefine.isUnitSubmitForForce());
            flowSObj.setSelectedRoleForceId(designTaskFlowsDefine.getSelectedRoleForceKey());
            flowSObj.setFilterCondition(designTaskFlowsDefine.getFilterCondition());
            flowSObj.setErroStatus(designTaskFlowsDefine.getErroStatus());
            flowSObj.setPromptStatus(designTaskFlowsDefine.getPromptStatus());
            flowSObj.setDefaultButtonName(designTaskFlowsDefine.getDefaultButtonName());
            flowSObj.setDefaultButtonNameConfig(designTaskFlowsDefine.getDefaultButtonNameConfig());
            flowSObj.setSendMessageMail(designTaskFlowsDefine.getSendMessageMail());
            flowSObj.setWorkFlowType(designTaskFlowsDefine.getWordFlowType().getValue() + "");
            flowSObj.setStepReportType(designTaskFlowsDefine.getStepReportType());
            flowSObj.setApplyReturn(designTaskFlowsDefine.isApplyReturn());
            flowSObj.setCheckBeforeFormula(designTaskFlowsDefine.getReportBeforeOperation());
            flowSObj.setCheckBeforeFormulaValue(designTaskFlowsDefine.getReportBeforeOperationValue());
            flowSObj.setMulCheckBeforeCheck(designTaskFlowsDefine.getRealMulCheckBeforeCheck());
            flowSObj.setCheckBeforeCheck(designTaskFlowsDefine.getReportBeforeAudit());
            flowSObj.setCheckBeforeCheckValue(designTaskFlowsDefine.getReportBeforeAuditValue());
            flowSObj.setCheckBeforeCheckType(designTaskFlowsDefine.getReportBeforeAuditType().getValue());
            flowSObj.setCheckBeforeCheckCustom(designTaskFlowsDefine.getReportBeforeAuditCustom());
            flowSObj.setSubmitAfterFormula(designTaskFlowsDefine.getSubmitAfterFormula());
            flowSObj.setSubmitAfterFormulaValue(designTaskFlowsDefine.getSubmitAfterFormulaValue());
            flowSObj.setMessageTemplate(designTaskFlowsDefine.getMessageTemplate());
            flowSObj.setGoBackAllSup(designTaskFlowsDefine.getGoBackAllSup());
            flowSObj.setOpenForceControl(designTaskFlowsDefine.isOpenForceControl());
            flowSObj.setDefaultNodeName(designTaskFlowsDefine.getDefaultNodeName());
            flowSObj.setDefaultNodeNameConfig(designTaskFlowsDefine.getDefaultNodeNameConfig());
            flowSObj.setSpecialAudit(designTaskFlowsDefine.getSpecialAudit());
            flowSObj.setOpenBackType(designTaskFlowsDefine.isOpenBackType());
            flowSObj.setBackTypeEntity(StringUtils.isEmpty((String)designTaskFlowsDefine.getBackTypeEntity()) ? " " : designTaskFlowsDefine.getBackTypeEntity());
            flowSObj.setReturnExplain(designTaskFlowsDefine.isReturnExplain());
            flowSObj.setAllowTakeBackForSubmit(designTaskFlowsDefine.isAllowTakeBackForSubmit());
        }
        return flowSObj;
    }

    public List<EntityTables> initEntityList(String masterKeys) throws Exception {
        return this.queryEntityFromView(masterKeys);
    }

    public HashMap<String, FunctionObj> getFunctionObjs() throws SyntaxException {
        ExecutorContext executorContext = new ExecutorContext(this.npRunTimeController);
        DefinitionsCache cache = new DefinitionsCache(executorContext);
        ReportFormulaParser formulaParser = cache.getFormulaParser(executorContext);
        Set functions = formulaParser.allFunctions();
        HashMap<String, FunctionObj> functionObjs = new HashMap<String, FunctionObj>();
        for (IFunction iFunction : functions) {
            FunctionObj functionObj = new FunctionObj(iFunction.name().toUpperCase(), iFunction.title(), iFunction.category(), iFunction.toDescription(), iFunction.parameters().size());
            functionObj.setIsInfiniteParameter(iFunction.isInfiniteParameter());
            functionObj.setInfiniteParameterCount(1);
            functionObj.setResultType(iFunction.getResultType(null, null));
            ArrayList<ParameterObj> parameterObjs = new ArrayList<ParameterObj>();
            for (IParameter parameter : iFunction.parameters()) {
                ParameterObj parameterObj = new ParameterObj(parameter.name(), parameter.title(), parameter.dataType(), parameter.isOmitable());
                parameterObjs.add(parameterObj);
            }
            functionObj.setParameterList(parameterObjs);
            functionObj.setParameterCount(parameterObjs.size());
            functionObjs.put(iFunction.name().toUpperCase(), functionObj);
        }
        return functionObjs;
    }

    public HashMap<String, List<OperatorObj>> getOperatorObjs() throws ParseException {
        ExecutorContext executorContext = new ExecutorContext(this.npRunTimeController);
        DefinitionsCache cache = new DefinitionsCache(executorContext);
        ReportFormulaParser formulaParser = cache.getFormulaParser(executorContext);
        Set operatorNodes = formulaParser.allExcelOperators();
        HashMap<String, List<OperatorObj>> operatorObjs = new HashMap<String, List<OperatorObj>>();
        for (OperatorNode operatorNode : operatorNodes) {
            List<Object> objs;
            OperatorObj operatorObj = new OperatorObj();
            operatorObj.setSign(operatorNode.sign());
            operatorObj.setPriority(100 - operatorNode.level());
            operatorObj.setPrevOperand(operatorNode.hasPrevOperand());
            operatorObj.setSuccOperand(operatorNode.hasSuccOperand());
            if (operatorObjs.containsKey(operatorNode.sign())) {
                objs = operatorObjs.get(operatorNode.sign());
                objs.add(operatorObj);
                continue;
            }
            objs = new ArrayList<OperatorObj>();
            objs.add(operatorObj);
            operatorObjs.put(operatorNode.sign(), objs);
        }
        return operatorObjs;
    }

    private List<EntityTables> queryEntityFromView(String masterKeys) {
        ArrayList<EntityTables> entityTablesObjList = new ArrayList<EntityTables>();
        if (!StringUtil.isNullOrEmpty((String)masterKeys)) {
            String[] masterKeyArr;
            for (String masterKey : masterKeyArr = masterKeys.split(";")) {
                IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
                if (periodAdapter.isPeriodEntity(masterKey)) {
                    TableModelDefine tableModelDefine = periodAdapter.getPeriodEntityTableModel(masterKey);
                    IPeriodEntity iPeriodByTableKey = periodAdapter.getIPeriodByTableKey(tableModelDefine.getID());
                    EntityTables entityTables = new EntityTables();
                    entityTables.setID(iPeriodByTableKey.getKey());
                    entityTables.setTitle(iPeriodByTableKey.getTitle());
                    EntityLinkageObject linkageObj = this.getEntityLinkageObj(iPeriodByTableKey.getKey());
                    entityTables.setEntityLinkageObject(linkageObj);
                    entityTables.setKind(TableKind.TABLE_KIND_ENTITY_PERIOD);
                    entityTables.setCode(iPeriodByTableKey.getCode());
                    entityTablesObjList.add(entityTables);
                    continue;
                }
                IEntityDefine iEntityDefine = this.iEntityMetaService.queryEntity(masterKey);
                if (iEntityDefine == null) continue;
                EntityTables entityTables = new EntityTables();
                entityTables.setID(iEntityDefine.getId());
                entityTables.setTitle(iEntityDefine.getTitle());
                EntityLinkageObject linkageObj = this.getEntityLinkageObj(iEntityDefine.getId());
                entityTables.setEntityLinkageObject(linkageObj);
                entityTables.setKind(TableKind.TABLE_KIND_ENTITY);
                entityTables.setCode(iEntityDefine.getCode());
                entityTablesObjList.add(entityTables);
            }
        }
        return entityTablesObjList;
    }

    public List<NoTimePeriod> getNoTimePeriodListForTask(TaskObj taskObject) {
        ArrayList<NoTimePeriod> noTimePeriods = new ArrayList<NoTimePeriod>();
        if (StringUtils.isNotEmpty((String)taskObject.getDatetime())) {
            IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(taskObject.getDatetime());
            List iPeriodRows = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntity.getKey()).getPeriodItems();
            for (IPeriodRow periodRow : iPeriodRows) {
                NoTimePeriod noTimePeriod = new NoTimePeriod();
                noTimePeriod.setTitle(periodRow.getTitle());
                noTimePeriod.setCode(periodRow.getCode());
                noTimePeriod.setNbCode(periodRow.getAlias());
                noTimePeriod.setStartPeriod(periodRow.getStartDate());
                noTimePeriod.setEndPeriod(periodRow.getEndDate());
                if (PeriodPropertyGroup.PERIOD_GROUP_BY_YEAR == periodEntity.getPeriodPropertyGroup()) {
                    noTimePeriod.setGroup(String.valueOf(periodRow.getYear()));
                }
                noTimePeriods.add(noTimePeriod);
            }
        }
        return noTimePeriods;
    }

    public EntityLinkageObject getEntityLinkageObj(String entityKey) {
        EntityLinkageObject entityLinkageObject = new EntityLinkageObject();
        DesignEntityLinkageDefine define = this.nrDesignTimeController.queryDesignEntityLinkageDefineByKey(entityKey);
        if (define == null) {
            return entityLinkageObject;
        }
        entityLinkageObject.setKey(define.getKey() == null ? null : define.getKey());
        entityLinkageObject.setMasterEntityKey(define.getMasterEntityKey() == null ? null : define.getMasterEntityKey());
        entityLinkageObject.setSlaveEntityKey(define.getSlaveEntityKeys() == null ? null : define.getSlaveEntityKeys());
        entityLinkageObject.setLinkageCondition(define.getLinkageCondition());
        return entityLinkageObject;
    }

    private void fixSchemeObjPeriod(FormSchemeObj formSchemeObj, DesignFormSchemeDefine designFormSchemeDefine) throws JQException {
        try {
            if (designFormSchemeDefine.getPeriodType() != FormSchemeExtendPropsDefaultValue.PERIOD_TYPE_EXTEND_VALUE) {
                formSchemeObj.setPeriodIsExtend(false);
            }
            DesignFormSchemeDefineGetterImpl define = new DesignFormSchemeDefineGetterImpl(designFormSchemeDefine);
            formSchemeObj.setPeriodType(define.getPeriodType().type());
            formSchemeObj.setSchemePeriodOffset(define.getPeriodOffset());
            formSchemeObj.setDueDateOffset(define.getDueDateOffset());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_106, (Throwable)e);
        }
    }

    private void fixSchemeObjMeasureUnit(FormSchemeObj formSchemeObj, DesignFormSchemeDefine designFormSchemeDefine) throws JQException {
        try {
            formSchemeObj.setMeasureUnitIsExtend(StringUtils.isEmpty((String)designFormSchemeDefine.getMeasureUnit()));
            formSchemeObj.setMeasureUnit(new DesignFormSchemeDefineGetterImpl(designFormSchemeDefine).getMeasureUnit());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_107, (Throwable)e);
        }
    }

    private void fixFSFillInAutomaticallyDueIsExtend(FormSchemeObj formSchemeObj, DesignFormSchemeDefine designFormSchemeDefine) throws Exception {
        formSchemeObj.setFillInAutomaticallyDueIsExtend(designFormSchemeDefine.getFillInAutomaticallyDue().getType() == FillInAutomaticallyDue.Type.DEFAULT.getValue());
        formSchemeObj.setFillInAutomaticallyDue(new DesignFormSchemeDefineGetterImpl(designFormSchemeDefine).getFillInAutomaticallyDue());
    }

    private void dealFormEntities(FormObj formObj, DesignFormDefine designFormDefine, String formKey, String formGroupKey, boolean needViews) throws JQException {
        try {
            if (StringUtils.isEmpty((String)designFormDefine.getMasterEntitiesKey())) {
                DesignFormGroupDefine formGroup = this.nrDesignTimeController.queryFormGroup(formGroupKey);
                DesignFormSchemeDefine formScheme = this.nrDesignTimeController.queryFormSchemeDefine(formGroup.getFormSchemeKey());
                DesignFormSchemeDefineGetterImpl designFormSchemeDefineUtil = new DesignFormSchemeDefineGetterImpl(formScheme);
                formObj.setMasterEntitiesKey(designFormSchemeDefineUtil.getMasterEntitiesKey());
                formObj.setEntitiesIsExtend(true);
            } else {
                formObj.setMasterEntitiesKey(designFormDefine.getMasterEntitiesKey());
                formObj.setEntitiesIsExtend(false);
            }
            formObj.setEntityList(this.returnEntityList(formObj.getMasterEntitiesKey(), needViews));
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_112, (Throwable)e);
        }
    }

    private void dealFormMeasureUnit(FormObj formObj, DesignFormDefine designFormDefine, String formGroupKey) throws JQException {
        try {
            if (StringUtils.isEmpty((String)designFormDefine.getMeasureUnit())) {
                DesignFormGroupDefine formGroup = this.nrDesignTimeController.queryFormGroup(formGroupKey);
                String formGroupMeasureUnit = formGroup.getMeasureUnit();
                if (StringUtils.isEmpty((String)formGroupMeasureUnit)) {
                    DesignFormSchemeDefine formScheme = this.nrDesignTimeController.queryFormSchemeDefine(formGroup.getFormSchemeKey());
                    DesignFormSchemeDefineGetterImpl designFormSchemeDefineUtil = new DesignFormSchemeDefineGetterImpl(formScheme);
                    formObj.setMeasureUnit(designFormSchemeDefineUtil.getMeasureUnit());
                } else {
                    formObj.setMeasureUnit(formGroupMeasureUnit);
                }
                formObj.setMeasureUnitIsExtend(true);
            } else {
                formObj.setMeasureUnit(designFormDefine.getMeasureUnit());
                formObj.setMeasureUnitIsExtend(false);
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_113, (Throwable)e);
        }
    }

    private void dealFillInAutomaticallyDue(FormObj formObj, DesignFormDefine designFormDefine, String formGroupKey) {
        if (designFormDefine.getFillInAutomaticallyDue().getType() == FillInAutomaticallyDue.Type.DEFAULT.getValue()) {
            DesignFormGroupDefine formGroupDefine = this.nrDesignTimeController.queryFormGroup(formGroupKey);
            DesignFormSchemeDefine formSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(formGroupDefine.getFormSchemeKey());
            DesignFormSchemeDefineGetterImpl formSchemeDefineGetter = new DesignFormSchemeDefineGetterImpl(formSchemeDefine);
            formObj.setFillInAutomaticallyDue(formSchemeDefineGetter.getFillInAutomaticallyDue());
            formObj.setFillInAutomaticallyDueIsExtend(true);
        } else {
            formObj.setFillInAutomaticallyDue(designFormDefine.getFillInAutomaticallyDue());
            formObj.setFillInAutomaticallyDueIsExtend(false);
        }
    }

    private String getShowAddress(DesignDataRegionDefine designDataRegionDefine, List<RowNumberSetting> rowNumberSettingList) {
        List designDataLinkDefineList = this.nrDesignTimeController.getAllLinksInRegion(designDataRegionDefine.getKey());
        String showAddress = designDataRegionDefine.getShowAddress();
        if (showAddress == null) {
            if (rowNumberSettingList != null) {
                int x = rowNumberSettingList.get(rowNumberSettingList.size() - 1).getPosX() + 1;
                int y = rowNumberSettingList.get(rowNumberSettingList.size() - 1).getPosY();
                String posXString = this.numberToLetter(x);
                if (posXString != null) {
                    showAddress = posXString + y;
                }
            } else {
                String posXString;
                int posX = 0;
                if (designDataLinkDefineList.size() != 0) {
                    posX = ((DesignDataLinkDefine)designDataLinkDefineList.get(0)).getPosX();
                }
                if ((posXString = this.numberToLetter(posX)) != null) {
                    showAddress = posXString + ((DesignDataLinkDefine)designDataLinkDefineList.get(0)).getPosY();
                }
            }
        }
        return showAddress;
    }

    public List<EntityTables> returnEntityList(String masterKey, boolean needViews) {
        String[] viewsArray;
        ArrayList<EntityTables> entityTableList = new ArrayList<EntityTables>();
        for (String view : viewsArray = masterKey.split(";")) {
            IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
            if (periodAdapter.isPeriodEntity(view)) {
                TableModelDefine tableModelDefine = periodAdapter.getPeriodEntityTableModel(view);
                IPeriodEntity iPeriodByTableKey = periodAdapter.getIPeriodByTableKey(tableModelDefine.getID());
                EntityTables entityTables = new EntityTables();
                entityTables.setID(iPeriodByTableKey.getKey());
                entityTables.setTitle(iPeriodByTableKey.getTitle());
                EntityLinkageObject linkageObj = this.getEntityLinkageObj(iPeriodByTableKey.getKey());
                entityTables.setEntityLinkageObject(linkageObj);
                entityTables.setKind(TableKind.TABLE_KIND_ENTITY_PERIOD);
                entityTables.setCode(iPeriodByTableKey.getCode());
                entityTableList.add(entityTables);
                continue;
            }
            IEntityDefine iEntityDefine = this.iEntityMetaService.queryEntity(view);
            if (iEntityDefine == null) continue;
            EntityTables entityTables = new EntityTables();
            entityTables.setID(iEntityDefine.getId());
            entityTables.setTitle(iEntityDefine.getTitle());
            EntityLinkageObject linkageObj = this.getEntityLinkageObj(iEntityDefine.getId());
            entityTables.setEntityLinkageObject(linkageObj);
            entityTables.setKind(TableKind.TABLE_KIND_ENTITY);
            entityTables.setCode(iEntityDefine.getCode());
            entityTableList.add(entityTables);
        }
        return entityTableList;
    }

    public String ObjectToIntegerStr(Object object) {
        String objectStr = String.valueOf(object);
        return objectStr.endsWith(".0") ? objectStr.substring(0, objectStr.length() - 2) : objectStr;
    }

    private String numberToLetter(int num) {
        if (num <= 0) {
            return null;
        }
        String letter = "";
        --num;
        do {
            if (letter.length() > 0) {
                --num;
            }
            letter = (char)(num % 26 + 65) + letter;
        } while ((num = (num - num % 26) / 26) > 0);
        return letter;
    }

    public FormObj setFormObjPropertyCopy(DesignTaskDefine designTaskDefine, DesignFormSchemeDefine formScheme, DesignFormGroupDefine formGroup, DesignFormDefine formDefine, boolean needLoadFormData, List<String> ownGroupKeys) throws Exception {
        FormObj formObject = new FormObj();
        formObject.setID(formDefine.getKey());
        formObject.setTitle(formDefine.getTitle());
        formObject.setCode(formDefine.getFormCode());
        formObject.setOrder(formDefine.getOrder());
        formObject.setTaskId(designTaskDefine.getKey());
        String joinGroupId = String.join((CharSequence)";", ownGroupKeys);
        if (ownGroupKeys.size() > 1) {
            formObject.setOwnGroupIdJoint(joinGroupId);
        } else {
            formObject.setOwnGroupIdJoint(formGroup.getKey());
        }
        formObject.setOwnGroupId(formGroup.getKey());
        FormType formType = formDefine.getFormType();
        int typeValue = FormType.FORM_TYPE_FIX.getValue();
        if (formType != null && formType != FormType.FORM_TYPE_ENTITY && formType != FormType.FORM_TYPE_FMDM && formType != FormType.FORM_TYPE_INTERMEDIATE) {
            typeValue = formType.getValue();
        }
        formObject.setFormType(typeValue);
        formObject.setQuoteType(formDefine.getQuoteType());
        formObject.setUnGather(formDefine.getIsGather());
        formObject.setMobileView(String.valueOf(formDefine.getFormViewType() == null ? 0 : formDefine.getFormViewType().getValue()));
        formObject.setSerialNumber(formDefine.getSerialNumber());
        this.dealFormEntitiesCopy(formObject, formDefine, formDefine.getKey(), formGroup.getKey(), true, formScheme, formGroup, designTaskDefine, needLoadFormData);
        this.dealFormMeasureUnitCopy(formObject, formDefine, formGroup.getKey(), formScheme, formGroup, designTaskDefine);
        this.dealFillInAutomaticallyDueCopy(formObject, formDefine, formScheme, designTaskDefine);
        formObject.setActiveCondition(formDefine.getFormCondition());
        formObject.setFillingGuide(DesignFormDefineBigDataUtil.bytesToString((byte[])this.formDefineService.getBigData(formDefine.getKey(), "FILLING_GUIDE")));
        formObject.setSecretRank(formDefine.getSecretLevel());
        formObject.setScriptEditor(formDefine.getScriptEditor());
        formObject.setformOnlyReadExp(formDefine.getReadOnlyCondition());
        formObject.setOwnerLevelAndId(formDefine.getOwnerLevelAndId());
        formObject.setSameServeCode(this.serveCodeService.isSameServeCode(formDefine.getOwnerLevelAndId()));
        if (needLoadFormData) {
            Grid2Data grid2Data = Grid2Data.bytesToGrid((byte[])this.formDefineService.getReportData(formDefine.getKey()));
            formObject.setFormStyle(grid2Data);
            if (formDefine.getFormType() == FormType.FORM_TYPE_SURVEY) {
                formObject.setSurveyData(formDefine.getSurveyData());
            }
            formObject.setRegions(this.getFormObjRegions(formDefine.getKey()));
        }
        formObject.setSameServeCode(this.serveCodeService.isSameServeCode(formObject.getOwnerLevelAndId()));
        formObject.setFormStatus(needLoadFormData ? 0 : 1);
        HashMap<String, String> orderBygroup = new HashMap<String, String>();
        orderBygroup.put(formGroup.getKey(), formDefine.getOrder());
        formObject.setOrdersBygroup(orderBygroup);
        String order = formObject.getOrdersBygroup().get(formGroup.getKey());
        if (StringUtils.isNotEmpty((String)order)) {
            formObject.setOrder(order);
        }
        formObject.setAnalysisForm(formDefine.isAnalysisForm());
        if (formObject.isAnalysisForm()) {
            DesignAnalysisFormParamDefine paramDefine = this.nrDesignTimeController.queryAnalysisFormParamDefine(formDefine.getKey());
            AnalysisFormParamObj analysisParamObj = new AnalysisFormParamObj();
            AnalysisFormParamObj.toObj(analysisParamObj, paramDefine);
            formObject.setAnalysisFormParam(analysisParamObj);
        }
        formObject.setLedgerForm(formDefine.getLedgerForm());
        if (formDefine.getFormType() == FormType.FORM_TYPE_ANALYSISREPORT) {
            formObject.setAnalysisReportKey((String)formDefine.getExtensionProp("reportKey"));
        }
        formObject.setFormScheme(formDefine.getFormScheme());
        formObject.setFormFoldingObjs(this.formFoldingConvert2VO(this.designFormFoldingService.getByFormKey(formDefine.getKey())));
        return formObject;
    }

    private void dealFormEntitiesCopy(FormObj formObj, DesignFormDefine designFormDefine, String formKey, String formGroupKey, boolean needViews, DesignFormSchemeDefine dfsd, DesignFormGroupDefine dfgd, DesignTaskDefine designTaskDefine, boolean needLoadFormData) throws JQException {
        try {
            if (StringUtils.isEmpty((String)designFormDefine.getMasterEntitiesKey())) {
                DesignFormSchemeDefine formScheme = dfsd;
                DesignFormSchemeDefineGetterImpl designFormSchemeDefineUtil = new DesignFormSchemeDefineGetterImpl(formScheme, designTaskDefine);
                formObj.setMasterEntitiesKey(designFormSchemeDefineUtil.getMasterEntitiesKey());
                formObj.setEntitiesIsExtend(true);
            } else {
                formObj.setMasterEntitiesKey(designFormDefine.getMasterEntitiesKey());
                formObj.setEntitiesIsExtend(false);
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_112, (Throwable)e);
        }
    }

    private void dealFormMeasureUnitCopy(FormObj formObj, DesignFormDefine designFormDefine, String formGroupKey, DesignFormSchemeDefine dfsd, DesignFormGroupDefine dfgd, DesignTaskDefine designTaskDefine) throws JQException {
        try {
            if (StringUtils.isEmpty((String)designFormDefine.getMeasureUnit())) {
                DesignFormSchemeDefine formScheme = dfsd;
                DesignFormSchemeDefineGetterImpl designFormSchemeDefineUtil = new DesignFormSchemeDefineGetterImpl(formScheme, designTaskDefine);
                formObj.setMeasureUnit(designFormSchemeDefineUtil.getMeasureUnit());
                formObj.setMeasureUnitIsExtend(true);
            } else {
                formObj.setMeasureUnit(designFormDefine.getMeasureUnit());
                formObj.setMeasureUnitIsExtend(false);
            }
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_113, (Throwable)e);
        }
    }

    private void dealFillInAutomaticallyDueCopy(FormObj formObj, DesignFormDefine designFormDefine, DesignFormSchemeDefine designFormSchemeDefine, DesignTaskDefine designTaskDefine) {
        if (designFormDefine.getFillInAutomaticallyDue().getType() == FillInAutomaticallyDue.Type.DEFAULT.getValue()) {
            DesignFormSchemeDefineGetterImpl formSchemeDefineGetter = new DesignFormSchemeDefineGetterImpl(designFormSchemeDefine, designTaskDefine);
            formObj.setFillInAutomaticallyDue(formSchemeDefineGetter.getFillInAutomaticallyDue());
            formObj.setFillInAutomaticallyDueIsExtend(true);
        } else {
            formObj.setFillInAutomaticallyDue(designFormDefine.getFillInAutomaticallyDue());
            formObj.setFillInAutomaticallyDueIsExtend(false);
        }
    }
}

