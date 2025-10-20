/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.parameter.engine.EnhancedParameterEnvAdapter
 *  com.jiuqi.bi.parameter.engine.IParameterEnv
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTIterator
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.cell.ICellNode
 *  com.jiuqi.bi.syntax.cell.ICellProvider
 *  com.jiuqi.bi.syntax.cell.IRegionNode
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.env.GetEnv
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.FunctionProvider
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.function.text.MoneyStr
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.operator.In
 *  com.jiuqi.bi.syntax.parser.FormulaParser
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.IParseListener
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 *  com.jiuqi.nvwa.framework.parameter.syntax.ParamNode
 *  com.jiuqi.nvwa.framework.parameter.syntax.ParamNodeProvider
 *  com.jiuqi.nvwa.framework.parameter.syntax.ParamSyntax
 */
package com.jiuqi.bi.quickreport.engine.parser;

import com.jiuqi.bi.parameter.engine.EnhancedParameterEnvAdapter;
import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.IReportParser;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.parser.adapter.CountIFSAdapter;
import com.jiuqi.bi.quickreport.engine.parser.adapter.HRegionLookUpAdapter;
import com.jiuqi.bi.quickreport.engine.parser.adapter.IFSAdapter;
import com.jiuqi.bi.quickreport.engine.parser.adapter.SumAdapter;
import com.jiuqi.bi.quickreport.engine.parser.adapter.VRegionLookUpAdapter;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DataSetNode;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DataSetNodeProvider;
import com.jiuqi.bi.quickreport.engine.parser.dataset.ReportParamNodeProvider;
import com.jiuqi.bi.quickreport.engine.parser.function.COLUMN;
import com.jiuqi.bi.quickreport.engine.parser.function.DataSetFunctionListener;
import com.jiuqi.bi.quickreport.engine.parser.function.DataSetFunctionProvider;
import com.jiuqi.bi.quickreport.engine.parser.function.Lag;
import com.jiuqi.bi.quickreport.engine.parser.function.Lead;
import com.jiuqi.bi.quickreport.engine.parser.function.MOM;
import com.jiuqi.bi.quickreport.engine.parser.function.NewSerialAdapter;
import com.jiuqi.bi.quickreport.engine.parser.function.Q_CurrentValue;
import com.jiuqi.bi.quickreport.engine.parser.function.Q_Eval;
import com.jiuqi.bi.quickreport.engine.parser.function.Q_IsLastLevel;
import com.jiuqi.bi.quickreport.engine.parser.function.Q_LevelOf;
import com.jiuqi.bi.quickreport.engine.parser.function.Q_LinkOf;
import com.jiuqi.bi.quickreport.engine.parser.function.Q_RankOf;
import com.jiuqi.bi.quickreport.engine.parser.function.ROW;
import com.jiuqi.bi.quickreport.engine.parser.function.YOY;
import com.jiuqi.bi.quickreport.engine.parser.restriction.RestrictionNodeProvider;
import com.jiuqi.bi.quickreport.engine.parser.variable.VariableNodeProvider;
import com.jiuqi.bi.quickreport.model.ParameterInfo;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTIterator;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.cell.ICellNode;
import com.jiuqi.bi.syntax.cell.ICellProvider;
import com.jiuqi.bi.syntax.cell.IRegionNode;
import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.env.GetEnv;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.FunctionProvider;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.function.text.MoneyStr;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.operator.In;
import com.jiuqi.bi.syntax.parser.FormulaParser;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.IParseListener;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import com.jiuqi.nvwa.framework.parameter.syntax.ParamNode;
import com.jiuqi.nvwa.framework.parameter.syntax.ParamNodeProvider;
import com.jiuqi.nvwa.framework.parameter.syntax.ParamSyntax;
import java.util.ListIterator;

public class ReportParser
implements IReportParser {
    private IContext context;
    private FormulaParser parser;
    private ReportParamNodeProvider paramProvider;
    private IParseListener paramListener;
    private ICellProvider workbook;
    private boolean hasMultiValueParams;
    private static final FunctionProvider STYLE_PROVIDER = new FunctionProvider();

    public ReportParser(IContext context) {
        this.context = context;
        this.parser = new FormulaParser();
        this.hasMultiValueParams = context instanceof ReportContext ? this.scanParams((ReportContext)context) : false;
        this.parser.registerDynamicNodeProvider((IDynamicNodeProvider)new RestrictionNodeProvider());
        this.parser.registerDynamicNodeProvider((IDynamicNodeProvider)new VariableNodeProvider());
        this.parser.registerDynamicNodeProvider((IDynamicNodeProvider)new DataSetNodeProvider());
        this.parser.registerFunctionProvider((IFunctionProvider)FunctionProvider.EXTERNAL_PROVIDER);
        this.parser.registerFunctionProvider((IFunctionProvider)FunctionProvider.MATRIX_PROVIDER);
        this.parser.registerFunctionProvider((IFunctionProvider)FunctionProvider.STAT_PROVIDER);
        this.parser.registerFunctionProvider((IFunctionProvider)FunctionProvider.LOOKUP_PROVIDER);
        this.parser.registerFunctionProvider((IFunctionProvider)FunctionProvider.INFO_PROVIDER);
        this.parser.registerFunctionProvider((IFunctionProvider)FunctionProvider.REFERENCE_PROVIDER);
        this.parser.registerFunctionProvider((IFunctionProvider)FunctionProvider.TRIGONOMETRIC_PROVIDER);
        this.parser.registerFunctionProvider(ReportParser.createExtProvider());
        this.parser.registerFunctionProvider((IFunctionProvider)DataSetFunctionProvider.DATASET_PROVIDER);
        this.parser.addParseListener((IParseListener)new DataSetFunctionListener());
    }

    private boolean scanParams(ReportContext context) {
        return context.getReport().getParameters().stream().map(ParameterInfo::getParamModel).anyMatch(p -> p.getSelectMode() == ParameterSelectMode.MUTIPLE);
    }

    private static IFunctionProvider createExtProvider() {
        FunctionProvider provider = new FunctionProvider();
        provider.add((IFunction)new COLUMN());
        provider.add((IFunction)new ROW());
        provider.add((IFunction)new Q_RankOf());
        provider.add((IFunction)new Q_LevelOf());
        provider.add((IFunction)new Q_IsLastLevel());
        provider.add((IFunction)new Q_LinkOf());
        provider.add((IFunction)new Q_Eval());
        provider.add((IFunction)new Lag());
        provider.add((IFunction)new Lead());
        provider.add((IFunction)new SumAdapter("SUM"));
        provider.add((IFunction)new SumAdapter("SumIF"));
        provider.add((IFunction)new IFSAdapter("SumIFS", false));
        provider.add((IFunction)new VRegionLookUpAdapter());
        provider.add((IFunction)new HRegionLookUpAdapter());
        provider.add((IFunction)new IFSAdapter("MinIFS"));
        provider.add((IFunction)new IFSAdapter("MaxIFS"));
        provider.add((IFunction)new IFSAdapter("AvgIFS"));
        provider.add((IFunction)new CountIFSAdapter());
        provider.add((IFunction)new NewSerialAdapter());
        provider.add((IFunction)new GetEnv());
        provider.add((IFunction)new YOY());
        provider.add((IFunction)new MOM());
        provider.add((IFunction)new MoneyStr());
        return provider;
    }

    @Override
    public IContext getContext() {
        return this.context;
    }

    @Override
    public void setParamEnv(com.jiuqi.nvwa.framework.parameter.IParameterEnv paramEnv) {
        if (this.paramProvider != null) {
            this.parser.unregisterDynamicNodeProvider((IDynamicNodeProvider)this.paramProvider);
            this.parser.removeParseListener(this.paramListener);
        }
        this.paramProvider = new ReportParamNodeProvider(paramEnv);
        this.paramListener = ParamSyntax.initParser((FormulaParser)this.parser, (ParamNodeProvider)this.paramProvider);
    }

    @Override
    @Deprecated
    public void setParamEnv(IParameterEnv paramEnv) {
        this.setParamEnv((com.jiuqi.nvwa.framework.parameter.IParameterEnv)new EnhancedParameterEnvAdapter(paramEnv));
    }

    @Override
    public void setWorkbook(ICellProvider workbook) {
        if (this.workbook != null) {
            this.parser.unregisterCellProvider(this.workbook);
        }
        this.workbook = workbook;
        this.parser.registerCellProvider(workbook);
    }

    @Override
    public IReportExpression parseEval(String expr) throws ReportExpressionException {
        IExpression fmlExpr;
        try {
            fmlExpr = this.parser.parseEval(expr, this.context);
        }
        catch (ParseException e) {
            throw new ReportExpressionException(e);
        }
        this.analyseExpression(fmlExpr);
        return new ReportExpression(fmlExpr);
    }

    @Override
    public IReportExpression parseCond(String expr) throws ReportExpressionException {
        IExpression fmlExpr;
        try {
            fmlExpr = this.parser.parseCond(expr, this.context);
        }
        catch (ParseException e) {
            throw new ReportExpressionException(e);
        }
        this.analyseExpression(fmlExpr);
        ReportExpression expression = new ReportExpression(fmlExpr);
        for (IASTNode node : expression.getRootNode()) {
            if (node instanceof ICellNode || node instanceof IRegionNode) {
                throw new ReportExpressionException("\u6761\u4ef6\u8868\u8fbe\u5f0f\u4e2d\u7981\u6b62\u4f7f\u7528\u5355\u5143\u683c\u8bed\u6cd5\u3002");
            }
            if (!(node instanceof FunctionNode) || !(((FunctionNode)node).getDefine() instanceof NewSerialAdapter)) continue;
            throw new ReportExpressionException("\u6761\u4ef6\u8868\u8fbe\u5f0f\u4e2d\u7981\u6b62\u4f7f\u7528NewSerial()\u51fd\u6570\u3002");
        }
        return expression;
    }

    private void analyseExpression(IExpression expr) throws ReportExpressionException {
        if (!this.hasMultiValueParams) {
            return;
        }
        IASTIterator itr = expr.astIterator();
        while (itr.hasNext()) {
            IASTNode node = (IASTNode)itr.next();
            if (node instanceof DSFieldNode) {
                ListIterator<IASTNode> i = ((DSFieldNode)node).getRestrictions().listIterator();
                while (i.hasNext()) {
                    IASTNode inNode = this.tryInParamNode(i.next());
                    if (inNode == null) continue;
                    i.set(inNode);
                }
                continue;
            }
            IASTNode inNode = this.tryInParamNode(node);
            if (inNode == null) continue;
            itr.set(inNode);
        }
    }

    private IASTNode tryInParamNode(IASTNode node) throws ReportExpressionException {
        if (!(node instanceof Equal)) {
            return null;
        }
        boolean inMode = false;
        try {
            ParamNode paramNode;
            if (node.getChild(0) instanceof ParamNode) {
                ParamNode paramNode2 = (ParamNode)node.getChild(0);
                if (paramNode2.getParam().getSelectMode() == ParameterSelectMode.MUTIPLE && node.getChild(1).getType(this.context) != 11) {
                    inMode = true;
                }
            } else if (node.getChild(1) instanceof ParamNode && (paramNode = (ParamNode)node.getChild(1)).getParam().getSelectMode() == ParameterSelectMode.MUTIPLE && node.getChild(0).getType(this.context) != 11) {
                inMode = true;
            }
        }
        catch (SyntaxException e) {
            throw new ReportExpressionException(e);
        }
        return inMode ? new In(node.getToken(), node.getChild(0), node.getChild(1)) : null;
    }

    @Override
    public IReportExpression parseWriteback(String expr) throws ReportExpressionException {
        IReportExpression expression = this.parseEval(expr);
        for (IASTNode node : expression) {
            if (!(node instanceof DSFieldNode) && !(node instanceof DataSetNode)) continue;
            throw new ReportExpressionException("\u56de\u5199\u516c\u5f0f\u4e2d\u7981\u6b62\u76f4\u63a5\u8bbf\u95ee\u6570\u636e\u96c6\u6216\u6570\u636e\u96c6\u5b57\u6bb5\u3002");
        }
        return expression;
    }

    @Override
    public Object getRawParser() {
        return this.parser;
    }

    @Override
    public void beginAction(int action) {
        switch (action) {
            case 1: {
                this.parser.registerFunctionProvider((IFunctionProvider)STYLE_PROVIDER);
                break;
            }
        }
    }

    @Override
    public void endAction(int action) {
        switch (action) {
            case 1: {
                this.parser.unregisterFunctionProvider((IFunctionProvider)STYLE_PROVIDER);
                break;
            }
        }
    }

    static {
        STYLE_PROVIDER.add((IFunction)new Q_CurrentValue());
    }
}

