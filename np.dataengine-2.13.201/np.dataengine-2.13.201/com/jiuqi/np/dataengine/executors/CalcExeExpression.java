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
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.UnknownReadWriteException;
import com.jiuqi.np.dataengine.executors.EvalItem;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.parse.ParseContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CalcExeExpression
extends CalcExpression {
    private static final long serialVersionUID = -7845227686351610207L;
    private CalcExpression calcExpression;
    private List<EvalItem> conditionEvals;

    public CalcExeExpression(CalcExpression calcExpression) {
        super(null, null, null, -1);
        this.calcExpression = calcExpression;
    }

    public CalcExeExpression(CalcExpression expression, List<EvalItem> conditionEvals) {
        this(expression);
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
        if (ExpressionUtils.judgeByConditionEvals(this.conditionEvals)) {
            return this.calcExpression.execute(context);
        }
        return 0;
    }

    @Override
    public QueryFields getWriteQueryFields() throws UnknownReadWriteException {
        return this.calcExpression.getWriteQueryFields();
    }

    @Override
    public QueryTable getWriteTable() throws UnknownReadWriteException {
        return this.calcExpression.getWriteTable();
    }

    @Override
    public DynamicDataNode getAssignNode() {
        return this.calcExpression.getAssignNode();
    }

    @Override
    public void setAssignNode(DynamicDataNode assignNode) {
        this.calcExpression.setAssignNode(assignNode);
    }

    @Override
    public DataEngineConsts.FormulaType getFormulaType() {
        return this.calcExpression.getFormulaType();
    }

    @Override
    public int compareTo(CalcExpression o) {
        return this.calcExpression.compareTo(o);
    }

    @Override
    public int getIndex() {
        return this.calcExpression.getIndex();
    }

    @Override
    public void setIndex(int index) {
        this.calcExpression.setIndex(index);
    }

    @Override
    public boolean isNeedExpand() {
        return this.calcExpression.isNeedExpand();
    }

    @Override
    public void setNeedExpand(boolean needExpand) {
        this.calcExpression.setNeedExpand(needExpand);
    }

    @Override
    public String getExtendAssignKey() {
        return this.calcExpression.getExtendAssignKey();
    }

    @Override
    public void setExtendAssignKey(String extendAssignKey) {
        this.calcExpression.setExtendAssignKey(extendAssignKey);
    }

    @Override
    public Object clone() {
        return new CalcExeExpression((CalcExpression)this.calcExpression.clone(), this.conditionEvals);
    }

    @Override
    public int getType(IContext context) throws SyntaxException {
        return this.calcExpression.getType(context);
    }

    @Override
    public ASTNodeType getNodeType() {
        return this.calcExpression.getNodeType();
    }

    @Override
    public Object evaluate(IContext context) throws SyntaxException {
        return this.calcExpression.evaluate(context);
    }

    @Override
    public boolean judge(IContext context) throws SyntaxException {
        return this.calcExpression.judge(context);
    }

    @Override
    public int childrenSize() {
        return this.calcExpression.childrenSize();
    }

    @Override
    public IASTNode getChild(int index) {
        return this.calcExpression.getChild(index);
    }

    @Override
    public void setChild(int index, IASTNode node) {
        this.calcExpression.setChild(index, node);
    }

    @Override
    public void addChild(IASTNode node) {
        this.calcExpression.addChild(node);
    }

    @Override
    public Token getToken() {
        return this.calcExpression.getToken();
    }

    @Override
    public boolean isStatic(IContext context) {
        return this.calcExpression.isStatic(context);
    }

    @Override
    public IASTNode optimize(IContext context, int level) throws SyntaxException {
        return this.calcExpression.optimize(context, level);
    }

    @Override
    public boolean support(Language lang) {
        return this.calcExpression.support(lang);
    }

    @Override
    public void interpret(IContext context, StringBuilder buffer, Language lang, Object info) throws InterpretException {
        this.calcExpression.interpret(context, buffer, lang, info);
    }

    @Override
    public String interpret(IContext context, Language lang, Object info) throws InterpretException {
        return this.calcExpression.interpret(context, lang, info);
    }

    @Override
    public int validate(IContext context) throws SyntaxException {
        return this.calcExpression.validate(context);
    }

    @Override
    public IASTNode offset(int deltaCol, int deltaRow) throws SyntaxException {
        return this.calcExpression.offset(deltaCol, deltaRow);
    }

    @Override
    public int adjust(IASTAdjustor adjustor) throws AdjustException {
        return this.calcExpression.adjust(adjustor);
    }

    @Override
    public void toString(StringBuilder buffer) {
        this.calcExpression.toString(buffer);
    }

    @Override
    public Iterator<IASTNode> iterator() {
        return this.calcExpression.iterator();
    }

    @Override
    public IDataFormator getDataFormator(IContext context) throws SyntaxException {
        return this.calcExpression.getDataFormator(context);
    }

    @Override
    public List<IASTNode> getSources(boolean assignMode) {
        return this.calcExpression.getSources(assignMode);
    }

    @Override
    public List<IASTNode> getDests(boolean assignMode) {
        return this.calcExpression.getDests(assignMode);
    }

    @Override
    public String evalAsString(IContext context) throws SyntaxException {
        return this.calcExpression.evalAsString(context);
    }

    @Override
    public List<WildcardRange> getWildcardRanges() {
        return this.calcExpression.getWildcardRanges();
    }

    @Override
    public List<IExpression> expandWildcards(IContext context) throws SyntaxException {
        return this.calcExpression.expandWildcards(context);
    }

    @Override
    public Formula getSource() {
        return this.calcExpression.getSource();
    }

    @Override
    public int getWildcardRow() {
        return this.calcExpression.getWildcardRow();
    }

    @Override
    public int getWildcardCol() {
        return this.calcExpression.getWildcardCol();
    }

    @Override
    public void setWildcardPos(int row, int col) {
        this.calcExpression.setWildcardPos(row, col);
    }

    @Override
    public String getKey() {
        return this.calcExpression.getKey();
    }

    @Override
    public String getMeanning(QueryContext queryContext) throws InterpretException {
        return this.calcExpression.getMeanning(queryContext);
    }

    @Override
    public String getFormula(QueryContext queryContext, FormulaShowInfo formulaShowInfo) throws InterpretException {
        return this.calcExpression.getFormula(queryContext, formulaShowInfo);
    }

    @Override
    public String generateJs(QueryContext queryContext, ScriptInfo scriptInfo) throws InterpretException {
        return this.calcExpression.generateJs(queryContext, scriptInfo);
    }

    @Override
    public String getFormKey() {
        return this.calcExpression.getFormKey();
    }

    @Override
    public boolean supportJs() {
        return this.calcExpression.supportJs();
    }

    @Override
    public QueryFields getQueryFields() {
        return this.calcExpression.getQueryFields();
    }

    @Override
    public IExpression getRealExpression() {
        return this.calcExpression.getRealExpression();
    }

    @Override
    public void initCompliedInfo(ParseContext queryContext, String compliedFormulaExp, String explain) throws InterpretException, ParseException {
        this.calcExpression.initCompliedInfo(queryContext, compliedFormulaExp, explain);
    }

    @Override
    @Deprecated
    public void initCompliedInfo(ParseContext queryContext, int index) throws ParseException, InterpretException {
        this.calcExpression.initCompliedInfo(queryContext, index);
    }

    @Override
    public void initCompliedInfo(ParseContext queryContext) throws ParseException, InterpretException {
        this.calcExpression.initCompliedInfo(queryContext);
    }

    @Override
    public String getCompliedFormulaExp() {
        return this.calcExpression.getCompliedFormulaExp();
    }

    @Override
    public String getExplain() {
        return this.calcExpression.getExplain();
    }

    @Override
    public String toString() {
        return this.calcExpression.toString();
    }

    @Override
    public int getScale(IContext context) {
        return this.calcExpression.getScale(context);
    }

    @Override
    public void addExtendReadKey(String key) {
        this.calcExpression.addExtendReadKey(key);
    }

    @Override
    public List<String> getExtendReadKeys() {
        return this.calcExpression.getExtendReadKeys();
    }

    @Override
    public QueryField getBalanceField() {
        return this.calcExpression.getBalanceField();
    }

    @Override
    public void setBalanceField(QueryField balanceField) {
        this.calcExpression.setBalanceField(balanceField);
    }

    @Override
    public String getBalanceFormula() {
        return this.calcExpression.getBalanceFormula();
    }

    @Override
    public void setBalanceFormula(String balanceFormula) {
        this.calcExpression.setBalanceFormula(balanceFormula);
    }

    @Override
    public IASTIterator astIterator() {
        return this.calcExpression.astIterator();
    }

    @Override
    public List<IParsedExpression> getConditions() {
        return this.calcExpression.getConditions();
    }

    @Override
    public boolean hasConditions() {
        return this.calcExpression.hasConditions();
    }

    @Override
    public void print(QueryContext qContext, StringBuilder msg) {
        this.calcExpression.print(qContext, msg);
    }

    public CalcExpression getCalcExpression() {
        return this.calcExpression;
    }

    @Override
    public QueryFields getParsedWriteFields() {
        return this.calcExpression.getParsedWriteFields();
    }

    @Override
    public boolean isValidate() {
        return this.calcExpression.isValidate();
    }

    @Override
    public void setValidate(boolean validate) {
        this.calcExpression.setValidate(validate);
    }

    @Override
    public QueryFields getReadQueryFields() throws UnknownReadWriteException {
        return this.calcExpression.getReadQueryFields();
    }
}

