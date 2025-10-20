/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 */
package com.jiuqi.va.biz.ruler.impl;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.impl.model.PluginImpl;
import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.ruler.ModelFormulaHandle;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.common.util.ValidateFormulaUtils;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.impl.FormulaParam;
import com.jiuqi.va.biz.ruler.impl.RulerDefineImpl;
import com.jiuqi.va.biz.ruler.impl.RulerExecutor;
import com.jiuqi.va.biz.ruler.impl.TriggerImpl;
import com.jiuqi.va.biz.ruler.intf.CheckException;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.ruler.intf.Formula;
import com.jiuqi.va.biz.ruler.intf.Ruler;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.biz.utils.FormulaUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class RulerImpl
extends PluginImpl
implements Ruler {
    private static final Logger log = LoggerFactory.getLogger(RulerImpl.class);
    private RulerExecutor rulerExecutor;

    public RulerExecutor getRulerExecutor() {
        return this.rulerExecutor;
    }

    public void setRulerExecutor(RulerExecutor rulerExecutor) {
        this.rulerExecutor = rulerExecutor;
    }

    @Override
    public RulerDefineImpl getDefine() {
        return (RulerDefineImpl)super.getDefine();
    }

    @Override
    public List<CheckResult> execute(String triggerType) {
        ArrayList<CheckResult> checkResults = new ArrayList<CheckResult>();
        try {
            ModelDataContext context = this.getContext();
            HashMap<UUID, Formula> formulas = new HashMap<UUID, Formula>();
            HashMap<UUID, IExpression> expression = new HashMap<UUID, IExpression>();
            ModelFormulaHandle handle = ModelFormulaHandle.getInstance();
            this.gatherFormulas(context, triggerType, formulas, expression, handle);
            if (formulas.isEmpty() || expression.isEmpty()) {
                return checkResults;
            }
            ValidateFormulaUtils.validateFormulas(context, formulas, expression);
            for (Map.Entry formula : formulas.entrySet()) {
                List<CheckResult> result = this.doExecute(context, (Formula)formula.getValue(), (IExpression)expression.get(formula.getKey()));
                if (CollectionUtils.isEmpty(result)) continue;
                checkResults.addAll(result);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(BizBindingI18nUtil.getMessage("va.bizbinding.rulerimpl.executeformulaexception") + e.getMessage(), e);
        }
        return checkResults;
    }

    @Override
    public List<CheckResult> execute(List<FormulaParam> formulaParams, String triggerType) {
        ArrayList<CheckResult> checkResults = new ArrayList<CheckResult>();
        if (formulaParams == null || formulaParams.size() == 0) {
            return checkResults;
        }
        try {
            ModelDataContext context = this.getContext();
            HashMap<UUID, Formula> allFormulas = new HashMap<UUID, Formula>();
            HashMap<UUID, IExpression> allExpression = new HashMap<UUID, IExpression>();
            ModelFormulaHandle handle = ModelFormulaHandle.getInstance();
            this.gatherFormulas(context, triggerType, allFormulas, allExpression, handle);
            if (allFormulas.isEmpty() || allExpression.isEmpty()) {
                return checkResults;
            }
            for (FormulaParam formulaParam : formulaParams) {
                HashMap<UUID, Formula> filterFormulas = new HashMap<UUID, Formula>();
                HashMap<UUID, IExpression> filterExpression = new HashMap<UUID, IExpression>();
                this.filterFormulas(formulaParam, allFormulas, allExpression, filterFormulas, filterExpression);
                context.put(formulaParam.getTableName(), formulaParam.getRow());
                for (Map.Entry formula : filterFormulas.entrySet()) {
                    List<CheckResult> result = this.doExecute(context, (Formula)formula.getValue(), (IExpression)filterExpression.get(formula.getKey()));
                    if (CollectionUtils.isEmpty(result)) continue;
                    checkResults.addAll(result);
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(BizBindingI18nUtil.getMessage("va.bizbinding.rulerimpl.executeformulaexception") + e.getMessage(), e);
        }
        return checkResults;
    }

    @Override
    public List<CheckResult> execute(ModelDataContext context, FormulaImpl formulaImpl) {
        ArrayList<CheckResult> checkResults = new ArrayList<CheckResult>();
        IExpression expression = formulaImpl.getCompiledExpression();
        try {
            List<CheckResult> result;
            if (expression == null) {
                expression = ModelFormulaHandle.getInstance().parse(context, formulaImpl.getExpression(), formulaImpl.getFormulaType());
            }
            if (!CollectionUtils.isEmpty(result = this.doExecute(context, formulaImpl, expression))) {
                checkResults.addAll(result);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(BizBindingI18nUtil.getMessage("va.bizbinding.rulerimpl.executeformulaexception") + e.getMessage(), e);
        }
        return checkResults;
    }

    @Override
    public Object evaluate(ModelDataContext context, FormulaImpl formulaImpl) {
        try {
            IExpression expression = formulaImpl.getCompiledExpression();
            if (expression == null) {
                expression = ModelFormulaHandle.getInstance().parse(context, formulaImpl.getExpression(), formulaImpl.getFormulaType());
            }
            return expression.evaluate((IContext)context);
        }
        catch (Exception e) {
            throw new RuntimeException(BizBindingI18nUtil.getMessage("va.bizbinding.rulerimpl.executeformulaexception") + e.getMessage(), e);
        }
    }

    private void filterFormulas(FormulaParam formulaParam, Map<UUID, Formula> formulas, Map<UUID, IExpression> expression, Map<UUID, Formula> filterFormulas, Map<UUID, IExpression> filterExpression) {
        String filterValue = formulaParam.getTableName();
        if (StringUtils.hasText(formulaParam.getFieldName())) {
            filterValue = String.format("%s[%s]", filterValue, formulaParam.getFieldName());
        }
        for (Map.Entry<UUID, Formula> formulaEntry : formulas.entrySet()) {
            if (!formulaEntry.getValue().getExpression().contains(filterValue)) continue;
            filterFormulas.put(formulaEntry.getKey(), formulaEntry.getValue());
            filterExpression.put(formulaEntry.getKey(), expression.get(formulaEntry.getKey()));
        }
    }

    private void gatherFormulas(ModelDataContext context, String triggerType, Map<UUID, Formula> toDoFormulas, Map<UUID, IExpression> expressionMap, ModelFormulaHandle handle) {
        ListContainer<FormulaImpl> formulas = this.getDefine().getFormulas();
        Stream<UUID> formulaIds = this.getFormulaIds(triggerType);
        if (formulaIds == null || formulas == null) {
            return;
        }
        Iterator iterator = formulaIds.iterator();
        while (iterator.hasNext()) {
            UUID formulaId = (UUID)iterator.next();
            formulas.stream().forEach(formula -> {
                if (formula.getId().equals(formulaId)) {
                    toDoFormulas.put(formulaId, (Formula)formula);
                    try {
                        expressionMap.put(formulaId, handle.parse(context, formula.getExpression(), formula.getFormulaType()));
                    }
                    catch (ParseException e) {
                        throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.rulerimpl.compilationerror", new Object[]{formula.getName()}), e);
                    }
                }
            });
        }
    }

    private List<CheckResult> doExecute(ModelDataContext context, Formula formula, IExpression expression) throws Exception {
        try {
            if (FormulaType.CHECK.equals((Object)formula.getFormulaType()) || FormulaType.WARN.equals((Object)formula.getFormulaType())) {
                Object result = expression.evaluate((IContext)context);
                if (result != null) {
                    if (result instanceof Boolean) {
                        if (!((Boolean)result).booleanValue()) {
                            return Collections.singletonList(FormulaUtils.gatherCheckResult(context, formula, expression));
                        }
                    } else if (StringUtils.hasText((String)result)) {
                        ((FormulaImpl)formula).setCheckMessage((String)result);
                        return Collections.singletonList(FormulaUtils.gatherCheckResult(context, formula, expression));
                    }
                }
            } else {
                expression.execute((IContext)context);
            }
            return null;
        }
        catch (CheckException checkException) {
            if (checkException.getCheckMessages() != null) {
                return checkException.getCheckMessages();
            }
            log.error(checkException.getMessage(), checkException);
            throw new Exception(BizBindingI18nUtil.getMessage("va.bizbinding.rulerimpl.executeerror", new Object[]{formula.getName()}) + checkException.getMessage(), checkException);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception(BizBindingI18nUtil.getMessage("va.bizbinding.rulerimpl.executeerror", new Object[]{formula.getName()}) + e.getMessage(), e);
        }
    }

    private Stream<UUID> getFormulaIds(String triggerType) {
        ListContainer<TriggerImpl> allTriggers = this.getDefine().getTriggers();
        if (allTriggers == null || !StringUtils.hasText(triggerType)) {
            return null;
        }
        TriggerImpl triggerImpl = allTriggers.stream().filter(o -> triggerType.equals(o.getTriggerType())).findFirst().orElse(null);
        return triggerImpl == null ? null : triggerImpl.getFormulaList();
    }

    private ModelDataContext getContext() {
        ModelDataContext context = new ModelDataContext(this.getModel());
        return context;
    }
}

