/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.util.UUIDUtils
 */
package com.jiuqi.gcreport.nr.impl.function.impl;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.nr.impl.function.NrFunction;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GetUIDFunction
extends NrFunction {
    private final String FUNCTION_NAME = "GETUID";

    public String name() {
        return "GETUID";
    }

    public String title() {
        return "\u751f\u6210\u552f\u4e00\u6807\u8bc6";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return UUIDUtils.newUUIDStr();
    }
}

