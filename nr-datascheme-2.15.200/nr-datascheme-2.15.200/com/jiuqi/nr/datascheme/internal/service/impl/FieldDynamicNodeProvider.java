/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.nr.datascheme.internal.service.FieldNode;
import java.util.List;

public class FieldDynamicNodeProvider
implements IReportDynamicNodeProvider {
    public IASTNode findSpecial(IContext iContext, Token token, String s) {
        return new FieldNode(token);
    }

    public IASTNode find(IContext iContext, Token token, String s) {
        return new FieldNode(token);
    }

    public IASTNode find(IContext iContext, Token token, List<String> list) {
        return new FieldNode(token);
    }

    public IASTNode findSpec(IContext iContext, Token token, String s, String s1) {
        return new FieldNode(token);
    }

    public IASTNode findRestrict(IContext iContext, Token token, List<String> list, List<IASTNode> list1) {
        return new FieldNode(token);
    }
}

