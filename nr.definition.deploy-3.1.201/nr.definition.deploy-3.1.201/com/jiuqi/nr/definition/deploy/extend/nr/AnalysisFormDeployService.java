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
@Order(value=50000)
public class AnalysisFormDeployService
implements IParamDeployExtendService,
IPartialDeployExtendService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnalysisFormDeployService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private boolean existAnalysisForm(String formSchemeKey) {
        String desSql = "SELECT COUNT(FM_KEY) COUNT FROM NR_PARAM_FORM_DES WHERE FM_ANALYSIS_FORM = 1 AND FM_FORMSCHEME = ?";
        String runSql = "SELECT COUNT(FM_KEY) COUNT FROM NR_PARAM_FORM WHERE FM_ANALYSIS_FORM = 1 AND FM_FORMSCHEME = ?";
        int count = this.jdbcTemplate.queryForList("SELECT COUNT(FM_KEY) COUNT FROM NR_PARAM_FORM_DES WHERE FM_ANALYSIS_FORM = 1 AND FM_FORMSCHEME = ?", Integer.class, new Object[]{formSchemeKey}).stream().findAny().orElse(0);
        if (count != 0) {
            return true;
        }
        count = this.jdbcTemplate.queryForList("SELECT COUNT(FM_KEY) COUNT FROM NR_PARAM_FORM WHERE FM_ANALYSIS_FORM = 1 AND FM_FORMSCHEME = ?", Integer.class, new Object[]{formSchemeKey}).stream().findAny().orElse(0);
        return count != 0;
    }

    @Override
    public void beforeDeploy(String formSchemeKey, Consumer<String> progress) {
        if (!this.existAnalysisForm(formSchemeKey)) {
            return;
        }
        progress.accept("\u53d1\u5e03\u5206\u6790\u8868\u53c2\u6570");
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u53d1\u5e03\u5206\u6790\u8868\u53c2\u6570");
        this.beforeDeploy(formSchemeKey, Collections.emptyList());
    }

    @Override
    public void beforeRollback(String formSchemeKey, Consumer<String> progress) {
        if (!this.existAnalysisForm(formSchemeKey)) {
            return;
        }
        progress.accept("\u56de\u6eda\u5206\u6790\u8868\u53c2\u6570");
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u56de\u6eda\u5206\u6790\u8868\u53c2\u6570");
        this.beforeRollback(formSchemeKey, Collections.emptyList());
    }

    @Override
    public ParamResourceType getType() {
        return ParamResourceType.FORM;
    }

    @Override
    public void beforeDeploy(String schemeKey, List<String> sourceKeys) {
        if (!this.existAnalysisForm(schemeKey)) {
            return;
        }
        if (CollectionUtils.isEmpty(sourceKeys)) {
            this.deploy(ParamDeploySQL.AnalysisForm.DELETE, schemeKey, "ANALYSIS_PARAM");
            this.deploy(ParamDeploySQL.AnalysisForm.DELETE_JOIN_GROUP, schemeKey, "ANALYSIS_GROUP_PARAM");
            this.deploy(ParamDeploySQL.AnalysisForm.DELETE_JOIN_FORM, schemeKey, "ANALYSIS_FORM_PARAM");
            this.deploy(ParamDeploySQL.AnalysisForm.INSERT, schemeKey, "ANALYSIS_PARAM");
            this.deploy(ParamDeploySQL.AnalysisForm.INSERT_JOIN_GROUP, schemeKey, "ANALYSIS_GROUP_PARAM");
            this.deploy(ParamDeploySQL.AnalysisForm.INSERT_JOIN_FORM, schemeKey, "ANALYSIS_FORM_PARAM");
        } else {
            for (String sourceKey : sourceKeys) {
                this.deploy(ParamDeploySQL.AnalysisForm.DELETE, sourceKey, "ANALYSIS_FORM_PARAM");
                this.deploy(ParamDeploySQL.AnalysisForm.INSERT, sourceKey, "ANALYSIS_FORM_PARAM");
            }
        }
    }

    @Override
    public void beforeRollback(String schemeKey, List<String> sourceKeys) {
        if (!this.existAnalysisForm(schemeKey)) {
            return;
        }
        if (CollectionUtils.isEmpty(sourceKeys)) {
            this.rollback(ParamDeploySQL.AnalysisForm.DELETE, schemeKey, "ANALYSIS_PARAM");
            this.rollback(ParamDeploySQL.AnalysisForm.DELETE_JOIN_GROUP, schemeKey, "ANALYSIS_GROUP_PARAM");
            this.rollback(ParamDeploySQL.AnalysisForm.DELETE_JOIN_FORM, schemeKey, "ANALYSIS_FORM_PARAM");
            this.rollback(ParamDeploySQL.AnalysisForm.INSERT, schemeKey, "ANALYSIS_PARAM");
            this.rollback(ParamDeploySQL.AnalysisForm.INSERT_JOIN_GROUP, schemeKey, "ANALYSIS_GROUP_PARAM");
            this.rollback(ParamDeploySQL.AnalysisForm.INSERT_JOIN_FORM, schemeKey, "ANALYSIS_FORM_PARAM");
        } else {
            for (String sourceKey : sourceKeys) {
                this.rollback(ParamDeploySQL.AnalysisForm.DELETE, sourceKey, "ANALYSIS_FORM_PARAM");
                this.rollback(ParamDeploySQL.AnalysisForm.INSERT, sourceKey, "ANALYSIS_FORM_PARAM");
            }
        }
    }

    private void deploy(ParamDeploySQL item, String key, String code) {
        this.jdbcTemplate.update(item.getDeploySQL(), new Object[]{key, code});
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a{}\uff0cSQL\uff1a{}\uff0c\u53c2\u6570\uff1a{}\uff0c{}", item.getDescription(), item.getDeploySQL(), key, code);
    }

    private void rollback(ParamDeploySQL item, String param, String code) {
        this.jdbcTemplate.update(item.getRollbackSQL(), new Object[]{param, code});
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u56de\u6eda\uff1a{}\uff0cSQL\uff1a{}\uff0c\u53c2\u6570\uff1a{}\uff0c{}", item.getDescription(), item.getRollbackSQL(), param, code);
    }
}

