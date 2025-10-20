/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.interpret.ScriptInfo
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DataEngineRunType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryFields
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.exception.FormulaParseException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.impl.FmlEngineBaseMonitor
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.FormulaShowInfo
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.ValidationRule
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.common.DataSchemeUtils
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.util.StringUtils
 *  com.jiuqi.xlib.utils.CollectionUtils
 */
package com.jiuqi.nr.definition.internal.runtime.parse;

import com.jiuqi.bi.syntax.interpret.ScriptInfo;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.FormulaParseException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.FmlEngineBaseMonitor;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.common.DataSchemeUtils;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.util.DataValidationIntepretUtil;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.util.StringUtils;
import com.jiuqi.xlib.utils.CollectionUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormulaScriptService {
    private static final Logger logger = LoggerFactory.getLogger(FormulaScriptService.class);
    private static final int MAXFORMULACOUNT = 2000;
    private final IRuntimeDataSchemeService iRuntimeDataSchemeService;
    private final IDataDefinitionRuntimeController dataDefinitionController;
    private final IRunTimeViewController viewController;
    private final IEntityViewRunTimeController entityViewController;
    private final IEntityMetaService iEntityMetaService;
    private Integer publicType;
    private final String FormulaCache = "_fc";

    public FormulaScriptService(IDataDefinitionRuntimeController dataDefinitionController, IRunTimeViewController viewController, IEntityViewRunTimeController entityViewController) {
        this.dataDefinitionController = dataDefinitionController;
        this.viewController = viewController;
        this.entityViewController = entityViewController;
        this.iRuntimeDataSchemeService = BeanUtil.getBean(IRuntimeDataSchemeService.class);
        this.iEntityMetaService = BeanUtil.getBean(IEntityMetaService.class);
    }

    public FormulaScriptService(IDataDefinitionRuntimeController dataDefinitionController, IRunTimeViewController viewController, IEntityViewRunTimeController entityViewController, int publicType) {
        this(dataDefinitionController, viewController, entityViewController);
        this.publicType = publicType;
    }

    public String getFormulaScript(String formSchemeKey, String formKey, List<IParsedExpression> expressions, FormulaSyntaxStyle syntaxStyle, FmlEngineBaseMonitor monitor) {
        return this.getFormulaScript(formSchemeKey, formKey, expressions, syntaxStyle, arg_0 -> ((FmlEngineBaseMonitor)monitor).exception(arg_0));
    }

    public String getFormulaScript(String formSchemeKey, String formKey, List<IParsedExpression> expressions, FormulaSyntaxStyle syntaxStyle) {
        return this.getFormulaScript(formSchemeKey, formKey, expressions, syntaxStyle, (FormulaParseException e) -> logger.error(e.getMessage(), (Throwable)e));
    }

    public String getFormulaScript(String formSchemeKey, String formKey, List<IParsedExpression> expressions, FormulaSyntaxStyle syntaxStyle, Consumer<FormulaParseException> exceptionHandler) {
        StringBuilder scriptBuilder = new StringBuilder();
        HashSet<String> scriptKeySet = new HashSet<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        scriptBuilder.append(String.format("var curDateTime = \"%s\";", sdf.format(new Date())));
        try {
            if (expressions.size() > 2000) {
                return scriptBuilder.toString();
            }
            ExecutorContext executorContext = this.createExecutorContext(formSchemeKey);
            QueryContext queryContext = new QueryContext(executorContext, null);
            int formulaIndex = 10000;
            if (this.publicType != null) {
                formulaIndex = this.publicType;
            }
            HashMap<String, String> allDataLinksByField = new HashMap<String, String>();
            HashMap<String, Set<String>> allDatalinks = new HashMap<String, Set<String>>();
            this.loadDataLinks(formSchemeKey, formKey, allDataLinksByField, allDatalinks);
            FormulaShowInfo jqStyleShowInfo = new FormulaShowInfo(DataEngineConsts.FormulaShowType.JQ);
            for (IParsedExpression expression : expressions) {
                try {
                    Object node2;
                    String writeFieldId;
                    String writeUid;
                    int resultType = expression.getRealExpression().getType((IContext)queryContext);
                    DynamicDataNode assignNode = expression.getAssignNode();
                    if (resultType == -1 || assignNode == null || (writeUid = allDataLinksByField.get(writeFieldId = assignNode.getQueryField().getUID())) == null) continue;
                    boolean supportJs = expression.supportJs();
                    ScriptInfo scriptInfo = new ScriptInfo();
                    String varName = "fml_" + formulaIndex++;
                    scriptInfo.setVarName(varName);
                    scriptInfo.setCalc(true);
                    scriptInfo.setFormulaKey(expression.getSource().getId());
                    scriptInfo.setExcelSyntax(syntaxStyle == FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_EXCEL);
                    if (syntaxStyle == null || syntaxStyle == FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_TRADITION) {
                        scriptInfo.setFormulaText(expression.getFormula(queryContext, jqStyleShowInfo));
                    }
                    QueryFields queryFields = expression.getQueryFields();
                    LinkedHashSet<String> curExpressionDatalink = new LinkedHashSet<String>();
                    ArrayList<ExpressionNode> expressionNodes = new ArrayList<ExpressionNode>();
                    for (Object node2 : expression.getRealExpression()) {
                        DynamicDataNode dynamicDataNode;
                        DataModelLinkColumn dataModelLinkColumn;
                        if (!(node2 instanceof DynamicDataNode) || (dataModelLinkColumn = (dynamicDataNode = (DynamicDataNode)node2).getDataModelLink()) == null || StringUtils.isEmpty((String)dataModelLinkColumn.getDataLinkCode())) continue;
                        DataLinkDefine dataLink = this.viewController.queryDataLinkDefineByUniquecode(dataModelLinkColumn.getReportInfo().getReportKey(), dataModelLinkColumn.getDataLinkCode());
                        curExpressionDatalink.add(dataLink.getKey());
                        expressionNodes.add(new ExpressionNode(dynamicDataNode.getQueryField(), dataLink.getKey(), dataModelLinkColumn.getDataLinkCode()));
                    }
                    HashMap<String, String> dataLinksByField = new HashMap<String, String>();
                    node2 = queryFields.iterator();
                    while (node2.hasNext()) {
                        QueryField queryField = (QueryField)node2.next();
                        Set<String> links = allDatalinks.get(queryField.getUID());
                        if (null == links || links.size() <= 1) continue;
                        for (String datalinkKey : links) {
                            if (!curExpressionDatalink.contains(datalinkKey)) continue;
                            dataLinksByField.put(queryField.getUID(), datalinkKey);
                        }
                    }
                    boolean canAdd = this.canMakeAddToScript(queryFields, writeFieldId, writeUid);
                    supportJs = supportJs && canAdd;
                    scriptInfo.setAutoCalc(supportJs);
                    if (!assignNode.getQueryField().getTable().getIsSimple()) {
                        scriptInfo.setShowAll(true);
                    }
                    for (ExpressionNode expressionNode : expressionNodes) {
                        QueryField queryField = expressionNode.getQueryField();
                        String datalinkCode = expressionNode.getDataLinkCode();
                        String datalinkKey = expressionNode.getDatalinkKey();
                        if (!allDataLinksByField.containsKey(queryField.getUID())) {
                            datalinkKey = null;
                        }
                        this.addQueryFieldToScript(scriptBuilder, queryField, varName, datalinkKey, writeFieldId, supportJs, scriptKeySet, datalinkCode);
                    }
                    this.setIsTableSelf(queryFields, writeFieldId, scriptInfo, key -> {
                        String value = (String)dataLinksByField.get(key);
                        if (null != value) {
                            return value;
                        }
                        return (String)allDataLinksByField.get(key);
                    });
                    String scriptText = expression.generateJs(queryContext, scriptInfo);
                    scriptBuilder.append(scriptText);
                    this.addWriteDataLinkExpressionToScript(scriptBuilder, varName, writeUid);
                    this.addExpressionToScript(scriptBuilder, varName);
                }
                catch (Exception e) {
                    exceptionHandler.accept(new FormulaParseException("\u89e3\u6790\u516c\u5f0f\uff1a\u83b7\u53d6\u8fd0\u7b97\u516c\u5f0f" + expression.getSource().getCode() + "[" + expression.getSource().getId() + "]\u7684JS\u811a\u672c\u5931\u8d25", (Throwable)e));
                }
            }
            List<Formula> checkExpressions = this.getCheckFormulasByDataLink(formKey);
            List<IParsedExpression> parseExpressions = this.parseFormula(checkExpressions, DataEngineConsts.FormulaType.CHECK, executorContext);
            this.getCheckFormulaScript(parseExpressions, allDataLinksByField, queryContext, formulaIndex, syntaxStyle, jqStyleShowInfo, scriptBuilder, scriptKeySet, exceptionHandler);
        }
        catch (Exception e) {
            exceptionHandler.accept(new FormulaParseException("\u89e3\u6790\u516c\u5f0f\uff1a\u83b7\u53d6\u516c\u5f0f\u7684JS\u811a\u672c\u5931\u8d25", (Throwable)e));
            return scriptBuilder.toString();
        }
        return scriptBuilder.toString();
    }

    private void getCheckFormulaScript(List<IParsedExpression> expressions, HashMap<String, String> dataLinksByField, QueryContext queryContext, int formulaIndex, FormulaSyntaxStyle syntaxStyle, FormulaShowInfo jqStyleShowInfo, StringBuilder scriptBuilder, HashSet<String> scriptKeySet, Consumer<FormulaParseException> exceptionHandler) {
        for (IParsedExpression expression : expressions) {
            try {
                boolean supportJs = expression.supportJs();
                int resultType = expression.getRealExpression().getType((IContext)queryContext);
                if (resultType == -1) continue;
                ScriptInfo scriptInfo = new ScriptInfo();
                String varName = "fml_" + formulaIndex++;
                scriptInfo.setVarName(varName);
                scriptInfo.setCalc(true);
                scriptInfo.setFormulaKey(expression.getSource().getId());
                scriptInfo.setExcelSyntax(syntaxStyle == FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_EXCEL);
                if (syntaxStyle == null || syntaxStyle == FormulaSyntaxStyle.FORMULA_SYNTAX_STYLE_TRADITION) {
                    scriptInfo.setFormulaText(expression.getFormula(queryContext, jqStyleShowInfo));
                }
                QueryFields queryFields = expression.getQueryFields();
                supportJs = true;
                scriptInfo.setAutoCalc(false);
                scriptInfo.setDecription(expression.getSource().getMeanning());
                for (QueryField queryField : queryFields) {
                    this.addQueryCheckFieldToScript(scriptBuilder, queryField, varName, dataLinksByField, supportJs, scriptKeySet);
                }
                String scriptText = expression.generateJs(queryContext, scriptInfo);
                scriptBuilder.append(scriptText);
                this.addCheckExpressionToScript(scriptBuilder, varName);
            }
            catch (Exception e) {
                exceptionHandler.accept(new FormulaParseException("\u89e3\u6790\u516c\u5f0f\uff1a\u83b7\u53d6\u8fd0\u7b97\u516c\u5f0f" + expression.getSource().getCode() + "[" + expression.getSource().getId() + "]\u7684JS\u811a\u672c\u5931\u8d25", (Throwable)e));
            }
        }
    }

    private void setIsTableSelf(QueryFields queryFields, String writeFieldId, ScriptInfo scriptInfo, Function<String, String> dataLinksByField) {
        for (QueryField item : queryFields) {
            String queryUid = item.getUID();
            if (writeFieldId.equals(queryUid)) continue;
            String dataLinkId = dataLinksByField.apply(queryUid);
            if (null == dataLinkId) {
                scriptInfo.setTableSelf(false);
                break;
            }
            scriptInfo.setTableSelf(true);
        }
    }

    private boolean canAddToScript(QueryFields queryFields, HashMap<String, String> dataLinksByField, String writeFieldId) {
        for (QueryField queryField : queryFields) {
            String dataLinkId;
            String queryUid = queryField.getUID();
            if (writeFieldId.equals(queryUid) || (dataLinkId = dataLinksByField.containsKey(queryUid) ? dataLinksByField.get(queryUid) : null) == null) continue;
            return true;
        }
        return false;
    }

    private boolean isFloatField(FieldDefine fieldDefine) {
        DataField dataField = null;
        dataField = fieldDefine instanceof DataField ? (DataField)fieldDefine : this.iRuntimeDataSchemeService.getDataField(fieldDefine.getKey());
        return DataFieldKind.FIELD == dataField.getDataFieldKind() || DataFieldKind.TABLE_FIELD_DIM == dataField.getDataFieldKind();
    }

    private boolean canMakeAddToScript(QueryFields queryFields, String writeFieldId, String writeUid) throws Exception {
        DataLinkDefine dataLinkDefine = this.viewController.queryDataLinkDefine(writeUid);
        if (StringUtils.isNotEmpty((String)dataLinkDefine.getFilterExpression())) {
            return false;
        }
        DataRegionDefine dataRegionDefine = this.viewController.queryDataRegionDefine(dataLinkDefine.getRegionKey());
        boolean isFloatForm = null != dataRegionDefine && DataRegionKind.DATA_REGION_SIMPLE != dataRegionDefine.getRegionKind();
        boolean isLj = false;
        boolean hasDifferentPeriod = false;
        HashSet<String> tables = new HashSet<String>();
        for (QueryField queryField : queryFields) {
            hasDifferentPeriod |= queryField.getDimensionRestriction() != null || queryField.getPeriodModifier() != null;
            isLj |= queryField.getIsLj();
            tables.add(queryField.getTableName());
        }
        if (isFloatForm && hasDifferentPeriod) {
            return false;
        }
        if (isFloatForm && tables.size() > 1) {
            return false;
        }
        return !isLj;
    }

    private void addQueryFieldToScript(StringBuilder scriptBuilder, QueryField queryField, String varName, String dataLinkId, String writeFieldId, boolean supportJs, HashSet<String> scriptKeySet, String datalinkCode) {
        String queryUid = queryField.getUID();
        if (!queryUid.equals(writeFieldId) && supportJs) {
            this.addDataLinkExpressionToScript(scriptBuilder, varName, dataLinkId);
        }
        if (dataLinkId != null) {
            this.addDataLinkByFieldCodeToScript(scriptBuilder, queryField, dataLinkId, scriptKeySet, datalinkCode);
        } else {
            String dimension;
            String string = dimension = queryField.getPeriodModifier() != null ? queryField.getPeriodModifier().toString() : "";
            if (StringUtils.isEmpty((String)dimension)) {
                dimension = queryField.getDimensionRestriction() != null ? queryField.dimensionValueSetToString(queryField.getDimensionRestriction()) : "";
            }
            this.addOtherLinkByFieldCodeToScript(scriptBuilder, queryField, dataLinkId, dimension, scriptKeySet);
        }
    }

    private void addQueryFieldToScript(StringBuilder scriptBuilder, QueryField queryField, String varName, HashMap<String, String> dataLinksByField, String writeFieldId, boolean supportJs, HashSet<String> scriptKeySet, String datalinkCode) {
        String queryUid = queryField.getUID();
        String dataLinkId = dataLinksByField.get(queryUid);
        if (!queryUid.equals(writeFieldId) && supportJs) {
            this.addDataLinkExpressionToScript(scriptBuilder, varName, dataLinkId);
        }
        if (dataLinkId != null) {
            this.addDataLinkByFieldCodeToScript(scriptBuilder, queryField, dataLinkId, scriptKeySet, datalinkCode);
        } else {
            String dimension;
            String string = dimension = queryField.getPeriodModifier() != null ? queryField.getPeriodModifier().toString() : "";
            if (StringUtils.isEmpty((String)dimension)) {
                dimension = queryField.getDimensionRestriction() != null ? queryField.dimensionValueSetToString(queryField.getDimensionRestriction()) : "";
            }
            this.addOtherLinkByFieldCodeToScript(scriptBuilder, queryField, dataLinkId, dimension, scriptKeySet);
        }
    }

    private void addQueryCheckFieldToScript(StringBuilder scriptBuilder, QueryField queryField, String varName, HashMap<String, String> dataLinksByField, boolean supportJs, HashSet<String> scriptKeySet) {
        String queryUid = queryField.getUID();
        String dataLinkId = dataLinksByField.get(queryUid);
        if (supportJs) {
            this.addDataLinkCheckExpressionToScript(scriptBuilder, varName, dataLinkId);
        }
        if (dataLinkId != null) {
            this.addDataLinkByFieldCodeToScript(scriptBuilder, queryField, dataLinkId, scriptKeySet);
        } else {
            String dimension;
            String string = dimension = queryField.getPeriodModifier() != null ? queryField.getPeriodModifier().toString() : "";
            if (StringUtils.isEmpty((String)dimension)) {
                dimension = queryField.getDimensionRestriction() != null ? queryField.dimensionValueSetToString(queryField.getDimensionRestriction()) : "";
            }
            this.addOtherLinkByFieldCodeToScript(scriptBuilder, queryField, dataLinkId, dimension, scriptKeySet);
        }
    }

    private void addExpressionToScript(StringBuilder scriptBuilder, String varName) {
        scriptBuilder.append("_fc");
        scriptBuilder.append("._ae(");
        scriptBuilder.append("\"" + varName + "\",");
        scriptBuilder.append(varName + ");");
    }

    private void addCheckExpressionToScript(StringBuilder scriptBuilder, String varName) {
        scriptBuilder.append("_fc");
        scriptBuilder.append("._ace(");
        scriptBuilder.append("\"" + varName + "\",");
        scriptBuilder.append(varName + ");");
    }

    private void addWriteDataLinkExpressionToScript(StringBuilder scriptBuilder, String varName, String writeUid) {
        scriptBuilder.append("_fc");
        scriptBuilder.append("._awde(");
        scriptBuilder.append("\"" + writeUid + "\",");
        scriptBuilder.append(varName + ");");
    }

    private void addDataLinkByFieldCodeToScript(StringBuilder scriptBuilder, QueryField queryField, String dataLinkId, HashSet<String> scriptKeySet) {
        if (!scriptKeySet.contains(dataLinkId)) {
            scriptBuilder.append("_fc");
            scriptBuilder.append("._adbf(");
            scriptBuilder.append("\"" + dataLinkId + "\",");
            scriptBuilder.append("\"" + queryField.getTableName() + "\",");
            scriptBuilder.append("\"" + queryField.getFieldName() + "\");");
            scriptKeySet.add(dataLinkId);
        }
    }

    private void addDataLinkByFieldCodeToScript(StringBuilder scriptBuilder, QueryField queryField, String dataLinkId, HashSet<String> scriptKeySet, String datalinkCode) {
        if (!scriptKeySet.contains(dataLinkId)) {
            scriptBuilder.append("_fc");
            scriptBuilder.append("._adbf(");
            scriptBuilder.append("\"" + dataLinkId + "\",");
            scriptBuilder.append("\"" + queryField.getTableName() + "\",");
            scriptBuilder.append("\"" + queryField.getFieldName() + "\",");
            scriptBuilder.append("\"" + datalinkCode + "\");");
            scriptKeySet.add(dataLinkId);
        }
    }

    private void addOtherLinkByFieldCodeToScript(StringBuilder scriptBuilder, QueryField queryField, String dataLinkId, String dimension, HashSet<String> scriptKeySet) {
        String key = queryField.getTableName() + queryField.getFieldName() + dimension;
        if (!scriptKeySet.contains(key)) {
            scriptBuilder.append("_fc");
            scriptBuilder.append("._aodbf(");
            scriptBuilder.append("\"" + queryField.getTableName() + "\",");
            scriptBuilder.append("\"" + queryField.getFieldName() + "\",");
            scriptBuilder.append("\"" + dimension + "\");");
            scriptKeySet.add(key);
        }
    }

    private void addDataLinkExpressionToScript(StringBuilder scriptBuilder, String varName, String dataLinkId) {
        scriptBuilder.append("_fc");
        scriptBuilder.append("._ade(");
        scriptBuilder.append("\"" + dataLinkId + "\",");
        scriptBuilder.append("\"" + varName + "\");");
    }

    private void addDataLinkCheckExpressionToScript(StringBuilder scriptBuilder, String varName, String writeUid) {
        scriptBuilder.append("_fc");
        scriptBuilder.append("._adce(");
        scriptBuilder.append("\"" + writeUid + "\",");
        scriptBuilder.append("\"" + varName + "\");");
    }

    private void loadDataLinks(String formSchemeKey, String formKey, Map<String, String> column2link, Map<String, Set<String>> column2links) throws Exception {
        List<DataLinkDefine> dataLinkDefines = this.viewController.getAllLinksInForm(formKey);
        for (DataLinkDefine dataLinkDefine : dataLinkDefines) {
            List infos;
            String fieldId = dataLinkDefine.getLinkExpression();
            if (dataLinkDefine.getType() == DataLinkType.DATA_LINK_TYPE_FMDM) {
                FormSchemeDefine formScheme = this.viewController.getFormScheme(formSchemeKey);
                String entityId = formScheme.getDw();
                IEntityModel entityModel = this.iEntityMetaService.getEntityModel(entityId);
                IEntityAttribute attribute = entityModel.getAttribute(fieldId);
                if (attribute == null) continue;
                column2link.put(attribute.getID(), dataLinkDefine.getKey());
                column2links.computeIfAbsent(attribute.getID(), k -> new HashSet()).add(dataLinkDefine.getKey());
                continue;
            }
            if (dataLinkDefine.getType() != DataLinkType.DATA_LINK_TYPE_FIELD || CollectionUtils.isEmpty((Collection)(infos = this.iRuntimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{fieldId})))) continue;
            String columnId = ((DataFieldDeployInfo)infos.get(0)).getColumnModelKey();
            column2link.put(columnId, dataLinkDefine.getKey());
            column2links.computeIfAbsent(columnId, k -> new HashSet()).add(dataLinkDefine.getKey());
        }
    }

    private List<Formula> getCheckFormulasByDataLink(String formKey) {
        ArrayList<Formula> formulas = new ArrayList<Formula>();
        List<DataLinkDefine> dataLinks = this.viewController.getAllLinksInForm(formKey);
        ValidationRule validationRule = null;
        for (DataLinkDefine dataLinkDefine : dataLinks) {
            List<String> checkFormulaList = dataLinkDefine.getDataValidation();
            if (checkFormulaList == null || checkFormulaList.size() <= 0) continue;
            for (String checkFormula : checkFormulaList) {
                if (StringUtils.isEmpty((String)checkFormula) || null == (validationRule = DataSchemeUtils.getValidationRule((String)checkFormula))) continue;
                Formula formula = new Formula();
                formula.setId(UUIDUtils.getKey());
                formula.setChecktype(Integer.valueOf(DataEngineConsts.FormulaType.CHECK.getValue()));
                formula.setFormula(validationRule.getVerification());
                formula.setFormKey(formKey);
                String desc = null;
                desc = StringUtils.isEmpty((String)validationRule.getMessage()) ? DataValidationIntepretUtil.intepret(this.dataDefinitionController, checkFormula) : validationRule.getMessage();
                formula.setMeanning(desc);
                formulas.add(formula);
            }
        }
        return formulas;
    }

    private List<IParsedExpression> parseFormula(List<Formula> formulas, DataEngineConsts.FormulaType formulaType, ExecutorContext context) throws ParseException {
        if (formulas == null || formulas.isEmpty()) {
            return Collections.emptyList();
        }
        FmlEngineBaseMonitor fmlEngineBaseMonitor = new FmlEngineBaseMonitor(DataEngineConsts.DataEngineRunType.PARSE);
        fmlEngineBaseMonitor.start();
        List expressions = DataEngineFormulaParser.parseFormula((ExecutorContext)context, formulas, (DataEngineConsts.FormulaType)formulaType, (IMonitor)fmlEngineBaseMonitor);
        fmlEngineBaseMonitor.finish();
        return expressions;
    }

    private ExecutorContext createExecutorContext(String formSchemeKey) {
        ExecutorContext context = new ExecutorContext(this.dataDefinitionController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.viewController, this.dataDefinitionController, this.entityViewController, formSchemeKey, true);
        context.setEnv((IFmlExecEnvironment)environment);
        return context;
    }

    private static class ExpressionNode {
        private QueryField queryField;
        private String datalinkKey;
        private String dataLinkCode;

        public ExpressionNode(QueryField queryField, String datalinkKey, String dataLinkCode) {
            this.queryField = queryField;
            this.datalinkKey = datalinkKey;
            this.dataLinkCode = dataLinkCode;
        }

        public QueryField getQueryField() {
            return this.queryField;
        }

        public void setQueryField(QueryField queryField) {
            this.queryField = queryField;
        }

        public String getDatalinkKey() {
            return this.datalinkKey;
        }

        public void setDatalinkKey(String datalinkKey) {
            this.datalinkKey = datalinkKey;
        }

        public String getDataLinkCode() {
            return this.dataLinkCode;
        }

        public void setDataLinkCode(String dataLinkCode) {
            this.dataLinkCode = dataLinkCode;
        }
    }
}

