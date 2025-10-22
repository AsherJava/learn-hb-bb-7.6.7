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
import java.util.Collections;
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
@Order(value=40000)
public class ConditionalStyleDeployService
implements IParamDeployExtendService,
IPartialDeployExtendService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionalStyleDeployService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void beforeDeploy(String formSchemeKey, Consumer<String> progress) {
        progress.accept("\u53d1\u5e03\u6761\u4ef6\u6837\u5f0f");
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u53d1\u5e03\u6761\u4ef6\u6837\u5f0f");
        this.beforeDeploy(formSchemeKey, Collections.emptyList());
    }

    @Override
    public void beforeRollback(String formSchemeKey, Consumer<String> progress) {
        progress.accept("\u56de\u6eda\u6761\u4ef6\u6837\u5f0f");
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u56de\u6eda\u6761\u4ef6\u6837\u5f0f");
        this.beforeRollback(formSchemeKey, Collections.emptyList());
    }

    @Override
    public ParamResourceType getType() {
        return ParamResourceType.FORM;
    }

    @Override
    public void beforeDeploy(String schemeKey, List<String> sourceKeys) {
        if (CollectionUtils.isEmpty(sourceKeys)) {
            this.deploy(ParamDeploySQL.ConditionalStyle.DELETE_STYLE, schemeKey);
            this.deploy(ParamDeploySQL.ConditionalStyle.INSERT_STYLE, schemeKey);
        } else {
            for (String sourceKey : sourceKeys) {
                this.deploy(ParamDeploySQL.ConditionalStyle.DELETE_STYLE_BY_FORM, sourceKey);
                this.deploy(ParamDeploySQL.ConditionalStyle.INSERT_STYLE_BY_FORM, sourceKey);
            }
        }
    }

    @Override
    public void beforeRollback(String schemeKey, List<String> sourceKeys) {
        if (CollectionUtils.isEmpty(sourceKeys)) {
            this.rollback(ParamDeploySQL.ConditionalStyle.DELETE_STYLE, schemeKey);
            this.rollback(ParamDeploySQL.ConditionalStyle.INSERT_STYLE, schemeKey);
        } else {
            for (String sourceKey : sourceKeys) {
                this.rollback(ParamDeploySQL.ConditionalStyle.DELETE_STYLE_BY_FORM, sourceKey);
                this.rollback(ParamDeploySQL.ConditionalStyle.INSERT_STYLE_BY_FORM, sourceKey);
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
}

