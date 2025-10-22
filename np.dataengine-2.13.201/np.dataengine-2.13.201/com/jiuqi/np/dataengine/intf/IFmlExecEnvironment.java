/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.IPeriodAdapter
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataLinkFinder;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.intf.IDimensionRelationProvider;
import com.jiuqi.np.dataengine.intf.IFieldValueProcessor;
import com.jiuqi.np.dataengine.intf.IFieldValueUpdateProcessor;
import com.jiuqi.np.dataengine.intf.ITableNameFinder;
import com.jiuqi.np.dataengine.intf.IUnitLeafFinder;
import com.jiuqi.np.dataengine.intf.ZBAuthJudger;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.IPeriodAdapter;
import java.util.List;
import java.util.Map;

public interface IFmlExecEnvironment
extends IDimensionRelationProvider {
    @Deprecated
    public IDataLinkFinder getDataLinkFinder();

    public IDataModelLinkFinder getDataModelLinkFinder();

    public IPeriodAdapter getPeriodAdapter(ExecutorContext var1);

    default public IPeriodAdapter getPeriodAdapter(ExecutorContext context, String linkAlias) {
        return this.getPeriodAdapter(context);
    }

    public VariableManager getVariableManager();

    public String getUnitDimesion(ExecutorContext var1);

    public int getPeriodType();

    public IContext getExternalContext();

    public List<IReportDynamicNodeProvider> getDataNodeFinders();

    public EntityViewDefine getEntityViewDefine(ExecutorContext var1, String var2);

    public EntityViewDefine getDefaultEntityViewDefine(String var1);

    default public IFieldValueProcessor getFieldValueProcessor() {
        return null;
    }

    default public IFieldValueUpdateProcessor getFieldValueUpdateProcessor() {
        return null;
    }

    @Deprecated
    public String getDimensionTableName(String var1);

    public boolean JudgeZBAuth();

    public ZBAuthJudger getZBAuthJudger(List<String> var1);

    public String findEntityTableCode(String var1);

    @Deprecated
    default public Map<String, List<String>> getRelationDimValues(ExecutorContext context, String mainDimValue, String period) {
        return null;
    }

    default public ITableNameFinder getTableNameFinder() {
        return null;
    }

    default public IUnitLeafFinder getUnitLeafFinder() {
        return null;
    }
}

