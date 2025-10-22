/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.AdjustException
 *  com.jiuqi.bi.syntax.ast.IASTAdjustor
 *  com.jiuqi.bi.syntax.ast.IASTIterator
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.cell.WildcardRange
 *  com.jiuqi.bi.syntax.format.IDataFormator
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.interpret.ScriptInfo
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.AdjustException;
import com.jiuqi.bi.syntax.ast.IASTAdjustor;
import com.jiuqi.bi.syntax.ast.IASTIterator;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.cell.WildcardRange;
import com.jiuqi.bi.syntax.format.IDataFormator;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.interpret.ScriptInfo;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.UnknownReadWriteException;
import com.jiuqi.np.dataengine.executors.EvalItem;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.parse.ParseContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CheckExeExpression
extends CheckExpression {
    private static final long serialVersionUID = -564911994465486768L;
    private CheckExpression checkExpression;
    private List<EvalItem> conditionEvals;

    public CheckExeExpression(CheckExpression checkExpression) {
        super(null, null);
        this.checkExpression = checkExpression;
    }

    public CheckExeExpression(CheckExpression checkExpression, List<EvalItem> conditionEvals) {
        this(checkExpression);
        this.conditionEvals = conditionEvals;
    }

    public List<EvalItem> getConditionEvals() {
        if (this.conditionEvals == null) {
            this.conditionEvals = new ArrayList<EvalItem>();
        }
        return this.conditionEvals;
    }

    @Override
    public int execute(IContext context) throws SyntaxException {
        return this.checkExpression.execute(context);
    }

    @Override
    public QueryFields getWriteQueryFields() throws UnknownReadWriteException {
        return this.checkExpression.getWriteQueryFields();
    }

    @Override
    public DynamicDataNode getAssignNode() {
        return this.checkExpression.getAssignNode();
    }

    @Override
    public DataEngineConsts.FormulaType getFormulaType() {
        return this.checkExpression.getFormulaType();
    }

    @Override
    public Object clone() {
        return new CheckExeExpression((CheckExpression)this.checkExpression.clone(), this.conditionEvals);
    }

    @Override
    public int getType(IContext context) throws SyntaxException {
        return this.checkExpression.getType(context);
    }

    @Override
    public ASTNodeType getNodeType() {
        return this.checkExpression.getNodeType();
    }

    @Override
    public Object evaluate(IContext context) throws SyntaxException {
        return this.checkExpression.evaluate(context);
    }

    @Override
    public boolean judge(IContext context) throws SyntaxException {
        if (ExpressionUtils.judgeByConditionEvals(this.conditionEvals)) {
            return this.checkExpression.judge(context);
        }
        return true;
    }

    @Override
    public int childrenSize() {
        return this.checkExpression.childrenSize();
    }

    @Override
    public IASTNode getChild(int index) {
        return this.checkExpression.getChild(index);
    }

    @Override
    public void setChild(int index, IASTNode node) {
        this.checkExpression.setChild(index, node);
    }

    @Override
    public void addChild(IASTNode node) {
        this.checkExpression.addChild(node);
    }

    @Override
    public Token getToken() {
        return this.checkExpression.getToken();
    }

    @Override
    public boolean isStatic(IContext context) {
        return this.checkExpression.isStatic(context);
    }

    @Override
    public IASTNode optimize(IContext context, int level) throws SyntaxException {
        return this.checkExpression.optimize(context, level);
    }

    @Override
    public boolean support(Language lang) {
        return this.checkExpression.support(lang);
    }

    @Override
    public void interpret(IContext context, StringBuilder buffer, Language lang, Object info) throws InterpretException {
        this.checkExpression.interpret(context, buffer, lang, info);
    }

    @Override
    public String interpret(IContext context, Language lang, Object info) throws InterpretException {
        return this.checkExpression.interpret(context, lang, info);
    }

    @Override
    public int validate(IContext context) throws SyntaxException {
        return this.checkExpression.validate(context);
    }

    @Override
    public IASTNode offset(int deltaCol, int deltaRow) throws SyntaxException {
        return this.checkExpression.offset(deltaCol, deltaRow);
    }

    @Override
    public int adjust(IASTAdjustor adjustor) throws AdjustException {
        return this.checkExpression.adjust(adjustor);
    }

    @Override
    public void toString(StringBuilder buffer) {
        this.checkExpression.toString(buffer);
    }

    @Override
    public Iterator<IASTNode> iterator() {
        return this.checkExpression.iterator();
    }

    @Override
    public IDataFormator getDataFormator(IContext context) throws SyntaxException {
        return this.checkExpression.getDataFormator(context);
    }

    @Override
    public List<IASTNode> getSources(boolean assignMode) {
        return this.checkExpression.getSources(assignMode);
    }

    @Override
    public List<IASTNode> getDests(boolean assignMode) {
        return this.checkExpression.getDests(assignMode);
    }

    @Override
    public String evalAsString(IContext context) throws SyntaxException {
        return this.checkExpression.evalAsString(context);
    }

    @Override
    public List<WildcardRange> getWildcardRanges() {
        return this.checkExpression.getWildcardRanges();
    }

    @Override
    public List<IExpression> expandWildcards(IContext context) throws SyntaxException {
        return this.checkExpression.expandWildcards(context);
    }

    @Override
    public Formula getSource() {
        return this.checkExpression.getSource();
    }

    @Override
    public int getWildcardRow() {
        return this.checkExpression.getWildcardRow();
    }

    @Override
    public int getWildcardCol() {
        return this.checkExpression.getWildcardCol();
    }

    @Override
    public void setWildcardPos(int row, int col) {
        this.checkExpression.setWildcardPos(row, col);
    }

    @Override
    public String getKey() {
        return this.checkExpression.getKey();
    }

    @Override
    public String getMeanning(QueryContext queryContext) throws InterpretException {
        return this.checkExpression.getMeanning(queryContext);
    }

    @Override
    public String getFormula(QueryContext queryContext, FormulaShowInfo formulaShowInfo) throws InterpretException {
        return this.checkExpression.getFormula(queryContext, formulaShowInfo);
    }

    @Override
    public String generateJs(QueryContext queryContext, ScriptInfo scriptInfo) throws InterpretException {
        return this.checkExpression.generateJs(queryContext, scriptInfo);
    }

    @Override
    public String getFormKey() {
        return this.checkExpression.getFormKey();
    }

    @Override
    public boolean supportJs() {
        return this.checkExpression.supportJs();
    }

    @Override
    public QueryFields getQueryFields() {
        return this.checkExpression.getQueryFields();
    }

    @Override
    public IExpression getRealExpression() {
        return this.checkExpression.getRealExpression();
    }

    @Override
    public void initCompliedInfo(ParseContext queryContext, String compliedFormulaExp, String explain) throws InterpretException, ParseException {
        this.checkExpression.initCompliedInfo(queryContext, compliedFormulaExp, explain);
    }

    @Override
    @Deprecated
    public void initCompliedInfo(ParseContext queryContext, int index) throws ParseException, InterpretException {
        this.checkExpression.initCompliedInfo(queryContext, index);
    }

    @Override
    public void initCompliedInfo(ParseContext queryContext) throws ParseException, InterpretException {
        this.checkExpression.initCompliedInfo(queryContext);
    }

    @Override
    public String getCompliedFormulaExp() {
        return this.checkExpression.getCompliedFormulaExp();
    }

    @Override
    public String getExplain() {
        return this.checkExpression.getExplain();
    }

    @Override
    public String toString() {
        return this.checkExpression.toString();
    }

    @Override
    public int getScale(IContext context) {
        return this.checkExpression.getScale(context);
    }

    @Override
    public void addExtendReadKey(String key) {
        this.checkExpression.addExtendReadKey(key);
    }

    @Override
    public List<String> getExtendReadKeys() {
        return this.checkExpression.getExtendReadKeys();
    }

    @Override
    public QueryField getBalanceField() {
        return this.checkExpression.getBalanceField();
    }

    @Override
    public void setBalanceField(QueryField balanceField) {
        this.checkExpression.setBalanceField(balanceField);
    }

    @Override
    public String getBalanceFormula() {
        return this.checkExpression.getBalanceFormula();
    }

    @Override
    public void setBalanceFormula(String balanceFormula) {
        this.checkExpression.setBalanceFormula(balanceFormula);
    }

    @Override
    public IASTIterator astIterator() {
        return this.checkExpression.astIterator();
    }

    @Override
    public List<IParsedExpression> getConditions() {
        return this.checkExpression.getConditions();
    }

    @Override
    public boolean hasConditions() {
        return this.checkExpression.hasConditions();
    }

    @Override
    public void print(QueryContext qContext, StringBuilder msg) {
        this.checkExpression.print(qContext, msg);
    }

    public CheckExpression getCheckExpression() {
        return this.checkExpression;
    }

    @Override
    public boolean isValidate() {
        return this.checkExpression.isValidate();
    }

    @Override
    public void setValidate(boolean validate) {
        this.checkExpression.setValidate(validate);
    }

    @Override
    public QueryFields getReadQueryFields() throws UnknownReadWriteException {
        return this.checkExpression.getReadQueryFields();
    }
}

