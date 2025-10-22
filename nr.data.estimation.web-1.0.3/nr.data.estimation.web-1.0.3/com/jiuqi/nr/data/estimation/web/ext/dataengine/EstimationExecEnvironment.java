/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataLinkFinder
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.np.dataengine.intf.IFieldValueProcessor
 *  com.jiuqi.np.dataengine.intf.IFieldValueUpdateProcessor
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.ITableNameFinder
 *  com.jiuqi.np.dataengine.intf.ZBAuthJudger
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.IPeriodAdapter
 */
package com.jiuqi.nr.data.estimation.web.ext.dataengine;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataLinkFinder;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.intf.IFieldValueProcessor;
import com.jiuqi.np.dataengine.intf.IFieldValueUpdateProcessor;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.ITableNameFinder;
import com.jiuqi.np.dataengine.intf.ZBAuthJudger;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.IPeriodAdapter;
import java.util.List;
import java.util.Map;

public class EstimationExecEnvironment
implements IFmlExecEnvironment {
    private IFmlExecEnvironment oriEnv;
    private ITableNameFinder tableNameFinder;

    public EstimationExecEnvironment(IFmlExecEnvironment oriEnv, ITableNameFinder tableNameFinder) {
        this.oriEnv = oriEnv;
        this.tableNameFinder = tableNameFinder;
    }

    public IDataLinkFinder getDataLinkFinder() {
        return this.oriEnv.getDataLinkFinder();
    }

    public IDataModelLinkFinder getDataModelLinkFinder() {
        return this.oriEnv.getDataModelLinkFinder();
    }

    public IPeriodAdapter getPeriodAdapter(ExecutorContext context) {
        return this.oriEnv.getPeriodAdapter(context);
    }

    public VariableManager getVariableManager() {
        return this.oriEnv.getVariableManager();
    }

    public String getUnitDimesion(ExecutorContext context) {
        return this.oriEnv.getUnitDimesion(context);
    }

    public int getPeriodType() {
        return this.oriEnv.getPeriodType();
    }

    public IContext getExternalContext() {
        return this.oriEnv.getExternalContext();
    }

    public List<IReportDynamicNodeProvider> getDataNodeFinders() {
        return this.oriEnv.getDataNodeFinders();
    }

    public EntityViewDefine getEntityViewDefine(ExecutorContext context, String dimensionName) {
        return this.oriEnv.getEntityViewDefine(context, dimensionName);
    }

    public EntityViewDefine getDefaultEntityViewDefine(String tableKey) {
        return this.oriEnv.getDefaultEntityViewDefine(tableKey);
    }

    public IFieldValueProcessor getFieldValueProcessor() {
        return this.oriEnv.getFieldValueProcessor();
    }

    public IFieldValueUpdateProcessor getFieldValueUpdateProcessor() {
        return this.oriEnv.getFieldValueUpdateProcessor();
    }

    public String getDimensionTableName(String dimensionName) {
        return this.oriEnv.getDimensionTableName(dimensionName);
    }

    public boolean JudgeZBAuth() {
        return this.oriEnv.JudgeZBAuth();
    }

    public ZBAuthJudger getZBAuthJudger(List<String> fieldKeys) {
        return this.oriEnv.getZBAuthJudger(fieldKeys);
    }

    public String findEntityTableCode(String code) {
        return this.oriEnv.findEntityTableCode(code);
    }

    public Map<String, List<String>> getRelationDimValues(ExecutorContext context, String mainDimValue, String period) {
        return this.oriEnv.getRelationDimValues(context, mainDimValue, period);
    }

    public ITableNameFinder getTableNameFinder() {
        return this.tableNameFinder;
    }
}

