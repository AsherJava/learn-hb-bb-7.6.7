/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.datasource;

import com.jiuqi.common.base.datasource.DbTypeCondition;
import com.jiuqi.common.base.util.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class BizDataSourceDbTypeCondition
implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String havingValue;
        String dbType;
        Environment environment = context.getEnvironment();
        String bizDataSource = environment.getProperty("jiuqi.gcreport.mdd.datasource");
        if (!StringUtils.isEmpty(bizDataSource)) {
            dbType = environment.getProperty(String.format("jiuqi.nvwa.datasources.%1$s.dbType", bizDataSource));
            if (StringUtils.isEmpty(dbType)) {
                dbType = environment.getProperty("spring.datasource.dbType");
            }
        } else {
            dbType = environment.getProperty("spring.datasource.dbType");
        }
        return !StringUtils.isEmpty(havingValue = (String)metadata.getAnnotationAttributes(DbTypeCondition.class.getName()).get("havingValue")) && havingValue.equals(dbType);
    }
}

