/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataFilter
 *  com.jiuqi.va.domain.org.OrgDataOption$FilterType
 */
package com.jiuqi.va.organization.filter;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataFilter;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.organization.filter.BiSyntaxOrganizationExpressionParser;
import com.jiuqi.va.organization.filter.BiSyntaxOrganizationFilterContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class BiSyntaxOrganizationFilter
implements OrgDataFilter {
    public static final String FILTER_KEY = "BI-SYNTAX";
    @Autowired
    private BiSyntaxOrganizationExpressionParser expressionParser;

    public static OrgDTO applyFilter(OrgDTO orgDTO, String expression) {
        if (orgDTO == null) {
            throw new IllegalArgumentException("'orgDTO' must not be null.");
        }
        if (StringUtils.hasText(expression)) {
            orgDTO.put(FILTER_KEY, null);
            orgDTO.setExpression(expression);
        }
        return orgDTO;
    }

    public static boolean isApplied(OrgDTO data) {
        return data.containsKey((Object)FILTER_KEY);
    }

    public OrgDataOption.FilterType getFilterType() {
        return OrgDataOption.FilterType.FORMULA;
    }

    public Boolean checkData(OrgDTO param, OrgDO data) {
        if (!BiSyntaxOrganizationFilter.isApplied(param)) {
            return null;
        }
        BiSyntaxOrganizationFilterContext ctx = new BiSyntaxOrganizationFilterContext(param, data);
        IExpression exp = this.expressionParser.parseExpression(ctx);
        try {
            return exp.judge((IContext)ctx);
        }
        catch (SyntaxException e) {
            StringBuilder msgBuilder = new StringBuilder();
            msgBuilder.append("\u7ec4\u7ec7\u673a\u6784").append(ctx.getParam().getCategoryname()).append(":").append(ctx.getData().getCode()).append("\u8fc7\u6ee4\u516c\u5f0f\u6267\u884c\u5931\u8d25\uff1a").append(ctx.getParam().getExpression()).append("\u3002");
            throw new OrganizationFilterException(msgBuilder.toString(), e);
        }
    }

    public boolean isEnable(OrgDTO param) {
        return BiSyntaxOrganizationFilter.isApplied(param);
    }

    public static class OrganizationFilterException
    extends RuntimeException {
        private static final long serialVersionUID = -4825340647976422828L;

        public OrganizationFilterException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

