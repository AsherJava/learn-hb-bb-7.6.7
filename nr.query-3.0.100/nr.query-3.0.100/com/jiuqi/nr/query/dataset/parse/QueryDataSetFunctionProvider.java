/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.FunctionException
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nr.query.dataset.parse;

import com.jiuqi.bi.syntax.function.FunctionException;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.query.dataset.parse.getNotNullValue;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class QueryDataSetFunctionProvider
implements IFunctionProvider {
    private Map<String, IFunction> functions = new HashMap<String, IFunction>();

    public QueryDataSetFunctionProvider() {
        this.addfunction((IFunction)new getNotNullValue());
    }

    private void addfunction(IFunction func) {
        this.functions.put(func.name().toUpperCase(), func);
        if (func.aliases() != null) {
            for (String alias : func.aliases()) {
                this.functions.put(alias.toUpperCase(), func);
            }
        }
    }

    public Iterator<IFunction> iterator() {
        return this.functions.values().iterator();
    }

    public IFunction find(IContext context, String funcName) throws FunctionException {
        return this.functions.get(funcName.toUpperCase());
    }
}

