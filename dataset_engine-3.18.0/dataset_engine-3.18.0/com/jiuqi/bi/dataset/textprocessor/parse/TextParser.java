/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTIterator
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.operator.Plus
 *  com.jiuqi.bi.syntax.parser.FormulaParser
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.IParseListener
 *  com.jiuqi.nvwa.framework.parameter.syntax.ParamNode
 *  com.jiuqi.nvwa.framework.parameter.syntax.ParamSyntax
 */
package com.jiuqi.bi.dataset.textprocessor.parse;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetNotFoundException;
import com.jiuqi.bi.dataset.expression.DSFieldNode;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.textprocessor.IDataSourceProvider;
import com.jiuqi.bi.dataset.textprocessor.TextFormulaContext;
import com.jiuqi.bi.dataset.textprocessor.parse.ASTNodeStack;
import com.jiuqi.bi.dataset.textprocessor.parse.ParseListener;
import com.jiuqi.bi.dataset.textprocessor.parse.TDynamicNodeProvider;
import com.jiuqi.bi.dataset.textprocessor.parse.TFunctionProvider;
import com.jiuqi.bi.dataset.textprocessor.parse.TextFormulaExpression;
import com.jiuqi.bi.dataset.textprocessor.parse.func.TFunction;
import com.jiuqi.bi.dataset.textprocessor.parse.node.DSNode;
import com.jiuqi.bi.dataset.textprocessor.parse.node.TFieldNode;
import com.jiuqi.bi.dataset.textprocessor.parse.node.TRestrictNode;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTIterator;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.operator.Plus;
import com.jiuqi.bi.syntax.parser.FormulaParser;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.IParseListener;
import com.jiuqi.nvwa.framework.parameter.syntax.ParamNode;
import com.jiuqi.nvwa.framework.parameter.syntax.ParamSyntax;
import java.util.List;
import java.util.Set;

public class TextParser {
    private FormulaParser parser;
    private ASTNodeStack funcStack = new ASTNodeStack();

    public TextParser() {
        this.parser = FormulaParser.getInstance();
        this.parser.addParseListener((IParseListener)new ParseListener(this.funcStack));
        this.parser.registerFunctionProvider((IFunctionProvider)TFunctionProvider.DS_PROVIDER);
        this.parser.registerFunctionProvider((IFunctionProvider)ParamSyntax.FUNC_PROVIDER);
        this.parser.registerDynamicNodeProvider((IDynamicNodeProvider)new TDynamicNodeProvider(this));
    }

    public void registerParamProvider(IDynamicNodeProvider paramProvider) {
        this.parser.registerDynamicNodeProvider(paramProvider);
    }

    public void unregisterParamProvider(IDynamicNodeProvider paramProvider) {
        this.parser.unregisterDynamicNodeProvider(paramProvider);
    }

    public void registerFunctionProvider(IFunctionProvider funcProvider) {
        this.parser.registerFunctionProvider(funcProvider);
    }

    public void unregisterFunctionProvider(IFunctionProvider funcProvider) {
        this.parser.unregisterFunctionProvider(funcProvider);
    }

    public IExpression parse(String formula, TextFormulaContext context) throws SyntaxException {
        IExpression expr = this.parser.parseEval(formula, (IContext)context);
        IASTNode root = expr.getChild(0);
        this.modifyFieldNodeValueMode(root, context);
        return new TextFormulaExpression(root);
    }

    private void modifyFieldNodeValueMode(IASTNode node, IContext context) throws SyntaxException {
        if (node instanceof ParamNode) {
            ParamNode pn = (ParamNode)node;
            pn.setValueMode(2);
        } else if (node instanceof DSFieldNode) {
            DSFieldNode dn = (DSFieldNode)node;
            dn.setValueMode(2);
        } else if (node instanceof TFieldNode) {
            TFieldNode dn = (TFieldNode)node;
            dn.setValueMode(2);
        } else {
            IASTIterator itor = node.astIterator();
            while (itor.hasNext()) {
                IASTNode n = (IASTNode)itor.next();
                if (!(n instanceof Plus)) continue;
                IASTNode child0 = n.getChild(0);
                IASTNode child1 = n.getChild(1);
                if (this.isStringNode(child0, context) && this.isDynamicNode(child1)) {
                    this.modifyFieldNodeValueMode(child1, context);
                }
                if (!this.isStringNode(child1, context) || !this.isDynamicNode(child0)) continue;
                this.modifyFieldNodeValueMode(child0, context);
            }
        }
    }

    private boolean isStringNode(IASTNode node, IContext context) throws SyntaxException {
        return node.getType(context) == 6;
    }

    private boolean isDynamicNode(IASTNode node) {
        return node instanceof ParamNode || node instanceof DSFieldNode || node instanceof TFieldNode;
    }

    public Set<IFunction> allFunctions() {
        return this.parser.allFunctions();
    }

    final IASTNode find(IContext context, Token token, String refName) throws SyntaxException {
        List restrictName;
        if (this.funcStack.isEmpty()) {
            return null;
        }
        TextFormulaContext tfc = (TextFormulaContext)context;
        IDataSourceProvider dsProvider = tfc.getDsProvider();
        String type = this.funcStack.getCurNodeType();
        if (type.equals("FUNCTION")) {
            String funcName = (String)this.funcStack.getCurNodeData();
            IFunction func = TFunctionProvider.DS_PROVIDER.find(context, funcName);
            if (func instanceof TFunction) {
                TFunction tf = (TFunction)func;
                int cursor = this.funcStack.getCurCursor();
                if (tf.isDatasetNodeParam(cursor)) {
                    DSModel dsModel;
                    if (dsProvider == null) {
                        throw new SyntaxException("\u672a\u8bbe\u7f6e\u6570\u636e\u63d0\u4f9b\u5668\uff0c\u65e0\u6cd5\u89e3\u6790\u6807\u8bc6\u4fe1\u606f");
                    }
                    try {
                        dsModel = dsProvider.findDatasetModel(refName);
                    }
                    catch (BIDataSetNotFoundException e) {
                        throw new SyntaxException("\u6570\u636e\u96c6\u3010" + refName + "\u3011\u4e0d\u5b58\u5728", (Throwable)e);
                    }
                    catch (BIDataSetException e) {
                        throw new SyntaxException(e.getMessage(), (Throwable)e);
                    }
                    return new DSNode(token, dsModel);
                }
                BIDataSetFieldInfo info = new BIDataSetFieldInfo();
                info.setName(refName);
                return new DSFieldNode(token, info);
            }
        } else if (type.equals("RESTRICT") && (restrictName = (List)this.funcStack.getCurNodeData()).size() == 2) {
            BIDataSetFieldInfo info = new BIDataSetFieldInfo();
            info.setName(refName);
            return new DSFieldNode(token, info);
        }
        return null;
    }

    final IASTNode find(IContext context, Token token, List<String> objPath) throws SyntaxException {
        DSModel dsModel;
        if (objPath.size() != 2) {
            return null;
        }
        TextFormulaContext tfc = (TextFormulaContext)context;
        IDataSourceProvider dsProvider = tfc.getDsProvider();
        if (dsProvider == null) {
            throw new SyntaxException("\u672a\u8bbe\u7f6e\u6570\u636e\u63d0\u4f9b\u5668\uff0c\u65e0\u6cd5\u89e3\u6790\u6807\u8bc6\u4fe1\u606f");
        }
        String dsName = objPath.get(0);
        String fdName = objPath.get(1);
        try {
            dsModel = dsProvider.findDatasetModel(dsName);
            if (dsModel == null) {
                return null;
            }
        }
        catch (BIDataSetNotFoundException e) {
            return null;
        }
        catch (BIDataSetException e) {
            throw new SyntaxException(e.getMessage(), (Throwable)e);
        }
        BIDataSetFieldInfo info = new BIDataSetFieldInfo();
        for (DSField field : dsModel.getFields()) {
            if (!field.getName().equalsIgnoreCase(fdName)) continue;
            info.loadFromDSField(field);
            return new TFieldNode(token, dsModel, info);
        }
        throw new SyntaxException("\u6570\u636e\u96c6" + dsName + "\u4e2d\u4e0d\u5b58\u5728\u5b57\u6bb5" + fdName);
    }

    final IASTNode findRestrict(IContext context, Token token, List<String> objPath, List<IASTNode> restrictItems) throws SyntaxException {
        DSModel dsModel;
        if (objPath.size() == 1 && restrictItems.size() == 0) {
            return this.find(context, token, objPath.get(0));
        }
        if (objPath.size() != 2) {
            return null;
        }
        TextFormulaContext tfc = (TextFormulaContext)context;
        IDataSourceProvider dsProvider = tfc.getDsProvider();
        if (dsProvider == null) {
            throw new SyntaxException("\u672a\u8bbe\u7f6e\u6570\u636e\u63d0\u4f9b\u5668\uff0c\u65e0\u6cd5\u89e3\u6790\u6807\u8bc6\u4fe1\u606f");
        }
        String dsName = objPath.get(0);
        String fdName = objPath.get(1);
        try {
            dsModel = dsProvider.findDatasetModel(dsName);
            if (dsModel == null) {
                return null;
            }
        }
        catch (BIDataSetNotFoundException e) {
            return null;
        }
        catch (BIDataSetException e) {
            throw new SyntaxException(e.getMessage(), (Throwable)e);
        }
        for (DSField fd : dsModel.getFields()) {
            if (!fd.getName().equalsIgnoreCase(fdName)) continue;
            BIDataSetFieldInfo info = new BIDataSetFieldInfo();
            info.loadFromDSField(fd);
            return new TRestrictNode(token, dsModel, info, restrictItems);
        }
        throw new SyntaxException("\u6570\u636e\u96c6" + dsName + "\u4e2d\u4e0d\u5b58\u5728\u5b57\u6bb5" + fdName);
    }
}

