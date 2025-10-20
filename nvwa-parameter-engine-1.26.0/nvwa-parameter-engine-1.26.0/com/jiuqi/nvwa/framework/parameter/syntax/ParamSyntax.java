/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.FunctionProvider
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.parser.FormulaParser
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.IParseListener
 *  com.jiuqi.bi.syntax.parser.ParseException
 */
package com.jiuqi.nvwa.framework.parameter.syntax;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.FunctionProvider;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.parser.FormulaParser;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.IParseListener;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.nvwa.framework.parameter.syntax.ParamNodeProvider;
import com.jiuqi.nvwa.framework.parameter.syntax.ParamTitle;
import com.jiuqi.nvwa.framework.parameter.syntax.ParamValue;
import java.util.List;

public class ParamSyntax {
    public static final FunctionProvider FUNC_PROVIDER = new FunctionProvider();

    private ParamSyntax() {
        throw new UnsupportedOperationException();
    }

    public static IParseListener initParser(FormulaParser parser, final ParamNodeProvider nodeProvider) {
        IParseListener listener = new IParseListener(){

            public void finishFunction(IContext context, IASTNode funcNode) throws ParseException {
                if (funcNode instanceof FunctionNode && FUNC_PROVIDER.exists(((FunctionNode)funcNode).getDefine().name())) {
                    nodeProvider.setZBEnable(true);
                }
            }

            public void startFunction(IContext context, String funcName) throws ParseException {
                if (FUNC_PROVIDER.exists(funcName)) {
                    nodeProvider.setZBEnable(false);
                }
            }

            public void finishParam(IContext context, IASTNode node) throws ParseException {
            }

            public void finishRestrict(IContext context, IASTNode restrictNode) throws ParseException {
            }

            public void finishRestrictItem(IContext context, IASTNode item) throws ParseException {
            }

            public void startRestrict(IContext context, List<String> objPathName) throws ParseException {
            }
        };
        parser.registerFunctionProvider((IFunctionProvider)FUNC_PROVIDER);
        parser.registerDynamicNodeProvider((IDynamicNodeProvider)nodeProvider);
        parser.addParseListener(listener);
        return listener;
    }

    static {
        FUNC_PROVIDER.add((IFunction)new ParamValue());
        FUNC_PROVIDER.add((IFunction)new ParamTitle());
    }
}

