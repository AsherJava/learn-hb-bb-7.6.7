/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 */
package com.jiuqi.gcreport.common.formula;

import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.nr.function.IGcFunction;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GcFunctionProvider
implements IFunctionProvider {
    private static Map<String, IFunction> functions;

    public GcFunctionProvider() {
        if (null == functions) {
            functions = new ConcurrentHashMap<String, IFunction>(128);
            Collection iGcFunctionList = SpringContextUtils.getBeans(IGcFunction.class);
            if (!CollectionUtils.isEmpty((Collection)iGcFunctionList)) {
                iGcFunctionList.stream().forEach(iGcFunction -> this.addFunction((IFunction)iGcFunction));
            }
        }
    }

    public void addFunction(IFunction func) {
        functions.put(func.name(), func);
        if (func.aliases() != null) {
            for (String alias : func.aliases()) {
                functions.put(alias, func);
            }
        }
    }

    public Iterator<IFunction> iterator() {
        return functions.values().iterator();
    }

    public IFunction find(IContext context, String funcName) {
        return functions.get(funcName);
    }

    public String toString() {
        return "GcFunctionProvider";
    }
}

