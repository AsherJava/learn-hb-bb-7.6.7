/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.IReportCellProvider
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.dataengine.common.DataEngineConsts
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.IQueryField
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryFields
 *  com.jiuqi.np.dataengine.common.QueryTable
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.node.CheckExpression
 *  com.jiuqi.np.dataengine.node.DynamicDataNodeFinder
 *  com.jiuqi.np.dataengine.node.ExpressionUtils
 *  com.jiuqi.np.dataengine.parse.CellFmlProvider
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.ValidationRule
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.fmdm.internal.formula.impl;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.IReportCellProvider;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.IQueryField;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.node.DynamicDataNodeFinder;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.parse.CellFmlProvider;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import com.jiuqi.nr.fmdm.common.FMDMModifyTypeEnum;
import com.jiuqi.nr.fmdm.exception.FMDMCheckException;
import com.jiuqi.nr.fmdm.internal.check.CheckNodeInfo;
import com.jiuqi.nr.fmdm.internal.check.CheckParam;
import com.jiuqi.nr.fmdm.internal.check.FMDMCheckFailNodeInfo;
import com.jiuqi.nr.fmdm.internal.check.FMDMCheckResult;
import com.jiuqi.nr.fmdm.internal.formula.DataEngineFormular;
import com.jiuqi.nr.fmdm.internal.formula.FormulaUtil;
import com.jiuqi.nr.fmdm.internal.formula.IFMDMFormulaCheckService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class FMDMFormulaCheckServiceImpl
implements IFMDMFormulaCheckService {
    private static final Logger log = LoggerFactory.getLogger(FMDMFormulaCheckServiceImpl.class);
    @Autowired
    private IFMDMDataService fmdmDataService;
    @Autowired
    private IFMDMAttributeService fmdmAttributeService;
    @Autowired
    private IRunTimeViewController runtimeController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DataModelService dataModelService;

    @Override
    public FMDMCheckResult check(CheckParam checkParam) {
        FMDMCheckResult checkResult = new FMDMCheckResult();
        FormDefine formDefine = this.runtimeController.queryFormById(checkParam.getFormKey());
        List<Formula> formulas = FormulaUtil.getFormulas(checkParam.getFormulaSchemeKey(), checkParam.getFormKey());
        List<DataField> dataField = FormulaUtil.getDataField(checkParam.getFormKey());
        if (CollectionUtils.isEmpty(formulas) && CollectionUtils.isEmpty(dataField)) {
            return checkResult;
        }
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment env = new ReportFmlExecEnvironment(this.runtimeController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formDefine.getFormScheme());
        executorContext.setEnv((IFmlExecEnvironment)env);
        ReportFormulaParser parser = this.getReportFormulaParser(executorContext, formDefine.getFormScheme());
        FormSchemeDefine formScheme = this.runtimeController.getFormScheme(formDefine.getFormScheme());
        QueryContext qContext = this.buildQueryContext(executorContext, checkParam, formScheme.getTaskKey());
        qContext.setDefaultGroupName(formDefine.getFormCode());
        for (Formula formula : formulas) {
            IExpression expression = null;
            try {
                expression = parser.parseCond(formula.getFormula(), (IContext)qContext);
            }
            catch (Exception e) {
                log.info("\u516c\u5f0f\u89e3\u6790\u5f02\u5e38" + formula, e);
            }
            if (expression == null) continue;
            try {
                if (expression.judge((IContext)qContext)) continue;
                checkResult.addResult(this.getFMDMCheckResult(expression, formula));
            }
            catch (Exception e) {
                log.info("\u516c\u5f0f\u6267\u884c\u5f02\u5e38" + formula, e);
            }
        }
        for (DataField field : dataField) {
            List validationRules = field.getValidationRules();
            FMDMCheckFailNodeInfo node = null;
            for (ValidationRule rule : validationRules) {
                IExpression expression = null;
                try {
                    expression = parser.parseCond(rule.getVerification(), (IContext)qContext);
                }
                catch (Exception e) {
                    log.info("\u6307\u6807{}[{}]\u7684\u6570\u636e\u6821\u9a8c{}\u89e3\u6790\u5f02\u5e38:{}", field.getTitle(), field.getCode(), rule.getVerification(), e);
                }
                if (expression == null) continue;
                try {
                    if (expression.judge((IContext)qContext)) continue;
                    if (node == null) {
                        node = new FMDMCheckFailNodeInfo();
                        node.setFieldCode(field.getCode());
                        node.setFieldTitle(field.getTitle());
                    }
                    node.addNode(this.getCheckInfo(rule));
                }
                catch (Exception e) {
                    log.info("\u6307\u6807{}[{}]\u7684\u6570\u636e\u6821\u9a8c{}\u6267\u884c\u5f02\u5e38:{}", field.getTitle(), field.getCode(), rule.getVerification(), e);
                }
            }
            if (node == null) continue;
            checkResult.addResult(node);
        }
        return checkResult;
    }

    private List<FMDMCheckFailNodeInfo> getFMDMCheckResult(IExpression expression, Formula formula) {
        CheckExpression parsedExpression = new CheckExpression(expression, formula);
        ExpressionUtils.bindExtnedRWKey((CheckExpression)parsedExpression);
        ArrayList<FMDMCheckFailNodeInfo> fmdmCheckFailNodeInfos = new ArrayList<FMDMCheckFailNodeInfo>();
        QueryFields queryFields = parsedExpression.getQueryFields();
        Iterator iterator = queryFields.iterator();
        while (iterator.hasNext()) {
            FMDMCheckFailNodeInfo node = new FMDMCheckFailNodeInfo();
            QueryField next = (QueryField)iterator.next();
            String fieldCode = next.getFieldCode();
            node.setFieldCode(fieldCode);
            node.setFieldTitle(next.getFieldName());
            CheckNodeInfo checkNodeInfo = new CheckNodeInfo();
            checkNodeInfo.setType(formula.getChecktype());
            checkNodeInfo.setContent(formula.getMeanning());
            node.addNode(checkNodeInfo);
            fmdmCheckFailNodeInfos.add(node);
        }
        return fmdmCheckFailNodeInfos;
    }

    private CheckNodeInfo getCheckInfo(ValidationRule rule) {
        CheckNodeInfo checkNodeInfo = new CheckNodeInfo();
        checkNodeInfo.setType(-1);
        checkNodeInfo.setContent(rule.getMessage());
        return checkNodeInfo;
    }

    private ReportFormulaParser getReportFormulaParser(ExecutorContext executorContext, String formScheme) {
        String bizFieldCode = this.getBizFieldCode(formScheme);
        ReportFormulaParser parser = ReportFormulaParser.getInstance();
        parser.registerDynamicNodeProvider((IReportDynamicNodeProvider)DataEngineFormular.initPriorityContextVariableManagerFmdm());
        if (executorContext.getVariableManager() != null) {
            parser.registerDynamicNodeProvider((IReportDynamicNodeProvider)executorContext.getVariableManager());
        }
        CellFmlProvider cellProvider = new CellFmlProvider();
        parser.registerCellProvider((IReportCellProvider)cellProvider);
        parser.setJQReportMode(true);
        DynamicDataNodeFinder nodeFinder = new DynamicDataNodeFinder();
        parser.registerDynamicNodeProvider((IReportDynamicNodeProvider)nodeFinder);
        parser.registerDynamicNodeProvider((IReportDynamicNodeProvider)DataEngineFormular.initContextVariableManagerFmdm(bizFieldCode));
        for (IReportDynamicNodeProvider rdnfinder : executorContext.getEnv().getDataNodeFinders()) {
            parser.registerDynamicNodeProvider(rdnfinder);
        }
        return parser;
    }

    private String getBizFieldCode(String formScheme) {
        String bizFieldCode = null;
        FormSchemeDefine formSchemeDefine = this.runtimeController.getFormScheme(formScheme);
        String viewKey = formSchemeDefine.getDw();
        if (!StringUtils.hasText(viewKey)) {
            TaskDefine taskDefine = this.runtimeController.queryTaskDefine(formSchemeDefine.getTaskKey());
            viewKey = taskDefine.getDw();
        }
        IEntityModel entityModel = this.entityMetaService.getEntityModel(viewKey);
        IEntityAttribute bizKeyField = entityModel.getBizKeyField();
        bizFieldCode = bizKeyField.getCode();
        return bizFieldCode;
    }

    private QueryContext buildQueryContext(ExecutorContext executorContext, CheckParam checkParam, String taskKey) {
        Map<String, Object> param = checkParam.getData();
        DimensionValueSet masterKeys = checkParam.getMasterKeys();
        QueryContext qContext = null;
        Set<String> keySet = param.keySet();
        HashMap<String, Object> valueMap = new HashMap<String, Object>(param.size());
        for (String key : keySet) {
            Object value = param.get(key);
            valueMap.put(key.toUpperCase(Locale.ROOT), value);
        }
        try {
            qContext = new QueryContext(executorContext, null);
            qContext.getCache().putAll(valueMap);
            TaskDefine taskDefine = this.runtimeController.queryTaskDefine(taskKey);
            List formScheme = this.runtimeController.queryFormSchemeByTask(taskKey);
            String formSchemeKey = ((FormSchemeDefine)formScheme.get(0)).getKey();
            IFMDMData fmdmData = null;
            if (FMDMModifyTypeEnum.UPDATE.equals((Object)checkParam.getModifyType())) {
                fmdmData = this.getFMDMData(formSchemeKey, masterKeys);
            }
            List<IFMDMAttribute> list = this.getFMDMAttribute(formSchemeKey, taskDefine.getDw());
            for (IFMDMAttribute ifmdmAttribute : list) {
                TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(ifmdmAttribute.getTableID());
                String tableName = tableModelDefine.getName();
                QueryTable queryTable = new QueryTable(tableName, tableName);
                QueryField field = new QueryField((ColumnModelDefine)ifmdmAttribute, queryTable);
                field.setDataType(this.getType(ifmdmAttribute.getColumnType().getValue()));
                Object value = fmdmData != null && !valueMap.containsKey(field.getFieldCode()) ? fmdmData.getValue(field.getFieldCode()).getAsObject() : valueMap.get(ifmdmAttribute.getCode());
                Object resultValue = DataEngineConsts.formatData((IQueryField)field, value);
                qContext.writeData(field, resultValue);
            }
            qContext.setMasterKeys(masterKeys);
        }
        catch (Exception e) {
            throw new FMDMCheckException("\u6784\u5efaQueryContext\u5f02\u5e38", e);
        }
        return qContext;
    }

    private IFMDMData getFMDMData(String formSchemeKey, DimensionValueSet dimensionValueSet) {
        DimensionCombination dimensionCombination = new DimensionCombinationBuilder(dimensionValueSet).getCombination();
        FMDMDataDTO queryDTO = new FMDMDataDTO();
        queryDTO.setFormSchemeKey(formSchemeKey);
        return this.fmdmDataService.queryFmdmData(queryDTO, dimensionCombination);
    }

    private List<IFMDMAttribute> getFMDMAttribute(String dataScheme, String entityId) {
        try {
            FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
            fmdmAttributeDTO.setEntityId(entityId);
            fmdmAttributeDTO.setFormSchemeKey(dataScheme);
            return this.fmdmAttributeService.list(fmdmAttributeDTO);
        }
        catch (Exception e) {
            throw new FMDMCheckException("\u67e5\u8be2\u5c01\u9762\u4ee3\u7801\u5c5e\u6027\u63a5\u53e3\u5f02\u5e38", e);
        }
    }

    private int getType(int type) {
        if (type == 5) {
            return 3;
        }
        return type;
    }
}

