/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.ScriptInfo
 *  com.jiuqi.bi.syntax.operator.IfThenElse
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DataLinkColumn
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryFields
 *  com.jiuqi.np.dataengine.definitions.FormulaCallBack
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IColumnModelFinder
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IFormulaRunner
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.ExpressionUtils
 *  com.jiuqi.np.dataengine.node.FormulaShowInfo
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache
 *  com.jiuqi.nr.data.logic.facade.param.base.BaseEnv
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckParam
 *  com.jiuqi.nr.data.logic.internal.util.DimensionUtil
 *  com.jiuqi.nr.data.logic.internal.util.FormulaParseUtil
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IdMutexProvider
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.common.FormulaSyntaxStyle
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.deploy.DeployFinishedEvent
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.jtable.service.impl;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.ScriptInfo;
import com.jiuqi.bi.syntax.operator.IfThenElse;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataLinkColumn;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.definitions.FormulaCallBack;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IFormulaRunner;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.internal.service.RuntimeDefinitionCache;
import com.jiuqi.nr.data.logic.facade.param.base.BaseEnv;
import com.jiuqi.nr.data.logic.facade.param.input.CheckParam;
import com.jiuqi.nr.data.logic.internal.util.DimensionUtil;
import com.jiuqi.nr.data.logic.internal.util.FormulaParseUtil;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IdMutexProvider;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.deploy.DeployFinishedEvent;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.FormulaConditionFile;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IFCRunTimeService;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class FCRunTimeServiceImpl
implements IFCRunTimeService,
ApplicationListener<DeployFinishedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(FCRunTimeServiceImpl.class);
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    private IEntityViewRunTimeController entityViewController;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private IColumnModelFinder couModelFinder;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired
    private FormulaParseUtil formulaParseUtil;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    private final NedisCache fcFileCache;
    private final String FormulaCondition = "FormulaCondition";
    private final IdMutexProvider idMutexProvider;
    private final String formulaConditionCache = "this.FormulaConditionCache";

    public FCRunTimeServiceImpl(NedisCacheProvider cacheProvider) {
        NedisCacheManager cacheManger = cacheProvider.getCacheManager();
        this.fcFileCache = cacheManger.getCache(RuntimeDefinitionCache.createCacheName(IParsedExpression.class).concat("_fcFile"));
        this.idMutexProvider = new IdMutexProvider();
    }

    @Override
    public FormulaConditionFile getFormulaConditionInForm(JtableContext jtableContext) {
        String fileName;
        if (jtableContext.getFormKey() == null) {
            throw new IllegalArgumentException("'formKey' must not be null.");
        }
        String cacheKey = "FormulaCondition" + jtableContext.getFormulaSchemeKey();
        Cache.ValueWrapper valueWrapper = this.fcFileCache.hGet(cacheKey, (Object)(fileName = jtableContext.getFormulaSchemeKey() + jtableContext.getFormKey()));
        FormulaConditionFile fcFile = valueWrapper != null ? (FormulaConditionFile)valueWrapper.get() : this.getFcFileByForm(jtableContext.getTaskKey(), jtableContext.getFormSchemeKey(), jtableContext.getFormulaSchemeKey(), jtableContext.getFormKey());
        this.setResult(fcFile, jtableContext);
        return fcFile;
    }

    private void setResult(FormulaConditionFile fcFile, JtableContext jtableContext) {
        String autoClacColor = this.iNvwaSystemOptionService.get("nr-data-entry-group", "JTABLE_AUTOCALC_COLOR");
        if (autoClacColor != null && !autoClacColor.equals("#D6F6EF")) {
            fcFile.setCalCellColor(autoClacColor);
        } else {
            fcFile.setCalCellColor("#D6F6EF");
        }
        ArrayList<String> formKeys = new ArrayList<String>(Arrays.asList(jtableContext.getFormKey()));
        formKeys.add("00000000-0000-0000-0000-000000000000");
        List allExpressions = this.formulaRunTimeController.getParsedExpressionByForms(jtableContext.getFormulaSchemeKey(), formKeys, DataEngineConsts.FormulaType.CALCULATE);
        ArrayList<IParsedExpression> calExpressions = new ArrayList<IParsedExpression>();
        FormDefine formDefine = this.viewController.queryFormById(jtableContext.getFormKey());
        ExecutorContext executorContext = this.createExecutorContext(formDefine.getFormScheme());
        HashMap<String, List> formulaConditionMap = new HashMap<String, List>();
        try {
            QueryContext queryContext = new QueryContext(executorContext, null);
            for (int i = 0; i < allExpressions.size(); ++i) {
                IParsedExpression expression;
                block20: {
                    expression = (IParsedExpression)allExpressions.get(i);
                    try {
                        int resultType = 0;
                        resultType = expression.getRealExpression().getType((IContext)queryContext);
                        DynamicDataNode assignNode = expression.getAssignNode();
                        if (resultType == -1) continue;
                        if (assignNode == null) {
                        }
                        break block20;
                    }
                    catch (Exception e) {
                        logger.error("\u516c\u5f0f\u89e3\u6790\u9519\u8bef\uff01", e);
                    }
                    continue;
                }
                if (!formulaConditionMap.containsKey(expression.getSource().getId()) && expression.getConditions().size() > 0) {
                    formulaConditionMap.put(expression.getSource().getId(), expression.getConditions());
                }
                calExpressions.add(expression);
            }
            HashMap<String, Boolean> result = new HashMap<String, Boolean>();
            HashMap<String, List<String>> floatError = new HashMap<String, List<String>>();
            LinkedHashSet execFml = new LinkedHashSet();
            for (String key : formulaConditionMap.keySet()) {
                execFml.addAll((Collection)formulaConditionMap.get(key));
            }
            FormulaConditionMonitor formulaConditionMonitor = new FormulaConditionMonitor();
            if (execFml.size() > 0) {
                FormulaCallBack callBack = this.formulaParseUtil.getFmlCallBack(new ArrayList(execFml), false);
                IFormulaRunner runner = this.dataAccessProvider.newFormulaRunner(callBack);
                CheckParam checkParam = new CheckParam();
                checkParam.setFormulaSchemeKey(jtableContext.getFormulaSchemeKey());
                checkParam.setVariableMap(jtableContext.getVariableMap());
                DimensionValueSet dimExeC = DimensionUtil.getDimensionValueSet(jtableContext.getDimensionSet());
                ExecutorContext fmlExecutorContext = this.formulaParseUtil.getExecutorContext((BaseEnv)checkParam, dimExeC);
                runner.prepareCheck(fmlExecutorContext, null, null);
                runner.setMasterKeyValues(dimExeC);
                runner.run((IMonitor)formulaConditionMonitor);
            }
            int checkFormulaIndex = 10000;
            LinkedHashSet<String> calDataLinks = new LinkedHashSet<String>();
            for (IParsedExpression expression : calExpressions) {
                Boolean notColor;
                DynamicDataNode assignNode;
                block21: {
                    assignNode = expression.getAssignNode();
                    notColor = false;
                    try {
                        boolean direction;
                        DataModelLinkColumn assignNodedataModelLinkColumn = assignNode.getDataModelLink();
                        DataLinkDefine dataLink = this.viewController.queryDataLinkDefineByUniquecode(assignNodedataModelLinkColumn.getReportInfo().getReportKey(), assignNodedataModelLinkColumn.getDataLinkCode());
                        DataLinkColumn dataLinkColumn = assignNode.getDataLink();
                        boolean readOnly = FCRunTimeServiceImpl.trackIsReadOnly(expression, assignNode);
                        boolean bl = direction = assignNode != null && assignNode.getDataLink().getField().getKey().equalsIgnoreCase(dataLinkColumn.getField().getKey());
                        if (DataEngineConsts.FormulaType.CALCULATE == expression.getFormulaType() && readOnly && direction) break block21;
                        notColor = true;
                    }
                    catch (Exception e) {
                        logger.warn("\u3010\u8fd0\u7b97\u516c\u5f0f\uff1a\u8be5\u516c\u5f0f\u8d4b\u503c\u5355\u5143\u683c\u5c06\u4e0d\u4f1a\u4f5c\u4e3a\u8fd0\u7b97\u5355\u5143\u683c\u4e0a\u8272\u3011\u83b7\u53d6\u516c\u5f0f\u8ffd\u8e2a\u5b9a\u4e49\u5931\u8d25\uff1a[{}]\u6839\u636edataNode.getDataLink()\u83b7\u53d6\uff0cDataLinkColumn\u5bf9\u8c61\u4e3a\u7a7a, {}", (Object)expression.getSource().getId(), (Object)e);
                        if (!formulaConditionMap.containsKey(expression.getSource().getId())) continue;
                        List formulaCondition = (List)formulaConditionMap.get(expression.getSource().getId());
                        checkFormulaIndex += formulaCondition.size();
                        continue;
                    }
                }
                if (formulaConditionMap.containsKey(expression.getSource().getId()) && ((List)formulaConditionMap.get(expression.getSource().getId())).size() > 0) {
                    List formulaCondition = (List)formulaConditionMap.get(expression.getSource().getId());
                    for (IParsedExpression checkExpression : formulaCondition) {
                        String formulaConditionName = String.format("FCfml_%s", checkFormulaIndex);
                        ++checkFormulaIndex;
                        if (formulaConditionMonitor.getFloatError().containsKey(checkExpression.getKey())) {
                            floatError.put(this.viewController.queryDataLinkDefineByUniquecode(jtableContext.getFormKey(), assignNode.getDataLink().getDataLinkCode()).getKey(), formulaConditionMonitor.getFloatError().get(checkExpression.getKey()));
                            continue;
                        }
                        if (formulaConditionMonitor.getResult().containsKey(checkExpression.getKey())) {
                            result.put(formulaConditionName, false);
                            continue;
                        }
                        result.put(formulaConditionName, true);
                    }
                    continue;
                }
                if (assignNode.getDataLink() != null) {
                    DataLinkDefine dataLinkDefine = this.viewController.queryDataLinkDefineByUniquecode(jtableContext.getFormKey(), assignNode.getDataLink().getDataLinkCode());
                    if (dataLinkDefine == null || notColor.booleanValue()) continue;
                    calDataLinks.add(dataLinkDefine.getKey());
                    continue;
                }
                DataTable table = this.dataSchemeService.getDataTableByCode(assignNode.getQueryField().getTableName());
                DataField field = this.dataSchemeService.getDataFieldByTableKeyAndCode(table.getKey(), assignNode.getQueryField().getFieldCode());
                List list = this.viewController.getLinksInFormByField(jtableContext.getFormKey(), field.getKey());
                if (list.size() < 1 || notColor.booleanValue()) continue;
                calDataLinks.add(((DataLinkDefine)list.get(0)).getKey());
            }
            fcFile.setResult(result);
            fcFile.setFloatError(floatError);
            if (calDataLinks.size() > 0) {
                fcFile.setCalDatalinks(new ArrayList<String>(calDataLinks));
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FormulaConditionFile getFormulaConditionInForm(String taskKey, String formulaSchemeKey, String formKey) {
        if (formKey == null) {
            throw new IllegalArgumentException("'formKey' must not be null.");
        }
        String formSchemeKey = "";
        try {
            List formSchemeDefines = this.viewController.queryFormSchemeByForm(formKey);
            formSchemeKey = ((FormSchemeDefine)formSchemeDefines.get(0)).getKey();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        String cacheKey = "FormulaCondition" + formulaSchemeKey;
        String fileName = formulaSchemeKey + formKey;
        Cache.ValueWrapper valueWrapper = this.fcFileCache.hGet(cacheKey, (Object)fileName);
        FormulaConditionFile fcFile = valueWrapper != null ? (FormulaConditionFile)valueWrapper.get() : this.getFcFileByForm(taskKey, formSchemeKey, formulaSchemeKey, formKey);
        return fcFile;
    }

    private FormulaConditionFile getFcFileByForm(String taskKey, String formSchemeKey, String formulaSchemeKey, String formKey) {
        return (FormulaConditionFile)RuntimeDefinitionCache.synchronizedRun((Object)this.idMutexProvider.getMutex(formulaSchemeKey + formKey), () -> {
            String fileName;
            String cacheKey = "FormulaCondition" + formulaSchemeKey;
            Cache.ValueWrapper valueWrapper = this.fcFileCache.hGet(cacheKey, (Object)(fileName = formulaSchemeKey + formKey));
            if (valueWrapper != null) {
                FormulaConditionFile fcFile = (FormulaConditionFile)valueWrapper.get();
                return fcFile;
            }
            ArrayList<String> formKeys = new ArrayList<String>(Arrays.asList(formKey));
            formKeys.add("00000000-0000-0000-0000-000000000000");
            List allExpressions = this.formulaRunTimeController.getParsedExpressionByForms(formulaSchemeKey, formKeys, DataEngineConsts.FormulaType.CALCULATE);
            FormDefine formDefine = this.viewController.queryFormById(formKey);
            List formulaConditions = this.formulaRunTimeController.getFormulaConditions(taskKey);
            if (formDefine != null && formulaConditions != null && formulaConditions.size() != 0) {
                FormulaConditionFile fcFile;
                ArrayList<IParsedExpression> calExpressions = new ArrayList<IParsedExpression>();
                ExecutorContext executorContext = this.createExecutorContext(formDefine.getFormScheme());
                HashMap<String, List<IParsedExpression>> formulaConditionMap = new HashMap<String, List<IParsedExpression>>();
                try {
                    QueryContext queryContext = new QueryContext(executorContext, null);
                    for (int i = 0; i < allExpressions.size(); ++i) {
                        IParsedExpression expression;
                        block11: {
                            expression = (IParsedExpression)allExpressions.get(i);
                            try {
                                int resultType = 0;
                                resultType = expression.getRealExpression().getType((IContext)queryContext);
                                DynamicDataNode assignNode = expression.getAssignNode();
                                if (resultType == -1) continue;
                                if (assignNode == null) {
                                }
                                break block11;
                            }
                            catch (Exception e) {
                                logger.error("\u516c\u5f0f\u89e3\u6790\u9519\u8bef\uff01", e);
                            }
                            continue;
                        }
                        if (!formulaConditionMap.containsKey(expression.getSource().getId()) && expression.getConditions().size() > 0) {
                            formulaConditionMap.put(expression.getSource().getId(), expression.getConditions());
                        }
                        calExpressions.add(expression);
                    }
                    HashMap<String, Set<String>> allDatalinks = this.getDataLinksCacheByField(formDefine.getFormScheme(), formKey);
                    fcFile = this.getFcJsInForm(calExpressions, formulaConditionMap, queryContext, taskKey, allDatalinks, formKey);
                }
                catch (InterpretException | ParseException e) {
                    throw new RuntimeException(e);
                }
                catch (SyntaxException e) {
                    throw new RuntimeException(e);
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
                this.fcFileCache.hSet(cacheKey, (Object)fileName, (Object)fcFile);
                return fcFile;
            }
            FormulaConditionFile fcFile = new FormulaConditionFile();
            this.fcFileCache.hSet(cacheKey, (Object)fileName, (Object)fcFile);
            return fcFile;
        });
    }

    private FormulaConditionFile getFcJsInForm(List<IParsedExpression> calExpressions, Map<String, List<IParsedExpression>> formulaConditionMap, QueryContext queryContext, String taskKey, HashMap<String, Set<String>> allDatalinks, String formKey) throws ParseException, InterpretException {
        FormulaConditionFile fcFile = new FormulaConditionFile();
        String autoClacColor = this.iNvwaSystemOptionService.get("nr-data-entry-group", "JTABLE_AUTOCALC_COLOR");
        if (autoClacColor != null && !autoClacColor.equals("#D6F6EF")) {
            fcFile.setCalCellColor(autoClacColor);
        } else {
            fcFile.setCalCellColor("#D6F6EF");
        }
        ArrayList<String> specialFloatAssignDataLinks = new ArrayList<String>();
        int formulaIndex = 10000;
        int checkFormulaIndex = 10000;
        StringBuilder scriptBuilder = new StringBuilder();
        TaskDefine task = this.viewController.queryTaskDefine(taskKey);
        if (task == null) {
            throw new ParseException("task not found.  ".concat(taskKey));
        }
        FormulaSyntaxStyle formulaSyntaxStyle = task.getFormulaSyntaxStyle();
        if (formulaSyntaxStyle == null) {
            throw new ParseException("syntax style not found. ");
        }
        FormulaShowInfo jqStyleShowInfo = new FormulaShowInfo(DataEngineConsts.FormulaShowType.JQ);
        LinkedHashSet<String> noColorFormula = new LinkedHashSet<String>();
        ArrayList<String> haveConditionCalLinks = new ArrayList<String>();
        for (IParsedExpression expression : calExpressions) {
            DynamicDataNode assignNode = expression.getAssignNode();
            String writeFieldId = assignNode.getQueryField().getUID();
            Boolean writeUid = allDatalinks.containsKey(writeFieldId);
            if (writeUid.booleanValue() && formulaConditionMap.containsKey(expression.getSource().getId())) {
                List<IParsedExpression> formulaCondition;
                String calFormulaName = String.format("fml_%s", formulaIndex);
                DataModelLinkColumn assignNodedataModelLinkColumn = assignNode.getDataModelLink();
                if (assignNodedataModelLinkColumn == null) {
                    logger.error("\u516c\u5f0f\u89e3\u6790\u5931\u8d25\uff1a[{}]\u6839\u636eassignNode.getDataModelLink()\u83b7\u53d6\uff0cDataModelLinkColumn\u5bf9\u8c61\u4e3a\u7a7a,\u8be5\u516c\u5f0f\u5c06\u4e0d\u4f1a\u8fdb\u884c\u524d\u7aef\u81ea\u52a8\u8fd0\u7b97", (Object)expression.getSource().getFormula());
                    continue;
                }
                DataLinkDefine dataLink = this.viewController.queryDataLinkDefineByUniquecode(assignNodedataModelLinkColumn.getReportInfo().getReportKey(), assignNodedataModelLinkColumn.getDataLinkCode());
                try {
                    boolean direction;
                    DataLinkColumn dataLinkColumn = assignNode.getDataLink();
                    String dataLinkCode = dataLinkColumn.getDataLinkCode();
                    boolean readOnly = FCRunTimeServiceImpl.trackIsReadOnly(expression, assignNode);
                    boolean bl = direction = assignNode != null && assignNode.getDataLink().getField().getKey().equalsIgnoreCase(dataLinkColumn.getField().getKey());
                    if (DataEngineConsts.FormulaType.CALCULATE != expression.getFormulaType() || !readOnly || !direction) {
                        noColorFormula.add(calFormulaName);
                    }
                }
                catch (Exception e) {
                    logger.error("\u83b7\u53d6\u516c\u5f0f\u8ffd\u8e2a\u5b9a\u4e49\u5931\u8d25\uff1a[{}]\u6839\u636edataNode.getDataLink()\u83b7\u53d6\uff0cDataLinkColumn\u5bf9\u8c61\u4e3a\u7a7a, {}", (Object)expression.getSource().getId(), (Object)e);
                }
                Boolean inFloatRegion = false;
                String floatRegionKey = "";
                Boolean specialFloat = true;
                if (!this.viewController.queryDataRegionDefine(dataLink.getRegionKey()).getRegionKind().equals((Object)DataRegionKind.DATA_REGION_SIMPLE)) {
                    inFloatRegion = true;
                    floatRegionKey = dataLink.getRegionKey();
                }
                if ((formulaCondition = formulaConditionMap.get(expression.getSource().getId())).size() > 0) {
                    haveConditionCalLinks.add(dataLink.getKey());
                }
                for (IParsedExpression checkExpression : formulaCondition) {
                    String formulaConditionName = String.format("FCfml_%s", checkFormulaIndex);
                    FormulaConditionFile.FormulaCondition curFormulaConditionObject = new FormulaConditionFile.FormulaCondition();
                    curFormulaConditionObject.setCalTarget(dataLink.getKey());
                    curFormulaConditionObject.setFormula(calFormulaName);
                    curFormulaConditionObject.setCondition(formulaConditionName);
                    ScriptInfo scriptInfo = new ScriptInfo();
                    scriptInfo.setVarName(formulaConditionName);
                    scriptInfo.setCalc(true);
                    scriptInfo.setFormulaKey(checkExpression.getSource().getId());
                    scriptInfo.setAutoCalc(false);
                    scriptInfo.setDecription(checkExpression.getSource().getMeanning());
                    scriptInfo.setExcelSyntax(formulaSyntaxStyle == FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_EXCEL);
                    if (formulaSyntaxStyle == null || formulaSyntaxStyle == FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_TRADITION) {
                        scriptInfo.setFormulaText(checkExpression.getFormula(queryContext, jqStyleShowInfo));
                    }
                    String scriptText = checkExpression.generateJs(queryContext, scriptInfo);
                    scriptBuilder.append(scriptText);
                    this.addCheckExpressionToScript(scriptBuilder, formulaConditionName);
                    QueryFields queryFields = checkExpression.getQueryFields();
                    if (queryFields.getCount() == 0) {
                        fcFile.AddPeriodCell(curFormulaConditionObject);
                    } else {
                        boolean flag = true;
                        LinkedHashSet<String> otherlink = new LinkedHashSet<String>();
                        for (IASTNode node : checkExpression.getRealExpression()) {
                            String dataLinkCode;
                            String datalink;
                            DynamicDataNode dynamicDataNode;
                            if (!(node instanceof DynamicDataNode) || !queryFields.hasField((dynamicDataNode = (DynamicDataNode)node).getQueryField())) continue;
                            DataModelLinkColumn dataModelLinkColumn = dynamicDataNode.getDataModelLink();
                            if (inFloatRegion.booleanValue() && (dataModelLinkColumn == null || dataModelLinkColumn.getRegion().equals(floatRegionKey))) {
                                specialFloat = false;
                            }
                            if (dataModelLinkColumn == null || StringUtils.isEmpty((String)dataModelLinkColumn.getDataLinkCode())) {
                                logger.error("\u516c\u5f0f\uff1a" + checkExpression.getFormula(queryContext, jqStyleShowInfo) + "\u89e3\u6790\u51fa\u9519\uff1a\u627e\u4e0d\u5230\u6570\u636e\u94fe\u63a5,\u5c06\u4f7f\u75280000-0000\u66ff\u4ee3\u6570\u636e\u94fe\u63a5code");
                                datalink = "0000-0000";
                                dataLinkCode = "0000-0000";
                                fcFile.AddTargetByDataLink(datalink, dataLinkCode, dynamicDataNode.getQueryField().getFieldCode(), curFormulaConditionObject);
                                continue;
                            }
                            if (!dataModelLinkColumn.getReportInfo().getReportKey().equals(formKey)) {
                                fcFile.AddOtherFormDataLink(dynamicDataNode.getQueryField().getTableName() + "[" + dynamicDataNode.getQueryField().getFieldName() + "]");
                                datalink = this.viewController.queryDataLinkDefineByUniquecode(dataModelLinkColumn.getReportInfo().getReportKey(), dataModelLinkColumn.getDataLinkCode()).getKey();
                                dataLinkCode = dataModelLinkColumn.getDataLinkCode();
                                fcFile.AddTargetByDataLink(datalink, dataLinkCode, dynamicDataNode.getQueryField().getFieldCode(), curFormulaConditionObject);
                                otherlink.add(datalink);
                                continue;
                            }
                            datalink = this.viewController.queryDataLinkDefineByUniquecode(formKey, dataModelLinkColumn.getDataLinkCode()).getKey();
                            dataLinkCode = dataModelLinkColumn.getDataLinkCode();
                            fcFile.AddTargetByDataLink(datalink, dataLinkCode, dynamicDataNode.getQueryField().getFieldCode(), curFormulaConditionObject);
                            flag = false;
                        }
                        if (flag) {
                            for (String datalink : otherlink) {
                                fcFile.addOtherDataLink(datalink);
                            }
                        }
                    }
                    ++checkFormulaIndex;
                }
                if (inFloatRegion.booleanValue() && specialFloat.booleanValue()) {
                    specialFloatAssignDataLinks.add(dataLink.getKey());
                }
            }
            ++formulaIndex;
        }
        fcFile.setHaveConditionCalLinks(haveConditionCalLinks);
        fcFile.setSpecialFloatAssignDataLinks(specialFloatAssignDataLinks);
        fcFile.setNoColorFormula(new ArrayList<String>(noColorFormula));
        fcFile.setFormulaConditionJS(scriptBuilder.toString());
        return fcFile;
    }

    private static boolean trackIsReadOnly(IParsedExpression expression, DynamicDataNode assignNode) {
        if (assignNode == null || null == assignNode.getDataLink()) {
            return true;
        }
        IASTNode rootNode = expression.getRealExpression().getChild(0);
        if (rootNode instanceof IfThenElse) {
            if (rootNode.childrenSize() < 3) {
                return false;
            }
            IASTNode elseNode = rootNode.getChild(2);
            DynamicDataNode elseAssignNode = ExpressionUtils.getAssignNode((IASTNode)elseNode);
            if (elseAssignNode == null || !elseAssignNode.equals((Object)assignNode)) {
                return false;
            }
        }
        return assignNode.getQueryField().getDimensionRestriction() == null && assignNode.getQueryField().getPeriodModifier() == null;
    }

    private ExecutorContext createExecutorContext(String formSchemeKey) {
        ExecutorContext context = new ExecutorContext(this.dataDefinitionController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.viewController, this.dataDefinitionController, this.entityViewController, formSchemeKey, true);
        context.setEnv((IFmlExecEnvironment)environment);
        return context;
    }

    private HashMap<String, Set<String>> getDataLinksCacheByField(String formSchemeKey, String formKey) throws Exception {
        HashMap<String, Set<String>> dataLinksByField = new HashMap<String, Set<String>>();
        List dataRegionDefines = this.viewController.getAllRegionsInForm(formKey);
        for (DataRegionDefine dataRegionDefine : dataRegionDefines) {
            List dataLinkDefines = this.viewController.getAllLinksInRegion(dataRegionDefine.getKey());
            for (DataLinkDefine dataLinkDefine : dataLinkDefines) {
                FieldDefine fieldDefine;
                String fieldId = dataLinkDefine.getLinkExpression();
                if (dataLinkDefine.getType() == DataLinkType.DATA_LINK_TYPE_FMDM) {
                    FormSchemeDefine formScheme = this.viewController.getFormScheme(formSchemeKey);
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

    private void addCheckExpressionToScript(StringBuilder scriptBuilder, String varName) {
        scriptBuilder.append("this.FormulaConditionCache");
        scriptBuilder.append(".AddCheckExpression(");
        scriptBuilder.append("\"" + varName + "\",");
        scriptBuilder.append(varName + ");");
    }

    @Override
    public void onApplicationEvent(DeployFinishedEvent event) {
        for (String formulaSchemeKey : event.getDeployParams().getFormulaScheme().getDesignTimeKeys()) {
            String cacheKey = "FormulaCondition" + formulaSchemeKey;
            this.fcFileCache.evict(cacheKey);
        }
    }

    public static class FormulaConditionMonitor
    extends AbstractMonitor
    implements IMonitor {
        private HashMap<String, Integer> formulaConditionError = new HashMap();
        private HashMap<String, List<String>> floatError = new HashMap();

        public void error(FormulaCheckEventImpl event) {
            String parsedExpressionKey = event.getParsedExpresionKey();
            if (event.getRowkey().getValue("RECORDKEY") != null) {
                if (this.floatError.containsKey(parsedExpressionKey)) {
                    this.floatError.get(parsedExpressionKey).add(event.getRowkey().getValue("RECORDKEY").toString());
                } else {
                    this.floatError.put(parsedExpressionKey, new ArrayList<String>(Arrays.asList(event.getRowkey().getValue("RECORDKEY").toString())));
                }
            } else {
                this.formulaConditionError.put(parsedExpressionKey, 1);
            }
        }

        public void finish() {
        }

        public HashMap<String, Integer> getResult() {
            return this.formulaConditionError;
        }

        public HashMap<String, List<String>> getFloatError() {
            return this.floatError;
        }
    }
}

