/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.sql.vo.QueryParamVO
 *  com.jiuqi.va.query.template.plugin.DataSourcePlugin
 *  com.jiuqi.va.query.template.vo.QueryTemplate
 *  org.aspectj.lang.JoinPoint
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Before
 *  org.springframework.web.context.request.RequestContextHolder
 */
package com.jiuqi.va.query.common;

import com.jiuqi.va.query.sql.vo.QueryParamVO;
import com.jiuqi.va.query.template.plugin.DataSourcePlugin;
import com.jiuqi.va.query.template.vo.QueryTemplate;
import com.jiuqi.va.query.util.DCQueryDES;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;

@Aspect
@Component
public class DecryptionAspect {
    @Before(value="execution(* com.jiuqi.va.query.sql.web.QuerySqlController.*(..)) && args(queryTemplate, ..)")
    public void decryptQueryTemplate(JoinPoint joinPoint, QueryTemplate queryTemplate) {
        this.decryptTemplate(queryTemplate);
    }

    @Before(value="execution(* com.jiuqi.va.query.sql.web.QuerySqlController.*(..)) && args(queryParamVO, ..)")
    public void decryptQueryParamVO(JoinPoint joinPoint, QueryParamVO queryParamVO) {
        this.decryptTemplate(queryParamVO.getQueryTemplate());
    }

    @Before(value="execution(* com.jiuqi.va.query.template.web.QueryTemplateContentController.*(..)) && args(queryTemplate, ..)")
    public void decryptQueryParamVO(JoinPoint joinPoint, QueryTemplate queryTemplate) {
        this.decryptTemplate(queryTemplate);
    }

    private void decryptTemplate(QueryTemplate queryTemplate) {
        if (queryTemplate == null) {
            return;
        }
        if (RequestContextHolder.getRequestAttributes() == null) {
            return;
        }
        DataSourcePlugin dataSourceSet = queryTemplate.getDataSourceSet();
        if (dataSourceSet == null) {
            return;
        }
        String defineSql = queryTemplate.getDataSourceSet().getDefineSql();
        boolean encrypted = queryTemplate.getDataSourceSet().isEncrypted();
        if (encrypted && StringUtils.hasText(defineSql)) {
            queryTemplate.getDataSourceSet().setDefineSql(DCQueryDES.decrypt(defineSql));
        }
    }
}

