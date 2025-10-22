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
import org.springframework.util.StringUtils;

@Service
public class FormulaDeployExecutorImpl
extends AbstractResourceDeployExecutor {
    @Override
    public ParamResourceType getType() {
        return ParamResourceType.FORMULA;
    }

    @Override
    public void deploy(String schemeKey, List<String> sourceKeys) {
        if (CollectionUtils.isEmpty(sourceKeys)) {
            this.deploy(schemeKey);
        } else {
            for (String sourceKey : sourceKeys) {
                this.deploy(schemeKey, sourceKey);
            }
        }
    }

    private void deploy(String formulaSchemeKey) {
        for (ParamDeploySQL.FormulaScheme item : ParamDeploySQL.FormulaScheme.values()) {
            this.deploy(item, formulaSchemeKey);
        }
    }

    private void deploy(String formulaSchemeKey, String formKey) {
        if (StringUtils.hasText(formKey)) {
            this.deploy(ParamDeploySQL.Formula.DELETE_FORMULA, formulaSchemeKey, formKey);
            this.deploy(ParamDeploySQL.Formula.INSERT_FORMULA, formulaSchemeKey, formKey);
        } else {
            this.deploy(ParamDeploySQL.Formula.DELETE_FORMULA_FORM_ISNULL, formulaSchemeKey);
            this.deploy(ParamDeploySQL.Formula.INSERT_FORMULA_FORM_ISNULL, formulaSchemeKey);
        }
    }

    private void deploy(ParamDeploySQL item, String formulaSchemeKey, String formKey) {
        int update = this.jdbcTemplate.update(item.getDeploySQL(), new Object[]{formulaSchemeKey, formKey});
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a{}\uff0cSQL\uff1a{}\uff0c\u53c2\u6570\uff1a{}\uff0c{}\uff0c\u5f71\u54cd\u884c\u6570\uff1a{}", item.getDescription(), item.getDeploySQL(), formulaSchemeKey, formKey, update);
    }

    private void deploy(ParamDeploySQL item, String formulaSchemeKey) {
        int update = this.jdbcTemplate.update(item.getDeploySQL(), new Object[]{formulaSchemeKey});
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a{}\uff0cSQL\uff1a{}\uff0c\u53c2\u6570\uff1a{}\uff0c\u5f71\u54cd\u884c\u6570\uff1a{}", item.getDescription(), item.getDeploySQL(), formulaSchemeKey, update);
    }

    @Override
    public void rollback(String schemeKey, List<String> sourceKeys) {
        if (CollectionUtils.isEmpty(sourceKeys)) {
            this.rollback(schemeKey);
        } else {
            for (String sourceKey : sourceKeys) {
                this.rollback(schemeKey, sourceKey);
            }
        }
    }

    private void rollback(String formulaSchemeKey) {
        for (ParamDeploySQL.FormulaScheme item : ParamDeploySQL.FormulaScheme.values()) {
            this.rollback(item, formulaSchemeKey);
        }
    }

    private void rollback(String formulaSchemeKey, String formKey) {
        if (StringUtils.hasText(formKey)) {
            this.rollback(ParamDeploySQL.Formula.DELETE_FORMULA, formulaSchemeKey, formKey);
            this.rollback(ParamDeploySQL.Formula.INSERT_FORMULA, formulaSchemeKey, formKey);
        } else {
            this.rollback(ParamDeploySQL.Formula.DELETE_FORMULA_FORM_ISNULL, formulaSchemeKey);
            this.rollback(ParamDeploySQL.Formula.INSERT_FORMULA_FORM_ISNULL, formulaSchemeKey);
        }
    }

    private void rollback(ParamDeploySQL item, String formulaSchemeKey, String formKey) {
        int update = this.jdbcTemplate.update(item.getRollbackSQL(), new Object[]{formulaSchemeKey, formKey});
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u56de\u6eda\uff1a{}\uff0cSQL\uff1a{}\uff0c\u53c2\u6570\uff1a{}\uff0c{}\uff0c\u5f71\u54cd\u884c\u6570\uff1a{}", item.getDescription(), item.getRollbackSQL(), formulaSchemeKey, formKey, update);
    }

    private void rollback(ParamDeploySQL item, String formulaSchemeKey) {
        int update = this.jdbcTemplate.update(item.getRollbackSQL(), new Object[]{formulaSchemeKey});
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u56de\u6eda\uff1a{}\uff0cSQL\uff1a{}\uff0c\u53c2\u6570\uff1a{}\uff0c\u5f71\u54cd\u884c\u6570\uff1a{}", item.getDescription(), item.getRollbackSQL(), formulaSchemeKey, update);
    }
}

