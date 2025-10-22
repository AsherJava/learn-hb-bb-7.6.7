/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.ParamResourceType
 */
package com.jiuqi.nr.definition.deploy.service.impl;

import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.deploy.common.ParamDeploySQL;
import com.jiuqi.nr.definition.deploy.service.impl.AbstractResourceDeployExecutor;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class PrintDeployExecutorImpl
extends AbstractResourceDeployExecutor {
    @Override
    public ParamResourceType getType() {
        return ParamResourceType.PRINT_TEMPLATE;
    }

    @Override
    public void deploy(String schemeKey, List<String> sourceKeys) {
        if (CollectionUtils.isEmpty(sourceKeys)) {
            this.deploy(ParamDeploySQL.PrintScheme.values(), schemeKey);
        } else {
            for (String sourceKey : sourceKeys) {
                this.deploy(ParamDeploySQL.PrintTemplate.values(), schemeKey, sourceKey);
            }
        }
    }

    @Override
    public void rollback(String schemeKey, List<String> sourceKeys) {
        if (CollectionUtils.isEmpty(sourceKeys)) {
            this.rollback(ParamDeploySQL.PrintScheme.values(), schemeKey);
        } else {
            for (String sourceKey : sourceKeys) {
                this.rollback(ParamDeploySQL.PrintTemplate.values(), schemeKey, sourceKey);
            }
        }
    }

    private void deploy(ParamDeploySQL[] sql, String schemeKey) {
        for (ParamDeploySQL item : sql) {
            int update = this.jdbcTemplate.update(item.getDeploySQL(), new Object[]{schemeKey});
            LOGGER.debug("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a{}\uff0cSQL\uff1a{}\uff0c\u53c2\u6570\uff1a{}\uff0c\u5f71\u54cd\u884c\u6570\uff1a{}", item.getDescription(), item.getDeploySQL(), schemeKey, update);
        }
    }

    private void rollback(ParamDeploySQL[] sql, String schemeKey) {
        for (ParamDeploySQL item : sql) {
            int update = this.jdbcTemplate.update(item.getRollbackSQL(), new Object[]{schemeKey});
            LOGGER.debug("\u62a5\u8868\u53c2\u6570\u56de\u6eda\uff1a{}\uff0cSQL\uff1a{}\uff0c\u53c2\u6570\uff1a{}\uff0c\u5f71\u54cd\u884c\u6570\uff1a{}", item.getDescription(), item.getRollbackSQL(), schemeKey, update);
        }
    }

    private void deploy(ParamDeploySQL[] sql, String schemeKey, String sourceKey) {
        for (ParamDeploySQL item : sql) {
            int update = this.jdbcTemplate.update(item.getDeploySQL(), new Object[]{schemeKey, sourceKey});
            LOGGER.debug("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a{}\uff0cSQL\uff1a{}\uff0c\u53c2\u6570\uff1a{}\uff0c{}\uff0c\u5f71\u54cd\u884c\u6570\uff1a{}", item.getDescription(), item.getDeploySQL(), schemeKey, sourceKey, update);
        }
    }

    private void rollback(ParamDeploySQL[] sql, String schemeKey, String sourceKey) {
        for (ParamDeploySQL item : sql) {
            int update = this.jdbcTemplate.update(item.getRollbackSQL(), new Object[]{schemeKey, sourceKey});
            LOGGER.debug("\u62a5\u8868\u53c2\u6570\u56de\u6eda\uff1a{}\uff0cSQL\uff1a{}\uff0c\u53c2\u6570\uff1a{}\uff0c{}\uff0c\u5f71\u54cd\u884c\u6570\uff1a{}", item.getDescription(), item.getRollbackSQL(), schemeKey, sourceKey, update);
        }
    }
}

