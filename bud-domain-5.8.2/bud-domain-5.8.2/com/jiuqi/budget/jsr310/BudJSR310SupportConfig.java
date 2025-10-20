/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.utils.CommonUtil
 *  com.jiuqi.va.mapper.config.VaMapperConfig
 *  javax.annotation.PostConstruct
 *  org.apache.ibatis.mapping.MappedStatement
 *  org.apache.ibatis.mapping.ResultMap
 *  org.apache.ibatis.mapping.ResultMapping
 *  org.apache.ibatis.session.Configuration
 *  org.apache.ibatis.session.SqlSessionFactory
 *  org.apache.ibatis.type.TypeHandler
 *  org.apache.ibatis.type.TypeHandlerRegistry
 */
package com.jiuqi.budget.jsr310;

import com.jiuqi.budget.common.utils.CommonUtil;
import com.jiuqi.budget.jsr310.BudLocalDateTimeTypeHandler;
import com.jiuqi.budget.jsr310.BudLocalDateTypeHandler;
import com.jiuqi.va.mapper.config.VaMapperConfig;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class BudJSR310SupportConfig {
    private static final Logger logger = LoggerFactory.getLogger(BudJSR310SupportConfig.class);
    @Autowired
    private VaMapperConfig vaMapperConfig;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostConstruct
    void doSupport() {
        String dbType = VaMapperConfig.getDbType();
        if (!"hana".equals(dbType) && !"dameng".equals(dbType)) {
            return;
        }
        Configuration configuration = this.sqlSessionFactory.getConfiguration();
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        BudLocalDateTimeTypeHandler localDateTimeTypeHandler = new BudLocalDateTimeTypeHandler();
        BudLocalDateTypeHandler localDateTypeHandler = new BudLocalDateTypeHandler();
        typeHandlerRegistry.register(LocalDateTime.class, (TypeHandler)localDateTimeTypeHandler);
        typeHandlerRegistry.register(LocalDate.class, (TypeHandler)localDateTypeHandler);
        Collection mappedStatementNames = configuration.getMappedStatementNames();
        Field typeHandlerField = null;
        try {
            typeHandlerField = ResultMapping.class.getDeclaredField("typeHandler");
        }
        catch (NoSuchFieldException e) {
            logger.error(e.getMessage(), e);
        }
        if (typeHandlerField == null) {
            return;
        }
        ReflectionUtils.makeAccessible(typeHandlerField);
        try {
            for (String mappedStatementName : mappedStatementNames) {
                boolean startWith = CommonUtil.startWith((String)mappedStatementName, (String)"com.jiuqi.budget.");
                if (!startWith) continue;
                MappedStatement mappedStatement = configuration.getMappedStatement(mappedStatementName);
                List resultMaps = mappedStatement.getResultMaps();
                for (ResultMap resultMap : resultMaps) {
                    List resultMappings = resultMap.getResultMappings();
                    for (ResultMapping resultMapping : resultMappings) {
                        if (resultMapping.getJavaType() == LocalDateTime.class) {
                            try {
                                typeHandlerField.set(resultMapping, (Object)localDateTimeTypeHandler);
                            }
                            catch (IllegalAccessException e) {
                                logger.error(e.getMessage(), e);
                            }
                            continue;
                        }
                        if (resultMapping.getJavaType() != LocalDate.class) continue;
                        try {
                            typeHandlerField.set(resultMapping, (Object)localDateTypeHandler);
                        }
                        catch (IllegalAccessException e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }
            }
        }
        finally {
            ReflectionUtils.clearCache();
        }
    }
}

