/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.context;

import com.jiuqi.bi.quickreport.engine.context.ParsingFunction;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.parser.function.DataSetFunctionNode;
import com.jiuqi.bi.quickreport.engine.parser.function.DataSetProviderFunctionNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ParseContext
implements IContext {
    private Deque<ParsingFunction> parsingFunctions = new ArrayDeque<ParsingFunction>();
    protected final Map<String, Set<String>> refFields = new HashMap<String, Set<String>>();
    private Object tag;

    public Object getTag() {
        return this.tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public final ParsingFunction startFunction(String funcName) {
        ParsingFunction func = new ParsingFunction(funcName);
        this.parsingFunctions.push(func);
        return func;
    }

    public final void finishFunction(DataSetFunctionNode funcNode) throws ReportExpressionException {
        IFunction funcDefine = funcNode.getDefine();
        ParsingFunction func = this.getCurrentFunction();
        if (func == null || !this.isFunction(funcDefine, func.getFuncName())) {
            throw new ReportExpressionException("\u89e3\u6790\u51fd\u6570\u65f6\u51fa\u9519\uff0c\u9047\u5230\u9519\u8bef\u7684\u8ddf\u8e2a\u5806\u6808\uff1a" + (func == null ? funcDefine.name() : func.getFuncName()));
        }
        if (funcNode instanceof DataSetProviderFunctionNode) {
            ((DataSetProviderFunctionNode)funcNode).setDataSetModel(func.getDataSet());
        }
        this.parsingFunctions.pop();
    }

    private boolean isFunction(IFunction funcDefine, String funcName) {
        if (funcDefine.name().equalsIgnoreCase(funcName)) {
            return true;
        }
        String[] aliases = funcDefine.aliases();
        if (aliases != null) {
            for (String name : aliases) {
                if (!name.equalsIgnoreCase(funcName)) continue;
                return true;
            }
        }
        return false;
    }

    public final ParsingFunction getCurrentFunction() {
        return this.parsingFunctions.isEmpty() ? null : this.parsingFunctions.peek();
    }

    public void refField(String dataSetName, String fieldName) {
        Set<String> fields = this.refFields.get(dataSetName = dataSetName.toUpperCase());
        if (fields == null) {
            fields = new HashSet<String>();
            this.refFields.put(dataSetName, fields);
        }
        fields.add(fieldName.toUpperCase());
    }
}

