/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.Expression
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.cell.WildcardExpander
 *  com.jiuqi.bi.syntax.cell.WildcardRange
 *  com.jiuqi.bi.syntax.format.IDataFormator
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.util.ASTHelper
 */
package com.jiuqi.bi.dataset.expression;

import com.jiuqi.bi.dataset.restrict.FilterOptimizer;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.Expression;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.cell.WildcardExpander;
import com.jiuqi.bi.syntax.cell.WildcardRange;
import com.jiuqi.bi.syntax.format.IDataFormator;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.util.ASTHelper;
import java.text.Format;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public final class DSExpression
extends ASTNode
implements IASTNode,
IExpression {
    private static final long serialVersionUID = 1L;
    private IASTNode root;
    private FilterOptimizer optimizer;
    private List<WildcardRange> wildcardRanges;

    public DSExpression(String formula, IASTNode root) {
        super(formula == null ? null : new Token(formula, 0, 0, 0));
        this.root = root;
    }

    public void setOptimizer(FilterOptimizer optimizer) {
        this.optimizer = optimizer;
    }

    public FilterOptimizer getOptimizer() {
        return this.optimizer;
    }

    public ASTNodeType getNodeType() {
        return ASTNodeType.EXPRESSION;
    }

    public List<IASTNode> getSources(boolean assignMode) {
        ArrayList<IASTNode> sources = new ArrayList<IASTNode>();
        IASTNode srcRoot = assignMode && this.root instanceof Equal ? this.root.getChild(1) : this.root;
        for (IASTNode n : srcRoot) {
            switch (n.getNodeType()) {
                case CELL: 
                case DYNAMICDATA: 
                case REGION: {
                    sources.add(n);
                    break;
                }
            }
        }
        return sources;
    }

    public List<IASTNode> getDests(boolean assignMode) {
        ArrayList<IASTNode> dests = new ArrayList<IASTNode>();
        if (assignMode && this.root instanceof Equal && this.root.getChild(0) != null) {
            for (IASTNode n : this.root.getChild(0)) {
                switch (n.getNodeType()) {
                    case CELL: 
                    case DYNAMICDATA: 
                    case REGION: {
                        dests.add(n);
                        break;
                    }
                }
            }
        }
        return dests;
    }

    public int getType(IContext context) throws SyntaxException {
        return this.root.getType(context);
    }

    public Object evaluate(IContext context) throws SyntaxException {
        return this.root.evaluate(context);
    }

    public int execute(IContext context) throws SyntaxException {
        return this.root.execute(context);
    }

    public int childrenSize() {
        return 1;
    }

    public IASTNode getChild(int index) {
        if (index != 0) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        return this.root;
    }

    public void setChild(int index, IASTNode node) {
        this.root = node;
    }

    public boolean isStatic(IContext context) {
        return this.root.isStatic(context);
    }

    public IASTNode optimize(IContext context, int level) throws SyntaxException {
        this.token = null;
        IASTNode newRoot = this.root.optimize(context, level);
        return newRoot == this.root ? this : new Expression(null, newRoot);
    }

    public boolean support(Language lang) {
        return this.root.support(lang);
    }

    public void interpret(IContext context, StringBuilder buffer, Language lang, Object info) throws InterpretException {
        if (this.isBoolCalc(context, lang, info) && !this.root.isStatic(context)) {
            buffer.append("CASE WHEN ");
            this.root.interpret(context, buffer, lang, info);
            buffer.append(" THEN 1 ELSE 0 END");
        } else if (this.isBoolFilter(context, lang, info) && this.root.isStatic(context)) {
            Object value;
            try {
                value = this.root.evaluate(context);
            }
            catch (SyntaxException e) {
                throw new InterpretException((Throwable)e);
            }
            if (value == null || !((Boolean)value).booleanValue()) {
                buffer.append("1=0");
            } else {
                buffer.append("1=1");
            }
        } else {
            this.root.interpret(context, buffer, lang, info);
        }
    }

    private boolean isBoolCalc(IContext context, Language lang, Object info) throws InterpretException {
        try {
            return lang == Language.SQL && !((ISQLInfo)info).isCondition() && this.getType(context) == 1;
        }
        catch (SyntaxException e) {
            throw new InterpretException((Throwable)e);
        }
    }

    private boolean isBoolFilter(IContext context, Language lang, Object info) throws InterpretException {
        try {
            return lang == Language.SQL && ((ISQLInfo)info).isCondition() && this.getType(context) == 1;
        }
        catch (SyntaxException e) {
            throw new InterpretException((Throwable)e);
        }
    }

    public void toString(StringBuilder buffer) {
        buffer.append('(');
        this.root.toString(buffer);
        buffer.append(')');
    }

    public String evalAsString(IContext context) throws SyntaxException {
        Format formator;
        Object obj = this.evaluate(context);
        if (obj == null) {
            return null;
        }
        if (obj instanceof Calendar) {
            obj = ((Calendar)obj).getTime();
        }
        if ((formator = ASTHelper.getNodeFormat((IContext)context, (IASTNode)this.root)) != null) {
            return formator.format(obj);
        }
        return String.valueOf(obj);
    }

    public IDataFormator getDataFormator(IContext context) throws SyntaxException {
        return this.root.getDataFormator(context);
    }

    public List<WildcardRange> getWildcardRanges() {
        if (this.wildcardRanges == null) {
            this.wildcardRanges = new ArrayList<WildcardRange>(2);
        }
        return this.wildcardRanges;
    }

    public List<IExpression> expandWildcards(IContext context) throws SyntaxException {
        WildcardExpander expander = new WildcardExpander(context, (IExpression)this);
        if (expander.expand()) {
            return expander.getExpandedExprs();
        }
        return null;
    }

    public int getWildcardCol() {
        return -1;
    }

    public int getWildcardRow() {
        return -1;
    }

    public void setWildcardPos(int arg0, int arg1) {
    }
}

