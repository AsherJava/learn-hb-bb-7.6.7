/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.textprocessor.parse;

import com.jiuqi.bi.dataset.restrict.RestrictionTag;
import com.jiuqi.bi.dataset.textprocessor.parse.TextParser;
import com.jiuqi.bi.dataset.textprocessor.parse.node.TRestrictTagNode;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.List;

public class TDynamicNodeProvider
implements IDynamicNodeProvider {
    private TextParser parser;

    public TDynamicNodeProvider(TextParser parser) {
        this.parser = parser;
    }

    public IASTNode find(IContext context, Token token, String refName) throws DynamicNodeException {
        if (RestrictionTag.isMB(refName) || RestrictionTag.isALL(refName) || RestrictionTag.isPREV(refName) || RestrictionTag.isNEXT(refName) || RestrictionTag.isCURRENT(refName)) {
            return new TRestrictTagNode(token, refName);
        }
        try {
            return this.parser.find(context, token, refName);
        }
        catch (SyntaxException e) {
            throw new DynamicNodeException(e.getMessage(), (Throwable)e);
        }
    }

    public IASTNode find(IContext context, Token token, List<String> objPath) throws DynamicNodeException {
        try {
            return this.parser.find(context, token, objPath);
        }
        catch (SyntaxException e) {
            throw new DynamicNodeException(e.getMessage(), (Throwable)e);
        }
    }

    public IASTNode findSpec(IContext context, Token token, String refName, String spec) throws DynamicNodeException {
        return null;
    }

    public IASTNode findRestrict(IContext context, Token token, List<String> objPath, List<IASTNode> restrictItems) throws DynamicNodeException {
        try {
            return this.parser.findRestrict(context, token, objPath, restrictItems);
        }
        catch (SyntaxException e) {
            throw new DynamicNodeException(e.getMessage(), (Throwable)e);
        }
    }
}

