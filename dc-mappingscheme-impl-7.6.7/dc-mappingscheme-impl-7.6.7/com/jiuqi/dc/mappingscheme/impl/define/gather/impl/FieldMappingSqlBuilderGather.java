/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil
 */
package com.jiuqi.dc.mappingscheme.impl.define.gather.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.SqlHandlerUtil;
import com.jiuqi.dc.mappingscheme.impl.define.IFieldMappingSqlBuilder;
import com.jiuqi.dc.mappingscheme.impl.define.IRuleType;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IFieldMappingSqlBuilderGather;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FieldMappingSqlBuilderGather
implements InitializingBean,
IFieldMappingSqlBuilderGather {
    @Autowired
    private IDbSqlHandler sqlHandler;
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired(required=false)
    private List<IFieldMappingSqlBuilder> providerList;
    private final Map<String, IFieldMappingSqlBuilder> providerMap = new ConcurrentHashMap<String, IFieldMappingSqlBuilder>();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        if (CollectionUtils.isEmpty(this.providerList)) {
            this.providerMap.clear();
            return;
        }
        this.providerList.forEach(item -> {
            String ruleTypeCode = Optional.ofNullable(item.getRuleType()).map(IRuleType::getCode).orElse(null);
            if (StringUtils.isEmpty((String)ruleTypeCode)) {
                this.logger.warn("\u5b57\u6bb5\u6620\u5c04Sql\u63d0\u4f9b\u5668\u63d2\u4ef6{}\u89c4\u5219\u7c7b\u578b\u53c2\u6570\u9519\u8bef\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            this.providerMap.computeIfAbsent(ruleTypeCode, k -> item);
        });
    }

    @Override
    public IFieldMappingSqlBuilder getProvider(String ruleType) {
        Assert.isNotNull((Object)ruleType);
        IFieldMappingSqlBuilder sqlBuilder = this.providerMap.get(ruleType);
        sqlBuilder.setSqlHandler(this.sqlHandler);
        return sqlBuilder;
    }

    @Override
    public IFieldMappingSqlBuilder getProvider(String ruleType, String dataSourceCode) {
        Assert.isNotNull((Object)ruleType);
        IDbSqlHandler sqlHandler = SqlHandlerUtil.getDbSqlHandler((String)this.dataSourceService.getDbType(dataSourceCode));
        IFieldMappingSqlBuilder sqlBuilder = this.providerMap.get(ruleType);
        sqlBuilder.setSqlHandler(sqlHandler);
        return sqlBuilder;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}

