/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Expression
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.cell.CellExcpetion
 *  com.jiuqi.bi.syntax.cell.CompatiblePosition
 *  com.jiuqi.bi.syntax.cell.ICellProvider
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.external.DateParser
 *  com.jiuqi.bi.syntax.external.IExternalParser
 *  com.jiuqi.bi.syntax.function.FunctionException
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IFunctionNodeProvider
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.operator.Divide
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.operator.LessThan
 *  com.jiuqi.bi.syntax.operator.LessThanOrEqual
 *  com.jiuqi.bi.syntax.operator.LinkPlus
 *  com.jiuqi.bi.syntax.operator.Minus
 *  com.jiuqi.bi.syntax.operator.MoreThan
 *  com.jiuqi.bi.syntax.operator.MoreThanOrEqual
 *  com.jiuqi.bi.syntax.operator.Multiply
 *  com.jiuqi.bi.syntax.operator.Negative
 *  com.jiuqi.bi.syntax.operator.NotEqual
 *  com.jiuqi.bi.syntax.operator.OperatorNode
 *  com.jiuqi.bi.syntax.operator.Plus
 *  com.jiuqi.bi.syntax.operator.Positive
 *  com.jiuqi.bi.syntax.operator.Power
 *  com.jiuqi.bi.syntax.parser.ErrorInfo
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.IParseListener
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.IReportCellProvider
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.syntax.reportparser.IReportWorkSheet
 *  com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider
 *  com.jiuqi.bi.syntax.script.IScriptContext
 *  com.jiuqi.bi.syntax.script.ScriptNode
 *  com.jiuqi.bi.util.StringUtils
 *  org.antlr.runtime.ANTLRStringStream
 *  org.antlr.runtime.CharStream
 *  org.antlr.runtime.CommonTokenStream
 *  org.antlr.runtime.TokenSource
 *  org.antlr.runtime.TokenStream
 */
package com.jiuqi.np.dataengine.parse.link;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Expression;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.cell.CellExcpetion;
import com.jiuqi.bi.syntax.cell.CompatiblePosition;
import com.jiuqi.bi.syntax.cell.ICellProvider;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.external.DateParser;
import com.jiuqi.bi.syntax.external.IExternalParser;
import com.jiuqi.bi.syntax.function.FunctionException;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IFunctionNodeProvider;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.operator.Divide;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.operator.LessThan;
import com.jiuqi.bi.syntax.operator.LessThanOrEqual;
import com.jiuqi.bi.syntax.operator.LinkPlus;
import com.jiuqi.bi.syntax.operator.Minus;
import com.jiuqi.bi.syntax.operator.MoreThan;
import com.jiuqi.bi.syntax.operator.MoreThanOrEqual;
import com.jiuqi.bi.syntax.operator.Multiply;
import com.jiuqi.bi.syntax.operator.Negative;
import com.jiuqi.bi.syntax.operator.NotEqual;
import com.jiuqi.bi.syntax.operator.OperatorNode;
import com.jiuqi.bi.syntax.operator.Plus;
import com.jiuqi.bi.syntax.operator.Positive;
import com.jiuqi.bi.syntax.operator.Power;
import com.jiuqi.bi.syntax.parser.ErrorInfo;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.IParseListener;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.IReportCellProvider;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.syntax.reportparser.IReportWorkSheet;
import com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider;
import com.jiuqi.bi.syntax.script.IScriptContext;
import com.jiuqi.bi.syntax.script.ScriptNode;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.parse.link.ANTLRLexer;
import com.jiuqi.np.dataengine.parse.link.ANTLRParser;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.TokenSource;
import org.antlr.runtime.TokenStream;

public class ReportFormulaParser_link {
    private List<IFunctionProvider> funcProviders = new ArrayList<IFunctionProvider>();
    private List<IReportCellProvider> cellProviders;
    private List<IReportDynamicNodeProvider> dynNodeProviders;
    private List<IExternalParser> externalParsers;
    private List<IParseListener> parseListeners;
    private boolean fastMode = false;
    private boolean excelMode = true;
    private int options = 0;

    public static ReportFormulaParser_link getInstance() {
        return new ReportFormulaParser_link();
    }

    public ReportFormulaParser_link() {
        this.registerFunctionProvider((IFunctionProvider)ReportFunctionProvider.GLOBAL_PROVIDER);
        this.cellProviders = new ArrayList<IReportCellProvider>();
        this.dynNodeProviders = new ArrayList<IReportDynamicNodeProvider>();
        this.externalParsers = new ArrayList<IExternalParser>();
        this.registerExternalParser((IExternalParser)DateParser.INSTANCE);
        this.parseListeners = new ArrayList<IParseListener>();
    }

    public void registerFunctionProvider(IFunctionProvider provider) {
        if (!this.funcProviders.contains(provider)) {
            this.funcProviders.add(0, provider);
        }
    }

    public void unregisterFunctionProvider(IFunctionProvider provider) {
        this.funcProviders.remove(provider);
    }

    public Set<IFunction> allFunctions() {
        TreeSet<IFunction> functions = new TreeSet<IFunction>(new Comparator<IFunction>(){

            @Override
            public int compare(IFunction o1, IFunction o2) {
                String g2;
                String g1 = o1.category() == null ? "" : o1.category();
                int c = g1.compareToIgnoreCase(g2 = o2.category() == null ? "" : o2.category());
                if (c != 0) {
                    return c;
                }
                return o1.name().compareToIgnoreCase(o2.name());
            }
        });
        HashSet<String> names = new HashSet<String>();
        for (IFunctionProvider provider : this.funcProviders) {
            for (IFunction func : provider) {
                if (names.contains(func.name().toUpperCase())) continue;
                functions.add(func);
                names.add(func.name().toUpperCase());
                if (func.aliases() == null) continue;
                for (String alias : func.aliases()) {
                    names.add(alias.toUpperCase());
                }
            }
        }
        return functions;
    }

    public Set<OperatorNode> allExcelOperators() {
        TreeSet<OperatorNode> operatorNodes = new TreeSet<OperatorNode>(new Comparator<OperatorNode>(){

            @Override
            public int compare(OperatorNode o1, OperatorNode o2) {
                String g2;
                String g1 = o1.sign() == null ? "" : o1.sign();
                int c = g1.compareToIgnoreCase(g2 = o2.sign() == null ? "" : o2.sign());
                if (c != 0) {
                    return c;
                }
                return new Integer(o1.level()).compareTo(new Integer(o2.level()));
            }
        });
        operatorNodes.add((OperatorNode)new Divide());
        operatorNodes.add((OperatorNode)new Equal());
        operatorNodes.add((OperatorNode)new LessThan());
        operatorNodes.add((OperatorNode)new LessThanOrEqual());
        operatorNodes.add((OperatorNode)new LinkPlus());
        operatorNodes.add((OperatorNode)new Minus());
        operatorNodes.add((OperatorNode)new MoreThan());
        operatorNodes.add((OperatorNode)new MoreThanOrEqual());
        operatorNodes.add((OperatorNode)new Multiply());
        operatorNodes.add((OperatorNode)new Negative());
        operatorNodes.add((OperatorNode)new NotEqual());
        operatorNodes.add((OperatorNode)new Plus());
        operatorNodes.add((OperatorNode)new Positive());
        operatorNodes.add((OperatorNode)new Power());
        return operatorNodes;
    }

    public void registerCellProvider(IReportCellProvider provider) {
        if (!this.cellProviders.contains(provider)) {
            this.cellProviders.add(0, provider);
        }
    }

    public void unregisterCellProvider(ICellProvider provider) {
        this.cellProviders.remove(provider);
    }

    public void registerDynamicNodeProvider(IReportDynamicNodeProvider provider) {
        if (!this.dynNodeProviders.contains(provider)) {
            this.dynNodeProviders.add(provider);
        }
    }

    public void unregisterDynamicNodeProvider(IDynamicNodeProvider provider) {
        this.dynNodeProviders.remove(provider);
    }

    public void registerExternalParser(IExternalParser parser) {
        if (!this.externalParsers.contains(parser)) {
            this.externalParsers.add(0, parser);
        }
    }

    public void unregisterExternalParse(IExternalParser parser) {
        this.externalParsers.remove(parser);
    }

    public void addParseListener(IParseListener listener) {
        if (!this.parseListeners.contains(listener)) {
            this.parseListeners.add(listener);
        }
    }

    public void removeParseListener(IParseListener listener) {
        this.parseListeners.remove(listener);
    }

    public boolean isFastMode() {
        return this.fastMode;
    }

    public void setFastMode(boolean fastMode) {
        this.fastMode = fastMode;
    }

    public boolean isExcelMode() {
        return this.excelMode;
    }

    public void setExcelMode(boolean excelMode) {
        this.excelMode = excelMode;
    }

    public boolean isJQReportMode() {
        return (this.options & 1) == 1;
    }

    public void setJQReportMode(boolean reportMode) {
        this.options = reportMode ? (this.options |= 1) : (this.options &= 0x1111110);
    }

    public IExpression parseEval(String formula, IContext context) throws ParseException {
        return this.parse(context, formula);
    }

    public IExpression parseCond(String formula, IContext context) throws ParseException {
        IExpression cond = this.parseEval(formula, context);
        try {
            if (cond.getType(context) != 1) {
                if (this.isArrayCond(context, cond)) {
                    throw new ParseException("\u89e3\u6790\u6761\u4ef6\u516c\u5f0f\u5931\u8d25\uff0c\u65e0\u6cd5\u5bf9\u591a\u503c\u5217\u8868\u8fdb\u884c\u7b49\u4e8e\u6761\u4ef6\u5224\u65ad\uff01");
                }
                throw new ParseException("\u89e3\u6790\u6761\u4ef6\u516c\u5f0f\u5931\u8d25\uff0c\u8868\u8fbe\u5f0f\u4e0d\u662f\u6709\u6548\u7684\u903b\u8f91\u5224\u65ad\u8868\u8fbe\u5f0f\uff01");
            }
        }
        catch (SyntaxException e) {
            throw new ParseException((Throwable)e);
        }
        return cond;
    }

    private boolean isArrayCond(IContext context, IExpression cond) {
        if (!(cond.getChild(0) instanceof Equal)) {
            return false;
        }
        Equal equal = (Equal)cond.getChild(0);
        try {
            return equal.getChild(1).getType(context) == 11 || equal.getChild(0).getType(context) == 11;
        }
        catch (SyntaxException e) {
            return false;
        }
    }

    public IExpression parseAssign(String formula, IContext context) throws ParseException {
        IExpression expr = this.parse(context, formula);
        return expr;
    }

    public ScriptNode parseScript(IScriptContext context, String formula) throws ParseException {
        CommonTokenStream stream = new CommonTokenStream();
        ANTLRLexer lexer = new ANTLRLexer();
        lexer.setCharStream((CharStream)new ANTLRStringStream(formula));
        stream.setTokenSource((TokenSource)lexer);
        ANTLRParser parser = new ANTLRParser((TokenStream)stream);
        parser.setFormulaParser(this);
        parser.setContext((IContext)context);
        ScriptNode scriptNode = null;
        try {
            parser.preProcessFromBlockForScript(formula);
            scriptNode = parser.getScriptTree();
            if (scriptNode != null && !this.fastMode) {
                scriptNode.validate(context);
            }
        }
        catch (SyntaxException e) {
            ParseException ex = new ParseException((Throwable)e);
            ex.setDetails(new ArrayList<ErrorInfo>(parser.errorParserTokenSet()));
            throw ex;
        }
        List<ErrorInfo> lexerErrors = lexer.errorParserTokenSet();
        if (!lexerErrors.isEmpty()) {
            ParseException ex = new ParseException("\u8868\u8fbe\u5f0f\u7684\u8bcd\u6cd5\u5b58\u5728\u9519\u8bef\uff01");
            ex.setDetails(new ArrayList<ErrorInfo>(lexerErrors));
            throw new ParseException((Throwable)ex);
        }
        if (scriptNode == null) {
            throw new ParseException("\u8bed\u6cd5\u89e3\u6790\u5931\u8d25\uff0c\u65e0\u6cd5\u751f\u6210\u8bed\u6cd5\u6811\uff01");
        }
        return scriptNode;
    }

    public Set<IASTNode> parseFromBlockForScript(IScriptContext context, String formula) throws ParseException {
        CommonTokenStream stream = new CommonTokenStream();
        ANTLRLexer lexer = new ANTLRLexer();
        lexer.setCharStream((CharStream)new ANTLRStringStream(formula));
        stream.setTokenSource((TokenSource)lexer);
        ANTLRParser parser = new ANTLRParser((TokenStream)stream);
        parser.setFormulaParser(this);
        parser.setContext((IContext)context);
        try {
            return parser.preProcessFromBlockForScript(formula);
        }
        catch (SyntaxException e) {
            ParseException ex = new ParseException(e.getMessage());
            ex.setStackTrace(e.getStackTrace());
            ex.setDetails(new ArrayList<ErrorInfo>(parser.errorParserTokenSet()));
            throw new ParseException((Throwable)ex);
        }
    }

    protected IExpression parse(IContext context, String formula) throws ParseException {
        CommonTokenStream stream = new CommonTokenStream();
        ANTLRLexer lexer = new ANTLRLexer();
        lexer.setCharStream((CharStream)new ANTLRStringStream(formula));
        stream.setTokenSource((TokenSource)lexer);
        ANTLRParser parser = new ANTLRParser((TokenStream)stream);
        parser.setFormulaParser(this);
        parser.setCompatibleOldExcel(this.isJQReportMode());
        parser.setContext(context);
        IASTNode root = null;
        try {
            root = parser.getTree();
            if (root != null && !this.fastMode) {
                root.validate(context);
            }
        }
        catch (SyntaxException e) {
            ParseException ex = new ParseException(e.getMessage(), (Throwable)e);
            ex.setDetails(new ArrayList<ErrorInfo>(parser.errorParserTokenSet()));
            throw new ParseException((Throwable)ex);
        }
        List<ErrorInfo> lexerErrors = lexer.errorParserTokenSet();
        if (!lexerErrors.isEmpty()) {
            ParseException ex = new ParseException("\u8868\u8fbe\u5f0f\u7684\u8bcd\u6cd5\u5b58\u5728\u9519\u8bef\uff01");
            ex.setDetails(new ArrayList<ErrorInfo>(lexerErrors));
            throw new ParseException((Throwable)ex);
        }
        if (root == null) {
            throw new ParseException("\u8bed\u6cd5\u89e3\u6790\u5931\u8d25\uff0c\u65e0\u6cd5\u751f\u6210\u8bed\u6cd5\u6811\uff01");
        }
        Expression expr = new Expression(formula, root);
        if (!parser.getWildcardRanges().isEmpty()) {
            expr.getWildcardRanges().addAll(parser.getWildcardRanges());
        }
        return expr;
    }

    public static void main(String[] args) throws SyntaxException {
        ReportFormulaParser_link p = ReportFormulaParser_link.getInstance();
        p.parseEval("-2", null);
    }

    final IASTNode produceAsFunction(IContext context, Token token, String funcName, List<IASTNode> parameters) throws SyntaxException {
        for (IFunctionProvider provider : this.funcProviders) {
            IFunction func = provider.find(context, funcName);
            if (func == null) continue;
            if (provider instanceof IFunctionNodeProvider) {
                return ((IFunctionNodeProvider)provider).createNode(context, token, func, parameters);
            }
            return new FunctionNode(token, func, parameters);
        }
        throw new FunctionException(token, "\u51fd\u6570\u4e0d\u5b58\u5728\uff01");
    }

    final IASTNode produceAsCell(IContext context, Token token, String sheetName, String cell) throws SyntaxException {
        Position pos;
        if ((!this.isJQReportMode() || cell.contains(",")) && (pos = Position.tryParse((String)cell)) != null && pos.isValidate()) {
            int cellOpts = 0;
            if (!StringUtils.isEmpty((String)sheetName)) {
                cellOpts |= 1;
            }
            for (ICellProvider iCellProvider : this.cellProviders) {
                IASTNode cellNode;
                IWorksheet worksheet = sheetName == null || sheetName.length() == 0 ? iCellProvider.activeWorksheet(context) : iCellProvider.find(context, sheetName);
                if (worksheet == null || (cellNode = worksheet.findCell(context, token, pos, cellOpts)) == null) continue;
                return cellNode;
            }
        }
        if (this.isJQReportMode() && (sheetName == null || sheetName.length() == 0)) {
            for (IReportDynamicNodeProvider provider : this.dynNodeProviders) {
                IASTNode kpi = provider.findSpecial(context, token, cell);
                if (kpi == null) continue;
                return kpi;
            }
        }
        throw new DynamicNodeException("\u6839\u636e groupName:" + sheetName + ",itemName:" + cell + "\u6ca1\u6709\u67e5\u627e\u5230\u6307\u6807\u6216\u8005\u5355\u5143\u683c\u5bf9\u8c61");
    }

    final IASTNode produceAsCell(IContext context, Token token, String sheetName, CompatiblePosition pos, List<IASTNode> restrictions) throws SyntaxException {
        int cellOpts = 2;
        if (!StringUtils.isEmpty((String)sheetName)) {
            cellOpts |= 1;
        }
        for (IReportCellProvider provider : this.cellProviders) {
            IASTNode cellNode;
            IWorksheet worksheet;
            String groupKey = null;
            if (restrictions != null) {
                for (IASTNode restriction : restrictions) {
                    String text;
                    if (!(restriction instanceof DataNode) || !(text = (String)((DataNode)restriction).evaluate(context)).startsWith("@")) continue;
                    groupKey = text.substring(1, text.length());
                }
            }
            if ((worksheet = sheetName == null || sheetName.length() == 0 ? provider.activeWorksheet(context, groupKey) : provider.find(context, groupKey, sheetName)) == null || (cellNode = worksheet.findCell(context, token, pos, cellOpts, restrictions)) == null) continue;
            return cellNode;
        }
        String cellExp = this.getCellExp(sheetName, pos.getPosition());
        throw new CellExcpetion("\u5355\u5143\u683c " + cellExp + " \u4e0d\u5b58\u5728");
    }

    final IASTNode produceAsRegion(IContext context, Token token, String sheetName, String startCell, String endCell) throws SyntaxException {
        int cellOpts = StringUtils.isEmpty((String)sheetName) ? 0 : 1;
        for (IReportCellProvider provider : this.cellProviders) {
            IASTNode regionNode;
            IReportWorkSheet worksheet = sheetName == null || sheetName.length() == 0 ? (IReportWorkSheet)provider.activeWorksheet(context) : (IReportWorkSheet)provider.find(context, sheetName);
            if (worksheet == null || (regionNode = worksheet.findRegion(context, token, startCell, endCell, cellOpts)) == null) continue;
            return regionNode;
        }
        throw new CellExcpetion(token, "\u5355\u5143\u683c\u533a\u57df\u4e0d\u5b58\u5728\u6216\u6570\u636e\u8303\u56f4\u65e0\u6548\uff01");
    }

    final IASTNode produceAsKPI(IContext context, Token token, String tableName, String kpiName, List<IASTNode> specExprs) throws SyntaxException {
        for (IDynamicNodeProvider iDynamicNodeProvider : this.dynNodeProviders) {
            ArrayList<String> objPath;
            IASTNode kpi = null;
            if (specExprs == null || specExprs.size() == 0) {
                if (tableName == null) {
                    kpi = iDynamicNodeProvider.find(context, token, kpiName);
                } else {
                    objPath = new ArrayList<String>(2);
                    objPath.add(tableName);
                    objPath.add(kpiName);
                    kpi = iDynamicNodeProvider.find(context, token, objPath);
                }
            } else {
                objPath = new ArrayList(2);
                objPath.add(tableName);
                objPath.add(kpiName);
                kpi = iDynamicNodeProvider.findRestrict(context, token, objPath, specExprs);
            }
            if (kpi == null) continue;
            return kpi;
        }
        StringBuilder msg = new StringBuilder();
        msg.append("\u6839\u636e groupName:").append(tableName).append(",itemName:").append(kpiName).append("\u6ca1\u6709\u67e5\u627e\u5230\u6307\u6807\u6216\u8005\u5355\u5143\u683c\u5bf9\u8c61");
        throw new DynamicNodeException(msg.toString());
    }

    /*
     * WARNING - void declaration
     */
    final IASTNode produceAsRestrict(IContext context, Token token, List<String> objPath, List<IASTNode> restrictItems) throws SyntaxException {
        void var6_9;
        for (IDynamicNodeProvider iDynamicNodeProvider : this.dynNodeProviders) {
            IASTNode restrict = null;
            if (restrictItems == null || restrictItems.size() == 0) {
                if (objPath != null) {
                    restrict = objPath.size() == 1 ? iDynamicNodeProvider.find(context, token, objPath.get(0)) : iDynamicNodeProvider.findRestrict(context, token, objPath, restrictItems);
                }
            } else {
                restrict = iDynamicNodeProvider.findRestrict(context, token, objPath, restrictItems);
            }
            if (restrict == null) continue;
            return restrict;
        }
        String itemName = null;
        Object var6_7 = null;
        if (objPath.size() == 1) {
            itemName = objPath.get(0);
        } else if (objPath.size() == 2) {
            String string = objPath.get(0);
            itemName = objPath.get(1);
        }
        StringBuilder msg = new StringBuilder();
        msg.append("\u6839\u636e groupName:").append((String)var6_9).append(",itemName:").append(itemName).append("\u6ca1\u6709\u67e5\u627e\u5230\u6307\u6807\u6216\u8005\u5355\u5143\u683c\u5bf9\u8c61");
        throw new DynamicNodeException(msg.toString());
    }

    /*
     * WARNING - void declaration
     */
    final IASTNode produceAsObject(IContext context, Token token, List<String> objPath) throws SyntaxException {
        void var5_8;
        for (IDynamicNodeProvider iDynamicNodeProvider : this.dynNodeProviders) {
            IASTNode ref = iDynamicNodeProvider.find(context, token, objPath);
            if (ref == null) continue;
            return ref;
        }
        String itemName = null;
        Object var5_6 = null;
        if (objPath != null) {
            if (objPath.size() == 1) {
                itemName = objPath.get(0);
            } else if (objPath.size() == 2) {
                String string = objPath.get(0);
                itemName = objPath.get(1);
            }
        }
        StringBuilder msg = new StringBuilder();
        msg.append("\u6839\u636e groupName:").append((String)var5_8).append(",itemName:").append(itemName).append("\u6ca1\u6709\u67e5\u627e\u5230\u6307\u6807\u6216\u8005\u5355\u5143\u683c\u5bf9\u8c61");
        throw new DynamicNodeException(msg.toString());
    }

    final IASTNode externalParse(IContext context, Token token, String expression) throws SyntaxException {
        for (IExternalParser parser : this.externalParsers) {
            IASTNode node = parser.parse(context, expression, token);
            if (node == null) continue;
            return node;
        }
        return new DataNode(token, expression);
    }

    final List<IParseListener> getParseListener() {
        return this.parseListeners;
    }

    private String getCellExp(String sheetName, Position position) {
        String cellExp;
        String string = cellExp = sheetName == null ? "" : sheetName;
        if (this.isJQReportMode()) {
            cellExp = position.isRowWildcard() ? cellExp + "[*" : cellExp + "[" + position.row();
            cellExp = position.isColWildcard() ? cellExp + ",*]" : cellExp + "," + position.col() + "]";
        } else {
            cellExp = cellExp + "!" + position;
        }
        return cellExp;
    }
}

