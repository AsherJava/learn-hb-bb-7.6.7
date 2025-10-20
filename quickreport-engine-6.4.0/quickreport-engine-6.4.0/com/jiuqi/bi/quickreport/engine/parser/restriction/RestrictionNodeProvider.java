/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.parser.restriction;

import com.jiuqi.bi.quickreport.engine.parser.restriction.RestrictionNode;
import com.jiuqi.bi.quickreport.engine.parser.restriction.RestrictionTag;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.List;

public final class RestrictionNodeProvider
implements IDynamicNodeProvider {
    public IASTNode find(IContext context, Token token, String refName) throws DynamicNodeException {
        if (RestrictionTag.isTag(refName)) {
            return new RestrictionNode(token, refName);
        }
        return null;
    }

    public IASTNode find(IContext context, Token token, List<String> objPath) throws DynamicNodeException {
        return null;
    }

    public IASTNode findSpec(IContext context, Token token, String refName, String spec) throws DynamicNodeException {
        return null;
    }

    public IASTNode findRestrict(IContext context, Token token, List<String> objPath, List<IASTNode> restrictItems) throws DynamicNodeException {
        return null;
    }
}

