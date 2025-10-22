/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.ScriptInfo
 */
package com.jiuqi.np.dataengine.node;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.ScriptInfo;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.UnknownReadWriteException;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.io.Serializable;
import java.util.List;

public interface IParsedExpression
extends Serializable {
    public DataEngineConsts.FormulaType getFormulaType();

    public String getKey();

    public String getFormKey();

    public Formula getSource();

    public String getMeanning(QueryContext var1) throws InterpretException;

    public String getFormula(QueryContext var1, FormulaShowInfo var2) throws InterpretException;

    public boolean supportJs();

    public QueryFields getQueryFields();

    public String generateJs(QueryContext var1, ScriptInfo var2) throws InterpretException;

    public DynamicDataNode getAssignNode();

    public QueryField getBalanceField();

    public String getBalanceFormula();

    public IExpression getRealExpression();

    public List<IParsedExpression> getConditions();

    public QueryFields getReadQueryFields() throws UnknownReadWriteException;

    public QueryFields getWriteQueryFields() throws UnknownReadWriteException;

    public boolean isValidate();
}

