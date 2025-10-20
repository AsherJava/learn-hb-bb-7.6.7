/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.FunctionProvider
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IFunctionNodeProvider
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.formula.provider;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.FunctionProvider;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IFunctionNodeProvider;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.formula.intf.IFunctionCategoryFilter;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class ModelFunctionProvider
extends FunctionProvider
implements IFunctionNodeProvider {
    private static List<ModelFunction> functions;
    private static List<IFunctionCategoryFilter> categories;
    public static final Map<String, FunctionProvider> functionProviderMap;
    public static final Set<String> isPrivateCategory;

    @Autowired
    private void setFunctions(List<ModelFunction> functions) {
        ModelFunctionProvider.functions = functions;
        this.gatherModelFunctions();
    }

    @Autowired(required=false)
    private void setCategories(List<IFunctionCategoryFilter> categories) {
        ModelFunctionProvider.categories = categories;
        this.getCategories();
    }

    private void getCategories() {
        for (IFunctionCategoryFilter iFunctionCategory : categories) {
            List<String> list = iFunctionCategory.categoryNames();
            isPrivateCategory.addAll(list);
        }
    }

    public static List<ModelFunction> getFunctions() {
        return functions;
    }

    private void gatherModelFunctions() {
        for (ModelFunction function : functions) {
            this.addFunction((IFunction)function);
        }
        FunctionProvider.GLOBAL_PROVIDER.forEach(o -> {
            if ("InList".equals(o.name())) {
                return;
            }
            this.addFunction((IFunction)o);
        });
        FunctionProvider.MATRIX_PROVIDER.forEach(this::addFunction);
        FunctionProvider.STAT_PROVIDER.forEach(this::addFunction);
        FunctionProvider.REFERENCE_PROVIDER.forEach(this::addFunction);
    }

    private void addFunction(IFunction function) {
        if (functionProviderMap.containsKey(function.category())) {
            functionProviderMap.get(function.category()).add(function);
        } else {
            ModelFunctionProvider provider = new ModelFunctionProvider();
            provider.add(function);
            functionProviderMap.put(function.category(), provider);
        }
    }

    public IASTNode createNode(IContext context, Token token, IFunction function, List<IASTNode> parameters) {
        return new FunctionNode(token, function, parameters);
    }

    static {
        functionProviderMap = new HashMap<String, FunctionProvider>();
        isPrivateCategory = new HashSet<String>();
    }
}

