/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider
 */
package com.jiuqi.nr.datacrud.spi.filter;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class FilterFunction
extends Function
implements InitializingBean {
    private static final FilterFunction function = new FilterFunction();
    private final ConcurrentHashMap<String, RowFilter> rowFilterMap = new ConcurrentHashMap();
    public static final String FUNCTION_NAME = "FILTER_ROW";

    public static FilterFunction getInstance() {
        return function;
    }

    private FilterFunction() {
        this.parameters().add(new Parameter("filterName", 6, "\u8fc7\u6ee4\u5668\u540d\u79f0"));
        this.parameters().add(new Parameter("formula", 6, "\u516c\u5f0f"));
    }

    public void registerRowFilter(RowFilter rowFilter) {
        if (rowFilter == null) {
            return;
        }
        if (rowFilter.name() == null) {
            return;
        }
        this.rowFilterMap.put(rowFilter.name(), rowFilter);
    }

    public String name() {
        return FUNCTION_NAME;
    }

    public String title() {
        return "\u8fc7\u6ee4\u6570\u636e\u884c";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) {
        return 1;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> list) throws SyntaxException {
        IASTNode iastNode = list.get(0);
        Object evaluate = iastNode.evaluate(iContext);
        String filterName = evaluate.toString();
        RowFilter filter = this.rowFilterMap.get(filterName);
        if (filter == null) {
            return true;
        }
        iastNode = list.get(1);
        evaluate = iastNode.evaluate(iContext);
        String formula = evaluate.toString();
        return filter.filter(formula, iContext);
    }

    @Override
    public void afterPropertiesSet() {
        ReportFunctionProvider.GLOBAL_PROVIDER.add((IFunction)function);
    }
}

