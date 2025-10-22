/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.definition.deploy.extend.nr;

import com.jiuqi.nr.definition.deploy.common.ParamDeploySQL;
import com.jiuqi.nr.definition.deploy.extend.IParamDeployExtendService;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@Order(value=20000)
public class FormulaVariableDeployService
implements IParamDeployExtendService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FormulaVariableDeployService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void afterDeploy(String formSchemeKey, Consumer<String> progress) {
        progress.accept("\u53d1\u5e03\u516c\u5f0f\u53d8\u91cf");
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u53d1\u5e03\u516c\u5f0f\u53d8\u91cf");
        this.deploy(ParamDeploySQL.FormulaVariable.DELETE_FORMULA_VARIABLE, formSchemeKey);
        this.deploy(ParamDeploySQL.FormulaVariable.INSERT_FORMULA_VARIABLE, formSchemeKey);
    }

    @Override
    public void afterRollback(String formSchemeKey, Consumer<String> progress) {
        progress.accept("\u56de\u6eda\u516c\u5f0f\u53d8\u91cf");
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u56de\u6eda\u516c\u5f0f\u53d8\u91cf");
        this.rollback(ParamDeploySQL.FormulaVariable.DELETE_FORMULA_VARIABLE, formSchemeKey);
        this.rollback(ParamDeploySQL.FormulaVariable.INSERT_FORMULA_VARIABLE, formSchemeKey);
    }

    private void deploy(ParamDeploySQL item, String param) {
        this.jdbcTemplate.update(item.getDeploySQL(), new Object[]{param});
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a{}\uff0cSQL\uff1a{}\uff0c\u53c2\u6570\uff1a{}", item.getDescription(), item.getDeploySQL(), param);
    }

    private void rollback(ParamDeploySQL item, String param) {
        this.jdbcTemplate.update(item.getRollbackSQL(), new Object[]{param});
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u56de\u6eda\uff1a{}\uff0cSQL\uff1a{}\uff0c\u53c2\u6570\uff1a{}", item.getDescription(), item.getRollbackSQL(), param);
    }
}

