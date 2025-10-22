/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.var.VariableManagerBase
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.i18n.ext.I18NResource
 *  com.jiuqi.np.i18n.ext.I18NResourceItem
 */
package com.jiuqi.nr.definition.i18n;

import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.var.VariableManagerBase;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.i18n.ext.I18NResource;
import com.jiuqi.np.i18n.ext.I18NResourceItem;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaVariDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class I18nFuncRegister
implements I18NResource {
    private static final Logger logger = LoggerFactory.getLogger(I18nFuncRegister.class);
    static final String NAME = "\u65b0\u62a5\u8868/\u516c\u5f0f\u7f16\u8f91\u5668";
    static final String NAME_SPACE = "func_type";
    public static final String FUNC_ = "TASK#FUNC#";
    static final String FUNC_GROUP = "TASK#FUNC#GROUP";
    static final String VARIABLE_GROUP = "TASK#VARIABLE#";
    static final String VARIABLE_SYS_GROUP = "TASK#SYS#VARIABLE#";
    private IDataDefinitionRuntimeController runtimeController;
    private IFormulaDesignTimeController designTimeController;
    private IDesignTimeViewController designTimeViewController;

    public IDataDefinitionRuntimeController getRuntimeController() {
        if (this.runtimeController == null) {
            this.runtimeController = BeanUtil.getBean(IDataDefinitionRuntimeController.class);
        }
        return this.runtimeController;
    }

    public IFormulaDesignTimeController getDesignTimeController() {
        if (this.designTimeController == null) {
            this.designTimeController = BeanUtil.getBean(IFormulaDesignTimeController.class);
        }
        return this.designTimeController;
    }

    public IDesignTimeViewController getDesignTimeViewController() {
        if (this.designTimeViewController == null) {
            this.designTimeViewController = BeanUtil.getBean(IDesignTimeViewController.class);
        }
        return this.designTimeViewController;
    }

    public String name() {
        return NAME;
    }

    public String getNameSpace() {
        return NAME_SPACE;
    }

    public List<I18NResourceItem> getCategory(String parentId) {
        ArrayList<I18NResourceItem> items = new ArrayList<I18NResourceItem>();
        items.add(new I18NResourceItem(FUNC_GROUP, "\u51fd\u6570\u5206\u7ec4"));
        items.add(new I18NResourceItem(VARIABLE_GROUP, "\u516c\u5f0f\u53d8\u91cf"));
        items.add(new I18NResourceItem(VARIABLE_SYS_GROUP, "\u7cfb\u7edf\u516c\u5f0f\u53d8\u91cf"));
        this.getFunctions().forEach(e -> items.add(new I18NResourceItem(FUNC_ + e.name(), e.title())));
        return items;
    }

    public List<I18NResourceItem> getResource(String parentId) {
        ArrayList<I18NResourceItem> resourceObjects = new ArrayList<I18NResourceItem>();
        if (!StringUtils.hasText(parentId)) {
            return Collections.emptyList();
        }
        if (FUNC_GROUP.equals(parentId)) {
            Set<IFunction> functions = this.getFunctions();
            functions.stream().map(IFunction::category).distinct().forEach(e -> resourceObjects.add(new I18NResourceItem(I18nFuncRegister.getCategoryKey(e), e)));
        } else {
            if (VARIABLE_GROUP.equals(parentId)) {
                return this.variable();
            }
            if (VARIABLE_SYS_GROUP.equals(parentId)) {
                return this.sysVariable();
            }
            Map<String, IFunction> nameMap = this.getFunctions().stream().collect(Collectors.toMap(IFunction::name, e -> e));
            IFunction function = nameMap.get(parentId.substring(FUNC_.length()));
            if (function != null) {
                I18NResourceItem desc = new I18NResourceItem(I18nFuncRegister.getDescKey(parentId), function.title());
                resourceObjects.add(desc);
                List parameters = function.parameters();
                for (IParameter parameter : parameters) {
                    String name = parameter.name();
                    String title = parameter.title();
                    I18NResourceItem param = new I18NResourceItem(I18nFuncRegister.getParamKey(parentId, name), title);
                    resourceObjects.add(param);
                }
            }
        }
        return resourceObjects;
    }

    private Set<IFunction> getFunctions() {
        DefinitionsCache cache;
        ExecutorContext executorContext = new ExecutorContext(this.getRuntimeController());
        try {
            cache = new DefinitionsCache(executorContext);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
        ReportFormulaParser formulaParser = cache.getFormulaParser(executorContext);
        return formulaParser.allFunctions();
    }

    private List<I18NResourceItem> variable() {
        ArrayList<I18NResourceItem> items = new ArrayList<I18NResourceItem>();
        List<DesignFormSchemeDefine> formschemes = this.getDesignTimeViewController().listAllFormScheme();
        HashSet variableCode = new HashSet();
        for (DesignFormSchemeDefine formscheme : formschemes) {
            List<FormulaVariDefine> queryAllFormulaVariable = this.getDesignTimeController().queryAllFormulaVariable(formscheme.getKey());
            queryAllFormulaVariable.forEach(define -> {
                String code = define.getCode();
                if (!variableCode.contains(code)) {
                    items.add(new I18NResourceItem(I18nFuncRegister.getVariableKey(code), define.getTitle()));
                    variableCode.add(code);
                }
            });
        }
        return items;
    }

    private List<I18NResourceItem> sysVariable() {
        ArrayList<I18NResourceItem> items = new ArrayList<I18NResourceItem>();
        VariableManagerBase variableManager = (VariableManagerBase)ExecutorContext.contextVariableManagerProvider.getNormalContextVariableManager();
        List allVars = variableManager.getAllVars();
        if (!CollectionUtils.isEmpty(allVars)) {
            allVars.stream().forEach(var -> {
                String varName = var.getVarName();
                String varTitle = var.getVarTitle();
                items.add(new I18NResourceItem(I18nFuncRegister.getSysVariableKey(varName), varTitle));
            });
        }
        return items;
    }

    public static String getCategoryKey(String name) {
        return "TASK#FUNC#CATEGORY#" + name;
    }

    public static String getDescKey(String func) {
        return func + "#DESC";
    }

    public static String getParamKey(String func, String param) {
        return func + "#PARAM#" + param;
    }

    public static String getVariableKey(String variable) {
        return VARIABLE_GROUP + variable;
    }

    public static String getSysVariableKey(String variable) {
        return VARIABLE_SYS_GROUP + variable;
    }
}

