/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.cell.CellNode
 *  com.jiuqi.bi.syntax.cell.CompatiblePosition
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.cell.WildcardRange
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.data.DimensionNode
 *  com.jiuqi.bi.syntax.data.PercentNode
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.keyword.KeywordNode
 *  com.jiuqi.bi.syntax.operator.And
 *  com.jiuqi.bi.syntax.operator.Divide
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.operator.IOperator
 *  com.jiuqi.bi.syntax.operator.IfThenElse
 *  com.jiuqi.bi.syntax.operator.In
 *  com.jiuqi.bi.syntax.operator.LessThan
 *  com.jiuqi.bi.syntax.operator.LessThanOrEqual
 *  com.jiuqi.bi.syntax.operator.Like
 *  com.jiuqi.bi.syntax.operator.LinkPlus
 *  com.jiuqi.bi.syntax.operator.Minus
 *  com.jiuqi.bi.syntax.operator.MoreThan
 *  com.jiuqi.bi.syntax.operator.MoreThanOrEqual
 *  com.jiuqi.bi.syntax.operator.Multiply
 *  com.jiuqi.bi.syntax.operator.Negative
 *  com.jiuqi.bi.syntax.operator.Not
 *  com.jiuqi.bi.syntax.operator.NotEqual
 *  com.jiuqi.bi.syntax.operator.Or
 *  com.jiuqi.bi.syntax.operator.Plus
 *  com.jiuqi.bi.syntax.operator.Positive
 *  com.jiuqi.bi.syntax.operator.Power
 *  com.jiuqi.bi.syntax.operator.SymbolOperator
 *  com.jiuqi.bi.syntax.parser.ErrorInfo
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.IParseListener
 *  com.jiuqi.bi.syntax.parser.NodeToken
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.script.IScriptContext
 *  com.jiuqi.bi.syntax.script.OrderField
 *  com.jiuqi.bi.syntax.script.ScriptBlock
 *  com.jiuqi.bi.syntax.script.ScriptEvent
 *  com.jiuqi.bi.syntax.script.ScriptNode
 *  com.jiuqi.bi.syntax.script.SelectField
 *  com.jiuqi.bi.syntax.script.SetNode
 *  com.jiuqi.bi.util.StringUtils
 *  org.antlr.runtime.CommonToken
 *  org.antlr.runtime.EarlyExitException
 *  org.antlr.runtime.FailedPredicateException
 *  org.antlr.runtime.MismatchedNotSetException
 *  org.antlr.runtime.MismatchedSetException
 *  org.antlr.runtime.MismatchedTokenException
 *  org.antlr.runtime.MismatchedTreeNodeException
 *  org.antlr.runtime.NoViableAltException
 *  org.antlr.runtime.Parser
 *  org.antlr.runtime.RecognitionException
 *  org.antlr.runtime.RecognizerSharedState
 *  org.antlr.runtime.Token
 *  org.antlr.runtime.TokenStream
 */
package com.jiuqi.np.dataengine.parse.link;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.cell.CellNode;
import com.jiuqi.bi.syntax.cell.CompatiblePosition;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.WildcardRange;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.data.DimensionNode;
import com.jiuqi.bi.syntax.data.PercentNode;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.keyword.KeywordNode;
import com.jiuqi.bi.syntax.operator.And;
import com.jiuqi.bi.syntax.operator.Divide;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.operator.IOperator;
import com.jiuqi.bi.syntax.operator.IfThenElse;
import com.jiuqi.bi.syntax.operator.In;
import com.jiuqi.bi.syntax.operator.LessThan;
import com.jiuqi.bi.syntax.operator.LessThanOrEqual;
import com.jiuqi.bi.syntax.operator.Like;
import com.jiuqi.bi.syntax.operator.LinkPlus;
import com.jiuqi.bi.syntax.operator.Minus;
import com.jiuqi.bi.syntax.operator.MoreThan;
import com.jiuqi.bi.syntax.operator.MoreThanOrEqual;
import com.jiuqi.bi.syntax.operator.Multiply;
import com.jiuqi.bi.syntax.operator.Negative;
import com.jiuqi.bi.syntax.operator.Not;
import com.jiuqi.bi.syntax.operator.NotEqual;
import com.jiuqi.bi.syntax.operator.Or;
import com.jiuqi.bi.syntax.operator.Plus;
import com.jiuqi.bi.syntax.operator.Positive;
import com.jiuqi.bi.syntax.operator.Power;
import com.jiuqi.bi.syntax.operator.SymbolOperator;
import com.jiuqi.bi.syntax.parser.ErrorInfo;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.IParseListener;
import com.jiuqi.bi.syntax.parser.NodeToken;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.script.IScriptContext;
import com.jiuqi.bi.syntax.script.OrderField;
import com.jiuqi.bi.syntax.script.ScriptBlock;
import com.jiuqi.bi.syntax.script.ScriptEvent;
import com.jiuqi.bi.syntax.script.ScriptNode;
import com.jiuqi.bi.syntax.script.SelectField;
import com.jiuqi.bi.syntax.script.SetNode;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.parse.link.ParserAssistant;
import com.jiuqi.np.dataengine.parse.link.ReportFormulaParser_link;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.FailedPredicateException;
import org.antlr.runtime.MismatchedNotSetException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.MismatchedTreeNodeException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;

public class BaseANTLRParser
extends Parser {
    private IASTNode root;
    private ReportFormulaParser_link parser;
    private IContext context;
    private Stack<IASTNode> operand = new Stack();
    private Stack<IASTNode> operator = new Stack();
    private Stack<Object> bqlNodeStack = new Stack();
    private Stack<Integer> operand_state = new Stack();
    private List<IASTNode> reference = new ArrayList<IASTNode>();
    private Map<String, IASTNode> fromNodeMaps = new HashMap<String, IASTNode>();
    private ArrayList<ArrayList<IASTNode>> array_item = new ArrayList();
    private List<org.antlr.runtime.Token> cacheToken = new ArrayList<org.antlr.runtime.Token>();
    private List<ErrorInfo> errors = new ArrayList<ErrorInfo>();
    private boolean isCompatibleOldExcel;
    private CellNode cellIdNode;
    private static final Map<String, String> tNames = new HashMap<String, String>(25);
    private IASTNode sheetNameNode = null;
    private IASTNode tableNameNode = null;
    private List<IASTNode> zbAppends = new ArrayList<IASTNode>();
    private CellPosItem cell;
    private Stack<WildcardRange> wildcardRanges = new Stack();
    protected int cellRegion_start = -1;
    protected int cellRegion_end = -1;

    public void setCompatibleOldExcel(boolean isCompatibleOldExcel) {
        this.isCompatibleOldExcel = isCompatibleOldExcel;
    }

    public boolean isCompatibleOldExcel() {
        return this.isCompatibleOldExcel;
    }

    public BaseANTLRParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }

    public BaseANTLRParser(TokenStream input, IContext context) {
        this(input);
        this.context = context;
    }

    public BaseANTLRParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public void setFormulaParser(ReportFormulaParser_link parser) {
        this.parser = parser;
    }

    public void setContext(IContext context) {
        this.context = context;
    }

    public IASTNode getTree() throws ParseException {
        try {
            this.evaluate();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ParseException((Throwable)e);
        }
        if (!this.errors.isEmpty()) {
            throw new ParseException("\u8bed\u6cd5\u89e3\u6790\u5931\u8d25\uff1a");
        }
        try {
            this.processStack();
        }
        catch (RecognitionException e) {
            throw new ParseException((Throwable)e);
        }
        return this.root;
    }

    public ScriptNode getScriptTree() throws ParseException {
        try {
            this.evaluate();
        }
        catch (Exception e) {
            throw new ParseException((Throwable)e);
        }
        if (!this.errors.isEmpty()) {
            throw new ParseException("\u8bed\u6cd5\u89e3\u6790\u5931\u8d25\uff1a");
        }
        if (this.bqlNodeStack.size() == 1) {
            return (ScriptNode)this.bqlNodeStack.pop();
        }
        throw new ParseException("\u8bed\u6cd5\u89e3\u6790\u5931\u8d25\uff1a");
    }

    public Set<IASTNode> preProcessFromBlockForScript(String formula) throws SyntaxException {
        if (formula == null || formula.length() == 0) {
            return null;
        }
        this.createScriptNode();
        int len = formula.length();
        for (int index = 0; index < len && index + 1 != len; ++index) {
            index = this.tryParseFormula(formula, index);
        }
        HashSet<IASTNode> fromNodes = new HashSet<IASTNode>();
        fromNodes.addAll(this.fromNodeMaps.values());
        return fromNodes;
    }

    public List<ErrorInfo> errorParserTokenSet() {
        return this.errors;
    }

    protected Object evaluate() throws RecognitionException {
        return null;
    }

    protected void pushOperand(IASTNode value) {
        this.operand.push(value);
    }

    protected void pushOperator(IASTNode symbol) throws RecognitionException {
        if (this.operator.empty()) {
            this.operator.push(symbol);
        } else if (this.hasNodeState(symbol) || symbol == SymbolOperator.LEFT_BRACKET || symbol instanceof IfThenElse) {
            this.operator.push(symbol);
        } else if (symbol == SymbolOperator.COMMA) {
            IASTNode node = this.operator.peek();
            while (node != SymbolOperator.LEFT_BRACKET) {
                if (node instanceof IOperator) {
                    boolean rs = this.calculateAndUpdateStack((IOperator)node);
                    if (!rs) {
                        break;
                    }
                } else if (this.hasNodeState(node)) {
                    this.processStackNodeByOperandState(node);
                } else {
                    this.recordErrorMsg(new ErrorInfo(this.convert(symbol.getToken()), "\u8bed\u6cd5\u6709\u9519\u8bef"));
                    throw new RecognitionException();
                }
                if (!this.operator.isEmpty()) {
                    node = this.operator.peek();
                    continue;
                }
                break;
            }
        } else if (symbol == SymbolOperator.RIGHT_BRACKET) {
            IASTNode node = this.operator.peek();
            while (node != SymbolOperator.LEFT_BRACKET) {
                if (node == SymbolOperator.RIGHT_BRACKET) {
                    this.recordErrorMsg(new ErrorInfo(this.convert(symbol.getToken()), "\u53f3\u62ec\u53f7\u4e0d\u5e94\u8be5\u51fa\u73b0\u5728\u8fd9\u4e2a\u4f4d\u7f6e"));
                    throw new RecognitionException();
                }
                if (this.hasNodeState(node)) {
                    this.processStackNodeByOperandState(node);
                } else if (node instanceof IOperator) {
                    boolean rs = this.calculateAndUpdateStack((IOperator)node);
                    if (!rs) {
                        break;
                    }
                } else {
                    this.recordErrorMsg(new ErrorInfo(this.convert(symbol.getToken()), "\u8bed\u6cd5\u6709\u9519\u8bef"));
                    throw new RecognitionException();
                }
                if (this.operator.isEmpty()) break;
                node = this.operator.peek();
                if (node != SymbolOperator.LEFT_BRACKET || this.operator.size() <= 1) continue;
                this.operator.pop();
                node = this.operator.peek();
                if (node instanceof SetNode) continue;
                node = SymbolOperator.LEFT_BRACKET;
                this.operator.push(node);
            }
            if (!this.operator.empty() && this.operator.peek() == SymbolOperator.LEFT_BRACKET) {
                this.operator.pop();
            }
        } else {
            IASTNode oper = this.operator.peek();
            if (oper instanceof IOperator) {
                if (oper instanceof SymbolOperator || oper instanceof IfThenElse || ((IOperator)symbol).level() < ((IOperator)oper).level()) {
                    this.operator.push(symbol);
                } else {
                    this.calculateAndUpdateStack((IOperator)oper);
                    if (!this.operator.isEmpty() && symbol instanceof IOperator) {
                        oper = this.operator.peek();
                        while (oper instanceof IOperator && ((IOperator)oper).level() <= ((IOperator)symbol).level() && !(oper instanceof SymbolOperator) && !(oper instanceof IfThenElse)) {
                            this.calculateAndUpdateStack((IOperator)oper);
                            if (this.operator.isEmpty()) break;
                            oper = this.operator.peek();
                        }
                    }
                    this.operator.push(symbol);
                }
            } else if (this.hasNodeState(oper)) {
                IASTNode topoper;
                this.processStackNodeByOperandState(oper);
                if (!this.operator.isEmpty() && (topoper = this.operator.peek()) instanceof IOperator && ((IOperator)topoper).level() <= ((IOperator)symbol).level()) {
                    this.calculateAndUpdateStack((IOperator)topoper);
                }
                this.pushOperator(symbol);
            } else {
                this.recordErrorMsg(new ErrorInfo(this.convert(symbol.getToken()), "\u8bed\u6cd5\u6709\u9519\u8bef"));
                throw new RecognitionException();
            }
        }
    }

    protected boolean calculateAndUpdateStack(IOperator oper) throws RecognitionException {
        if (oper instanceof SymbolOperator || oper instanceof IfThenElse) {
            return false;
        }
        if (oper.childrenSize() > this.operand.size()) {
            StringBuilder builder = new StringBuilder(oper.sign());
            builder.append("\u8fd0\u7b97\u7b26\u7f3a\u5c11\u8db3\u591f\u7684\u6709\u6548\u8fd0\u7b97\u6570");
            this.recordErrorMsg(new ErrorInfo(this.convert(oper.getToken()), builder.toString()));
            throw new RecognitionException();
        }
        for (int i = oper.childrenSize() - 1; i >= 0; --i) {
            oper.setChild(i, this.operand.pop());
        }
        this.operator.pop();
        this.operand.push((IASTNode)oper);
        return true;
    }

    private void processStackNodeByOperandState(IASTNode node) {
        int parameterNum = this.operand.size() - this.operand_state.pop();
        if (parameterNum > 0) {
            IASTNode[] params = new IASTNode[parameterNum];
            int i = 0;
            while (parameterNum > 0) {
                params[i++] = this.operand.pop();
                --parameterNum;
            }
            while (i-- > 0) {
                node.addChild(params[i]);
            }
        }
        this.operand.push(node);
        this.operator.pop();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void processStack() throws RecognitionException, ParseException {
        if (this.operator.empty()) {
            if (this.operand.size() != 1) throw new ParseException("\u8bed\u6cd5\u89e3\u6790\u5931\u8d25\uff0c\u5c1d\u8bd5\u89e3\u6790\u5806\u6808\u4fe1\u606f\u51fa\u73b0\u5f02\u5e38");
            this.root = this.operand.pop();
        } else {
            while (!this.operator.empty()) {
                IASTNode node = this.operator.peek();
                if (node instanceof SymbolOperator) {
                    this.operator.pop();
                    continue;
                }
                if (node instanceof IfThenElse) {
                    this.root = this.operator.pop();
                    if (this.root.getChild(1) == null && !this.operand.isEmpty()) {
                        this.root.setChild(1, this.operand.pop());
                    } else if (this.root.getChild(2) == null && !this.operand.isEmpty()) {
                        this.root.setChild(2, this.operand.pop());
                    }
                    if (this.operator.empty()) continue;
                    IOperator oper2 = (IOperator)this.operator.peek();
                    if (oper2 instanceof IfThenElse) {
                        oper2.setChild(2, node);
                        this.root = oper2;
                        continue;
                    }
                    this.recordErrorMsg(new ErrorInfo(this.convert(oper2.getToken()), "\u8bed\u6cd5\u6709\u9519\u8bef"));
                    throw new ParseException("\u8bed\u6cd5\u89e3\u6790\u5931\u8d25\uff0c\u5c1d\u8bd5\u89e3\u6790\u5806\u6808\u4fe1\u606f\u51fa\u73b0\u5f02\u5e38");
                }
                if (this.hasNodeState(node)) {
                    this.processStackNodeByOperandState(node);
                    continue;
                }
                if (!(node instanceof IOperator)) continue;
                this.calculateAndUpdateStack((IOperator)node);
                this.root = node;
            }
        }
        if (this.operand.isEmpty()) return;
        if (this.operand.size() != 1) throw new ParseException("\u8bed\u6cd5\u89e3\u6790\u5931\u8d25\uff0c\u5c1d\u8bd5\u89e3\u6790\u5806\u6808\u4fe1\u606f\u51fa\u73b0\u5f02\u5e38");
        this.root = this.operand.pop();
    }

    protected void processIF() throws RecognitionException {
        IASTNode node = this.operator.peek();
        while (!(node instanceof IfThenElse)) {
            if (node instanceof IOperator) {
                boolean rs = this.calculateAndUpdateStack((IOperator)node);
                if (!rs) {
                    break;
                }
            } else if (node instanceof FunctionNode) {
                this.processStackNodeByOperandState(node);
            } else {
                this.recordErrorMsg(new ErrorInfo(this.convert(node.getToken()), "IF_THEN_ELSE\u8bed\u6cd5\u6709\u9519\u8bef"));
                throw new RecognitionException();
            }
            if (this.operator.isEmpty()) break;
            node = this.operator.peek();
        }
        if (this.operand.isEmpty()) {
            throw new RecognitionException();
        }
        node.setChild(0, this.operand.pop());
        this.root = node;
    }

    protected void processThen() throws RecognitionException {
        IASTNode node = this.operator.peek();
        while (!(node instanceof IfThenElse)) {
            if (node instanceof IOperator) {
                this.calculateAndUpdateStack((IOperator)node);
            } else if (node instanceof FunctionNode) {
                this.processStackNodeByOperandState(node);
            } else {
                this.recordErrorMsg(new ErrorInfo(this.convert(node.getToken()), "IF_THEN_ELSE\u8bed\u6cd5\u6709\u9519\u8bef"));
                throw new RecognitionException();
            }
            if (this.operator.isEmpty()) break;
            node = this.operator.peek();
        }
        if (this.operand.isEmpty()) {
            throw new RecognitionException();
        }
        node.setChild(1, this.operand.pop());
        this.root = node;
    }

    protected void putArrayItem() throws RecognitionException {
        IASTNode node;
        if (!this.operator.empty() && ((node = this.operator.peek()) instanceof Negative || node instanceof Positive)) {
            this.calculateAndUpdateStack((IOperator)node);
        }
        this.array_item.get(this.array_item.size() - 1).add(this.operand.pop());
    }

    protected void addArrayLine() {
        this.array_item.add(new ArrayList());
    }

    protected void pushPlus(org.antlr.runtime.Token token) throws RecognitionException {
        Plus node = new Plus(this.transToken(token));
        this.pushOperator((IASTNode)node);
    }

    protected void pushEqual(org.antlr.runtime.Token token) throws RecognitionException {
        Equal node = new Equal(this.transToken(token), this.parser.isExcelMode());
        this.pushOperator((IASTNode)node);
    }

    protected void pushNotEqual(org.antlr.runtime.Token token) throws RecognitionException {
        NotEqual node = new NotEqual(this.transToken(token), this.parser.isExcelMode());
        this.pushOperator((IASTNode)node);
    }

    protected void pushLessThan(org.antlr.runtime.Token token) throws RecognitionException {
        LessThan node = new LessThan(this.transToken(token), this.parser.isExcelMode());
        this.pushOperator((IASTNode)node);
    }

    protected void pushLessThanOrEqual(org.antlr.runtime.Token token) throws RecognitionException {
        LessThanOrEqual node = new LessThanOrEqual(this.transToken(token), this.parser.isExcelMode());
        this.pushOperator((IASTNode)node);
    }

    protected void pushMoreThan(org.antlr.runtime.Token token) throws RecognitionException {
        MoreThan node = new MoreThan(this.transToken(token), this.parser.isExcelMode());
        this.pushOperator((IASTNode)node);
    }

    protected void pushMoreThanOrEqual(org.antlr.runtime.Token token) throws RecognitionException {
        MoreThanOrEqual node = new MoreThanOrEqual(this.transToken(token), this.parser.isExcelMode());
        this.pushOperator((IASTNode)node);
    }

    protected void pushLink(org.antlr.runtime.Token token) throws RecognitionException {
        LinkPlus node = new LinkPlus(this.transToken(token));
        this.pushOperator((IASTNode)node);
    }

    protected void pushLike(org.antlr.runtime.Token token) throws RecognitionException {
        Like node = new Like(this.transToken(token));
        this.pushOperator((IASTNode)node);
    }

    protected void pushIn(org.antlr.runtime.Token token) throws RecognitionException {
        In node = new In(this.transToken(token));
        this.pushOperator((IASTNode)node);
    }

    protected void pushAnd(org.antlr.runtime.Token token) throws RecognitionException {
        And node = new And(this.transToken(token));
        this.pushOperator((IASTNode)node);
    }

    protected void pushOr(org.antlr.runtime.Token token) throws RecognitionException {
        Or node = new Or(this.transToken(token));
        this.pushOperator((IASTNode)node);
    }

    protected void pushNot(org.antlr.runtime.Token token) throws RecognitionException {
        Not node = new Not(this.transToken(token), this.parser.isExcelMode());
        this.pushOperator((IASTNode)node);
    }

    protected void pushMinus(org.antlr.runtime.Token token) throws RecognitionException {
        Minus node = new Minus(this.transToken(token));
        this.pushOperator((IASTNode)node);
    }

    protected void pushMultiply(org.antlr.runtime.Token token) throws RecognitionException {
        Multiply node = new Multiply(this.transToken(token), this.parser.isExcelMode());
        this.pushOperator((IASTNode)node);
    }

    protected void pushDivide(org.antlr.runtime.Token token) throws RecognitionException {
        Divide node = new Divide(this.transToken(token), this.parser.isExcelMode());
        this.pushOperator((IASTNode)node);
    }

    protected void pushNegative(org.antlr.runtime.Token token) throws RecognitionException {
        Negative node = new Negative(this.transToken(token));
        this.pushOperator((IASTNode)node);
    }

    protected void pushPositive(org.antlr.runtime.Token token) throws RecognitionException {
        Positive node = new Positive(this.transToken(token));
        this.pushOperator((IASTNode)node);
    }

    protected void pushPower(org.antlr.runtime.Token token) throws RecognitionException {
        Power node = new Power(this.transToken(token));
        this.pushOperator((IASTNode)node);
    }

    protected void pushIfThenElse(org.antlr.runtime.Token token) throws RecognitionException {
        IfThenElse node = new IfThenElse(this.transToken(token));
        this.pushOperator((IASTNode)node);
    }

    protected void pushFunction(org.antlr.runtime.Token token) throws RecognitionException {
        Token tk = this.transToken(token);
        try {
            String text = token == null ? null : token.getText();
            IASTNode node = this.parser.produceAsFunction(this.context, tk, text, null);
            this.pushOperator(node);
            this.pushOperandState();
        }
        catch (SyntaxException e) {
            RecognitionException exception = new RecognitionException();
            exception.setStackTrace(e.getStackTrace());
            this.recordErrorMsg(new ErrorInfo(this.convert(tk), e.getMessage()));
            throw exception;
        }
    }

    protected void pushLeftBracket() throws RecognitionException {
        this.pushOperator((IASTNode)SymbolOperator.LEFT_BRACKET);
    }

    protected void pushRightBracket() throws RecognitionException {
        this.pushOperator((IASTNode)SymbolOperator.RIGHT_BRACKET);
    }

    protected void pushComma() throws RecognitionException {
        this.pushOperator((IASTNode)SymbolOperator.COMMA);
    }

    protected void pushInt(org.antlr.runtime.Token token) throws RecognitionException {
        DataNode node;
        String text = token == null ? null : token.getText();
        try {
            node = new DataNode(this.transToken(token), Integer.parseInt(text));
        }
        catch (NumberFormatException e) {
            node = new DataNode(this.transToken(token), Double.parseDouble(text));
        }
        this.pushOperand((IASTNode)node);
    }

    protected void pushSimpleId(org.antlr.runtime.Token token) throws RecognitionException {
        this.pushOperand((IASTNode)new DataNode(this.transToken(token), token.getText()));
    }

    protected void pushFloat(org.antlr.runtime.Token token) throws RecognitionException {
        String text = token == null ? null : token.getText();
        DataNode node = new DataNode(this.transToken(token), Double.parseDouble(text));
        this.pushOperand((IASTNode)node);
    }

    protected void pushPercent(org.antlr.runtime.Token token) throws RecognitionException {
        String text = token.getText();
        String valStr = text.substring(0, text.length() - 1);
        int decimal = PercentNode.decimalOf((String)text);
        double value = Double.parseDouble(valStr) / 100.0;
        PercentNode node = new PercentNode(this.transToken(token), value, decimal);
        this.pushOperand((IASTNode)node);
    }

    protected void pushString(org.antlr.runtime.Token token) throws RecognitionException {
        String text = token.getText();
        String value = text.equals("\"\"") ? "" : text.substring(1, text.length() - 1);
        int pos = (value = value.replace("\\\\", "\\").replace("\\\"", "\"").replace("\\'", "'").replace("\\t", "\t").replace("\\r", "\r").replace("\\n", "\n")).indexOf("\\u");
        if (pos >= 0) {
            try {
                value = BaseANTLRParser.unicode2String(value);
            }
            catch (Exception e) {
                RecognitionException exception = new RecognitionException();
                exception.setStackTrace(e.getStackTrace());
                this.recordErrorMsg(new ErrorInfo(this.convert(this.transToken(token)), e.getMessage()));
                throw exception;
            }
        }
        DataNode node = new DataNode(this.transToken(token), value);
        this.pushOperand((IASTNode)node);
    }

    protected void pushDate(org.antlr.runtime.Token token) throws RecognitionException {
        Token tk = this.transToken(token);
        String text = token.getText();
        try {
            IASTNode node = this.parser.externalParse(this.context, tk, text.substring(1, text.length() - 1));
            this.pushOperand(node);
        }
        catch (SyntaxException e) {
            RecognitionException exception = new RecognitionException();
            exception.setStackTrace(e.getStackTrace());
            this.recordErrorMsg(new ErrorInfo(this.convert(tk), e.getMessage()));
            throw exception;
        }
    }

    protected void pushTrue(org.antlr.runtime.Token token) throws RecognitionException {
        DataNode node = new DataNode(this.transToken(token), Boolean.TRUE.booleanValue());
        this.pushOperand((IASTNode)node);
    }

    protected void pushFalse(org.antlr.runtime.Token token) throws RecognitionException {
        DataNode node = new DataNode(this.transToken(token), Boolean.FALSE.booleanValue());
        this.pushOperand((IASTNode)node);
    }

    protected void pushNull(org.antlr.runtime.Token token) throws RecognitionException {
        DataNode node = new DataNode(this.transToken(token), 0, null);
        this.pushOperand((IASTNode)node);
    }

    protected void pushKeyWord(org.antlr.runtime.Token token) throws RecognitionException {
        KeywordNode node = new KeywordNode(this.transToken(token), token.getText());
        this.pushOperand((IASTNode)node);
    }

    protected void pushArray(org.antlr.runtime.Token tk) throws RecognitionException {
        Token token = this.transToken(tk);
        try {
            DataNode node = new DataNode(token, this.createArrayData());
            this.pushOperand((IASTNode)node);
        }
        catch (SyntaxException e) {
            RecognitionException exception = new RecognitionException();
            exception.setStackTrace(e.getStackTrace());
            this.recordErrorMsg(new ErrorInfo(this.convert(token), e.getMessage()));
            throw exception;
        }
        finally {
            this.array_item.clear();
        }
    }

    protected void pushSheetName(org.antlr.runtime.Token token) {
        String text = token.getText();
        if (text.startsWith("'") && text.endsWith("'")) {
            text = text.substring(1, text.length() - 1);
        }
        this.sheetNameNode = new DataNode(this.transToken(token), text);
    }

    protected void clearSheetName() {
        this.sheetNameNode = null;
    }

    protected void pushTableName(org.antlr.runtime.Token token) {
        String text = token.getText();
        if (text.startsWith("'") && text.endsWith("'")) {
            text = text.substring(1, text.length() - 1);
        }
        this.tableNameNode = new DataNode(this.transToken(token), text);
    }

    protected void clearTableName() {
        this.tableNameNode = null;
    }

    protected void pushZbAppend(org.antlr.runtime.Token token) {
        String text = "";
        if (token != null) {
            text = token.getText();
        }
        this.zbAppends.add((IASTNode)new DataNode(this.transToken(token), text));
    }

    protected void pushSquareNode(org.antlr.runtime.Token token) throws RecognitionException {
        this.pushOperator(new ParserAssistant.SquareBracketNode(this.transToken(token), null));
        this.pushOperandState();
    }

    protected void pushCellPos() throws RecognitionException {
        this.pushOperand((IASTNode)new DataNode(null, 0, (Object)this.cell));
    }

    protected void processCellItem() throws RecognitionException {
        IASTNode node = this.operator.peek();
        this.processStackNodeByOperandState(node);
    }

    protected void pushCellExcelBasic() throws RecognitionException {
        Token token = this.mergeCacheToken();
        this.pushOperand(new ParserAssistant.UCellNode(token, token.text()));
    }

    protected void processExcelBasicRegion() throws RecognitionException {
        IASTNode end = this.operand.pop();
        IASTNode start = this.operand.pop();
        try {
            ParserAssistant.URegionNode node = new ParserAssistant.URegionNode(start.getToken(), (String)start.evaluate(this.context), (String)end.evaluate(this.context));
            this.pushOperand(node);
        }
        catch (SyntaxException e) {
            RecognitionException exception = new RecognitionException();
            exception.setStackTrace(e.getStackTrace());
            this.recordErrorMsg(new ErrorInfo(this.convert(start.getToken()), e.getMessage()));
            throw exception;
        }
    }

    protected void pushExcelRowColRegion() throws RecognitionException {
        Token token = this.mergeCacheToken();
        this.pushOperand(new ParserAssistant.URegionNode(token, token.text()));
    }

    protected void processFormulaCell() throws RecognitionException {
        if (!this.isCompatibleOldExcel()) {
            RecognitionException exception = new RecognitionException();
            this.recordErrorMsg(new ErrorInfo(null, "\u516c\u5f0f\u672a\u652f\u6301\u5355\u5143\u683c\u8bed\u6cd5"));
            throw exception;
        }
        ParserAssistant.SquareBracketNode node = (ParserAssistant.SquareBracketNode)this.operand.pop();
        try {
            Object v;
            CellPosItem row = (CellPosItem)node.getChild(0).evaluate(this.context);
            CellPosItem col = null;
            if (node.childrenSize() > 1 && node.getChild(1) instanceof DataNode && (v = node.getChild(1).evaluate(this.context)) instanceof CellPosItem) {
                col = (CellPosItem)v;
            }
            if (col == null) {
                col = new CellPosItem();
                col.pos = -2;
            }
            ArrayList<IASTNode> params = new ArrayList<IASTNode>(this.zbAppends);
            this.zbAppends.clear();
            String sheetName = this.sheetNameNode == null ? null : (String)this.sheetNameNode.evaluate(this.context);
            Position p = new Position(col.pos, row.pos);
            CompatiblePosition pos = new CompatiblePosition(p, row.offset, col.offset, row.multiply, col.multiply);
            IASTNode cellNode = this.parser.produceAsCell(this.context, node.getWholeToken(), sheetName, pos, params);
            this.pushOperand(cellNode);
        }
        catch (SyntaxException e) {
            RecognitionException exception = new RecognitionException();
            exception.setStackTrace(e.getStackTrace());
            this.recordErrorMsg(new ErrorInfo(null, e.getMessage()));
            throw exception;
        }
    }

    protected void processSimpleCell() throws RecognitionException {
        ParserAssistant.UCellNode cell = (ParserAssistant.UCellNode)this.operand.pop();
        try {
            String sheetName = this.sheetNameNode == null ? null : (String)this.sheetNameNode.evaluate(this.context);
            IASTNode node = this.parser.produceAsCell(this.context, cell.getToken(), sheetName, cell.getCellExpr());
            this.pushOperand(node);
        }
        catch (SyntaxException e) {
            RecognitionException exception = new RecognitionException();
            exception.setStackTrace(e.getStackTrace());
            this.recordErrorMsg(new ErrorInfo(this.convert(cell.getToken()), e.getMessage()));
            throw exception;
        }
    }

    protected void processRegion() throws RecognitionException {
        ParserAssistant.URegionNode region = (ParserAssistant.URegionNode)this.operand.pop();
        try {
            String sheetName = this.sheetNameNode == null ? null : (String)this.sheetNameNode.evaluate(this.context);
            IASTNode node = this.parser.produceAsRegion(this.context, region.getToken(), sheetName, region.getStart(), region.getEnd());
            this.pushOperand(node);
        }
        catch (SyntaxException e) {
            RecognitionException exception = new RecognitionException();
            exception.setStackTrace(e.getStackTrace());
            this.recordErrorMsg(new ErrorInfo(this.convert(region.getToken()), e.getMessage()));
            throw exception;
        }
    }

    protected void pushZbAppendItem() throws RecognitionException {
        Token token = this.mergeCacheToken();
        this.zbAppends.add((IASTNode)new DataNode(token, token.text()));
    }

    protected void pushDimItem() throws RecognitionException {
        Token token = this.mergeCacheToken();
        DimensionNode dimesionNode = null;
        String[] strs = token.text().split("=");
        if (strs.length == 2) {
            Object value = strs[1];
            if (strs[1].startsWith("'") || strs[1].startsWith("\"")) {
                if (strs[1].length() > 2) {
                    value = strs[1].substring(1, strs[1].length() - 1);
                    dimesionNode = new DimensionNode(token, strs[0], 6, value);
                }
            } else if (strs[1].length() > 0) {
                value = Integer.valueOf(strs[1]);
                dimesionNode = new DimensionNode(token, strs[0], 3, value);
            }
        }
        if (dimesionNode == null) {
            RecognitionException exception = new RecognitionException();
            this.recordErrorMsg(new ErrorInfo(this.convert(token), "\u9519\u8bef\u7684\u7ef4\u5ea6\u9650\u5b9a"));
            throw exception;
        }
        this.zbAppends.add((IASTNode)dimesionNode);
    }

    protected void processZB() throws RecognitionException {
        this.processStackNodeByOperandState(this.operator.peek());
        ParserAssistant.SquareBracketNode node = (ParserAssistant.SquareBracketNode)this.operand.pop();
        try {
            String tableName = this.tableNameNode == null ? null : (String)this.tableNameNode.evaluate(this.context);
            String kpiName = (String)node.getChild(0).evaluate(this.context);
            ArrayList<IASTNode> specExprs = new ArrayList<IASTNode>(this.zbAppends);
            this.zbAppends.clear();
            IASTNode kpi = this.parser.produceAsKPI(this.context, node.getWholeToken(), tableName, kpiName, specExprs);
            this.pushOperand(kpi);
        }
        catch (SyntaxException e) {
            RecognitionException exception = new RecognitionException();
            exception.setStackTrace(e.getStackTrace());
            this.recordErrorMsg(new ErrorInfo(this.convert(node.getToken()), e.getMessage()));
            throw exception;
        }
    }

    protected void processRestrict() throws RecognitionException {
        this.processStackNodeByOperandState(this.operator.peek());
        ParserAssistant.SquareBracketNode node = (ParserAssistant.SquareBracketNode)this.operand.pop();
        try {
            String tableName = this.tableNameNode == null ? null : (String)this.tableNameNode.evaluate(this.context);
            String name = (String)node.getChild(0).evaluate(this.context);
            List<String> objPath = Arrays.asList(name.split("\\."));
            if (objPath.size() == 1) {
                objPath = new ArrayList<String>();
                objPath.add(tableName);
                objPath.add(name);
            }
            ArrayList<IASTNode> rsItems = new ArrayList<IASTNode>();
            for (int i = 1; i < node.childrenSize(); ++i) {
                rsItems.add(node.getChild(i));
            }
            IASTNode restNode = this.parser.produceAsRestrict(this.context, node.getWholeToken(), objPath, rsItems);
            this.pushOperand(restNode);
            this.notifyFinishRestrict();
        }
        catch (SyntaxException e) {
            RecognitionException exception = new RecognitionException();
            exception.setStackTrace(e.getStackTrace());
            this.recordErrorMsg(new ErrorInfo(this.convert(node.getToken()), e.getMessage()));
            throw exception;
        }
    }

    protected void pushRestrictObjExprName() throws RecognitionException {
        Token token = this.mergeCacheToken();
        this.pushOperand((IASTNode)new DataNode(token, token.text()));
    }

    protected void resetCell() {
        this.cell = new CellPosItem();
    }

    protected void setCellPos(org.antlr.runtime.Token token) throws RecognitionException {
        String txt = token.getText();
        this.cell.pos = txt.equals("*") ? -2 : Integer.valueOf(txt);
    }

    protected void getCellItembyId(org.antlr.runtime.Token token) throws RecognitionException {
        String txt = token.getText();
        ArrayList<IASTNode> specExprs = null;
        try {
            IASTNode kpi;
            String sheetName = this.sheetNameNode == null ? null : (String)this.sheetNameNode.evaluate(this.context);
            int index = txt.lastIndexOf("_");
            String kpiName = txt;
            if (index > 0) {
                sheetName = txt.substring(0, index);
                kpiName = txt.substring(index + 1);
            }
            if ((index = kpiName.indexOf("$")) > 0) {
                specExprs = new ArrayList<IASTNode>(1);
                specExprs.add((IASTNode)new DataNode(null, "@" + kpiName.substring(index + 1)));
                kpiName = kpiName.substring(0, index);
            }
            if ((kpi = this.parser.produceAsKPI(this.context, null, sheetName, kpiName, specExprs)) instanceof CellNode) {
                this.cellIdNode = (CellNode)kpi;
            }
        }
        catch (SyntaxException e) {
            RecognitionException exception = new RecognitionException();
            exception.setStackTrace(e.getStackTrace());
            throw exception;
        }
    }

    protected void setCellPosById(org.antlr.runtime.Token token) throws RecognitionException {
        this.cell.pos = this.getCellPos(token);
    }

    protected void resetCellIdNode() {
        this.cellIdNode = null;
    }

    protected int getCellPos(org.antlr.runtime.Token token) throws RecognitionException {
        String txt = token.getText();
        Position cellPosition = this.cellIdNode.getCellPosition();
        return txt.toLowerCase().equals("c") ? cellPosition.col() : cellPosition.row();
    }

    protected void setCellOffset(org.antlr.runtime.Token token) throws RecognitionException {
        String txt = token.getText();
        if (txt.charAt(0) == '+') {
            txt = txt.substring(1);
        }
        this.cell.offset = Integer.valueOf(txt);
    }

    protected void setCellMultiply(org.antlr.runtime.Token token) throws RecognitionException {
        String txt = token.getText();
        if (txt.charAt(0) == '*') {
            txt = txt.substring(1);
            this.cell.multiply = Integer.valueOf(txt).intValue();
        } else {
            txt = txt.substring(1);
            this.cell.multiply = 1.0 / (double)Integer.valueOf(txt).intValue();
        }
    }

    public List<WildcardRange> getWildcardRanges() {
        return this.wildcardRanges;
    }

    protected void resetCellRegion() {
        this.cellRegion_start = -1;
        this.cellRegion_end = -1;
    }

    protected void resetCellPres() {
        this.wildcardRanges.clear();
    }

    protected void pushCellPres() {
        this.wildcardRanges.add(new WildcardRange());
    }

    protected void pushCellPresItem() {
        WildcardRange wr = this.wildcardRanges.peek();
        if (this.cellRegion_end == -1) {
            wr.add(this.cellRegion_start);
        } else {
            wr.add(this.cellRegion_start, this.cellRegion_end);
        }
    }

    protected void pushSetNode(org.antlr.runtime.Token token) throws RecognitionException {
        SetNode setNode = new SetNode(this.transToken(token));
        this.pushOperator((IASTNode)setNode);
        this.pushOperandState();
    }

    protected int getIntToken(org.antlr.runtime.Token token) throws RecognitionException {
        return Integer.valueOf(token.getText());
    }

    protected void concatToken(org.antlr.runtime.Token token1, org.antlr.runtime.Token token2) {
        token1.setText(token1.getText() + token2.getText());
    }

    protected void pushObjName(org.antlr.runtime.Token token) {
        DataNode node = new DataNode(this.transToken(token), token.getText());
        this.reference.add((IASTNode)node);
    }

    protected void pushObj_func() throws RecognitionException {
        RecognitionException e = new RecognitionException();
        SyntaxException syntaxException = new SyntaxException("\u8be5\u7c7b\u8bed\u6cd5\u76ee\u524d\u4e0d\u652f\u6301");
        e.setStackTrace(syntaxException.getStackTrace());
        throw e;
    }

    protected void pushObject() throws RecognitionException {
        ArrayList<String> objPath = new ArrayList<String>(this.reference.size());
        for (IASTNode n : this.reference) {
            objPath.add(n.getToken().text());
        }
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < objPath.size(); ++i) {
            buf.append((String)objPath.get(i));
            if (i == objPath.size() - 1) continue;
            buf.append(".");
        }
        Token token = this.reference.get(0).getToken();
        token = new Token(buf.toString(), token.line(), token.column(), token.index());
        try {
            IASTNode node = this.parser.produceAsObject(this.context, token, objPath);
            this.pushOperand(node);
        }
        catch (SyntaxException e) {
            RecognitionException exception = new RecognitionException();
            exception.setStackTrace(e.getStackTrace());
            this.recordErrorMsg(new ErrorInfo(this.convert(token), e.getMessage()));
            throw exception;
        }
        this.reference.clear();
    }

    protected void notifyStartFunction(org.antlr.runtime.Token token) throws RecognitionException {
        List<IParseListener> parseListeners = this.parser.getParseListener();
        try {
            for (IParseListener pListener : parseListeners) {
                pListener.startFunction(this.context, token.getText());
            }
        }
        catch (ParseException e) {
            this.recordErrorMsg(new ErrorInfo(this.convert(this.transToken(token)), e.getMessage()));
            RecognitionException re = new RecognitionException();
            re.setStackTrace(e.getStackTrace());
            throw re;
        }
    }

    protected void notifyFinishParam() throws RecognitionException {
        List<IParseListener> parseListeners = this.parser.getParseListener();
        try {
            for (IParseListener pListener : parseListeners) {
                IASTNode param = null;
                if (!this.operator.isEmpty() && this.operator.peek() instanceof FunctionNode) {
                    param = this.operator.peek();
                } else if (!this.operand.isEmpty()) {
                    param = this.operand.peek();
                }
                pListener.finishParam(this.context, param);
            }
        }
        catch (ParseException e) {
            this.recordErrorMsg(new ErrorInfo(null, e.getMessage()));
            RecognitionException re = new RecognitionException();
            re.setStackTrace(e.getStackTrace());
            throw re;
        }
    }

    protected void notifyFinishFunction() throws RecognitionException {
        List<IParseListener> parseListeners = this.parser.getParseListener();
        try {
            for (IParseListener pListener : parseListeners) {
                pListener.finishFunction(this.context, this.operator.peek());
            }
        }
        catch (ParseException e) {
            this.recordErrorMsg(new ErrorInfo(null, e.getMessage()));
            RecognitionException re = new RecognitionException();
            re.setStackTrace(e.getStackTrace());
            throw re;
        }
    }

    protected void notifyStartRestrict() throws RecognitionException {
        List<Object> restrictName = new ArrayList();
        Token token = this.operand.peek().getToken();
        restrictName = Arrays.asList(token.text().split("."));
        try {
            List<IParseListener> parseListeners = this.parser.getParseListener();
            for (IParseListener pListener : parseListeners) {
                pListener.startRestrict(this.context, restrictName);
            }
        }
        catch (ParseException e) {
            this.recordErrorMsg(new ErrorInfo(null, e.getMessage()));
            RecognitionException re = new RecognitionException();
            re.setStackTrace(e.getStackTrace());
            throw re;
        }
    }

    protected void notifyFinishRestrictItem(IASTNode item) throws RecognitionException {
        List<IParseListener> parseListeners = this.parser.getParseListener();
        try {
            for (IParseListener pListener : parseListeners) {
                pListener.finishRestrictItem(this.context, item);
            }
        }
        catch (ParseException e) {
            this.recordErrorMsg(new ErrorInfo(this.convert(item.getToken()), e.getMessage()));
            RecognitionException re = new RecognitionException();
            re.setStackTrace(e.getStackTrace());
            throw re;
        }
    }

    protected void notifyFinishRestrict() throws RecognitionException {
        List<IParseListener> parseListeners = this.parser.getParseListener();
        try {
            for (IParseListener pListener : parseListeners) {
                pListener.finishRestrict(this.context, this.operand.peek());
            }
        }
        catch (ParseException e) {
            this.recordErrorMsg(new ErrorInfo(null, e.getMessage()));
            RecognitionException re = new RecognitionException();
            re.setStackTrace(e.getStackTrace());
            throw re;
        }
    }

    protected void addTokenToCache(org.antlr.runtime.Token token) {
        this.cacheToken.add(token);
    }

    protected void clearCacheToken() {
        this.cacheToken.clear();
    }

    protected Token mergeCacheToken() {
        StringBuilder buf = new StringBuilder();
        org.antlr.runtime.Token first = this.cacheToken.get(0);
        for (int i = 0; i < this.cacheToken.size(); ++i) {
            buf.append(this.cacheToken.get(i).getText());
        }
        return new Token(buf.toString(), first.getLine(), first.getCharPositionInLine(), first.getTokenIndex());
    }

    protected void createScriptNode() {
        if (this.bqlNodeStack.size() == 1) {
            return;
        }
        this.bqlNodeStack.clear();
        ScriptNode node = new ScriptNode();
        this.bqlNodeStack.push(node);
    }

    protected void createSelectField() throws RecognitionException {
        this.processExprStackForScript();
        IASTNode item = this.operand.pop();
        SelectField node = new SelectField(item);
        this.bqlNodeStack.push(node);
    }

    protected void renameSelectField(org.antlr.runtime.Token token) {
        SelectField field = (SelectField)this.bqlNodeStack.peek();
        field.setAliasToken(this.transToken(token));
        field.setAlias(token.getText());
    }

    protected void appendSelectFieldToScriptNode() {
        SelectField field = (SelectField)this.bqlNodeStack.pop();
        ScriptNode script = (ScriptNode)this.bqlNodeStack.peek();
        script.getSelect().add(field);
    }

    protected void appendFromFieldToScriptNode() throws RecognitionException {
        ArrayList<String> objPath = new ArrayList<String>(this.reference.size());
        for (IASTNode n : this.reference) {
            objPath.add(n.getToken().text());
        }
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < objPath.size(); ++i) {
            buf.append((String)objPath.get(i));
            if (i == objPath.size() - 1) continue;
            buf.append(".");
        }
        IASTNode node = this.fromNodeMaps.get(buf.toString());
        ScriptNode script = (ScriptNode)this.bqlNodeStack.peek();
        script.getFrom().add(node);
        this.reference.clear();
    }

    protected void appendFilterFieldToScriptNode() throws RecognitionException {
        this.processExprStackForScript();
        IASTNode node = this.operand.pop();
        ScriptNode script = (ScriptNode)this.bqlNodeStack.peek();
        script.getFilter().add(node);
    }

    protected void appendSliceFieldToScriptNode() {
        SetNode node = (SetNode)this.operand.pop();
        ScriptNode script = (ScriptNode)this.bqlNodeStack.peek();
        script.getSlice().add(node);
    }

    protected void appendWhereFieldToScriptNode() throws RecognitionException {
        this.processExprStackForScript();
        IASTNode node = this.operand.pop();
        ScriptNode script = (ScriptNode)this.bqlNodeStack.peek();
        script.setWhere(node);
    }

    protected void appendOrderFieldToScriptNode() throws RecognitionException {
        this.processExprStackForScript();
        IASTNode expr = this.operand.pop();
        OrderField order = new OrderField();
        order.setExpr(expr);
        ScriptNode script = (ScriptNode)this.bqlNodeStack.peek();
        script.getOrderItems().add(order);
    }

    protected void setOrderToDesc() throws RecognitionException {
        ScriptNode script = (ScriptNode)this.bqlNodeStack.peek();
        List items = script.getOrderItems();
        int size = items.size();
        if (size > 0) {
            ((OrderField)items.get(size - 1)).setOrderType(1);
        }
    }

    protected void appendOptionsToScriptNode() throws RecognitionException {
        this.processExprStackForScript();
        IASTNode node = this.operand.pop();
        ScriptNode script = (ScriptNode)this.bqlNodeStack.peek();
        script.getOptionItems().add(node);
    }

    protected void notifyScriptBlock(ScriptBlock block, int status) throws RecognitionException {
        if (this.context == null || !(this.context instanceof IScriptContext)) {
            return;
        }
        if (block == ScriptBlock.FROM) {
            return;
        }
        try {
            this.notifyBlock(block, status);
        }
        catch (SyntaxException e) {
            this.recordErrorMsg(new ErrorInfo(null, e.getMessage()));
            throw new RecognitionException();
        }
    }

    private void notifyBlock(ScriptBlock block, int status) throws SyntaxException {
        ScriptEvent event = new ScriptEvent();
        event.node = (ScriptNode)this.bqlNodeStack.get(0);
        event.block = block;
        event.status = status;
        ((IScriptContext)this.context).notify(event);
    }

    private void processExprStackForScript() throws RecognitionException {
        while (!this.operator.empty()) {
            boolean rs;
            IASTNode node = this.operator.peek();
            if (node instanceof IfThenElse) {
                IASTNode oper = this.operator.pop();
                if (oper.getChild(1) == null && !this.operand.isEmpty()) {
                    oper.setChild(1, this.operand.pop());
                } else if (oper.getChild(2) == null && !this.operand.isEmpty()) {
                    oper.setChild(2, this.operand.pop());
                }
                this.operand.push(oper);
                continue;
            }
            if (this.hasNodeState(node)) {
                this.processStackNodeByOperandState(node);
                continue;
            }
            if (node instanceof IOperator && (rs = this.calculateAndUpdateStack((IOperator)node))) continue;
            break;
        }
    }

    public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
        String msg = this.getErrorMessage(e, tokenNames);
        if (e.token != null) {
            Token token = null;
            if (e.token.getType() == -1) {
                token = this.calExprEndToken(this.getTokenStream().toString());
            } else {
                int line = e.token.getLine();
                int col = e.token.getCharPositionInLine();
                int index = e.token.getTokenIndex();
                if (e.token instanceof CommonToken) {
                    index = ((CommonToken)e.token).getStartIndex();
                }
                token = new Token(e.token.getText(), line, col, index);
            }
            this.recordErrorMsg(new ErrorInfo(this.convert(token), msg));
        }
    }

    public String getErrorMessage(RecognitionException e, String[] tokenNames) {
        StringBuilder buf = new StringBuilder();
        if (e instanceof MismatchedTokenException) {
            MismatchedTokenException mte = (MismatchedTokenException)e;
            buf.append(this.getTokenErrorDisplay(e.token));
            if (mte.expecting == -1) {
                buf.append("\u5904\u9700\u8981\u4e00\u4e2a\u7ed3\u675f\u6807\u8bc6");
            } else {
                buf.append("\u9644\u8fd1\u9047\u5230\u65e0\u6cd5\u8bc6\u522b\u7684\u8bed\u6cd5");
                String expecting = tNames.get(tokenNames[mte.expecting]);
                if (expecting == null) {
                    expecting = tokenNames[mte.expecting];
                }
                if (!expecting.equals("'!'")) {
                    buf.append(", \u53ef\u80fd\u9700\u8981").append(expecting);
                }
            }
        } else if (e instanceof MismatchedTreeNodeException) {
            MismatchedTreeNodeException mtne = (MismatchedTreeNodeException)e;
            buf.append(mtne.node);
            if (mtne.expecting == -1) {
                buf.append("\u5904\u9700\u8981\u4e00\u4e2a\u7ed3\u675f\u6807\u8bc6");
            } else {
                String expecting = tNames.get(tokenNames[mtne.expecting]);
                if (expecting == null) {
                    expecting = tokenNames[mtne.expecting];
                }
                buf.append("\u5904\u9700\u8981").append(expecting);
            }
        } else if (e instanceof NoViableAltException) {
            buf.append(this.getTokenErrorDisplay(e.token));
            buf.append(" \u9644\u8fd1\u9047\u5230\u65e0\u6cd5\u8bc6\u522b\u7684\u8bed\u6cd5");
        } else if (e instanceof EarlyExitException) {
            buf.append(this.getTokenErrorDisplay(e.token));
            buf.append(" \u9644\u8fd1\u9047\u5230\u65e0\u6cd5\u8bc6\u522b\u7684\u8bed\u6cd5");
        } else if (e instanceof MismatchedSetException) {
            MismatchedSetException mse = (MismatchedSetException)e;
            buf.append(this.getTokenErrorDisplay(e.token));
            buf.append(" \u5904\u9700\u8981").append(mse.expecting);
        } else if (e instanceof MismatchedNotSetException) {
            MismatchedNotSetException mse = (MismatchedNotSetException)e;
            buf.append(this.getTokenErrorDisplay(e.token));
            buf.append(" \u5904\u9700\u8981").append(mse.expecting);
        } else if (e instanceof FailedPredicateException) {
            FailedPredicateException fpe = (FailedPredicateException)e;
            buf.append(fpe.ruleName).append("\u65ad\u8a00\u5931\u8d25\uff1a{").append(fpe.predicateText).append("}?");
        } else {
            buf.append(e.getMessage());
        }
        return buf.toString();
    }

    public String getTokenErrorDisplay(org.antlr.runtime.Token t) {
        String s = t.getText();
        if (s == null) {
            s = t.getType() == -1 ? "\u8868\u8fbe\u5f0f\u672b\u5c3e" : "<" + t.getType() + ">";
        }
        s = s.replaceAll("\n", "\\\\n");
        s = s.replaceAll("\r", "\\\\r");
        if ((s = s.replaceAll("\t", "\\\\t")).trim().equals("'")) {
            return "\u5355\u5f15\u53f7";
        }
        return "'" + s + "'";
    }

    private void pushOperandState() {
        this.operand_state.push(this.operand.size());
    }

    private void recordErrorMsg(ErrorInfo errorInfo) {
        for (int i = 0; i < this.errors.size(); ++i) {
            NodeToken oldTk = this.errors.get(i).getToken();
            NodeToken newTk = errorInfo.getToken();
            if (newTk == null || !newTk.equals((Object)oldTk)) continue;
            return;
        }
        this.errors.add(errorInfo);
    }

    private boolean hasNodeState(IASTNode node) {
        return node instanceof FunctionNode || node instanceof SetNode || node instanceof ParserAssistant.SquareBracketNode;
    }

    private ArrayData createArrayData() throws SyntaxException {
        ArrayData data;
        int baseType = this.checkArray();
        if (this.array_item.size() == 1) {
            data = new ArrayData(baseType, this.array_item.get(0).size());
            int i = 0;
            for (IASTNode node : this.array_item.get(0)) {
                data.set(i++, node.evaluate(this.context));
            }
        } else {
            data = new ArrayData(baseType, this.array_item.get(0).size(), this.array_item.size());
            int i = 0;
            for (int j = 0; j < this.array_item.size(); ++j) {
                i = 0;
                for (IASTNode node : this.array_item.get(j)) {
                    data.set(i++, j, node.evaluate(this.context));
                }
            }
        }
        return data;
    }

    private int checkArray() throws SyntaxException {
        int baseType = this.array_item.get(0).get(0).getType(this.context);
        int length = this.array_item.get(0).size();
        for (int i = 0; i < this.array_item.size(); ++i) {
            if (length != this.array_item.get(i).size()) {
                throw new SyntaxException("\u6570\u7ec4\u8fd0\u7b97\u65f6\u7ef4\u5ea6\u4e0d\u5339\u914d\uff01");
            }
            for (IASTNode node : this.array_item.get(i)) {
                if (baseType == node.getType(this.context)) continue;
                throw new SyntaxException("\u6570\u7ec4\u4e2d\u51fa\u73b0\u591a\u79cd\u57fa\u672c\u7684\u6570\u636e\u7c7b\u578b");
            }
        }
        return baseType;
    }

    private int tryParseFormula(String formula, int index) throws SyntaxException {
        switch (formula.charAt(index)) {
            case '-': 
            case '/': {
                index = this.skipComment(formula, index);
                break;
            }
            case '\'': {
                index = this.findChar(formula, '\'', index + 1);
                if (index != -1) break;
                throw new SyntaxException("\u8bed\u6cd5\u6709\u8bef\uff0c\u8868\u8fbe\u5f0f\u7ed3\u5c3e\u7f3a\u5c11\u5355\u5f15\u53f7");
            }
            case '\"': {
                index = this.findChar(formula, '\"', index + 1);
                if (index != -1) break;
                throw new SyntaxException("\u8bed\u6cd5\u6709\u8bef\uff0c\u8868\u8fbe\u5f0f\u7ed3\u5c3e\u7f3a\u5c11\u53cc\u5f15\u53f7");
            }
            case '\t': 
            case '\n': 
            case ' ': {
                return this.tryParseFrom(formula, index) ? formula.length() : index;
            }
        }
        return index;
    }

    private boolean tryParseFrom(String formula, int index) throws SyntaxException {
        char next = formula.charAt(index + 1);
        if (next == 'f' || next == 'F') {
            int pos = this.skipFromKey(formula, index + 1);
            if ((pos = this.skipComment(formula, pos)) > index + 1) {
                List<Token> fromList = this.getFromList(formula, pos + 1);
                if (this.context instanceof IScriptContext) {
                    this.notifyBlock(ScriptBlock.FROM, 1);
                }
                this.fromNodeMaps.clear();
                for (Token cube : fromList) {
                    String txt = cube.text();
                    String[] objPath = txt.split("\\.");
                    IASTNode cubeNode = objPath.length > 1 ? this.parser.produceAsObject(this.context, cube, Arrays.asList(objPath)) : this.parser.produceAsKPI(this.context, cube, null, txt, null);
                    this.fromNodeMaps.put(cube.text(), cubeNode);
                }
                if (this.context instanceof IScriptContext) {
                    this.notifyBlock(ScriptBlock.FROM, 2);
                }
                return true;
            }
        }
        return false;
    }

    private int skipComment(String formula, int index) {
        boolean isMultiComment;
        char next;
        char cur = formula.charAt(index);
        boolean isSingleComment = cur == (next = formula.charAt(index + 1)) && (cur == '/' || cur == '-');
        boolean bl = isMultiComment = cur == '/' && next == '*';
        if (isSingleComment) {
            if ((index = this.findChar(formula, '\n', index + 2)) == -1) {
                return formula.length();
            }
            --index;
        } else if (isMultiComment) {
            return this.skipMultiComment(formula, index + 2);
        }
        return index;
    }

    private int findChar(String formula, char c, int start) {
        for (int i = start; i < formula.length(); ++i) {
            if (formula.charAt(i) != c) continue;
            return i;
        }
        return -1;
    }

    private int skipMultiComment(String formula, int start) {
        for (int i = start; i < formula.length() - 1; ++i) {
            if (formula.charAt(i) != '*' || formula.charAt(i + 1) != '/') continue;
            return i + 1;
        }
        return start;
    }

    private int skipFromKey(String formula, int start) {
        if (start >= formula.length() - 4) {
            return start;
        }
        String str = formula.substring(start, start + 4);
        if (!str.equalsIgnoreCase("from")) {
            return start;
        }
        char c = formula.charAt(start + 4);
        if (this.isBlank(c) || this.isCommentStartPos(formula, start + 4)) {
            return start + 4;
        }
        return start;
    }

    private boolean isBlank(char c) {
        return c == ' ' || c == '\n' || c == '\t' || c == '\r';
    }

    private boolean isSeparator(char c) {
        return c == ',' || c == ';' || this.isBlank(c);
    }

    private boolean isCommentStartPos(String formula, int pos) {
        char next;
        if (pos > formula.length() - 2) {
            return false;
        }
        char cur = formula.charAt(pos);
        boolean isSingleComment = cur == (next = formula.charAt(pos + 1)) && (cur == '/' || cur == '-');
        boolean isMultiComment = cur == '/' && next == '*';
        return isSingleComment || isMultiComment;
    }

    private List<Token> getFromList(String formula, int start) {
        Token token;
        int pos;
        int last = start;
        ArrayList<Token> cubeList = new ArrayList<Token>();
        for (pos = start; pos <= formula.length() - 1; ++pos) {
            String val;
            char c = formula.charAt(pos);
            if (this.isCommentStartPos(formula, pos)) {
                Token token2 = this.getToken(formula, last, pos);
                if (token2 != null) {
                    cubeList.add(token2);
                    last = pos + 1;
                }
                pos = this.skipComment(formula, pos);
                last = pos + 1;
                continue;
            }
            if (!this.isSeparator(c) || (val = formula.substring(last, pos).trim()).length() <= 0) continue;
            Token token3 = this.getToken(formula, last, pos);
            if (token3 == null) break;
            cubeList.add(token3);
            last = pos + 1;
        }
        if (pos > last && (token = this.getToken(formula, last, pos)) != null) {
            cubeList.add(token);
        }
        return cubeList;
    }

    private Token getToken(String formula, int start, int end) {
        if (start >= end || end > formula.length()) {
            return null;
        }
        String val = formula.substring(start, end).trim();
        if (val.length() > 0 && this.isCubeStr(val)) {
            return this.calcToken(formula, val, start);
        }
        return null;
    }

    private boolean isCubeStr(String str) {
        return !(str = str.toUpperCase()).equals("FILTER") && !str.equals("WHERE") && !str.equals("SLICE") && !str.equals("ORDER") && !str.equals("OPTIONS");
    }

    private Token calcToken(String source, String tokenStr, int startIndex) {
        int pos = 0;
        int line = 1;
        String lineSepor = StringUtils.LINE_SEPARATOR;
        if (lineSepor == null) {
            lineSepor = "\n";
        }
        while (pos < startIndex) {
            if (source.startsWith(lineSepor, pos)) {
                pos += lineSepor.length();
                ++line;
                continue;
            }
            ++pos;
        }
        int col = startIndex - pos;
        return new Token(tokenStr, line, col, startIndex);
    }

    private Token calExprEndToken(String source) {
        int pos = 0;
        int line = 1;
        int last = 0;
        String lineSepor = StringUtils.LINE_SEPARATOR;
        if (lineSepor == null) {
            lineSepor = "\n";
        }
        while (pos < source.length()) {
            if (source.startsWith(lineSepor, pos)) {
                ++line;
                last = pos += lineSepor.length();
                continue;
            }
            ++pos;
        }
        return new Token(source, line, pos - last, pos);
    }

    private Token transToken(org.antlr.runtime.Token token) {
        if (token == null) {
            return new Token(null, 0, 0, 0);
        }
        int index = token.getTokenIndex();
        if (token instanceof CommonToken) {
            index = ((CommonToken)token).getStartIndex();
        }
        return new Token(token.getText(), token.getLine(), token.getCharPositionInLine(), index);
    }

    private static String unicode2String(String str) throws Exception {
        StringBuilder buf = new StringBuilder();
        int i = -1;
        int pos = 0;
        do {
            if ((i = str.indexOf("\\u", pos)) == -1) {
                buf.append(str.substring(pos));
                break;
            }
            if (i + 5 < str.length()) {
                buf.append(str.substring(pos, i));
                pos = i + 6;
                try {
                    char c = (char)Integer.parseInt(str.substring(i + 2, i + 6), 16);
                    buf.append(c);
                }
                catch (NumberFormatException e) {
                    throw new Exception("\u975e\u6cd5\u7684unicode\u5b57\u7b26");
                }
            } else {
                throw new Exception("\u975e\u6cd5\u7684unicode\u5b57\u7b26");
            }
        } while (pos != str.length());
        return buf.toString();
    }

    private NodeToken convert(Token token) {
        return new NodeToken(token.text(), token.line(), token.column(), token.index());
    }

    public static void main(String[] args) throws Exception {
        String s = "\\u0056";
        s = BaseANTLRParser.unicode2String(s);
        System.out.println(s);
    }

    static {
        tNames.put("EQ", "=");
        tNames.put("GT", ">");
        tNames.put("GE", ">=");
        tNames.put("LT", "<");
        tNames.put("LE", "<=");
        tNames.put("NE", "!=");
        tNames.put("PLUS", "+");
        tNames.put("MINUS", "-");
        tNames.put("LINK", "&");
        tNames.put("MULTI", "\u00d7");
        tNames.put("DIVIDE", "\u00f7");
        tNames.put("POWER", "\u4e58\u65b9");
        tNames.put("LBRACKET", "[");
        tNames.put("RBRACKET", "]");
        tNames.put("COMMA", "\u9017\u53f7");
        tNames.put("COLON", "\u5192\u53f7");
        tNames.put("POINT", "\u70b9");
        tNames.put("LBRACE", "{");
        tNames.put("RBRACE", "}");
        tNames.put("SEMICOLON", ";");
        tNames.put("LPAREN", "(");
        tNames.put("RPAREN", ")");
        tNames.put("WS", "\u7a7a\u683c");
        tNames.put("QUOTE", "\u5355\u5f15\u53f7");
        tNames.put("DOUBLEQUOTE", "\u53cc\u5f15\u53f7");
        tNames.put("'<EOF>'", "\u8868\u8fbe\u5f0f\u672b\u5c3e");
    }

    private static final class CellPosItem {
        public int pos;
        public int offset;
        public double multiply;

        private CellPosItem() {
        }
    }
}

