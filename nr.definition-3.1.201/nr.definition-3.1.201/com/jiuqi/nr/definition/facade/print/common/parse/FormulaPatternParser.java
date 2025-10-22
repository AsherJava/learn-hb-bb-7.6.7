/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.exception.ExpressionException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.period.IPeriodAdapter
 */
package com.jiuqi.nr.definition.facade.print.common.parse;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.print.common.parse.AbstractSETParser;
import com.jiuqi.nr.definition.facade.print.common.parse.ParseContext;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormulaPatternParser
extends AbstractSETParser {
    private static final Logger logger = LoggerFactory.getLogger(FormulaPatternParser.class);

    public FormulaPatternParser(String startTag, String endTag) {
        super(startTag, endTag);
    }

    public boolean canReUser() {
        return true;
    }

    @Override
    protected String doParse(ParseContext parseContext, String pattern) {
        VariableManager manager;
        List<Variable> variables;
        ExecutorContext executorContext = parseContext.getExecutorContext();
        IExpressionEvaluator expressionEvaluator = parseContext.getExpressionEvaluator();
        DimensionValueSet dimensionValueSet = parseContext.getDimensionValueSet();
        if (null == executorContext || null == expressionEvaluator) {
            IDataAccessProvider dataAccessProvider = BeanUtil.getBean(IDataAccessProvider.class);
            IDataDefinitionRuntimeController runtimeController = BeanUtil.getBean(IDataDefinitionRuntimeController.class);
            IRunTimeViewController controller = BeanUtil.getBean(IRunTimeViewController.class);
            IEntityViewRunTimeController entityViewRunTimeController = BeanUtil.getBean(IEntityViewRunTimeController.class);
            expressionEvaluator = dataAccessProvider.newExpressionEvaluator();
            String formSchemeKey = parseContext.getFormSchemeKey();
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(controller, runtimeController, entityViewRunTimeController, formSchemeKey);
            executorContext = new ExecutorContext(runtimeController);
            executorContext.setEnv((IFmlExecEnvironment)environment);
            executorContext.setJQReportModel(true);
        }
        if (null != (variables = parseContext.getVariables()) && variables.size() > 0 && null != (manager = executorContext.getVariableManager())) {
            for (Variable variable : variables) {
                manager.add(variable);
            }
        }
        AbstractData result = null;
        try {
            result = expressionEvaluator.eval(pattern, executorContext, dimensionValueSet);
        }
        catch (ExpressionException e) {
            logger.error("\u89e3\u6790\u6587\u5b57\u6807\u7b7e\u7684\u65f6\u5019\uff0c\u672a\u77e5\u7684\u5b57\u7b26\u65e0\u6cd5\u89e3\u6790\uff1a" + pattern);
            logger.error(e.getMessage(), e);
        }
        String value = "";
        if (null != result) {
            value = this.doParseSysToUser(executorContext, pattern, result);
            return value;
        }
        return value;
    }

    private boolean isIntegerForDouble(double obj) {
        double eps = 1.0E-10;
        return obj - Math.floor(obj) < eps;
    }

    private String doParseSysToUser(ExecutorContext executorContext, String pattern, AbstractData result) {
        if (result.isNull) {
            return "";
        }
        String value = "";
        if ("CUR_PERIODSTR".equalsIgnoreCase(pattern) || "[CUR_PERIODSTR]".equalsIgnoreCase(pattern)) {
            if (null != result.getAsString()) {
                IPeriodAdapter periodAdapter = executorContext.getEnv().getPeriodAdapter(executorContext);
                value = periodAdapter.getPeriodTitle(result.getAsString());
            }
        } else if ("CUR_PERIOD".equalsIgnoreCase(pattern) || "[CUR_PERIOD]".equalsIgnoreCase(pattern)) {
            if (null != result.getAsString()) {
                String asString = result.getAsString();
                value = FormulaPatternParser.convertType2Str(asString);
            }
        } else if (3 == result.dataType) {
            try {
                double res = result.getAsFloat();
                if (this.isIntegerForDouble(res)) {
                    return (int)Math.round(res) + "";
                }
                return res + "";
            }
            catch (DataTypeException dataTypeException) {}
        } else if (4 == result.dataType) {
            try {
                return result.getAsInt() + "";
            }
            catch (DataTypeException dataTypeException) {
            }
        } else {
            value = result.getAsString();
        }
        return value;
    }

    public static String convertType2Str(String periodType) {
        String strType = null;
        switch (periodType) {
            case "N": {
                strType = "\u5e74";
                break;
            }
            case "H": {
                strType = "\u534a\u5e74";
                break;
            }
            case "J": {
                strType = "\u5b63";
                break;
            }
            case "Y": {
                strType = "\u6708";
                break;
            }
            case "X": {
                strType = "\u65ec";
                break;
            }
            case "R": {
                strType = "\u65e5";
                break;
            }
            case "Z": {
                strType = "\u5468";
                break;
            }
            case "B": {
                strType = "\u671f";
            }
        }
        return strType;
    }

    @Override
    public boolean canReuser(String patternStr) {
        return true;
    }
}

