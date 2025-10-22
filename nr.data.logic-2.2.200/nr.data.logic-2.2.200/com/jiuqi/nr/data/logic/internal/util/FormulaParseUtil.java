/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.definitions.FormulaCallBack
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFieldValueUpdateProcessor
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaUnitGroup
 *  com.jiuqi.nr.definition.facade.IFormulaUnitGroupGetter
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.nr.data.logic.internal.util;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.definitions.FormulaCallBack;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFieldValueUpdateProcessor;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.logic.common.CommonUtils;
import com.jiuqi.nr.data.logic.facade.extend.FmlExeContextProvider;
import com.jiuqi.nr.data.logic.facade.extend.IFormulaFilter;
import com.jiuqi.nr.data.logic.facade.param.base.BaseEnv;
import com.jiuqi.nr.data.logic.facade.param.input.FormulaFilterEnv;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.internal.helper.FormulaGetter;
import com.jiuqi.nr.data.logic.internal.util.FileFieldValueProcessor;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaUnitGroup;
import com.jiuqi.nr.definition.facade.IFormulaUnitGroupGetter;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class FormulaParseUtil {
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired(required=false)
    private List<IFormulaFilter> formulaFilters;
    @Autowired(required=false)
    private IFormulaUnitGroupGetter formulaUnitGroupGetter;
    private static final Logger logger = LoggerFactory.getLogger(FormulaParseUtil.class);

    public List<IParsedExpression> listExpression(BaseEnv baseEnv, DataEngineConsts.FormulaType formulaType, List<String> accessForms) {
        String formulaSchemeKey = baseEnv.getFormulaSchemeKey();
        Map<String, List<String>> formulas = this.getFormulas(baseEnv.getMode(), baseEnv.getRangeKeys(), formulaSchemeKey, formulaType);
        return this.getExpressionsByFormulas(baseEnv, formulaType, accessForms, formulas);
    }

    public List<IParsedExpression> listExpression(BaseEnv baseEnv, DataEngineConsts.FormulaType formulaType, List<String> accessForms, Map<String, List<String>> formulas) {
        return this.getExpressionsByFormulas(baseEnv, formulaType, accessForms, formulas);
    }

    private List<IParsedExpression> getExpressionsByFormulas(BaseEnv baseEnv, DataEngineConsts.FormulaType formulaType, List<String> accessForms, Map<String, List<String>> formulas) {
        String formulaSchemeKey = baseEnv.getFormulaSchemeKey();
        String formSchemeKey = this.formulaRunTimeController.queryFormulaSchemeDefine(formulaSchemeKey).getFormSchemeKey();
        ArrayList<IParsedExpression> expressions = new ArrayList<IParsedExpression>();
        for (String formKey : accessForms) {
            List<String> formulaList;
            List<IParsedExpression> parsedExpressions;
            List<IParsedExpression> filteredExpressions = this.filterParsedExpressions(formulaSchemeKey, formKey, formSchemeKey, parsedExpressions = this.getExpressions(formulaType, formulaSchemeKey, formKey, formulaList = formulas.get(formKey)));
            if (filteredExpressions == null) continue;
            expressions.addAll(filteredExpressions);
        }
        return expressions;
    }

    private List<IParsedExpression> getExpressions(DataEngineConsts.FormulaType formulaType, String formulaSchemeKey, String formKey, List<String> formulaKeys) {
        ArrayList<IParsedExpression> result = new ArrayList<IParsedExpression>();
        List parsedExpressions = this.formulaRunTimeController.getParsedExpressionByForm(formulaSchemeKey, formKey, formulaType);
        if (formulaKeys == null || formulaKeys.isEmpty()) {
            result.addAll(parsedExpressions);
        } else {
            for (IParsedExpression parsedExpression : parsedExpressions) {
                Formula formula = parsedExpression.getSource();
                if (!formulaKeys.contains(formula.getId())) continue;
                result.add(parsedExpression);
            }
        }
        return result;
    }

    private List<IParsedExpression> filterParsedExpressions(String formulaSchemeKey, String formKey, String formSchemeKey, List<IParsedExpression> expressions) {
        if (this.formulaFilters != null && !this.formulaFilters.isEmpty()) {
            FormulaFilterEnv formulaFilterEnv = new FormulaFilterEnv();
            formulaFilterEnv.setFormSchemeKey(formSchemeKey);
            formulaFilterEnv.setFormulaSchemeKey(formulaSchemeKey);
            formulaFilterEnv.setFormKey(formKey);
            for (IFormulaFilter formulaFilter : this.formulaFilters) {
                expressions = formulaFilter.filterParsedExpression(formulaFilterEnv, expressions);
            }
        }
        return expressions;
    }

    public ExecutorContext getExecutorContext(BaseEnv baseEnv, DimensionValueSet dimensionValueSet) {
        String formSchemeKey = this.formulaRunTimeController.queryFormulaSchemeDefine(baseEnv.getFormulaSchemeKey()).getFormSchemeKey();
        ExecutorContext executorContext = this.getExecutorContext(formSchemeKey, dimensionValueSet);
        FmlExeContextProvider fmlExeContextProvider = baseEnv.getFmlExeContextProvider();
        return fmlExeContextProvider == null ? executorContext : fmlExeContextProvider.get(executorContext, baseEnv);
    }

    public ExecutorContext getExecutorContext(String formSchemeKey) {
        return this.getExecutorContext(formSchemeKey, null);
    }

    public ExecutorContext getExecutorContext(@NonNull String formSchemeKey, @Nullable DimensionValueSet dimensionValueSet) {
        String contextEntityId;
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setJQReportModel(true);
        List variables = DsContextHolder.getDsContext().getVariables();
        HashMap<String, Object> variableMap = new HashMap<String, Object>();
        if (!CollectionUtils.isEmpty(variables)) {
            VariableManager variableManager = executorContext.getVariableManager();
            for (Variable variable : variables) {
                variableManager.add(variable);
                try {
                    variableMap.put(variable.getVarName(), variable.getVarValue(null));
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeKey, null, variableMap);
        FileFieldValueProcessor fileFieldValueProcessor = new FileFieldValueProcessor();
        environment.setFieldValueUpdateProcessor((IFieldValueUpdateProcessor)fileFieldValueProcessor);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        if (dimensionValueSet != null) {
            executorContext.setVarDimensionValueSet(dimensionValueSet);
        }
        if (StringUtils.isNotEmpty((String)(contextEntityId = DsContextHolder.getDsContext().getContextEntityId()))) {
            executorContext.setOrgEntityId(contextEntityId);
        }
        executorContext.setAutoDataMasking(false);
        return executorContext;
    }

    public Map<String, List<String>> getFormulas(Mode mode, List<String> rangeKeys, String formulaSchemeKey, DataEngineConsts.FormulaType formulaType) {
        HashMap<String, List<String>> formulas;
        block13: {
            List formulaDefines;
            block14: {
                block12: {
                    formulas = new HashMap<String, List<String>>();
                    formulaDefines = null;
                    if (DataEngineConsts.FormulaType.CHECK == formulaType) {
                        formulaDefines = this.formulaRunTimeController.getCheckFormulasInScheme(formulaSchemeKey);
                    } else if (DataEngineConsts.FormulaType.CALCULATE == formulaType) {
                        formulaDefines = this.formulaRunTimeController.getCalculateFormulasInScheme(formulaSchemeKey);
                    }
                    if (formulaDefines == null) {
                        logger.warn("\u5f53\u524d\u516c\u5f0f\u65b9\u6848{}\u4e0b\u516c\u5f0f\u4e3aNULL", (Object)formulaSchemeKey);
                        return Collections.emptyMap();
                    }
                    if (rangeKeys != null && !rangeKeys.isEmpty()) break block12;
                    for (FormulaDefine formulaDefine : formulaDefines) {
                        String formKey = formulaDefine.getFormKey();
                        if (formKey == null) {
                            formKey = "00000000-0000-0000-0000-000000000000";
                        }
                        String key = formulaDefine.getKey();
                        if (formulas.containsKey(formKey)) {
                            ((List)formulas.get(formKey)).add(key);
                            continue;
                        }
                        ArrayList<String> value = new ArrayList<String>();
                        value.add(key);
                        formulas.put(formKey, value);
                    }
                    break block13;
                }
                if (Mode.FORM != mode) break block14;
                for (String rangeKey : rangeKeys) {
                    formulas.put(rangeKey, new ArrayList());
                }
                break block13;
            }
            if (Mode.FORMULA != mode) break block13;
            Map<String, FormulaDefine> formulaDefineMap = formulaDefines.stream().filter(o -> o.getKey() != null).collect(Collectors.toMap(IBaseMetaItem::getKey, o -> o, (v1, v2) -> v2));
            for (String rangeKey : rangeKeys) {
                FormulaDefine formulaDefine = formulaDefineMap.get(rangeKey);
                if (formulaDefine == null) {
                    logger.warn("\u516c\u5f0f\u672a\u627e\u5230{}", (Object)rangeKey);
                    continue;
                }
                String formKey = formulaDefine.getFormKey();
                if (formKey == null) {
                    formKey = "00000000-0000-0000-0000-000000000000";
                }
                if (formulas.containsKey(formKey)) {
                    ((List)formulas.get(formKey)).add(rangeKey);
                    continue;
                }
                ArrayList<String> value = new ArrayList<String>();
                value.add(rangeKey);
                formulas.put(formKey, value);
            }
        }
        return formulas;
    }

    public List<IParsedExpression> filterFormula(List<IParsedExpression> formulas, Collection<String> accessForms, Map<String, List<String>> formulaMaps, Collection<Integer> formulaCheckTypes, boolean containBetweenForm, boolean autoCheckBJ, Mode mode) {
        if (CollectionUtils.isEmpty(formulas)) {
            return Collections.emptyList();
        }
        ArrayList<IParsedExpression> result = new ArrayList<IParsedExpression>();
        boolean bJExe = accessForms.contains("00000000-0000-0000-0000-000000000000");
        block0: for (IParsedExpression formula : formulas) {
            List<String> formulaKeys;
            if (!CollectionUtils.isEmpty(formulaCheckTypes) && !formulaCheckTypes.contains(formula.getSource().getChecktype())) continue;
            if (formula.getFormKey() == null) {
                Object bjFml;
                if (containBetweenForm) {
                    if (!bJExe) continue;
                    if (Mode.FORM == mode) {
                        result.add(formula);
                        continue;
                    }
                    if (Mode.FORMULA != mode || (bjFml = formulaMaps.get("00000000-0000-0000-0000-000000000000")) == null || !bjFml.contains(formula.getSource().getId())) continue;
                    result.add(formula);
                    continue;
                }
                if (!autoCheckBJ) continue;
                bjFml = formula.getRealExpression().iterator();
                while (bjFml.hasNext()) {
                    String formKey;
                    DynamicDataNode dataNode;
                    IASTNode node = (IASTNode)bjFml.next();
                    if (!(node instanceof DynamicDataNode) || (dataNode = (DynamicDataNode)node).getDataLink() == null || dataNode.getDataLink().getReportInfo() == null || !accessForms.contains(formKey = dataNode.getDataLink().getReportInfo().getReportKey())) continue;
                    result.add(formula);
                    continue block0;
                }
                continue;
            }
            if (DataEngineConsts.FormulaType.CHECK != formula.getFormulaType() || !accessForms.contains(formula.getFormKey())) continue;
            if (Mode.FORM == mode) {
                result.add(formula);
                continue;
            }
            if (Mode.FORMULA != mode || (formulaKeys = formulaMaps.get(formula.getFormKey())) == null || !formulaKeys.contains(formula.getSource().getId())) continue;
            result.add(formula);
        }
        return result;
    }

    public List<FormulaUnitGroup> getFormulaUnitGroups(List<String> unitSet, String formulaSchemeKey) {
        if (this.formulaUnitGroupGetter != null) {
            return this.formulaUnitGroupGetter.get(unitSet, formulaSchemeKey, DataEngineConsts.FormulaType.CHECK);
        }
        ArrayList<FormulaUnitGroup> result = new ArrayList<FormulaUnitGroup>();
        FormulaUnitGroup formulaUnitGroup = new FormulaUnitGroup();
        formulaUnitGroup.setFormulaList(this.formulaRunTimeController.getParsedExpressionByForm(formulaSchemeKey, null, DataEngineConsts.FormulaType.CHECK));
        result.add(formulaUnitGroup);
        return result;
    }

    public List<String> getFormsInRange(Mode mode, List<String> rangeKeys, FormulaGetter formulaGetter) {
        List<String> formKeys = Mode.FORM == mode && !CollectionUtils.isEmpty(rangeKeys) ? rangeKeys : formulaGetter.getFormFromFormula(DataEngineConsts.FormulaType.CHECK, rangeKeys);
        return formKeys;
    }

    public List<IParsedExpression> customFilterFml(List<IParsedExpression> fml, String formSchemeKey, String formulaSchemeKey) {
        if (!CollectionUtils.isEmpty(this.formulaFilters)) {
            Map<String, List<IParsedExpression>> map = this.mapFmlByForm(fml);
            ArrayList<IParsedExpression> result = new ArrayList<IParsedExpression>();
            for (Map.Entry<String, List<IParsedExpression>> entry : map.entrySet()) {
                String formKey = entry.getKey();
                result.addAll(this.filterParsedExpressions(formulaSchemeKey, formKey, formSchemeKey, entry.getValue()));
            }
            return result;
        }
        return fml;
    }

    private Map<String, List<IParsedExpression>> mapFmlByForm(List<IParsedExpression> fml) {
        if (!CollectionUtils.isEmpty(fml)) {
            HashMap<String, List<IParsedExpression>> result = new HashMap<String, List<IParsedExpression>>();
            fml.forEach(o -> {
                String formKey = o.getSource().getFormKey();
                if (StringUtils.isEmpty((String)formKey)) {
                    formKey = "00000000-0000-0000-0000-000000000000";
                }
                if (result.containsKey(formKey)) {
                    ((List)result.get(formKey)).add(o);
                } else {
                    ArrayList<IParsedExpression> v = new ArrayList<IParsedExpression>();
                    v.add((IParsedExpression)o);
                    result.put(formKey, v);
                }
            });
            return result;
        }
        return Collections.emptyMap();
    }

    public FormulaCallBack getFmlCallBack(List<IParsedExpression> expressions, boolean jit) {
        FormulaCallBack formulaCallBack = new FormulaCallBack();
        if (jit) {
            formulaCallBack.getFormulas().addAll(expressions.stream().filter(CommonUtils.distinctByKey(IParsedExpression::getSource)).map(IParsedExpression::getSource).collect(Collectors.toList()));
        } else {
            formulaCallBack.getParsedExpressions().addAll(expressions);
        }
        return formulaCallBack;
    }

    public List<FormulaDefine> getFormulaDefines(String formulaSchemeKey, Collection<String> formulaKeys) {
        if (CollectionUtils.isEmpty(formulaKeys)) {
            return Collections.emptyList();
        }
        List allFormulasInScheme = this.formulaRunTimeController.getAllFormulasInScheme(formulaSchemeKey);
        return allFormulasInScheme.stream().filter(o -> o != null && formulaKeys.contains(o.getKey())).collect(Collectors.toList());
    }
}

