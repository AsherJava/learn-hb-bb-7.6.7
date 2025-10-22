/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FuncReadWriteType
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.util.DataEngineFormulaParser
 */
package com.jiuqi.nr.data.engine.init;

import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.util.DataEngineFormulaParser;
import com.jiuqi.nr.data.engine.fml.var.ContextVariableManager;
import com.jiuqi.nr.data.engine.fml.var.PriorityContextVariableManager;
import com.jiuqi.nr.data.engine.fml.var.VarADJUST_TITLE;
import com.jiuqi.nr.data.engine.fml.var.VarAdjust;
import com.jiuqi.nr.data.engine.fml.var.VarCCY;
import com.jiuqi.nr.data.engine.fml.var.VarCUR_PERIOD;
import com.jiuqi.nr.data.engine.fml.var.VarCUR_PERIODSTR;
import com.jiuqi.nr.data.engine.fml.var.VarCUR_PERIODTITLE;
import com.jiuqi.nr.data.engine.fml.var.VarCUR_TIME;
import com.jiuqi.nr.data.engine.fml.var.VarCUR_TIMEKEY;
import com.jiuqi.nr.data.engine.fml.var.VarCUR_YEAR;
import com.jiuqi.nr.data.engine.fml.var.VarCurUserORG;
import com.jiuqi.nr.data.engine.fml.var.VarDWDM;
import com.jiuqi.nr.data.engine.fml.var.VarDWKJ;
import com.jiuqi.nr.data.engine.fml.var.VarDWMC;
import com.jiuqi.nr.data.engine.fml.var.VarSYS_SRC_TQRQ;
import com.jiuqi.nr.data.engine.fml.var.VarSYS_UNITCODE;
import com.jiuqi.nr.data.engine.fml.var.VarSYS_UNITKEY;
import com.jiuqi.nr.data.engine.fml.var.VarSYS_UNITTITLE;
import com.jiuqi.nr.data.engine.fml.var.VarSYS_YEAR;
import com.jiuqi.nr.data.engine.fml.var.VarUSER_DESCRIPTION;
import com.jiuqi.nr.data.engine.fml.var.VarUSER_GUID;
import com.jiuqi.nr.data.engine.fml.var.VarUSER_NAME;
import com.jiuqi.nr.data.engine.fml.var.VarUSER_TITLE;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
public class DataEngineStartUp {
    private static final boolean NR_FUNCTIONS_CHECK = "true".equals(System.getProperty("nr.dataengine.functions.check"));

    public void init() throws Exception {
        ContextVariableManager normalContextVariableManager = new ContextVariableManager();
        ExecutorContext.contextVariableManagerProvider.setNormalContextVariableManager((IReportDynamicNodeProvider)normalContextVariableManager);
        PriorityContextVariableManager priorityContextVariableManager = new PriorityContextVariableManager();
        ExecutorContext.contextVariableManagerProvider.setPriorityContextVariableManager((IReportDynamicNodeProvider)priorityContextVariableManager);
        this.initNormalVars(normalContextVariableManager);
        this.initPriorityVars(priorityContextVariableManager);
    }

    protected void initPriorityVars(ContextVariableManager contextVariableManager) {
        contextVariableManager.add(new VarCUR_YEAR());
        contextVariableManager.add(new VarSYS_YEAR());
        contextVariableManager.add(new VarCUR_PERIOD());
        contextVariableManager.add(new VarCUR_TIME());
        contextVariableManager.add(new VarCUR_PERIODSTR());
        contextVariableManager.add(new VarCUR_PERIODTITLE());
        contextVariableManager.add(new VarUSER_GUID());
        contextVariableManager.add(new VarUSER_NAME());
        contextVariableManager.add(new VarUSER_TITLE());
        contextVariableManager.add(new VarUSER_DESCRIPTION());
        contextVariableManager.add(new VarSYS_UNITCODE());
        contextVariableManager.add(new VarSYS_UNITTITLE());
        contextVariableManager.add(new VarSYS_SRC_TQRQ());
        contextVariableManager.add(new VarCUR_TIMEKEY());
        contextVariableManager.add(new VarSYS_UNITKEY());
    }

    protected void initNormalVars(ContextVariableManager contextVariableManager) {
        contextVariableManager.add(new VarCUR_YEAR());
        contextVariableManager.add(new VarSYS_YEAR());
        contextVariableManager.add(new VarCUR_PERIOD());
        contextVariableManager.add(new VarCUR_TIME());
        contextVariableManager.add(new VarCUR_PERIODSTR());
        contextVariableManager.add(new VarCUR_PERIODTITLE());
        contextVariableManager.add(new VarUSER_GUID());
        contextVariableManager.add(new VarUSER_NAME());
        contextVariableManager.add(new VarUSER_TITLE());
        contextVariableManager.add(new VarUSER_DESCRIPTION());
        contextVariableManager.add(new VarSYS_UNITCODE());
        contextVariableManager.add(new VarSYS_UNITTITLE());
        contextVariableManager.add(new VarDWDM());
        contextVariableManager.add(new VarDWKJ());
        contextVariableManager.add(new VarDWMC());
        contextVariableManager.add(new VarSYS_SRC_TQRQ());
        contextVariableManager.add(new VarCurUserORG());
        contextVariableManager.add(new VarAdjust());
        contextVariableManager.add(new VarADJUST_TITLE());
        contextVariableManager.add(new VarCUR_TIMEKEY());
        contextVariableManager.add(new VarSYS_UNITKEY());
        contextVariableManager.add(new VarCCY());
    }

    public void initWhenStarted() throws Exception {
        if (NR_FUNCTIONS_CHECK) {
            ReportFunctionProvider funcProvider = ReportFunctionProvider.GLOBAL_PROVIDER;
            ArrayList<IFunction> deprecatedFunctions = new ArrayList<IFunction>();
            ArrayList<IFunction> unKnownRWFunctions = new ArrayList<IFunction>();
            ArrayList<IFunction> notIReportFunctions = new ArrayList<IFunction>();
            for (IFunction func : funcProvider) {
                if (func instanceof IReportFunction) {
                    IReportFunction reportFunc = (IReportFunction)func;
                    if (reportFunc.getReadWriteType() == DataEngineConsts.FuncReadWriteType.UNKNOWN) {
                        unKnownRWFunctions.add(func);
                    }
                } else if (!DataEngineFormulaParser.innerFunctions.contains(func.name())) {
                    notIReportFunctions.add(func);
                }
                if (!func.isDeprecated()) continue;
                deprecatedFunctions.add(func);
            }
            StringBuilder msg = new StringBuilder();
            if (deprecatedFunctions.size() > 0) {
                msg.append("\n\u5df2\u5e9f\u5f03\u7684\u51fd\u6570\uff0c\u516c\u5f0f\u5411\u5bfc\u4e2d\u4e0d\u53ef\u89c1\uff1a\n");
                for (IFunction func : deprecatedFunctions) {
                    this.printFunction(msg, func);
                }
            }
            if (unKnownRWFunctions.size() > 0) {
                msg.append("\n\u8bfb\u5199\u8303\u56f4\u672a\u77e5\u7684\u51fd\u6570\uff0c\u4f1a\u5bfc\u81f4\u65e0\u6cd5\u81ea\u52a8\u6839\u636e\u4f9d\u8d56\u5173\u7cfb\u53ea\u8fd0\u7b97\u6570\u636e\u76f8\u5173\u516c\u5f0f\uff0c\u800c\u53ea\u80fd\u5168\u7b97\uff1a\n");
                for (IFunction func : unKnownRWFunctions) {
                    this.printFunction(msg, func);
                }
            }
            if (notIReportFunctions.size() > 0) {
                msg.append("\n\u672a\u5b9e\u73b0IReportFunction\u63a5\u53e3\u7684\u51fd\u6570\uff0c\u4f1a\u5bfc\u81f4\u65e0\u6cd5\u81ea\u52a8\u6839\u636e\u4f9d\u8d56\u5173\u7cfb\u53ea\u8fd0\u7b97\u6570\u636e\u76f8\u5173\u516c\u5f0f\uff0c\u800c\u53ea\u80fd\u5168\u7b97\uff1a\n");
                for (IFunction func : notIReportFunctions) {
                    this.printFunction(msg, func);
                }
            }
            DataEngineUtil.logger.info(msg.toString());
        }
    }

    private void printFunction(StringBuilder msg, IFunction func) {
        msg.append(func.name()).append(" | ").append(func.title()).append(":    ").append(func.getClass().getName()).append("\n");
    }
}

