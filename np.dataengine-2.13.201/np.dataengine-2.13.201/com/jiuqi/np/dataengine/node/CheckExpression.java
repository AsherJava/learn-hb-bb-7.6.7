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
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.interpret.ScriptInfo
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.np.dataengine.node;

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
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.interpret.ScriptInfo;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.UnknownReadWriteException;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.parse.ParseContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.util.ExpressionKeyUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckExpression
implements IExpression,
IParsedExpression {
    private static final Logger logger = LoggerFactory.getLogger(CheckExpression.class);
    private static final long serialVersionUID = 3405377370853320445L;
    private IExpression expression;
    private Formula source;
    private List<String> extendReadKeys;
    private QueryField balanceField;
    private String balanceFormula;
    private String compliedFormulaExp;
    private String explain;
    private List<IParsedExpression> conditions;
    protected QueryFields queryFields;
    private boolean validate = true;
    protected IFunction unknownReadWriteFunc = null;
    private String key;

    public CheckExpression(IExpression expression, Formula source) {
        this.expression = expression;
        this.source = source;
    }

    public int getType(IContext context) throws SyntaxException {
        return this.expression.getType(context);
    }

    public ASTNodeType getNodeType() {
        return this.expression.getNodeType();
    }

    public Object evaluate(IContext context) throws SyntaxException {
        return this.expression.evaluate(context);
    }

    public boolean judge(IContext context) throws SyntaxException {
        return this.expression.judge(context);
    }

    public int execute(IContext context) throws SyntaxException {
        return this.expression.execute(context);
    }

    public int childrenSize() {
        return this.expression.childrenSize();
    }

    public IASTNode getChild(int index) {
        return this.expression.getChild(index);
    }

    public void setChild(int index, IASTNode node) {
        this.expression.setChild(index, node);
    }

    public void addChild(IASTNode node) {
        this.expression.addChild(node);
    }

    public Token getToken() {
        return this.expression.getToken();
    }

    public boolean isStatic(IContext context) {
        return this.expression.isStatic(context);
    }

    public IASTNode optimize(IContext context, int level) throws SyntaxException {
        return this.optimize(context, level);
    }

    public boolean support(Language lang) {
        return this.expression.support(lang);
    }

    public void interpret(IContext context, StringBuilder buffer, Language lang, Object info) throws InterpretException {
        this.expression.interpret(context, buffer, lang, info);
    }

    public String interpret(IContext context, Language lang, Object info) throws InterpretException {
        return this.expression.interpret(context, lang, info);
    }

    public int validate(IContext context) throws SyntaxException {
        return this.expression.validate(context);
    }

    public IASTNode offset(int deltaCol, int deltaRow) throws SyntaxException {
        return this.expression.offset(deltaCol, deltaRow);
    }

    public int adjust(IASTAdjustor adjustor) throws AdjustException {
        return this.expression.adjust(adjustor);
    }

    public void toString(StringBuilder buffer) {
        this.expression.toString(buffer);
    }

    public Iterator<IASTNode> iterator() {
        return this.expression.iterator();
    }

    public IDataFormator getDataFormator(IContext context) throws SyntaxException {
        return this.expression.getDataFormator(context);
    }

    public List<IASTNode> getSources(boolean assignMode) {
        return this.expression.getSources(assignMode);
    }

    public List<IASTNode> getDests(boolean assignMode) {
        return this.expression.getDests(assignMode);
    }

    public String evalAsString(IContext context) throws SyntaxException {
        return this.expression.evalAsString(context);
    }

    public List<WildcardRange> getWildcardRanges() {
        return this.expression.getWildcardRanges();
    }

    public List<IExpression> expandWildcards(IContext context) throws SyntaxException {
        return this.expression.expandWildcards(context);
    }

    public Object clone() {
        CheckExpression neweExpression = new CheckExpression((IExpression)this.expression.clone(), this.source);
        return neweExpression;
    }

    @Override
    public Formula getSource() {
        return this.source;
    }

    public int getWildcardRow() {
        return this.expression.getWildcardRow();
    }

    public int getWildcardCol() {
        return this.expression.getWildcardCol();
    }

    public void setWildcardPos(int row, int col) {
        this.expression.setWildcardPos(row, col);
    }

    @Override
    public DataEngineConsts.FormulaType getFormulaType() {
        return DataEngineConsts.FormulaType.CHECK;
    }

    @Override
    public String getKey() {
        if (this.key == null) {
            this.key = ExpressionKeyUtil.generateKey(this);
        }
        return this.key;
    }

    @Override
    public String getMeanning(QueryContext queryContext) throws InterpretException {
        return this.expression.interpret((IContext)queryContext, Language.EXPLAIN, null);
    }

    @Override
    public String getFormula(QueryContext queryContext, FormulaShowInfo formulaShowInfo) throws InterpretException {
        return this.expression.interpret((IContext)queryContext, Language.FORMULA, (Object)formulaShowInfo);
    }

    @Override
    public String generateJs(QueryContext queryContext, ScriptInfo scriptInfo) throws InterpretException {
        return this.expression.interpret((IContext)queryContext, Language.JavaScript, (Object)scriptInfo);
    }

    @Override
    public DynamicDataNode getAssignNode() {
        return null;
    }

    @Override
    public String getFormKey() {
        return this.getSource().getFormKey();
    }

    @Override
    public boolean supportJs() {
        return this.expression.support(Language.JavaScript);
    }

    @Override
    public QueryFields getQueryFields() {
        if (this.queryFields == null) {
            this.initQueryFields(null);
        }
        return this.queryFields;
    }

    @Override
    public IExpression getRealExpression() {
        return this.expression;
    }

    public void initCompliedInfo(ParseContext queryContext, String compliedFormulaExp, String explain) throws ParseException, InterpretException {
        FormulaShowInfo formulaShowInfo = queryContext.getExeContext().getFormulaShowInfo();
        this.setCompliedFormulaExp(StringUtils.isNotEmpty((String)compliedFormulaExp) ? compliedFormulaExp : this.getFormula(queryContext, formulaShowInfo));
        this.setExplain(StringUtils.isNotEmpty((String)explain) ? explain : this.getRealExpression().interpret((IContext)queryContext, Language.EXPLAIN, (Object)formulaShowInfo));
        this.initQueryFields(queryContext);
    }

    @Deprecated
    public void initCompliedInfo(ParseContext queryContext, int index) throws ParseException, InterpretException {
        this.initCompliedInfo(queryContext);
    }

    public void initCompliedInfo(ParseContext queryContext) throws ParseException, InterpretException {
        this.initCompliedInfo(queryContext, null, null);
    }

    public String getCompliedFormulaExp() {
        return this.compliedFormulaExp;
    }

    public String getExplain() {
        return this.explain;
    }

    public String toString() {
        try {
            if (this.compliedFormulaExp != null) {
                return this.compliedFormulaExp;
            }
            return this.getFormula(null, new FormulaShowInfo(DataEngineConsts.FormulaShowType.JQ));
        }
        catch (InterpretException e) {
            logger.error(e.getMessage(), e);
            return super.toString();
        }
    }

    public int getScale(IContext context) {
        return Integer.MIN_VALUE;
    }

    public void addExtendReadKey(String key) {
        if (this.extendReadKeys == null) {
            this.extendReadKeys = new ArrayList<String>();
        }
        this.extendReadKeys.add(key);
    }

    public List<String> getExtendReadKeys() {
        return this.extendReadKeys;
    }

    @Override
    public QueryField getBalanceField() {
        return this.balanceField;
    }

    public void setBalanceField(QueryField balanceField) {
        this.balanceField = balanceField;
    }

    @Override
    public String getBalanceFormula() {
        return this.balanceFormula;
    }

    public void setBalanceFormula(String balanceFormula) {
        this.balanceFormula = balanceFormula;
    }

    public IASTIterator astIterator() {
        return null;
    }

    @Override
    public List<IParsedExpression> getConditions() {
        if (this.conditions == null) {
            this.conditions = new ArrayList<IParsedExpression>();
        }
        return this.conditions;
    }

    public boolean hasConditions() {
        return this.conditions != null && this.conditions.size() > 0;
    }

    public void print(QueryContext qContext, StringBuilder msg) {
        msg.append(this.source.getReportName()).append("[").append(this.source.getCode()).append("]:");
        try {
            msg.append(this.getFormula(qContext, new FormulaShowInfo(DataEngineConsts.FormulaShowType.JQ)));
            msg.append("----").append(this.getFormula(qContext, new FormulaShowInfo(DataEngineConsts.FormulaShowType.DATA)));
            msg.append("\n");
        }
        catch (InterpretException interpretException) {
            // empty catch block
        }
    }

    @Override
    public QueryFields getWriteQueryFields() throws UnknownReadWriteException {
        return null;
    }

    protected void initQueryFields(QueryContext qContext) {
        try {
            this.queryFields = ExpressionUtils.getAllQueryFields(qContext, (IASTNode)this.expression);
            this.addConditionQueryFields(qContext, this.queryFields);
        }
        catch (UnknownReadWriteException e) {
            this.unknownReadWriteFunc = e.getFunc();
        }
    }

    protected void addConditionQueryFields(QueryContext qContext, QueryFields queryFields) throws UnknownReadWriteException {
        if (queryFields != null && this.hasConditions()) {
            for (IParsedExpression condition : this.conditions) {
                ExpressionUtils.recursiveGetAllQueryFields(qContext, (IASTNode)condition.getRealExpression(), queryFields);
            }
        }
    }

    @Override
    public boolean isValidate() {
        return this.validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    @Override
    public QueryFields getReadQueryFields() throws UnknownReadWriteException {
        if (this.unknownReadWriteFunc != null) {
            throw new UnknownReadWriteException("\u51fd\u6570" + this.unknownReadWriteFunc.name() + "()\u7684\u8bfb\u5199\u8303\u56f4\u672a\u77e5");
        }
        return this.getQueryFields();
    }

    public void setCompliedFormulaExp(String compliedFormulaExp) {
        this.compliedFormulaExp = compliedFormulaExp;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}

