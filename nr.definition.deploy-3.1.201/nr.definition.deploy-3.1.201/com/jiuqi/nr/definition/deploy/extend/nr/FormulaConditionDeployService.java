/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.ParamResourceType
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.definition.deploy.extend.nr;

import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.deploy.common.ParamDeploySQL;
import com.jiuqi.nr.definition.deploy.extend.IParamDeployExtendService;
import com.jiuqi.nr.definition.deploy.extend.IPartialDeployExtendService;
import java.util.List;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Order(value=30000)
public class FormulaConditionDeployService
implements IParamDeployExtendService,
IPartialDeployExtendService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FormulaConditionDeployService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void beforeDeploy(String formSchemeKey, Consumer<String> progress) {
        progress.accept("\u53d1\u5e03\u516c\u5f0f\u9002\u7528\u6761\u4ef6");
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u53d1\u5e03\u516c\u5f0f\u9002\u7528\u6761\u4ef6");
        this.deploy(ParamDeploySQL.FormulaCondition.DELETE, formSchemeKey);
        this.deploy(ParamDeploySQL.FormulaCondition.DELETE_LINK, formSchemeKey);
        this.deploy(ParamDeploySQL.FormulaCondition.INSERT, formSchemeKey);
        this.deploy(ParamDeploySQL.FormulaCondition.INSERT_LINK, formSchemeKey);
    }

    @Override
    public void beforeRollback(String formSchemeKey, Consumer<String> progress) {
        progress.accept("\u56de\u6eda\u516c\u5f0f\u9002\u7528\u6761\u4ef6");
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u56de\u6eda\u516c\u5f0f\u9002\u7528\u6761\u4ef6");
        this.deploy(ParamDeploySQL.FormulaCondition.DELETE, formSchemeKey);
        this.deploy(ParamDeploySQL.FormulaCondition.DELETE_LINK, formSchemeKey);
        this.deploy(ParamDeploySQL.FormulaCondition.INSERT, formSchemeKey);
        this.deploy(ParamDeploySQL.FormulaCondition.INSERT_LINK, formSchemeKey);
    }

    @Override
    public ParamResourceType getType() {
        return ParamResourceType.FORMULA;
    }

    @Override
    public void beforeDeploy(String schemeKey, List<String> sourceKeys) {
        if (CollectionUtils.isEmpty(sourceKeys)) {
            this.deploy(ParamDeploySQL.FormulaCondition.DELETE_BY_FORMULASCHEME, schemeKey);
            this.deploy(ParamDeploySQL.FormulaCondition.DELETE_LINK_BY_FORMULASCHEME, schemeKey);
            this.deploy(ParamDeploySQL.FormulaCondition.INSERT_BY_FORMULASCHEME, schemeKey);
            this.deploy(ParamDeploySQL.FormulaCondition.INSERT_LINK_BY_FORMULASCHEME, schemeKey);
        } else {
            for (String sourceKey : sourceKeys) {
                if (null == sourceKey) {
                    this.deploy(ParamDeploySQL.FormulaCondition.DELETE_BY_NULLFORM, schemeKey);
                    this.deploy(ParamDeploySQL.FormulaCondition.DELETE_LINK_BY_NULLFORM, schemeKey);
                    this.deploy(ParamDeploySQL.FormulaCondition.INSERT_BY_NULLFORM, schemeKey);
                    this.deploy(ParamDeploySQL.FormulaCondition.INSERT_LINK_BY_NULLFORM, schemeKey);
                    continue;
                }
                this.deploy(ParamDeploySQL.FormulaCondition.DELETE_BY_FORM, schemeKey, sourceKey);
                this.deploy(ParamDeploySQL.FormulaCondition.DELETE_LINK_BY_FORM, schemeKey, sourceKey);
                this.deploy(ParamDeploySQL.FormulaCondition.INSERT_BY_FORM, schemeKey, sourceKey);
                this.deploy(ParamDeploySQL.FormulaCondition.INSERT_LINK_BY_FORM, schemeKey, sourceKey);
            }
        }
    }

    @Override
    public void beforeRollback(String schemeKey, List<String> sourceKeys) {
        if (CollectionUtils.isEmpty(sourceKeys)) {
            this.rollback(ParamDeploySQL.FormulaCondition.DELETE_BY_FORMULASCHEME, schemeKey);
            this.rollback(ParamDeploySQL.FormulaCondition.DELETE_LINK_BY_FORMULASCHEME, schemeKey);
            this.rollback(ParamDeploySQL.FormulaCondition.INSERT_BY_FORMULASCHEME, schemeKey);
            this.rollback(ParamDeploySQL.FormulaCondition.INSERT_LINK_BY_FORMULASCHEME, schemeKey);
        } else {
            for (String sourceKey : sourceKeys) {
                if (null == sourceKey) {
                    this.rollback(ParamDeploySQL.FormulaCondition.DELETE_BY_NULLFORM, schemeKey);
                    this.rollback(ParamDeploySQL.FormulaCondition.DELETE_LINK_BY_NULLFORM, schemeKey);
                    this.rollback(ParamDeploySQL.FormulaCondition.INSERT_BY_NULLFORM, schemeKey);
                    this.rollback(ParamDeploySQL.FormulaCondition.INSERT_LINK_BY_NULLFORM, schemeKey);
                    continue;
                }
                this.rollback(ParamDeploySQL.FormulaCondition.DELETE_BY_FORM, schemeKey, sourceKey);
                this.rollback(ParamDeploySQL.FormulaCondition.DELETE_LINK_BY_FORM, schemeKey, sourceKey);
                this.rollback(ParamDeploySQL.FormulaCondition.INSERT_BY_FORM, schemeKey, sourceKey);
                this.rollback(ParamDeploySQL.FormulaCondition.INSERT_LINK_BY_FORM, schemeKey, sourceKey);
            }
        }
    }

    private void deploy(ParamDeploySQL item, String param) {
        this.jdbcTemplate.update(item.getDeploySQL(), new Object[]{param});
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a{}\uff0cSQL\uff1a{}\uff0c\u53c2\u6570\uff1a{}", item.getDescription(), item.getDeploySQL(), param);
    }

    private void rollback(ParamDeploySQL item, String param) {
        this.jdbcTemplate.update(item.getRollbackSQL(), new Object[]{param});
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u56de\u6eda\uff1a{}\uff0cSQL\uff1a{}\uff0c\u53c2\u6570\uff1a{}", item.getDescription(), item.getRollbackSQL(), param);
    }

    private void deploy(ParamDeploySQL item, String schemeKey, String sourceKey) {
        this.jdbcTemplate.update(item.getDeploySQL(), new Object[]{schemeKey, sourceKey});
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a{}\uff0cSQL\uff1a{}\uff0c\u53c2\u6570\uff1a{}\uff0c{}", item.getDescription(), item.getDeploySQL(), schemeKey, sourceKey);
    }

    private void rollback(ParamDeploySQL item, String schemeKey, String sourceKey) {
        this.jdbcTemplate.update(item.getRollbackSQL(), new Object[]{schemeKey, sourceKey});
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u56de\u6eda\uff1a{}\uff0cSQL\uff1a{}\uff0c\u53c2\u6570\uff1a{}\uff0c{}", item.getDescription(), item.getRollbackSQL(), schemeKey, sourceKey);
    }
}

