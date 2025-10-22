/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMainDimFilter
 *  com.jiuqi.np.definition.common.FormulaCheckType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.provider.IBusinessEntityService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.data.engine.condition.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMainDimFilter;
import com.jiuqi.np.definition.common.FormulaCheckType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.data.engine.condition.IConditionCache;
import com.jiuqi.nr.data.engine.condition.IFormConditionService;
import com.jiuqi.nr.data.engine.condition.impl.ConditionCacheImpl;
import com.jiuqi.nr.data.engine.condition.impl.FormGroupItem;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.provider.IBusinessEntityService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormConditionServiceImpl
implements IFormConditionService {
    private static final Logger logger = LoggerFactory.getLogger(FormConditionServiceImpl.class);
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IBusinessEntityService businessEntityService;
    @Autowired
    private IEntityMetaService entityMetaService;

    @Override
    public IConditionCache getConditionForms(DimensionValueSet dimensionValueSet, String formSchemeKey) {
        List<String> entityDims = this.getEntityDimensions(formSchemeKey);
        ConditionCacheImpl conditionCache = new ConditionCacheImpl();
        FormGroupItem groupItem = new FormGroupItem();
        HashSet<String> formKeys = new HashSet<String>();
        HashSet<String> formGroups = new HashSet<String>();
        HashMap<String, List<String>> formsByGroup = new HashMap<String, List<String>>();
        Map<String, String> conditions = this.getFormConditions(formSchemeKey, formKeys, formsByGroup, formGroups, groupItem);
        groupItem.setFormKeys(new ArrayList<String>(formKeys));
        groupItem.setGroupKeys(new ArrayList<String>(formGroups));
        groupItem.setFormsByGroup(formsByGroup);
        conditionCache.setEntityDimensions(entityDims);
        conditionCache.setGroupItem(groupItem);
        conditionCache.setConditionMap(null);
        if (conditions == null || conditions.size() <= 0) {
            return conditionCache;
        }
        Map<DimensionValueSet, Set<String>> conditionResultInitCache = this.getConditionResultInitCache(dimensionValueSet, conditions, formSchemeKey);
        conditionCache.setConditionMap(conditionResultInitCache);
        return conditionCache;
    }

    private List<String> getEntityDimensions(String formSchemeKey) {
        try {
            FormSchemeDefine formSchemeDefine = this.runtimeView.getFormScheme(formSchemeKey);
            String[] entitys = formSchemeDefine.getMasterEntitiesKey().split(";");
            ArrayList<String> entityDimensions = new ArrayList<String>();
            for (String viewKey : entitys) {
                String entityDim = this.businessEntityService.getDimensionNameByEntityId(viewKey);
                entityDimensions.add(entityDim);
            }
            return entityDimensions;
        }
        catch (Exception ex) {
            return null;
        }
    }

    private Map<DimensionValueSet, Set<String>> getConditionResultInitCache(DimensionValueSet dimensionValueSet, Map<String, String> conditions, String formSchemeKey) {
        HashMap<DimensionValueSet, Set> result = null;
        List expressions = this.formulaRunTimeController.getFormConditionsByFormScheme(formSchemeKey);
        if ((expressions == null || expressions.size() <= 0) && conditions.size() <= 0) {
            return result;
        }
        EntityViewDefine dwEntityView = this.runtimeView.getViewByFormSchemeKey(formSchemeKey);
        String dimensionName = this.entityMetaService.getDimensionName(dwEntityView.getEntityId());
        List unitKeys = new ArrayList<String>();
        Object value = dimensionValueSet.getValue(dimensionName);
        if (value instanceof String) {
            unitKeys.add(value.toString());
        } else {
            unitKeys = (List)value;
        }
        try {
            ExecutorContext executorContext = this.getExecutorContext(formSchemeKey);
            ArrayList<Formula> formulas = new ArrayList<Formula>();
            for (String key : conditions.keySet()) {
                Formula formula = new Formula();
                formula.setId(key);
                formula.setFormKey(key);
                String formulaVale = conditions.get(key);
                if (!StringUtils.isNotEmpty((String)formulaVale) || formulaVale.startsWith("//")) continue;
                formula.setFormula(conditions.get(key));
                formula.setChecktype(Integer.valueOf(FormulaCheckType.FORMULA_CHECK_ERROR.getValue()));
                formulas.add(formula);
            }
            result = new HashMap<DimensionValueSet, Set>();
            IMainDimFilter mainDimFilter = this.dataAccessProvider.newMainDimFilter();
            DimensionValueSet dimensionTemp = new DimensionValueSet(dimensionValueSet);
            dimensionTemp.setValue(dimensionName, unitKeys);
            Map formulasMap = mainDimFilter.filterByFormulas(executorContext, dimensionTemp, formulas);
            if (formulasMap != null && formulasMap.size() > 0) {
                for (Map.Entry formulaItem : formulasMap.entrySet()) {
                    String formKey = (String)formulaItem.getKey();
                    List unitTemp = (List)formulaItem.getValue();
                    for (String unitKey : unitKeys) {
                        DimensionValueSet dim = new DimensionValueSet();
                        dim.setValue(dimensionName, (Object)unitKey);
                        dim.setValue("DATATIME", dimensionValueSet.getValue("DATATIME"));
                        if (unitTemp != null && unitTemp.contains(unitKey)) continue;
                        Set formKeyTemp = (Set)result.get(dim);
                        if (formKeyTemp == null) {
                            HashSet<String> formKeysTemp = new HashSet<String>();
                            formKeysTemp.add(formKey);
                            result.put(dim, formKeysTemp);
                            continue;
                        }
                        formKeyTemp.add(formKey);
                        result.put(dim, formKeyTemp);
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    private ExecutorContext getExecutorContext(String formSchemeKey) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeKey, null, null);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        return executorContext;
    }

    private Map<String, String> getFormConditions(String formSchemeKey, Set<String> formKeys, Map<String, List<String>> formsByGroup, Set<String> formGroups, FormGroupItem groupItem) {
        HashMap<String, String> conditions = new HashMap<String, String>();
        List rootFormGroups = this.runtimeView.queryRootGroupsByFormScheme(formSchemeKey);
        if (rootFormGroups != null) {
            for (FormGroupDefine formGroupDefine : rootFormGroups) {
                formGroups.add(formGroupDefine.getKey());
                this.getFormGroupData(formGroupDefine, formSchemeKey, conditions, formKeys, formsByGroup, formGroups, groupItem);
            }
        }
        return conditions;
    }

    private void getFormGroupData(FormGroupDefine formGroupDefine, String formSchemeKey, Map<String, String> conditions, Set<String> formKeys, Map<String, List<String>> formsByGroup, Set<String> formGroups, FormGroupItem groupItem) {
        List formGroupDefines;
        if (StringUtils.isNotEmpty((String)formGroupDefine.getCondition())) {
            conditions.put(formGroupDefine.getKey(), formGroupDefine.getCondition());
            groupItem.setGroupCondition(true);
        }
        if ((formGroupDefines = this.runtimeView.getChildFormGroups(formGroupDefine.getKey())) != null) {
            for (FormGroupDefine childFormGroupDefine : formGroupDefines) {
                formGroups.add(childFormGroupDefine.getKey());
                this.getFormGroupData(childFormGroupDefine, formSchemeKey, conditions, formKeys, formsByGroup, formGroups, groupItem);
            }
        }
        List formDefines = null;
        try {
            formDefines = this.runtimeView.getAllFormsInGroupWithoutOrder(formGroupDefine.getKey(), false);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        if (formDefines != null) {
            List<String> formsInGroup = formsByGroup.get(formGroupDefine.getKey());
            if (formsInGroup == null) {
                formsInGroup = new ArrayList<String>();
                formsByGroup.put(formGroupDefine.getKey(), formsInGroup);
            }
            for (FormDefine formDefine : formDefines) {
                formKeys.add(formDefine.getKey());
                formsInGroup.add(formDefine.getKey());
                if (!StringUtils.isNotEmpty((String)formDefine.getFormCondition())) continue;
                conditions.put(formDefine.getKey(), formDefine.getFormCondition());
                groupItem.setFormCondition(true);
            }
        }
    }
}

