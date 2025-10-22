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
public class ReportDeployExecutorImpl
extends AbstractResourceDeployExecutor {
    @Override
    public ParamResourceType getType() {
        return ParamResourceType.REPORT_TEMPLATE;
    }

    @Override
    public void deploy(String schemeKey, List<String> sourceKeys) {
        if (CollectionUtils.isEmpty(sourceKeys)) {
            this.deploy(ParamDeploySQL.ReportTemplate.DELETE_TEMPLATES_TAG, schemeKey);
            this.deploy(ParamDeploySQL.ReportTemplate.DELETE_TEMPLATES, schemeKey);
            this.deploy(ParamDeploySQL.ReportTemplate.INSERT_TEMPLATES_TAG, schemeKey);
            this.deploy(ParamDeploySQL.ReportTemplate.INSERT_TEMPLATES, schemeKey);
        } else {
            for (String sourceKey : sourceKeys) {
                this.deploy(ParamDeploySQL.ReportTemplate.DELETE_TEMPLATE_TAG, sourceKey);
                this.deploy(ParamDeploySQL.ReportTemplate.DELETE_TEMPLATE, sourceKey);
                this.deploy(ParamDeploySQL.ReportTemplate.INSERT_TEMPLATE_TAG, sourceKey);
                this.deploy(ParamDeploySQL.ReportTemplate.INSERT_TEMPLATES, sourceKey);
            }
        }
    }

    @Override
    public void rollback(String schemeKey, List<String> sourceKeys) {
        if (CollectionUtils.isEmpty(sourceKeys)) {
            this.rollback(ParamDeploySQL.ReportTemplate.DELETE_TEMPLATES_TAG, schemeKey);
            this.rollback(ParamDeploySQL.ReportTemplate.DELETE_TEMPLATES, schemeKey);
            this.rollback(ParamDeploySQL.ReportTemplate.INSERT_TEMPLATES_TAG, schemeKey);
            this.rollback(ParamDeploySQL.ReportTemplate.INSERT_TEMPLATES, schemeKey);
        } else {
            for (String sourceKey : sourceKeys) {
                this.rollback(ParamDeploySQL.ReportTemplate.DELETE_TEMPLATE_TAG, sourceKey);
                this.rollback(ParamDeploySQL.ReportTemplate.DELETE_TEMPLATE, sourceKey);
                this.rollback(ParamDeploySQL.ReportTemplate.INSERT_TEMPLATE_TAG, sourceKey);
                this.rollback(ParamDeploySQL.ReportTemplate.INSERT_TEMPLATES, sourceKey);
            }
        }
    }

    private void deploy(ParamDeploySQL item, String param) {
        int update = this.jdbcTemplate.update(item.getDeploySQL(), new Object[]{param});
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a{}\uff0cSQL\uff1a{}\uff0c\u53c2\u6570\uff1a{}\uff0c\u5f71\u54cd\u884c\u6570\uff1a{}", item.getDescription(), item.getDeploySQL(), param, update);
    }

    private void rollback(ParamDeploySQL item, String param) {
        int update = this.jdbcTemplate.update(item.getRollbackSQL(), new Object[]{param});
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u56de\u6eda\uff1a{}\uff0cSQL\uff1a{}\uff0c\u53c2\u6570\uff1a{}\uff0c\u5f71\u54cd\u884c\u6570\uff1a{}", item.getDescription(), item.getRollbackSQL(), param, update);
    }
}

