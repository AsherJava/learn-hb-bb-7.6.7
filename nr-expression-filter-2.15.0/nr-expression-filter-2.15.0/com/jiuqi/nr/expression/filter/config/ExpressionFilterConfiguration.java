/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.expression.filter.config;

import com.jiuqi.nr.expression.filter.BaseDataExpressionFilter;
import com.jiuqi.nr.expression.filter.OrganizationExpressionFilter;
import com.jiuqi.nr.expression.filter.parse.EntityFormulaParser;
import com.jiuqi.nr.expression.filter.parse.EntityNodeProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpressionFilterConfiguration {
    @Bean(value={"basedata-expression-filter"})
    public BaseDataExpressionFilter getBaseDataExpressionFilter() {
        return new BaseDataExpressionFilter();
    }

    @Bean(value={"organization-expression-filter"})
    public OrganizationExpressionFilter getOrganizationExpressionFilter() {
        return new OrganizationExpressionFilter();
    }

    @Bean(value={"entity-formula-parser"})
    public EntityFormulaParser getEntityFormulaParser() {
        return new EntityFormulaParser();
    }

    @Bean(value={"entity-node-provider"})
    public EntityNodeProvider getEntityNodeProvider() {
        return new EntityNodeProvider();
    }
}

