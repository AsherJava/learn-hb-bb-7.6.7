/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.org.OrgDataFilter
 */
package com.jiuqi.va.organization.filter.config;

import com.jiuqi.va.domain.org.OrgDataFilter;
import com.jiuqi.va.organization.filter.BiSyntaxOrganizationDynamicNodeProvider;
import com.jiuqi.va.organization.filter.BiSyntaxOrganizationExpressionParser;
import com.jiuqi.va.organization.filter.BiSyntaxOrganizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrganizationFilterAutoConfiguration {
    @Bean(value={"va-organization-filter-bisyntax-parser-node-provider"})
    BiSyntaxOrganizationDynamicNodeProvider getBiSyntaxOrganizationDynamicNodeProvider() {
        return new BiSyntaxOrganizationDynamicNodeProvider();
    }

    @Bean(value={"va-organization-filter-bisyntax-parser"})
    BiSyntaxOrganizationExpressionParser getBiSyntaxOrganizationExpressionParser() {
        return new BiSyntaxOrganizationExpressionParser();
    }

    @Bean(value={"va-organization-filter-bisyntax"})
    OrgDataFilter getBiSyntaxOrganizationFilter() {
        return new BiSyntaxOrganizationFilter();
    }
}

