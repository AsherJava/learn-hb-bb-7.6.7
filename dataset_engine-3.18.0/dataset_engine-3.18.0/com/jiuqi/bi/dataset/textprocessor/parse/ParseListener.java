/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.IParseListener
 *  com.jiuqi.bi.syntax.parser.ParseException
 */
package com.jiuqi.bi.dataset.textprocessor.parse;

import com.jiuqi.bi.dataset.function.Lag;
import com.jiuqi.bi.dataset.function.RowNum;
import com.jiuqi.bi.dataset.textprocessor.parse.ASTNodeStack;
import com.jiuqi.bi.dataset.textprocessor.parse.TFunctionProvider;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.IParseListener;
import com.jiuqi.bi.syntax.parser.ParseException;
import java.util.List;
import java.util.Stack;

public class ParseListener
implements IParseListener {
    private ASTNodeStack nodeStack;
    private Stack<String> allFunc = new Stack();
    private Stack<String> commonFunc = new Stack();

    public ParseListener(ASTNodeStack funcStack) {
        this.nodeStack = funcStack;
    }

    public void startFunction(IContext context, String funcName) throws ParseException {
        IFunction func = TFunctionProvider.DS_PROVIDER.find(context, funcName);
        this.allFunc.add(funcName);
        if (func == null) {
            this.commonFunc.add(funcName);
        } else {
            if (func instanceof RowNum) {
                Object data = this.nodeStack.getCurNodeData();
                boolean isValid = true;
                isValid = data == null ? false : (data instanceof String ? "DS_PRINTROWS".equalsIgnoreCase((String)data) : false);
                if (!isValid) {
                    throw new ParseException("\u51fd\u6570\u3010" + func.name() + "\u3011\u4e0d\u80fd\u72ec\u7acb\u4f7f\u7528\uff0c\u5fc5\u987b\u5d4c\u5957\u5728DS_PRINTROWS\u51fd\u6570\u4e2d\u4f7f\u7528");
                }
            } else if (func instanceof Lag && this.nodeStack.isEmpty()) {
                throw new ParseException("\u51fd\u6570\u3010" + func.name() + "\u3011\u4e0d\u80fd\u72ec\u7acb\u4f7f\u7528\uff0c\u5fc5\u987b\u5d4c\u5957\u5728\u5176\u4ed6\u51fd\u6570\u4e2d\u4f7f\u7528");
            }
            this.nodeStack.pushFunction(funcName);
        }
    }

    public void finishParam(IContext context, IASTNode node) throws ParseException {
        String name = this.allFunc.peek();
        if (this.commonFunc.contains(name)) {
            return;
        }
        this.nodeStack.nextCursor();
    }

    public void finishFunction(IContext context, IASTNode funcNode) throws ParseException {
        String name = this.allFunc.pop();
        if (this.commonFunc.contains(name)) {
            this.commonFunc.pop();
        } else {
            this.nodeStack.pop();
        }
    }

    public void startRestrict(IContext context, List<String> objPathName) {
        this.nodeStack.pushRestrict(objPathName);
    }

    public void finishRestrictItem(IContext context, IASTNode item) {
        this.nodeStack.nextCursor();
    }

    public void finishRestrict(IContext context, IASTNode restrictNode) throws ParseException {
        this.nodeStack.pop();
    }
}

