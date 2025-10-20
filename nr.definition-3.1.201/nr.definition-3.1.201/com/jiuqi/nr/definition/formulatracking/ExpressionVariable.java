/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.var.Variable
 */
package com.jiuqi.nr.definition.formulatracking;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;

public class ExpressionVariable
extends Variable {
    private final String expression;

    public ExpressionVariable(String varName, String varTitle, int dataType, String expression) {
        super(varName, varTitle, dataType);
        this.expression = expression;
    }

    public String getExpression() {
        return this.expression;
    }

    public Object getVarValue(IContext context) throws Exception {
        QueryContext qContext = (QueryContext)context;
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setFormSchemeKey(((ReportFmlExecEnvironment)qContext.getExeContext().getEnv()).getFormSchemeKey());
        IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringBeanUtils.getBean(IDataAccessProvider.class);
        IDataQuery dataQuery = dataAccessProvider.newDataQuery(queryEnvironment);
        dataQuery.setStatic(true);
        dataQuery.addExpressionColumn(this.expression);
        dataQuery.setMasterKeys(qContext.getCurrentMasterKey());
        IReadonlyTable executeReader = dataQuery.executeReader(qContext.getExeContext());
        IDataRow dataRow = executeReader.getItem(0);
        AbstractData dataRowValue = dataRow.getValue(0);
        return dataRowValue.getAsObject();
    }
}

