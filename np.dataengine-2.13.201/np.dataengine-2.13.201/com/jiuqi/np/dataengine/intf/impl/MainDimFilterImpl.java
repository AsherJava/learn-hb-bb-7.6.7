/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.np.dataengine.intf.impl;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.common.TempResource;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.executors.ExprExecNetwork;
import com.jiuqi.np.dataengine.executors.FmlExecRegionCreator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMainDimFilter;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainDimFilterImpl
implements IMainDimFilter {
    private QueryParam queryParam;

    public MainDimFilterImpl(QueryParam queryParam) {
        this.queryParam = queryParam;
    }

    @Override
    public Map<String, List<String>> filterByFormulas(ExecutorContext executorContext, DimensionValueSet masterKeys, List<Formula> checkFormulas, TempAssistantTable tempAssistantTable) throws Exception {
        HashMap<String, List<String>> result = new HashMap<String, List<String>>();
        String mainDimension = this.findMainDimension(executorContext, masterKeys);
        QueryContext qContext = this.createQueryContext(executorContext, masterKeys, mainDimension);
        MainDimFilterMonitor monitor = (MainDimFilterMonitor)qContext.getMonitor();
        try (TempResource tempResource = new TempResource();){
            qContext.setTempResource(tempResource);
            tempResource.setConnectionProvider(this.queryParam.getConnectionProvider());
            if (tempAssistantTable != null) {
                tempResource.addTempAssistantTable(mainDimension, tempAssistantTable);
            }
            ReportFormulaParser formulaParser = executorContext.getCache().getFormulaParser(executorContext);
            FmlExecRegionCreator regionCreator = new FmlExecRegionCreator(qContext, this.queryParam);
            ExprExecNetwork execNetwork = new ExprExecNetwork(qContext, regionCreator);
            for (Formula formula : checkFormulas) {
                try {
                    IExpression condition = formulaParser.parseEval(formula.getFormula(), (IContext)qContext);
                    CheckExpression checkExpression = new CheckExpression(condition, formula);
                    execNetwork.arrangeCheckExpression(checkExpression);
                }
                catch (ParseException e) {
                    throw new ParseException("\u89e3\u6790\u516c\u5f0f\u51fa\u9519\uff1a" + formula.getFormula() + " /n" + e.getMessage(), (Throwable)e);
                }
            }
            execNetwork.initialize(monitor);
            execNetwork.checkRunTask(monitor);
            monitor.finish();
        }
        Map<String, Set<String>> filteredKeys = monitor.getFilteredKeys();
        List allValues = (List)masterKeys.getValue(mainDimension);
        for (Formula formula : checkFormulas) {
            Set<String> falseDimValues = filteredKeys.get(formula.getId());
            ArrayList filteredDimValues = new ArrayList(allValues);
            if (falseDimValues != null) {
                filteredDimValues.removeAll(falseDimValues);
            }
            result.put(formula.getId(), filteredDimValues);
        }
        return result;
    }

    @Override
    public Map<String, List<String>> filterByFormulas(ExecutorContext executorContext, DimensionValueSet masterKeys, List<Formula> checkFormulas) throws Exception {
        return this.filterByFormulas(executorContext, masterKeys, checkFormulas, null);
    }

    @Override
    public List<String> filterByCondition(ExecutorContext executorContext, DimensionValueSet masterKeys, String condition) throws Exception {
        String id = "MainDimFilter";
        Formula formula = new Formula();
        String filter = condition;
        formula.setFormula(filter);
        formula.setReportName("default");
        formula.setCode(id);
        formula.setId(id);
        ArrayList<Formula> checkFormulas = new ArrayList<Formula>(1);
        checkFormulas.add(formula);
        Map<String, List<String>> map = this.filterByFormulas(executorContext, masterKeys, checkFormulas);
        return map.get(id);
    }

    private String findMainDimension(ExecutorContext executorContext, DimensionValueSet masterKeys) throws ParseException {
        String mainDimension = null;
        IFmlExecEnvironment env = executorContext.getEnv();
        if (env != null) {
            mainDimension = env.getUnitDimesion(executorContext);
            String currentEntityId = executorContext.getCurrentEntityId();
            if (StringUtils.isNotEmpty((String)currentEntityId)) {
                mainDimension = executorContext.getCache().getDataModelDefinitionsCache().getDimensionProvider().getDimensionNameByEntityId(currentEntityId);
            }
        }
        if (mainDimension == null) {
            for (int i = 0; i < masterKeys.size(); ++i) {
                Object dimValue = masterKeys.getValue(i);
                if (!(dimValue instanceof List)) continue;
                mainDimension = masterKeys.getName(i);
            }
        }
        return mainDimension;
    }

    private QueryContext createQueryContext(ExecutorContext executorContext, DimensionValueSet masterKeys, String mainDimension) throws Exception {
        MainDimFilterMonitor monitior = new MainDimFilterMonitor();
        monitior.setMainDim(mainDimension);
        QueryContext cContext = new QueryContext(executorContext, this.queryParam, monitior);
        cContext.setRunnerType(DataEngineConsts.DataEngineRunType.JUDGE);
        cContext.setMasterKeys(masterKeys);
        cContext.setBatch(true);
        return cContext;
    }

    private class MainDimFilterMonitor
    extends AbstractMonitor {
        private Map<String, Set<String>> filteredKeys = new HashMap<String, Set<String>>();
        private String mainDim;

        private MainDimFilterMonitor() {
        }

        @Override
        public void error(CheckExpression expression, QueryContext context) throws SyntaxException, DataTypeException {
            String key = expression.getSource().getId();
            String dimValue = (String)context.getRowKey().getValue(this.mainDim);
            Set<String> dimValues = this.filteredKeys.get(key);
            if (dimValues == null) {
                dimValues = new HashSet<String>();
                this.filteredKeys.put(key, dimValues);
            }
            if (dimValue != null) {
                dimValues.add(dimValue);
            }
        }

        public void setMainDim(String mainDim) {
            this.mainDim = mainDim;
        }

        public Map<String, Set<String>> getFilteredKeys() {
            return this.filteredKeys;
        }
    }
}

