/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.parser.FormulaParser
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 */
package com.jiuqi.va.organization.filter;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.parser.FormulaParser;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.va.organization.filter.BiSyntaxOrganizationDynamicNodeProvider;
import com.jiuqi.va.organization.filter.BiSyntaxOrganizationFilterContext;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

public class BiSyntaxOrganizationExpressionParser {
    private final Map<String, IExpression> expressionCache = new HashMap<String, IExpression>();
    @Autowired
    private BiSyntaxOrganizationDynamicNodeProvider dynamicNodeProvider;

    IExpression parseExpression(BiSyntaxOrganizationFilterContext ctx) {
        IExpression exp = this.searchFromCache(ctx);
        if (exp == null) {
            exp = this.hardParse(ctx);
        }
        return exp;
    }

    IExpression searchFromCache(BiSyntaxOrganizationFilterContext ctx) {
        return this.expressionCache.get(this.makeCacheKey(ctx));
    }

    IExpression hardParse(BiSyntaxOrganizationFilterContext ctx) {
        IExpression exp;
        FormulaParser parser = new FormulaParser();
        parser.registerDynamicNodeProvider((IDynamicNodeProvider)this.dynamicNodeProvider);
        try {
            exp = parser.parseCond(ctx.getParam().getExpression(), (IContext)ctx);
        }
        catch (ParseException e) {
            StringBuilder msgBuilder = new StringBuilder();
            msgBuilder.append("\u7ec4\u7ec7\u673a\u6784").append(ctx.getParam().getCategoryname()).append("\u8fc7\u6ee4\u516c\u5f0f\u89e3\u6790\u5931\u8d25\uff1a").append(ctx.getParam().getExpression()).append("\u3002");
            throw new BaseDataExpressionParseException(msgBuilder.toString(), e);
        }
        this.expressionCache.put(this.makeCacheKey(ctx), exp);
        return exp;
    }

    String makeCacheKey(BiSyntaxOrganizationFilterContext ctx) {
        return ctx.getParam().getCategoryname().concat("_").concat(ctx.getParam().getExpression());
    }

    public static class BaseDataExpressionParseException
    extends RuntimeException {
        private static final long serialVersionUID = -396406475492417546L;

        public BaseDataExpressionParseException(String message) {
            super(message);
        }

        public BaseDataExpressionParseException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

