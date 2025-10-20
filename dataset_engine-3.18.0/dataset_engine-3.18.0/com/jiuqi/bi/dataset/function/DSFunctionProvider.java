/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxRuntimeException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.function.FunctionException
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IFunctionNodeProvider
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.function.IMultiInstance
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.function;

import com.jiuqi.bi.dataset.expression.DSFunctionNode;
import com.jiuqi.bi.dataset.function.Bottom;
import com.jiuqi.bi.dataset.function.CRG;
import com.jiuqi.bi.dataset.function.Count;
import com.jiuqi.bi.dataset.function.DenseRankOn;
import com.jiuqi.bi.dataset.function.Filter;
import com.jiuqi.bi.dataset.function.Geomean;
import com.jiuqi.bi.dataset.function.Group;
import com.jiuqi.bi.dataset.function.Lag;
import com.jiuqi.bi.dataset.function.MA;
import com.jiuqi.bi.dataset.function.MOM;
import com.jiuqi.bi.dataset.function.Max;
import com.jiuqi.bi.dataset.function.Min;
import com.jiuqi.bi.dataset.function.Quartile;
import com.jiuqi.bi.dataset.function.RankAt;
import com.jiuqi.bi.dataset.function.RankOn;
import com.jiuqi.bi.dataset.function.RowNum;
import com.jiuqi.bi.dataset.function.Sum;
import com.jiuqi.bi.dataset.function.Top;
import com.jiuqi.bi.dataset.function.YOY;
import com.jiuqi.bi.syntax.SyntaxRuntimeException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.function.FunctionException;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IFunctionNodeProvider;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.function.IMultiInstance;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DSFunctionProvider
implements IFunctionProvider,
IFunctionNodeProvider {
    private List<IFunction> functions = new ArrayList<IFunction>();
    private Map<String, IFunction> finder = new HashMap<String, IFunction>();
    public static final DSFunctionProvider DS_PROVIDER = new DSFunctionProvider();

    public IASTNode createNode(IContext context, Token token, IFunction function, List<IASTNode> parameters) throws FunctionException {
        return new DSFunctionNode(token, function, parameters);
    }

    public void add(IFunction function) {
        if (this.finder.containsKey(function.name().toUpperCase())) {
            throw new SyntaxRuntimeException("\u51fd\u6570\u540d\u79f0\u5b58\u5728\u51b2\u7a81\uff1a" + function.name());
        }
        if (function.aliases() != null) {
            for (String alias : function.aliases()) {
                if (!this.finder.containsKey(alias.toUpperCase())) continue;
                throw new SyntaxRuntimeException("\u51fd\u6570\u522b\u540d\u5b58\u5728\u51b2\u7a81\uff1a" + alias);
            }
        }
        this.functions.add(function);
        this.finder.put(function.name().toUpperCase(), function);
        if (function.aliases() != null) {
            for (String alias : function.aliases()) {
                this.finder.put(alias.toUpperCase(), function);
            }
        }
    }

    public void remove(IFunction function) {
        this.finder.remove(function.name().toUpperCase());
        if (function.aliases() != null) {
            for (String alias : function.aliases()) {
                this.finder.remove(alias.toUpperCase());
            }
        }
        this.functions.remove(function);
    }

    public IFunction find(IContext context, String funcName) throws FunctionException {
        IFunction func = this.finder.get(funcName.toUpperCase());
        if (func == null) {
            return null;
        }
        if (func instanceof IMultiInstance) {
            return (IFunction)((IMultiInstance)func).clone();
        }
        return func;
    }

    public Iterator<IFunction> iterator() {
        return new Itr(this.functions.iterator());
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append('[');
        boolean started = false;
        for (IFunction func : this.functions) {
            if (started) {
                buffer.append(", ");
            } else {
                started = true;
            }
            buffer.append(func.name());
        }
        buffer.append(']');
        return buffer.toString();
    }

    static {
        DS_PROVIDER.add((IFunction)new Top());
        DS_PROVIDER.add((IFunction)new Bottom());
        DS_PROVIDER.add((IFunction)new RankAt());
        DS_PROVIDER.add((IFunction)new RankOn());
        DS_PROVIDER.add((IFunction)new DenseRankOn());
        DS_PROVIDER.add((IFunction)new Count());
        DS_PROVIDER.add((IFunction)new Sum());
        DS_PROVIDER.add((IFunction)new CRG());
        DS_PROVIDER.add((IFunction)new MA());
        DS_PROVIDER.add((IFunction)new Quartile());
        DS_PROVIDER.add((IFunction)new Geomean());
        DS_PROVIDER.add((IFunction)new YOY());
        DS_PROVIDER.add((IFunction)new MOM());
        DS_PROVIDER.add((IFunction)new Group());
        DS_PROVIDER.add((IFunction)new Filter());
        DS_PROVIDER.add((IFunction)new Lag());
        DS_PROVIDER.add((IFunction)new Max());
        DS_PROVIDER.add((IFunction)new Min());
        DS_PROVIDER.add((IFunction)new RowNum());
    }

    private static final class Itr
    implements Iterator<IFunction> {
        private Iterator<IFunction> i;

        public Itr(Iterator<IFunction> i) {
            this.i = i;
        }

        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }

        @Override
        public IFunction next() {
            return this.i.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

