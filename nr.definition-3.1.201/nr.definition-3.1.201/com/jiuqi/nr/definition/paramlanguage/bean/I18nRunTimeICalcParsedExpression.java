/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.ScriptInfo
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryFields
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.exception.UnknownReadWriteException
 *  com.jiuqi.np.dataengine.node.CalcExpression
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.FormulaShowInfo
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.definition.paramlanguage.bean;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.ScriptInfo;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.UnknownReadWriteException;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.util.StringUtils;

public class I18nRunTimeICalcParsedExpression
extends CalcExpression {
    private final IParsedExpression parsedExpression;
    private String meaning;
    private Formula i18nFormula;

    public I18nRunTimeICalcParsedExpression(CalcExpression parsedExpression) {
        super((IExpression)parsedExpression, parsedExpression.getSource(), parsedExpression.getAssignNode(), parsedExpression.getIndex());
        this.parsedExpression = parsedExpression;
    }

    public DataEngineConsts.FormulaType getFormulaType() {
        return this.parsedExpression.getFormulaType();
    }

    public String getKey() {
        return this.parsedExpression.getKey();
    }

    public void setSource(Formula formula) {
        this.i18nFormula = formula;
    }

    public Formula getSource() {
        return this.i18nFormula;
    }

    public String getFormKey() {
        return this.parsedExpression.getFormKey();
    }

    public String getFormula(QueryContext queryContext, FormulaShowInfo formulaShowInfo) throws InterpretException {
        return this.parsedExpression.getFormula(queryContext, formulaShowInfo);
    }

    public boolean supportJs() {
        return this.parsedExpression.supportJs();
    }

    public QueryFields getQueryFields() {
        return this.parsedExpression.getQueryFields();
    }

    public String generateJs(QueryContext queryContext, ScriptInfo scriptInfo) throws InterpretException {
        return this.parsedExpression.generateJs(queryContext, scriptInfo);
    }

    public DynamicDataNode getAssignNode() {
        return this.parsedExpression.getAssignNode();
    }

    public QueryField getBalanceField() {
        return this.parsedExpression.getBalanceField();
    }

    public String getBalanceFormula() {
        return this.parsedExpression.getBalanceFormula();
    }

    public IExpression getRealExpression() {
        return this.parsedExpression.getRealExpression();
    }

    public String getMeanning(QueryContext queryContext) throws InterpretException {
        return StringUtils.isEmpty((String)this.meaning) ? this.parsedExpression.getMeanning(queryContext) : this.meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public QueryFields getWriteQueryFields() throws UnknownReadWriteException {
        return this.parsedExpression.getWriteQueryFields();
    }

    public QueryFields getParsedWriteFields() {
        return ((CalcExpression)this.parsedExpression).getParsedWriteFields();
    }
}

